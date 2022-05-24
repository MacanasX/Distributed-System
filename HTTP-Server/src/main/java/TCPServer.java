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

        InputStream inputStream = this.TCPsocket.getInputStream();

        DataInputStream dataInputStream = new DataInputStream(inputStream);

        // read the message from the socket
        String[] tmp_array;
        String message = dataInputStream.readUTF();
        System.out.println("Got a Message from: " + this.TCPsocket.getInetAddress() );
        System.out.println(message);
        tmp_array = message.split(",");



        this.TCPsocket.close();


    }


public void run(){

    try {
        this.listen();
    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
    }

}

}
