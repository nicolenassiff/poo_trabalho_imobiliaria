package imobiliaria.model;

public class ModeloImovel {
    private int id;
    private String endereco;
    private String tipo;
    private double aluguel;
    private String status;

    // Construtor vazio
    public ModeloImovel() {}

    // Construtor com ID
    public ModeloImovel(int id, String endereco, String tipo, double aluguel, String status) {
        this.id = id;
        this.endereco = endereco;
        this.tipo = tipo;
        this.aluguel = aluguel;
        this.status = status;
    }

    // Construtor sem ID (para cadastro)
    public ModeloImovel(String endereco, String tipo, double aluguel, String status) {
        this.endereco = endereco;
        this.tipo = tipo;
        this.aluguel = aluguel;
        this.status = status;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public double getAluguel() { return aluguel; }
    public void setAluguel(double aluguel) { this.aluguel = aluguel; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // toString para exibir no terminal
    @Override
    public String toString() {
        return "ID: " + id + " | Endereço: " + endereco + " | Tipo: " + tipo +
                " | Aluguel: R$" + aluguel + " | Status: " + status;
    }
}
