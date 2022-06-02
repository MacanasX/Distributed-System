import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class TCPresponse extends Thread {



  private ServerSocket serverSocket = null;

  private Logger logger = null;
  private FileHandler filehandler = null;
  int logCounter = 0;
  Calendar calendar;


  TCPresponse() throws IOException {
    this.serverSocket = new ServerSocket(53258);
    this.logger = Logger.getLogger("MyRTT");
    this.filehandler = new FileHandler("/sharedData/RTT/MyRTT.log");

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

        Calendar calendar = Calendar.getInstance();
        // Getting the time in milliseconds.
        long endeMilliSeconds = calendar.getTimeInMillis();
        logger.info("Endzeit " + logCounter+ ": " + String.valueOf(endeMilliSeconds));
        System.out.println("----Endzeit beträgt: " + endeMilliSeconds);
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

