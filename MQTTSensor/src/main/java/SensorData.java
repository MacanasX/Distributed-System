import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import org.json.simple.JSONObject;

public class SensorData {

    private double value;
    private String unit;
    private String time;
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
        jsonObject.put("messageId", Main.messageCounterId);
        Main.messageCounterId++;
        jsonObject.put("timestamp", this.time);
        jsonObject.put("unit",this.unit);
        jsonObject.put("value", this.value);
        jsonObject.put("sensor_type", sensor.getType());
        jsonObject.put("sensorId",sensor.getMyID());
        jsonObject.put("name", sensor.getSensorName());


        return jsonObject;

    }

    public void  generateData()
    {
        Double value =(new Random().nextDouble() * (10 - 1)+ 1);
        value = ((double)((int)(value *100.0)))/100.0;
        this.value = value;
        SimpleDateFormat date = new SimpleDateFormat("yyyy.MM.dd.HH:mm:ss");
        this.time = date.format(new Date());

        generateUnit();



    }

}

