import java.util.ArrayList;
import java.util.Date;

public class HTTPPost {

  public String checkHtppMessage(String message) {

    String[] tmp = message.split("\\r?\\n");
    String httpHeaderResponse;

    if(tmp[0].contains("POST") && tmp[0].contains("HTTP") && tmp[0].contains("1.1")) {
      java.util.Date date = new java.util.Date();

      httpHeaderResponse = "HTTP/1.1 200 OK /" + "\n" + "Date: " + date + "\n" +
          "Server: " + "HTTPServer" + "\n" + "    " + "\n" + "Accept:  application/json" + "\n" +
          "Accept-Language: en-US,en;q=0.5" + "\n" + "Accept-Encoding:  " + "\n" +
          "Connection: keep-alive" + "\n" + "Content-Type: application/json; charset=utf-8" + "\n"
          +
          tmp[9] + "\n" + "\n";


    } else {
      java.util.Date date = new java.util.Date();
      httpHeaderResponse = "HTTP/1.1 404 Not Found /" + "\n" + "Date: " + date + "\n" +
          "Server: " + "HTTPServer" + "\n" + "    " + "\n" + "Accept:  application/json" + "\n" +
          "Accept-Language: en-US,en;q=0.5" + "\n" + "Accept-Encoding:  " + "\n" +
          "Connection: keep-alive" + "\n" + "Content-Type: application/json; charset=utf-8" + "\n"
          +
          tmp[9] + "\n" + "\n";


    }
    // }
    return httpHeaderResponse;

  }
}


