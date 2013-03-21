package org.ga.algorithm;

public class NodeDivide extends Node {
  public NodeDivide(Node child1, Node child2) {
    super(child1, child2);
  }
  public double evaluate(double x, double y) {
    /*
    double weight = this.m_child_nodes[1].evaluate(x, y);
    if (weight < -1) weight = -1;
    if (weight > 1) weight = 1;
    weight = (weight + 1) / 2;
    return weight * this.m_child_nodes[0].evaluate(x, y);
    //*/
    //*
    double dividor = this.m_child_nodes[1].evaluate(x, y);
    if (dividor == 0)
      return 0;
    else
      return this.m_child_nodes[0].evaluate(x, y) / dividor;
      //return Math.pow(this.m_child_nodes[0].evaluate(x, y), 1 / dividor);
       
       //*/
  }
  public String get_string() {
    return "/";
  }
  public Node get_copy() {
    return new NodeDivide(this.m_child_nodes[0].get_copy(),
                       this.m_child_nodes[1].get_copy());
  }
}
