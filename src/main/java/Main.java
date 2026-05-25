import utils.PixGenerator;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {
        try {
            BigDecimal valor = new BigDecimal("250.50");

            String email = "teste@email.com";

            String pix = PixGenerator.gerarPix(valor, email);
            System.out.println(pix);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}