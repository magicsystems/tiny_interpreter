package interpreter.statement;


import interpreter.Context;
import interpreter.expression.Expression;
import interpreter.expression.NumberExpression;
import interpreter.expression.SequenceExpression;

/**
 * Puts resolve variable into context
 */
public class VariableStatement implements Statement {
    private final Expression variable;
    private final String name;

    public VariableStatement(Expression variable, String name) {
        this.variable = variable;
        this.name = name;
    }

    @Override
    public void execute(Context context) {
        if (variable instanceof NumberExpression) {
            context.putNumericVariable(name, ((NumberExpression)variable).value(context));
        } else if (variable instanceof SequenceExpression) {
            context.putSequenceVariable(name, (SequenceExpression) variable);
        }
    }
}
