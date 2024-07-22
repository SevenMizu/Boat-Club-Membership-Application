/*
 * This Java source file was generated by the Gradle 'init' task.
 */

package assignment4;

/**
 * This is the generated Hello World Greeting App.
 */
public class App {

  /** 
   * Returns a nice greeting message.
   *
   * @return the greeing message.
   */
  public String getGreeting() {
    return "Boat Club Program!!";
  }

  /**
   * The App starting point.
   *
   * @param args Unused program arguments.
   */
  public static void main(String[] args) {
    App theApp = new App();
    System.out.println(theApp.getGreeting());
    BoatClubApp boatClubApp = new BoatClubApp();
    boatClubApp.run(); 
  }
}
