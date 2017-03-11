package interpreter.rule;

import interpreter.Context;
import interpreter.expression.ArithmeticExpression;
import interpreter.expression.Expression;
import interpreter.expression.NumberExpression;
import interpreter.expression.Operation;
import interpreter.parser.Parser;
import interpreter.error.ParserError;

import static interpreter.Util.emptyNumberExpression;

/**
 * Rule for {@link interpreter.expression.Operation} application.
 * <p/>
 * Will be applied only if operation is not within round brackets.
 */
public class ArithmeticOperationRule implements Rule {
    private final Operation operation;


    public ArithmeticOperationRule(Operation operation) {
        this.operation = operation;
    }

    @Override
    public boolean couldBeApplied(String line) {
        int openCloseBracketsDiffer = 0;
        char[] array = line.toCharArray();
        for (char next : array) {
            if (next == '(') {
                openCloseBracketsDiffer++;
            } else if (next == ')') {
                openCloseBracketsDiffer--;
            } else if (next == operation.getChar()) {
                if (openCloseBracketsDiffer <= 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Expression parse(Parser parser, String line, Context context) {
        int position = line.indexOf(operation.getChar());
        if (position == 0 || position == line.length() - 1) {
            context.addException(new ParserError("Expression expected"));
            return emptyNumberExpression();
        } else {
            NumberExpression left = parser.numberExpression(context, line.substring(0, position));
            NumberExpression right = parser.numberExpression(context, line.substring(position + 1, line.length()));
            return new ArithmeticExpression(left, right, operation);
        }
    }
}
