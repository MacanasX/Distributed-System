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

    public void listen() throws IOException, ClassNotFoundException {
        // lesen

     /*   ObjectInputStream ois = new ObjectInputStream(this.TCPsocket.getInputStream());
        String message = (String) ois.readObject();
        System.out.println(message);
        */
       // ObjectInputStream ois = new ObjectInputStream(this.TCPsocket.getInputStream());
      //  String message = (String) ois.readObject();
       // System.out.println("Message Received: " + message);

        // Send a response information to the client application
      //  ObjectOutputStream oos = new ObjectOutputStream(this.TCPsocket.getOutputStream());
       // oos.writeObject("Hi...");

       // ois.close();
       // oos.close();
        InputStream inputStream = this.TCPsocket.getInputStream();
        // create a DataInputStream so we can read data from it.
        DataInputStream dataInputStream = new DataInputStream(inputStream);

        // read the message from the socket
        String message = dataInputStream.readUTF();
        System.out.println("Got a Message from a Client: ");
        System.out.println(message);
     //  BufferedReader in = new BufferedReader(new InputStreamReader(this.TCPsocket.getInputStream()));
      // String text = in.readLine();

        this.TCPsocket.close();


        // schreiben
     //   BufferedWriter out = new BufferedWriter(new OutputStreamWriter(TCPsocket.getOutputStream()));
      //  out.write(text.toUpperCase());
      //  out.newLine();
      //  out.flush();

        // aufräumen
       // out.close();
       // in.close();


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
    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
    }

}

}
