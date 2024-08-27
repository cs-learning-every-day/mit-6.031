/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.*;

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
