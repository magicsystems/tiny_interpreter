package interpreter;

import interpreter.statement.Statement;
import interpreter.statement.NoOpStatement;
import interpreter.expression.Expression;
import interpreter.expression.NoOpExpression;
import interpreter.expression.NoOpNumberExpression;
import interpreter.expression.NoOpSequenceExpression;
import interpreter.expression.NumberExpression;
import interpreter.expression.SequenceExpression;
import interpreter.parser.OneArgLambda;
import interpreter.parser.TwoArgsLambda;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class Util {

    private final static Set<String> KEYWORDS = new HashSet<>(Arrays.asList("var", "reduce", "map", "print", "out"));

    private static final Expression NO_OP_EXPRESSION = new NoOpExpression();
    private static final NumberExpression NO_OP_NUMBER_EXPRESSION = new NoOpNumberExpression();
    private static final SequenceExpression NO_OP_SEQUENCE = new NoOpSequenceExpression();
    private static final Statement NO_OP_STATEMENT = new NoOpStatement();
    private static final OneArgLambda NO_OP_ONE_ARG_LAMBDA = new OneArgLambda(NO_OP_NUMBER_EXPRESSION, "");
    private static final TwoArgsLambda NO_OP_TWO_ARG_LAMBDA = new TwoArgsLambda(NO_OP_NUMBER_EXPRESSION, "", "");


    public static List<Double> sequenceToList(SequenceExpression sequence, Context context) {
        return sequence.stream(context).boxed().collect(Collectors.toList());
    }

    public static boolean keyword(String str) {
        return KEYWORDS.contains(str);
    }

    public static Expression emptyExpression() {
        return NO_OP_EXPRESSION;
    }

    public static Statement emptyStatement() {
        return NO_OP_STATEMENT;
    }

    public static NumberExpression emptyNumberExpression() {
        return NO_OP_NUMBER_EXPRESSION;
    }

    public static SequenceExpression emptySequence() {
        return NO_OP_SEQUENCE;
    }

    public static OneArgLambda emptyOneArgLambda() {
        return NO_OP_ONE_ARG_LAMBDA;
    }

    public static TwoArgsLambda emptyTwoArgsLambda() {
        return NO_OP_TWO_ARG_LAMBDA;
    }

}
