package yufa;

import entity.GrammarNode;
import entity.LexT;
import entity.WordA;

import java.util.List;

import static lexical.analysis.Tests1.lineAy;

public class Test2 {
    public static void main(String[] args) {

        String str1 = "const int a =100;\n" +
                "char h = 'f';\n" +
                "void abc(int,char);\n" +
                "\n" +
                "void main()\n" +
                "{\n" +
                "int a = 1;\n" +
                "while(a<10){a=1+3;}\n" +
                "\n" +
                "if(a>=10){a=5;}\n" +
                "\n" +
                "else {a=1+3;}\n" +
                "\n" +
                "int a = 10;   \n" +
                "return 0;\n" +
                "}void abc(int a, int b, char c)\n" +
                "{\n" +
                "int a = 1;\n" +
                "while(a<10){a=1+3;}\n" +
                "\n" +
                "if(a>=10){a=5;}\n" +
                "\n" +
                "else {a=1+3;}\n" +
                "\n" +
                "int a = 10;   \n" +
                "return 0;\n" +
                "\n" +
                "return 0;\n" +
                "}\n" +
                "int bcd()\n" +
                "{\n" +
                "int a = 1;\n" +
                "while(a<10){a=1+3;}\n" +
                "\n" +
                "if(a>=10){a=5;}\n" +
                "\n" +
                "else {a=1+3;}\n" +
                "\n" +
                "int a = 10;   \n" +
                "return 0;\n" +
                "}";
        LexT contents = lineAy(str1);
        GrammarNode node = getYu(contents);
        System.out.println(node.toString()); // 从根节点开始打印整棵树
    }
    public static GrammarNode getYu(LexT content){//<程序>→<声明语句>main()<复合语句><函数块>
        GrammarNode node0 = new GrammarNode("程序");
        List<WordA> list = content.words;
        int num = 0;
        int i = 0;
        node0.addChild(new GrammarNode("声明语句"));
        i = declarativeStatements(list,node0.getChild(num),i);
        node0.addChild(new GrammarNode("main()"));
        num++;
        i++;
        i++;
        i++;
        i++;
        System.out.println(list.get(i).getWord());
        i = compoundStatements(list,node0.getChild(num),i);
        node0.addChild(new GrammarNode("函数块"));
        num++;
        i = functionBlocks(list,node0.getChild(num),i);
        return node0;
    }
    public static int functionBlocks(List<WordA> list, GrammarNode node, int i){//<函数块>→<函数定义><函数块>|ε
        int num = 0;
        if (i >= list.size()) {
            return i;
        }
        node.addChild(new GrammarNode("函数定义"));
        i = functionDefinition(list,node.getChild(num),i);
        node.addChild(new GrammarNode("函数块"));
        num++;
        i = functionBlocks(list,node.getChild(num),i);
        return i;
    }
    public static int functionDefinition(List<WordA> list, GrammarNode node, int i){//<函数定义>→<函数类型><变量>(<函数定义形参列表>)<复合语句>
        int num = 0;
        node.addChild(new GrammarNode("函数类型"));
        i = functionType(list,node.getChild(num),i);
        node.addChild(new GrammarNode("变量"));
        num++;
        i = variable(list,node.getChild(num),i);
        node.addChild(new GrammarNode("("));
        num++;
        i++;
        node.addChild(new GrammarNode("函数定义形参列表"));
        num++;
        i = functionDefinesAListOfParameters(list,node.getChild(num),i);
        node.addChild(new GrammarNode(")"));
        num++;
        i++;
        node.addChild(new GrammarNode("复合语句"));
        num++;
        i = compoundStatements(list,node.getChild(num),i);
        return i;
    }
    public static int functionDefinesAListOfParameters(List<WordA> list, GrammarNode node, int i){//<函数定义形参列表>→<函数定义形参>|ε
        int num = 0;
        if (list.get(i).getWord().equals(")")) {
            return i;
        }
        node.addChild(new GrammarNode("函数定义形参"));
        i = functionDefinesTheFormParameters(list,node.getChild(num),i);
        return i;
    }
    public static int functionDefinesTheFormParameters(List<WordA> list, GrammarNode node, int i){//<函数定义形参>→<变量类型><变量><函数定义形参0>
        int num = 0;
        node.addChild(new GrammarNode("变量类型"));
        i = functionType(list,node.getChild(num),i);
        node.addChild(new GrammarNode("变量"));
        num++;
        i = variable(list,node.getChild(num),i);
        node.addChild(new GrammarNode("函数定义形参0"));
        num++;
        i = functionDefinesTheFormParameters0(list,node.getChild(num),i);
        return i;
    }
    public static int functionDefinesTheFormParameters0(List<WordA> list, GrammarNode node, int i){//<函数定义形参0>→,<函数定义形参>|ε
        int num = 0;
        if (list.get(i).getWord().equals(",")) {
            node.addChild(new GrammarNode(","));
            i++;
            node.addChild(new GrammarNode("函数定义形参"));
            num++;
            i = functionDefinesTheFormParameters(list,node.getChild(num),i);
        }
        return i;
    }
    public static int compoundStatements(List<WordA> list, GrammarNode node, int i){//<复合语句>→{<语句表>}
        int num = 0;
        node.addChild(new GrammarNode("{"));
        node.addChild(new GrammarNode("语句表"));
        num++;
        i++;
        i = tableOfStatements(list,node.getChild(num),i);
        node.addChild(new GrammarNode("}"));
        num++;
        i++;
        return i;
    }
    public static int tableOfStatements(List<WordA> list, GrammarNode node0, int i){//<语句表>→<语句><语句表0>
        int num = 0;
        node0.addChild(new GrammarNode("语句"));
        i = statement(list,node0.getChild(num),i);
        node0.addChild(new GrammarNode("语句表0"));
        num++;
        i = tableOfStatements0(list,node0.getChild(num),i);
        return i;
    }
    public static int tableOfStatements0(List<WordA> list, GrammarNode node0, int i){//<语句表0>→<语句表>｜ε
        int num = 0;
        if (list.get(i).getWord().equals("}"))
            return i;
        node0.addChild(new GrammarNode("语句表"));
        i = tableOfStatements(list,node0.getChild(num),i);
        return i;
    }
    public static int statement(List<WordA> list, GrammarNode node0, int i){//<语句>→<值声明>|<执行语句>
        int num = 0;
        if (isValueD(list,i)){//值声明
            node0.addChild(new GrammarNode("值声明"));
            i = valueDeclaration(list,node0.getChild(num),i);
            return i;
        }
        node0.addChild(new GrammarNode("执行语句"));
        i = executeTheStatement(list,node0.getChild(num),i);
        return i;
    }
    public static int executeTheStatement(List<WordA> list, GrammarNode node0, int i){//<执行语句>→<数据处理语句>|<控制语句>|<复合语句>
        int num = 0;
        if (isControlStatements(list,i)){
            node0.addChild(new GrammarNode("控制语句"));
            i = controlStatements(list,node0.getChild(num),i);
            return i;
        }else if (isCompoundStatements(list,i)){
            node0.addChild(new GrammarNode("复合语句"));
            i = compoundStatements(list,node0.getChild(num),i);
            return i;
        }
        node0.addChild(new GrammarNode("数据处理语句"));
        i = dataProcessingStatements(list,node0.getChild(num),i);
        return i;
    }
    public static int dataProcessingStatements(List<WordA> list, GrammarNode node0, int i){//<数据处理语句>→<赋值语句>|<函数调用语句>
        int num = 0;
        if (list.get(i + 1).getWord().equals("=")){
            node0.addChild(new GrammarNode("赋值语句"));
            i = assignmentStatements(list,node0.getChild(num),i);
        }
        return i;
    }
    public static int assignmentStatements(List<WordA> list, GrammarNode node0, int i){//<赋值语句>→<赋值表达式>;
        int num = 0;
        node0.addChild(new GrammarNode("赋值表达式"));
        i = assignmentExpressions(list,node0.getChild(num),i);
        node0.addChild(new GrammarNode(";"));
        i++;
        return i;
    }
    public static int assignmentExpressions(List<WordA> list, GrammarNode node0, int i){//<赋值表达式>→<变量>=<赋值表达式0>
        int num = 0;
        node0.addChild(new GrammarNode("变量"));
        i = variable(list,node0.getChild(num),i);
        node0.addChild(new GrammarNode("="));
        num++;
        i++;
        node0.addChild(new GrammarNode("赋值表达式0"));
        num++;
        i = assignmentExpressions0(list,node0.getChild(num),i);
        return i;
    }
    public static int assignmentExpressions0(List<WordA> list, GrammarNode node0, int i){//<赋值表达式0>→<布尔表达式>|<函数调用>
        int num = 0;
        if (list.get(i + 1).getWord().equals("(")){//函数调用
            node0.addChild(new GrammarNode("函数调用"));
            i = functionCalls(list,node0.getChild(num),i);
            return i;
        }
        node0.addChild(new GrammarNode("布尔表达式"));
        i = booleanExpression(list,node0.getChild(num),i);
        return  i;
    }
    public static int functionCalls(List<WordA> list, GrammarNode node0, int i){//<函数调用>→<变量>(<实参列表>)
        int num = 0;
        node0.addChild(new GrammarNode("变量"));
        i = variable(list,node0.getChild(num),i);
        node0.addChild(new GrammarNode("("));
        num++;
        i++;
        node0.addChild(new GrammarNode("实参列表"));
        num++;
        i = listOfArguments(list,node0.getChild(num),i);
        node0.addChild(new GrammarNode(")"));
        i++;
        return i;
    }
    public static int listOfArguments(List<WordA> list, GrammarNode node0, int i){//<实参列表>→<实参>|ε
        int num = 0;
        if (list.get(i).getWord().equals(")")){
            return i;
        }
        node0.addChild(new GrammarNode("实参"));
        i = arguments(list,node0.getChild(num),i);
        return i;
    }
    public static int arguments(List<WordA> list, GrammarNode node0, int i){//<实参>→<布尔表达式><实参0>
        int num = 0;
        node0.addChild(new GrammarNode("布尔表达式"));
        i = booleanExpression(list,node0.getChild(num),i);
        node0.addChild(new GrammarNode("实参0"));
        i = arguments0(list,node0.getChild(num),i);
        return i;
    }
    public static int arguments0(List<WordA> list, GrammarNode node0, int i){//<实参0>→,<实参>|ε
        int num = 0;
        if (list.get(i).getWord().equals(",")){
            node0.addChild(new GrammarNode(","));
            i++;
            node0.addChild(new GrammarNode("实参"));
            num++;
            i = arguments0(list,node0.getChild(num),i);
            return i;
        }
        return i;
    }
    public static int controlStatements(List<WordA> list, GrammarNode node0, int i){//<控制语句>→<if语句>|<while语句>|<return语句>
        int num = 0;
        if (list.get(i).getWord().equals("if")){
            node0.addChild(new GrammarNode("if语句"));
            i = ifStatements(list,node0.getChild(num),i);
            return i;
        }else if (list.get(i).getWord().equals("while")){
            node0.addChild(new GrammarNode("while语句"));
            i = whileStatements(list,node0.getChild(num),i);
            return i;
        }else {
            node0.addChild(new GrammarNode("return语句"));
            i = returnStatements(list,node0.getChild(num),i);
            return i;
        }


    }
    public static int returnStatements(List<WordA> list, GrammarNode node0, int i){//<return 语句>→return<return 语句0>
        int num = 0;
        node0.addChild(new GrammarNode("return"));
        i++;
        node0.addChild(new GrammarNode("return 语句0"));
        num++;
        i = returnStatements0(list,node0.getChild(num),i);
        return i;
    }
    public static int returnStatements0(List<WordA> list, GrammarNode node0, int i){//<return 语句0>;|<布尔表达式>;
        int num = 0;
        if (list.get(i).getWord().equals(";")){
            node0.addChild(new GrammarNode(";"));
            i++;
            return i;
        }
        node0.addChild(new GrammarNode("布尔表达式"));
        i = booleanExpression(list,node0.getChild(num),i);
        node0.addChild(new GrammarNode(";"));
        i++;
        num++;
        return i;
    }
    public static int whileStatements(List<WordA> list, GrammarNode node0, int i){//<while 语句>→while(<布尔表达式>)<复合语句>
        int num = 0;
        node0.addChild(new GrammarNode("while"));
        i++;
        node0.addChild(new GrammarNode("("));
        num++;
        i++;
        node0.addChild(new GrammarNode("布尔表达式"));
        num++;
        i = booleanExpression(list,node0.getChild(num),i);
        node0.addChild(new GrammarNode(")"));
        num++;
        i++;
        node0.addChild(new GrammarNode("复合语句"));
        num++ ;
        i = compoundStatements(list,node0.getChild(num),i);
        return i;
    }
    public static int ifStatements(List<WordA> list, GrammarNode node0, int i){//<if 语句>→if(<布尔表达式>)<复合语句><if Tail>
        int num = 0;
        node0.addChild(new GrammarNode("if"));
        node0.addChild(new GrammarNode("("));
        num++;
        i++;
        i++;
        node0.addChild(new GrammarNode("布尔表达式"));
        num++;
        i = booleanExpression(list,node0.getChild(num),i);
        node0.addChild(new GrammarNode(")"));
        num++;
        i++;
        node0.addChild(new GrammarNode("复合语句"));
        num++;
        i = compoundStatements(list,node0.getChild(num),i);
        node0.addChild(new GrammarNode("if Tail"));
        num++;
        i = ifTailStatements(list,node0.getChild(num),i);

        return i;
    }
    public static int ifTailStatements(List<WordA> list, GrammarNode node0, int i){//<if Tail>→else <ifTail0>|ε
        int num = 0;
        if (list.get(i).getWord().equals("else")){
            node0.addChild(new GrammarNode("else"));
            i++;
            node0.addChild(new GrammarNode("ifTail0"));
            num++;
            i = ifTailStatements0(list,node0.getChild(num),i);
            return i;
        }
        return i;
    }
    public static int ifTailStatements0(List<WordA> list, GrammarNode node0, int i){//<ifTail0> → <复合语句> | <if语句>
        int num = 0;
        if (list.get(i).getWord().equals("if")){
            node0.addChild(new GrammarNode("if语句"));
            i = ifStatements(list,node0.getChild(num),i);
            return i;
        }
        node0.addChild(new GrammarNode("复合语句"));
        i = compoundStatements(list,node0.getChild(num),i);
        return i;
    }
    public static boolean isCompoundStatements(List<WordA> list, int i){//真为是复合语句
        if(list.get(i).getWord().equals("{"))
            return true;
        return false;
    }
    public static boolean isControlStatements(List<WordA> list, int i){//真为是控制语句
        if (list.get(i).getWord().equals("if")||list.get(i).getWord().equals("return")||list.get(i).getWord().equals("while"))
            return true;
        return false;
    }
    public static int declarativeStatements(List<WordA> list,GrammarNode node0,int i){//<声明语句>→<值声明>|<函数声明>|ε
        int num = 0;
        while ( i < list.size() && !"main".equals(list.get(i).getWord()) ){
            if (isValueD(list,i)){
                node0.addChild(new GrammarNode("值声明"));
                i = valueDeclaration(list,node0.getChild(num),i);

            }else {
                if ("main".equals(list.get(i + 1).getWord()))
                    break;
                node0.addChild(new GrammarNode("函数声明"));
                i = functionDeclarations(list,node0.getChild(num),i);
            }
            i++;
            while (list.get(i).getWord().equals(";"))
                i++;
            num++;
        }
        return i;
    }
    public static boolean isValueD(List<WordA> list,int i){//真表示为值声明，假表示为函数声明
        if (list.get(i).getWord().equals("const")){
            return true;
        }else if (i + 2 < list.size() && list.get(i + 2).getWord().equals("="))
            return true;
        return false;

    }
    public static int valueDeclaration(List<WordA> list,GrammarNode node0,int i){//<值声明>→<常量声明>|<变量声明>
        int num = 0;
        if (list.get(i).getWord().equals("const")){
            node0.addChild(new GrammarNode("常量声明"));
            i = constantDeclarations(list,node0.getChild(num),i);
        }else {
            node0.addChild(new GrammarNode("变量声明"));
            i = variableDeclarations(list,node0.getChild(num),i);
        }
        return i;
    }
    public static int variableDeclarations(List<WordA> list,GrammarNode node0,int i){//<变量声明>→var<变量类型><变量声明表>
        int num = 0;
        node0.addChild(new GrammarNode("var"));
        node0.addChild(new GrammarNode("变量类型"));
        num++;
        i = variableType(list,node0.getChild(num),i);
        node0.addChild(new GrammarNode("变量声明表"));
        num++;
        i = variableDeclarationTable(list,node0.getChild(num),i);
        num++;
        return i;
    }
    public static int variableDeclarationTable(List<WordA> list,GrammarNode node0,int i){//<变量声明表>→<单变量声明> <变量声明表0>
        int num = 0;
        node0.addChild(new GrammarNode("单变量声明"));
        i = univariateDeclarations(list,node0.getChild(num),i);
        node0.addChild(new GrammarNode("变量声明表0"));
        num++;
        i = variableDeclarationTable0(list,node0.getChild(num),i);
        return i;
    }
    public static int variableDeclarationTable0(List<WordA> list,GrammarNode node0,int i){//<变量声明表0>→;|,<变量声明表>
        int num = 0;
        if (list.get(i).getWord().equals(";")){
            node0.addChild(new GrammarNode(";"));
            i++;
            return i;
        }
        node0.addChild(new GrammarNode(","));
        node0.addChild(new GrammarNode("变量声明表"));
        num++;
        i++;
        i = variableDeclarationTable(list,node0.getChild(num),i);
        return i;
    }
    public static  int univariateDeclarations(List<WordA> list,GrammarNode node0,int i){//<单变量声明>→<变量><单变量声明0>
        int num = 0;
        node0.addChild(new GrammarNode("变量"));
        i = variable(list,node0.getChild(num),i);
        node0.addChild(new GrammarNode("单变量声明0"));
        num++;
        i = univariateDeclarations0(list,node0.getChild(num),i);
        return i;
    }
    public static  int univariateDeclarations0(List<WordA> list,GrammarNode node0,int i){//<单变量声明0>→=<布尔表达式>|ε
        int num = 0;
        if (!list.get(i).getWord().equals("=")){
            return i;
        }
        node0.addChild(new GrammarNode("="));
        i++;
        node0.addChild(new GrammarNode("布尔表达式"));
        num++;
        i = booleanExpression(list,node0.getChild(num),i);
        num++;


        return i;
    }
    public static int booleanExpression(List<WordA> list,GrammarNode node0,int i){//<布尔表达式>→<布尔项><布尔表达式0>
        int num = 0;
        node0.addChild(new GrammarNode("布尔项"));
        i = booleanItems(list,node0.getChild(num),i);
        node0.addChild(new GrammarNode("布尔表达式0"));
        num++;
        i = booleanExpression0(list,node0.getChild(num),i);
        return i;
    }
    public static int booleanExpression0(List<WordA> list,GrammarNode node0,int i){//<布尔表达式0>→||<布尔项><布尔表达式0>|ε
        int num = 0;
        if (list.get(i).getWord().equals("||")){
            return i;
        }
        return i;
    }
    public static int booleanItems(List<WordA> list,GrammarNode node0,int i){//<布尔项>→<布尔因子><布尔项0>
        int num = 0;
        node0.addChild(new GrammarNode("布尔因子"));
        i = booleanFactor(list,node0.getChild(num),i);
        node0.addChild(new GrammarNode("布尔项0"));
        num++;
        i = booleanItems0(list,node0.getChild(num),i);
        return i;
    }
    public static int booleanItems0(List<WordA> list,GrammarNode node0,int i){//<布尔项0>→&&<布尔因子><布尔项0>|ε
        int num = 0;
        return i;
    }
    public static int booleanFactor(List<WordA> list,GrammarNode node0,int i){//<布尔因子>→<算术表达式><布尔因子0>
        int num = 0;
        node0.addChild(new GrammarNode("算术表达式"));
        i = arithmeticExpressions(list,node0.getChild(num),i);
        node0.addChild(new GrammarNode("布尔因子0"));
        num++;
        i = booleanFactor0(list,node0.getChild(num),i);
        return i;
    }
    public static int booleanFactor0(List<WordA> list,GrammarNode node0,int i){//<布尔因子0>→<关系运算符><算术表达式>|ε
        int num = 0;
        if (list.get(i).getWord().equals("<") || list.get(i).getWord().equals(">") || list.get(i).getWord().equals("<=") || list.get(i).getWord().equals(">=")
        ||list.get(i).getWord().equals("=") || list.get(i).getWord().equals("==")){
            node0.addChild(new GrammarNode("关系运算符"));
            i = relationalOperators(list,node0.getChild(num),i);
            node0.addChild(new GrammarNode("算术表达式"));
            num++;
            i = arithmeticExpressions(list,node0.getChild(num),i);
            return i;
        }
        return i;

    }
    public static int relationalExpressions(List<WordA> list,GrammarNode node0,int i){//<关系表达式>→<算术表达式><关系运算符><算术表达式>
        int num = 0;
        node0.addChild(new GrammarNode("算术表达式"));
        i = arithmeticExpressions(list,node0.getChild(num),i);
        node0.addChild(new GrammarNode("关系运算符"));
        num++;
        i = relationalOperators(list,node0.getChild(num),i);
        node0.addChild(new GrammarNode("算数表达式"));
        i = arithmeticExpressions(list,node0.getChild(num),i);
        return i;
    }
    public static int relationalOperators(List<WordA> list,GrammarNode node0,int i){//<关系运算符>→>|<|>=|<=|==|!=
        int num = 0;
        node0.addChild(new GrammarNode(list.get(i).getWord()));
        i++;
        return i;

    }
    public static int variableType(List<WordA> list,GrammarNode node0,int i){//<变量类型>→int|char|float
        int num = 0;
        node0.addChild(new GrammarNode(list.get(i).getWord()));
        i++;
        return i;
    }
    public static  int arithmeticExpressions(List<WordA> list,GrammarNode node0,int i){//<算术表达式>→<项><算术表达式 0>
        int num = 0;
        node0.addChild(new GrammarNode("项"));
        i = item(list,node0.getChild(num),i);
        node0.addChild(new GrammarNode("算术表达式0"));
        num++;
        i = arithmeticExpressions0(list,node0.getChild(num),i);
        return  i;
    }

    public static  int arithmeticExpressions0(List<WordA> list,GrammarNode node0,int i){//<算术表达式 0>-> +<项><算术表达式 0>|-<项><算术表达式 0>|ε
        int num = 0;
        if (list.get(i).getWord().equals("+") || list.get(i).getWord().equals("-") ){
            node0.addChild(new GrammarNode(list.get(i).getWord()));
            node0.addChild(new GrammarNode("项"));
            num++;
            i++;
            i = item(list,node0.getChild(num),i);
            node0.addChild(new GrammarNode("算术表达式0"));
            num++;
            i = arithmeticExpressions0(list,node0.getChild(num),i);
            return i;
        }
        return i;
    }
    public static  int item(List<WordA> list,GrammarNode node0,int i){ //<项>-><因子><项 0>
        int num = 0;
        node0.addChild(new GrammarNode("因子"));
        i = factor(list,node0.getChild(num),i);
        node0.addChild(new GrammarNode("项0"));
        num++;
        i = item0(list,node0.getChild(num),i);
        return i;
    }
    public static  int item0(List<WordA> list,GrammarNode node0,int i){// <项 0>->*<因子><项 0>|/<因子><项 0>|%<因子><项 0>|ε
        int num = 0;
        if (list.get(i).getWord().equals("*") || list.get(i).getWord().equals("/") || list.get(i).getWord().equals("%")){
            node0.addChild(new GrammarNode(list.get(i).getWord()));
            node0.addChild(new GrammarNode("因子"));
            num++;
            i++;
            i = factor(list,node0.getChild(num),i);
            node0.addChild(new GrammarNode("项0"));
            num++;
            i = item0(list,node0.getChild(num),i);
            return i;
        }

        return i;
    }
    public static int factor(List<WordA> list,GrammarNode node0,int i){//<因子>->(<算术表达式>)|<常量>|<变量>｜<函数调用>|<因子0>
        int num = 0;
        if (list.get(i).getWord().equals("(")){
            node0.addChild(new GrammarNode("("));
            i++;
            node0.addChild(new GrammarNode("算术表达式"));
            num++;
            i = arithmeticExpressions(list,node0.getChild(num),i);
            node0.addChild(new GrammarNode(")"));
            i++;
            num++;
            return i;
        }if(isConstant(list,i)){
            node0.addChild(new GrammarNode("常量"));
            i = constant(list,node0.getChild(num),i);
            return i;
        }else if(isVar(list,i)){
            node0.addChild(new GrammarNode("变量"));
            i = variable(list,node0.getChild(num),i);
            return i;
        }else if (isFun(list,i)){
            node0.addChild(new GrammarNode("函数调用"));
            return i;
        }else {
            node0.addChild(new GrammarNode("因子0"));
            i = factor0(list,node0.getChild(num),i);
            return i;
        }

    }
    public static int factor0(List<WordA> list,GrammarNode node0,int i){//<因子0>→+<因子>|-<因子>|!<因子>
        int num = 0;
        node0.addChild(new GrammarNode(list.get(i).getWord()));
        i++;
        i = factor(list,node0.getChild(num),i);
        return i;
    }
    public static boolean isConstant(List<WordA> list,int i){//真代表为常量
        String str = list.get(i).getWord();

        return list.get(i).getToken() == 800 || list.get(i).getToken() == 406 || list.get(i).getToken() == 71;
    }

    public static boolean isVar(List<WordA> list,int i){//真代表为变量
        int token = list.get(i).getToken();

        return token == 700;
    }
    public static boolean isFun(List<WordA> list,int i){//真代表为函数
        if (i + 1 <list.size() && list.get(i).getWord().equals("("))
            return true;

        return false;
    }
    public static int functionDeclarations(List<WordA> list,GrammarNode node0,int i){//<函数声明>→<函数类型><变量>(<函数声明形参列表>)
        int num = 0;
        node0.addChild(new GrammarNode("函数类型"));
        i = functionType(list,node0.getChild(num),i);
        node0.addChild(new GrammarNode("变量"));
        num++;
        i = variable(list,node0.getChild(num),i);
        node0.addChild(new GrammarNode("("));
        num++;
        node0.addChild(new GrammarNode("函数声明形参列表"));
        num++;
        i++;
        i = listOfFunctionDeclarationParameters(list,node0.getChild(num),i);
        node0.addChild(new GrammarNode(")"));
        num++;
        i++;
        return i;
    }
    public static int functionType(List<WordA> list,GrammarNode node0,int i){//<函数类型>→int|char|float|void
        int num = 0;
        if (list.get(i).getWord().equals("void")){
            node0.addChild(new GrammarNode("void"));
            i++;
            return i;
        }
        node0.addChild(new GrammarNode(list.get(i).getWord()));
        i++;
        return i;
    }
    public static int listOfFunctionDeclarationParameters(List<WordA> list,GrammarNode node0,int i){//<函数声明形参列表>→<函数声明形参>|ε
        int num = 0;
        if ( i  < list.size() && list.get(i  ).getWord().equals(")")){
            return i;
        }
        node0.addChild(new GrammarNode("函数声明形参"));
        i = functionDeclarationParameters(list,node0.getChild(num),i);
        return i;
    }
    public static int functionDeclarationParameters(List<WordA> list,GrammarNode node0,int i){//<函数声明形参>→<变量类型><函数声明形参0>
        int num = 0;
        node0.addChild(new GrammarNode("变量类型"));
        i = variableType(list,node0.getChild(num),i);
        node0.addChild(new GrammarNode("函数声明形参0"));
        num++;
        i = functionDeclarationParameters0(list,node0.getChild(num),i);
        return i;
    }
    public static int functionDeclarationParameters0(List<WordA> list,GrammarNode node0,int i){//<函数声明形参0>→,<函数声明形参>|ε
        int num = 0;
        if ( list.size() > i &&list.get(i  ).getWord().equals(")")){
            return i;
        }
        node0.addChild(new GrammarNode(","));
        node0.addChild(new GrammarNode("函数声明形参"));
        num++;
        i++;
        i = functionDeclarationParameters(list,node0.getChild(num),i);
        return i;
    }
    public static int constantDeclarations(List<WordA> list,GrammarNode node0,int i){//<常量声明>→const<常量类型><常量声明表>
        int num = 0;
        node0.addChild(new GrammarNode("const"));
        i++;
        num++;
        node0.addChild(new GrammarNode("常量类型"));
        i = constantType(list,node0.getChild(num),i);
        i++;
        num++;
        node0.addChild(new GrammarNode("常量声明表"));
        i = constantDeclarationTable(list,node0.getChild(num),i);
//        i++;
        return i;
    }
    public static int constantType(List<WordA> list,GrammarNode node0,int i) {//<常量类型>→int|char|float
        if (list.get(i).getWord().equals("int")){
            node0.addChild(new GrammarNode("int"));
        }else if (list.get(i).getWord().equals("float")){
            node0.addChild(new GrammarNode("float"));
        }else if (list.get(i).getWord().equals("char")){
            node0.addChild(new GrammarNode("char"));
        }
        return i;
    }
    public static int constantDeclarationTable(List<WordA> list, GrammarNode node0, int i) {//<常量声明表>→<变量>=<常量声明表0>
        int num = 0;
        node0.addChild(new GrammarNode("变量"));
        i = variable(list,node0.getChild(num),i);
        num++;//  =1

        node0.addChild(new GrammarNode("="));
        num++;
        i++;

        node0.addChild(new GrammarNode("常量声明表0"));
        i = constantDeclarationTable0(list,node0.getChild(num),i);
//        i++;
        return i;
    }
    public static int variable(List<WordA> list,GrammarNode node0,int i) {//<变量>→identifier

        node0.addChild(new GrammarNode(list.get(i).getWord()));
        i++;
        return i;
    }
    public static int constantDeclarationTable0(List<WordA> list,GrammarNode node0,int i){//<常量声明表0>→ <常量声明表值><常量声明表1>
        int num = 0;
        node0.addChild(new GrammarNode("常量声明表值"));
        i =constantsDeclareTableValues(list, node0.getChild(num),i);
        i++;
        num++;
        node0.addChild(new GrammarNode("常量声明表1"));
        i = constantDeclarationTable1(list,node0.getChild(num),i);
        return i;
    }
    public static int constantDeclarationTable1(List<WordA> list,GrammarNode node0,int i){//<常量声明表1>→ ;|,常量声明表
        int num = 0;
        if (list.get(i).getWord().equals(";")){
            node0.addChild(new GrammarNode(";"));
            return i;
        }else {
            node0.addChild(new GrammarNode(",常量声明表"));
            i =constantDeclarationTable(list, node0.getChild(num),i);
        }
        return i;
    }

    public static  int constantsDeclareTableValues(List<WordA> list,GrammarNode node0,int i) {//<常量声明表值>→<变量>|<常量>
        if (variablesOrConstants(list.get(i))){//变量
            int num = 0;
            node0.addChild(new GrammarNode("变量"));
            i = variable(list,node0.getChild(num),i);
        }else {//常量
            int num = 0;
            node0.addChild(new GrammarNode("常量"));
            i = constant(list,node0.getChild(num),i);
        }
        return i;
    }
    public static boolean variablesOrConstants(WordA wordA){//真代表变量，假代表常量
        return wordA.getToken() == 700;
    }
    public static int constant(List<WordA> list,GrammarNode node0,int i) {//<常量>→<数值型常量>|<字符型常量>
        if (numbersOrCharacters(list.get(i))){
            int num = 0;
            node0.addChild(new GrammarNode("数值型常量"));
            i = numericConstants(list,node0.getChild(num),i);
        }else {
            int num = 0;
            node0.addChild(new GrammarNode("字符型常量"));
            i = characterConstants(list,node0.getChild(num),i);
        }
        return i;
    }
    public static boolean numbersOrCharacters(WordA wordA) {//真代表数值常量，假代表字符型常量
        return !(wordA.getToken() == 406);
    }
    public static int numericConstants(List<WordA> list,GrammarNode node0,int i) {//<数值型常量>→integer|floatnumber

        node0.addChild(new GrammarNode(list.get(i).getWord()));
        i++;
        return i;
    }public static int characterConstants(List<WordA> list,GrammarNode node0,int i) {//<字符型常量>→character
        node0.addChild(new GrammarNode(list.get(i).getWord()));
        i++;
        return i;
    }

}
