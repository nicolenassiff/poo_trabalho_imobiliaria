package imobiliaria.DAO;

import imobiliaria.Database;
import imobiliaria.model.ModeloImovel;
import java.sql.*;
import java.util.*;

public class ImovelDAO {
    public void inserir(ModeloImovel imovel) {
        String sql = "INSERT INTO imoveis(endereco,tipo,aluguel,status) VALUES(?,?,?,?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, imovel.getEndereco());
            ps.setString(2, imovel.getTipo());
            ps.setDouble(3, imovel.getAluguel());
            ps.setString(4, imovel.getStatus());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<ModeloImovel> listarDisponiveis() {
        List<ModeloImovel> lista = new ArrayList<>();
        String sql = "SELECT * FROM imoveis WHERE status='disponivel'";
        try (Connection conn = Database.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new ModeloImovel(
                        rs.getInt("id"),
                        rs.getString("endereco"),
                        rs.getString("tipo"),
                        rs.getDouble("aluguel"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }
    public ModeloImovel buscarPorId(int id) {
        String sql = "SELECT * FROM imoveis WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new ModeloImovel(
                        rs.getInt("id"),
                        rs.getString("endereco"),
                        rs.getString("tipo"),
                        rs.getDouble("aluguel"),
                        rs.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
