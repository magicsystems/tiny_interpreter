package interpreter.expression;

import interpreter.Context;


public class Identifier implements NumberExpression {
    private final String name;

    public Identifier(String name) {
        this.name = name;
    }

    @Override
    public double value(Context context) {
        return context.getValue(name);
    }

    @Override
    public double lambdaValue(Object[] context) {
        for (int i = 0; i < context.length; i += 2) {
            if (context[i].equals(name)) {
                return (Double) context[i + 1];
            }
        }
        return 0;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
