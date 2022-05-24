import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class TCPHandler extends Thread {

    private Socket TCPsocket;
    public static volatile ArrayList<String> messageBuffer = new ArrayList<>();
   public final static Integer LOCK = 0;
   private SharedBuffer myBuffer = null;




  TCPHandler(Socket mySocket, SharedBuffer myBuffer){

        this.TCPsocket = mySocket;
        this.myBuffer = myBuffer;
       // this.myPort=myPort;

    }

    public void run(){

            try {
              OutputStream outputStream = this.TCPsocket.getOutputStream();
              // create a data output stream from the output stream so we can send data through it
              DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            String TCPmessage = "";
            // System.out.println("Sending string to the ServerSocket");

            // if (TCPHandler.messageBuffer.size() != 0) {
            while (myBuffer.getBufferSize() > 0) {

              TCPmessage = TCPmessage + myBuffer.get();
              if (myBuffer.getBufferSize() > 0)
                TCPmessage = TCPmessage + "," + "\n";


            }

            myBuffer.clearBuffer();


            HTTPRequest myrequest = new HTTPRequest();
            TCPmessage = myrequest.generateHTTPHeader(TCPmessage);
            dataOutputStream.writeUTF(TCPmessage);
            dataOutputStream.flush(); // send the message
            dataOutputStream.close();
            this.TCPsocket.close();
          } catch(IOException e){
            e.printStackTrace();
          }

    }

    public Socket getTCPsocket(){

        return TCPsocket;
    }

    public String readFromMessageBuffer(){

        for(int i = 0 ; i < messageBuffer.size(); i++)
        {



        }
        if(messageBuffer.size() == 0)
            return "Leer";
        else{
            return  messageBuffer.get(0);
        }


    }

    public void setMessageBuffer(ArrayList<String> messageBuffer){

        this.messageBuffer = messageBuffer;
    }




}
