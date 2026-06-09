package dao;

import util.DatabaseConnection;
import java.math.BigDecimal;
import java.sql.*;

public class DoacaoPixDAO {

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