package org.example;


import com.opencsv.CSVReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileReader;
import java.time.Duration;
import java.util.List;

import static org.bouncycastle.oer.its.ieee1609dot2.basetypes.Duration.seconds;

public class WebScraperToCSVPartThree {

    static String csvFilePath = "C:\\Users\\aceau\\IdeaProjects\\SeleniumSelfHealingTest\\webpage_elements.csv";

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver;
        // Initialize WebDriver
        driver = new ChromeDriver();
        String url = "C:\\Users\\aceau\\OneDrive\\Documents\\Belong-sample\\BelongSample.html"; // Replace with the target URL

        try {
            driver.get(url);
            Thread.sleep(5000); // Wait for page load

            // Read CSV
            CSVReader reader = new CSVReader(new FileReader(csvFilePath));
            List<String[]> rows = reader.readAll();

            // Skip header row
            for (int i = 1; i < rows.size(); i++) {
                String[] row = rows.get(i);
                String tagName = row[0];
                String text = row[1];
                String attributes = row[2];
                String prediction = row.length > 3 ? row[3] : "Unverified"; // Handle Prediction column
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10) );
                try {
                    if (prediction.equals("Valid")) { // Example prediction logic
                        // Locate and interact with element using tag name and text
                        String xpath = String.format("//%s[contains(text(),'%s')]", tagName, text);
                        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
                        element.click();  // Example operation
                        System.out.println("Clicked element: " + text);
                        driver.navigate().back();
                    } else {
                        System.out.println("Skipping element due to prediction: " + prediction);
                    }
                } catch (Exception e) {
                    System.out.println("Failed to interact with element: " + text);
                    System.out.println("Error: " + e.getMessage());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
