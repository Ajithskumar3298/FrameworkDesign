package FrameworkDesign.tests;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import FrameworkDesign.pageobjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAlone {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		String productname = "ZARA COAT 3";
		String name = "Ajith Kumar";
		String cvv = "355";
		String country = "India";

		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();

		
		LandingPage landingpage = new  LandingPage(driver);
		
		landingpage.goTo();
		landingpage.loginApplication("ajithkumar.s@gmail.com", "Ajith@1998");
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));

		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

		WebElement Actualprod = products.stream()
				.filter(product -> product.findElement(By.cssSelector("b")).getText().equals(productname)).findFirst()
				.orElse(null);

		Actualprod.findElement(By.cssSelector(".card-body button:last-of-type")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));

		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));

		driver.findElement(By.cssSelector("[routerlink*= \"cart\"]")).click();
		List<WebElement> cartproduct = driver.findElements(By.cssSelector(".cartSection h3"));
		boolean match = cartproduct.stream().anyMatch(cartprod -> cartprod.getText().equalsIgnoreCase(productname));
		Assert.assertTrue(match);

		JavascriptExecutor scroll = (JavascriptExecutor) driver;
		
		scroll.executeScript("window.scrollBy(0,400)");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class*='subtotal'] button")));
	
		driver.findElement(By.cssSelector("[class*='subtotal'] button")).click();

		driver.findElement(By.xpath("//div[@class='field small']/input[@class='input txt']")).sendKeys(cvv);
		driver.findElement(By.xpath("//div[@class='field']/input[@class='input txt']")).sendKeys(name);
		driver.findElement(By.cssSelector("div input[placeholder='Select Country']")).sendKeys(country);

		List<WebElement> countrylist = driver.findElements(By.cssSelector("button[class*='list-group-item']"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		
		for (WebElement list : countrylist) {
			
			if(list.getText().equalsIgnoreCase(country)) {
				
				list.click();
			}
		}	
		scroll.executeScript("window.scrollBy(0,600)");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class*='btnn action']")));
		
		driver.findElement(By.cssSelector("[class*='btnn action']")).click();
		
		String confirmmessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		boolean message = confirmmessage.equalsIgnoreCase("THANKYOU FOR THE ORDER.");
		Assert.assertTrue(message);
		System.out.println(message);
		
	
		

	}

}
