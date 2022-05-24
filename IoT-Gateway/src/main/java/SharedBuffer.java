import java.util.ArrayList;

public class SharedBuffer {


  public volatile boolean available = false;
  private ArrayList<String> data ;
  private int bufferSize = 0;
  private int maxBufferSize= checkSensor.sensors.size();
  SharedBuffer()
  {
    this.data = new ArrayList<>();
  }
  public synchronized void put(String x) {
    while(available) {
      try {
        wait();
      }
      catch(InterruptedException e) {}
    }
    data.add(x);
    bufferSize++;
    maxBufferSize = checkSensor.sensors.size();
    if(bufferSize == maxBufferSize) {
      available = true;
      notifyAll();
    }
  }

  public synchronized String get() {
    while(!available) {
      try {
        wait();
      }
      catch(InterruptedException e) {}
    }


    String tmp = data.get(bufferSize-1);
    data.remove(bufferSize-1);
    bufferSize--;
    if(bufferSize == 0) {
      data.clear();
      available = false;
      notifyAll();
    }

    return tmp;
  }


  public  int getBufferSize()
  {

    return data.size();
  }
  public  void clearBuffer(){
      data.clear();

  }
  public boolean isEmpty(){

    return data.isEmpty();
  }

  }

