
package org.ga.algorithm;

// Population luokka??
import java.util.*;
import java.io.*;


public abstract class Node {

  //Random rand,random;
  protected static Random random = new Random();
  protected Node[] m_child_nodes;
  protected int m_child_count;
  
  
  public Node() {
    this.m_child_count = 0;
    this.m_child_nodes = null;
  }
  public Node(Node child) {
    this.m_child_count = 1;
    this.m_child_nodes = new Node[this.m_child_count];
    this.m_child_nodes[0] = child;
  }
  public Node(Node child1, Node child2) {
    this.m_child_count = 2;
    this.m_child_nodes = new Node[this.m_child_count];
    this.m_child_nodes[0] = child1;
    this.m_child_nodes[1] = child2;
  }
  public Node(Node child1, Node child2, Node child3) {
    this.m_child_count = 3;
    this.m_child_nodes = new Node[this.m_child_count];
    this.m_child_nodes[0] = child1;
    this.m_child_nodes[1] = child2;
    this.m_child_nodes[2] = child3;
  }
  public Node(Node child1, Node child2, Node child3, Node child4) {
    this.m_child_count = 3;
    this.m_child_nodes = new Node[this.m_child_count];
    this.m_child_nodes[0] = child1;
    this.m_child_nodes[1] = child2;
    this.m_child_nodes[2] = child3;
    this.m_child_nodes[3] = child4;
  }

  public abstract double evaluate(double x, double y);
  
  public abstract Node get_copy();
  
  public int get_tree_size() {
    int size = 1;
    for (int ind = 0; ind < this.m_child_count; ind++)
      size += this.m_child_nodes[ind].get_tree_size();
    return size;
  }
  
  public int get_child_count() {
    return this.m_child_count;
  }
  
  public int get_leaf_count() {
    if (this.m_child_count == 0)
      return 1;
    else {
      int size = 0;
      for (int ind = 0; ind < this.m_child_count; ind++)
        size += this.m_child_nodes[ind].get_leaf_count();
      return size;
    }
  }
  
  public Node get_child(int index) {
    return this.m_child_nodes[index];
  }
  
  public void set_child(int index, Node child) {
    this.m_child_nodes[index] = child;
  }
  
  public void write_tree(BufferedWriter file, String id) throws IOException {
    file.write(id + " [label=\"" + this.get_string() + "\"]\n");
    for (int ind = 0; ind < this.m_child_count; ind++) {
      String child_id = id + (ind+1);
      file.write(id + " -> " + child_id + "\n");
      this.m_child_nodes[ind].write_tree(file, child_id);
    }
  }
  
  public abstract String get_string();

}
