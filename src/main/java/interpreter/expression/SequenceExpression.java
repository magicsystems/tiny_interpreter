package interpreter.expression;

import interpreter.Context;

import java.util.stream.DoubleStream;


public interface SequenceExpression extends Expression {
    DoubleStream stream(Context context);
}
