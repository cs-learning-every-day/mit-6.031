package expressivo.expressions;

import expressivo.Expression;

public class Times implements Expression {

  private final Expression left;
  private final Expression right;

  public Times(Expression left, Expression right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public boolean equals(Object thatObject) {
    if (this == thatObject)
      return true;
    if (!(thatObject instanceof Times))
      return false;
    Times that = (Times) thatObject;
    return this.left.equals(that.left) && this.right.equals(that.right);
  }

  @Override
  public int hashCode() {
    return left.hashCode() * right.hashCode();
  }

  @Override
  public String toString() {
    return "(" + left.toString() + " * " + right.toString() + ")";
  }

}
