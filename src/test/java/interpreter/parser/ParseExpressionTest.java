package interpreter.parser;


import interpreter.Context;
import interpreter.expression.NumberExpression;
import interpreter.expression.SequenceExpression;
import interpreter.Util;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class ParseExpressionTest {

    @Test
    public void testSequenceParse() {
        Parser parser = new Parser();
        Context context = new Context();
        SequenceExpression sequence = parser.sequenceExpression(context, "{4, 6}");
        assertThat(context.hasException(), equalTo(false));
        List<Double> list = Util.sequenceToList(sequence, context);
        assertThat(list, equalTo(Arrays.asList(4.0, 5.0, 6.0)));
    }

    @Test
    public void testParseReduce() {
        checkNumberFunctionResult("reduce({4, 7}, 0, x y -> x + y)", 22.0);
    }

    @Test
    public void testParseReduceWithMap() {
        checkNumberFunctionResult("reduce(map({1, 3}, x -> x * 2 + 0.5), 0, x y -> x + y)", 13.5);
    }

    @Test
    public void testParseReduceWithReduce() {
        checkNumberFunctionResult("reduce(map({1, 3}, x -> x * 2 + 0.5), " +
                "reduce({2,4}, 1, x y -> x), x y -> x + y)", 16.5);
    }

    @Test
    public void testParseReduceWithVariable() {
        Parser parser = new Parser();
        Context emptyContext = new Context();
        SequenceExpression sequenceExpression = parser.sequenceExpression(emptyContext, "{1, 6}");
        assertThat(emptyContext.hasException(), equalTo(false));
        emptyContext.putSequenceVariable("sequence", sequenceExpression);
        NumberExpression expression = parser.numberExpression(emptyContext, "reduce(sequence, 1, x y -> x * y)");
        assertThat(emptyContext.hasException(), equalTo(false));
        assertThat(expression.value(emptyContext), equalTo(720.0));

        expression = parser.numberExpression(emptyContext, "reduce(sequence, 1, a e -> (a * e))");
        assertThat(emptyContext.hasException(), equalTo(false));
        assertThat(expression.value(emptyContext), equalTo(720.0));

    }

    @Test
    public void testParseArithmeticFormula() {
        checkNumberFunctionResult("14 * 5  + 3 / 3 - 5", 66.0);
        checkNumberFunctionResult("(2^3 + (35 - 5) * (19 +1))", 608.0);
    }

    @Test
    public void testParseLambdaWithoutErrors() {
        Parser parser = new Parser();
        Context context = new Context();
        context.putNumericVariable("i", 2.0);
        parser.expression(context, "(-1)^i / (2 * i + 1)");
        assertThat(context.hasException(), equalTo(false));
    }

    @Test
    public void testParseMap() {
        Parser parser = new Parser();
        Context emptyContext = new Context();
        emptyContext.putNumericVariable("n", 3.0);
        SequenceExpression expression = parser.sequenceExpression(emptyContext, "map({1, n}, i -> 2 ^ i + 1)");
        assertThat(emptyContext.hasException(), equalTo(false));
        List<Double> sequence = Util.sequenceToList(expression, emptyContext);
        assertThat(sequence, equalTo(Arrays.asList(3.0, 5.0, 9.0)));
    }


    @Test
    public void testParseLambda() {
        Parser parser = new Parser();
        Context emptyContext = new Context();
        TwoArgsLambda lambda = parser.parseTwoArgsLambda(emptyContext, " x y -> x + y");
        assertThat(emptyContext.hasException(), equalTo(false));
        assertThat(lambda.getFirstIdentifier(), equalTo("x"));
        assertThat(lambda.getSecondIdentifier(), equalTo("y"));
        Context context = new Context();
        context.putNumericVariable("x", 12.0);
        context.putNumericVariable("y", 15.0);
        assertThat(lambda.getException().value(context), equalTo(27.0));
    }


    private static void checkNumberFunctionResult(String function, double result) {
        Parser parser = new Parser();
        Context context = new Context();
        NumberExpression expression = parser.numberExpression(context, function);
        assertThat(context.hasException(), equalTo(false));
        assertThat(expression.value(context), equalTo(result));
    }

}
