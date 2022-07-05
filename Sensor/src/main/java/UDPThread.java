import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class UDPThread implements Runnable {

    DatagramPacket udpPacket = null;
    DatagramSocket Socket = null;
    public static final int httpPort = 1234;


    UDPThread(DatagramPacket packet, DatagramSocket Socket) {
        this.udpPacket = packet;
        this.Socket = Socket;


    }

    public void run() {
        // überprüfe, ob es sich bei der Anfrage vom iot um ein "PULL"-Request handelt
        String message = new String(this.udpPacket.getData()).trim();
        if (message.equals("PULL")) {
            //besorge ID, Type und Name aus den umgebungsvariabeln
            int sensID = Integer.parseInt(System.getenv("SENSOR_ID"));
            String type = System.getenv("SENSOR_TYPE");
            String name = System.getenv("SENSOR_NAME");

            //erstelle Sensor
            Sensor sensor1 = new Sensor(sensID, name, type);
            SensorData sensorData = new SensorData(sensor1);


            try {
                //generiere zufällige Sensordaten
                sensorData.generateData();
                String message2 = sensorData.serializeToJSON().toString();

                byte[] buffer = message2.getBytes();

                DatagramPacket p = new DatagramPacket(buffer, buffer.length, this.udpPacket.getAddress(), httpPort); //Port vom HTTPserver?
                //sende die Sensordaten an das iot-Gateway
                this.Socket.send(p);

            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }
}




