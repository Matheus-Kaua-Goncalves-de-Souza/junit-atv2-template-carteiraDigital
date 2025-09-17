import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class Pagamento {

    @ParameterizedTest
    @CsvSource({ "100.0, 30.0, true", "50.0, 80.0, false", "10.0, 10.0, true" })
    void pagamentoComCarteiraVerificadaENaoBloqueada(double inicial, double valor, boolean esperado) {
        DigitalWallet digitalWallet = new DigitalWallet("Matheus", inicial);
        digitalWallet.verify();
        digitalWallet.unlock();
        assumeTrue(digitalWallet.isVerified() && !digitalWallet.isLocked());

        assertEquals(esperado, digitalWallet.pay(valor));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-10, 0, -0.1})
    void deveLancarExcecaoParaPagamentoInvalido(double valor) {
        DigitalWallet digitalWallet = new DigitalWallet("Matheus", 100);
        digitalWallet.verify();
        digitalWallet.unlock();
        assumeTrue(digitalWallet.isVerified() && !digitalWallet.isLocked());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> digitalWallet.pay(valor));
        assertEquals("Amount must be > 0", ex.getMessage());
    }

    @Test
    void deveLancarSeNaoVerificadaOuBloqueada() {
        DigitalWallet digitalWallet = new DigitalWallet("Matheus", 100);

        assertThrows(IllegalStateException.class,
                () -> digitalWallet.pay(10));
    }
}
