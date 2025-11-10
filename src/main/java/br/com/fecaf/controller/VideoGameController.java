package br.com.fecaf.controller;

import br.com.fecaf.model.VideoGame;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/jogos")
public class VideoGameController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoGameController.class);

    private final List<VideoGame> videoGames = new ArrayList<>();

    @PostConstruct
    public void carregarJson() {
        try (InputStream stream = getClass().getResourceAsStream("/data/jogos.json")) {
            if (stream == null) {
                throw new IOException("Recurso /data/jogos.json não encontrado");
            }
            ObjectMapper mapper = new ObjectMapper();
            List<VideoGame> conteudo = mapper.readValue(stream, new TypeReference<List<VideoGame>>() {
            });
            videoGames.clear();
            videoGames.addAll(conteudo);
            LOGGER.info("Catálogo de jogos carregado com sucesso ({} registros).", videoGames.size());
        } catch (IOException e) {
            LOGGER.error("Falha ao carregar o catálogo de jogos", e);
            throw new IllegalStateException("Não foi possível carregar o catálogo de jogos", e);
        }
    }

    @GetMapping
    public ResponseEntity<List<VideoGame>> listarJogos() {
        return ResponseEntity.ok(new ArrayList<>(videoGames));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoGame> buscarJogo(@PathVariable("id") int id) {
        return videoGames.stream()
                .filter(jogo -> jogo.getId() == id)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<VideoGame> cadastrarJogo(@RequestBody VideoGame novoJogo) {
        int proximoId = videoGames.stream()
                .map(VideoGame::getId)
                .max(Comparator.naturalOrder())
                .orElse(0) + 1;
        novoJogo.setId(proximoId);
        videoGames.add(novoJogo);
        LOGGER.info("Jogo '{}' cadastrado com ID {}", novoJogo.getTitulo(), novoJogo.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(novoJogo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletarJogo(@PathVariable("id") int id) {
        boolean removido = videoGames.removeIf(jogo -> jogo.getId() == id);
        if (removido) {
            LOGGER.info("Jogo com ID {} removido do catálogo", id);
            return ResponseEntity.ok(Map.of("mensagem", "Jogo removido com sucesso."));
        }
        LOGGER.warn("Tentativa de remover jogo inexistente (ID: {})", id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("mensagem", "Jogo não encontrado."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideoGame> editarJogo(@PathVariable("id") int id, @RequestBody VideoGame jogoAtualizado) {
        Optional<VideoGame> existente = videoGames.stream()
                .filter(jogo -> jogo.getId() == id)
                .findFirst();

        if (existente.isEmpty()) {
            LOGGER.warn("Tentativa de atualizar jogo inexistente (ID: {})", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        VideoGame jogo = existente.get();
        jogo.setTitulo(jogoAtualizado.getTitulo());
        jogo.setDesenvolvedora(jogoAtualizado.getDesenvolvedora());
        jogo.setGenero(jogoAtualizado.getGenero());
        jogo.setPlataforma(jogoAtualizado.getPlataforma());
        jogo.setAnoLancamento(jogoAtualizado.getAnoLancamento());
        jogo.setPreco(jogoAtualizado.getPreco());
        jogo.setClassificacao(jogoAtualizado.getClassificacao());
        jogo.setNota(jogoAtualizado.getNota());
        jogo.setVendas(jogoAtualizado.getVendas());
        jogo.setStatus(jogoAtualizado.getStatus());

        LOGGER.info("Jogo com ID {} atualizado", id);
        return ResponseEntity.ok(jogo);
    }
}
