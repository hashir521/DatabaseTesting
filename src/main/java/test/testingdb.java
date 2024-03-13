package test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import com.mysql.cj.protocol.Resultset;

public class testingdb {

    public static void main(String[] args) {
        inserData d = new inserData();
        d.dataBaseTesting();

    }

}
