package interpreter.rule;

import interpreter.Context;
import interpreter.expression.Expression;
import interpreter.expression.MapExpression;
import interpreter.expression.SequenceExpression;
import interpreter.parser.OneArgLambda;
import interpreter.parser.Parser;
import interpreter.error.ParserError;

import static interpreter.Util.emptyExpression;
import static interpreter.Util.emptySequence;

/**
 * Rule for 'map' function recognition.
 * <p>
 * Signature: map(expr, identifier -> expr)
 */
public class MapRule implements Rule {
    private static final String START = "map(";
    private static final String END = ")";

    @Override
    public boolean couldBeApplied(String line) {
        return line.startsWith(START) && line.endsWith(END);
    }

    @Override
    public Expression parse(Parser parser, String line, Context context) {
        String args = line.substring(START.length(), line.length() - END.length());
        int position = 0;
        Context localContext = new Context(context);
        while ((position = args.indexOf(",", position + 1)) != -1) {
            localContext.clearExceptions();
            String newLine = args.substring(0, position);
            SequenceExpression sequence = parser.sequenceExpression(localContext, newLine);
            if (!localContext.hasException()) {
                newLine = args.substring(position + 1, args.length());
                OneArgLambda lambda = parser.parseOneArgLambda(localContext, newLine);
                if (!localContext.hasException()) {
                    return new MapExpression(sequence, lambda.getIdentifier(), lambda.getExpression());
                } else {
                    position = args.length();
                }
            } else if (localContext.hasIncompatibleParseError()) {
                position = args.length();
            }
        }
        if(localContext.hasException()) {
            context.addException(localContext.getExceptions().get(0));
        } else {
            context.addException(new ParserError("Invalid syntax for map function '" + line + "'"));
        }
        return emptySequence();
    }

}
