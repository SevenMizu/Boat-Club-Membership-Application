package assignment4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

/**
 * Boat club app class. 
 */
public class BoatClubApp {
  private MemberRegistry memberRegistry;
  private FileManager fileManager;

  public BoatClubApp() {
    this.memberRegistry = new MemberRegistry();
    this.fileManager = new FileManager();
  }

  /**
   * Runs the main loop of the Boat Club application.
   */
  public void run() {
    try {
      fileManager.loadFromFile("registry.data", memberRegistry);
    } catch (IOException e) {
      System.out.println("Error loading registry data: " + e.getMessage());
    }

    Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    boolean running = true;

    while (running) {
      System.out.println("1. Add Member");
      System.out.println("2. List Members");
      System.out.println("3. Select Member");
      System.out.println("4. Quit");

      int choice = scanner.nextInt();
      scanner.nextLine(); 

      switch (choice) {
        case 1:
          addMember(scanner);
          break;
        case 2:
          listMembers();
          break;
        case 3:
          selectMember(scanner);
          break;
        case 4:
          running = false;
          break;
        default:
          System.out.println("Invalid choice. Please try again.");
      }
    }

    try {
      fileManager.saveToFile("registry.data", memberRegistry);
    } catch (IOException e) {
      System.out.println("Error saving registry data: " + e.getMessage());
    }

    scanner.close();
  }

  /**
   * Adds a new member to the registry.
   *
   * @param scanner the scanner to read user input.
   */
  private void addMember(Scanner scanner) {
    System.out.print("Enter name: ");
    String name = scanner.nextLine();
    System.out.print("Enter email (optional): ");
    String email = scanner.nextLine();
    Member member = new Member(name, email.isEmpty() ? null : email);
    memberRegistry.addMember(member);
    System.out.println("Member added with ID: " + member.getId());
  }

  /**
   * Lists all members in the registry.
   */
  private void listMembers() {
    List<Member> members = memberRegistry.getMembers();
    if (members.isEmpty()) {
      System.out.println("No members found.");
    } else {
      for (Member member : members) {
        System.out.println("ID: " + member.getId() + ", Name: " + member.getName() 
            + ", Email: " + (member.getEmail() != null ? member.getEmail() : "N/A"));
      }
    }
  }

  /**
   * Selects a member by ID and presents member-related options.
   *
   * @param scanner the scanner to read user input.
   */
  private void selectMember(Scanner scanner) {
    System.out.print("Enter member ID: ");
    String memberId = scanner.nextLine();
    Member member = memberRegistry.findMemberById(memberId);

    if (member == null) {
      System.out.println("Member not found.");
      return;
    }

    boolean memberMenu = true;
    while (memberMenu) {
      System.out.println("1. View Member Details");
      System.out.println("2. Delete Member");
      System.out.println("3. Add Boat");
      System.out.println("4. List Boats");
      System.out.println("5. Select Boat");
      System.out.println("6. Back");

      int choice = scanner.nextInt();
      scanner.nextLine(); 

      switch (choice) {
        case 1:
          viewMemberDetails(member);
          break;
        case 2:
          memberRegistry.deleteMember(member);
          memberMenu = false;
          break;
        case 3:
          addBoat(scanner, member);
          break;
        case 4:
          listBoats(member);
          break;
        case 5:
          selectBoat(scanner, member);
          break;
        case 6:
          memberMenu = false;
          break;
        default:
          System.out.println("Invalid choice. Please try again.");
      }
    }
  }

  /**
   * Views the details of a member.
   *
   * @param member the member whose details are to be viewed.
   */
  private void viewMemberDetails(Member member) {
    System.out.println("ID: " + member.getId());
    System.out.println("Name: " + member.getName());
    System.out.println("Email: " + (member.getEmail() != null ? member.getEmail() : "N/A"));
  }
  
  /** Adds a boat to member's boat list.
   * 
   * @param scanner for the scanner that reads the user input.
   * @param member for member who will get the boat that's added.
   */
  private void addBoat(Scanner scanner, Member member) {
    System.out.print("Enter boat name: ");
    final String boatName = scanner.nextLine();
    System.out.print("Enter boat type (sailboat, motorboat, motorsailer, canoe): ");
    final String boatType = scanner.nextLine();
    System.out.print("Enter boat length: ");
    final double boatLength = scanner.nextDouble();
    scanner.nextLine(); 
  
    double boatDepth = 0;
    int boatEnginePower = 0;
  
    if (boatType.equals("sailboat") || boatType.equals("motorsailer")) {
      System.out.print("Enter boat depth: ");
      boatDepth = scanner.nextDouble();
      scanner.nextLine(); 
    }
  
    if (boatType.equals("motorboat") || boatType.equals("motorsailer")) {
      System.out.print("Enter boat engine power: ");
      boatEnginePower = scanner.nextInt();
      scanner.nextLine(); 
    }
  
    Boat boat = new Boat(boatName, boatType, boatLength, boatDepth, boatEnginePower);
    member.getBoats().add(boat);
    System.out.println("Boat added.");
  }
  
  /**
   * Lists all boats for a given member.
   *
   * @param member the member whose boats are to be listed.
   */
  private void listBoats(Member member) {
    List<Boat> boats = member.getBoats();
    if (boats.isEmpty()) {
      System.out.println("No boats found for this member.");
    } else {
      for (Boat boat : boats) {
        System.out.println(
            "Name: " + boat.getName() 
            + ", Type: " + boat.getType() 
            + ", Length: " + boat.getLength() 
            + (boat.getType().equals("sailboat") || boat.getType().equals("motorsailer") 
            ? ", Depth: " + boat.getDepth() 
            : "") 
            + (boat.getType().equals("motorboat") || boat.getType().equals("motorsailer") 
            ? ", Engine Power: " + boat.getEnginePower() 
            : "")
        );
      }
    }
  }

  /**
   * Selects a boat by name and presents boat-related options.
   *
   * @param scanner the scanner to read user input.
   * @param member  the member whose boat is to be selected.
   */
  private void selectBoat(Scanner scanner, Member member) {
    System.out.print("Enter boat name: ");
    String boatName = scanner.nextLine();
    Boat boat = member.getBoats().stream().filter(b -> b.getName().equals(boatName)).findFirst().orElse(null);

    if (boat == null) {
      System.out.println("Boat not found.");
      return;
    }

    boolean boatMenu = true;
    while (boatMenu) {
      System.out.println("1. View Boat Details");
      System.out.println("2. Delete Boat");
      System.out.println("3. Back");

      int choice = scanner.nextInt();
      scanner.nextLine(); 

      switch (choice) {
        case 1:
          viewBoatDetails(boat);
          break;
        case 2:
          member.getBoats().remove(boat);
          boatMenu = false;
          System.out.println("Boat deleted.");
          break;
        case 3:
          boatMenu = false;
          break;
        default:
          System.out.println("Invalid choice. Please try again.");
      }
    }
  }

  /**
   * Views the details of a boat.
   *
   * @param boat the boat whose details are to be viewed.
   */
  private void viewBoatDetails(Boat boat) {
    System.out.println("Name: " + boat.getName());
    System.out.println("Type: " + boat.getType());
    System.out.println("Length: " + boat.getLength());
    if (boat.getType().equals("sailboat") || boat.getType().equals("motorsailer")) {
      System.out.println("Depth: " + boat.getDepth());
    }
    if (boat.getType().equals("motorboat") || boat.getType().equals("motorsailer")) {
      System.out.println("Engine Power: " + boat.getEnginePower());
    }
  }
}
