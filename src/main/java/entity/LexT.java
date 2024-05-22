package entity;

import java.util.List;

public class LexT {
    public List<WordA> words ;
    public List<Erro> erros;

    public LexT(List<WordA> words, List<Erro> erros) {
        this.words = words;
        this.erros = erros;
    }
}
