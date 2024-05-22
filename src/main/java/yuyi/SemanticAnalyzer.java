//package yuyi;
//
//import entity.LexT;
//import entity.WordA;
//
//import java.util.*;
//
//import static lexical.analysis.Tests1.lineAy;
//
//public class SemanticAnalyzer {
//
//    public static void main(String[] args) {
//        // 假设从某个源代码解析成了词法单元（Token）
//        String str1 = "void main()\n" +
//                "{\n" +
//                "if(a>=10){a=5;}\n" +
//                "\n" +
//                "else {a=1+3;}\n" +
//                "\n" +
//                "int a = 10;   \n" +
//                "return 0;\n" +
//                "}";
//        LexT contents = lineAy(str1);
//        List<WordA> tokens = contents.words;
//
//        // 进行语义分析
//        SemanticAnalyzer analyzer = new SemanticAnalyzer();
//        analyzer.analyze(tokens);
//    }
//    private SymbolTable symbolTable;
//
//    public SemanticAnalyzer() {
//        symbolTable = new SymbolTable();
//    }
//
//    public void analyze(List<WordA> tokens) {
//        for (WordA token : tokens) {
//            switch (token.getToken()) {
//                case 1: // 假设 1 代表 int、char、float 关键字
//                case 2: // 假设 2 代表常量声明关键字
//                    handleDeclaration(tokens, token);
//                    break;
//                case 700: // 假设 3 代表标识符
//                    handleUsage(tokens, token);
//                    break;
//                // 添加更多的case来处理其他语义检查
//                default:
//                    break;
//            }
//        }
//
//        checkUnusedVariables();
//        checkMainFunction();
//    }
//
//    private void handleDeclaration(List<WordA> tokens, WordA token) {
//        String type = token.getWord();
//        String name = tokens.get(tokens.indexOf(token) + 1).getWord();
//        if (tokens.get(tokens.indexOf(token) + 2).getWord().equals("(")) {
//            // 函数声明或定义
//            if (symbolTable.isSymbolDeclared(name)) {
//                System.out.println("Error: " + name + " has already been declared.");
//            } else {
//                symbolTable.addFunctionDeclaration(name, type);
//                if (tokens.get(tokens.indexOf(token) + 3).getWord().equals(")")) {
//                    if (tokens.get(tokens.indexOf(token) + 4).getWord().equals(";")) {
//                        System.out.println("Function declared: " + type + " " + name);
//                    } else {
//                        System.out.println("Function defined: " + type + " " + name);
//                        symbolTable.addFunctionDefinition(name);
//                    }
//                }
//            }
//        } else {
//            // 变量声明
//            if (symbolTable.isSymbolDeclared(name)) {
//                System.out.println("Error: " + name + " has already been declared.");
//            } else {
//                symbolTable.addVariable(name, type);
//                System.out.println("Variable declared: " + type + " " + name);
//            }
//        }
//    }
//
//    private void handleUsage(List<WordA> tokens, WordA token) {
//        String identifier = token.getWord();
//        if (!symbolTable.isSymbolDeclared(identifier)) {
//            System.out.println("Error: " + identifier + " has not been declared.");
//        } else {
//            symbolTable.markVariableAsUsed(identifier);
//        }
//    }
//
//    private void checkUnusedVariables() {
//        Set<String> unusedVariables = symbolTable.getUnusedVariables();
//        for (String var : unusedVariables) {
//            System.out.println("Warning: Variable " + var + " declared but not used.");
//        }
//    }
//
//    private void checkMainFunction() {
//        if (!symbolTable.isFunctionDefined("main")) {
//            System.out.println("Error: No main function defined.");
//        }
//    }
//    private void handleExpression(List<WordA> tokens, WordA token) {
//        String operator = token.getWord();
//        if (operator.equals("%")) {
//            WordA leftOperand = tokens.get(tokens.indexOf(token) - 1);
//            WordA rightOperand = tokens.get(tokens.indexOf(token) + 1);
//            if (!isIntegerType(leftOperand) || !isIntegerType(rightOperand)) {
//                System.out.println("Error: Operands of % must be of integer type.");
//            }
//        }
//    }
//
//    private boolean isIntegerType(WordA token) {
//        Symbol symbol = symbolTable.getSymbol(token.getWord());
//        return symbol != null && symbol.getType().equals("int");
//    }
//
//    private void handleAssignment(List<WordA> tokens, WordA token) {
//        WordA leftOperand = tokens.get(tokens.indexOf(token) - 1);
//        if (symbolTable.isConstantDeclared(leftOperand.getWord())) {
//            System.out.println("Error: Cannot assign to constant " + leftOperand.getWord());
//        }
//    }
////    private HashMap<String, SymbolInfo> symbolTable; // 符号表
////    private HashSet<String> declaredVariables; // 已声明变量集合
////    private HashSet<String> usedVariables; // 已使用变量集合
////    private HashSet<String> declaredFunctions; // 已声明函数集合
////    private HashSet<String> definedFunctions; // 已定义函数集合
////    private boolean hasMainFunction; // 是否存在 main 函数
////
////    public SemanticAnalyzer() {
////        symbolTable = new HashMap<>();
////        declaredVariables = new HashSet<>();
////        usedVariables = new HashSet<>();
////        declaredFunctions = new HashSet<>();
////        definedFunctions = new HashSet<>();
////        hasMainFunction = false;
////    }
////
////    // 符号表中添加变量或常量
////    private void addToSymbolTable(String name, String type, boolean isFunction) {
////        symbolTable.put(name, new SymbolInfo(name, type, isFunction));
////    }
////
////    // 检查变量是否已声明
////    private boolean isVariableDeclared(String name) {
////        return declaredVariables.contains(name);
////    }
////
////    // 检查变量是否已使用
////    private boolean isVariableUsed(String name) {
////        return usedVariables.contains(name);
////    }
////
////    // 检查函数是否已声明
////    private boolean isFunctionDeclared(String name) {
////        return declaredFunctions.contains(name);
////    }
////
////    // 检查函数是否已定义
////    private boolean isFunctionDefined(String name) {
////        return definedFunctions.contains(name);
////    }
////
////    // 添加变量到符号表
////    private void addVariable(String name, String type) {
////        declaredVariables.add(name);
////        addToSymbolTable(name, type, false);
////    }
////
////    // 添加函数到符号表
////    private void addFunction(String name, String type) {
////        declaredFunctions.add(name);
////        addToSymbolTable(name, type, true);
////    }
////
////    // 检查赋值语句左边是否有常量
////    private boolean isLeftSideConstant(String name) {
////        SymbolInfo symbolInfo = symbolTable.get(name);
////        return symbolInfo != null && !symbolInfo.isFunction();
////    }
////
////    // 分析语义
////    public void analyze(List<WordA> tokens) {
////        for (WordA token : tokens) {
////            // 根据 token 类型进行相应处理
////            switch (token.getToken()) {
////                case TokenType.KEYWORD_INT:
////                case TokenType.KEYWORD_CHAR:
////                case TokenType.KEYWORD_FLOAT:
////                    String type = token.getWord();
////                    String name = tokens.get(tokens.indexOf(token) + 1).getWord();
////                    if (tokens.get(tokens.indexOf(token) + 2).getWord().equals("(")) {
////                        // 函数声明或定义
////                        if (isFunctionDeclared(name) || isVariableDeclared(name)) {
////                            System.out.println("Error: " + name + " has already been declared.");
////                        }
////                        if (tokens.get(tokens.indexOf(token) + 3).getWord().equals(")")) {
////                            addFunction(name, type);
////                            if (tokens.get(tokens.indexOf(token) + 4).getWord().equals(";")) {
////                                // 函数声明
////                                System.out.println("Function declared: " + type + " " + name);
////                            } else {
////                                // 函数定义
////                                System.out.println("Function defined: " + type + " " + name);
////                                definedFunctions.add(name);
////                            }
////                        }
////                    } else {
////                        // 变量声明
////                        if (isVariableDeclared(name)) {
////                            System.out.println("Error: " + name + " has already been declared.");
////                        }
////                        addVariable(name, type);
////                        System.out.println("Variable declared: " + type + " " + name);
////                    }
////                    break;
////                case TokenType.IDENTIFIER:
////                    String identifier = token.getWord();
////                    if (!isVariableDeclared(identifier) && !isFunctionDeclared(identifier)) {
////                        System.out.println("Error: " + identifier + " has not been declared.");
////                    } else {
////                        usedVariables.add(identifier);
////                    }
////                    break;
////
////                default:
////                    break;
////            }
////        }
////
////        // 检查未使用的变量
////        for (String variable : declaredVariables) {
////            if (!usedVariables.contains(variable)) {
////                System.out.println("Warning: Variable " + variable + " declared but not used.");
////            }
////        }
////
////        // 检查是否有 main 函数
////        if (!isFunctionDefined("main")) {
////            System.out.println("Error: No main function defined.");
////        }
////    }
////
////    // 符号信息类
////    private static class SymbolInfo {
////        private String name;
////        private String type;
////        private boolean isFunction;
////
////        public SymbolInfo(String name, String type, boolean isFunction) {
////            this.name = name;
////            this.type = type;
////            this.isFunction = isFunction;
////        }
////
////        public String getName() {
////            return name;
////        }
////
////        public String getType() {
////            return type;
////        }
////
////        public boolean isFunction() {
////            return isFunction;
////        }
////    }
//}
//
//
////class SymbolTable {
////    private Map<String, Symbol> table;
////    private List<String> declaredFunctions;
////    private List<String> definedFunctions;
////    private Set<String> usedVariables;
////
////    public SymbolTable() {
////        table = new HashMap<>();
////        declaredFunctions = new ArrayList<>();
////        definedFunctions = new ArrayList<>();
////        usedVariables = new HashSet<>();
////    }
////
////    public void addVariable(String name, String type) {
////        table.put(name, new Symbol(name, type, SymbolType.VARIABLE));
////    }
////
////    public void addConstant(String name, String type) {
////        table.put(name, new Symbol(name, type, SymbolType.CONSTANT));
////    }
////
////    public void addFunctionDeclaration(String name, String returnType) {
////        declaredFunctions.add(name);
////        table.put(name, new Symbol(name, returnType, SymbolType.FUNCTION));
////    }
////
////    public void addFunctionDefinition(String name) {
////        definedFunctions.add(name);
////    }
////
////    public boolean isVariableDeclared(String name) {
////        Symbol symbol = table.get(name);
////        return symbol != null && symbol.getSymbolType() == SymbolType.VARIABLE;
////    }
////
////    public boolean isConstantDeclared(String name) {
////        Symbol symbol = table.get(name);
////        return symbol != null && symbol.getSymbolType() == SymbolType.CONSTANT;
////    }
////
////    public boolean isFunctionDeclared(String name) {
////        return declaredFunctions.contains(name);
////    }
////
////    public boolean isFunctionDefined(String name) {
////        return definedFunctions.contains(name);
////    }
////
////    public boolean isSymbolDeclared(String name) {
////        return table.containsKey(name);
////    }
////
////    public Symbol getSymbol(String name) {
////        return table.get(name);
////    }
////
////    public void markVariableAsUsed(String name) {
////        usedVariables.add(name);
////    }
////
////    public Set<String> getUnusedVariables() {
////        Set<String> declaredVariables = new HashSet<>();
////        for (Symbol symbol : table.values()) {
////            if (symbol.getSymbolType() == SymbolType.VARIABLE) {
////                declaredVariables.add(symbol.getName());
////            }
////        }
////        declaredVariables.removeAll(usedVariables);
////        return declaredVariables;
////    }
////
////    public void setName() {
//    }
//}
//
//class Symbol {
//    private String name;
//    private String type;
//    private SymbolType symbolType;
//
//    public Symbol(String name, String type, SymbolType symbolType) {
//        this.name = name;
//        this.type = type;
//        this.symbolType = symbolType;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public SymbolType getSymbolType() {
//        return symbolType;
//    }
//}
//
//enum SymbolType {
//    VARIABLE,
//    CONSTANT,
//    FUNCTION
//}
