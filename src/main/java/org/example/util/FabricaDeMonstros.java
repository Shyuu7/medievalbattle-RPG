package org.example.util;

import org.example.monstros.Kobold;
import org.example.monstros.MortoVivo;
import org.example.monstros.Orc;

import java.util.Random;

public class FabricaDeMonstros {
    private static final String[] MONSTROS = {
            "Kobold",
            "Orc",
            "MortoVivo"
    };

    public static Personagem gerarMonstroAleatorio() throws IllegalArgumentException {
        Random random = new Random();
        int randomIndex = random.nextInt(MONSTROS.length);
        String tipoMonstroAleatorio = MONSTROS[randomIndex];

        switch (tipoMonstroAleatorio) {
            case "Kobold":
                return new Kobold("Kobold");
            case "Orc":
                return new Orc("Orc");
            case "MortoVivo":
                return new MortoVivo("Morto Vivo");
            default:
                throw new IllegalArgumentException("Não foi possível criar este monstro.");
        }
    }
}

