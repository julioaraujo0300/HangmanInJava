public class Palavra {
    private int tentativas;
    private String palavra;
    private String dica;
    private String palavraProgresso;
    private String feedback;
    private char ajuda1;
    private char ajuda2;
    private  boolean acertouLetra = false;



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

    public void setFeedback(String f){
        feedback = f;
    }

    public boolean getAcertouLetra(){
        return acertouLetra;
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
                if(palavraProgresso.charAt(i) == letra){

                }else {
                    acertouLetra = true;
                    palavraProgresso = palavraProgresso.replaceAll("_ ", "_");
                    String temp;
                    temp = palavraProgresso.substring(0, i) + letra + palavraProgresso.substring(i + 1);
                    temp = temp.replaceAll("_", "_ ");
                    palavraProgresso = temp;
                }
            }
        }
        if(!acertouLetra){
            tentativas--;
        }
    }


    public boolean acertou(){
        if(palavra.matches(palavraProgresso)){
            return true;
        }else{
            return false;
        }
    }
}
