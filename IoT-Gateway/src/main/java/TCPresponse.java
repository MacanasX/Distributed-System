import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class TCPresponse extends Thread {



  private ServerSocket serverSocket = null;



  int logCounter = 0;
  Calendar calendar;


  TCPresponse() throws IOException {
    this.serverSocket = new ServerSocket(53258);

  }

  public void run() {

    while (true) {
      Socket client = null;
      try {
        client = serverSocket.accept();
      //  logger.addHandler(filehandler);
      //  SimpleFormatter formatter = new SimpleFormatter();
      //  filehandler.setFormatter(formatter);

      } catch (IOException e) {
        e.printStackTrace();
      }

      String response;
      InputStream inputStream = null;
      try {
        inputStream = client.getInputStream();
      } catch (IOException e) {
        e.printStackTrace();
      }

      DataInputStream dataInputStream = new DataInputStream(inputStream);

      String message = null;
      try {
        message = dataInputStream.readUTF();
        System.out.println(message);
        RttLogger.memory_End.add(ZonedDateTime.now().toInstant().toEpochMilli());
        Calendar calendar = Calendar.getInstance();
        // Getting the time in milliseconds.


        long endeMilliSeconds = calendar.getTimeInMillis();
        RttLogger.logger.log(Level.INFO,"RTT for " + logCounter + " :" + RttLogger.getRtt() +" ms");
      //  System.out.println("----Endzeit beträgt: " + endeMilliSeconds);
        RttLogger.endTime = 0;
        logCounter++;

      } catch (IOException e) {
        e.printStackTrace();
      }
      try {
        client.close();
      } catch (IOException e) {
        e.printStackTrace();
      }


    }
  }
}

