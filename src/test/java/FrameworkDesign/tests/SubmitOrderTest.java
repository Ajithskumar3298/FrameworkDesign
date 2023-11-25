package FrameworkDesign.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import FrameworkDesign.pageobjects.CartPage;
import FrameworkDesign.pageobjects.CheckoutPage;
import FrameworkDesign.pageobjects.ConfirmationPage;
import FrameworkDesign.pageobjects.OrderPage;
import FrameworkDesign.pageobjects.ProductCatalogue;
import FrameworkDesign.testComponents.BaseTest;

public class SubmitOrderTest extends BaseTest {

	String productName = "ZARA COAT 3";

	@Test(dataProvider = "getData", groups = "Purchase")
	public void submitOrder(HashMap<String, String> input) throws IOException, InterruptedException {

		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(input.get("product"));
		Thread.sleep(2000);
		CartPage cartPage = productCatalogue.goToCartPage();

		Boolean match = cartPage.VerifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		Thread.sleep(2000);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("india");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		Thread.sleep(2000);
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

	}

	@Test(dependsOnMethods = { "submitOrder" })
	public void orderhistorypage() {

		ProductCatalogue ProductCatelog = landingPage.loginApplication("ajithkumar.s@gmail.com", "Ajith@1998");
		OrderPage orderPage = ProductCatelog.goToOrdersPage();
		Assert.assertTrue(orderPage.VerifyOrderDisplay(productName));

	}

	@DataProvider
	public Object[][] getData() throws IOException {

		List<HashMap<String, String>> data = getJsonDataToMap(
				System.getProperty("user.dir") + "\\src\\test\\java\\FrameworkDesign\\data\\PurchaseOrder.json");
		return new Object[][] { {data.get(0)}, {data.get(1)} };
		
	}

	/*
	 * @DataProvider 
	 * public Object[][] getData() {
	 * HashMap<String, String> firstdata = new HashMap<String, String>();
	 * firstdata.put("email", "ajithkumar.s@gmail.com"); firstdata.put("password",
	 * "Ajith@1998"); firstdata.put("product", "ZARA COAT 3");
	 * 
	 * HashMap<String, String> secounddata = new HashMap<String, String>();
	 * secounddata.put("email", "Hari@gmail.com"); secounddata.put("password",
	 * "Harikuttan@1999"); secounddata.put("product", "ADIDAS ORIGINAL");
	 * 
	 * return new Object[][] {{firstdata},{secounddata}}; }
	 * 
	 * 
	 * @DataProvider 
	 * public Object[][] getData() { return new Object[][] { {
	 * "ajithkumar.s@gmail.com", "Ajith@1998", "ZARA COAT 3" }, { "Hari@gmail.com",
	 * "Harikuttan@1999", "ADIDAS ORIGINAL" } }; }
	 */

}
