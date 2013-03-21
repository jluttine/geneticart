package org.ga.gui;

import javax.swing.*;
//import javax.swing.border.*;
import java.awt.*;
import java.awt.image.*;
import java.util.*;
import javax.imageio.*;
import java.io.*;
import org.ga.algorithm.*;

public class GraphPanel extends JPanel implements Runnable {

  int data_pixels[];
  int data_width;
  int data_height;
  NodeTree function_tree;
  Image image;
  MemoryImageSource source;
  private boolean m_show_drawing;
  private boolean m_stop_drawing;
  private Thread m_drawing_thread;
  private static Random random = new Random();

  public GraphPanel() {
    super(false);
    this.setBackground(new Color(0,0,0));
    //this.setBorder(BorderFactory.createLineBorder(new Color(255,0,0)));
    //this.create_function();
    this.m_show_drawing = false;
  }
  
  public GraphPanel(GraphPanel copy) { //NodeTree function) {
    this();
    this.function_tree = copy.function_tree.get_copy();
    this.m_show_drawing = true;
    this.data_width = copy.data_width;
    this.data_height = copy.data_height;
    this.setPreferredSize(new Dimension(this.data_width,this.data_height));
    this.image = copy.image.getScaledInstance(copy.image.getWidth(null),
                                              copy.image.getHeight(null),
                                              Image.SCALE_FAST);
  }
  
  public void create_function() {
    int size = (int)(random.nextDouble() * 5 + 8);
    this.function_tree = new NodeTree(2, size);//create_node(1, 8);
    this.image = null;
    this.source = null;
  }
  
  public void run() {
    this.m_stop_drawing = false;
    this.do_drawing(5);
    this.paint(this.getGraphics());
  }
  
  public void stop() {
    if (this.m_drawing_thread != null) {
      this.m_stop_drawing = true;
      try {
        this.m_drawing_thread.join();
      }
      catch (InterruptedException excep) {
        System.err.println("GraphPanel.draw_function thread exception: Interrupted.");
      }
    }
  }
  
  private void do_drawing(int show_drawing_size) {
    // Draws the function all over.
    this.data_width = this.getWidth();
    this.data_height = this.getHeight();
    int size = this.data_width * this.data_height;
    this.data_pixels = new int[size];
    
    this.source = new MemoryImageSource(this.data_width, this.data_height, this.data_pixels, 0, this.data_width);
    this.source.setAnimated(true);
    this.image = createImage(this.source);
    
    //Graphics graphics = this.getGraphics();
    for (int xnd = 0; xnd < this.data_width && !this.m_stop_drawing; xnd++) {
      for (int ynd = 0; ynd < this.data_height; ynd++ ) {
        // ADJUST:
        // HERE YOU CAN CHANGE THE VIEW OF THE FUNCTION BY CHANGING THE COEFFICIENT AND THE SUBSTRACTOR!
        // 2 * ... - 1, means axis [-1, 1] and so on...
        double x = 10*(double)xnd / (this.data_width - 1)-5;
        double y = 10*(double)ynd / (this.data_height - 1)-5;
        double value = this.function_tree.evaluate(x, y);
        // This is continuous (repetative?) color mapping.
        // Continuous color
        /*
        int color = 0xFF & (char)(value * 127.5 + 127.5);
        red = green = blue = color;
        //*/
        // 
        //*
        int color = 0;
        if (value >= 1.0) color = 0xFF;
        else if (value > -1.0) color = 0xFF & (char)(value * 127.5 + 127.5);
        //*/
        this.data_pixels[xnd+ynd*this.data_width] = (0xFF << 24) | (color << 16) | (color << 8) | color;
      }
      if (this.m_show_drawing && (xnd % show_drawing_size) == (show_drawing_size - 1)) {
        source.newPixels(xnd - show_drawing_size, 0, show_drawing_size, this.data_height);
        this.getGraphics().drawImage(this.image,
                           0,
                           0,
                           this.getWidth(),
                           this.getHeight(),
                           0,
                           0,
                           this.data_width,
                           this.data_height,
                           null);
      }
    }

    source.newPixels(0, 0, this.data_width, this.data_height);
  }
  
  public void draw_function() {
    if (this.function_tree == null) {
      //System.err.println("GraphPanel.draw_function: no function.");
      return;
    }

    if (this.m_show_drawing) {
      // Draw in a new thread.
      // Stop old thread.
      this.stop();
      this.m_drawing_thread = new Thread(this);
      this.m_drawing_thread.setPriority(Thread.MIN_PRIORITY);
      this.m_drawing_thread.start();
      //...
    }
    else {
      // Draw in this thread.
      this.do_drawing(0);
    }

  }
  
  public void mutate_function() {
    if (this.function_tree != null)
      this.function_tree.mutate();
  }
  
  public void clone_function(NodeTree[] clonables) {
    NodeTree clone_tree = NodeTree.clone(clonables);
    if (clone_tree != null)
      this.function_tree = clone_tree;
  }

  public void crossbreed_function(NodeTree[] parents, int type) {
    NodeTree child_tree = NodeTree.crossbreed(parents, type);
    if (child_tree != null)
      this.function_tree = child_tree;
  }

  public void save() {
    if (this.function_tree != null) {
      WindowSave save = new WindowSave(this.function_tree, this.image);
      save.setVisible(true);
    }
  }
  
 
  public NodeTree get_function() {
    return this.function_tree;
  }
  
  public void paint(Graphics g) {
    super.paint(g);
    // Blits the already drawn function graph.
    if (this.image != null) {
      g.drawImage(image,
                  0,
                  0,
                  this.getWidth(),
                  this.getHeight(),
                  0,
                  0,
                  this.data_width,
                  this.data_height,
                  null);
    }
  }
  
}
