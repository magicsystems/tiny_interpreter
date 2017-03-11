package interpreter.parser;


import interpreter.Context;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class ParserErrorTest {

    @Test
    public void testIncompatibleTypes() {
        checkError("{1 , 3} + 3*4", "Incompatible type error. Number is required, but got '{1.0, 3.0}'");
    }

    @Test
    public void testUndefinedVariable() {
        checkError("map(sequence, x -> x +3)", "Undefined variable 'sequence'");
    }

    @Test
    public void testIncompatibleTypeWithMapAndReduce() {
        checkError("map(reduce({1, 4}, 1, x y -> x * y), u -> u + 3)",
                "Incompatible type error. Sequence is required, but got 'reduce({1.0, 4.0}, 1.0, (x, y) ->(x * y))'");
    }


    @Test
    public void testInvalidLambda() {
        Parser parser = new Parser();
        Context context = new Context();
        parser.parseTwoArgsLambda(context, "x c -> x + t*7");
        assertThat(context.hasException(), equalTo(true));
        assertThat(context.getErrors(), hasSize(1));
        assertThat(context.getErrors().get(0).getMessage(),
                equalTo("Undefined variable 't'"));
    }

    @Test
    public void testMissingElements() {
        checkError("(3 + 1", "Expression expected ')'");
        checkError("3 + (1*4))", "Expression expected '('");
        checkError("{1 ,5", "Expression expected '}'");
        checkError("{1,}", "Expression expected");
        checkError("{,}", "Expression expected");
        checkError("{,46}", "Expression expected");
        checkError("1 +", "Expression expected");
        checkError("  + 32*5", "Expression expected");
    }

    @Test
    public void testReduceErrors() {
        checkError("reduce({1,4}, r, x y -> x+1)", "Undefined variable 'r'");
    }

    private static void checkError(String line, String exception) {
        Parser parser = new Parser();
        Context context = new Context();
        parser.expression(context, line);
        assertThat(context.hasException(), equalTo(true));
        assertThat(context.getErrors(), hasSize(1));
        assertThat(context.getErrors().get(0).getMessage(),
                equalTo(exception));
    }
}
