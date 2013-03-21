package org.ga.algorithm;

public class NodeAbs extends Node {
  public NodeAbs(Node child) {
    super(child);
  }
  public double evaluate(double x, double y) {
    return Math.abs(this.m_child_nodes[0].evaluate(x, y));
  }
  public String get_string() {
    return "abs";
  }
  public Node get_copy() {
    return new NodeAbs(this.m_child_nodes[0].get_copy());
  }

}
