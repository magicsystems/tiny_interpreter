package interpreter.expression;

import interpreter.Context;


public class NoOpNumberExpression implements NumberExpression {

    @Override
    public double value(Context context) {
        return 0;
    }

    @Override
    public double lambdaValue(Object[] context) {
        return 0;
    }

    @Override
    public String toString() {
       return "No Op Number Expression";
    }
}
