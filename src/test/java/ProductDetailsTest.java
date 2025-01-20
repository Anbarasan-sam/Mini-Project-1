package MiniProject1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import utils.ScreenshotUtil;

public class ProductDetailsTest {
	private WebDriver driver;
	private HomePage homePage;
	private ProductDetailsPage productPage;
	private CartPage cartPage;

	@BeforeTest(groups = "Product")
	public void setUp() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.demoblaze.com/");
		homePage = new HomePage(driver);
	}

    @Test(groups = "Product")
    public void testRedirectionToProductDetailsPage() {
       try {
    	homePage = new HomePage(driver);
       productPage = new ProductDetailsPage(driver);
       Thread.sleep(2000);
        homePage.selectProduct("Samsung galaxy s6");
        Assert.assertTrue(productPage.isProductDetailsDisplayed(), "Product details page is not displayed.");
       
        homePage = new HomePage(driver);
        productPage = new ProductDetailsPage(driver);

        Assert.assertEquals(productPage.getProductName(), "Samsung galaxy s6", "Product name is incorrect.");
        Assert.assertTrue(productPage.getProductPrice().contains("360"), "Product price is incorrect.");
        Assert.assertEquals(productPage.getProductDescription(),productPage.getProductDescription(),"Product description is incorrect.");
   
    	   homePage = new HomePage(driver);
        productPage = new ProductDetailsPage(driver);
        cartPage = new CartPage(driver);

        productPage.clickAddToCart();
        
        Thread.sleep(2000);
        String alertMessage = driver.switchTo().alert().getText();
        Assert.assertTrue(alertMessage.contains("Product added"), "Product not added to cart.");
        driver.switchTo().alert().accept();

        homePage.clickCart();
        Thread.sleep(3000);
        Assert.assertEquals(cartPage.getCartItemCount(), 1, "Cart item count is incorrect.");
        Assert.assertTrue(cartPage.isProductInCart("Samsung galaxy s6"), "Product is not in the cart.");
    }
       catch(Exception e) {
    	e.printStackTrace();
    }
       }
    @AfterMethod(groups = "Product")
    public void takeScreenshotOnFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            ScreenshotUtil.takeScreenshot(driver, result.getName());
        }
    }
    
    @AfterTest(groups = "Product")
    public void tearDown() {
    	
			driver.quit();
    }
}
