package imobiliaria;

import imobiliaria.DAO.ClienteDAO;
import imobiliaria.DAO.ContratoDAO;
import imobiliaria.DAO.ImovelDAO;
import imobiliaria.model.ModeloCliente;
import imobiliaria.model.ModeloContrato;
import imobiliaria.model.ModeloImovel;

import java.time.LocalDate;
import java.util.*;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static ImovelDAO imovelDAO = new ImovelDAO();
    private static ClienteDAO clienteDAO = new ClienteDAO();
    private static ContratoDAO contratoDAO = new ContratoDAO();

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n===== MENU IMOBILIÁRIA =====");
            System.out.println("1. Cadastrar Imóvel");
            System.out.println("2. Listar Imóveis Disponíveis");
            System.out.println("3. Cadastrar Cliente");
            System.out.println("4. Listar Clientes");
            System.out.println("5. Cadastrar Contrato");
            System.out.println("6. Listar Contratos Ativos");
            System.out.println("7. Contratos Expirando nos Próximos 30 Dias");
            System.out.println("8. Clientes com Mais Contratos");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");

            int opcao = sc.nextInt();
            sc.nextLine(); // limpar buffer

            switch (opcao) {
                case 1 -> cadastrarImovel();
                case 2 -> listarImoveis();
                case 3 -> cadastrarCliente();
                case 4 -> listarClientes();
                case 5 -> cadastrarContrato();
                case 6 -> listarContratosAtivos();
                case 7 -> contratosExpirando();
                case 8 -> clientesComMaisContratos();
                case 0 -> {
                    System.out.println("Encerrando...");
                    System.exit(0);
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private static void cadastrarImovel() {
        System.out.print("Endereço: ");
        String endereco = sc.nextLine();
        System.out.print("Tipo: ");
        String tipo = sc.nextLine();
        System.out.print("Valor do aluguel: ");
        double aluguel = sc.nextDouble();
        sc.nextLine();

        ModeloImovel imovel = new ModeloImovel(0, endereco, tipo, aluguel, "disponivel");
        imovelDAO.inserir(imovel);
        System.out.println("✅ Imóvel cadastrado!");
    }

    private static void listarImoveis() {
        List<ModeloImovel> imoveis = imovelDAO.listarDisponiveis();
        if (imoveis.isEmpty()) {
            System.out.println("Nenhum imóvel disponível.");
            return;
        }
        for (ModeloImovel i : imoveis) {
            System.out.println(i);
        }
    }

    private static void cadastrarCliente() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Telefone: ");
        String telefone = sc.nextLine();

        ModeloCliente cliente = new ModeloCliente(nome, telefone);
        clienteDAO.inserir(cliente);
        System.out.println("✅ Cliente cadastrado!");
    }

    private static void listarClientes() {
        List<ModeloCliente> clientes = clienteDAO.listar();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        clientes.sort(Comparator.comparing(ModeloCliente::getNome));
        for (ModeloCliente c : clientes) {
            System.out.println(c);
        }
    }

    private static void cadastrarContrato() {
        // Listar clientes por nome
        List<ModeloCliente> clientes = clienteDAO.listar();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado. Cadastre antes de criar contrato.");
            return;
        }

        System.out.println("Clientes cadastrados:");
        clientes.sort(Comparator.comparing(ModeloCliente::getNome));
        for (ModeloCliente c : clientes) {
            System.out.println("- " + c.getNome());
        }

        System.out.print("Digite o nome do cliente: ");
        String nomeCliente = sc.nextLine();
        ModeloCliente clienteSelecionado = clientes.stream()
                .filter(c -> c.getNome().equalsIgnoreCase(nomeCliente))
                .findFirst()
                .orElse(null);

        if (clienteSelecionado == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        // Listar imóveis disponíveis
        List<ModeloImovel> imoveis = imovelDAO.listarDisponiveis();
        if (imoveis.isEmpty()) {
            System.out.println("Nenhum imóvel disponível.");
            return;
        }

        System.out.println("Imóveis disponíveis:");
        for (ModeloImovel i : imoveis) {
            System.out.println(i);
        }

        System.out.print("Digite o ID do imóvel: ");
        int imovelId = sc.nextInt();
        sc.nextLine();

        System.out.print("Valor do Aluguel: ");
        double valor = sc.nextDouble();
        sc.nextLine();

        System.out.print("Data Início (YYYY-MM-DD): ");
        LocalDate inicio = LocalDate.parse(sc.nextLine());
        System.out.print("Data Fim (YYYY-MM-DD): ");
        LocalDate fim = LocalDate.parse(sc.nextLine());

        ModeloContrato contrato = new ModeloContrato(clienteSelecionado.getId(), imovelId, valor, inicio, fim);
        contratoDAO.inserir(contrato);
        System.out.println("✅ Contrato cadastrado!");
    }

    private static void listarContratosAtivos() {
        List<ModeloContrato> contratos = contratoDAO.listarAtivos();
        if (contratos.isEmpty()) {
            System.out.println("Nenhum contrato ativo.");
            return;
        }

        for (ModeloContrato c : contratos) {
            ModeloCliente cliente = clienteDAO.buscarPorId(c.getClienteId());
            ModeloImovel imovel = imovelDAO.buscarPorId(c.getImovelId());

            System.out.println(
                    "Contrato ID: " + c.getId() +
                            " | Cliente: " + (cliente != null ? cliente.getNome() : "Desconhecido") +
                            " | Imóvel: " + (imovel != null ? imovel.getEndereco() : "Desconhecido") +
                            " | Valor: R$" + c.getValor() +
                            " | Início: " + c.getInicio() +
                            " | Fim: " + c.getFim()
            );
        }
    }

    private static void contratosExpirando() {
        List<ModeloContrato> contratos = contratoDAO.contratosExpirando();
        if (contratos.isEmpty()) {
            System.out.println("Nenhum contrato expirando nos próximos 30 dias.");
            return;
        }
        for (ModeloContrato c : contratos) {
            System.out.println(c);
        }
    }

    private static void clientesComMaisContratos() {
        List<ModeloCliente> clientes = clienteDAO.listar();
        Map<Integer, Integer> contagem = new HashMap<>();

        for (ModeloCliente cliente : clientes) {
            int qtd = (int) contratoDAO.listarAtivos().stream()
                    .filter(c -> c.getClienteId() == cliente.getId())
                    .count();
            contagem.put(cliente.getId(), qtd);
        }

        int max = contagem.values().stream().max(Integer::compare).orElse(0);
        if (max == 0) {
            System.out.println("Nenhum contrato cadastrado.");
            return;
        }

        System.out.println("Clientes com mais contratos:");
        for (Map.Entry<Integer, Integer> entry : contagem.entrySet()) {
            if (entry.getValue() == max) {
                ModeloCliente c = clienteDAO.buscarPorId(entry.getKey());
                System.out.println(c.getNome() + " - " + entry.getValue() + " contratos");
            }
        }
    }
}
