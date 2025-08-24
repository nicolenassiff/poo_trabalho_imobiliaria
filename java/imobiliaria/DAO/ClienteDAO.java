package imobiliaria.DAO;


import imobiliaria.model.ModeloCliente;
import imobiliaria.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    // Inserir cliente
    public void inserir(ModeloCliente cliente) {
        String sql = "INSERT INTO clientes (nome, telefone) VALUES (?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getTelefone());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Listar todos clientes
    public List<ModeloCliente> listar() {
        List<ModeloCliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (Connection conn = Database.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new ModeloCliente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("telefone")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Buscar cliente por ID
    public ModeloCliente buscarPorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new ModeloCliente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("telefone")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
