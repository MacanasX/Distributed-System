//package com.pgx.java.socket;
import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Server extends Thread {
    private DatagramSocket udpSocket;
    private int port;
    private int threadname;
    public void run()
    {
        try {
            while(true) {
                this.PullRequest();
                this.listen();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public Server(int port, DatagramSocket socket, int name) throws SocketException, IOException {
        this.port = port;
        this.udpSocket = socket;
        this.threadname = name;
    }
    private void  listen() throws Exception {
        System.out.println("-- Running Server at " + InetAddress.getLocalHost() + "--");
        String msg;

       /* while(true) {
            try {


            }
            catch(IOException e){
                e.printStackTrace();
                break;
            }
        }*/
        Threadlistening();
    }
    private synchronized void PullRequest() throws IOException, InterruptedException {
        String [] sensoren = {"sensor1","sensor2","sensor3","sensor4"};
        String pullMessage = "PULL";
        byte [] messageBuffer = pullMessage.getBytes();
        InetAddress Address = InetAddress.getByName(sensoren[0]);
        DatagramPacket p = new DatagramPacket(messageBuffer, messageBuffer.length, Address, 1235);

        udpSocket.send(p);





    }
    private synchronized void Threadlistening() throws IOException, InterruptedException {
        String msg;
        byte[] buf = new byte[256];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);

        // blocks until a packet is received
        udpSocket.receive(packet);

        msg = new String(packet.getData()).trim();

        System.out.println(
              "Meine ID:" + this.threadname +  "Message from " + packet.getAddress().getHostAddress() + ": " + msg);


        Thread.sleep(2000);
    }

    public static void main(String[] args) throws Exception {
        int portNumbers = Integer.parseInt(System.getenv("NUMBERPORTS"));
        ArrayList<Server> Threadlist = new ArrayList<>();
        DatagramSocket socket= new DatagramSocket(1234);
        for(int i = 0; i < portNumbers ; i++)
        {
            Server clientthreads = new Server(1234,socket,i);
            Threadlist.add(clientthreads);
        }
        System.out.println("Größe der threadlist:" + Threadlist.size());
        for(int i = 0; i < Threadlist.size() ; i++)
        {
            Threadlist.get(i).start();
        }
    }
}