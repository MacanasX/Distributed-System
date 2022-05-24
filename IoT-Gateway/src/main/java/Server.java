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
    public static Integer ALIVE_SENSOR;
    public static String ADDRESS;
    public static Integer PORT = 1234;
    private SharedBuffer buffer = null;

    public synchronized void writeIntoMessageBuffer(String message) {

       // messageBuffer.add(message);

    }

    public void run() {
        while (true) {

                while (!buffer.isEmpty()) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                    }

                }
                try {
                    PullRequest();
                } catch (IOException | InterruptedException e) {
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


    public Server(int port, DatagramSocket socket, int name, SharedBuffer buffer) throws SocketException, IOException {
        this.port = port;
        this.udpSocket = socket;
        this.threadname = name;
        this.buffer = buffer;


    }

    public void setMessageBuffer(ArrayList<String> messageBuffer) {

       // this.messageBuffer = messageBuffer;
    }


    private void printreceivedMessage(String msg) {

        String message = msg.replace("{", "");
        String message2 = message.replace("}", "");
        // message=message.substring(1,message.length()-1);
        String[] messageSplit = message2.split(",");
        //String a = ""gasg"";

        for (String a : messageSplit
        ) {
            System.out.println(a);
        }
        System.out.println("\n");


    }

    private void listen() throws Exception {

        Threadlistening();
    }

    public void PullRequest() throws IOException, InterruptedException {
        String[] sensoren = {"sensor1", "sensor2", "sensor3", "sensor4"};
        String pullMessage = "PULL";
        System.out.println("AUFGERUFEN!" +  checkSensor.sensors.size());
        //TimeUnit.SECONDS.sleep(2);
        // server.sleep(5000);
        byte[] messageBuffer = pullMessage.getBytes();

        for (int i = 0; i < checkSensor.sensors.size(); i++) {
            try {
                //if (checkSensor.isAlive.get(i)) {
                InetAddress Address = InetAddress.getByName(checkSensor.sensors.get(i));
                DatagramPacket p = new DatagramPacket(messageBuffer, messageBuffer.length, Address,
                    1235);
                udpSocket.send(p);
                // }
            } catch (ArrayIndexOutOfBoundsException | UnknownHostException e) {
                System.out.println("Client ist nicht mehr vorhanden!");
                System.out.print("\033[H\033[2J");
                System.out.flush();
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
        int sensoren = Integer.parseInt(System.getenv("NUMBERPORTS"));
        ;
        ALIVE_SENSOR = Integer.parseInt(System.getenv("NUMBERPORTS"));
        ADDRESS = InetAddress.getByName("iot").toString();
        ArrayList<String> bufferforThreads = new ArrayList<>();
        checkSensor.sensors.add("sensor1");
        checkSensor.sensors.add("sensor2");
        checkSensor.sensors.add("sensor3");
        checkSensor.sensors.add("sensor4");
        String useragent = System.getenv("USER-AGENT");

        ArrayList<Server> Threadlist = new ArrayList<>();
        DatagramSocket socket = new DatagramSocket(1234);

        SharedBuffer ourBuffer = new SharedBuffer();
        checkSensor check = new checkSensor();
        new Thread(check).start();
        Server server = new Server(1234, socket, 4,ourBuffer);
        PullThread pullrequest = new PullThread(socket,ourBuffer);
        new Thread(pullrequest).start();
       // pullrequest.PullRequest();
       // server.PullRequest();
       // server.start();
        while (true) {


            checkSensor.isAlive.clear();
            //tem.out.println("Sensoren am leben2 : " + ALIVE_SENSOR);
           // new Thread(pullrequest).start();
            System.out.println("Buffer is empty vor if " +ourBuffer.isEmpty());

               // server.PullRequest();

           // check.join();
            System.out.println("Buffer is empty nach if " +ourBuffer.isEmpty());



                byte[] buf = new byte[256];

                DatagramPacket packet = new DatagramPacket(buf, buf.length);

                socket.receive(packet);
                UDPHandler thread = new UDPHandler(packet, ourBuffer);
                thread.start();

           // thread.join();
            // Server.join(500);
            // System.out.println("Größe vom shared Buffer: " + bufferforThreads.size());
              //  System.out.println("Sensoren Aktiv: "+ checkSensor.sensors.size());
              //  System.out.println("ShareBuffer größe: " + ourBuffer.getBufferSize());

            if(ourBuffer.getBufferSize() == checkSensor.sensors.size()) {
                TCPHandler Client = new TCPHandler(new Socket(address, 53257),ourBuffer);
                Client.start();
            }

        }

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

