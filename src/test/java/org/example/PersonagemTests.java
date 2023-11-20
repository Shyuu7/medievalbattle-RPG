package org.example;

import org.example.exceptions.fatorDeDanoException;
import org.example.herois.Barbaro;
import org.example.herois.Paladino;
import org.example.util.Personagem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonagemTests {
    @Test
    @DisplayName("Exception lançada quando o cálculo de dano for < 0")
    public void testeDanoPaladino() {
        Personagem paladino = new Paladino("Trevor");
        assertThrows(fatorDeDanoException.class, () -> {
            paladino.setFatorDeDano(-78);
            paladino.calcularDano();
        });
    }

    @Test
    @DisplayName("Testando aleatoriedade do fator de ataque de um barbaro")
    public void testeAtaqueBarbaro() {
        Personagem barbaro = new Barbaro("Conan");
        int fatorDeAtaque1 = barbaro.calcularAtaque();
        int fatorDeAtaque2 = barbaro.calcularAtaque();
        assertNotEquals(fatorDeAtaque1, fatorDeAtaque2);
    }
}
