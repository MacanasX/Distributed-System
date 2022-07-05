import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class RttLogger {
  public static java.util.logging.Logger logger = java.util.logging.Logger.getLogger("RoundTripTime");;
  public static FileHandler filehandler = null;
  public static int messageID;
  public static int sensorID;
  public static long startTime ;
  public static long endTime;
  public static long rttTime;
  public static long rttTime2;
  private static int  counter=0;
  private static long sum;
  public static Queue<Long> memory_Start =  new LinkedList<>();
  public static Queue<Long> memory_End = new LinkedList<>();

  public static Map<Integer, Long> start = new HashMap<>();
  public static Map<Integer, Long> end = new HashMap<>();

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

/*  public static String format(LogRecord record){
    if(record.getLevel() == Level.INFO){
      return record.getMessage() + "\r\n";
    }
    return "";
  }*/
  /*public static void removeRows(){
    File inputFile = new File("myFile.txt");
    File tempFile = new File("myTempFile.txt");

    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

    String lineToRemove = "bbb";
    String currentLine;

    while((currentLine = reader.readLine()) != null) {
      // trim newline when comparing with lineToRemove
      String trimmedLine = currentLine.trim();
      if(trimmedLine.equals(lineToRemove)) continue;
      writer.write(currentLine + System.getProperty("line.separator"));
    }
    writer.close();
    reader.close();
    boolean successful = tempFile.renameTo(inputFile);
  }*/

  public static long getRtt2(int key) {

    //System.out.print("bool:   "+ start.containsKey(key) );

        if (start.containsKey(key)) {


          rttTime2 = end.get(key) - start.get(key);
          //format(logger.info("test"));
          //MITTELWERT BERECHNEN
          if (counter % 10 == 0 && counter != 0){
            logger.log(Level.INFO,"The calculated median of the last 10 packets is " + sum/10 +" ms\n"); //.substring(42, 52)
             sum = 0;
             counter++;
          } //Jul 04, 2022 11:16:21 AM RttLogger getRtt2
          else {
            counter++;
            sum += rttTime2;
          }

          return rttTime2;
        }


    return 0;
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

  public static Map<Integer, Long> getStart() {
    return start;
  }

  public static void setStart(Map<Integer, Long> start) {
    RttLogger.start = start;
  }




}
