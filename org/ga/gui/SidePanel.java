package org.ga.gui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import org.ga.algorithm.*;

public class SidePanel extends JPanel implements ActionListener {
  private JButton m_redraw_button;
  private JButton m_mutate_button;
  private JButton m_generate_button;
  private JButton m_clone_button;
  private JButton m_crossbreed_simple_button;
  private JButton m_crossbreed_seed_button;
  private JButton m_crossbreed_dominant_button;
  private PopulationPanel m_population_panel;
  
  public SidePanel(PopulationPanel population_panel) {
    super();
    this.m_population_panel = population_panel;
    GridLayout layout = new GridLayout(7, 1);
    layout.setVgap(10);
    this.setBorder(new EmptyBorder(10,10,10,10));
    this.setLayout(layout);
    this.m_redraw_button = this.add_button("Redraw");
    this.m_generate_button = this.add_button("Generate");
    this.m_mutate_button = this.add_button("Mutate");
    this.m_clone_button = this.add_button("Clone");
    this.m_crossbreed_simple_button = this.add_button("Crossbreed (simple)");
    this.m_crossbreed_seed_button = this.add_button("Crossbreed (seed)");
    this.m_crossbreed_dominant_button = this.add_button("Crossbreed (dominant)");
    Dimension size = this.getPreferredSize();
    this.setMinimumSize(size);
    this.setMaximumSize(size);
  }
  
  private JButton add_button(String label) {
    JButton button = new JButton(label);
    this.add(button);
    button.addActionListener(this);
    return button;
  }
  
  public void actionPerformed(ActionEvent event) {
    if (event.getSource() == this.m_mutate_button) {
      // Mutate button event.
      this.m_population_panel.mutate_functions();
    }
    if (event.getSource() == this.m_redraw_button) {
      // Redraw button event.
      this.m_population_panel.redraw_functions();
    }
    if (event.getSource() == this.m_generate_button) {
      // Generate population.
      this.m_population_panel.generate_functions();
    }
    if (event.getSource() == this.m_clone_button) {
      // Crossbreed.
      this.m_population_panel.clone_functions();
    }
    if (event.getSource() == this.m_crossbreed_simple_button) {
      // Crossbreed.
      this.m_population_panel.crossbreed_functions(NodeTree.CROSSBREED_SIMPLE);
    }
    if (event.getSource() == this.m_crossbreed_seed_button) {
      // Crossbreed.
      this.m_population_panel.crossbreed_functions(NodeTree.CROSSBREED_SEED);
    }
    if (event.getSource() == this.m_crossbreed_dominant_button) {
      // Crossbreed.
      this.m_population_panel.crossbreed_functions(NodeTree.CROSSBREED_DOMINANT);
    }
  }
}
