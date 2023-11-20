package org.example.util;

import lombok.Getter;
import lombok.Setter;
import org.example.exceptions.fatorDeDanoException;

@Getter@Setter
public abstract class Personagem implements iPersonagem {
    private String nome;
    private int pontosDeVida;
    private int forca;
    private int defesa;
    private int agilidade;
    private Dado DadoDano;
    private int fatorDeDano;

    public Personagem(String nome, int pontosDeVida, int forca, int defesa, int agilidade, int numeroVezes, int facesDadoDano) {
        this.nome = nome;
        this.pontosDeVida = pontosDeVida;
        this.forca = forca;
        this.defesa = defesa;
        this.agilidade = agilidade;
        this.DadoDano = new Dado(facesDadoDano);
        this.fatorDeDano = DadoDano.jogarMultiplosDados(numeroVezes);
    }

    public int calcularIniciativa() {
        Dado dadoIniciativa = new Dado(10);
        return dadoIniciativa.jogarDado() + getAgilidade();
    }

    public int calcularAtaque() {
        Dado dadoAtaque = new Dado(10);
        return dadoAtaque.jogarDado() + getAgilidade() + getForca();
    }

    public int calcularDefesa() {
        Dado dadoDefesa = new Dado(10);
        return dadoDefesa.jogarDado() + getAgilidade() + getDefesa();
    }

    public void setFatorDeDano(int fatorDeDano) {
        if (fatorDeDano > 0) {
            this.fatorDeDano = fatorDeDano;
        } else throw new fatorDeDanoException("Fator de dano inválido! Necessário valor maior que zero");
    }

    public int calcularDano() {
        return fatorDeDano + getForca();
    }
}
