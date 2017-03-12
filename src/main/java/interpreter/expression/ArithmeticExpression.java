package interpreter.expression;


import interpreter.Context;

/**
 * Expression representation fo arithmetic formula.
 *
 * Supported  operations are values of {@link Operation} enum
 */
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
        throw new IllegalStateException("Unsupported Operation " + operation + " in value() method");
    }

    @Override
    public double lambdaValue(Object[] context) {
        switch (operation) {
            case ADD:
                return left.lambdaValue(context) + right.lambdaValue(context);
            case SUB:
                return left.lambdaValue(context) - right.lambdaValue(context);
            case MULT:
                return left.lambdaValue(context) * right.lambdaValue(context);
            case DIV:
                return left.lambdaValue(context) / right.lambdaValue(context);
            case POW:
                return Math.pow(left.lambdaValue(context), right.lambdaValue(context));
        }
        throw new IllegalStateException("Unsupported Operation " + operation + " in lambdaValue() method");
    }

    public String toString() {
        return "("+left.toString() + " " + operation + " " + right.toString() + ")";
    }

}
