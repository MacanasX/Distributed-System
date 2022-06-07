import org.json.simple.JSONObject;

public class Sensor {

  private String name;
  private Integer messageId;
  private String unit;
  private Double value;
  private String sensorType;
  private Integer sensorId;
  private String timestamp;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getMessageId() {
    return messageId;
  }

  public void setMessageId(Integer messageId) {
    this.messageId = messageId;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public Double getValue() {
    return value;
  }

  public void setValue(Double value) {
    this.value = value;
  }

  public String getSensorType() {
    return sensorType;
  }

  public void setSensorType(String sensorType) {
    this.sensorType = sensorType;
  }

  public Integer getSensorId() {
    return sensorId;
  }

  public void setSensorId(Integer sensorId) {
    this.sensorId = sensorId;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public Sensor(String name, Integer messageId, String unit, Double value,
      String sensorType, Integer sensorId, String timestamp) {
    this.name = name;
    this.messageId = messageId;
    this.unit = unit;
    this.value = value;
    this.sensorType = sensorType;
    this.sensorId = sensorId;
    this.timestamp = timestamp;
  }

}
