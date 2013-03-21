
package org.ga;

import javax.swing.JFrame;

import org.ga.gui.WindowMain;

public class GeneticArt implements Runnable {

  public void run() {
    WindowMain window = new WindowMain();
    // Exit the application when closing this window.
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setVisible(true);
  }

  /**
	 * @param args
	 */
  public static void main(String[] args) {
    // This bit of code is copied from Sun's tutorials because
    // they advised it for thread safety reasons.
    javax.swing.SwingUtilities.invokeLater(new GeneticArt());
  }
}
