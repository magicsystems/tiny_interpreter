package interpreter.statement;

import interpreter.Context;
import interpreter.expression.*;
import java.util.StringJoiner;

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
        StringJoiner joiner = new StringJoiner(", ", "{", "}");
        while (sequence.hasNext()) {
            joiner.add(String.valueOf(sequence.value(context)));
        }
        sequence.clear();
        return joiner.toString();
    }
}
