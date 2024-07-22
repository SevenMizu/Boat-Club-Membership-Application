package assignment4;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/* Member class with member properties.
 */
class Member {
  private String name;
  private String email;
  private String id;
  private List<Boat> boats;

  public Member(String name, String email) {
    this.name = name;
    this.email = email;
    this.id = generateUniqueId();
    this.boats = new ArrayList<>();
  }

  private String generateUniqueId() {
    return UUID.randomUUID().toString().substring(0, 6);
  }

  public Boat findBoatByName(String name) {
    for (Boat boat : boats) {
      if (boat.getName().equals(name)) {
        return boat;
      }
    }
    return null;
  }

  
  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getId() {
    return id;
  }

  public List<Boat> getBoats() {
    return boats;
  }
}
