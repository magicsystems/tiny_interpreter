package interpreter.expression;


import interpreter.Context;

public interface NumberExpression extends Expression {
    double value(Context context);
}
