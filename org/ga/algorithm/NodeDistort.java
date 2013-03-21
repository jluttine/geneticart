package org.ga.algorithm;

public class NodeDistort extends Node {
  public NodeDistort(Node child1, Node child2, Node child3) {
    super(child1, child2, child3);
  }
  public double evaluate(double x, double y) {
    double new_x = this.m_child_nodes[0].evaluate(x, y);
    double new_y = this.m_child_nodes[1].evaluate(x, y);
    /*
    if (new_x < new_y)
      return this.m_child_nodes[2].evaluate(x, y);
    else
      return -this.m_child_nodes[2].evaluate(x, y);
      //*/
    return this.m_child_nodes[2].evaluate(new_x, new_y);
  }
  public String get_string() {
    return "distort";
  }
  public Node get_copy() {
    return new NodeDistort(this.m_child_nodes[0].get_copy(),
                           this.m_child_nodes[1].get_copy(),
                           this.m_child_nodes[2].get_copy());
  }
}
