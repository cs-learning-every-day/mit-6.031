/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import expressivo.expressions.Number;
import expressivo.expressions.Plus;
import expressivo.expressions.Times;
import expressivo.expressions.Variable;

/**
 * Tests for the Expression abstract data type.
 */
public class ExpressionTest {

    // Testing strategy
    // TODO

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testNumberDifferentiation() {
        Expression Number = new Number(5);
        assertEquals(new Number(0), Number.differentiate("x"));
    }

    @Test
    public void testVariableDifferentiation() {
        Expression variable = new Variable("x");
        assertEquals(new Number(1), variable.differentiate("x"));
        assertEquals(new Number(0), variable.differentiate("y"));
    }

    @Test
    public void testPlusDifferentiation() {
        Expression sum = new Plus(new Variable("x"), new Number(5));
        assertEquals(new Plus(new Number(1), new Number(0)), sum.differentiate("x"));
    }

    @Test
    public void testTimesDifferentiation() {
        Expression product = new Times(new Variable("x"), new Number(5));
        assertEquals("((1.0 * 5.0) + (x * 0.0))", product.differentiate("x").toString());

        product = new Times(new Variable("x"), new Times(new Variable("x"), new Variable("x")));
        assertEquals("((1.0 * (x * x)) + (x * ((1.0 * x) + (x * 1.0))))", product.differentiate("x").toString());
    }

    @Test
    public void testSimplifyWithNumber() {
        Expression expr = new Plus(new Number(1), new Number(2));
        assertEquals(new Number(3).toString(), expr.simplify(new HashMap<>()).toString());
    }

    @Test
    public void testSimplifyWithVariable() {
        Expression expr = new Plus(new Variable("x"), new Number(2));
        Map<String, Double> env = new HashMap<>();
        env.put("x", 3.0);
        assertEquals(new Number(5).toString(), expr.simplify(env).toString());
    }

    @Test
    public void testSimplifyWithTimes() {
        Expression expr = new Times(new Variable("x"), new Number(2));
        Map<String, Double> env = new HashMap<>();
        env.put("x", 3.0);
        assertEquals(new Number(6).toString(), expr.simplify(env).toString());
    }

    @Test
    public void testSimplifyWithPlusOfVariables() {
        Expression expr = new Plus(new Variable("x"), new Variable("y"));
        Map<String, Double> env = new HashMap<>();
        env.put("x", 2.0);
        assertEquals(new Plus(new Number(2), new Variable("y")).toString(), expr.simplify(env).toString());
    }

    @Test
    public void testParseSimpleExpression() {
        Expression expr = Expression.parse("3 + 2.4");
        assertEquals("(3.0 + 2.4)", expr.toString());
    }

    @Test
    public void testParseComplexExpression() {
        Expression expr = Expression.parse("3 * (x + 2.4)");
        assertEquals("(3.0 * (x + 2.4))", expr.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseInvalidExpression() {
        Expression.parse("3 *");
    }

    // TODO tests for Expression
    @Test
    public void testNumber() {
        Expression e = new Number(5.0);
        assertEquals("5.0", e.toString());
        assertEquals(e, new Number(5.0));
        assertNotEquals(e, new Number(4.0));
        assertNotEquals(e, new Variable("test"));
    }

    @Test
    public void testVariable() {
        Expression var = new Variable("x");
        assertEquals("x", var.toString());
        assertEquals(var, new Variable("x"));
    }

    @Test
    public void testPlus() {
        Expression expr = new Plus(new Number(2), new Variable("x"));
        assertEquals("(2.0 + x)", expr.toString());
        assertEquals(expr, new Plus(new Number(2), new Variable("x")));
    }

    @Test
    public void testTimes() {
        Expression expr = new Times(new Number(3), new Variable("y"));
        assertEquals("(3.0 * y)", expr.toString());
        assertEquals(expr, new Times(new Number(3), new Variable("y")));
    }

}
