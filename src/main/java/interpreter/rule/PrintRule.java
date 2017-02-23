package interpreter.rule;

import interpreter.Context;
import interpreter.statement.Statement;
import interpreter.statement.PrintStatement;
import interpreter.parser.Parser;

/**
 *  Prints string constants:
 *  <p>
 *  print "hello"
 */
public class PrintRule implements Rule {
    private static final String START = "print \"";

    @Override
    public boolean couldBeApplied(String line) {
        return line.startsWith(START) && line.endsWith("\"") && !line.equals(START);
    }

    @Override
    public Statement parse(Parser parser, String line, Context context) {
        String newLine = line.substring(START.length(), line.length() - 1);
        return new PrintStatement(newLine);
    }
}
