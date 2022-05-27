import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TCPServer implements Runnable {

    private Socket TCPsocket;


    TCPServer(Socket mySocket) throws IOException {
    this.TCPsocket = mySocket;


    }

    public void listen() throws IOException, ClassNotFoundException {
        String destination = System.getenv("DESTINATIONTCP");
        String response;

      //  InputStream inputStream = this.TCPsocket.getInputStream();
     //  BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        DataInputStream input = new DataInputStream(this.TCPsocket.getInputStream());



        String message = input.readUTF();
        System.out.println("Got a Message from: " + this.TCPsocket.getInetAddress() );
        System.out.println(message);
        HTTPPost header = new HTTPPost();
        response=header.checkHtppMessage(message);
       // PrintStream output = new PrintStream(TCPsocket.getOutputStream());
       // output.println(response);

       Socket dest = new Socket(destination, 53258);

        OutputStream outputStream = dest.getOutputStream();
         //create a data output stream from the output stream so we can send data through it
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        dataOutputStream.writeUTF(response);
       // dataOutputStream.flush();
      //  dataOutputStream.close();

    }


public void run(){

    try {
      System.out.println("THREAD ERZEUGT!");
        this.listen();
    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
    }

}

}
