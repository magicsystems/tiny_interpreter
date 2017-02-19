package interpreter.expression;

import interpreter.Context;


public class NoOpSequenceExpression implements SequenceExpression {
    boolean element = true;

    @Override
    public boolean hasNext() {
        return element;
    }

    @Override
    public void clear() {
        element = true;
    }

    @Override
    public double value(Context context) {
        element = false;
        return 0;
    }

    @Override
    public String toString() {
        return "No Op Sequence Expression";
    }
}
