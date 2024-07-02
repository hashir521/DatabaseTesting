package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class inserData extends testingdb {
    WebDriver driver;

    public void dataBaseTesting() throws InterruptedException {
        String JDBC_URL = "jdbc:mysql://localhost:3306/test";
        String USERNAME = "root";
        String PASSWORD = "";
        String s;
        ResultSet rs;

        try (Connection con = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            System.out.println("Connected to the MySQL database.");

            Statement stmt = con.createStatement();

            String createTableQuery = "CREATE TABLE IF NOT EXISTS muhammad (" + "id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "username VARCHAR(50) NOT NULL," + "email VARCHAR(100) NOT NULL UNIQUE)";
            stmt.executeUpdate(createTableQuery);
            
            // Batch insert statements
	        stmt.addBatch("INSERT IGNORE INTO muhammad (username, email) VALUES ('Arqum', 'Arqum1222@gmail.com')");
	        stmt.addBatch("INSERT IGNORE INTO muhammad (username, email) VALUES ('Muaz', 'Muaz10@hotmail.com')");
	       stmt.addBatch("INSERT IGNORE INTO muhammad (username, email) VALUES ('Uzair', 'Uzair@2333@yahoo.com')");
	        stmt.addBatch("INSERT IGNORE INTO muhammad (username, email) VALUES ('Muhammad', 'Muhammad10@yahoo.com')");
	        stmt.addBatch("INSERT IGNORE INTO muhammad (username, email) VALUES ('Hasan', 'Hasan999@hotmail.com')");
	        stmt.addBatch("INSERT IGNORE INTO muhammad (username, email) VALUES ('Haq', 'Haq9@hotmail.com')");

	       
                        
            int rowsInserted[] = stmt.executeBatch();
            System.out.println(rowsInserted.length + " row(s) inserted.");

          //  stmt.executeUpdate("UPDATE muhammad set email = 'Arqum1222@gmail.com' where username = 'Arqum'");
           // stmt.executeUpdate("UPDATE muhammad SET email = 'Uzair@2333@yahoo.com' WHERE username = 'Uzair'");
           // stmt.executeUpdate("UPDATE muhammad SET email = 'Muhammad10@yahoo.com' WHERE username = 'Muhammad'");
           // stmt.executeUpdate("UPDATE muhammad SET email = 'Hasan999@hotmail.com' WHERE username = 'Hasan'");
           // System.out.println("Records updated successfully.");
            System.out.println("");
            System.out.println("DataBase Records");
            System.out.println("");

            s = "select username , email from muhammad";
            rs = stmt.executeQuery(s);

            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.get("https://demoqa.com/login");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            while (rs.next()) {
                String UserName = rs.getString("username");
                String Email = rs.getString("email");

                System.out.print(UserName + " ");
                System.out.println(Email);

                driver.findElement(By.id("userName")).sendKeys(UserName);
                driver.findElement(By.id("password")).sendKeys(Email);
                Thread.sleep(2000);

                WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("login")));

                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", loginButton);

                loginButton.click();
                Thread.sleep(4000);

                boolean loginSuccess = false;

                try {
                    // Check if login was successful
                    WebElement usernameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[@id='userName-value']")));
                    String displayedUsername = usernameElement.getText();

                    if (displayedUsername.equals(UserName)) {
                        System.out.println("Passed: Username matched");
                        System.out.println("Logged in as: " + displayedUsername);
                        loginSuccess = true;
                    }
                } catch (Exception e) {
                    System.out.println("Failed: Invalid username or password");
                    System.out.println("Failed: username or password does not matched");
                }

                if (!loginSuccess) {
                    // Handle unsuccessful login here
                    // For example, you can click on a "New User" button to register
                    WebElement newUserButton = driver.findElement(By.id("newUser"));
                    newUserButton.click();
                    driver.findElement(By.id("firstname")).sendKeys(UserName);
                    Thread.sleep(2000);

                    driver.findElement(By.id("lastname")).sendKeys(UserName);
                    Thread.sleep(2000);
                    driver.findElement(By.id("userName")).sendKeys(UserName);
                    Thread.sleep(2000);
                    driver.findElement(By.id("password")).sendKeys(Email);
                    Thread.sleep(3000);
                             
                    // Handle CAPTCHA manually
                    System.out.println("Please solve the CAPTCHA manually and press Enter to continue...");
                    Scanner scanner = new Scanner(System.in);
                    scanner.nextLine();  // Wait for the user to solve CAPTCHA and press Enter
                    driver.findElement(By.id("register")).click();
                    System.out.println("User Register Successfully."+ "\n");
                    driver.findElement(By.id("gotologin")).click();
                    Thread.sleep(2000);
                    continue;
                    
                    
                    
                }

                // Perform logout
                WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Log out')]")));
                logoutButton.click();

                // Wait for the login form to be visible after logout
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login")));
                System.out.println(UserName+ " " +"Logged out successfully" + "\n");
            }
			/*
			 * String deleteQuery = "DELETE FROM muhammad WHERE name = 'Jamil'"; int
			 * rowsAffected = stmt.executeUpdate(deleteQuery);
			 * 
			 * System.out.println(rowsAffected + " row(s) deleted.");
			 */

            con.close();
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database or execute query.");
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
