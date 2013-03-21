package org.ga.gui;

import java.awt.event.ActionEvent;
import java.awt.image.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import org.ga.algorithm.*;
import java.awt.*;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class WindowSave extends JDialog implements ActionListener {
  private NodeTree m_function;
  private BufferedImage m_image;
  private JButton m_picture_button;
  //private JButton m_function_button;
  private JButton m_graph_button;
  private static File last_filepath = null;
  
  public WindowSave(NodeTree function, Image image) {
    super((Frame)null, "otsikko", true);
    // Copy the data.
    this.m_function = function;
    this.m_image = new BufferedImage(image.getWidth(null),
                                     image.getHeight(null),
                                     BufferedImage.TYPE_INT_RGB);  
    this.m_image.getGraphics().drawImage(image, 0, 0, null);
    
    GridLayout layout = new GridLayout(2, 1);
    layout.setVgap(5);
    JPanel panel = new JPanel();
    panel.setBorder(new EmptyBorder(10,10,10,10));
    panel.setLayout(layout);
    this.m_picture_button = new JButton("Save as picture");
    //this.m_function_button = new JButton("Save as function");
    this.m_graph_button = new JButton("Save as graph");
    panel.add(this.m_picture_button);
    //panel.add(this.m_function_button);
    panel.add(this.m_graph_button);
    this.m_picture_button.addActionListener(this);
    this.m_graph_button.addActionListener(this);
    this.add(panel);
    this.pack();
  }
  
  public void actionPerformed(ActionEvent event) {
    if (event.getSource() == this.m_picture_button) {
      this.save_image();
      this.dispose();
    }
    if (event.getSource() == this.m_graph_button) {
      this.save_function();
      this.dispose();
    }
  }
 
  public void save_function() {
    JFileChooser dialog = new JFileChooser(WindowSave.last_filepath);
    ExampleFileFilter filter = new ExampleFileFilter();
    filter.addExtension("dot");
    filter.setDescription("DOT files for GraphViz");
    dialog.setFileFilter(filter);
    dialog.showSaveDialog(this);
    File file = dialog.getSelectedFile();
    if (file != null) {
      this.m_function.save_tree(file);
      WindowSave.last_filepath = file;
    }
  }
  
  public void save_image() {
    JFileChooser dialog = new JFileChooser(WindowSave.last_filepath);
    ExampleFileFilter filter = new ExampleFileFilter();
    filter.addExtension("jpg");
    filter.addExtension("jpeg");
    filter.setDescription("JPEG image file");
    dialog.setFileFilter(filter);
    dialog.showSaveDialog(this);
    //this.function_tree.save_tree(dialog.getSelectedFile());
//    File file = dialog.getSelectedFile();
    File file = dialog.getSelectedFile();
    if (file != null) {
      try {
        WindowSave.last_filepath = file;
        BufferedImage buf_image = new BufferedImage(this.m_image.getWidth(), this.m_image.getHeight(), BufferedImage.TYPE_INT_RGB);
        buf_image.getGraphics().drawImage(this.m_image, 0, 0, null);
        ImageIO.write(buf_image, "jpeg", file);
      }
      catch (IOException excep) {
        System.err.println("GraphPanel.save_image: io exception\n");
      }
    }
  }
 
}
