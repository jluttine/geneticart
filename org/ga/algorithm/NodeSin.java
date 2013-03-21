package org.ga.algorithm;

public class NodeSin extends Node {
  public NodeSin(Node child) {
    super(child);
  }
  public double evaluate(double x, double y) {
    return Math.sin(this.m_child_nodes[0].evaluate(x,y));
  }

  public String get_string() {
    return "sin";
  }
  public Node get_copy() {
    return new NodeSin(this.m_child_nodes[0].get_copy());
  }
}
