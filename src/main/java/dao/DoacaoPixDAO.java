package dao;

import util.DatabaseConnection;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoacaoPixDAO {

    public java.util.List<Object[]> listarDoacoes() {
        java.util.List<Object[]> lista = new java.util.ArrayList<>();

        String sql = """
            SELECT
                d.id_doacao,
                d.nome_doador,
                d.email_doador,
                td.nome AS tipo_doacao,
                sd.nome AS status_doacao,
                dd.valor,
                d.data_doacao
            FROM doacao d
            INNER JOIN tipo_doacao td ON d.id_tipo_doacao = td.id_tipo_doacao
            INNER JOIN status_doacao sd ON d.id_status_doacao = sd.id_status_doacao
            LEFT JOIN doacao_dinheiro dd ON d.id_doacao = dd.id_doacao
            ORDER BY d.data_doacao DESC
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getLong("id_doacao"),
                    rs.getString("nome_doador"),
                    rs.getString("email_doador"),
                    rs.getString("tipo_doacao"),
                    rs.getString("status_doacao"),
                    rs.getBigDecimal("valor"),
                    rs.getTimestamp("data_doacao")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    
public List<Object[]> buscarPorId(long idDoacao) {
    List<Object[]> lista = new ArrayList<>();

    String sql = """
        SELECT 
            d.id_doacao,
            d.nome_doador,
            d.email_doador,
            td.nome AS tipo_doacao,
            sd.nome AS status_doacao,
            dd.valor,
            d.data_doacao
        FROM doacao d
        INNER JOIN tipo_doacao td ON d.id_tipo_doacao = td.id_tipo_doacao
        INNER JOIN status_doacao sd ON d.id_status_doacao = sd.id_status_doacao
        LEFT JOIN doacao_dinheiro dd ON d.id_doacao = dd.id_doacao
        WHERE d.id_doacao = ?
    """;

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setLong(1, idDoacao);

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getLong("id_doacao"),
                    rs.getString("nome_doador"),
                    rs.getString("email_doador"),
                    rs.getString("tipo_doacao"),
                    rs.getString("status_doacao"),
                    rs.getBigDecimal("valor"),
                    rs.getTimestamp("data_doacao")
                });
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return lista;
}
    
    public boolean excluir(long idDoacao) {
        String sql1 = "DELETE FROM doacao_dinheiro WHERE id_doacao = ?";
        String sql2 = "DELETE FROM doacao WHERE id_doacao = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt1 = conn.prepareStatement(sql1);
                 PreparedStatement stmt2 = conn.prepareStatement(sql2)) {

                stmt1.setLong(1, idDoacao);
                stmt1.executeUpdate();

                stmt2.setLong(1, idDoacao);
                int rows = stmt2.executeUpdate();

                conn.commit();
                return rows > 0;
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public long inserirDoacaoPendente(int idDoador, String nomeDoador, String emailDoador,
                                      int idTipoDoacao, Integer idCampanha, String observacao) {
        String sql = "INSERT INTO doacao (id_doador, nome_doador, email_doador, id_tipo_doacao, id_status_doacao, id_campanha, observacao) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, idDoador);
            stmt.setString(2, nomeDoador);
            stmt.setString(3, emailDoador);
            stmt.setInt(4, idTipoDoacao);
            stmt.setInt(5, 1);

            if (idCampanha != null) stmt.setInt(6, idCampanha);
            else stmt.setNull(6, Types.INTEGER);

            stmt.setString(7, observacao);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public void inserirDoacaoDinheiro(long idDoacao, BigDecimal valor, String paymentId, String copiaCola, String qrCode) {
        String sql = "INSERT INTO doacao_dinheiro (id_doacao, valor, status_pagamento, mercado_pago_preference_id, pix_copia_cola, pix_qr_code) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, idDoacao);
            stmt.setBigDecimal(2, valor);
            stmt.setString(3, "PENDENTE");
            stmt.setString(4, paymentId);
            stmt.setString(5, copiaCola);
            stmt.setString(6, qrCode);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean confirmarPagamento(long idDoacao) {
        String sql1 = "UPDATE doacao_dinheiro SET status_pagamento=? WHERE id_doacao=?";
        String sql2 = "UPDATE doacao SET id_status_doacao=? WHERE id_doacao=?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt1 = conn.prepareStatement(sql1);
                 PreparedStatement stmt2 = conn.prepareStatement(sql2)) {

                stmt1.setString(1, "CONCLUIDO");
                stmt1.setLong(2, idDoacao);
                stmt1.executeUpdate();

                stmt2.setInt(1, 2);
                stmt2.setLong(2, idDoacao);
                stmt2.executeUpdate();

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}