
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.sql.Timestamp;
import org.json.simple.JSONObject;


public class Sensor {

    private int myID;
    private String sensorName;
    private String type;
    private SensorData mydata;
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    Sensor(int myID,String sensorName,String type)
    {
        this.myID = myID;
        this.sensorName = sensorName;
        this.type = type;
    }

    public int getMyID() {
        return myID;
    }

    public void setMyID(int myID) {
        this.myID = myID;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }







}


