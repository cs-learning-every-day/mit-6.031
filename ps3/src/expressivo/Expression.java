/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import java.util.Map;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import expressivo.expressions.Plus;
import expressivo.expressions.Times;
import expressivo.expressions.Variable;
import expressivo.expressions.Number;
import expressivo.parser.ExpressionLexer;
import expressivo.parser.ExpressionParser;

/**
 * An immutable data type representing a polynomial expression of:
 * + and *
 * nonnegative integers and floating-point numbers
 * variables (case-sensitive nonempty strings of letters)
 * 
 * <p>
 * PS3 instructions: this is a required ADT interface.
 * You MUST NOT change its name or package or the names or type signatures of
 * existing methods.
 * You may, however, add additional methods, or strengthen the specs of existing
 * methods.
 * Declare concrete variants of Expression in their own Java source files.
 */
public interface Expression {

    // Datatype definition
    // TODO

    /**
     * Parse an expression.
     * 
     * @param input expression to parse, as defined in the PS3 handout.
     * @return expression AST for the input
     * @throws IllegalArgumentException if the expression is invalid
     */
    public static Expression parse(String input) {
        try {
            CharStream cs = new ANTLRInputStream(input);
            ExpressionLexer lexer = new ExpressionLexer(cs);
            lexer.removeErrorListeners();
            lexer.addErrorListener(new BaseErrorListener() {
                @Override
                public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                        int line, int charPositionInLine, String msg, RecognitionException e) {
                    throw new IllegalArgumentException("Invalid expression: " + msg);
                }
            });

            CommonTokenStream tokens = new CommonTokenStream(lexer);
            ExpressionParser parser = new ExpressionParser(tokens);

            parser.removeErrorListeners();
            parser.addErrorListener(new BaseErrorListener() {
                @Override
                public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                        int line, int charPositionInLine, String msg, RecognitionException e) {
                    throw new IllegalArgumentException("Invalid expression: " + msg);
                }
            });

            ExpressionParser.ExprContext context = parser.expr();
            return buildAST(context);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse expression", e);
        }

    }

    private static Expression buildAST(ExpressionParser.ExprContext context) {
        if (context.term().size() == 1) {
            return buildTermAST(context.term(0));
        }

        Expression left = buildTermAST(context.term(0));

        for (int i = 1; i < context.term().size(); i++) {
            Expression right = buildTermAST(context.term(i));
            left = new Plus(left, right);
        }

        return left;
    }

    private static Expression buildTermAST(ExpressionParser.TermContext context) {
        if (context.factor().size() == 1) {
            return buildFactorAST(context.factor(0));
        }

        Expression left = buildFactorAST(context.factor(0));
        for (int i = 1; i < context.factor().size(); i++) {
            Expression right = buildFactorAST(context.factor(i));
            left = new Times(left, right);
        }
        return left;
    }

    private static Expression buildFactorAST(ExpressionParser.FactorContext context) {
        if (context.NUMBER() != null) {
            return new Number(Double.parseDouble(context.NUMBER().getText()));
        } else if (context.VARIABLE() != null) {
            return new Variable(context.VARIABLE().getText());
        } else {
            return buildAST(context.expr());
        }
    }

    /**
     * @return a parsable representation of this expression, such that
     *         for all e:Expression, e.equals(Expression.parse(e.toString())).
     */
    @Override
    public String toString();

    /**
     * @param thatObject any object
     * @return true if and only if this and thatObject are structurally-equal
     *         Expressions, as defined in the PS3 handout.
     */
    @Override
    public boolean equals(Object thatObject);

    /**
     * @return hash code value consistent with the equals() definition of structural
     *         equality, such that for all e1,e2:Expression,
     *         e1.equals(e2) implies e1.hashCode() == e2.hashCode()
     */
    @Override
    public int hashCode();

    // TODO more instance methods
    /**
     * Differentiate this expression with respect to a variable.
     * 
     * @param variable the variable to differentiate by, a case-sensitive nonempty
     *                 string of letters
     * @return the derivative of this expression with respect to the variable
     */
    Expression differentiate(String variable);

    /**
     * Simplify this expression by substituting variables with values from the
     * environment.
     * 
     * @param environment maps variables to values. Variables not in the environment
     *                    remain unchanged.
     * @return a simplified expression with variables replaced and any possible
     *         simplifications performed.
     */
    Expression simplify(Map<String, Double> environment);

}
