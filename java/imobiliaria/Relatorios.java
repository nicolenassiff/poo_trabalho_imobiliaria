package imobiliaria;


import imobiliaria.Database;
import java.sql.*;

public class Relatorios {
    public void clientesComMaisContratos() {
        String sql = "SELECT cl.nome, COUNT(c.id) as total FROM contratos c JOIN clientes cl ON cl.id=c.cliente_id GROUP BY cl.nome ORDER BY total DESC";
        try (Connection conn = Database.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("%s | %d contratos%n", rs.getString("nome"), rs.getInt("total"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void contratosExpirando() {
        String sql = "SELECT c.id, i.endereco, cl.nome, c.fim FROM contratos c JOIN imoveis i ON i.id=c.imovel_id JOIN clientes cl ON cl.id=c.cliente_id WHERE c.fim <= CURRENT_DATE + INTERVAL '30 days'";
        try (Connection conn = Database.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("Contrato %d | %s | %s | Fim: %s%n", rs.getInt("id"), rs.getString("endereco"), rs.getString("nome"), rs.getString("fim"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
