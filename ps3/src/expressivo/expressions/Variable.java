package expressivo.expressions;

import java.util.Map;

import expressivo.Expression;

public class Variable implements Expression {
  private final String name;

  private void checkRep() {
    assert name.matches("[a-zA-Z]+") : "Variable name must consist of letters only";
  }

  public Variable(String name) {
    this.name = name;
    checkRep();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof Variable))
      return false;
    Variable that = (Variable) obj;
    return this.name.equals(that.name);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public Expression differentiate(String variable) {
    if (this.name.equals(variable)) {
      return new Number(1);
    } else {
      return new Number(0);
    }
  }

  @Override
  public Expression simplify(Map<String, Double> environment) {
    if (environment.containsKey(name)) {
      return new Number(environment.get(name));
    } else {
      return this;
    }
  }

}
