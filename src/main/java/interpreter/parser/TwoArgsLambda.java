package interpreter.parser;

import interpreter.expression.NumberExpression;

public class TwoArgsLambda {
    private final String firstIdentifier;
    private final String secondIdentifier;
    private final NumberExpression expression;

    public TwoArgsLambda(NumberExpression expression, String firstIdentifier, String secondIdentifier) {
        this.firstIdentifier = firstIdentifier;
        this.secondIdentifier = secondIdentifier;
        this.expression = expression;
    }

    public String getFirstIdentifier() {
        return firstIdentifier;
    }

    public String getSecondIdentifier() {
        return secondIdentifier;
    }

    public NumberExpression getException() {
        return expression;
    }
}
