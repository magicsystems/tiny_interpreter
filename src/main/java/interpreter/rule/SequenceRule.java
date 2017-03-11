package interpreter.rule;


import interpreter.Context;
import interpreter.expression.Expression;
import interpreter.expression.NumberExpression;
import interpreter.expression.Sequence;
import interpreter.parser.Parser;

import static interpreter.Util.emptySequence;
import static interpreter.Util.expressionExpected;
import static interpreter.Util.expressionExpectedCheck;
import static interpreter.Util.numberOfChars;

/**
 * Rule for sequence expressions:
 * <p/>
 * {1, 6} or {1, n}
 */

public class SequenceRule implements Rule {
    @Override
    public boolean couldBeApplied(String line) {
        boolean startsWith = line.startsWith("{");
        boolean endsWith = line.endsWith("}");
        if (startsWith && endsWith) {
            return true;
        } else if (startsWith || endsWith) {
            return numberOfChars(line, '{') != numberOfChars(line, '}');
        }
        return false;
    }

    @Override
    public Expression parse(Parser parser, String line, Context context) {
        Expression expression = expressionExpectedCheck(line, "{", "}", context);
        if (expression != null) {
            return expression;
        }
        String newLine = line.substring(1, line.length() - 1);
        int firstComma = newLine.indexOf(",");
        if (firstComma <= 0 || firstComma == newLine.length() - 1) {
            context.addError(expressionExpected());
            return emptySequence();
        } else {
            NumberExpression left = parser.numberExpression(context, newLine.substring(0, firstComma));
            NumberExpression right = parser.numberExpression(context,
                    newLine.substring(firstComma + 1, newLine.length()));
            return new Sequence(left, right);
        }
    }
}
