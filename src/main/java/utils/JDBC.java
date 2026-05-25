package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/project_sunny"; // Change to your DB name
        String user = "root";
        String password = "Anima123";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            if (conn != null) {
                System.out.println("Conectado com sucesso ao Banco de Dados!");
            }
        } catch (SQLException e) {
            System.err.println("Conexão falhou!");
            e.printStackTrace();
        }
    }
}