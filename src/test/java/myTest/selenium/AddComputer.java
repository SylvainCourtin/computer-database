package myTest.selenium;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AddComputer {
	private WebDriver driver;
	private Logger logger;
	
	@Before
	public void initBrowser()
	{
		System.setProperty("webdriver.gecko.driver", "/home/courtin/Documents/training-java/computer-database/src/main/resources/selenium/geckodriver");
		driver = new FirefoxDriver();
		logger = LoggerFactory.getLogger(AddComputer.class);
	}
	
	@After
	public void closeBrowser()
	{
		//webDriver.close();
	}
	
	@Test
	public void checkMenu() throws InterruptedException
	{
		driver.get("http://localhost:8080/computerdatabase/");
		List<WebElement> webElements = driver.findElements(By.tagName("a"));
		for (WebElement element : webElements) {
			if(element.getText().contains("Add"))
			{
				element.click();
				break;
			}
		}
		Thread.sleep(2000L);
		String nameTestComputer = "Cheese !";
		driver.findElement(By.id("computerName")).sendKeys(nameTestComputer);
		
		driver.findElement(By.id("act")).click();
		Thread.sleep(2000L);
		//On fait un search
		WebElement searchBox = driver.findElement(By.id("searchbox"));
		searchBox.sendKeys(nameTestComputer);
		searchBox.sendKeys(Keys.RETURN);
		
		Thread.sleep(2000L);
		webElements = driver.findElements(By.tagName("tr"));
		logger.debug("number of line", webElements.size());
		WebElement line = null;
		boolean asBeingAdded =false;
		for (WebElement webElement : webElements) {
			logger.debug(webElement.getText());
			if(webElement.getText().contains(nameTestComputer))
			{
				line = webElement;
				asBeingAdded = true;
			}
		}
		if(!asBeingAdded)
			fail("The new computer wasn't found");
		
		//maintenant on le delete
		driver.findElement(By.id("edit")).click();
		line.findElement(By.name("cb")).click();
		driver.findElement(By.id("deleteSelected")).findElement(By.tagName("i")).click();
		driver.switchTo().alert().accept();
		
		if(!driver.findElement(By.className("alert alert-success")).getText().contains("Success"))
			fail("Should be succesfull");
	}
}
