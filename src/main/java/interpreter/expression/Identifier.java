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
    public double lambdaValue(Object[] array) {
        for (int i = 0; i < array.length; i += 2) {
            if (array[i].equals(name)) {
                return (Double) array[i + 1];
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
