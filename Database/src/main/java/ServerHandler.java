import java.util.ArrayList;
import javax.xml.crypto.Data;
import org.apache.thrift.TException;
import thriftserver.*;




public class ServerHandler implements  CRUD.Iface {

private ArrayList<Sensor> table = new ArrayList<>();
public static ArrayList<Sensor> cache = new ArrayList<>();


 @Override
 public boolean insert(Sensor insert) throws TException {

  if(table.isEmpty())
  {
   System.out.println("Generated new Table for Sensor");
   cache.add(insert);
   System.out.println("Inserted " + insert.sensorName + " with Id " + insert.id + " and timestamp " + insert.timestamp + " into Database!" );
  }
  else{
   cache.add(insert);
   System.out.println("Inserted " + insert.sensorName + " with Id " + insert.id + " and timestamp " + insert.timestamp + " into Database!" );
  }
  return true;
 }

 @Override
 public Sensor select(Sensor select) throws TException {
  boolean inDatabase = false;

  for(int i = 0 ; i < table.size() ; i++)
  {
   if(select.equals(table.get(i)))
   {

    inDatabase = true;
    return table.get(i);
   }
  }

   if(!inDatabase)
    System.out.println("No matching Sensor found");


  return null;
 }

 @Override
 public boolean update(Sensor update) throws TException {

  for(int i = table.size()-1 ; i >= 0 ; i--)
  {
    if(update.getId() == table.get(i).getId())
    {
       table.add(i,update);
       System.out.println("Last Sensor got updated!");
       return true;
    }

  }

  System.out.println("No update possible!");
  return false;
 }

 @Override
 public boolean remove(Sensor remove) throws TException {
  Boolean gotRemoved = false;

  for(int i = 0 ; i < table.size() ; i++)
  {
   if(remove.equals(table.get(i))) {
    table.remove(i);
    gotRemoved = true;
    System.out.println("Sensor got removed from Database!");
    return gotRemoved;
   }
  }
   if(!gotRemoved) {
    System.out.println("No matching Sensor found");

   }
  return gotRemoved;

}
@Override
 public boolean commit(Sensor toComit){

  for(int i = 0 ; i < cache.size() ; i++){

   if(cache.get(i).equals(toComit)){
      table.add(toComit);
      cache.remove(i);
   }
   return true;

  }

return false;
}
@Override
 public boolean abort (Sensor toAbort){

  for(int i = 0 ; i < cache.size()  ; i++){

   if(cache.get(i).equals(toAbort))
      cache.remove(i);
     return true;
  }


  return false;
}
}