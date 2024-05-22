package jieshi;

import java.util.*;

public class Test4 {
}
class IntermediateCodeInterpreter {
    private Map<String, Integer> variables = new HashMap<>();
    private List<String> instructions;
    private int instructionPointer = 0;
    private Stack<Integer> callStack = new Stack<>();

    public IntermediateCodeInterpreter(List<String> instructions) {
        this.instructions = instructions;

    }

    public void execute() {
        while (instructionPointer < instructions.size()) {
            String instruction = instructions.get(instructionPointer).trim();
            interpret(instruction);
            instructionPointer++;
        }
    }

    private void interpret(String instruction) {
        String[] parts = instruction.split("\\s+");
        String operation = parts[1];

        switch (operation) {
            case "=":
                handleAssignment(parts);
                break;
            case "call":
                handleFunctionCall(parts);
                break;
            case "para":
                handleParameter(parts);
                break;
            case "sys":
                handleSysCall();
                break;
            case "+":
                handleAddition(parts);
                break;
            case ">=":
                handleComparison(parts);
                break;
            case "jz":
                handleJumpIfZero(parts);
                break;
            case "j":
                handleJump(parts);
                break;
            case "ret":
                handleReturn(parts);
                break;
            default:
                break;
        }
    }

    private void handleAssignment(String[] parts) {
        String target = parts[parts.length  - 1];
        String value = parts[2];
        variables.put(target, getValue(value));
    }

    private void handleFunctionCall(String[] parts) {
        String functionName = parts[2];
        switch (functionName) {
            case "read":
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter value: ");
                int input = scanner.nextInt();
                variables.put(parts[parts.length  - 1], input);
                break;
            case "write":
                System.out.println("Output: " + callStack.get(0));
                break;

            default:
                callFunction(functionName);
                break;
        }
    }

    private void handleParameter(String[] parts) {
        // Parameters are typically pushed onto the stack for function calls
        String param = parts[2];
        callStack.push(getValue(param));
    }

    private void handleSysCall() {
        // Handle system calls
    }

    private void handleAddition(String[] parts) {
        String target = parts[0];
        int left = getValue(parts[2]);
        int right = getValue(parts[3]);
        variables.put(target, left + right);
    }

    private void handleComparison(String[] parts) {
        String target = parts[parts.length  - 1];
        int left = getValue(parts[2]);
        int right = getValue(parts[3]);
        variables.put(target, Math.max(left, right));

    }

    private void handleJumpIfZero(String[] parts) {
        String condition = parts[2];
        int targetLine = Integer.parseInt(parts[3]);
        if (getValue(condition) == 0) {
            instructionPointer = targetLine - 2; // Adjust for zero-based indexing
        }
    }

    private void handleJump(String[] parts) {
        int targetLine = Integer.parseInt(parts[2]);
        instructionPointer = targetLine - 2; // Adjust for zero-based indexing
    }

    private void handleReturn(String[] parts) {
        if (parts.length > 2) {
            String returnValue = parts[2];
            System.out.println("Return value: " + getValue(returnValue));
        }
        // Simulate return from function
    }

    private void callFunction(String functionName) {
        // Simulate function call, adjust instruction pointer to function entry point
        if ("sum".equals(functionName)) {
            instructionPointer = 16 - 1; // Function entry point for "sum"
        } else if ("max".equals(functionName)) {
            instructionPointer = 21 - 1; // Function entry point for "max"
        }
    }

    private int getValue(String operand) {
        if (operand.startsWith("$")) {
            return variables.getOrDefault(operand, 0);
        } else if (Character.isDigit(operand.charAt(0))) {
            return Integer.parseInt(operand);
        } else {
            return variables.getOrDefault(operand, 0);
        }
    }

    public static void main(String[] args) {
        List<String> code = Arrays.asList(
                "0 main",
                "1 = 1 a",
                "2 call read $___t0",
                "3 = $___t0 N",
                "4 call read $___t1",
                "5 = $___t1 M",
                "6 >= M N   $___t2",
                " 7       jz   $___t2\t         \t       10",
                " 8\t        =\t        M\t         \t   result",
                "  9\t        j\t         \t         \t       11",
                " 10\t        =\t        N\t         \t   result",
                " 11\t        +\t   result\t      100\t   $___t3",
                " 12\t        =\t   $___t3\t         \t        a",
                " 13\t     para\t        a\t         \t         ",
                " 14\t     call\t    write\t         \t   $___t4",
                "15 sys"
        );

        IntermediateCodeInterpreter interpreter = new IntermediateCodeInterpreter(code);
        interpreter.execute();
    }
}

//class IntermediateCodeInterpreter {
//    private Map<String, Integer> variables = new HashMap<>();
//    private List<String[]> instructions;
//    private int instructionPointer = 0;
//    private Scanner scanner = new Scanner(System.in);
//
//    public IntermediateCodeInterpreter(List<String[]> instructions) {
//        this.instructions = instructions;
//    }
//
//    public void execute() {
//        while (instructionPointer < instructions.size()) {
//            String[] instruction = instructions.get(instructionPointer);
//            interpret(instruction);
//            instructionPointer++;
//        }
//    }
//
//    private void interpret(String[] instruction) {
//        String operation = instruction[0];
//
//        switch (operation) {
//            case "=":
//                handleAssignment(instruction);
//                break;
//            case "call":
//                handleFunctionCall(instruction);
//                break;
//            case "para":
//                handleParameter(instruction);
//                break;
//            case "sys":
//                handleSysCall();
//                break;
//            case "+":
//                handleAddition(instruction);
//                break;
//            case ">=":
//                handleComparison(instruction);
//                break;
//            case "jz":
//                handleJumpIfZero(instruction);
//                break;
//            case "j":
//                handleJump(instruction);
//                break;
//            default:
//                break;
//        }
//    }
//
//    private void handleAssignment(String[] instruction) {
//        String target = instruction[3];
//        String value = instruction[1];
//        variables.put(target, getValue(value));
//    }
//
//    private void handleFunctionCall(String[] instruction) {
//        String functionName = instruction[1];
//        switch (functionName) {
//            case "read":
//                System.out.print("Enter value: ");
//                int input = scanner.nextInt();
//                variables.put(instruction[3], input);
//                break;
//            case "write":
//                System.out.println("Output: " + getValue(instruction[3]));
//                break;
//            default:
//                // Handle other function calls if necessary
//                break;
//        }
//    }
//
//    private void handleParameter(String[] instruction) {
//        // Handle parameter passing if necessary
//    }
//
//    private void handleSysCall() {
//        // Handle system calls if necessary
//    }
//
//    private void handleAddition(String[] instruction) {
//        String target = instruction[3];
//        int left = getValue(instruction[1]);
//        int right = getValue(instruction[2]);
//        variables.put(target, left + right);
//    }
//
//    private void handleComparison(String[] instruction) {
//        String target = instruction[3];
//        int left = getValue(instruction[1]);
//        int right = getValue(instruction[2]);
//        variables.put(target, left >= right ? 1 : 0);
//    }
//
//    private void handleJumpIfZero(String[] instruction) {
//        String condition = instruction[1];
//        int targetLine = Integer.parseInt(instruction[2]);
//        if (getValue(condition) == 0) {
//            instructionPointer = targetLine - 1; // Adjust for zero-based indexing
//        }
//    }
//
//    private void handleJump(String[] instruction) {
//        int targetLine = Integer.parseInt(instruction[1]);
//        instructionPointer = targetLine - 1; // Adjust for zero-based indexing
//    }
//
//    private int getValue(String operand) {
//        if (operand.startsWith("$")) {
//            return variables.getOrDefault(operand, 0);
//        } else if (Character.isDigit(operand.charAt(0))) {
//            return Integer.parseInt(operand);
//        } else {
//            return variables.getOrDefault(operand, 0);
//        }
//    }
//
//    public static void main(String[] args) {
//        List<String[]> code = Arrays.asList(
//                new String[]{"0", "main", "_", "_"},
//                new String[]{"1", "=", "1", "a"},
//                new String[]{"2", "call", "read", "$___t0"},
//                new String[]{"3", "=", "$___t0", "N"},
//                new String[]{"4", "call", "read", "$___t1"},
//                new String[]{"5", "=", "$___t1", "M"},
//                new String[]{"6", ">=", "M", "N", "$___t2"},
//                new String[]{"7", "jz", "$___t2", "10"},
//                new String[]{"8", "=", "M", "result"},
//                new String[]{"9", "j", "11"},
//                new String[]{"10", "=", "N", "result"},
//                new String[]{"11", "+", "result", "100", "$___t3"},
//                new String[]{"12", "=", "$___t3", "a"},
//                new String[]{"13", "para", "a", "_"},
//                new String[]{"14", "call", "write", "$___t4"},
//                new String[]{"15", "sys", "_", "_"}
//        );
//
//        IntermediateCodeInterpreter interpreter = new IntermediateCodeInterpreter(code);
//        interpreter.execute();
//    }
//}
