package interpreter.parser;


import interpreter.Context;
import interpreter.error.IncompatibleTypeError;
import interpreter.error.ParserError;
import interpreter.statement.Statement;
import interpreter.expression.Expression;
import interpreter.expression.Identifier;
import interpreter.expression.NumberExpression;
import interpreter.expression.Operation;
import interpreter.expression.SequenceExpression;
import interpreter.rule.ArithmeticOperationRule;
import interpreter.rule.BracketRule;
import interpreter.rule.DefinedVariable;
import interpreter.rule.MapRule;
import interpreter.rule.NumberRule;
import interpreter.rule.OutRule;
import interpreter.rule.PrintRule;
import interpreter.rule.ReduceRule;
import interpreter.rule.Rule;
import interpreter.rule.SequenceRule;
import interpreter.rule.VariableStatementRule;
import interpreter.Util;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * Parser for separate lines in follows grammar:
 * <p>
 * expr ::= expr op expr | (expr) | identifier | { expr, expr } | number |
 * map(expr, identifier -> expr) | reduce(expr, expr, identifier identifier -> expr)
 * op ::= + | - | * | / | ^
 * stmt ::= var identifier = expr | out expr | print “string”
 */
public class Parser {
    private final List<Rule> expressionRules = Arrays.asList(
            new ReduceRule(),
            new MapRule(),
            new SequenceRule(),
            new BracketRule(),
            new NumberRule(),
            new ArithmeticOperationRule(Operation.SUB),
            new ArithmeticOperationRule(Operation.ADD),
            new ArithmeticOperationRule(Operation.DIV),
            new ArithmeticOperationRule(Operation.MULT),
            new ArithmeticOperationRule(Operation.POW),
            new DefinedVariable());

    private final List<Rule> statementRules = Arrays.asList(new VariableStatementRule(), new OutRule(), new PrintRule());

    private final OneArgLambdaParser oneArgLambdaParser = new OneArgLambdaParser();
    private final TwoArgsLambdaParser twoArgsLambdaParser = new TwoArgsLambdaParser();
    private final IdentifierParser identifierParser = new IdentifierParser();

    public Expression expression(Context context, String line) {
        return parse(expressionRules, context, line, Util::emptyExpression, "expression");
    }

    public Statement statement(Context context, String line) {
        return parse(statementRules, context, line, Util::emptyStatement, "statement");
    }

    public NumberExpression numberExpression(Context context, String line) {
        return typedExpression(context, line, NumberExpression.class,
                Util::emptyNumberExpression, "Number");
    }

    public SequenceExpression sequenceExpression(Context context, String line) {
        return typedExpression(context, line, SequenceExpression.class,
                Util::emptySequence, "Sequence");
    }

    public OneArgLambda parseOneArgLambda(Context context, String line) {
        String trimmedLine = line.trim();
        return oneArgLambdaParser.parse(this, context, trimmedLine);
    }

    public TwoArgsLambda parseTwoArgsLambda(Context context, String line) {
        String trimmedLine = line.trim();
        return twoArgsLambdaParser.parse(this, context, trimmedLine);
    }

    public Identifier parseIdentifier(Context context, String line) {
        String trimmedLine = line.trim();
        return identifierParser.parseLambdaParameter(trimmedLine, context);
    }

    private <T> T typedExpression(Context context, String line,
                                  Class<T> type, Supplier<T> empty, String requiredType) {
        Expression expression = expression(context, line);
        if (!context.hasException()) {
            if (type.isAssignableFrom(expression.getClass())) {
                return (T) expression;
            } else {
                context.addException(new IncompatibleTypeError("Incompatible type error. " +
                        requiredType + " is required, but got '" + expression.toString() + "'"));
            }
        }
        return empty.get();
    }

    private <T> T parse(List<Rule> rules,
                        Context context,
                        String line, Supplier<T> empty, String type) {
        String trimmedLine = line.trim();
        for (Rule rule : rules) {
            if (rule.couldBeApplied(trimmedLine)) {
                return (T) rule.parse(this, trimmedLine, context);
            }
        }
        addException(context, trimmedLine, type);
        return empty.get();
    }

    private static void addException(Context context, String line, String type) {
        ParserError parserError = new ParserError("Line '" + line + "' couldn't be recognised " +
                "as a valid '" + type + " " + "'");
        context.addException(parserError);
    }
}
