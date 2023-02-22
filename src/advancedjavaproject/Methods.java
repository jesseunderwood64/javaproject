package advancedjavaproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Statement;
public class Methods {
		
    // Subtract inventory from different items
    public static void subtractInventory(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        try {
            // Get the product ID and inventory to add
            System.out.print("Enter the product ID: ");
            int productId = scanner.nextInt();
            System.out.print("Enter the inventory to subtract: ");
            int inventoryToSubtract = scanner.nextInt();
            
            // Check if there's enough inventory to subtract
            String checkSql = "SELECT numberofproducts FROM products WHERE idproducts = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkSql);
            checkStatement.setInt(1, productId);
            ResultSet checkResult = checkStatement.executeQuery();
            if (checkResult.next()) {
                int currentInventory = checkResult.getInt("numberofproducts");
                if (inventoryToSubtract > currentInventory) {
                    System.out.println("Error: Not enough inventory to subtract.");
                    return;
                }
            } else {
                System.out.println("Error: Product not found.");
                return;
            }

            String subtractsql = "UPDATE products SET numberofproducts = numberofproducts - ? WHERE idproducts = ?";
            PreparedStatement statement;
            statement = connection.prepareStatement(subtractsql);
            statement.setInt(1, inventoryToSubtract);
            statement.setInt(2, productId);
            int rowsAffected = statement.executeUpdate();
            // Print the number of rows affected
            System.out.printf("%d row(s) updated.%n", rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Items have been subtracted to the database.");
        }
    }
    
    // Add inventory to different items
    public static void addInventory(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        // Get the product ID and inventory to add
        System.out.print("Enter the product ID: ");
        int productId = scanner.nextInt();
        System.out.print("Enter the inventory to add: ");
        int inventoryToAdd = scanner.nextInt();

        String addsql = "UPDATE products SET numberofproducts = numberofproducts + ? WHERE idproducts = ?";
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(addsql);
            statement.setInt(1, inventoryToAdd);
            statement.setInt(2, productId);
            int rowsAffected = statement.executeUpdate();
            // Print the number of rows affected
            System.out.printf("%d row(s) updated.%n", rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Items have been added to the database.");
        }
    }
	
    // Update specifications
    public static void updateSpecifications(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the ID of the row to update: ");
        int id = scanner.nextInt();
        System.out.print("Enter the specifications: ");
        String specifications = scanner.next();

        String specificationssql = "UPDATE products SET specifications = ? WHERE idproducts = ?";
        try (PreparedStatement statement = connection.prepareStatement(specificationssql)) {
            statement.setString(1, specifications);
            statement.setInt(2, id);
            int rowsAffected = statement.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            System.out.println("Error updating string: " + e.getMessage());
        } finally {
            System.out.println("Specifications updated.");
        }
    }
    
    
    
    
    /**
     * List all products in the database.
     * 
     * @param connection the database connection
     */
    public static void listProducts(Connection connection) {
    	try {
            // Query the database to get all the products
            String sql = "SELECT * FROM products";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            // Print the header
            System.out.printf("%-10s %-30s %-20s %-10s\n", "ID", "Products", "Specifications", "Quantity");
            System.out.println("---------------------------------------------------------");

            // Print each product row
            while (resultSet.next()) {
                int id = resultSet.getInt("idproducts");
                String name = resultSet.getString("products");
                String specifications = resultSet.getString("specifications");
                int quantity = resultSet.getInt("numberofproducts");

                System.out.printf("%-10d %-30s %-50s %-10d\n", id, name, specifications, quantity);
            }

            // Close the resources
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("listProducts method called.");

       
    }

    /**
     * Search for a product in the database by name.
     * 
     * @param connection the database connection
     */
    public static void searchProduct(Connection connection) {
    	    	    Scanner scanner = new Scanner(System.in);

    	    // Prompt the user to enter a search term
    	    System.out.print("Enter a search term: ");
    	    String searchTerm = scanner.nextLine();

    	    try {
    	        // Construct the SQL query to search for products
    	        String sql = "SELECT * FROM products WHERE products LIKE ? OR specifications LIKE ? OR numberofproducts LIKE ?";
    	        PreparedStatement statement = connection.prepareStatement(sql);
    	        statement.setString(1, "%" + searchTerm + "%");
    	        statement.setString(2, "%" + searchTerm + "%");
    	        statement.setString(3, "%" + searchTerm + "%");

    	        // Execute the query and get the results
    	        ResultSet resultSet = statement.executeQuery();

    	        // Display the results to the user
    	        while (resultSet.next()) {
    	            int id = resultSet.getInt("idproducts");
    	            String name = resultSet.getString("products");
    	            String description = resultSet.getString("specifications");
    	           
    	            int numberofproducts = resultSet.getInt("numberofproducts");
    	            String specifications = resultSet.getString("specifications");
    	            System.out.println("Product Details:");
    	            System.out.println("ID: " + id);
    	            System.out.println("Name: " + name);
    	            System.out.println("Description: " + numberofproducts);
    	            System.out.println("Specifications: " + specifications);

    	        }
    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	    }
    	    System.out.println("Search products Called.");
    	}

    

    /**
     * Calculate the total value of all items in the database.
     * 
     * @param connection the database connection
     */




        public static void calculateTotalvalue(Connection connection)  {
            Statement statement;
			try {
				statement = connection.createStatement();
				
				 ResultSet resultSet = statement.executeQuery("SELECT SUM(numberofproducts) AS total_value FROM products");
		            
		            if (resultSet.next()) {
		                int totalValue = resultSet.getInt("total_value");
		                System.out.println("Total value of products: " + totalValue);
		            }
		            
		            resultSet.close();
		            statement.close();
		        
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				System.out.println("calculatetotalvalue called.");
			}
			}
           
    }

    
