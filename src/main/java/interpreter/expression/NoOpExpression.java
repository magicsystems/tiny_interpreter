package interpreter.expression;

import interpreter.Context;


public class NoOpExpression implements Expression {

    @Override
    public double value(Context context) {
        return 0;
    }

    @Override
    public String toString() {
        return "No Op expression";
    }
}
