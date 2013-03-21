package org.ga.gui;

import java.awt.*;
import javax.swing.*;
import org.ga.algorithm.*;

public class PopulationPanel extends JPanel {
//  private Population m_population;
  private ImagePanel[] m_population;
  public PopulationPanel() {
    super();
    GridLayout layout = new GridLayout(5, 5);
    layout.setHgap(5);
    layout.setVgap(5);
    this.setLayout(layout);
    this.m_population = new ImagePanel[25];
    for (int ind = 0; ind < 25; ind++) {
      this.m_population[ind] = new ImagePanel();
      this.add(this.m_population[ind]);
    }
  }
  public void redraw_functions() {
    for (int ind = 0; ind < this.m_population.length; ind++) {
      this.m_population[ind].redraw_function();
    }
  }
  public void mutate_functions() {
    for (int ind = 0; ind < this.m_population.length; ind++) {
      if (this.m_population[ind].get_status() == ImagePanel.Status.DISCARD)
        this.m_population[ind].mutate_function();
    }
  }
  public void generate_functions() {
    for (int ind = 0; ind < this.m_population.length; ind++) {
      if (this.m_population[ind].get_status() == ImagePanel.Status.DISCARD)
        this.m_population[ind].create_function();
    }
  }
  
  public void clone_functions() {
    NodeTree[] parents = this.get_parents();
    if (parents == null) {
      System.err.println("Can't clone: no parents specified.");
      // this.generate_functions(); // do this???
      return;
    }
    for (int ind = 0; ind < this.m_population.length; ind++) {
      if (this.m_population[ind].get_status() == ImagePanel.Status.DISCARD)
        this.m_population[ind].clone_function(parents);
    }
  }
  
  public void crossbreed_functions(int type) {
    NodeTree[] parents = this.get_parents();
    if (parents == null) {
      System.err.println("Can't crossbreed: no parents specified.");
      // this.generate_functions(); // do this???
      return;
    }
    for (int ind = 0; ind < this.m_population.length; ind++) {
      if (this.m_population[ind].get_status() == ImagePanel.Status.DISCARD)
        this.m_population[ind].crossbreed_function(parents, type);
    }
  }
  
  private NodeTree[] get_parents() {
    int parent_count = 0;
    for (int ind = 0; ind < this.m_population.length; ind++) {
      if (this.m_population[ind].get_status() == ImagePanel.Status.PARENT &&
          this.m_population[ind].get_function() != null)
        parent_count++;
    }
    
    if (parent_count == 0)
      return null;
    
    NodeTree[] parents = new NodeTree[parent_count];
    int parent_index = 0;
    for (int ind = 0; ind < this.m_population.length; ind++) {
      if (this.m_population[ind].get_status() == ImagePanel.Status.PARENT &&
          this.m_population[ind].get_function() != null) {
        parents[parent_index] = this.m_population[ind].get_function();
        parent_index++;
      }
    }
    return parents;
  }
}
