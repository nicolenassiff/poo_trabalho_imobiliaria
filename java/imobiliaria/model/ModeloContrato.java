package imobiliaria.model;

import java.time.LocalDate;

public class ModeloContrato {
    private int id;
    private int clienteId;
    private int imovelId;
    private double valor;
    private LocalDate inicio;
    private LocalDate fim;

    // Construtor vazio
    public ModeloContrato() {}

    // Construtor com todos os campos (inclui ID)
    public ModeloContrato(int id, int clienteId, int imovelId, double valor, LocalDate inicio, LocalDate fim) {
        this.id = id;
        this.clienteId = clienteId;
        this.imovelId = imovelId;
        this.valor = valor;
        this.inicio = inicio;
        this.fim = fim;
    }

    // Construtor sem ID (para cadastro)
    public ModeloContrato(int clienteId, int imovelId, double valor, LocalDate inicio, LocalDate fim) {
        this.clienteId = clienteId;
        this.imovelId = imovelId;
        this.valor = valor;
        this.inicio = inicio;
        this.fim = fim;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getClienteId() { return clienteId; }
    public void setClienteId(int clienteId) { this.clienteId = clienteId; }

    public int getImovelId() { return imovelId; }
    public void setImovelId(int imovelId) { this.imovelId = imovelId; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public LocalDate getInicio() { return inicio; }
    public void setInicio(LocalDate inicio) { this.inicio = inicio; }

    public LocalDate getFim() { return fim; }
    public void setFim(LocalDate fim) { this.fim = fim; }

    // toString para exibir no terminal
    @Override
    public String toString() {
        return "ID: " + id +
                " | Cliente ID: " + clienteId +
                " | Imóvel ID: " + imovelId +
                " | Valor: R$" + valor +
                " | Início: " + inicio +
                " | Fim: " + fim;
    }
}
