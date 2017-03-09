package interpreter.expression;


import interpreter.Context;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

/**
 * SequenceExpression which uses  {@link java.util.function.Function} for production
 * next element until right border of the interval is reached
 */
public class Sequence implements SequenceExpression {
    private final NumberExpression left;
    private final NumberExpression right;

    public Sequence(NumberExpression left, NumberExpression right) {
        this.left = left;
        this.right = right;
    }
    @Override
    public DoubleStream stream(Context context) {
        int leftInt = (int)this.left.value(context);
        int rightInt = (int)this.right.value(context) + 1;
        return IntStream.range(leftInt, rightInt).parallel().mapToDouble(Double::valueOf);
    }

    @Override
    public String toString() {
        return "{" + left + ", " + right + "}";
    }
}
