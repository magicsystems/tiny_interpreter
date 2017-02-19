package interpreter.expression;


import interpreter.Context;
import interpreter.Util;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class ExpressionCalculationTest {
    Context emptyContext = new Context();

    @Test
    public void testArithmeticExpression() {
        ArithmeticExpression sum = new ArithmeticExpression(new Number(1.0), new Number(4.0), Operation.ADD);
        assertThat(5.0, equalTo(sum.value(emptyContext)));
        ArithmeticExpression pow = new ArithmeticExpression(new Number(2.0), sum, Operation.POW);
        assertThat(32.0, equalTo(pow.value(emptyContext)));
        ArithmeticExpression div = new ArithmeticExpression(pow, sum, Operation.DIV);
        assertThat(6.4, equalTo(div.value(emptyContext)));
    }

    @Test
    public void testMapExpression() {
       MapExpression mapExpression = new MapExpression(new Sequence(new Number(5.0), new Number(7.0),
               x -> x  + 1), "x", new ArithmeticExpression(new Identifier("x"), new Number(4.0), Operation.MULT));
        List<Double> result = Util.sequenceToList(mapExpression, emptyContext);

        assertThat(Arrays.asList(20.0, 24.0, 28.0), equalTo(result));
    }
}
