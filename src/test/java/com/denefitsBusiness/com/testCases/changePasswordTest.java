package com.denefitsBusiness.com.testCases;

import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.denefitsBusiness.com.Base.BaseTest;
import com.denefitsBusiness.com.util.Xls_Reader;
import com.denefitsBusiness.com.util.dataUtil;
import com.relevantcodes.extentreports.LogStatus;

public class changePasswordTest  extends BaseTest{
	
	String testName = "changepasswordTest";
	Xls_Reader xls;
	
	@Test(dataProvider = "data")
	public void login(Hashtable<String, String> data) throws InterruptedException
	{
		
		test = rep.startTest("Change Password Test");
		
		test.log(LogStatus.INFO, "Starting Change Password test");
		
		//System.out.println(data.get("UserName")+"----"+ data.get("Password")+"---"+ data.get("Browser")+"----"+ data.get("Runmode"));
				
		if(!dataUtil.isRunnable(testName, xls) || data.get("Runmode").equalsIgnoreCase("N"))
		{
			test.log(LogStatus.SKIP, "Skipping the testcase as Runmode is N");
			throw new SkipException("Skipping the testcase as Rumode is N");
		}
		
		openBrowser(data.get("Browser"));
		
		navigate("siteUrl");
		Thread.sleep(7000);
		
		login();
		Thread.sleep(2000);
		test.log(LogStatus.INFO, "Check For Login");
		takeScreenShot();
		Thread.sleep(3000);
		test.log(LogStatus.INFO, "Check For Login");
		takeScreenShot();
		WebElement prof_image = getElement("profile_image_link_xpath");
		Actions act = new Actions(driver);
		act.moveToElement(prof_image).build().perform();
		Thread.sleep(2000);
		driver.findElements(By.xpath("//a[text()='Change Password']")).get(1).click();
		Thread.sleep(2000);
		type("changePass_Current_xpath", data.get("CurrentPassword"));
		type("changePass_new_xpath", data.get("NewPassword"));
		type("changePass_confirmNew_xpath", data.get("ConfirmNewPassword"));
		click("changePass_confirm_button_xpath");	
		Thread.sleep(1500);
		test.log(LogStatus.INFO, "Check Password Changed");
		takeScreenShot();
	}
	
	
	@DataProvider
	public Object[][] data()
	{
		xls = new Xls_Reader(System.getProperty("user.dir") + "//data.xlsx");
		return dataUtil.getTestData(xls, "changepasswordTest");
	}
	
	@AfterMethod
	public void quit()
	{
		rep.endTest(test);
		rep.flush();
	}

}


