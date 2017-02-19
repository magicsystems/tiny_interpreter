package interpreter.rule;

import interpreter.Context;
import interpreter.expression.Expression;
import interpreter.expression.MapExpression;
import interpreter.expression.SequenceExpression;
import interpreter.parser.OneArgLambda;
import interpreter.parser.Parser;
import interpreter.error.ParserError;

import static interpreter.Util.emptyExpression;

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
        while ((position = args.indexOf(",", position + 1)) != -1) {
            context.clearExceptions();
            String newLine = args.substring(0, position);
            SequenceExpression sequence = parser.sequenceExpression(context, newLine);
            if (!context.hasException()) {
                newLine = args.substring(position + 1, args.length());
                OneArgLambda lambda = parser.parseOneArgLambda(context, newLine);
                if (!context.hasException()) {
                    return new MapExpression(sequence, lambda.getIdentifier(), lambda.getExpression());
                }
            } else if (context.hasIncompatibleParseError()) {
                position = args.length();
            }
        }
        if (!context.hasException()) {
            context.addException(new ParserError("Invalid syntax for map function '" + line + "'"));
        }
        return emptyExpression();
    }

}
