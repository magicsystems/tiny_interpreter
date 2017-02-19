package interpreter.rule;


import interpreter.Context;
import interpreter.expression.Expression;
import interpreter.expression.ReduceExpression;
import interpreter.expression.SequenceExpression;
import interpreter.parser.Parser;
import interpreter.parser.ParserError;
import interpreter.parser.TwoArgsLambda;

import static interpreter.Util.emptyExpression;


/**
 * Rule for 'reduce' function recognition.
 * <p>
 * Signature: reduce(sequence, expr, identifier identifier -> expr)
 */
public class ReduceRule implements Rule {

    private static final String START = "reduce(";
    private static final String END = ")";

    @Override
    public boolean couldBeApplied(String line) {
        return line.startsWith(START) && line.endsWith(END);
    }

    @Override
    public Expression parse(Parser parser, String line, Context context) {
        String args = line.substring(START.length(), line.length() - 1);
        int firstPosition = 0;
        while ((firstPosition = args.indexOf(",", firstPosition + 1)) != -1) {
            context.clearExceptions();
            String newLine = args.substring(0, firstPosition);
            SequenceExpression sequence = parser.sequenceExpression(context, newLine);
            if (!context.hasException()) {
                int secondPosition = firstPosition;
                while ((secondPosition = args.indexOf(",", secondPosition + 1)) != -1) {
                    context.clearExceptions();
                    newLine = args.substring(firstPosition + 1, secondPosition);
                    Expression expression = parser.expression(context, newLine);
                    if (!context.hasException()) {
                        newLine = args.substring(secondPosition + 1, args.length());
                        TwoArgsLambda lambda = parser.parseTwoArgsLambda(context, newLine);
                        if (!context.hasException()) {
                            return new ReduceExpression(sequence, expression,
                                    lambda.getFirstIdentifier(), lambda.getSecondIdentifier(), lambda.getException());
                        }
                    }
                }
            }
        }
        if (!context.hasException()) {
            context.addException(new ParserError("Invalid syntax for reduce function '" + line + "'"));
        }
        return emptyExpression();
    }
}
