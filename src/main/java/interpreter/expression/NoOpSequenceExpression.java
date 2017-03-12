package interpreter.expression;

import interpreter.Context;

import java.util.stream.DoubleStream;


public class NoOpSequenceExpression implements SequenceExpression {

    @Override
    public DoubleStream stream(Context context) {
        return DoubleStream.empty();
    }

    @Override
    public String toString() {
        return "No Op Sequence Expression";
    }
}
