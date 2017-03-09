package interpreter.parser;

import interpreter.expression.NumberExpression;

public class OneArgLambda {
    private final String identifier;
    private final NumberExpression expression;

    public OneArgLambda(NumberExpression expression, String identifier) {
        this.expression = expression;
        this.identifier = identifier;
    }

    public NumberExpression getExpression() {
        return expression;
    }

    public String getIdentifier() {
        return identifier;
    }
}
