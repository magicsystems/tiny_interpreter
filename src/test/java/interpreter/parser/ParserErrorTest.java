package interpreter.parser;


import interpreter.Context;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class ParserErrorTest {

    @Test
    public void testIncompatibleTypes() {
        Parser parser = new Parser();
        Context context = new Context();
        parser.expression(context, "{1 , 3} + 3*4");
        assertThat(context.hasException(), equalTo(true));
        assertThat(context.getExceptions(), hasSize(1));
        assertThat(context.getExceptions().get(0).getMessage(),
                equalTo("Incompatible type error. Number is required, but got '{1.0, 3.0}'"));
    }

    @Test
    public void testUndefinedVariable() {
        Parser parser = new Parser();
        Context context = new Context();
        parser.expression(context, "map(sequence, x -> x +3)");
        assertThat(context.hasException(), equalTo(true));
        assertThat(context.getExceptions(), hasSize(1));
        assertThat(context.getExceptions().get(0).getMessage(),
                equalTo("Undefined variable 'sequence'"));
    }

    @Test
    public void testIncompatibleTypeWithMapAndReduce() {
        Parser parser = new Parser();
        Context context = new Context();
        parser.expression(context, "map(reduce({1, 4}, 1, x y -> x * y), u -> u + 3)");
        assertThat(context.hasException(), equalTo(true));
        assertThat(context.getExceptions(), hasSize(1));
        assertThat(context.getExceptions().get(0).getMessage(),
                equalTo("Incompatible type error. Sequence is required, but got 'reduce({1.0, 4.0}, 1.0, (x, y) ->(x * y))'"));
    }

    @Test
    public void testInvalidLambda() {
        Parser parser = new Parser();
        Context context = new Context();
        parser.parseTwoArgsLambda(context, "x c -> x + t*7");
        assertThat(context.hasException(), equalTo(true));
        assertThat(context.getExceptions(), hasSize(1));
        assertThat(context.getExceptions().get(0).getMessage(),
                equalTo("Undefined variable 't'"));
    }
}
