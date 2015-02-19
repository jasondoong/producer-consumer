package com.jason;

import org.junit.Test;

import java.util.Observable;
import java.util.Observer;

import static org.junit.Assert.assertEquals;

public class ObservableBufferImplTest {
  @Test
  public void testNofifyBufferSizeChange() throws InterruptedException {
    ObservableBufferImpl<String> buffer = new ObservableBufferImpl<String>(10);
    TestObserver observer = new TestObserver();
    buffer.addObserver(observer);

    //change buffer three times
    buffer.put("abc");
    buffer.put("def");
    buffer.take();
    //As each put() and take() will fire a notify, so observer will receive three notifies.
    assertEquals(3,observer.getUpdateCount());
  }
}

class TestObserver implements Observer{

  int count = 0;
  @Override
  public void update(Observable o, Object arg) {
    count++;
  }

  public int getUpdateCount(){
    return count;
  }
}
