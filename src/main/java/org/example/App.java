package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.example.herois.*;
import org.example.relatorios.ProgramaRelatorios;
import org.example.util.FabricaDeMonstros;
import org.example.util.Personagem;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import java.util.Date;


public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Personagem jogador;
        Date dataAtual = new Date();
        String heroiEscolhido = "";


        try {
            System.out.println("Digite o nome de seu personagem: ");
            String nome = input.nextLine();
            System.out.println("Digite a classe de herói que gostaria de utilizar: Barbaro, Guerreiro, Paladino, Arqueiro ou Assassino");
            String classeEscolhida = input.nextLine().toLowerCase();


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

                case "assassino":
                    jogador = new Assassino(nome);
                    break;

                case "arqueiro":
                    jogador = new Arqueiro(nome);
                    break;

                default:
                    throw new InputMismatchException();
            }

            LOGGER.info("Criado o " + classeEscolhida + " " + jogador.getNome());
            LOGGER.info("Atributos do player: " + jogador.getPontosDeVida() + " pontos de vida / "
                    + jogador.getForca() + " força / " + jogador.getDefesa() + " defesa / " + jogador.getAgilidade()
                    + " agilidade / " + jogador.getFatorDeDano() + " Fator de Dano.");

        } catch (InputMismatchException e) {
            LOGGER.error("Classe inválida. Player não escolheu uma das classes apresentadas.");
            return;
        } finally {
            input.close();
        }

        Personagem monstroInimigo = FabricaDeMonstros.gerarMonstroAleatorio();
        LOGGER.info("Monstro inimigo do tipo : " + monstroInimigo.getNome());

        boolean jogadorVivo = true;
        boolean monstroVivo = true;
        int vidaJogador = jogador.getPontosDeVida();
        int vidaMonstro = monstroInimigo.getPontosDeVida();
        int rodadas = 1;

        String pastaTemp = "temp";
        File tempDir = new File(pastaTemp);
        if (!tempDir.exists()) {
            if (tempDir.mkdirs()) {
                LOGGER.debug("Diretório para salvar relatórios das partidas criados com sucesso.");
            } else {
                LOGGER.error("Erro ao criar a pasta 'temp'.");
                return;
            }
        }

        String arquivoCSV = "temp" + File.separator + jogador.getNome() + ".csv";


        do {
            int iniciativaJogador = jogador.calcularIniciativa();
            int iniciativaMonstro = monstroInimigo.calcularIniciativa();
            LOGGER.info("\nCalculando iniciativas: Jogador - " + iniciativaJogador + " / Inimigo - " + iniciativaMonstro);

            while (iniciativaJogador == iniciativaMonstro) {
                iniciativaJogador = jogador.calcularIniciativa();
                iniciativaMonstro = monstroInimigo.calcularIniciativa();
                LOGGER.info("\nRecalculando iniciativas: Jogador - " + iniciativaJogador + " / Inimigo "
                        + iniciativaMonstro);
            }

            if (iniciativaJogador > iniciativaMonstro) {
                LOGGER.info("O jogador iniciará o ataque!");
                int ataqueJogador = jogador.calcularAtaque();
                int defesaMonstro = monstroInimigo.calcularDefesa();
                LOGGER.info("Fator de ataque do jogador: " + ataqueJogador);
                LOGGER.info("Fator de defesa do monstro: " + defesaMonstro);

                if (ataqueJogador > defesaMonstro) {
                    LOGGER.info("O jogador ganhou o embate. Calculando dano.");
                    int danoJogador = jogador.calcularDano();
                    vidaMonstro = vidaMonstro - danoJogador;
                    LOGGER.info("\nO jogador infligiu " + danoJogador + " de dano. Monstro possui " + vidaMonstro
                            + " pontos de vida restantes.");

                    if (vidaMonstro <= 0) {
                        monstroVivo = false;
                        LOGGER.info("\nO monstro foi derrotado! Vitória do herói!");
                        ProgramaRelatorios.salvarDadosDaPartida(arquivoCSV, dataAtual, heroiEscolhido, "GANHOU", monstroInimigo.getNome(), rodadas);
                    }
                } else {
                    LOGGER.debug("O monstro conseguiu se defender! O jogador não infligiu nenhum dano.");
                }

            } else {
                LOGGER.info("O monstro iniciará o ataque!");
                int ataqueMonstro = monstroInimigo.calcularAtaque();
                int defesaJogador = jogador.calcularDefesa();
                LOGGER.info("Fator de ataque do monstro: " + ataqueMonstro);
                LOGGER.info("Fator de defesa do jogador: " + defesaJogador);

                if (ataqueMonstro > defesaJogador) {
                    LOGGER.info("O monstro ganhou o embate. Calculando dano.");
                    int danoMonstro = monstroInimigo.calcularDano();
                    vidaJogador = vidaJogador - danoMonstro;
                    LOGGER.debug("\nO monstro infligiu " + danoMonstro + " de dano. Jogador possui " + vidaJogador
                            + " pontos de vida restantes.");

                    if (vidaJogador <= 0) {
                        jogadorVivo = false;
                        LOGGER.info("\nO jogador foi derrotado! Vitória do monstro!");
                        ProgramaRelatorios.salvarDadosDaPartida(arquivoCSV, dataAtual, heroiEscolhido, "PERDEU", monstroInimigo.getNome(), rodadas);
                    }
                } else {
                    LOGGER.debug("O jogador conseguiu se defender! O monstro não infligiu nenhum dano.");
                }
            }
            rodadas++;
        } while (jogadorVivo && monstroVivo);

        LOGGER.info("O jogo durou " + rodadas + " rodadas.");
    }
}
