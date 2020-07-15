package com.denefitsBusiness.com.testCases;

import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.denefitsBusiness.com.Base.BaseTest;
import com.denefitsBusiness.com.util.*;
import com.relevantcodes.extentreports.LogStatus;


public class loginTest extends BaseTest{
	
	String testName = "loginTest";
	Xls_Reader xls;
	
	@Test(dataProvider = "data")
	public void login(Hashtable<String, String> data) throws InterruptedException
	{
		
		test = rep.startTest("Login Test");
		
		test.log(LogStatus.INFO, "Starting Login test");
		
		System.out.println(data.get("UserName")+"----"+ data.get("Password")+"---"+ data.get("Browser")+"----"+ data.get("Runmode"));
				
		if(!dataUtil.isRunnable(testName, xls) || data.get("Runmode").equalsIgnoreCase("N"))
		{
			test.log(LogStatus.SKIP, "Skipping the testcase as Runmode is N");
			throw new SkipException("Skipping the testcase as Rumode is N");
		}
		
		openBrowser(data.get("Browser"));
		
		navigate("siteUrl");
		
		type("login_email_name", data.get("UserName"));
		type("login_pass_xpath", data.get("Password"));
	    Thread.sleep(5000);
		click("login_signIn_xpath");
		}
	
	
	
	@DataProvider
	public Object[][] data()
	{
		xls = new Xls_Reader(System.getProperty("user.dir") + "//data.xlsx");
		return dataUtil.getTestData(xls, "loginTest");
	}
	
	@AfterMethod
	public void quit()
	{
		rep.endTest(test);
		rep.flush();
	}

}
