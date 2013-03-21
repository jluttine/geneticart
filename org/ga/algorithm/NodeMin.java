package org.ga.algorithm;

public class NodeMin extends Node {
  public NodeMin(Node child1, Node child2) {
    super(child1, child2);
  }
  public double evaluate(double x, double y) {
    return Math.min(this.m_child_nodes[0].evaluate(x, y),
                    this.m_child_nodes[1].evaluate(x, y));
  }

  public String get_string() {
    return "min";
  }
  public Node get_copy() {
    return new NodeMin(this.m_child_nodes[0].get_copy(),
                       this.m_child_nodes[1].get_copy());
  }
}
