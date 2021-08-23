package edu.cnm.deepdive.teamassignments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Enables Spring Boots's auto configuration mechanism, enables autoscan on the package and allows the register of extra beans in classes.
 */
@SpringBootApplication
public class TeamAssignmentsServiceApplication {

  /**
   * The main method that runs the application database.
   * @param args supplies command line arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(TeamAssignmentsServiceApplication.class, args);
  }

}
