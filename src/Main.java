import javax.swing.*;
import  java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    static ArrayList<Palavra> palavras = new ArrayList<Palavra>();
    static ArrayList<Palavra> palavrasEmJogo = new ArrayList<Palavra>();

    public static void main(String[] args) {
        String tent;
        int nrPalavras = 1;
        int pontuacao = 0;
        String nomeJogador = "";

        palavras.add( new Palavra("computador", 3, "Informática", 'o', 'u', "Esta é fácil bro")  );
        palavras.add( new Palavra("porto", 1, "Cidade", 'r', '0', "Vê lá se falhas pah")  );
        palavras.add( new Palavra("venus", 1, "Planeta", 'v', '0', "( ͡° ͜ʖ ͡°)")  );
        palavras.add( new Palavra("oftalmologista", 5, "Profissão(Saúde)", 'o', 'f', "vê lá bem")  );
        palavras.add( new Palavra("rinoceronte", 3, "Animal Selvagem", 'e', 'o', "pesado")  );
        palavras.add( new Palavra("gabardine", 3, "Peça de Roupa", 'b', 'd', "Que briol")  );
        palavras.add( new Palavra("lenovo", 2, "Marca de Computadores", 'n', '0', "Bing chilling")  );
        palavras.add( new Palavra("paralelepipedo", 6, "Não dou >:)", 'i', '0', "Boa sorte amigo")  );


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
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter("pontuacoes.txt", true);
            myWriter.write("Jogador: " + nomeJogador + "| Pontuação: " + String.valueOf(pontuacao) + "\n");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}