package org.example.relatorios;

class Partida {
    private String data;
    private String heroiEscolhido;
    private String resultado;
    private String monstroEnfrentado;
    private int quantidadeDeRodadas;

    public Partida(String data, String heroiEscolhido, String resultado, String monstroEnfrentado, int quantidadeDeRodadas) {
        this.data = data;
        this.heroiEscolhido = heroiEscolhido;
        this.resultado = resultado;
        this.monstroEnfrentado = monstroEnfrentado;
        this.quantidadeDeRodadas = quantidadeDeRodadas;
    }

    public String getData() {
        return data;
    }

    public String getHeroiEscolhido() {
        return heroiEscolhido;
    }

    public String getResultado() {
        return resultado;
    }

    public String getMonstroEnfrentado() {
        return monstroEnfrentado;
    }

    public int getQuantidadeDeRodadas() {
        return quantidadeDeRodadas;
    }


}
