package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;


        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.chrome.ChromeDriver;
        import com.opencsv.CSVWriter;

        import java.io.FileWriter;
        import java.io.IOException;
        import java.util.List;

public class WebScraperToCSV {
    public static void main(String[] args) {
        // Set the path to your WebDriver
        WebDriverManager.chromedriver().setup();
        WebDriver driver;
        // Initialize WebDriver
        driver = new ChromeDriver();

        // Open the target webpage
        String url = "C:\\Users\\aceau\\OneDrive\\Documents\\Belong-sample\\BelongSample.html"; // Replace with the target URL
        driver.get(url);

        try {
            // Wait for the page to load
            Thread.sleep(5000);

            // Find all elements on the page
            List<WebElement> elements = driver.findElements(By.xpath("//*"));

            // Set up the CSV writer
            String csvFile = "webpage_elements.csv";
            CSVWriter writer = new CSVWriter(new FileWriter(csvFile));

            // Write header row
            String[] header = {"Tag Name", "Text Content", "Attributes"};
            writer.writeNext(header);

            // Loop through all elements and write data to CSV
            for (WebElement element : elements) {
                String tagName = element.getTagName();
                String text = element.getText();

                // Get attributes using JavaScript
                String attributes = (String) ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                        "var items = ''; " +
                                "for (var i = 0; i < arguments[0].attributes.length; i++) { " +
                                "items += arguments[0].attributes[i].name + '=' + arguments[0].attributes[i].value + ' '; " +
                                "} return items.trim();",
                        element
                );

                // Write row to CSV
                String[] row = {tagName, text, attributes};
                writer.writeNext(row);
            }

            // Close the writer
            writer.close();

            System.out.println("Scraping complete. Data saved to 'webpage_elements.csv'.");
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        } finally {
            // Close the WebDriver
            driver.quit();
        }
    }
}
