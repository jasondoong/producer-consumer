package com.client;

import javax.swing.*;

/**
 * Created by jason on 2015/2/27.
 */
public class GuiStarter {
  public static void main(String[] args) {
    //creating and showing this application's GUI.
    createAndShowApplication();
  }

  private static void createAndShowApplication() {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        createAndShowGUI();
      }
    });
  }

  /**
   * Create the GUI and show it.  For thread safety,
   * this method should be invoked from the
   * event-dispatching thread.
   */
  static void createAndShowGUI() {

    //Create and set up the window.
    JFrame frame = new JFrame("Producer/Consumer Demo");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //Create and set up the content pane.
    MainGui newContentPane = new MainGui();
    //TestA newContentPane = new TestA();

    frame.setContentPane(newContentPane);

    //Display the window.
    frame.pack();
    frame.setVisible(true);
  }
}
