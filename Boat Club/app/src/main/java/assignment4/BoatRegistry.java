package assignment4;

class BoatRegistry {
  public Boat findBoatByName(Member member, String name) {
    for (Boat boat : member.getBoats()) {
      if (boat.getName().equals(name)) {
        return boat;
      }
    }
    return null;
  }
}
