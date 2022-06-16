import databaseclient.Sensor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class HTTPPost {



  public String checkHtppMessage(String message) throws ParseException {

    String[] tmp = message.split("\\r?\\n");
    String httpHeaderResponse ="";
    //check for Json String
    boolean isJSON = checkForContentType(tmp[tmp.length-1]);

    if(tmp[0].contains("POST") && tmp[0].contains("HTTP") && tmp[0].contains("1.1") && isJSON ) {
      java.util.Date date = new java.util.Date();

      httpHeaderResponse =
          "HTTP/1.1 200 OK /" + System.getProperty("line.separator")
              + "Date: " + date + System.getProperty("line.separator")
              + "Server: " + "HTTPserver" + System.getProperty("line.separator")
              + "Accept:  application/json" + System.getProperty("line.separator")
              + "Accept-Language: en-US,en;q=0.5" + System.getProperty("line.separator")
              + "Connection: keep-alive" + System.getProperty("line.separator")
              + "Content-Type: application/json; charset=utf-8" + System.getProperty("line.separator")
              + tmp[6];



      JSONParser parser = new JSONParser();

      JSONObject json = (JSONObject) parser.parse(tmp[tmp.length-1]);

            //add to Q for Thread
            HTTPServer.myReceivedMessages.add(new Sensor(json.get("name").toString(),json.get("sensor_type").toString(),
                json.get("unit").toString(),json.get("timestamp").toString(),Double.parseDouble(json.get("value").toString()),
                Integer.parseInt(json.get("sensorId").toString()),Integer.parseInt(json.get("messageId").toString())));
            //Save for HTTP-Server
      HTTPServer.myReceivedMessages_List.add(new Sensor(json.get("name").toString(),json.get("sensor_type").toString(),
          json.get("unit").toString(),json.get("timestamp").toString(),Double.parseDouble(json.get("value").toString()),
          Integer.parseInt(json.get("sensorId").toString()),Integer.parseInt(json.get("messageId").toString())));

    } else if (!isJSON){
      java.util.Date date = new java.util.Date();
      httpHeaderResponse = "HTTP/1.1 404 Not Found /" + System.getProperty("line.separator")
          + "Date: " + date + System.getProperty("line.separator")
          + "Server: " + "HTTPserver" + System.getProperty("line.separator")
          + "Accept:  application/json" + System.getProperty("line.separator")
          + "Accept-Language: en-US,en;q=0.5" + System.getProperty("line.separator")
          + "Connection: keep-alive" + System.getProperty("line.separator")
          + "Content-Type: application/json; charset=utf-8" + System.getProperty("line.separator")
          + tmp[6];


    }


    return httpHeaderResponse;
  }



  private boolean checkForContentType(String json )  {

    JSONParser parser = new JSONParser();

    try {
      JSONObject jsonObject = (JSONObject) parser.parse(json);
    } catch (ParseException e) {
      return false;
    }

    return true;
  }


}