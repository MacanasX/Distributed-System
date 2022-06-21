import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer extends Thread {
  public static final int PORT = 4000;
  private ServerSocket serverSocket;


TCPServer() throws IOException {

  serverSocket = new ServerSocket(PORT);

}


  public void run(){

    while(true){

      try {
        Socket socket = serverSocket.accept();
        TCPHandler tcpclient = new TCPHandler(socket);
       tcpclient.start();
      } catch (IOException e) {
        e.printStackTrace();
      }



    }


  }

}
