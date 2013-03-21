package org.ga.gui;

import org.ga.algorithm.*;
import java.awt.*;
import javax.swing.*;

import java.awt.event.*;

public class ImagePanel extends JPanel implements ActionListener {
  
  public static final class Status {
    public static final int DISCARD = 0;
    public static final int KEEP = 1;
    public static final int PARENT = 2;
  }
  
  private GraphPanel m_graph;
  private JButton m_redraw_button;
  private JButton m_discard_button;
  private JButton m_save_button;
  private JButton m_status_button;
  private JButton m_zoom_button;
  private int m_status;
  
  public ImagePanel() {
    super();
    // General look
    this.setBorder(BorderFactory.createLineBorder(new Color(0,0,0)));
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    // Button panel layout
    FlowLayout button_layout = new FlowLayout();
    button_layout.setVgap(0);
    button_layout.setHgap(1);
    
    // Button panel construction.
    JPanel button_panel = new JPanel(button_layout);
    //button_panel.setBackground(new Color(0,0,255));
    this.m_zoom_button = this.add_button(button_panel, "Z");
    this.m_discard_button = this.add_button(button_panel, "N");
    //this.m_redraw_button = this.add_button(button_panel, "R");
    this.m_save_button = this.add_button(button_panel, "S");
    this.m_status_button = this.add_button(button_panel, "?");
    
    this.update_status(0);
    
    // Graph panel
    this.m_graph = new GraphPanel();
    
    // Add both panels
    this.add(button_panel);
    this.add(this.m_graph);
    
    // Minimize the size of the button panel.
    button_panel.setMaximumSize(this.getPreferredSize());
  }
  
  public ImagePanel(GraphPanel copy) {
    super();
    // General look
    this.setBorder(BorderFactory.createLineBorder(new Color(0,0,0)));
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    // Button panel layout
    FlowLayout button_layout = new FlowLayout();
    button_layout.setVgap(0);
    button_layout.setHgap(1);
    
    // Button panel construction.
    JPanel button_panel = new JPanel(button_layout);
    //button_panel.setBackground(new Color(0,0,255));
//    this.m_zoom_button = this.add_button(button_panel, "Z");
//    this.m_discard_button = this.add_button(button_panel, "N");
    this.m_redraw_button = this.add_button(button_panel, "R");
    this.m_save_button = this.add_button(button_panel, "S");
//    this.m_status_button = this.add_button(button_panel, "?");
    
    // Graph panel
    this.m_graph = new GraphPanel(copy);
    
    // Add both panels
    this.add(button_panel);
    this.add(this.m_graph);
    
    // Minimize the size of the button panel.
    button_panel.setMaximumSize(this.getPreferredSize());
  }

  private void update_status(int status) {
    this.m_status = status % 3;
    if (this.m_status < 0)
      this.m_status += 3;
    if (this.m_status == Status.DISCARD) {
      this.m_status_button.setBackground(Color.red);
      this.m_status_button.setText("-");
    }
    else if (this.m_status == Status.KEEP) {
      this.m_status_button.setBackground(Color.yellow);
      this.m_status_button.setText("+");
    }
    else { // Status.PARENT
      this.m_status_button.setBackground(Color.green);
      this.m_status_button.setText("#");
    }
  }
  
  public void redraw_function() {
    this.m_graph.draw_function();
    this.m_graph.paint(this.m_graph.getGraphics());
  }
  
  public void mutate_function() {
    this.m_graph.mutate_function();
    this.redraw_function();
    this.repaint();
  }
  
  public void clone_function(NodeTree[] parents) {
    this.m_graph.clone_function(parents);
    this.redraw_function();
    this.repaint();
  }

  public void crossbreed_function(NodeTree[] parents, int type) {
    this.m_graph.crossbreed_function(parents, type);
    this.redraw_function();
    this.repaint();
  }

  public void create_function() {
    this.m_graph.create_function();
    this.redraw_function();
  }
  
  public NodeTree get_function() {
    return this.m_graph.get_function();
  }
  
  public void actionPerformed(ActionEvent event) {
    if (event.getSource() == this.m_discard_button) {
      this.m_graph.create_function();
      this.m_graph.draw_function();
      this.m_graph.paint(this.m_graph.getGraphics());
    }
    if (event.getSource() == this.m_redraw_button) {
      this.m_graph.draw_function();
      this.m_graph.paint(this.m_graph.getGraphics());
    }
    if (event.getSource() == this.m_save_button) {
      this.m_graph.save();
    }
    if (event.getSource() == this.m_status_button) {
      this.update_status(++this.m_status);
    }
    if (event.getSource() == this.m_zoom_button) {
      //this.update_status(++this.m_status);
      if (this.m_graph.get_function() != null) {
        WindowFunction zoom_window = new WindowFunction(this.m_graph);
        zoom_window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        zoom_window.setVisible(true);
      }
    }
    //this.m_graph.draw(this.m_graph.getGraphics());
    //System.out.println("Tekstiä");
  }
  
  public int get_status() {
    return this.m_status;
  }
  
  public GraphPanel get_graph_panel() {
    return this.m_graph;
  }
  
  private JButton add_button(JPanel panel, String label) {
    JButton button = new JButton(label);
    button.setMargin(new Insets(0,0,0,0));
    button.setPreferredSize(new Dimension(17,17));
    button.addActionListener(this);
    panel.add(button);
    return button;
  }
}
