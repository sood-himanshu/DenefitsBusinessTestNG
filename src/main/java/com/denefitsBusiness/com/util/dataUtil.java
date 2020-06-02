package com.denefitsBusiness.com.util;

import java.util.Hashtable;

public class dataUtil
{
	public static Object[][] getTestData(Xls_Reader xls, String testCaseName)
	{
		String sheetName = "TestCases";
		String testName = testCaseName;
		int startTestRow = 1;
		while(!testName.equalsIgnoreCase(xls.getCellData(sheetName, 0, startTestRow)))
		{
			startTestRow ++;
		}
		
		System.out.println(startTestRow);
		
		int colStartRow = startTestRow + 1;
		int dataStartRow = startTestRow + 2;
		
		int rows = 0;
		while(!xls.getCellData(sheetName, 0, dataStartRow+rows).equals(""))
		{
			rows++;
		}
		
		System.out.println("rows are  ->"+rows);
		
		int cols = 0;
		while(!xls.getCellData(sheetName, cols, dataStartRow).equals(""))
		{
			cols++;
		}
		
		System.out.println("cols are "+cols);
		
		Hashtable<String, String> table = null;
		Object[][] data = new Object[rows][1];
		int datarow = 0;
		
		for(int rnum = dataStartRow; rnum < dataStartRow + rows ; rnum++)
		{
			table = new Hashtable<String, String>();
			for(int cnum = 0; cnum<cols; cnum++)
			{
				String key = xls.getCellData(sheetName, cnum, colStartRow);
				String value = xls.getCellData(sheetName, cnum, rnum);
				table.put(key, value);
			}
			data[datarow][0]=table;
			datarow++;
		}
		return data;
	}
	
	public static boolean isRunnable(String testCaseName, Xls_Reader xls)
	{
		String sheet = "TestRunmodes";
		int rows = xls.getRowCount(sheet);
		for(int r = 2 ; r <= rows ; r++)
		{
			String tName = xls.getCellData(sheet, "TCID", r);
			if(tName.equalsIgnoreCase(testCaseName))
			{
				String runmode = xls.getCellData(sheet, "Runmode", r);
				if(runmode.equalsIgnoreCase("Y"))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		return false;
	}

	

}
