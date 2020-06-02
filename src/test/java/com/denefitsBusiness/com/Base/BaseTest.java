package com.denefitsBusiness.com.Base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

import com.denefitsBusiness.com.util.ExtentManager;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;




public class BaseTest {
	
	public static WebDriver driver;
	public Properties prop;
	public ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;
	
	
	public void openBrowser(String bType)
	{
		prop = new Properties();
		try
		{
			FileInputStream fn = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\java\\business\\denefits\\com\\resources\\projectConfig.properties");
		    prop.load(fn);
		} catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if((prop.getProperty(bType)).equalsIgnoreCase("mozilla"))
		{
			//System.setProperty("webdriver.gecko.driver", "D:\\New folder (2)\\Practise\\Jars\\jars\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		else if((prop.getProperty(bType)).equalsIgnoreCase("chrome"))
		{
			driver = new ChromeDriver();
		}
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
	}
	
	public void navigate(String url)
	{
		driver.get(prop.getProperty(url));
		
	}
	
	public void click(String locatorkey)
	{
		getElement(locatorkey).click();
	}
	
	public void type(String locatorkey, String text)
	{
		getElement(locatorkey).sendKeys(text);
	}
	
	public WebElement getElement(String locatorkey)
	{
		WebElement e = null;
		try
		{
			if(locatorkey.endsWith("_id"))
			{
				e = driver.findElement(By.id(prop.getProperty(locatorkey)));
			}
			else if(locatorkey.endsWith("_xpath"))
			{
				e = driver.findElement(By.xpath(prop.getProperty(locatorkey)));
			}
			else if(locatorkey.endsWith("_name"))
			{
				e = driver.findElement(By.name(prop.getProperty(locatorkey)));
			}
			else
			{
				reportFailure("Locator not found" + locatorkey);
				Assert.fail("Locator not founc" +locatorkey);
			}
		}
		catch(Exception ex)
		{
			reportFailure(ex.getMessage());
			ex.printStackTrace();
			Assert.fail("Failed the test "+ ex.getMessage());
		}
		
		return e;
	}
	
	/*************Validation**********************************************************/
	
	public boolean verifyTitle()
	{
		return false;
	}
	
	public boolean verifyText(String locatorkey, String expectedResult)
	{
		String actualText = getElement(locatorkey).getText().trim();
		String expectedText = prop.getProperty(expectedResult);
		if(actualText.equals(expectedText))
		{
			return true;
		}
		else
		{		
			return false;
		}
	}
		
	
	public boolean isElementPresent(String locatorkey)
	{
		List<WebElement> elementList=null;
		if(locatorkey.endsWith("_id"))
		{
			elementList = driver.findElements(By.id(prop.getProperty(locatorkey)));
		}
		else if(locatorkey.endsWith("_xpath"))
		{
			elementList = driver.findElements(By.xpath(prop.getProperty(locatorkey)));
		}
		else if(locatorkey.endsWith("_name"))
		{
			elementList = driver.findElements(By.name(prop.getProperty(locatorkey)));
		}
		else
		{
			reportFailure("Locator not found" + locatorkey);
			Assert.fail("Locator not founc" +locatorkey);
		}
		
		if(elementList.size()==0)
		{
			return false;
		}
		else
		{
			return true;
		}
		
	}
	
	/**************Reporting Function********************************************************************/

	public void reportPass(String msg)
	{
		test.log(LogStatus.PASS, "Test Passed" + msg);
	}
	
	public void reportFailure(String msg)
	{
		test.log(LogStatus.FAIL, "Test failed " +msg);
		takeScreenShot();
		Assert.fail(msg);
	}
	
	
	public static void takeScreenShot()
	{
			Date d=new Date();
			String screenshotName=d.toString().replace(":", "_").replace(" ", "_")+".png";
			File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(srcFile, new File(System.getProperty("user.dir")+"//screenshots//" +screenshotName));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		test.log(LogStatus.INFO, "Screenshot -> " +test.addScreenCapture(System.getProperty("user.dir")+"//screenshots//" +screenshotName));
			
	}
	
}
