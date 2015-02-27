package com.client;

/**
 * Created by jason on 2015/2/19.
 */
import com.jason.ProducerConsumerSystem;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by jason on 2015/2/17.
 */
public class MainGui extends JPanel
  implements ActionListener, Observer {

  protected JButton b1;
  protected JTextArea textArea;

  public MainGui() {
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    createProducerArea();
    createConsumerArea();
    createStartButton();
    createTextArea();
  }

  private void createProducerArea() {
    add(new TextField("Producer:"));
  }

  private void createConsumerArea() {
    add(new TextField("Consumer:"));
  }

  private void createStartButton() {

    b1 = new JButton("Start");
    b1.addActionListener(this);
    b1.setToolTipText("Click this button to start producer/consumer system.");

    JPanel panel = alignRight(b1);
    //Add Components to this container, using the default FlowLayout.
    add(panel);
  }

  //Todo: find better way to align the componet left/right
  private JPanel alignRight(JComponent component) {
    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
    panel.add(component);
    return panel;
  }

  private void createTextArea() {
    textArea = new JTextArea(20, 50);
    textArea.setEditable(false);
    JScrollPane scrollPane = AddToScrollPane();
    makeItAutoScroll();
    add(scrollPane);
  }



  private void makeItAutoScroll() {
    DefaultCaret caret=(DefaultCaret)textArea.getCaret();
    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
  }

  private JScrollPane AddToScrollPane() {
    return new JScrollPane(textArea);
  }

  public void actionPerformed(ActionEvent e) {
    startProducerConsumerSystem();
  }

  private void startProducerConsumerSystem() {
    int bufferSize = 20;
    ProducerConsumerSystem system = new ProducerConsumerSystem(bufferSize);
    system.adddQueueSizeObserver(this);
    try {
      system.startProducer(MyProducer.class).instanceNum(2);
      system.startConsumer(MyConsumer.class).instanceNum(5);
    } catch (IllegalAccessException|InstantiationException e1) {
      e1.printStackTrace();
    }

    //you can add new producer after system starts
    system.startProducer(new MyProducer("dynamic"));

    //you can add new consumer too
    system.startConsumer(new MyConsumer());
  }

  public void update(Observable obj, Object arg) {
    textArea.append(arg.toString()+"\n");
  }

}
