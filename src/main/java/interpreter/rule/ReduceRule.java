package interpreter.rule;


import interpreter.Context;
import interpreter.error.ParserError;
import interpreter.expression.Expression;
import interpreter.expression.NumberExpression;
import interpreter.expression.ReduceExpression;
import interpreter.expression.SequenceExpression;
import interpreter.parser.Parser;
import interpreter.parser.TwoArgsLambda;

import static interpreter.Util.emptyNumberExpression;
import static interpreter.Util.numberOfChars;


/**
 * Rule for 'reduce' function recognition.
 * <p/>
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
        ReduceExpression expression = parseReduceExpression(args, context, parser);
        if (expression != null) {
            return expression;
        } else {
            if (!context.hasErrors()) {
                context.addError(new ParserError("Invalid syntax for reduce function"));
            }
            return emptyNumberExpression();
        }
    }

    private static ReduceExpression parseReduceExpression(String args, Context context, Parser parser) {
        int firstPosition = 0;
        if(numberOfChars(args, ',') < 2) {
            return null;
        }
        Context localContext = new Context(context);
        while ((firstPosition = args.indexOf(",", firstPosition + 1)) != -1) {
            localContext.clearExceptions();
            String newLine = args.substring(0, firstPosition);
            SequenceExpression sequence = parser.sequenceExpression(localContext, newLine);
            if (!localContext.hasErrors()) {
                int secondPosition = firstPosition;
                while ((secondPosition = args.indexOf(",", secondPosition + 1)) != -1) {
                    localContext.clearExceptions();
                    newLine = args.substring(firstPosition + 1, secondPosition);
                    NumberExpression expression = parser.numberExpression(localContext, newLine);
                    if (!localContext.hasErrors()) {
                        newLine = args.substring(secondPosition + 1, args.length());
                        TwoArgsLambda lambda = parser.parseTwoArgsLambda(localContext, newLine);
                        if (!localContext.hasErrors()) {
                            return new ReduceExpression(sequence, expression,
                                    lambda.getFirstIdentifier(), lambda.getSecondIdentifier(), lambda.getException());
                        } else {
                            context.addErrors(localContext.getErrors());
                            firstPosition = args.length();
                            secondPosition = args.length();
                        }
                    } else if (localContext.hasUndefinedVariableError()) {
                        context.addErrors(localContext.getErrors());
                        firstPosition = args.length();
                        secondPosition = args.length();
                    }
                }
            } else if (localContext.hasIncompatibleParseError() || localContext.hasUndefinedVariableError()) {
                context.addErrors(localContext.getErrors());
                firstPosition = args.length();
            }
        }
        return null;
    }
}
