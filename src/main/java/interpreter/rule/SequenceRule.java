package interpreter.rule;


import interpreter.Context;
import interpreter.expression.Expression;
import interpreter.expression.NumberExpression;
import interpreter.expression.Sequence;
import interpreter.parser.Parser;
import interpreter.error.ParserError;

import static interpreter.Util.emptySequence;

/**
 * Rule for sequence expressions:
 * <p/>
 * {1, 6} or {1, n}
 */

public class SequenceRule implements Rule {
    @Override
    public boolean couldBeApplied(String line) {
        return line.startsWith("{") && line.endsWith("}");
    }

    @Override
    public Expression parse(Parser parser, String line, Context context) {
        String newLine = line.substring(1, line.length() - 1);
        int firstComma = newLine.indexOf(",");
        if (firstComma != -1) {
            NumberExpression left = parser.numberExpression(context, newLine.substring(0, firstComma));
            NumberExpression right = parser.numberExpression(context,
                    newLine.substring(firstComma + 1, newLine.length()));
            return new Sequence(left, right);
        } else {
            context.addException(new ParserError("Invalid sequence '" + newLine + ""));
            return emptySequence();
        }
    }
}
