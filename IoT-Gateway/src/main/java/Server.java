//package com.pgx.java.socket;
import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;


public class Server extends Thread {
    private final DatagramSocket udpSocket;
    private final int port;
    private final int threadname;
    public static Integer ALIVE_SENSOR ;
    private ArrayList<String> messageBuffer;

    public synchronized void writeIntoMessageBuffer(String message){

        messageBuffer.add(message);

    }

    public void run()
    {
            while(true) {
                String [] sensoren = {"sensor1","sensor2","sensor3","sensor4"};

                for(int i = 0 ; i < ALIVE_SENSOR;i ++ ) {
                    try {
                        InetAddress Address = InetAddress.getByName(sensoren[i]);
                        String Hostname = Address.toString();
                       // checkSensor(Hostname,1235);
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }


                }


            }
       /* while(messageBuffer.size() < 4){

            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
  */
    }
    public Server(int port, DatagramSocket socket, int name) throws SocketException, IOException {
        this.port = port;
        this.udpSocket = socket;
        this.threadname = name;
        this.messageBuffer = null;


    }
    public void setMessageBuffer(ArrayList<String> messageBuffer){

        this.messageBuffer = messageBuffer;
    }





    private void printreceivedMessage(String msg)  {

       String message = msg.replace("{","");
       String message2 = message.replace("}","");
      // message=message.substring(1,message.length()-1);
        String[] messageSplit = message2.split(",");
       //String a = ""gasg"";

        for (String a:messageSplit
             ) {
          System.out.println(a);
        }
        System.out.println("\n");


    }
    private void  listen() throws Exception {


        Threadlistening();
    }
    private synchronized void PullRequest() throws IOException, InterruptedException {
        String [] sensoren = {"sensor1","sensor2","sensor3","sensor4"};
        String pullMessage = "PULL";
        checkSensor check = new checkSensor();
        new Thread(check).start();
        this.wait(4000);
        byte [] messageBuffer = pullMessage.getBytes();

        for(int i = 0 ; i < 4; i++)
        {   try {
            //if (checkSensor.isAlive.get(i)) {
                InetAddress Address = InetAddress.getByName(sensoren[i]);
                DatagramPacket p = new DatagramPacket(messageBuffer, messageBuffer.length, Address,
                    1235);
                udpSocket.send(p);
            //}
        }
        catch (ArrayIndexOutOfBoundsException | UnknownHostException e) {
            e.printStackTrace();
        }
        }

    }
    private synchronized void Threadlistening() throws IOException, InterruptedException {
        String msg;
        byte[] buf = new byte[256];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);

        // blocks until a packet is received
        udpSocket.receive(packet);

        msg = new String(packet.getData()).trim();
        this.writeIntoMessageBuffer(msg);
        this.printreceivedMessage(msg);
      //  System.out.println("Größe vom BUffer: " + messageBuffer.size());

       // System.out.println(
          //    "Meine ID:" + this.threadname +  "Message from " + packet.getAddress().getHostAddress() + ": " + msg);


        Thread.sleep(5000);
    }

    public static void main(String[] args) throws Exception {
        String address = System.getenv("DESTINATIONTCP");
        int sensoren =Integer.parseInt(System.getenv("NUMBERPORTS"));;
        ALIVE_SENSOR = Integer.parseInt(System.getenv("NUMBERPORTS"));
        System.out.println("Sensoren am leben: " + ALIVE_SENSOR);
        ArrayList<String> bufferforThreads = new ArrayList<>();





        ArrayList<Server> Threadlist = new ArrayList<>();
        DatagramSocket socket= new DatagramSocket(1234);
        Server Server = new Server(1234,socket,4);



        while(true){
            checkSensor.isAlive.clear();
            System.out.println("Sensoren am leben2 : " + ALIVE_SENSOR);
           // TimeUnit.SECONDS.sleep(2);

            Server.PullRequest();
            byte[] buf = new byte[256];

            DatagramPacket packet = new DatagramPacket(buf,buf.length);

            socket.receive(packet);

            UDPHandler thread =new UDPHandler(packet,bufferforThreads);
            thread.run();
            if((bufferforThreads.size() % 4) == 0)
            {
                TCPHandler tcpClient = new TCPHandler(new Socket(address,53257));
                tcpClient.setMessageBuffer(bufferforThreads);
                tcpClient.run();
            }



        }


       /* System.out.println("-- Running Server at " + InetAddress.getLocalHost() + "--");

        for(int i = 0; i < portNumbers ; i++)
        {
            Server clientthreads = new Server(1234,socket,i);
            clientthreads.setMessageBuffer(bufferforThreads);
            Threadlist.add(clientthreads);
        }

        for(int i = 0; i < Threadlist.size() ; i++)
        {
            Threadlist.get(i).start();
        }
        tcpClient.run();

    }
    */

} }