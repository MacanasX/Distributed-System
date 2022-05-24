import java.util.ArrayList;

public class SharedBuffer {


  private boolean available = false;
  private ArrayList<String> data = new ArrayList<>();
  private int bufferSize = 0;

  public synchronized void put(String x) {
    while(available) {
      try {
        wait();
      }
      catch(InterruptedException e) {}
    }
    data.add(x);
    bufferSize++;
    if(bufferSize == 4) {
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
      available = false;
      notifyAll();
    }

    return tmp;
  }


  public int getBufferSize()
  {

    return data.size();
  }

}
