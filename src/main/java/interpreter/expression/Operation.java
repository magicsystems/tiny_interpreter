package interpreter.expression;


public enum Operation {
    ADD('+'),
    SUB('-'),
    MULT('*'),
    DIV('/'),
    POW('^');

    private final char operation;

    Operation(char operation) {
        this.operation = operation;
    }

    public String toString() {
        return String.valueOf(operation);
    }

    public char getChar() {
        return operation;
    }
}
