package org.ga.algorithm;

public class NodeY extends Node {
  public NodeY() {
    super();
  }
  public double evaluate(double x, double y) {
    return y;
  }
  public String get_string() {
    return "y";
  }
  public Node get_copy() {
    return new NodeY();
  }
}
