package expressivo.expressions;

import java.util.Map;
import java.util.Objects;

import expressivo.Expression;

public class Number implements Expression {
  private final double value;

  private void checkRep() {
    assert value >= 0 : "Number value must be non-negative";
  }

  public Number(double value) {
    this.value = value;
    checkRep();
  }

  @Override
  public String toString() {
    return Double.toString(value);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof Number))
      return false;

    Number oth = (Number) obj;
    return Double.compare(value, oth.value) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public Expression differentiate(String variable) {
    return new Number(0);
  }

  @Override
  public Expression simplify(Map<String, Double> environment) {
    return this;
  }

  public double getValue() {
    return value;
  }

}
