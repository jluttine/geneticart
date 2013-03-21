package org.ga.algorithm;

import java.util.*;

public class NodeConstant extends Node {
  private double m_value;
  public NodeConstant() {
    super();
    this.m_value = (new Random()).nextDouble() * 2 - 1;
  }
  public double evaluate(double x, double y) {
    return this.m_value;
  }
  public String get_string() {
    return "" + m_value;
  }
  public Node get_copy() {
    NodeConstant copy = new NodeConstant();
    copy.m_value = this.m_value;
    return copy;
  }
}
