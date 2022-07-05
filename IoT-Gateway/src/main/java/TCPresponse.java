import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class TCPresponse extends Thread {

    private ServerSocket serverSocket = null;
    public static final int TcpIotPort = 53258;

    TCPresponse() throws IOException {
        this.serverSocket = new ServerSocket(TcpIotPort); //53258

    }

    public void run() {

        while (true) {
            Socket client = null;
            try {
                client = serverSocket.accept();

            } catch (IOException e) {
                e.printStackTrace();
            }

            String response;
            InputStream inputStream = null;
            try {
                inputStream = client.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            DataInputStream dataInputStream = new DataInputStream(inputStream);

            String message = null;
            try {
                message = dataInputStream.readUTF();
                //  System.out.println("HIER IST DIE RESPONSE MASSAGE");
                System.out.println(message);
                //  RttLogger.memory_End.add(ZonedDateTime.now().toInstant().toEpochMilli());


                String[] tmpSplit = message.split(",");
                // hier wird ein eindeutiger key aus SensorID und MessageID gebaut, um die RTT zu berechnen
                String msgID = tmpSplit[3];
                String sensID = tmpSplit[7];
                String hash = msgID + sensID;
                int key = hash.hashCode();

                RttLogger.end.put(key, ZonedDateTime.now().toInstant().toEpochMilli());
                RttLogger.logger.log(Level.INFO, "RTT for " + sensID.substring(0, sensID.length() - 1) + " and " + msgID + " : " + RttLogger.getRtt2(key) + " ms\n");


            } catch (IOException e) {
                //e.printStackTrace();
            }
            try {
                client.close();
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
    }
}

