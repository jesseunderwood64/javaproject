package advancedjavaproject;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.*;

public class advancedjavaproject {

    private static final Logger logger = Logger.getLogger(advancedjavaproject.class.getName());
    private static final String LOG_FILE_NAME = "advancedjavaproject.log";

    public static void main(String[] args) {
        // Configure the logger
        LogManager.getLogManager().reset();
        logger.setLevel(Level.ALL);
        try {	
            FileHandler fileHandler = new FileHandler(LOG_FILE_NAME);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error configuring logger", e);
        }

        // Connect to the database
        Connection connection = null;
        try {
            String url = "jdbc:mysql://localhost:3306/advancedjavaproject";
            String username = "root";
            String password = "Sauvage";
            connection = DriverManager.getConnection(url, username, password);
            logger.log(Level.INFO, "Connected to the database");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error connecting to the database", e);
        }

        // Prompt the user for input
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        // Loop through the program until the user chooses to exit
        while (choice != 7) {
            // Display the menu options
            System.out.println("Choose a number between 1 and 7:");
            System.out.println("1. Add items");
            System.out.println("2. Subtract items");
            System.out.println("3. Update specifications");
            System.out.println("4. List products");
            System.out.println("5. Search products");
            System.out.println("6. Calculate total of all items");
            System.out.println("7. Exit");

            // Get the user's choice
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            // Execute the selected action
            switch (choice) {
                case 1:
                    Methods.addInventory(connection);
                    break;
                case 2:
                    Methods.subtractInventory(connection);
                    break;
                case 3:
                    Methods.updateSpecifications(connection);
                    break;
                case 4:
                    Methods.listProducts(connection);
                    break;
                case 5:
                    Methods.searchProduct(connection);
                    break;
                case 6:
                    Methods.calculateTotalvalue(connection);
                    break;
                case 7:
                    System.out.println("Exiting program...");
                    logger.log(Level.INFO, "Program exited");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.");
                    logger.log(Level.WARNING, "Invalid user choice: " + choice);
                    break;
            }
        }

        // Close the database connection
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error closing database connection", e);
        }
    }

}



