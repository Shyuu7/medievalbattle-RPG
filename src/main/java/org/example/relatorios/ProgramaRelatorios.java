package org.example.relatorios;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ProgramaRelatorios {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Digite o nickname do jogador: ");
        String nickname = input.nextLine();
        input.close();

        String pastaTemp = "temp";
        File tempDir = new File(pastaTemp);
        if (!tempDir.exists()) {
            if (tempDir.mkdirs()) {
                System.out.println("Pasta 'temp' criada com sucesso.");
            } else {
                System.out.println("Erro ao criar a pasta 'temp'.");
                return;
            }
        }

        String arquivoCSV = pastaTemp + File.separator + nickname + ".csv";
        List<Partida> batalhas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(arquivoCSV))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 5) {
                    String data = dados[0];
                    String heroiEscolhido = dados[1];
                    String resultado = dados[2];
                    String monstroEnfrentado = dados[3];
                    int quantidadeDeRodadas = Integer.parseInt(dados[4]);
                    Partida batalha = new Partida(data, heroiEscolhido, resultado, monstroEnfrentado,
                            quantidadeDeRodadas);
                    batalhas.add(batalha);
                }
            }
        } catch (IOException e) {
            System.out.println("Nenhuma batalha encontrada para o jogador " + nickname);
            return;
        }

        System.out.println("\nRelatórios para o jogador " + nickname + ":\n");
        int totalPontos = 0;
        Map<String, Integer> heroisMaisJogados = new HashMap<>();
        Map<String, Integer> monstrosMaisEnfrentados = new HashMap<>();

        for (Partida batalha : batalhas) {
            totalPontos += 100 - batalha.getQuantidadeDeRodadas();

            heroisMaisJogados.put(batalha.getHeroiEscolhido(),
                    heroisMaisJogados.getOrDefault(batalha.getHeroiEscolhido(), 0) + 1);

            monstrosMaisEnfrentados.put(batalha.getMonstroEnfrentado(),
                    monstrosMaisEnfrentados.getOrDefault(batalha.getMonstroEnfrentado(), 0) + 1);

            System.out.println("Data da partida: " + batalha.getData());
            System.out.println("Herói escolhido: " + batalha.getHeroiEscolhido());
            System.out.println("Resultado: " + batalha.getResultado());
            System.out.println("Monstro enfrentado: " + batalha.getMonstroEnfrentado());
            System.out.println("Quantidade de rodadas: " + batalha.getQuantidadeDeRodadas());
            System.out.println();
        }

        System.out.println("Heroi mais jogado: " + getChaveComValorMaximo(heroisMaisJogados));
        System.out.println("Monstro mais enfrentado: " + getChaveComValorMaximo(monstrosMaisEnfrentados));
        System.out.println("Quantidade total de pontos: " + totalPontos);

    }

    private static String getChaveComValorMaximo(Map<String, Integer> map) {
        String chaveComValorMaximo = null;
        int valorMaximo = 0;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() > valorMaximo) {
                valorMaximo = entry.getValue();
                chaveComValorMaximo = entry.getKey();
            }
        }
        return chaveComValorMaximo;
    }

    public static void salvarDadosDaPartida(String arquivoCSV, Date dataPartida, String heroiEscolhido, String resultado, String monstroEnfrentado, int quantidadeDeRodadas) {
        try (FileWriter writer = new FileWriter(arquivoCSV, true)) {
            String dataFormatada = new SimpleDateFormat("dd/MM/yyyy").format(dataPartida);
            writer.append(dataFormatada + ";");
            writer.append(heroiEscolhido + ";");
            writer.append(resultado + ";");
            writer.append(monstroEnfrentado + ";");
            writer.append(quantidadeDeRodadas + "\n");
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados da partida.");
        }
    }
}
