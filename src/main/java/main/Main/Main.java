package main.Main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;
import view.login.LoginView;
import util.PixGenerator;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            LoginView frame = new LoginView();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (screenSize.width - frame.getWidth()) / 2;
            int y = (screenSize.height - frame.getHeight()) / 2;
            frame.setLocation(x, y);
            frame.setVisible(true);
        });
    }
}

