import javax.swing.*;
import java.awt.*;
import  java.util.ArrayList;

public class Main {
    static ArrayList<Palavra> palavras = new ArrayList<Palavra>();

    public static void main(String[] args) {
        String tent;
        String feedback;

        palavras.add( new Palavra("computador", 4, "informática", 'o', 'u', "Esta é fácil bro")  );
        palavras.add( new Palavra("porto", 2, "Cidade Portuguesa", 'r', '0', "Vê lá se falhas pah")  );
        palavras.add( new Palavra("venus", 2, "Planeta", 'v', '0', "( ͡° ͜ʖ ͡°)")  );
        palavras.add( new Palavra("oftalmologista", 6, "Profissão(Saúde)", 'o', 'f', "agora tá complicado")  );
        palavras.add( new Palavra("rinoceronte", 4, "Animal", 'e', 'o', "pesado")  );




        for(int i = 0; i < palavras.size(); i++){
            Palavra p = palavras.get(i);
            do {
                tent = JOptionPane.showInputDialog(null, "Tentativas: " + p.getTentativas() + " | Dica: " + p.getDica() + " | " + p.getPalavraProgresso() + "\n Insira uma letra:", p.getFeedback(), JOptionPane.QUESTION_MESSAGE);
                tent = tent.toLowerCase();
                if(tent.length() == 1 && tent.matches(".*[a-z].*")){
                    p.verificaLetra(tent.charAt(0));
                    p.setFeedback(p.getAcertouLetra() ? "Letra correta! Tens " + p.getTentativas() + " tentativas" : "Letra incorreta :( Tens " + p.getTentativas() + " tentativas");
                }
            }while(p.getTentativas() > 0 && !p.acertou());
        }
    }
}