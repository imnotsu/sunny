package utils;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.resources.payment.Payment;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class PixGenerator {

    public static String gerarPix(BigDecimal valor, String email) throws Exception {

        MercadoPagoConfig.setAccessToken("APP_USR-1029819585391993-090115-bfee3d69d772551e75f4f23954e2ef10-1185798625");

        Map<String, String> customHeaders = new HashMap<>();
        customHeaders.put("x-idempotency-key", "IDENTIFICADOR_UNICO_DA_TRANSACAO");

        MPRequestOptions requestOptions = MPRequestOptions.builder()
                .customHeaders(customHeaders)
                .build();

        PaymentCreateRequest paymentCreateRequest = PaymentCreateRequest.builder()
                .transactionAmount(valor)
                .paymentMethodId("pix")
                .payer(PaymentPayerRequest.builder()
                        .email(email)
                        .build())
                .build();

        PaymentClient client = new PaymentClient();
        Payment payment = client.create(paymentCreateRequest, requestOptions);

        return payment.getPointOfInteraction()
                .getTransactionData()
                .getQrCode();
    }
}
