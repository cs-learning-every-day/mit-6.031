package expressivo.expressions;

import java.util.Map;

import expressivo.Expression;

public class Plus implements Expression {
  private final Expression left;
  private final Expression right;

  public Plus(Expression left, Expression right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public boolean equals(Object thatObject) {
    if (this == thatObject)
      return true;
    if (!(thatObject instanceof Plus))
      return false;
    Plus that = (Plus) thatObject;
    return this.left.equals(that.left) && this.right.equals(that.right);
  }

  @Override
  public int hashCode() {
    return left.hashCode() + right.hashCode();
  }

  @Override
  public String toString() {
    return "(" + left.toString() + " + " + right.toString() + ")";
  }

  @Override
  public Expression differentiate(String variable) {
    return new Plus(left.differentiate(variable), right.differentiate(variable));
  }

  @Override
  public Expression simplify(Map<String, Double> environment) {
    Expression simplifiedLeft = left.simplify(environment);
    Expression simplifiedRight = right.simplify(environment);

    if (simplifiedLeft instanceof Number && simplifiedRight instanceof Number) {
      double leftValue = ((Number) simplifiedLeft).getValue();
      double rightValue = ((Number) simplifiedRight).getValue();
      return new Number(leftValue + rightValue);
    }

    return new Plus(simplifiedLeft, simplifiedRight);
  }

}
