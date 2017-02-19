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

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
