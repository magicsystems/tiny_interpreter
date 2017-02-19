package interpreter.parser;


import interpreter.expression.Expression;

public class TwoArgsLambda {
    private final String firstIdentifier;
    private final String secondIdentifier;
    private final Expression expression;

    public TwoArgsLambda(Expression expression, String firstIdentifier, String secondIdentifier) {
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

    public Expression getException() {
        return expression;
    }
}
