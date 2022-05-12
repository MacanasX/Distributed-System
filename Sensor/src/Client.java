//package com.pgx.java.socket;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {

    private DatagramSocket udpSocket;
    private InetAddress inetAddress;
    private byte [] buffer;
    private Scanner scanner;

    private Client(DatagramSocket datagramsocket, InetAddress inetAddress) throws IOException {

        this.udpSocket = datagramsocket;
        this.inetAddress = inetAddress;
    }

    private void start() throws IOException {

        Scanner scanner = new Scanner(System.in);
        Sensor sensor1 = new Sensor(1,"Temperatursensor", "Temperatur");
        SensorData sensorData = new SensorData(sensor1);


        while (true) {
            try {

                sensorData.generateData();
                String message =sensorData.serializeToJSON().toString();

                buffer = message.getBytes();

                DatagramPacket p = new DatagramPacket(buffer, buffer.length, inetAddress, 1234);

               udpSocket.send(p);

            }

        catch(IOException e){
            e.printStackTrace();
            break;
        }
        }
    }
    public static void main(String[] args) throws NumberFormatException, IOException {


        DatagramSocket Socket = new DatagramSocket();
        InetAddress Address = InetAddress.getByName("localhost");

        Client sender = new Client(Socket,Address);
        System.out.println("-- Running UDP Client at " + InetAddress.getLocalHost() + " --");

        sender.start();
    }
}


