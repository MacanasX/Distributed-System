import javax.xml.crypto.Data;
import org.apache.thrift.TException;
import server.*;


public class ServerHandler implements  CRUD.Iface {


 /* @Override
  public int addTwo(int num1, int num2) throws TException {
    System.out.println("Received: " + num1 + ", " + num2);
    return num1 + num2;
  }

  @Override
  public Result addOne(Operands operands) throws TException {
    return null;
  }
client.insert((String)json.get("unit").toString(),(String)json.get("name").toString(),(String)json.get("messageId").toString()
                    ,(String)json.get("value").toString(),(String)json.get("sensor_type").toString(),
                    (String)json.get("timestamp").toString(),(String)json.get("sensorId").toString());

public Sensor(String name, Integer messageId, String unit, Double value,
      String sensorType, Integer sensorId, String timestamp)
  */
  @Override
  public void insert(String a, String b, String c, String d, String e, String f, String g)
      throws TException {
    System.out.println("Aufgerufen");
   DatabaseServer.table.add(new Sensor(b,Integer.parseInt(c),a,Double.parseDouble(d),e,Integer.parseInt(g),f));
   System.out.println("Inserted Sensor " + g + " Timestamp:  " +f );
  }

  @Override
  public String select(String a, String b, String c, String d, String e, String f, String g)
      throws TException {
   System.out.println("Aufgerufen");
    return null;
  }

  @Override
  public void update(String a, String b, String c, String d, String e, String f, String g)
      throws TException {
   System.out.println("Aufgerufen");

  }

  @Override
  public void remove(String a, String b, String c, String d, String e, String f, String g)
      throws TException {
   System.out.println("Aufgerufen");
  }

}