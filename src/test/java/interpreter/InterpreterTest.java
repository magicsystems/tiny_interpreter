package interpreter;


import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertThat;

public class InterpreterTest {

    private static final String PROGRAM_ONE =
            "var n = 5\n" +
                    "var sequence = map({1, n}, i -> 2^i + 1)\n" +
                    "var val = reduce(sequence, 0, x y -> x + y)\n" +
                    "print \"val = \"\n" +
                    "out val";
    @Test
    public void testInterpretation() {
        runProgram(PROGRAM_ONE, Arrays.asList("val = ", "67.0"));
    }


    private static final String HELLO_WORLD = "print \"Hello World!\"";

    @Test
    public void testHelloWorld() {
        runProgram(HELLO_WORLD, Arrays.asList("Hello World!"));
    }


    private static final String PRINT_SEQUENCE =
            "var sequence = map({1, 4}, i -> (-1)^(i+1))\n" +
                    "out sequence";

    @Test
    public void testPrintSequence() {
        runProgram(PRINT_SEQUENCE, Arrays.asList("{1.0, -1.0, 1.0, -1.0}"));
    }


    private static final String PRINT_SEQUENCE_VARIABLE =
            "var sequence = {1,4}\n" +
                    "out sequence";

    @Test
    public void testPrintSequenceVariable() {
        runProgram(PRINT_SEQUENCE_VARIABLE, Arrays.asList("{1.0, 2.0, 3.0, 4.0}"));
    }


    private static final String TWO_REDUCE_OPERATION_PROGRAM =
            "var m = map({1,7}, x -> x/2)\n" +
                    "var sum = reduce(m, 0,  x y -> x + y)\n" +
                    "var mult = reduce(m, 1, x  y  ->  x * y)\n" +
                    "out sum\n" +
                    "out mult\n";

    @Test
    public void testTwoReduce() {
        runProgram(TWO_REDUCE_OPERATION_PROGRAM, Arrays.asList("14.0", "39.375"));
    }


    private static void runProgram(String program, List<String> output) {
        Interpreter interpreter = new Interpreter();
        Result result = interpreter.execute(program);
        assertThat(result.getAllErrorsList(), empty());
        assertThat(result.getOutput(), equalTo(output));
    }

}
