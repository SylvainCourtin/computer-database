package myTest.selenium;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bddTest.MyBDDTest;


public class ManagementComputer {
	private WebDriver driver;
	private Logger logger;
	
	@BeforeClass
	public static void init()
	{
		MyBDDTest.getInstance().init();
		System.setProperty("webdriver.gecko.driver", "/home/courtin/Documents/training-java/computer-database/src/main/resources/selenium/geckodriver");
	}
	
	@AfterClass
	public static void destroy()
	{
		MyBDDTest.getInstance().destroy();
	}
	
	@Before
	public void initBrowser()
	{
		driver = new FirefoxDriver();
		logger = LoggerFactory.getLogger(ManagementComputer.class);
	}
	
	@After
	public void closeBrowser()
	{
		driver.close();
	}
	
	@Test
	public void addComputer() throws InterruptedException
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
		driver.findElement(By.id("introduced")).sendKeys("10102010");
		driver.findElement(By.id("discontinued")).sendKeys("10102012");
		Select selectCompanies  = new Select(driver.findElement(By.id("companyId")));
		selectCompanies.selectByVisibleText("ASUS");
		
		driver.findElement(By.id("act")).click();
		Thread.sleep(2000L);

		WebElement line = search(nameTestComputer, webElements);
		
		delete(line);
	}
	
	@Test
	public void failAddComputerNoName() throws InterruptedException
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
		String nameTestComputer = "";
		driver.findElement(By.id("computerName")).sendKeys(nameTestComputer);
		
		driver.findElement(By.id("act")).click();
		Thread.sleep(2000L);
		
		if(!(driver.findElement(By.tagName("h1")) != null && 
				driver.findElement(By.tagName("h1")).getText().contains("Add Computer")))
		{
			fail("We should stay on the page Add Computer");
		}
	}
	
	@Test
	public void failAddComputerWrongDate () throws InterruptedException
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
		String nameTestComputer = "ERROR ";
		driver.findElement(By.id("computerName")).sendKeys(nameTestComputer);
		driver.findElement(By.id("introduced")).sendKeys("10102010");
		driver.findElement(By.id("discontinued")).sendKeys("10102000");
		
		
		try
		{
			driver.findElement(By.id("act")).click();
			fail("The button is unclickable");
		}
		catch (Exception e) {
			logger.debug("We catch the exception expected : " + e.getMessage());
		}
	}
	
	private WebElement search(String nameTestComputer, List<WebElement> webElements) throws InterruptedException
	{
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
			fail("The search of the new computer failed");
		return line;
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
	
	private void selectDate(String sDate, WebElement selectDate)
	{
		/*
		  //button to open calendar

        WebElement selectDate = driver.findElement(By.xpath("//span[@aria-controls='datetimepicker_dateview']"));
        
    selectDate.click();

    //button to move next in calendar

    WebElement nextLink = driver.findElement(By.xpath("//div[@id='datetimepicker_dateview']//div[@class='k-header']//a[contains(@class,'k-nav-next')]"));

    //button to click in center of calendar header

    WebElement midLink = driver.findElement(By.xpath("//div[@id='datetimepicker_dateview']//div[@class='k-header']//a[contains(@class,'k-nav-fast')]"));

    //button to move previous month in calendar

    WebElement previousLink = driver.findElement(By.xpath("//div[@id='datetimepicker_dateview']//div[@class='k-header']//a[contains(@class,'k-nav-prev')]")); 

        //Split the date time to get only the date part

        String date_dd_MM_yyyy[] = (dateTime.split(" ")[0]).split("/");

        //get the year difference between current year and year to set in calander

        int yearDiff = Integer.parseInt(date_dd_MM_yyyy[2])- Calendar.getInstance().get(Calendar.YEAR);

        midLink.click();

        if(yearDiff!=0){

            //if you have to move next year

            if(yearDiff>0){

                for(int i=0;i< yearDiff;i++){

                    System.out.println("Year Diff->"+i);

                    nextLink.click();

                }

            }

            //if you have to move previous year

            else if(yearDiff<0){

                for(int i=0;i< (yearDiff*(-1));i++){

                    System.out.println("Year Diff->"+i);

                    previousLink.click();

                }

            }

        }
        
        Thread.sleep(1000);

        //Get all months from calendar to select correct one

        List<WebElement> list_AllMonthToBook = driver.findElements(By.xpath("//div[@id='datetimepicker_dateview']//table//tbody//td[not(contains(@class,'k-other-month'))]"));
        
        list_AllMonthToBook.get(Integer.parseInt(date_dd_MM_yyyy[1])-1).click();
        
        Thread.sleep(1000);

        //get all dates from calendar to select correct one

        List<WebElement> list_AllDateToBook = driver.findElements(By.xpath("//div[@id='datetimepicker_dateview']//table//tbody//td[not(contains(@class,'k-other-month'))]"));
        
        list_AllDateToBook.get(Integer.parseInt(date_dd_MM_yyyy[0])-1).click();
        
        ///FOR TIME

        WebElement selectTime = driver.findElement(By.xpath("//span[@aria-controls='datetimepicker_timeview']"));

        //click time picker button

        selectTime.click();

        //get list of times

        List<WebElement> allTime = driver.findElements(By.xpath("//div[@data-role='popup'][contains(@style,'display: block')]//ul//li[@role='option']"));
      
        dateTime = dateTime.split(" ")[1]+" "+dateTime.split(" ")[2];

     //select correct time

        for (WebElement webElement : allTime) {

            if(webElement.getText().equalsIgnoreCase(dateTime))

            {

                webElement.click();
		 */
	}
}
