package assignment4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

/** File manager class.
 */
public class FileManager {


  /** 
   * Load from the file.
   * 
   * 
   * @param filename for filename.
   * @param memberRegistry for registry.
   * @throws IOException in case.
   */
  public void loadFromFile(String filename, MemberRegistry memberRegistry) throws IOException {
    BufferedReader reader = new BufferedReader(
        new InputStreamReader(
            new FileInputStream(filename), StandardCharsets.UTF_8
      )
    );
    String line;
    Member currentMember = null;

    while ((line = reader.readLine()) != null) {
      if (line.startsWith("MEMBER:")) {
        String[] parts = line.split(":");
        String name = parts[1];
        String email = parts[2].isEmpty() ? null : parts[2];
        currentMember = new Member(name, email);
        memberRegistry.addMember(currentMember);
      } else if (line.startsWith("BOAT:") && currentMember != null) {
        String[] parts = line.split(":");
        String boatName = parts[1];
        String type = parts[2];
        double length = Double.parseDouble(parts[3]);
        double depth = type.equals("sailboat") || type.equals("motorsailer") ? Double.parseDouble(parts[4]) : 0;
        int enginePower = type.equals("motorboat") || type.equals("motorsailer") ? Integer.parseInt(parts[5]) : 0;
        Boat boat = new Boat(boatName, type, length, depth, enginePower);
        currentMember.getBoats().add(boat);
      }
    }

    reader.close();
  }

  /** 
   * Save to the file class.
   * 
   * @param filename for filename.
   * @param memberRegistry for registry.
   * @throws IOException in case.
   */
  public void saveToFile(String filename, MemberRegistry memberRegistry) throws IOException {
    BufferedWriter writer = new BufferedWriter(
            new OutputStreamWriter(
                new FileOutputStream(filename), StandardCharsets.UTF_8
            )
        );
    List<Member> members = memberRegistry.getMembers();

    for (Member member : members) {
      writer.write("MEMBER:" + member.getName() + ":" 
          + (member.getEmail() != null ? member.getEmail() : "") + ":" + member.getId() + "\n");
      for (Boat boat : member.getBoats()) {
        writer.write("BOAT:" + boat.getName() + ":" 
            + boat.getType() + ":" + boat.getLength() + ":" + boat.getDepth() + ":" + boat.getEnginePower() + "\n");
      }
    }

    writer.close();
  }
}
