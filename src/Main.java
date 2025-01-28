import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import  java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    static ArrayList<Palavra> palavras = new ArrayList<Palavra>();
    static ArrayList<Palavra> palavrasEmJogo = new ArrayList<Palavra>();

    public static void main(String[] args) {
        String tent;
        int nrPalavras = 5;
        int pontuacao = 0;
        String nomeJogador = "";
        carregarPalavrasDaAPI(nrPalavras);


        do{
            Palavra p = palavras.get(ThreadLocalRandom.current().nextInt(0, palavras.size()));
            if(!p.getEscolhida()){
                palavrasEmJogo.add(p);
                p.setEscolhida(true);
            }
        }while (palavrasEmJogo.size() < nrPalavras);


        for(int i = 0; i < palavrasEmJogo.size(); i++){
            Palavra p = palavrasEmJogo.get(i);
            do {
                tent = JOptionPane.showInputDialog(null, "Tentativas: " + p.getTentativas() + " | Dica: " + p.getDica() + " | " + p.getPalavraProgresso() + "\n Insira uma letra:", p.getFeedback(), JOptionPane.QUESTION_MESSAGE);
                tent = tent.toLowerCase();
                if(tent.length() == 1 && tent.matches(".*[a-z].*")){
                    p.setEmJogo(true);
                    p.verificaLetra(tent.charAt(0));
//                    p.setFeedback(p.getAcertouLetra() ? "Letra correta! Tens " + p.getTentativas() + " tentativas" : "Letra incorreta :( Tens " + p.getTentativas() + " tentativas");
                }
            }while(p.getTentativas() > 0 && !p.acertou());
            pontuacao += p.getPontuacao();
        }

        nomeJogador = JOptionPane.showInputDialog(null, "Final do jogo, a tua pontuação foi: " + pontuacao + " !" + " Insere o teu nome:", "Bom jogo :)" ,JOptionPane.DEFAULT_OPTION);
//        JOptionPane.showMessageDialog(null, "Final do jogo, a tua pontuação foi: " + pontuacao);


        //Criação e escrita no ficheiro de pontuação
        try {
            File myObj = new File("pontuacoes.txt");
            if (myObj.createNewFile()) {
                System.out.println("Ficheiro criado: " + myObj.getName());
            } else {
                System.out.println("Ficheiro já existe.");
            }
            FileWriter myWriter = new FileWriter("pontuacoes.txt", true);
            myWriter.write("Jogador: " + nomeJogador + "| Pontuação: " + String.valueOf(pontuacao) + "\n");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static void carregarPalavrasDaAPI(int quantidade) {
        try {
            int palavasAdicionadas = 0;
            do{
            String url = "https://random-word-api.herokuapp.com/word?number=1";
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            String palavra = response.toString().replace("[", "").replace("]", "").replace("\"", "");
            String dica = obterDicaDaAPI(palavra.trim().toLowerCase());
            if (dica != null && !dica.equals("0")) { // Verifica se a dica é válida
                palavras.add(new Palavra(palavra.trim(), 5, dica, '0', '0', "Boa sorte!"));
                palavasAdicionadas++;
                System.out.println("Palavra adicionada com sucesso: " + palavra + " com dica: " + dica);
            } else {
                System.out.println("Palavra ignorada (sem definição): " + palavra);
            }
            }while (palavasAdicionadas < quantidade);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar palavras da API: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static String obterDicaDaAPI(String palavra) {
        try {
            String url = "https://api.dictionaryapi.dev/api/v2/entries/en/" + palavra;
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            if (connection.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();

                // Extrair a definição do JSON retornado
                String json = response.toString();
                int defIndex = json.indexOf("\"definition\":\"");
                if (defIndex != -1) {
                    int start = defIndex + 14;
                    int end = json.indexOf("\"", start);
                    return json.substring(start, end);
                }
            } else if (connection.getResponseCode() == 404) {
                System.out.println("Palavra não encontrada no dicionário: " + palavra);
            } else {
                System.out.println("Erro inesperado para a palavra: " + palavra + " | Código de resposta: " + connection.getResponseCode());
            }
        } catch (Exception e) {
            System.out.println("Erro ao obter dica para a palavra: " + palavra + " | Erro: " + e.getMessage());
        }

        return null;
    }
}