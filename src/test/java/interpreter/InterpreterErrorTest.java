package interpreter;


import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class InterpreterErrorTest {


    private static final String DUPLICATE_IDENTIFIERS_PROGRAM =
            "var m = map({1,7}, x -> x/2)\n" +
                    "var m = reduce(m, 0, x y -> x + y)\n";

    @Test
    public void testDuplicate() {
        runProgramWithError(DUPLICATE_IDENTIFIERS_PROGRAM, "Variable 'm' already defined in the current context");
    }


    private static final String INVALID_TYPE_REDUCE_PROGRAM =
            "var n = 5\n" +
                    "var val = reduce(n, 0, x y -> x + y)\n" +
                    "print \"val = \"\n" +
                    "out val";

    @Test
    public void testInvalidTypeReduceProgram() {
        runProgramWithError(INVALID_TYPE_REDUCE_PROGRAM,
                "Incompatible type error. Sequence is required, but got 'n'",
                "Undefined variable 'val'");
    }


    private static final String INVALID_TYPE_MAP_PROGRAM =
            "var n = {1,5}\n" +
                    "var red = reduce(n, 0, x y -> x + y)\n" +
                    "var val = map(red, y -> 3 + y)\n";

    @Test
    public void testInvalidTypeMapProgram() {
        runProgramWithError(INVALID_TYPE_MAP_PROGRAM,
                "Incompatible type error. Sequence is required, but got " + "'red'");
    }

    private static final String INVALID_SEQUENCE_FOR_MAP_PROGRAM = "var n={1,5}\n " +
            "var x = map({1,n}, c -> c*2)";

    @Test
    public void testInvalidSequenceForMapProgram() {
        runProgramWithError(INVALID_SEQUENCE_FOR_MAP_PROGRAM,
                "Incompatible type error. Number is required, but got '{1.0, 5.0}'");
    }

    private static final String INVALID_LAMBDA_IN_REDUCE_PROGRAM = "var n={1,5}\n" +
            "var result = reduce(n, 0, x y -> x + z)";

    @Test
    public void testInvalidLambdaInReduceProgram() {
        runProgramWithError(INVALID_LAMBDA_IN_REDUCE_PROGRAM,
                "Undefined variable 'z'");
    }


    private static final String INVALID_LAMBDA_IN_MAP_PROGRAM = "var n={1,5}\n" +
            "var result = map(n, x -> x + y)";

    @Test
    public void testInvalidLambdaInMapProgram() {
        runProgramWithError(INVALID_LAMBDA_IN_MAP_PROGRAM,
                "Undefined variable 'y'");
    }

    private static final String EXPRESSION_EXPECTED_IN_LAMBDA = "out reduce({1,4}, 1, x y -> x+)";

    @Test
    public void testExpressionExpectedInLambda() {
        runProgramWithError(EXPRESSION_EXPECTED_IN_LAMBDA,
                "Expression expected");
    }

    private static void runProgramWithError(String program, String... errors) {
        Interpreter interpreter = new Interpreter();
        Result result = interpreter.execute(program);
        assertThat(result.getAllErrorsList(), hasSize(errors.length));
        for (int i = 0; i < errors.length; i++) {
            assertThat(result.getAllErrorsList().get(i).getMessage(),
                    equalTo(errors[i]));
        }
    }
}
