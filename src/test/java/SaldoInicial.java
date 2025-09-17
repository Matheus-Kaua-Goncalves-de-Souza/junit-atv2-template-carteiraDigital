import org.junit.jupiter.api.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class SaldoInicial {

    @ParameterizedTest
    @ValueSource(doubles = {10, 0, 20})
    void deveConfigurarSaldoInicialCorreto(double balance) {
        DigitalWallet digitalWallet = new DigitalWallet("Matheus", balance);
        assertEquals(balance, digitalWallet.getBalance());
    }

    @ParameterizedTest
    @ValueSource(doubles = {-10, -5, -20})
    void deveLancarExcecaoParaSaldoInicialNegativo(double balance) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new DigitalWallet("Matheus", balance));
        assertEquals("Negative initial balance", ex.getMessage());
    }
}
