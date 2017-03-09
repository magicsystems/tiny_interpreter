package interpreter.statement;

import interpreter.Context;
import interpreter.expression.*;
import java.util.stream.Collectors;

/**
 * Puts sequence expression to be print into context
 */
public class OutSequenceStatement implements Statement {
    private final SequenceExpression sequence;

    public OutSequenceStatement(SequenceExpression sequence) {
        this.sequence = sequence;
    }

    @Override
    public void execute(Context context) {
        context.addOutPutString(sequenceToString(sequence, context));
    }

    private static String sequenceToString(SequenceExpression sequence, Context context) {
        return sequence.stream(context).mapToObj(String::valueOf).collect(Collectors.joining(", ", "{", "}"));
    }
}
