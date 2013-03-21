package org.ga.algorithm;

public class NodePolar extends Node {
  
  public NodePolar(Node child1) {
    super(child1);
  }

  public double evaluate(double x, double y) {
    double cart_x = Math.sqrt(x*x + y*y);//x * Math.cos(y);
    double cart_y = Math.atan2(y, x);//x * Math.sin(y);
    return this.m_child_nodes[0].evaluate(cart_x,cart_y);
  }

  public Node get_copy() {
    return new NodePolar(this.m_child_nodes[0].get_copy());
  }

  public String get_string() {
    return "polar";
  }

}
