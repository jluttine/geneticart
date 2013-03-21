package org.ga.algorithm;

public class NodeX extends Node {
  public NodeX() {
    super();
  }
  public double evaluate(double x, double y) {
    return x;
  }
  public String get_string() {
    return "x";
  }
  public Node get_copy() {
    return new NodeX();
  }
}
