package br.com.fecaf.model;



//id, nome, tipo, fabricante, anoLancamento, capacidadePassageiros, velocidadeMaxima, combustivel, missao, status, custoOperacional

public class NaveEspacial {
    private String nome, tipo, fabricante, combustivel, missao, status;
    private int id, anoLacamento, capacidadePassageiros;
    private double velocidadeMaximo, custoOperacional;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
    }

    public String getMissao() {
        return missao;
    }

    public void setMissao(String missao) {
        this.missao = missao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnoLacamento() {
        return anoLacamento;
    }

    public void setAnoLacamento(int anoLacamento) {
        this.anoLacamento = anoLacamento;
    }

    public int getCapacidadePassageiros() {
        return capacidadePassageiros;
    }

    public void setCapacidadePassageiros(int capacidadePassageiros) {
        this.capacidadePassageiros = capacidadePassageiros;
    }

    public double getVelocidadeMaximo() {
        return velocidadeMaximo;
    }

    public void setVelocidadeMaximo(double velocidadeMaximo) {
        this.velocidadeMaximo = velocidadeMaximo;
    }

    public double getCustoOperacional() {
        return custoOperacional;
    }

    public void setCustoOperacional(double custoOperacional) {
        this.custoOperacional = custoOperacional;
    }

    @Override
    public String toString() {
        return "NaveEspacial{" +
                "nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", fabricante='" + fabricante + '\'' +
                ", combustivel='" + combustivel + '\'' +
                ", missao='" + missao + '\'' +
                ", status='" + status + '\'' +
                ", id=" + id +
                ", anoLacamento=" + anoLacamento +
                ", capacidadePassageiros=" + capacidadePassageiros +
                ", velocidadeMaximo=" + velocidadeMaximo +
                ", custoOperacional=" + custoOperacional +
                '}';
    }
}

