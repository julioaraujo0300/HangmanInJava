public class Palavra {
    private int tentativas;
    private String palavra;
    private String dica;
    private String palavraProgresso;
    private String feedback;
    private char ajuda1;
    private char ajuda2;
    private  boolean acertouLetra = false;
    private int pontuacao;
    private boolean emJogo = false;
    private boolean escolhida = false;


    //setters e getters
    public  int getTentativas(){
        return  tentativas;
    }


    public String getDica(){
        return  dica;
    }

    public  String getPalavraProgresso(){
        return  palavraProgresso;
    }

    public String getFeedback(){
        return  feedback;
    }

    public int getPontuacao(){
        return pontuacao;
    }

    public void setEmJogo(boolean b){
        emJogo = b;
    }

    public boolean getEscolhida(){
        return  escolhida;
    }

    public void setEscolhida(boolean b){
        escolhida = b;
    }

    //construtor
    public Palavra(String palavraAdivinhar, int palavraTentativas, String palavraDica, char dica1, char dica2, String feed) {
        palavra = palavraAdivinhar;
        tentativas = palavraTentativas;
        dica = palavraDica;
        ajuda1 = dica1;
        ajuda2 = dica2;
        palavraProgresso = palavra.replaceAll("[a-z]", "_ " );
        verificaLetra(ajuda1);
        verificaLetra(ajuda2);
        feedback = feed;
    }

    //funções
    public void verificaLetra(char letra){
        acertouLetra = false;
        for (int i = 0; i < palavra.length(); i++){
            if(palavra.charAt(i) == letra){
                acertouLetra = true;
                palavraProgresso = palavraProgresso.replaceAll("_ ", "_");
                String temp;
                temp = palavraProgresso.substring(0, i) + letra + palavraProgresso.substring(i + 1);
                temp = temp.replaceAll("_", "_ ");
                palavraProgresso = temp;
                if(emJogo){
                    pontuacao += 10;
                    feedback = "Letra correta! Tens " + tentativas + " tentativas" + " e estás com " + pontuacao + " pontos";
                }
            }
        }
        if(!acertouLetra && emJogo){
            tentativas--;
            feedback = "Letra incorreta :( Tens " + tentativas + " tentativas";
        }
    }


    public boolean acertou(){
        if(palavra.matches(palavraProgresso)){
            pontuacao += tentativas * 10;
            return true;
        }else{
            return false;
        }
    }
}
