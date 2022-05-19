import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
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
            while(true) {
                OutputStream output = this.getTCPsocket().getOutputStream();
                byte[] data = this.readFromMessageBuffer().getBytes();
                output.write(data);
                PrintWriter writer = new PrintWriter(output, true);
                writer.println(output);
            }
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
