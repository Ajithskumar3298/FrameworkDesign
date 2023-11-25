package FrameworkDesign.testComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import FrameworkDesign.pageobjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	public WebDriver driver;
	public LandingPage landingPage;

	public WebDriver initializeDriver() throws IOException

	{
		// properties class
		ChromeOptions chromeoptions = new ChromeOptions();
		FirefoxOptions firefoxoption = new FirefoxOptions();
		EdgeOptions edgeoptions = new EdgeOptions();

		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
				+ "//src//main//java//FrameworkDesign//resources//GlobalData.properties");
		prop.load(fis);

		String browserName; // =prop.getProperty("browser");
		if (System.getProperty("browser") != null) {

			browserName = System.getProperty("browser");
		} else {
			browserName = prop.getProperty("browser");
		}
		// prop.getProperty("browser");

		if (browserName.contains("chrome")) {
			WebDriverManager.chromedriver().setup();
			if (browserName.contains("headless")) {

				chromeoptions.addArguments("headless");

			}
			driver = new ChromeDriver(chromeoptions);
			driver.manage().window().setSize(new Dimension(1440, 990));

			// Fire fox
		} else if (browserName.contains("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			if (browserName.contains("headless")) {

				firefoxoption.addArguments("headless");
			}

			driver = new FirefoxDriver(firefoxoption);
//			driver.manage().window().setSize(new Dimension(1440, 990));

			// Edge
		} else if (browserName.contains("edge")) {

			WebDriverManager.edgedriver().setup();
			if (browserName.contains("headless")) {

				edgeoptions.addArguments("headless");

			}
			driver = new EdgeDriver(edgeoptions);
//			driver.manage().window().setSize(new Dimension(1440, 990));
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;

	}

	public List<HashMap<String, String>> getJsonDataToMap(String filepath) throws IOException {

//		j-son to string
		String jsoncontent = FileUtils.readFileToString(new File(filepath), StandardCharsets.UTF_8);

//		String to hash map

		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsoncontent,
				new TypeReference<List<HashMap<String, String>>>() {
				});
		return data;

	}

	public String getScreenShot(String testCaseName, WebDriver driver) throws IOException {

		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir") + "//Reports//" + testCaseName + ".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir") + "//Reports//" + testCaseName + ".png";
	}

	@BeforeMethod(alwaysRun = true)
	public LandingPage launchApplication() throws IOException {

		driver = initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goTo();
		return landingPage;

	}

	@AfterMethod(alwaysRun = true)

	public void tearDown() {
		driver.quit();
	}
}
