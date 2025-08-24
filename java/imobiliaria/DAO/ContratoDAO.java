package imobiliaria.DAO;


import imobiliaria.model.ModeloContrato;
import imobiliaria.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContratoDAO {

    // Inserir contrato
    public void inserir(ModeloContrato contrato) {
        String sql = "INSERT INTO contratos (cliente_id, imovel_id, valor, inicio, fim) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, contrato.getClienteId());
            ps.setInt(2, contrato.getImovelId());
            ps.setDouble(3, contrato.getValor());
            ps.setDate(4, java.sql.Date.valueOf(contrato.getInicio()));
            ps.setDate(5, java.sql.Date.valueOf(contrato.getFim()));

            ps.executeUpdate();

            // Atualiza o status do imóvel para "alugado"
            String updateImovel = "UPDATE imoveis SET status = 'alugado' WHERE id = ?";
            try (PreparedStatement ps2 = conn.prepareStatement(updateImovel)) {
                ps2.setInt(1, contrato.getImovelId());
                ps2.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Listar contratos ativos
    public List<ModeloContrato> listarAtivos() {
        List<ModeloContrato> lista = new ArrayList<>();
        String sql = "SELECT * FROM contratos WHERE fim >= CURRENT_DATE";
        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                ModeloContrato contrato = new ModeloContrato(
                        rs.getInt("id"),
                        rs.getInt("cliente_id"),
                        rs.getInt("imovel_id"),
                        rs.getDouble("valor"),
                        rs.getDate("inicio").toLocalDate(),
                        rs.getDate("fim").toLocalDate()
                );
                lista.add(contrato);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Contratos expirando nos próximos 30 dias
    public List<ModeloContrato> contratosExpirando() {
        List<ModeloContrato> lista = new ArrayList<>();
        String sql = "SELECT * FROM contratos WHERE fim BETWEEN CURRENT_DATE AND CURRENT_DATE + INTERVAL '30 days'";
        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                ModeloContrato contrato = new ModeloContrato(
                        rs.getInt("id"),
                        rs.getInt("cliente_id"),
                        rs.getInt("imovel_id"),
                        rs.getDouble("valor"),
                        rs.getDate("inicio").toLocalDate(),
                        rs.getDate("fim").toLocalDate()
                );
                lista.add(contrato);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Buscar contrato por ID
    public ModeloContrato buscarPorId(int id) {
        String sql = "SELECT * FROM contratos WHERE id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new ModeloContrato(
                        rs.getInt("id"),
                        rs.getInt("cliente_id"),
                        rs.getInt("imovel_id"),
                        rs.getDouble("valor"),
                        rs.getDate("inicio").toLocalDate(),
                        rs.getDate("fim").toLocalDate()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
