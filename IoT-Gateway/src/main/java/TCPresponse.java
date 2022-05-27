import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPresponse extends Thread {


  private ServerSocket serverSocket = null;


  TCPresponse() throws IOException {
    this.serverSocket = new ServerSocket(53257);


  }

  public void run() {
    while (true) {
      Socket client = null;
      try {
        client = serverSocket.accept();
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
       // System.out.println(message);
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

