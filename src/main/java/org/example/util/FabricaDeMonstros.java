package org.example.util;

import org.example.monstros.*;

import java.util.Random;

public class FabricaDeMonstros {
    private static final String[] MONSTROS = {
            "Kobold",
            "Orc",
            "MortoVivo",
            "Banshee",
            "Slime"
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
            case "Banshee":
                return new Banshee("Banshee");
            case "Slime":
                return new Slime("Slime");

            default:
                throw new IllegalArgumentException("Não foi possível criar este monstro.");
        }
    }
}

