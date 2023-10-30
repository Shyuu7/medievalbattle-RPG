package org.example;
import org.example.herois.Guerreiro;
import org.example.herois.Paladino;
import org.example.relatorios.ProgramaRelatorios;
import org.example.util.FabricaDeMonstros;
import org.example.util.Personagem;
import org.example.herois.Barbaro;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import java.util.Date;


public class App 
{
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Personagem jogador = null;
        Date dataAtual = new Date();
        String heroiEscolhido = "";


        try {
            System.out.println("Digite o nome de seu personagem: ");
            String nome = input.nextLine();
            System.out.println("Digite a classe de herói que gostaria de utilizar: Barbaro, Guerreiro ou Paladino");
            String classeEscolhida = input.nextLine().toLowerCase();
            heroiEscolhido = classeEscolhida;

            switch (classeEscolhida) {
                case "paladino":
                    jogador = new Paladino(nome);
                    break;

                case "barbaro":
                    jogador = new Barbaro(nome);
                    break;

                case "guerreiro":
                    jogador = new Guerreiro(nome);
                    break;

                default:
                    throw new InputMismatchException();
            }

            System.out.println("Criado o " + classeEscolhida + " " + jogador.getNome());
            System.out.println("Seus atributos: " + jogador.getPontosDeVida() + " pontos de vida / "
                    + jogador.getForca() + " força / " + jogador.getDefesa() + " defesa / " + jogador.getAgilidade()
                    + " agilidade / " + jogador.getFatorDeDano() + " Fator de Dano.");

        } catch (InputMismatchException e) {
            System.out.println("Classe inválida. Favor escolher uma das classes apresentadas.");
            return;
        } finally {
            input.close();
        }

        Personagem monstroInimigo = FabricaDeMonstros.gerarMonstroAleatorio();
        System.out.println("Você enfrentará um monstro do tipo: " + monstroInimigo.getNome());
        System.out.println("==========================================");

        boolean jogadorVivo = true;
        boolean monstroVivo = true;
        int vidaJogador = jogador.getPontosDeVida();
        int vidaMonstro = monstroInimigo.getPontosDeVida();
        int rodadas = 1;

        String pastaTemp = "temp";
        File tempDir = new File(pastaTemp);
        if (!tempDir.exists()) {
            if (tempDir.mkdirs()) {
                System.out.println("Diretório para salvar relatórios das partidas criados com sucesso.");
            } else {
                System.out.println("Erro ao criar a pasta 'temp'.");
                return;
            }
        }

        String arquivoCSV = "temp" + File.separator + jogador.getNome() + ".csv";


        do {
            int iniciativaJogador = jogador.calcularIniciativa();
            int iniciativaMonstro = monstroInimigo.calcularIniciativa();
            System.out.println(
                    "\nCalculando iniciativas: Jogador - " + iniciativaJogador + " / Inimigo - " + iniciativaMonstro);
            System.out.println("==========================================");

            while (iniciativaJogador == iniciativaMonstro) {
                iniciativaJogador = jogador.calcularIniciativa();
                iniciativaMonstro = monstroInimigo.calcularIniciativa();
                System.out.println(
                        "\nRecalculando iniciativas: Jogador - " + iniciativaJogador + " / Inimigo "
                                + iniciativaMonstro);
                System.out.println("==========================================");
            }

            if (iniciativaJogador > iniciativaMonstro) {
                System.out.println("O jogador iniciará o ataque!");
                System.out.println();
                int ataqueJogador = jogador.calcularAtaque();
                int defesaMonstro = monstroInimigo.calcularDefesa();
                System.out.println("Fator de ataque do jogador: " + ataqueJogador);
                System.out.println("Fator de defesa do monstro: " + defesaMonstro);
                System.out.println("==========================================");

                if (ataqueJogador > defesaMonstro) {
                    System.out.println("O jogador ganhou o embate. Calculando dano.");
                    int danoJogador = jogador.calcularDano();
                    vidaMonstro = vidaMonstro - danoJogador;
                    System.out.println("\nO jogador infligiu " + danoJogador + " de dano. Monstro possui " + vidaMonstro
                            + " pontos de vida restantes.");

                    if (vidaMonstro <= 0) {
                        monstroVivo = false;
                        System.out.println("\nO monstro foi derrotado! Vitória do herói!");
                        ProgramaRelatorios.salvarDadosDaPartida(arquivoCSV, dataAtual, heroiEscolhido, "GANHOU", monstroInimigo.getNome(), rodadas);
                    }
                } else {
                    System.out.println("O monstro conseguiu se defender! O jogador não infligiu nenhum dano.");
                }

            } else {
                System.out.println("O monstro iniciará o ataque!");
                System.out.println();
                int ataqueMonstro = monstroInimigo.calcularAtaque();
                int defesaJogador = jogador.calcularDefesa();
                System.out.println("Fator de ataque do monstro: " + ataqueMonstro);
                System.out.println("Fator de defesa do jogador: " + defesaJogador);
                System.out.println("==========================================");

                if (ataqueMonstro > defesaJogador) {
                    System.out.println("O monstro ganhou o embate. Calculando dano.");
                    int danoMonstro = monstroInimigo.calcularDano();
                    vidaJogador = vidaJogador - danoMonstro;
                    System.out.println("\nO monstro infligiu " + danoMonstro + " de dano. Jogador possui " + vidaJogador
                            + " pontos de vida restantes.");

                    if (vidaJogador <= 0) {
                        jogadorVivo = false;
                        System.out.println("\nO jogador foi derrotado! Vitória do monstro!");
                        ProgramaRelatorios.salvarDadosDaPartida(arquivoCSV, dataAtual, heroiEscolhido, "PERDEU", monstroInimigo.getNome(), rodadas);
                    }
                } else {
                    System.out.println("O jogador conseguiu se defender! O monstro não infligiu nenhum dano.");
                }
            }
            rodadas++;
        } while (jogadorVivo && monstroVivo);

        System.out.println("O jogo durou " + rodadas + " rodadas.");
    }
}
