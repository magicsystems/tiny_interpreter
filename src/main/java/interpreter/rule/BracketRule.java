package interpreter.rule;

import interpreter.Context;
import interpreter.expression.BracketExpression;
import interpreter.expression.Expression;
import interpreter.parser.Parser;

import static interpreter.Util.emptyNumberExpression;
import static interpreter.Util.expressionExpected;
import static interpreter.Util.expressionExpectedCheck;
import static interpreter.Util.numberOfChars;

/**
 * Rule for recognition expressions inside brackets:
 * <p/>
 * (expr)
 */
public class BracketRule implements Rule {

    @Override
    public boolean couldBeApplied(String line) {
        boolean startsWith = line.startsWith("(");
        boolean endsWith = line.endsWith(")");
        if (startsWith && endsWith) {
            int nextOpeningBracket = line.indexOf("(", 1);
            int nextClosingBracket = line.indexOf(")");
            return nextOpeningBracket <= nextClosingBracket;
        }
        return (startsWith || endsWith) && numberOfChars(line, '(') != numberOfChars(line, ')');
    }

    @Override
    public Expression parse(Parser parser, String line, Context context) {
        Expression expression = expressionExpectedCheck(line, "(", ")", context);
        if (expression != null) {
            return expression;
        }
        String newLine = line.substring(1, line.length() - 1);
        if (!newLine.isEmpty()) {
            return new BracketExpression(parser.numberExpression(context, newLine));
        } else {
            context.addError(expressionExpected());
            return emptyNumberExpression();
        }
    }

}
