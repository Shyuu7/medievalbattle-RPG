package org.example.util;

import java.util.Random;

public class Dado {
    private int numeroLados;
    private Random random;

    public Dado(int numeroLados) {
        this.numeroLados = numeroLados;
        this.random = new Random();
    }

    public int jogarDado() {
        return random.nextInt(numeroLados) + 1;
    }

    public int jogarMultiplosDados(int numeroVezes) {
        int somaDados = 0;
        for (int i = 0; i < numeroVezes; i++) {
            somaDados += jogarDado();
        }
        return somaDados;
    }
}
