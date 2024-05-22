package lexical.analysis;

import entity.Erro;
import entity.LexT;
import entity.WordA;

import java.util.ArrayList;
import java.util.List;

public class Tests1 {
    public static void main(String[] args) {
        String content ="void main()\n" +
                "{\n" +
                "\tint a=0;    //测试括号配对\n" +
                "}";
        LexT contents = lineAy(content);
        StringBuffer str = new StringBuffer();
        StringBuffer str1 = new StringBuffer();
        int i = 0;
        while (i < contents.words.size()){
            str.append(contents.words.get(i).toString() + "\n");
            i++;
        }
        i = 0;
        while (i < contents.erros.size()){
            str.append(contents.erros.get(i).toString() + "\n");
            i++;
        }
        System.out.println(str);
        System.out.println(str1);


    }
    public static LexT lineAy(String content){
        int a = 0;
        int i = 0;
        int col = 1;
        List<WordA> words = new ArrayList<>();
        List<Erro>  erros = new ArrayList<>();
        StringBuffer str;
        while (i - 1< content.length()){
            if (i == content.length())
                break;
            while (content.charAt(i) == ' ' && i - 1 < content.length())
                i++;
            switch (a){
                case 0:
                    if (content.charAt(i) == '\n'){
                        i++;
                        col++;
                        break;
                    }
                    if (content.charAt(i) == '\t'){
                        i++;
                        break;
                    }
                    if ((Character.isLetter(content.charAt(i))) || content.charAt(i) == '_'){ // 首个为字母或者下划线
                        a = 1;
                    }else if (Character.isDigit(content.charAt(i))){ //首个为数字
                        a = 3;
                    }else if (content.charAt(i) == '\'')//字符
                        a = 21;
                    else if (content.charAt(i) == '\"')//字符串常量
                        a = 25;
                    else if (content.charAt(i) == '/')//注释
                        a = 28;
                    else if (content.charAt(i) == '>'){
                        a = 29;
                    }else if (content.charAt(i) == '<'){
                        a = 30;
                    }else if (content.charAt(i) == '='){
                        a = 31;
                    }
                    else if (content.charAt(i) == '!'){
                        a = 32;
                    }else if (content.charAt(i) == ';' ||content.charAt(i) == '{' || content.charAt(i) == '}' ||
                        content.charAt(i) == '(' ||content.charAt(i) == ')' || content.charAt(i) == ',' ){
                        a = 33;
                    }else if (content.charAt(i) == '+'){
                        a = 34;
                    }
                    else if (content.charAt(i) == '-'){
                        a = 35;
                    }
                    else {
                        erros.add(new Erro(col));
                        col++;
                        while (content.charAt(i) != ' ')
                            i++;
                    }
                    break;

                case 1:
                    str = new StringBuffer();
                    while (Character.isDigit(content.charAt(i)) || Character.isLetter(content.charAt(i)) || content.charAt(i) == '_'){
                        str.append(content.charAt(i));
                        i++;
                    }
                    words.add(new WordA(str.toString(),700,col));
                    a = 0;
                    break;


                case 3:
                    if (content.charAt(i) >= '1' && content.charAt(i) <= '9'){
                        a = 4;
                        break;
                    } else if (content.charAt(i) == '0'){
                        a = 12;
                        break;
                    }

                case 4:
                    str = new StringBuffer();
                    while (Character.isDigit(content.charAt(i))){
                        str.append(content.charAt(i));
                        i++;
                    }
                    if (content.charAt(i) == '.'){
                        str.append(content.charAt(i));
                        i++;
                        while (Character.isDigit(content.charAt(i)) || content.charAt(i) == 'e'){
                            str.append(content.charAt(i));
                            i++;
                        }
                        words.add(new WordA(str.toString(),800,col));//小数
                        a = 0;
                        break;
                    }else {
                        if (Character.isLetter(content.charAt(i)) ){
                            erros.add(new Erro(col));
                            while (content.charAt(i) != ' ')
                                i++;
                        }
                        words.add(new WordA(str.toString(),800,col));//整数
                        a = 0;
                        break;
                    }
                case 12:
                    if (content.charAt(i + 1) == ' ' || content.charAt(i + 1 ) == ';'){
                        str = new StringBuffer();
                        str.append(content.charAt(i));
                        words.add(new WordA(str.toString(),800,col));//整数
                        i++;
                        a = 0;
                        break;
                    }
                    if (content.charAt(i + 1) >= '1' && content.charAt(i + 1) <= '7'){
                        a = 13;
                        break;
                    }else if (content.charAt(i + 1) == 'x' || content.charAt(i + 1) == 'X'){
                        a = 15;
                        break;
                    }
                    else if (content.charAt(i + 1) == 'b' || content.charAt(i + 1) == 'B'){
                        a = 18;
                        break;
                    }
                case 13://八进制
                    str = new StringBuffer();
                    str.append(content.charAt(i));
                    i++;
                    while (content.charAt(i ) >= '0' && content.charAt(i ) <= '7'){
                        str.append(content.charAt(i));
                        i++;
                    }
                    if (content.charAt(i) >= '8'){
                        erros.add(new Erro(col));
                        while (content.charAt(i) != ' ')
                            i++;
                        a = 0;
                        break;
                    }
                    words.add(new WordA(str.toString(),800,col));
                    a = 0;
                    break;
                case 15://16进制
                    str = new StringBuffer();
                    str.append(content.charAt(i));
                    i++;
                    str.append(content.charAt(i));
                    i++;
                    while ((content.charAt(i ) >= '0' && content.charAt(i ) <= '9') ||
                            (content.charAt(i ) >= 'a' && content.charAt(i ) <= 'f') ||
                            (content.charAt(i ) >= 'A' && content.charAt(i ) <= 'F')){
                        str.append(content.charAt(i));
                        i++;
                    }

                    if (content.charAt(i) >= 'g'){
                        erros.add(new Erro(col));
                        while (content.charAt(i) != ' ')
                            i++;
                        a = 0;
                        break;
                    }
                    words.add(new WordA(str.toString(),800,col));
                    a = 0;
                    break;
                case 18://二进制
                    str = new StringBuffer();
                    str.append(content.charAt(i));
                    i++;
                    while ((content.charAt(i + 1) >= '0' && content.charAt(i + 1) <= '1')){
                        str.append(content.charAt(i));
                        i++;
                    }
                    if (content.charAt(i) >= '2'){
                        erros.add(new Erro(col));
                        while (content.charAt(i) != ' ')
                            i++;
                        a = 0;
                        break;
                    }
                    words.add(new WordA(str.toString(),800,col));
                    a = 0;
                    break;

                case 21: //单引号
                    str = new StringBuffer();

                    i++;
                    if (content.charAt(i) == '\\'){
                        a = 24;
                        break;
                    }else {
                        str = new StringBuffer();
                        str.append(content.charAt(i));
                        words.add(new WordA(str.toString(),406,col));
                        i++;
                        if (content.charAt(i) == '\''){
                            str = new StringBuffer();

                            a = 0;
                            i++;
                            break;
                        }else {
                            erros.add(new Erro(col));
                            while (content.charAt(i) != ' ')
                                i++;
                            a = 0;
                            break;
                        }
                    }
                case 22:
                    str = new StringBuffer();
                    str.append(content.charAt(i));
                    words.add(new WordA(str.toString(),46,col));
                    i++;
                    if (content.charAt(i) == '\''){
                        str = new StringBuffer();
                        str.append(content.charAt(i));
                        words.add(new WordA(str.toString(),47,col));
                        a = 0;
                        break;
                    }
                case 24:
                    str = new StringBuffer();
                    str.append(content.charAt(i));
                    words.add(new WordA(str.toString(),58,col));
                    i++;
                    if (content.charAt(i) == 'n' || content.charAt(i) == 'r' || content.charAt(i) == 't' ||
                            content.charAt(i) == '\\' || content.charAt(i) == '\'' || content.charAt(i) == '\"'){
                        str = new StringBuffer();
                        str.append(content.charAt(i));
                        words.add(new WordA(str.toString(),59,col));
                        a = 0;
                        break;
                    }
                case 25:
                    str = new StringBuffer();
                    str.append(content.charAt(i));
                    words.add(new WordA(str.toString(),70,col));
                    i++;
                    str = new StringBuffer();
                    if (i+1>= content.length())
                        break;
                    int temp = i;
                    while (i < content.length() && content.charAt(i) != '"' && content.charAt(i) != '\n'){
                        str = new StringBuffer();
                        str.append(content.charAt(i));
                        i++;
                    }
                    if ((content.charAt(i - 1) != '"' && i == content.length() ) || content.charAt(i) == '\n'){
                        erros.add(new Erro(col));
                        i = temp + 1;
                        words.remove(words.size() - 1);

                        a = 0;
                        break;
                    }
                    words.add(new WordA(str.toString(),71,col));
                    if (content.charAt(i) == '"'){
                        str = new StringBuffer();
                        str.append(content.charAt(i));
                        words.add(new WordA(str.toString(),72,col));
                        i++;
                        a = 0;
                        break;
                    }
                case 28:
                    if (content.charAt(i +1) == '/'){
                        words.add(new WordA("//",73,col));
                        while (content.charAt(i) != '\n')
                            i++;
                        a = 0;
                        break;
                    }else if (content.charAt(i +1) == '*'){
                        words.add(new WordA("/*",74,col));
                        i++;
                        while (i + 1 < content.length()){
                            i++;

                            if (content.charAt(i) == '\n')
                                col++;
                            if (content.charAt(i) == '*' && content.charAt(i + 1 ) == '/'){
                                i++;
                                i++;
                                a = 0;
                                words.add(new WordA("*/",75,col));
                                break;
                            }

                        }

                    }else {
                        words.add(new WordA("/",76,col));
                        i++;
                        a = 0;
                        break;
                    }
                case 29:
                    if (i + 1 < content.length() && content.charAt(i + 1) == '='){
                        words.add(new WordA(">=",77,col));
                        i++;
                        a = 0;
                        break;
                    }else {
                        words.add(new WordA(">",78,col));
                        i++;
                        a = 0;
                        break;
                    }
                case 30:
                    if (i + 1 < content.length() && content.charAt(i + 1) == '='){
                        words.add(new WordA("<=",79,col));
                        i++;
                        a = 0;
                        break;
                    }else {
                        words.add(new WordA("<",80,col));
                        i++;
                        a = 0;
                        break;
                    }
                case 31: //等号
                    if (i + 1 < content.length() && content.charAt(i + 1) == '='){
                        words.add(new WordA("==",81,col));
                        i++;
                        a = 0;
                        break;
                    }else {
                        words.add(new WordA("=",82,col));
                        i++;
                        a = 0;
                        break;
                    }
                case 32:
                    if (i + 1 < content.length() && content.charAt(i + 1) == '='){
                        words.add(new WordA("!=",83,col));
                        i++;
                        a = 0;
                        break;
                    }else {
                        words.add(new WordA("!",84,col));
                        i++;
                        a = 0;
                        break;
                    }
                case 33:
                    words.add(new WordA(Character.toString(content.charAt(i)),85,col));
                    i++;
                    a = 0;
                    break;
                case 34:
                    if (i + 1 < content.length() && content.charAt(i + 1) == '='){
                        words.add(new WordA("+=",86,col));
                        i++;
                        a = 0;
                        break;
                    }else {
                        words.add(new WordA("+",87,col));
                        i++;
                        a = 0;
                        break;
                    }
                case 35:
                    if (i+1 >= content.length())
                        break;
                    i++;
                    if (content.charAt(i) >= '1' && content.charAt(i) <= '9'){
                        str = new StringBuffer();
                        str.append('-');
                        while (Character.isDigit(content.charAt(i))){
                            str.append(content.charAt(i));
                            i++;
                        }
                        words.add(new WordA(str.toString(),88,col));
                        i++;
                        a = 0;
                        break;
                    }else {
                        words.add(new WordA("-",88,col));
                        i++;
                        a = 0;
                        break;
                    }

            }
        }
        return new LexT(words,erros);
    }

}
