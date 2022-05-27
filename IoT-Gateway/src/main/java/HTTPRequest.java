public class HTTPRequest {


  public String generateHTTPHeader(String message){

    String header="POST / HTTP/1.1" + "\n"
        + "Host:  " + Server.ADDRESS + ":"+Server.PORT +"\n"
        + "User-Agent:  "+ "    " + "\n"
        + "Accept:  application/json" +"\n"
        + "Accept-Language: en-US,en;q=0.5" + "\n"
        + "Accept-Encoding:  " + "\n"
        + "Connection: keep-alive" + "\n"
        +"Content-Type: application/json; charset=utf-8" +"\n"
        + "Content-Length:  "+ message.length() + "\n"
        + "\n"
        + message;

    return header;

  }

}
