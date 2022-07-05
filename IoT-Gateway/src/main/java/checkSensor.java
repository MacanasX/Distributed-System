import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class checkSensor extends Thread {

    public static ArrayList<Boolean> isAlive = new ArrayList<>();
    public static ArrayList<String> sensors = new ArrayList<>();
    private SharedBuffer mybuffer = null;

    checkSensor(SharedBuffer mybuffer) {

        this.mybuffer = mybuffer;
    }


    public void initLists() {
        for (int i = 1; i <= Server.ALIVE_SENSOR; i++) {
            checkSensor.sensors.add("sensor" + i);
            checkSensor.isAlive.add(true);

        }

    }


    public void run() {


        while (true) {

            for (int i = 1; i <= sensors.size(); i++) {
                try {

                    InetAddress Address = InetAddress.getByName(checkSensor.sensors.get(i - 1));
                    isAlive.set(i - 1, check(Address, 1235));

                } catch (UnknownHostException | SocketException e) {
                    // System.out.println(sensors.get(i) + " ist nicht mehr erreichbar!");
                    isAlive.set((i - 1), false);
                    //  mybuffer.clearBuffer();

                    // isAlive.add(i,false);
                }
                int SENSORCOUNTER = 0;
                for (int j = 0; j < checkSensor.sensors.size(); j++) {
                    if (checkSensor.isAlive.get(j))
                        SENSORCOUNTER++;

                    // System.out.println("SENSOR " + i+1 + " ist alive " + checkSensor.isAlive.get(j));
                }
                Server.ALIVE_SENSOR = SENSORCOUNTER;

            }
        }

    }


    private boolean check(InetAddress Address, int port) throws SocketException {

        boolean isAlive = false;

        // Creates a socket address from a hostname and a port number
        SocketAddress socketAddress = new InetSocketAddress(Address, port);
        DatagramSocket socket = new DatagramSocket(1238);
        socket.connect(Address, 1235);
        socket.close();


        isAlive = true;
        return isAlive;
    }


}
