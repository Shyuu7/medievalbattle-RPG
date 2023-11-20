package org.example;

import org.example.util.Dado;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DadoTests {

    @Test
    @DisplayName("Testando resultado de um único d8")
    public void testeD8() {
        Dado d8 = new Dado(8);
        int resultado = d8.jogarDado();
        assertTrue(resultado >= 1 && resultado <= 8);
    }

    @Test
    @DisplayName("Testando resultado de um único d6")
    public void testeD6() {
        Dado d6 = new Dado(6);
        int resultado = d6.jogarDado();
        assertTrue(resultado <= 6);
    }

    @Test
    @DisplayName("Testando limites da soma de múltiplos d10")
    public void testeD10s() {
        Dado d10 = new Dado(10);
        int resultado = d10.jogarMultiplosDados(4);
        assertTrue(resultado >= 4 && resultado <= 40);
    }
}
