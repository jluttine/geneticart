
package org.ga.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WindowMain extends JFrame {
  public WindowMain() {
    super("Otsikko");
    //Population population = new Population(9); // 9 is population size...
    PopulationPanel population_panel = new PopulationPanel();
    SidePanel side_panel = new SidePanel(population_panel);
    this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));//FlowLayout());
    this.getContentPane().add(side_panel);
    this.getContentPane().add(population_panel);
    //this.pack();
    this.setSize(new Dimension(800,600));//this.getPreferredSize());
    //population_panel.redraw_functions();
    //this.lockInMinSize(300, 300);
  }

  private void lockInMinSize(int width, int height) {
    //Ensures user cannot resize frame to be smaller than frame is right now.
    final int origX = width;
    final int origY = height;
    final JFrame frame = this;
    this.addComponentListener(new java.awt.event.ComponentAdapter() {
       public void componentResized(ComponentEvent event) {
         frame.setSize(
             (frame.getWidth() < origX) ? origX : frame.getWidth(),
             (frame.getHeight() < origY) ? origY : frame.getHeight());
             }
       });
    }}
