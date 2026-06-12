package dao;

import util.DatabaseConnection;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CampanhaDAO {

    public List<Object[]> listarTodas() {
        List<Object[]> lista = new ArrayList<>();
        String sql = """
            SELECT 
                id_campanha,
                titulo,
                descricao,
                mes_referencia,
                meta_dinheiro,
                meta_alimentos,
                meta_roupas,
                meta_brinquedos,
                meta_livros,
                meta_calcados,
                meta_eletrodomesticos,
                meta_saude,
                ativa,
                id_admin,
                criado_em
            FROM campanha
            ORDER BY id_campanha DESC
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Object[]{
                    rs.getInt("id_campanha"),
                    rs.getString("titulo"),
                    rs.getString("descricao"),
                    rs.getDate("mes_referencia"),
                    rs.getBigDecimal("meta_dinheiro"),
                    rs.getInt("meta_alimentos"),
                    rs.getInt("meta_roupas"),
                    rs.getInt("meta_brinquedos"),
                    rs.getInt("meta_livros"),
                    rs.getInt("meta_calcados"),
                    rs.getInt("meta_eletrodomesticos"),
                    rs.getInt("meta_saude"),
                    rs.getBoolean("ativa"),
                    rs.getInt("id_admin"),
                    rs.getTimestamp("criado_em")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    
    public List<Object[]> listarMetasResumidas() {
        List<Object[]> lista = new ArrayList<>();

        String sql = """
            SELECT
                id_campanha,
                titulo,
                descricao,
                CASE
                    WHEN meta_dinheiro > 0 THEN 'DINHEIRO'
                    WHEN meta_alimentos > 0 THEN 'ALIMENTO'
                    WHEN meta_roupas > 0 THEN 'ROUPA'
                    WHEN meta_brinquedos > 0 THEN 'BRINQUEDO'
                    WHEN meta_livros > 0 THEN 'LIVRO'
                    WHEN meta_calcados > 0 THEN 'CALCADO'
                    WHEN meta_eletrodomesticos > 0 THEN 'ELETRODOMESTICO'
                    WHEN meta_saude > 0 THEN 'SAUDE'
                    ELSE 'N/A'
                END AS tipo_meta,
                CASE
                    WHEN meta_dinheiro > 0 THEN meta_dinheiro
                    WHEN meta_alimentos > 0 THEN meta_alimentos
                    WHEN meta_roupas > 0 THEN meta_roupas
                    WHEN meta_brinquedos > 0 THEN meta_brinquedos
                    WHEN meta_livros > 0 THEN meta_livros
                    WHEN meta_calcados > 0 THEN meta_calcados
                    WHEN meta_eletrodomesticos > 0 THEN meta_eletrodomesticos
                    WHEN meta_saude > 0 THEN meta_saude
                    ELSE 0
                END AS valor_meta,
                ativa,
                id_admin,
                criado_em
            FROM campanha
            ORDER BY id_campanha DESC
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Object[] {
                    rs.getInt("id_campanha"),
                    rs.getString("titulo"),
                    rs.getString("descricao"),
                    rs.getString("tipo_meta"),
                    rs.getBigDecimal("valor_meta"),
                    rs.getBoolean("ativa"),
                    rs.getInt("id_admin"),
                    rs.getTimestamp("criado_em")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }    
    
    public ResultSet buscarPorId(int idCampanha) throws SQLException {
        String sql = """
            SELECT 
                id_campanha,
                titulo,
                descricao,
                mes_referencia,
                meta_dinheiro,
                meta_alimentos,
                meta_roupas,
                meta_brinquedos,
                meta_livros,
                meta_calcados,
                meta_eletrodomesticos,
                meta_saude,
                ativa,
                id_admin,
                criado_em
            FROM campanha
            WHERE id_campanha = ?
        """;

        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idCampanha);
        return stmt.executeQuery();
    }

    public boolean inserir(String titulo, String descricao, java.sql.Date mesReferencia,
                           BigDecimal metaDinheiro, int metaAlimentos, int metaRoupas,
                           int metaBrinquedos, int metaLivros, int metaCalcados,
                           int metaEletrodomesticos, int metaSaude, boolean ativa, int idAdmin) {

        String sql = """
            INSERT INTO campanha
            (titulo, descricao, mes_referencia, meta_dinheiro, meta_alimentos, meta_roupas,
             meta_brinquedos, meta_livros, meta_calcados, meta_eletrodomesticos, meta_saude, ativa, id_admin)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, titulo);
            stmt.setString(2, descricao);
            stmt.setDate(3, mesReferencia);
            stmt.setBigDecimal(4, metaDinheiro);
            stmt.setInt(5, metaAlimentos);
            stmt.setInt(6, metaRoupas);
            stmt.setInt(7, metaBrinquedos);
            stmt.setInt(8, metaLivros);
            stmt.setInt(9, metaCalcados);
            stmt.setInt(10, metaEletrodomesticos);
            stmt.setInt(11, metaSaude);
            stmt.setBoolean(12, ativa);
            stmt.setInt(13, idAdmin);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
public boolean atualizar(int idCampanha, String titulo, String descricao, java.sql.Date mesReferencia,
                         BigDecimal metaDinheiro, int metaAlimentos, int metaRoupas,
                         int metaBrinquedos, int metaLivros, int metaCalcados,
                         int metaEletrodomesticos, int metaSaude, boolean ativa) {

    String sql = """
        UPDATE campanha SET
            titulo = ?,
            descricao = ?,
            mes_referencia = ?,
            meta_dinheiro = ?,
            meta_alimentos = ?,
            meta_roupas = ?,
            meta_brinquedos = ?,
            meta_livros = ?,
            meta_calcados = ?,
            meta_eletrodomesticos = ?,
            meta_saude = ?,
            ativa = ?
        WHERE id_campanha = ?
    """;

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, titulo);
        stmt.setString(2, descricao);
        stmt.setDate(3, mesReferencia);
        stmt.setBigDecimal(4, metaDinheiro);
        stmt.setInt(5, metaAlimentos);
        stmt.setInt(6, metaRoupas);
        stmt.setInt(7, metaBrinquedos);
        stmt.setInt(8, metaLivros);
        stmt.setInt(9, metaCalcados);
        stmt.setInt(10, metaEletrodomesticos);
        stmt.setInt(11, metaSaude);
        stmt.setBoolean(12, ativa);
        stmt.setInt(13, idCampanha);

        return stmt.executeUpdate() > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    public boolean excluir(int idCampanha) {
        String sql = "DELETE FROM campanha WHERE id_campanha = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCampanha);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}