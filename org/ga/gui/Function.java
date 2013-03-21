
package org.ga.gui;
import org.ga.algorithm.*;
public class Function {
  private NodeTree m_node_tree;
  //private ColorNode m_color_tree;
  //private int m_status; // parent, keep, kill
  public Function(int min_level, int max_level) {
    this.m_node_tree = new NodeTree(min_level, max_level);
    
  }
  void mutate() {
    this.m_node_tree.mutate();
  }
}
