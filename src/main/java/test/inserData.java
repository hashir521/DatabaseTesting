package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class inserData extends testingdb {
	Connection con;
	WebDriver driver;
	
	// Method to insert data into the database
	public void dataBaseTesting() throws InterruptedException {
		String JDBC_URL = "jdbc:mysql://localhost:3306/test";
		String USERNAME = "root";
		String PASSWORD = "admin";
		String s;
		ResultSet rs;
		
		/*
		 * driver = new ChromeDriver(); driver.manage().window().maximize();
		 * driver.get("https://demoqa.com/login");
		 * 
		 * // Implement scrolling JavascriptExecutor js = (JavascriptExecutor) driver;
		 * js.executeScript("window.scrollBy(0,50)");
		 */

		// Step 1
		try (Connection con = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
			System.out.println("Connected to the MySQL database.");

			// Step 2
			Statement stmt = con.createStatement();

			// Step 3
			// Create the 'muhammad' table if it doesn't exist
			String createTableQuery = "CREATE TABLE IF NOT EXISTS muhammad (" + "id INT AUTO_INCREMENT PRIMARY KEY,"
					+ "username VARCHAR(50) NOT NULL," + "email VARCHAR(100) NOT NULL)";
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
			
			  driver = new ChromeDriver();
			  driver.manage().window().maximize();
			  driver.get("https://demoqa.com/login");
			  
			  // Implement scrolling JavascriptExecutor js = (JavascriptExecutor) driver;
			//  js.executeScript("window.scrollBy(0,100)");
			 

			// Wait for the login form to be visible
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userName")));

			while (rs.next()) {
				String UserName = rs.getString("username");
				String Email = rs.getString("email");

				System.out.print(UserName + " ");
				System.out.println(Email);
				
				driver.findElement(By.id("userName")).sendKeys(UserName);
				driver.findElement(By.id("password")).sendKeys(Email);
				 Thread.sleep(2000);
				//driver.findElement(By.id("login")).click();
				// Wait for the login button to be clickable
                WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("login")));

                // Scroll to the login button
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", loginButton);

                // Click on the login button
                loginButton.click();
				Thread.sleep(4000);
				
				//WebElement usernameElement = driver.findElement(By.xpath("//button[contains(text(),'Log out')]"));
				// Wait for the username element to be visible after login
		        WebElement usernameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[@id='userName-value']")));
		        String displayedUsername = usernameElement.getText();
			
			    // Compare the displayed username with the expected username
			    if (displayedUsername.equals(UserName)) {
			        System.out.println("Passed: Username matched");
			        System.out.println("Logged in as: " + displayedUsername);
			        
			        // Perform logout
		            WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Log out')]")));
		            logoutButton.click();
		            
		            // Wait for the login button to be visible after logout
		            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login")));
		            System.out.println("Logged out successfully");

			    } else {
			        System.out.println("Failed: Username didn't match");
			    }
			
			}

			// Step 5
			con.close();
		} catch (SQLException e) {
			System.err.println("Failed to connect to the database or execute query.");
			e.printStackTrace();
		} finally {
			
				driver.quit();
			
		}
	}
}
