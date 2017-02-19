package interpreter.parser;

import interpreter.expression.Expression;

public class OneArgLambda {
    private final String identifier;
    private final Expression expression;

    public OneArgLambda(Expression expression, String identifier) {
        this.expression = expression;
        this.identifier = identifier;
    }

    public Expression getExpression() {
        return expression;
    }

    public String getIdentifier() {
        return identifier;
    }
}
