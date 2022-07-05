//package com.pgx.java.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;


public class Server extends Thread {

    private final DatagramSocket udpSocket;
    private final int port;
    private final int threadname;
    public static Integer ALIVE_SENSOR = Integer.parseInt(System.getenv("NUMBEROFSENSORS"));
    public static String ADDRESS;
    public static Integer PORT = 1234;
    private SharedBuffer buffer = null;


    public void run() {
        while (true) {

            byte[] buf = new byte[256];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                this.udpSocket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // UDPHandler thread = new UDPHandler( his.buffer);
            // thread.start();
        }
    }


    public Server(int port, DatagramSocket socket, int name, SharedBuffer buffer) throws SocketException, IOException {
        this.port = port;
        this.udpSocket = socket;
        this.threadname = name;
        this.buffer = buffer;


    }



    public static void main(String[] args) throws Exception {
        String address = System.getenv("DESTINATIONTCP");

        ADDRESS = InetAddress.getByName("iot").toString();
        BlockingQueue<String> sharedQueue = new LinkedBlockingQueue<String>(8);


        // checkSensor.sensors.add("sensor1");
        // checkSensor.sensors.add("sensor2");
        // checkSensor.sensors.add("sensor3");
        // checkSensor.sensors.add("sensor4");


        //UDP SOCKET
        DatagramSocket socket = new DatagramSocket(1234);
        //SHAREDBUFFER FOR THREADS
        SharedBuffer ourBuffer = new SharedBuffer();
        //CHECK SENSORS ALIVE
        checkSensor check = new checkSensor(ourBuffer);
        //GET HTTP POST RESPONSE
        TCPresponse serverSocket = new TCPresponse();
        Server server = new Server(1234, socket, 4, ourBuffer);
        //GET SENSORS DATA
        PullThread pullrequest = new PullThread(socket, ourBuffer);
        TCPHandler Client = new TCPHandler(new Socket(address, 53257), sharedQueue);

        Client.start();
        serverSocket.start();
        check.initLists();
        new Thread(check).start();
        new Thread(pullrequest).start();


        while (true) {

            byte[] buf = new byte[256];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);

            socket.receive(packet);
            //   String message = new String(packet.getData()).trim();
            //  System.out.println("Got a Message from " + packet.getAddress());
            //  System.out.println(message);

            //ourBuffer.put(message);
            UDPHandler thread = new UDPHandler(packet, sharedQueue);
            thread.start();
            // System.out.println("ALIVE SENSORS: " + ALIVE_SENSOR);

            //  if(ourBuffer.getBufferSize() == ALIVE_SENSOR) {
            //SEND DATA TO HTTP SERVER - > TCP

            // }

        }

    }
}


