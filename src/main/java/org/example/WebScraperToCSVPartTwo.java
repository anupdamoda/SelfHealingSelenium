package org.example;



import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
        import org.openqa.selenium.JavascriptExecutor;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.chrome.ChromeDriver;
        import com.opencsv.CSVWriter;

        import java.io.FileWriter;
        import java.io.IOException;
        import java.util.List;

public class WebScraperToCSVPartTwo {

    static String csvFilePath = "webpage_elements.csv";

    public static void main(String[] args) {
        // Set the path to your WebDriver
        WebDriverManager.chromedriver().setup();
        WebDriver driver;
        // Initialize WebDriver
        driver = new ChromeDriver();
        String url = "C:\\Users\\aceau\\OneDrive\\Documents\\Belong-sample\\BelongSample.html"; // Replace with target URL

        try {
            driver.get(url);
            Thread.sleep(5000); // Wait for page load

            // Scrape elements
            List<WebElement> elements = driver.findElements(By.xpath("//*"));
            try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath))) {
                writer.writeNext(new String[]{"Tag Name", "Text", "Attributes"});

                for (WebElement element : elements) {
                    String tagName = element.getTagName();
                    String text = element.getText().trim();
                    String attributes = (String) ((JavascriptExecutor) driver).executeScript(
                            "var items = ''; " +
                                    "for (var i = 0; i < arguments[0].attributes.length; i++) { " +
                                    "items += arguments[0].attributes[i].name + '=' + arguments[0].attributes[i].value + ';'; " +
                                    "} return items;",
                            element
                    );

                    writer.writeNext(new String[]{tagName, text, attributes});
                }
            }
            System.out.println("Data saved to: " + csvFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}


