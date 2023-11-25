package FrameworkDesign.tests;

import java.io.IOException;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import FrameworkDesign.pageobjects.CartPage;
import FrameworkDesign.pageobjects.ProductCatalogue;
import FrameworkDesign.testComponents.BaseTest;
import FrameworkDesign.testComponents.Retry;

public class ErrorValidationTest extends BaseTest {

	@Test(groups = {"ErrorHandling"},retryAnalyzer = Retry.class)
	public void loginErrorValidation() throws IOException {
		
		
		landingPage.loginApplication("ajithkumar.s@gmail.com", "Ajith@165998");
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
		
	 
	}
	
	@Test(retryAnalyzer = Retry.class)
	public void productErrorValidation() throws InterruptedException {
		
		String productname = "ZARA COAT 3";
        ProductCatalogue ProductCatelog=landingPage.loginApplication("Hari@gmail.com", "Harikuttan@1999");
		
		List<WebElement> products = ProductCatelog.getProductList();
		
		ProductCatelog.addProductToCart(productname);
		CartPage CartPage=ProductCatelog.goToCartPage();
		
		boolean match = CartPage.VerifyProductDisplay("ZARA COAT 36");
		Assert.assertFalse(match);
	}

}
