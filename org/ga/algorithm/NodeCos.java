package org.ga.algorithm;

public class NodeCos extends Node {
  public NodeCos(Node child) {
    super(child);
  }
  public double evaluate(double x, double y) {
    return Math.cos(this.m_child_nodes[0].evaluate(x,y));
  }
  public String get_string() {
    return "cos";
  }
  public Node get_copy() {
    return new NodeCos(this.m_child_nodes[0].get_copy());
  }

}
