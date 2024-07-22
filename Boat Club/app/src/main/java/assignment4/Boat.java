package assignment4;

/** Boat class with methods.
 */
public class Boat {
  private String name;
  private String type;
  private double length;
  private double depth;
  private int enginePower;

  public Boat(String name, String type, double length, double depth, int enginePower) {
    this.name = name;
    this.type = type;
    this.length = length;
    this.depth = depth;
    this.enginePower = enginePower;
  }

  
  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public double getLength() {
    return length;
  }

  public double getDepth() {
    return depth;
  }

  public int getEnginePower() {
    return enginePower;
  }
}
