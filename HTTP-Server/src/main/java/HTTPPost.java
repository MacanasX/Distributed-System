import java.util.ArrayList;
import java.util.Date;

public class HTTPPost {

  public String checkHtppMessage(String message) {

    String[] tmp = message.split("\\r?\\n");
    String httpHeaderResponse;

    if(tmp[0].contains("POST") && tmp[0].contains("HTTP") && tmp[0].contains("1.1")) {
      java.util.Date date = new java.util.Date();

      httpHeaderResponse =
          "HTTP/1.1 200 OK /" + System.getProperty("line.separator")
          + "Date: " + date + System.getProperty("line.separator")
              + "Server: " + System.getProperty("line.separator")
              + "HTTPServer" +  "    " + System.getProperty("line.separator")
              +  "Accept:  application/json" + System.getProperty("line.separator")
              + "Accept-Language: en-US,en;q=0.5" + System.getProperty("line.separator")
              + "Accept-Encoding:  " + System.getProperty("line.separator")
              + "Connection: keep-alive" + System.getProperty("line.separator")
              + "Content-Type: application/json; charset=utf-8" + System.getProperty("line.separator")
          + tmp[8];


    } else {
      java.util.Date date = new java.util.Date();
      httpHeaderResponse = "HTTP/1.1 404 Not Found /" + System.getProperty("line.separator")
          + "Date: " + date + System.getProperty("line.separator")
          + "Server: " + System.getProperty("line.separator")
          + "HTTPServer" + System.getProperty("line.separator") + "    " + System.getProperty("line.separator")
          + "Accept:  application/json" + System.getProperty("line.separator")
          + "Accept-Language: en-US,en;q=0.5" + System.getProperty("line.separator")
          + "Accept-Encoding:  " + System.getProperty("line.separator")
          + "Connection: keep-alive" + System.getProperty("line.separator")
          + "Content-Type: application/json; charset=utf-8" + System.getProperty("line.separator")
          + tmp[8];


    }

    return httpHeaderResponse;

  }
}


