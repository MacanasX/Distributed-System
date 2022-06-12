namespace java databaseclient

struct Sensor {

  1: string sensorName,
  2: string sensorType,
  3: string unit,
  4: string timestamp,
  5: double value,
  6: i32 id,
  7: i32 messageId

}


service CRUD {

  bool insert (1: Sensor insert),
  Sensor select (1:Sensor select),
  bool update (1: Sensor update),
  bool remove (1: Sensor remove)

}