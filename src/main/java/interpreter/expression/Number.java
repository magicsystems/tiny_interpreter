package interpreter.expression;

import interpreter.Context;

/**
 * Expression representation for double value
 */
public class Number implements NumberExpression {
    private final double value;

    public Number(double value) {
        this.value = value;
    }

    @Override
    public double value(Context context) {
        return value;
    }

    @Override
    public double lambdaValue(Object[] context) {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
