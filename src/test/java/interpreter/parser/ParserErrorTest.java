package interpreter.parser;


import interpreter.Context;
import interpreter.error.ParserError;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

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
        assertThat(context.hasErrors(), equalTo(true));
        assertThat(context.getErrors(), hasSize(1));
        assertThat(context.getErrors().get(0).getMessage(),
                equalTo("Undefined variable 't'"));
    }

    @Test
    public void testMissingElements() {
        checkError("()", "Expression expected");
        checkError("( 3 + 1", "Expression expected ')'");
        checkError("3 + (1*4) )", "Expression expected '('");
        checkError("{1 ,5", "Expression expected '}'");
        checkError("{1,}", "Expression expected");
        checkError("{1,  }", "Expression expected");
        checkError("{13}", "Expression expected");
        checkError("{}", "Expression expected");
        checkError("{ ,}", "Expression expected");
        checkError("{,46 }", "Expression expected");
        checkError("1 +", "Expression expected");
        checkError("  + 32*5", "Expression expected");
    }

    @Test
    public void testReduceErrors() {
        checkError("reduce({1,4}, r, x y -> x+1)", "Undefined variable 'r'");
        checkError("reduce({1,4}, 1 x y -> x+1)", "Invalid syntax for reduce function");
        checkError("reduce({1,4} 1 x y -> x+1)", "Invalid syntax for reduce function");
        checkError("reduce(sec, 1, x y -> x+1)", "Undefined variable 'sec'");
        checkError("reduce({1,5}, 1, x y > x+1)", "There should be '->' in 'x y > x+1'");
    }

    @Test
    public void testMapErrors() {
        checkError("map({1,4} f -> f+ 1)", "Invalid syntax for map function");
        checkError("map({1,4}, f -> f+ )", "Expression expected");
    }

    @Test
    public void testMapsWithInvalidLambda() {
        checkError("map({1,5}, 1, x y -> x+1)", "Invalid identifier '1, x y'",
                "Undefined variable 'x'");
    }

    private static void checkError(String line, String... exceptions) {
        Parser parser = new Parser();
        Context context = new Context();
        parser.expression(context, line);
        assertThat(context.hasErrors(), equalTo(true));
        assertThat(context.getErrors(), hasSize(exceptions.length));
        assertThat(context.getErrors().stream().map(ParserError::getMessage).collect(Collectors.toList()),
                equalTo(Arrays.asList(exceptions)));
    }
}
