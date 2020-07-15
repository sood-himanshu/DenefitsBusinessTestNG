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
import com.denefitsBusiness.com.util.Xls_Reader;
import com.denefitsBusiness.com.util.dataUtil;
import com.relevantcodes.extentreports.LogStatus;

public class createContractTest extends BaseTest {

	String testName = "createcontractTest";
	Xls_Reader xls;
	
	@Test(dataProvider = "data")
	public void createContract(Hashtable<String, String> data) throws InterruptedException
	{
		
		test = rep.startTest("Create Contract Test");
		
		test.log(LogStatus.INFO, "Starting Create Password Test");
		
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
		
		click("start_payment_button_xpath");
		Thread.sleep(5000);
		click("pay_over_time_button_xpath");
		Thread.sleep(2000);
		
		if(data.get("ContractType").equalsIgnoreCase("2"))
		{
			click("no_fee_financing_button_xpath");
		}
		else if(data.get("ContractType").equalsIgnoreCase("1"))
		{
			click("10_fee_financing_button_xpath");
		}
		Thread.sleep(5000);
		type("service_cost_xpath", data.get("ServiceCost"));
		click("service_cost_next_step_button_xpath");
		Thread.sleep(3000);
		click("service_breakdown_skip_button_xpath");
		Thread.sleep(3000);
		type("down_payment_input_xpath", data.get("DownPayment"));
		click("downpayment_next_button_xpath");
		Thread.sleep(8000);		
		click("select_plan_button_xpath");
		Thread.sleep(4000);
		type("customer_email_input_xpath", data.get("CustomerEmail"));
		click("customer_email_continue_button_xpath");
		Thread.sleep(5000);
	}
	
	
	
	@DataProvider
	public Object[][] data()
	{
		xls = new Xls_Reader(System.getProperty("user.dir") + "//data.xlsx");
		return dataUtil.getTestData(xls, "createcontractTest");
	}
	
	@AfterMethod
	public void quit()
	{
		rep.endTest(test);
		rep.flush();
	}
}
