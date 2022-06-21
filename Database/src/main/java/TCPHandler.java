import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import thriftserver.Sensor;

public class TCPHandler extends Thread {


  private Socket clientSocket = null;
  private InputStream inputStream = null;
  private DataInputStream dataInputStream = null;
  private DataOutputStream output= null;


  TCPHandler(Socket socket) throws IOException {

    this.clientSocket = socket;
    DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
    DataInputStream dataInputStream = new DataInputStream(inputStream);

  }

public void run (){
    String arrayForMessages[] = null;
    String dbname = System.getenv("DATABASENAME");
   String tcpMessage = null;
  try {
    tcpMessage = dataInputStream.readUTF();
  } catch (IOException e) {
    e.printStackTrace();
  }
  System.out.println("Nachrich angekommen: "+ tcpMessage);
  if(tcpMessage.contains("PREPARE")){
    arrayForMessages = tcpMessage.split(";");
    try {
      output.writeUTF("READY;"+arrayForMessages[1]+";"+arrayForMessages[2]);
    } catch (IOException e) {
      e.printStackTrace();
    }


  }
  else if(tcpMessage.contains("COMMIT")){

    try {
      output.writeUTF("ACK;"+ arrayForMessages[1]+";"+arrayForMessages[2]);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}


}
