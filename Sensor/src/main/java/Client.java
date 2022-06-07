//package com.pgx.java.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Client {

    private DatagramSocket udpSocket;
    private InetAddress inetAddress;
    private byte [] buffer;
    private Scanner scanner;
    public static int messageCounterId = 0;

    private Client(DatagramSocket datagramsocket, InetAddress inetAddress) throws IOException {

        this.udpSocket = datagramsocket;
        this.inetAddress = inetAddress;
    }
    private void waitForPullRequest() throws IOException {

        String msg;
        byte[] buf = new byte[256];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);

        // blocks until a packet is received
        udpSocket.receive(packet);
        msg = new String(packet.getData()).trim();
        if(msg.equals("PULL")){
           this.start();


        }


    }
    private void start()  {
        String name = System.getenv("SENSOR_NAME");
        String type = System.getenv("SENSOR_TYPE");
        int sensID = Integer.parseInt(System.getenv("SENSOR_ID"));


        Scanner scanner = new Scanner(System.in);
        Sensor sensor1 = new Sensor(sensID,name, type);
        SensorData sensorData = new SensorData(sensor1);


        while (true) {
            try {

                sensorData.generateData();
                String message =sensorData.serializeToJSON().toString();

                buffer = message.getBytes();

                DatagramPacket p = new DatagramPacket(buffer, buffer.length, inetAddress, 1234);

                udpSocket.send(p);
                TimeUnit.SECONDS.sleep(7);
            }

            catch(IOException e){
                e.printStackTrace();
                break;
            }
            catch (InterruptedException k){
                k.printStackTrace();
                break;
            }
        }
    }
    public static void main(String[] args) throws NumberFormatException, IOException {

        String dest = System.getenv("DESTINATION");
        DatagramSocket Socket = new DatagramSocket(1235);
        InetAddress Address = InetAddress.getByName(dest) ; //InetAddress.getLocalHost()

        Client sender = new Client(Socket,Address);
        System.out.println("-- Running UDP Client at " + InetAddress.getLocalHost() + " --");
        while(true){
            byte[] buf = new byte[256];

            DatagramPacket packet = new DatagramPacket(buf,buf.length);

            Socket.receive(packet);
            UDPThread thread =new UDPThread(packet, Socket);
            new Thread(thread).start();

           // sender.waitForPullRequest();


        }



    }
}


