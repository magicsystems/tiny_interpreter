package interpreter.expression;


import interpreter.Context;

public interface Expression {
    double value(Context context);
}
