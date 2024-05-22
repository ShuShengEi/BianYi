package entity;

public class WordA {
    String word;
    int token;
    int line;

    public WordA() {
    }

    public WordA(String word, int token) {
        this.word = word;
        this.token = token;
    }

    public WordA(String word, int token, int line) {
        this.word = word;
        this.token = token;
        this.line = line;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }
    public String toString(){
        StringBuffer str = new StringBuffer();
        str.append(Integer.toString(this.line) + "     " + this.word + "           " + Integer.toString(this.token));
        return str.toString();
    }
}
