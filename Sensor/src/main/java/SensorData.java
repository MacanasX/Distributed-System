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


    private void generateUnit(){
        switch (this.sensor.getType())
        {
            case "Temperatur":
                this.unit = "CELSIUS";
                break;
            case "Helligkeit":
                this.unit ="LUMEN";
                break;
            case "Niederschlag":
                this.unit ="LITER";
                break;
            case "Wind":
                this.unit ="SEEMEILE";
        }

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
        generateUnit();



    }

}

