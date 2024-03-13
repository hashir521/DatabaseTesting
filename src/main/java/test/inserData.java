package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class inserData extends testingdb{
	Connection con;
	 public inserData() {
	        this.con = con;
	    }

	    // Method to insert data into the database
	    public void dataBaseTesting() {
	    	String JDBC_URL = "jdbc:mysql://localhost:3306/test";
	        String USERNAME = "root";
	        String PASSWORD = "admin";
	        String s;
	        ResultSet rs;
	        WebDriver driver;
	       driver = new ChromeDriver();
	       driver.manage().window().maximize();
	       
	       driver.get("https://demo.guru99.com/test/newtours/");

	        // Step 1
	        try (Connection con = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
	            System.out.println("Connected to the MySQL database.");

	            // Step 2
	            Statement stmt = con.createStatement();
	            
	            
	            // Step 3
	            // Create the 'muhammad' table if it doesn't exist
	            String createTableQuery = "CREATE TABLE IF NOT EXISTS muhammad (" +
	                                      "id INT AUTO_INCREMENT PRIMARY KEY," +
	                                      "username VARCHAR(50) NOT NULL," +
	                                      "email VARCHAR(100) NOT NULL)";
	            stmt.executeUpdate(createTableQuery);

	            // Batch insert statements
//	            stmt.addBatch("INSERT IGNORE INTO muhammad (username, email) VALUES ('Arqum', 'Shakeel')");
//	            stmt.addBatch("INSERT IGNORE INTO muhammad (username, email) VALUES ('Uzair', 'Usaid')");
//	            stmt.addBatch("INSERT IGNORE INTO muhammad (username, email) VALUES ('Muhammad', 'Hashir')");
//	            stmt.addBatch("INSERT IGNORE INTO muhammad (username, email) VALUES ('Hasan', 'Hashir')");
	            
	            
	            int rowsInserted[] = stmt.executeBatch();
	            System.out.println(rowsInserted.length + " row(s) inserted.");
	            
	            // Update statements
	            stmt.executeUpdate("UPDATE muhammad set email = 'Arqum1222@gmail.com' where username = 'Arqum'");
	            stmt.executeUpdate("UPDATE muhammad SET email = 'Uzair@2333@yahoo.com' WHERE username = 'Uzair'");    
	            stmt.executeUpdate("UPDATE muhammad SET email = 'Muhammad10@yahoo.com' WHERE username = 'Muhammad'");
	            stmt.executeUpdate("UPDATE muhammad SET email = 'Hasan999@hotmail.com' WHERE username = 'Hasan'");
	            System.out.println("Records updated successfully.");
	            System.out.println("");
	            System.out.println("DataBase Records");
	            System.out.println("");
	            
	            
	            
	            		// Step 4
	            // get data from database using select command
	            s = "select username , email from muhammad";
	             rs = stmt.executeQuery(s);
	             // rs.next() is to check data is present or not
	             while(rs.next()) {
	           	 String UserName = rs.getString("username");
	                String Email = rs.getString("email");
	               
	           	 System.out.print(UserName+" ");
	            	 System.out.println(Email);
	            	 
	            	 driver.findElement(By.name("userName")).sendKeys(UserName);
	           	 driver.findElement(By.name("password")).sendKeys(Email);
	            	 driver.findElement(By.name("submit")).click();
	            	 
	            	 if(driver.getPageSource().equals("Thank you for Loggin.")) {
	            		 System.out.println("Passed");
	            	 }
	            	 else {
	            		 System.out.println("FAiled");
	            	 }
	            	// driver.findElement(By.xpath("/html/body/div[2]/table/tbody/tr/td[2]"
	            	 	//	+ "/table/tbody/tr[2]/td/table/tbody/tr/td[1]/a")).click();
	             }
	           
	            // Step 5
	            con.close();
	        } catch (SQLException e) {
	            System.err.println("Failed to connect to the database or execute query.");
	            e.printStackTrace();
	        }
	    }
}
