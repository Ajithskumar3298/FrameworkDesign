package FrameworkDesign.stepDefenition;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import FrameworkDesign.pageobjects.CartPage;
import FrameworkDesign.pageobjects.CheckoutPage;
import FrameworkDesign.pageobjects.ConfirmationPage;
import FrameworkDesign.pageobjects.LandingPage;
import FrameworkDesign.pageobjects.ProductCatalogue;
import FrameworkDesign.testComponents.BaseTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefenition_Impliment extends BaseTest {

	public LandingPage landingPage;
	public ProductCatalogue productCatalogue;
	public CartPage cartPage;
	public ConfirmationPage confirmationPage;

	@Given("I landed on Ecommerce page")
	public void I_landed_on_ecommerce_page() throws IOException {

		landingPage = launchApplication();
	}

	@Given("^Logged into the application with username (.+) and password (.+)$")
	public void logged_into_the_application_with_username_and_password(String username, String password) {

		productCatalogue = landingPage.loginApplication(username, password);
	}

	@When("^I Add (.+) to cart$")
	public void i_add_product_to_cart(String productName) throws InterruptedException {

		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
	}

	@When("^Checkout (.+) and submit the order$")
	public void Checkout_ProductName_and_submit_the_order(String productName) throws InterruptedException {

		cartPage = productCatalogue.goToCartPage();
		Boolean match = cartPage.VerifyProductDisplay(productName);
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("india");
		confirmationPage = checkoutPage.submitOrder();
	}

	@Then("{string} message is displayed on confirmation page")
	public void message_is_displayed_on_confirmation_page(String string) {

		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase(string));
		driver.close();
	}
	
	@Then("{string} message is displayed")
	public void message_is_displayed(String message) throws Throwable {
		
		Assert.assertEquals(message, landingPage.getErrorMessage());
		driver.close();
	}

}
