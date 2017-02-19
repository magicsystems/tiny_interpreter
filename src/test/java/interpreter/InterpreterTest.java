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

    private static final String HELLO_WORLD = "print \"Hello World!\"";

    private static final String PRINT_SEQUENCE =
            "var sequence = map({1, 4}, i -> (-1)^(i+1))\n" +
                    "out sequence";

    private static final String PRINT_SEQUENCE_VARIABLE =
            "var sequence = {1,4}\n" +
                    "out sequence";


    @Test
    public void testInterpretation() {
        runProgram(PROGRAM_ONE, Arrays.asList("val = ", "67.0"));
    }

    @Test
    public void testHelloWorld() {
        runProgram(HELLO_WORLD, Arrays.asList("Hello World!"));
    }

    @Test
    public void testPrintSequence() {
        runProgram(PRINT_SEQUENCE, Arrays.asList("{1.0, -1.0, 1.0, -1.0}"));
    }

    @Test
    public void testPrintSequenceVariable() {
        runProgram(PRINT_SEQUENCE_VARIABLE, Arrays.asList("{1.0, 2.0, 3.0, 4.0}"));
    }

    private static void runProgram(String program, List<String> output) {
        Interpreter interpreter = new Interpreter();
        Result result = interpreter.execute(program);
        assertThat(result.getExceptionList(), empty());
        assertThat(result.getOutput(), equalTo(output));
    }
}
