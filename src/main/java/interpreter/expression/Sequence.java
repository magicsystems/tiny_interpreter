package interpreter.expression;


import interpreter.Context;

import java.util.function.Function;

/**
 * SequenceExpression which uses  {@link java.util.function.Function} for production
 * next element until right border of the interval is reached
 */
public class Sequence implements SequenceExpression {
    private final Expression left;
    private final Expression right;
    private final Function<Double, Double> function;

    private double currentValue = 0;
    private double lastValue = Double.MAX_VALUE;

    public Sequence(Expression left, Expression right, Function<Double, Double> function) {
        this.left = left;
        this.right = right;
        this.function = function;
    }

    @Override
    public double value(Context context) {
        if (lastValue == Double.MAX_VALUE) {
            lastValue = right.value(context);
            currentValue = left.value(context);
        } else {
            currentValue = function.apply(currentValue);
        }
        return currentValue;
    }

    @Override
    public boolean hasNext() {
        return currentValue < lastValue;
    }

    @Override
    public void clear() {
        currentValue = 0;
        lastValue = Double.MAX_VALUE;
    }

    @Override
    public String toString() {
        return "{" + left + ", " + right + "}";
    }
}
