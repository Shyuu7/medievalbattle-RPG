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

        if ("Kobold".equals(tipoMonstroAleatorio)) {
            return new Kobold("Kobold");
        } else if ("Orc".equals(tipoMonstroAleatorio)) {
            return new Orc("Orc");
        } else if ("MortoVivo".equals(tipoMonstroAleatorio)) {
            return new MortoVivo("Morto Vivo");
        } else {
            throw new IllegalArgumentException("Não foi possível criar este monstro.");
        }
    }
};

