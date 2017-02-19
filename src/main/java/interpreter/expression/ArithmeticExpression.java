package interpreter.expression;


import interpreter.Context;


public class ArithmeticExpression implements NumberExpression {
    private final NumberExpression left;
    private final NumberExpression right;
    private final Operation operation;

    public ArithmeticExpression(NumberExpression left, NumberExpression right, Operation operation) {
        this.left = left;
        this.right = right;
        this.operation = operation;
    }

    @Override
    public double value(Context context) {
        switch (operation) {
            case ADD:
                return left.value(context) + right.value(context);
            case SUB:
                return left.value(context) - right.value(context);
            case MULT:
                return left.value(context) * right.value(context);
            case DIV:
                return left.value(context) / right.value(context);
            case POW:
                return Math.pow(left.value(context), right.value(context));

        }
        throw new IllegalStateException("Unsupported Operation " + operation + " in Arithmetic Exception");
    }

    public String toString() {
        return "("+left.toString() + " " + operation + " " + right.toString() + ")";
    }

}
