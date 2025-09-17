import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.Assumptions;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class Estorno {

    static Stream<org.junit.jupiter.params.provider.Arguments> valoresEstorno() {
        return Stream.of(
            org.junit.jupiter.params.provider.Arguments.of(100.0, 10.0, 110.0),
            org.junit.jupiter.params.provider.Arguments.of(0.0,   5.0,   5.0),
            org.junit.jupiter.params.provider.Arguments.of(50.0,  0.01, 50.01)
        );
    }

    @ParameterizedTest
    @MethodSource("valoresEstorno")
    void refundComCarteiraValida(double inicial, double valor, double saldoEsperado) {
        DigitalWallet digitalWallet = new DigitalWallet("Matheus", inicial);
        digitalWallet.unlock();
        digitalWallet.verify();
        assumeTrue(digitalWallet.isVerified() && !digitalWallet.isLocked());

        digitalWallet.refund(valor);
        assertEquals(saldoEsperado, digitalWallet.getBalance());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-10, 0, -0.1})
    void deveLancarExcecaoParaRefundInvalido(double valor) {
        DigitalWallet digitalWallet = new DigitalWallet("Matheus", 100);
        digitalWallet.verify();
        digitalWallet.unlock();
        assumeTrue(digitalWallet.isVerified() && !digitalWallet.isLocked());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> digitalWallet.refund(valor));
        assertEquals("Amount must be > 0", ex.getMessage());
    }

    @Test
    void deveLancarSeNaoVerificadaOuBloqueada() {
        DigitalWallet digitalWallet = new DigitalWallet("MAtheus", 100);

        assertThrows(IllegalStateException.class,
                () -> digitalWallet.refund(10));
    }
}
