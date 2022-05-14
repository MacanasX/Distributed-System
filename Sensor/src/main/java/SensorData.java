import org.json.simple.JSONObject;

import java.sql.Timestamp;
import java.util.Random;

public class SensorData {

    private double value;
    private String unit;
    private Timestamp time;
    private Sensor sensor;
    SensorData(Sensor sensor)
    {
        this.sensor= sensor;
    }

    public JSONObject serializeToJSON ()
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("timestamp", this.time);
        jsonObject.put("name", sensor.getSensorName());
        jsonObject.put("sensor_type", sensor.getType());
        jsonObject.put("value", this.value);
        jsonObject.put("id",sensor.getMyID());
        jsonObject.put("unit",this.unit);


        return jsonObject;

    }

    public void  generateData()
    {
        Double value =(new Random().nextDouble() * (10 - 1)+ 1);
        this.value = value;
        this.time = new Timestamp(System.currentTimeMillis());
        this.unit = "Fahrenheit";




    }

}
