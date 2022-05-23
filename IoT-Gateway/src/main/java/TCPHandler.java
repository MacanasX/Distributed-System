import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class TCPHandler implements Runnable {

    private Socket TCPsocket;
    private ArrayList<String> messageBuffer;


    TCPHandler(Socket mySocket){

        this.TCPsocket = mySocket;
        this.messageBuffer= null;
       // this.myPort=myPort;

    }

    public void run(){
        try {


            OutputStream outputStream = this.TCPsocket.getOutputStream();
            // create a data output stream from the output stream so we can send data through it
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            String TCPmessage="" ;
           // System.out.println("Sending string to the ServerSocket");
            for(int i = 0 ; i < this.messageBuffer.size(); i++) {

                TCPmessage = TCPmessage + this.messageBuffer.get(i);
                if(i < 3)
                    TCPmessage = TCPmessage + "," + "\n";

            }
            this.messageBuffer.clear();
            // write the message we want to send
            dataOutputStream.writeUTF(TCPmessage);
            dataOutputStream.flush(); // send the message
            dataOutputStream.close(); // close the output stream when we're done

               // ObjectOutputStream ois = new ObjectOutputStream(this.TCPsocket.getOutputStream());
              //  BufferedWriter out = new BufferedWriter(new OutputStreamWriter(TCPsocket.getOutputStream()));
                //out.writeUTF("Hello World");

// zeilenumbruch senden
               // ois.newLine();
               //ois.flush();
             /*   OutputStream output = this.getTCPsocket().getOutputStream();
                byte[] data = this.readFromMessageBuffer().getBytes();
                output.write(data);
                PrintWriter writer = new PrintWriter(output, true);
                writer.println(output); */

        } catch (IOException e) {
            e.printStackTrace();
        }


      //  System.out.println("hallo");


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
