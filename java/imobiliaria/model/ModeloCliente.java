package imobiliaria.model;

public class ModeloCliente {
    private int id;
    private String nome;
    private String telefone;

    // Construtor vazio
    public ModeloCliente() {}

    // Construtor com ID
    public ModeloCliente(int id, String nome, String telefone) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
    }

    // Construtor sem ID (para cadastro)
    public ModeloCliente(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    // toString para exibir no terminal
    @Override
    public String toString() {
        return "ID: " + id + " | Nome: " + nome + " | Telefone: " + telefone;
    }
}
