public class Palavra {
    // Atributos da classe Palavra
    private int tentativas;                 // Número de tentativas restantes
    private String palavra;                 // Palavra a ser adivinhada
    private String dica;                    // Dica sobre a palavra
    private String palavraProgresso;        // Representação da palavra com letras descobertas
    private String feedback;                // Feedback do jogador
    private char ajuda1;                    // Primeira letra da ajuda
    private char ajuda2;                    // Segunda letra da ajuda
    private boolean acertouLetra = false;   // Indica se a última tentativa foi correta
    private int pontuacao;                  // Pontuação do jogador
    private boolean emJogo = false;         // Indica se a palavra está em jogo
    private boolean escolhida = false;      // Indica se a palavra já foi escolhida

    // Getters e Setters
    public int getTentativas() {
        return tentativas;
    }

    public String getDica() {
        return dica;
    }

    public String getPalavraProgresso() {
        return palavraProgresso;
    }

    public String getFeedback() {
        return feedback;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setEmJogo(boolean b) {
        emJogo = b;
    }

    public boolean getEscolhida() {
        return escolhida;
    }

    public void setEscolhida(boolean b) {
        escolhida = b;
    }

    // Construtor da classe Palavra
    public Palavra(String palavraAdivinhar, int palavraTentativas, String palavraDica, char dica1, char dica2, String feed) {
        palavra = palavraAdivinhar;
        tentativas = palavraTentativas;
        dica = palavraDica;
        ajuda1 = dica1;
        ajuda2 = dica2;
        palavraProgresso = palavra.replaceAll("[a-z]", "_ "); // Oculta as letras da palavra
        verificaLetra(ajuda1); // Revela a primeira letra de ajuda
        verificaLetra(ajuda2); // Revela a segunda letra de ajuda
        feedback = feed;
    }

    // Método que verifica se a letra fornecida está na palavra
    public void verificaLetra(char letra) {
        acertouLetra = false;
        for (int i = 0; i < palavra.length(); i++) {
            if (palavra.charAt(i) == letra) {
                acertouLetra = true;
                palavraProgresso = palavraProgresso.replaceAll("_ ", "_"); // Ajusta o progresso visual
                String temp;
                temp = palavraProgresso.substring(0, i) + letra + palavraProgresso.substring(i + 1);
                temp = temp.replaceAll("_", "_ ");
                palavraProgresso = temp;
                if (emJogo) {
                    pontuacao += 10; // Adiciona pontos assim que uma letra está correta
                    feedback = "Letra correta! Tens " + tentativas + " tentativas e estás com " + pontuacao + " pontos";
                }
            }
        }
        if (!acertouLetra && emJogo) {
            tentativas--; // Reduz tentativas ao errar
            feedback = "Letra incorreta :( Tens " + tentativas + " tentativas";
        }
    }

    // Método que verifica se a palavra foi completamente descoberta
    public boolean acertou() {
        if (palavra.matches(palavraProgresso)) {
            pontuacao += tentativas * 10; // Pontuação bónus por tentativas restantes
            return true;
        } else {
            return false;
        }
    }
}
