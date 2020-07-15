package com.denefitsBusiness.com.testCases;

import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.Select;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.denefitsBusiness.com.Base.BaseTest;
import com.denefitsBusiness.com.util.Xls_Reader;
import com.denefitsBusiness.com.util.dataUtil;
import com.relevantcodes.extentreports.LogStatus;


public class registerTest extends BaseTest{
	
	String testName = "registerTest";
	Xls_Reader xls;
	
	@Test(dataProvider = "data")
	public void login(Hashtable<String, String> data) throws InterruptedException
	{
		
		test = rep.startTest("Register Test");
		
		test.log(LogStatus.INFO, "Starting Register test");
		
		//System.out.println(data.get("UserName")+"----"+ data.get("Password")+"---"+ data.get("Browser")+"----"+ data.get("Runmode"));
				
		if(!dataUtil.isRunnable(testName, xls) || data.get("Runmode").equalsIgnoreCase("N"))
		{
			test.log(LogStatus.SKIP, "Skipping the testcase as Runmode is N");
			throw new SkipException("Skipping the testcase as Rumode is N");
		}
		
		openBrowser(data.get("Browser"));
		
		navigate("siteUrl");
		Thread.sleep(7000);
		
		click("sign_up_link_xpath");
		Thread.sleep(10000);
		
		type("register_first_name_xpath", data.get("FirstName"));
		type("register_last_name_xpath", data.get("LastName"));
		click("register_phone_xpath");
		getElement("register_phone_xpath").sendKeys(Keys.HOME, data.get("Phone"));
		type("register_email_xpath", data.get("Email"));
		type("register_password_xpath", data.get("Password"));
		
		Select s = new Select(getElement("register_industry_drop_down_xpath"));
		Thread.sleep(2000);
		test.log(LogStatus.INFO, "Checking Email Address exist or not");
		takeScreenShot();
		s.selectByIndex(1);
		Thread.sleep(3000);
		s= new Select(getElement("register_sub_industry_drop_down_xpath"));
		s.selectByIndex(1);
		getElement("register_address_xpath").sendKeys(data.get("Address")); 
		
		click("register_zip_code_drop_down_xpath");
		Thread.sleep(2000);
		getElement("register_zip_xpath").sendKeys("12345");
		Thread.sleep(4000);
		getElement("register_zip_xpath").sendKeys(Keys.ENTER);
		click("sign_up_button_xpath");
		Thread.sleep(2000);
		test.log(LogStatus.INFO, "Taking Screenshot");
		takeScreenShot();
		Thread.sleep(7000);
		
		try
		{
			// Logic to check register successful or not
			String signUpButton=driver.findElement(By.xpath(prop.getProperty("sign_up_button_xpath"))).getText();
		
			if(signUpButton.equals("Sign Up")){
				System.out.println("Not able to register");
				test.log(LogStatus.FAIL, "Not Able to Login");
				takeScreenShot();
			}else{
				System.out.println("Able to register");
			}
		}
		catch(Throwable t)
		{
			test.log(LogStatus.INFO, "Check for the Registration");
			takeScreenShot();
			System.out.println("Able to Register");
		}
		
	}
	
	
	@DataProvider
	public Object[][] data()
	{
		xls = new Xls_Reader(System.getProperty("user.dir") + "//data.xlsx");
		return dataUtil.getTestData(xls, "registerTest");
	}
	
	@AfterMethod
	public void quit()
	{
		rep.endTest(test);
		rep.flush();
	}

}

