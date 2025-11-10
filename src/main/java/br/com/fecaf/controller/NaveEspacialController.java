package br.com.fecaf.controller;

import br.com.fecaf.model.NaveEspacial;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController// leitura e escrita.
@RequestMapping("/naves")// para acessar localmente as funcionalidades da classe.
public class NaveEspacialController {

    private List<NaveEspacial> naves = new ArrayList<>();

    @PostConstruct
    public void carregarJson(){


        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream strem = getClass().getResourceAsStream("/data/naves.json");
            this.naves = mapper.readValue(strem, new TypeReference<List<NaveEspacial>>() {});
            System.out.println("O Json foi carregado!");
        } catch (IOException e) {
            System.out.println("Não foi possivel carregar o Json!");
            throw new RuntimeException(e);
        }

    }
    @GetMapping
    public List<NaveEspacial> listarNaves (){
        return naves;
    }
    @GetMapping("{id}")
    public NaveEspacial buscarNave(@PathVariable("id") int id){
        for(NaveEspacial nave : naves){
            if(nave.getId() == id ){
                return nave; // retorna nave encontrada pelo id
            }
        }
        return null;


    }
    @PostMapping
    public NaveEspacial cadastrarNave(@RequestBody NaveEspacial novaNave) {
        //  Atribuir um ID automaticamente (caso necessário)
        novaNave.setId(naves.size() + 1);

        //  Adicionar a nova nave à lista
        naves.add(novaNave);

        //  Retornar a nave cadastrada
        return novaNave;
    }

    @DeleteMapping("{id}")
    public String deletarNave(@PathVariable("id") int id) {
        boolean removido = naves.removeIf(nave -> nave.getId() == id);

        if (removido) {
            return" Nave com ID " + id + " removida com sucesso!";
        } else {
            return" Nave com ID " + id + " não encontrada.";
        }
    }
    @PutMapping
    public NaveEspacial editarNave(@RequestBody NaveEspacial naveAtualizada){
        boolean removido = naves.removeIf(nave -> nave.getId() == naveAtualizada.getId());

        if(removido){
            naves.add(naveAtualizada);
            return naveAtualizada;
        }
        return null;

    }

}




