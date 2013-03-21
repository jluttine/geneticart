
package org.ga.gui;

import javax.swing.*;
import java.awt.event.*;

// Window for zooming a single function and saving
// it in an image file (jpeg?).
public class WindowFunction extends JFrame implements WindowListener {
  private ImagePanel m_image_panel;
  //private JButton m_redraw_button;
  
  public WindowFunction(GraphPanel copy) {
    super();
    this.m_image_panel = new ImagePanel(copy);
    this.add(this.m_image_panel);
    this.pack();
    this.addWindowListener(this);
  }
  
  public void  windowActivated(WindowEvent e) {}
  public void  windowClosed(WindowEvent e) {}
  public void  windowClosing(WindowEvent e) {
    // Stop drawing thread. This prevents null pointer exceptions
    // in the graph panel.
    this.m_image_panel.get_graph_panel().stop();
  }
  public void  windowDeactivated(WindowEvent e) {}
  public void  windowDeiconified(WindowEvent e) {}
  public void  windowIconified(WindowEvent e) {}
  public void  windowOpened(WindowEvent e) {}
}
