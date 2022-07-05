public class TestHTTPcheck {


    public String generateFalseHTTPHeader(String message){

        String header="GET / HTTP/1.1" + "\n"
                + "Host:  " + Server.ADDRESS + ":"+Server.PORT +"\n"
                + "Accept:  application/json" +"\n"
                + "Accept-Language: en-US,en;q=0.5" + "\n"
                + "Connection: keep-alive" + "\n"
                +"Content-Type: application/json; charset=utf-8" +"\n"
                + "Content-Length:  "+ message.length() + "\n"
                + "\n"
                + message;

        return header;

    }


}
