package com.excilys.computerdatabase.selenium;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.utils.DateUtil;


public class ManagementComputer {
	private WebDriver driver;
	private Logger logger = LoggerFactory.getLogger(ManagementComputer.class);
	private final String url = "http://localhost:8080/computerdatabase-webapp/";
	
	@BeforeClass
	public static void init()
	{
		//If of executable => chmod 777 on this driver (yes in target)
		System.setProperty("webdriver.gecko.driver", ClassLoader.getSystemClassLoader().getResource("selenium/geckodriver").getFile());
	}
	
	@Before
	public void initBrowser()
	{
		driver = new FirefoxDriver();
	}
	
	@After
	public void closeBrowser()
	{
		driver.close();
	}
	
	@Test
	public void addComputer() throws InterruptedException
	{
		driver.get(url);
		List<WebElement> webElements = driver.findElements(By.tagName("a"));
		for (WebElement element : webElements) {
			if(element.getText().contains("Add"))
			{
				element.click();
				break;
			}
		}
		Thread.sleep(600L);
		String nameTestComputer = "Cheese !";
		driver.findElement(By.id("computerName")).sendKeys(nameTestComputer);
		String dateIntroduced = "2011-10-10";
		String dateDiscontinued ="2012-10-10" ;
		String nameEntreprise = "ASUS";
		changeDate("introduced", dateIntroduced);
		changeDate("discontinued", dateDiscontinued);
		Select selectCompanies  = new Select(driver.findElement(By.id("companyId")));
		selectCompanies.selectByVisibleText(nameEntreprise);
		
		driver.findElement(By.id("act")).click();
		
		Thread.sleep(500L);
		WebElement line = search(nameTestComputer, 
				DateUtil.formatDateToString(DateUtil.stringToDateInv(dateIntroduced)), 
				DateUtil.formatDateToString(DateUtil.stringToDateInv(dateDiscontinued)),
				nameEntreprise);
		
		delete(line);
	}
	
	@Test
	public void failAddComputerNoName() throws InterruptedException
	{
		driver.get(url);
		List<WebElement> webElements = driver.findElements(By.tagName("a"));
		for (WebElement element : webElements) {
			if(element.getText().contains("Add"))
			{
				element.click();
				break;
			}
		}
		Thread.sleep(500L);
		String nameTestComputer = "";
		driver.findElement(By.id("computerName")).sendKeys(nameTestComputer);
		
		driver.findElement(By.id("act")).click();
		
		if(!(driver.findElement(By.tagName("h1")) != null && 
				driver.findElement(By.tagName("h1")).getText().contains("Add Computer")))
		{
			fail("We should stay on the page Add Computer");
		}
	}
	
	@Test
	public void failAddComputerWrongDate () throws InterruptedException
	{
		driver.get(url);
		List<WebElement> webElements = driver.findElements(By.tagName("a"));
		for (WebElement element : webElements) {
			if(element.getText().contains("Add"))
			{
				element.click();
				break;
			}
		}
		Thread.sleep(500L);
		String nameTestComputer = "ERROR ";
		driver.findElement(By.id("computerName")).sendKeys(nameTestComputer);
		String dateIntroduced = "2012-10-10";
		String dateDiscontinued ="2011-10-10";
		changeDate("introduced", dateIntroduced);
		changeDate("discontinued", dateDiscontinued);
		Thread.sleep(500L);
		try
		{
			if(driver.findElement(By.id("act")).isEnabled())
			{
				driver.findElement(By.id("act")).click();
				if(!driver.findElement(By.id("main")).getText().contains("Error"))
					fail("The button should be unclickable because the date didn't match");
			}
		}
		catch (Exception e) {
			logger.warn("We catch the exception expected : " + e.getMessage());
		}
	}
	
	@Test
	public void testEdit() throws InterruptedException 
	{
		//Try edit the computer "ACE"
		String computer = "ACE";
		driver.get(url);
		List<WebElement> webElements = driver.findElements(By.tagName("a"));
		for (WebElement element : webElements) {
			if(element.getText().contains("computers"))
			{
				element.click();
				break;
			}
		}
		Thread.sleep(300L);
		WebElement line = search(computer);
		Thread.sleep(300L);
		try {
			line.findElement(By.tagName("a")).click();
			Thread.sleep(300L);
			String introduced="2000-01-01";
			String discontinued="2003-05-11";
			pageUpdate(introduced,discontinued);
			Thread.sleep(2000L);
			if(!driver.findElement(By.className("alert-success")).getText().contains("Success"))
			{
				logger.debug(driver.findElement(By.className("alert-success")).getText());
				fail("Fail updated");
			}
			line = search(computer, DateUtil.formatDateToString(DateUtil.stringToDateInv(introduced)), DateUtil.formatDateToString(DateUtil.stringToDateInv(discontinued)));

		}catch (Exception e) {
			logger.warn("We catch the exception expected : " + e.getMessage());
			fail("Didn't expected exception "+e.getMessage());
		}
		
	}
	
	/**
	 * *****************************************************************Take to much time
	 * @throws InterruptedException
	 */
	//@Test
	public void testChargeManySearch() throws InterruptedException
	{
		driver.get(url);
		List<WebElement> webElements = driver.findElements(By.tagName("a"));
		for (WebElement element : webElements) {
			if(element.getText().contains("computers"))
			{
				element.click();
				break;
			}
		}
		Thread.sleep(100L);
		for (char c = 'a'; c < 'z'; c++) {
			Thread.sleep(100L);
			WebElement searchBox = driver.findElement(By.id("searchbox"));
			searchBox.clear();
			searchBox.sendKeys(""+c);
			searchBox.sendKeys(Keys.RETURN);
			Thread.sleep(300L);
			int nbPage = Integer.parseInt(driver.findElement(By.id("selectPage")).getAttribute("max"));
			for(int i = 1; i < nbPage; i++)
			{
				Thread.sleep(100L);
				driver.findElement(By.id("selectPage")).sendKeys(Keys.ARROW_UP);
				driver.findElement(By.id("selectPage")).sendKeys(Keys.RETURN);
			}
			
		}
	}
	
	/*-------------------------------------------------UTILS-------------------------------------------------------------------------*/
	
	/**
	 * 
	 * @param nameTestComputer
	 * @param webElements
	 * @param contains List of string you want to find for this computer (like date or company), the date sould be like dd/MM/yyyy
	 * @return
	 * @throws InterruptedException
	 */
	private WebElement search(String nameTestComputer, String...contains ) throws InterruptedException
	{
		
		WebElement searchBox = driver.findElement(By.id("searchbox"));
		searchBox.sendKeys(nameTestComputer);
		searchBox.sendKeys(Keys.RETURN);
		
		Thread.sleep(500L);
		List<WebElement> webElements = driver.findElements(By.tagName("tr"));
		logger.debug("number of line", webElements.size());
		WebElement line = null;
		boolean asBeingAdded =false;
		for (WebElement webElement : webElements) {
			logger.debug(webElement.getText());
			if(webElement.findElement(By.tagName("a")).getText().equals(nameTestComputer))
			{
				line = webElement;
				asBeingAdded = true;
			}
		}
		if(!asBeingAdded)
			fail("The search of the computer failed");
		for (String elem : contains) {
			logger.debug("find ?"+elem);
			if(!line.getText().contains(elem))
				fail("Success added but the elem :"+elem+" was not been add");
		}
		return line;
	}
	
	private void pageUpdate(String introduced, String discontinued)
	{
		changeDate("introduced", introduced);
		changeDate("discontinued", discontinued);
		Select selectCompanies  = new Select(driver.findElement(By.id("companyId")));
		if(selectCompanies.getFirstSelectedOption().getText().contains("ASUS"))
			selectCompanies.selectByVisibleText("--");
		else
			selectCompanies.selectByVisibleText("ASUS");
		
		driver.findElement(By.id("act")).click();

	}
	
	private void delete(WebElement line) throws InterruptedException
	{
		driver.findElement(By.id("edit")).click();
		line.findElement(By.name("cb")).click();
		driver.findElement(By.id("deleteSelected")).findElement(By.tagName("i")).click();
		driver.switchTo().alert().accept();
		
		Thread.sleep(1000L);
		try {
			if(!(driver.findElement(By.className("alert")) != null && 
					driver.findElement(By.className("alert")).getText().contains("Success")))
				fail("The deleted of this computer failed");
		}catch (NoSuchElementException e) {
			fail("No exception expected");
		}
		
	}
	/**
	 * 
	 * @param idTag
	 * @param sDate yyyy-MM-dd
	 */
	private void changeDate(String idTag, String sDate)
	{
		JavascriptExecutor js;
		if (driver instanceof JavascriptExecutor) {
		    js = (JavascriptExecutor)driver;
		    if(driver.findElement(By.id(idTag)).isEnabled())
		    	js.executeScript("return document.getElementById('"+idTag+"').value='"+sDate+"';");
		}
	}

}
