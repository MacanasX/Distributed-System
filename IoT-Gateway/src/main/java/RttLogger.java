import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.FileHandler;

public class RttLogger {
  public static java.util.logging.Logger logger = java.util.logging.Logger.getLogger("RoundTripTime");;
  public static FileHandler filehandler = null;
  public static long startTime ;
  public static long endTime;
  public static long rttTime;
  public static Queue<Long> memory_Start =  new LinkedList<>();
  public static Queue<Long> memory_End = new LinkedList<>();

  static {
    try {
      filehandler = new FileHandler("/sharedData/RTT/MyRTT.txt");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  public static long getRtt()
  {
    rttTime = memory_End.remove() - memory_Start.remove()  ;

    return rttTime;
  }

  public static long getStartTime()
  {
    startTime = memory_Start.element();


    return startTime;
  }
  public  static long getEndTime()
  {
    endTime = memory_End.element();

    return endTime;

  }




}
