import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TCPServer implements Runnable {
    private ArrayList<String> receivedData;
    private Socket TCPsocket;


    TCPServer(Socket mySocket) throws IOException {
    this.TCPsocket = mySocket;

    }

    public void listen() throws IOException {
        // lesen
        BufferedReader in = new BufferedReader(new InputStreamReader(this.TCPsocket.getInputStream()));
        String text = in.readLine();
        System.out.println(text);
        // schreiben
     //   BufferedWriter out = new BufferedWriter(new OutputStreamWriter(TCPsocket.getOutputStream()));
      //  out.write(text.toUpperCase());
      //  out.newLine();
      //  out.flush();

        // aufräumen
       // out.close();
        in.close();


      /*  String data = null;
        Socket client = this.TCPsocket.accept();
        String clientAddress = client.getInetAddress().getHostAddress();
        System.out.println("\r\nNew connection from " + clientAddress);

        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        while ( (data = in.readLine()) != null ) {
            System.out.println("\r\nMessage from " + clientAddress + ": " + data);
        }
        */

    }
















public void run(){

    try {
        this.listen();
    } catch (IOException e) {
        e.printStackTrace();
    }

}

}
