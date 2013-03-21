package org.ga.algorithm;

public class NodeMultiply extends Node {
  public NodeMultiply(Node child1, Node child2) {
    super(child1, child2);
  }
  public double evaluate(double x, double y) {
    return this.m_child_nodes[0].evaluate(x, y) *
           this.m_child_nodes[1].evaluate(x, y);
  }

  public String get_string() {
    return "*";
  }
  public Node get_copy() {
    return new NodeMultiply(this.m_child_nodes[0].get_copy(),
                       this.m_child_nodes[1].get_copy());
  }
}
