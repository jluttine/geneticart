package org.ga.algorithm;

public class NodeMax extends Node {
  public NodeMax(Node child1, Node child2) {
    super(child1, child2);
  }
  public double evaluate(double x, double y) {
    return Math.max(this.m_child_nodes[0].evaluate(x, y),
                    this.m_child_nodes[1].evaluate(x, y));
  }

  public String get_string() {
    return "max";
  }
  public Node get_copy() {
    return new NodeMax(this.m_child_nodes[0].get_copy(),
                       this.m_child_nodes[1].get_copy());
  }
}
