package entity;

public class Erro {
    int line;

    public Erro() {
    }

    public Erro(int line) {
        this.line = line;
    }
    public String toString(){
        StringBuffer str = new StringBuffer();
        str.append(Integer.toString(this.line) );
        return str.toString();
    }
}
