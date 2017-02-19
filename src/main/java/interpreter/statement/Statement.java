package interpreter.statement;


import interpreter.Context;

/**
 * Executes actions against context. For example, declare variable or print message.
 */
public interface Statement {

    void execute(Context context);
}
