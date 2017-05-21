package ExecutionEngine;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import Utility.CommonMethods;
import Utility.Log1;
import Utility.MigrationPlanner_PG_POF;

public class MigrationPlanner extends CommonMethods {
	public static MigrationPlanner_PG_POF mpPage;
	//public static boolean mpPageStatus = false;
	public static JavascriptExecutor js;
	public static List<WebElement> lstgroup;
	
	@Test(priority=5, enabled =true)
	public static void testMigrationPlannerDownloadAll() throws InterruptedException, IOException, AWTException, InvalidFormatException{
		
		mpPage = PageFactory.initElements(driver, MigrationPlanner_PG_POF.class);	
		Robot r = new Robot();
		String plan = null;
		
		isprovider = TestCSV.ispname;
		mpPage.get_mPlanner(mpPageStatus);
		lstgroup=mpPage.get_mPlannerGroups();
		
		for(int i=0;i<lstgroup.size();i++){
			String groupname = lstgroup.get(i).getText();
	    	lstgroup.get(i).click();
	    	Thread.sleep(3000);
	    	  
		js = (JavascriptExecutor)driver;
		js.executeScript("return document.getElementById('SummaryId').scrollIntoView(true);");
		
		findElement(driver, mpPage.Summary, 120).click();
		try{	
			findElement(driver, mpPage.plan, 120).click();
		}
		catch(WebDriverException wde){
			Thread.sleep(10000);	
			findElement(driver, mpPage.plan, 120).click();
		}
		List<WebElement> dropdown = mpPage.planList;
		
		for(int j=0;j<dropdown.size();j++)
		{
		plan = dropdown.get(j).getText();	
		dropdown.get(j).click();
		r.keyPress(KeyEvent.VK_ENTER);
		
		deleteFile(mPlanner_path+groupname+".xlsx");
		js.executeScript("return document.getElementById('interCommunication').scrollIntoView(true);");
		Thread.sleep(15000);
		if(mpPage.downloadAll != null){
			try{
				findElement(driver,mpPage.downloadAll,10).click();
			}
			catch(WebDriverException e){
				Thread.sleep(10000);
				findElement(driver,mpPage.downloadAll,10).click();
			}
		}
		
		while(fileExists(mPlanner_path+groupname+".xlsx")){
			break;
		}
		
		js.executeScript("return document.getElementById('noOfNodesId').scrollIntoView(true);");
		Thread.sleep(5000);
		String noOfNodes = mpPage.nodes.getText();
		String planTotalCost = mpPage.totalCost.getText();
		String planComputeCost = mpPage.computeCost.getText();
		String planStorageCost = mpPage.storageCost.getText();
		String planNetworkCost = mpPage.networkCost.getText();
		
		getExcel(mPlanner_path+groupname+".xlsx");   
        int rowno = 4;
        mpPage.compare_CSVSummary(mPlanner_sheet1,rowno,mp_nodescol,noOfNodes,"Nodes", "CSV8", groupname, plan);
        mpPage.compare_CSVSummary(mPlanner_sheet1,rowno,mp_totalcostcol, planTotalCost, "TotalCost", "CSV9", groupname, plan);
        mpPage.compare_CSVSummary(mPlanner_sheet1,rowno,mp_computecostcol,planComputeCost,"ComputeCost", "CSV10", groupname, plan);
        mpPage.compare_CSVSummary(mPlanner_sheet1,rowno,mp_storagecostcol,planStorageCost,"StorageCost", "CSV11", groupname, plan);
        mpPage.compare_CSVSummary(mPlanner_sheet1,rowno,mp_nwcostcol,planNetworkCost,"NetworkCost", "CSV12", groupname, plan);
        
        rowno=3;
        mpPage.compare_CSVInfra(mPlanner_sheet2,rowno,infsum_totalcostcol, planTotalCost, "TotalCost", "CSV23", groupname, plan);
        mpPage.compare_CSVInfra(mPlanner_sheet2,rowno,infsum_computecostcol,planComputeCost, "ComputeCost", "CSV23", groupname, plan);
        mpPage.compare_CSVInfra(mPlanner_sheet2,rowno,infsum_storagecostcol,planStorageCost, "StorageCost", "CSV23", groupname, plan);
        mpPage.compare_CSVInfra(mPlanner_sheet2,rowno,infsum_nwcostcol,planNetworkCost, "NetworkCost", "CSV23", groupname, plan);
        	
        if(isprovider.contains("Azure")){
        	mpPage.compare_CSVInfra(mPlanner_sheet3,rowno,comp_compcostcol_azure,planComputeCost,"ComputeCost", "CSV24", groupname, plan);
        }
        else if(isprovider.contains("Google")){
        	mpPage.compare_CSVInfra(mPlanner_sheet3,rowno,comp_compcostcol_google,planComputeCost,"ComputeCost", "CSV24", groupname, plan);
        }
        else{
        	mpPage.compare_CSVInfra(mPlanner_sheet3,rowno,comp_compcostcol_aws,planComputeCost,"ComputeCost", "CSV24", groupname, plan);
        }
        
		//Thread.sleep(2000);
        if(isprovider.contains("Google")){
        	mpPage.compare_CSVInfra(mPlanner_sheet4,rowno,st_storagecostcol_google,planStorageCost, "StorageCost", "CSV25", groupname, plan);
        }
        else{
		mpPage.compare_CSVInfra(mPlanner_sheet4,rowno,st_storagecostcol,planStorageCost, "StorageCost", "CSV25", groupname, plan);
        }
		//Thread.sleep(2000);	
		mpPage.compare_CSVInfra(mPlanner_sheet5,rowno,nw_networkcostcol,planNetworkCost,"NetworkCost", "CSV25", groupname, plan);
		
		//Verify Duplicate Instance name in Infrastructure Summary sheet
		mpPage.testDuplicateComponent(mPlanner_sheet2, 3, infsum_component, "CSV_33", groupname, plan);
		}
		
		}
		Log1.info("");
		Log1.info("method name : testMigrationPlannerDownloadAll ");
	    Log1.info("Overall Test Result : "+getStatus());
	  
	}
	
	@Test(priority=6, enabled=true)
	public static void testMigrationPlannerDetailedReports() throws InterruptedException, IOException, AWTException, InvalidFormatException{
		
		mpPage = PageFactory.initElements(driver, MigrationPlanner_PG_POF.class);	
		Robot r = new Robot();
		String plan = null;
		
		isprovider = TestCSV.ispname;
		mpPage.get_mPlanner(mpPageStatus);
		lstgroup=mpPage.get_mPlannerGroups();
		
		for(int i=0;i<lstgroup.size();i++){
			 String groupname = lstgroup.get(i).getText();
	    	 lstgroup.get(i).click();
	    	 Thread.sleep(3000);
	    
	    js = (JavascriptExecutor)driver;
		js.executeScript("return document.getElementById('SummaryId').scrollIntoView(true);");
		findElement(driver, mpPage.Summary, 120).click();
		try{	
			findElement(driver, mpPage.plan, 120).click();
		}
		catch(WebDriverException wde){
			Thread.sleep(5000);	
			findElement(driver, mpPage.plan, 120).click();
		}
		
		List<WebElement> dropdown = mpPage.planList;
		for(int j=0;i<1;i++)
		{
		plan = dropdown.get(j).getText();
		dropdown.get(j).click();
		r.keyPress(KeyEvent.VK_ENTER);
		
		/**Verifying Firewall Rules ( Inbound firwall, outbound firewall, UDP firewall)**/
        deleteFile(mPlanner_path+groupname+".xlsx");
		mpPage.get_detailed_report("firewallRulesBlock");
		while(fileExists(mPlanner_path+groupname+".xlsx")){
			break;
		}
		CommonMethods.getExcel(mPlanner_path+groupname+".xlsx");
		mpPage.validate_detailed_report("Inbound Firewall Rules","CSV_13",groupname,plan);
		mpPage.validate_detailed_report("Outbound Firewall Rules","CSV_14",groupname,plan);
		mpPage.validate_detailed_report("UDP Firewall Rules","CSV_15",groupname,plan);
		ExcelFile.close();
	
		/** Verifying Installed Apps **/
		deleteFile(mPlanner_path+groupname+".xlsx");
		mpPage.get_detailed_report("installedAppsBlock");
		while(fileExists(mPlanner_path+groupname+".xlsx")){
			break;
		}
		CommonMethods.getExcel(mPlanner_path+groupname+".xlsx");
		mpPage.validate_detailed_report("Installed Applications","CSV_16",groupname,plan);
		ExcelFile.close();	
		
		/** Verifying Client App DNS **/
		deleteFile(mPlanner_path+groupname+".xlsx");
		mpPage.get_detailed_report("appDNSBlock");
		while(fileExists(mPlanner_path+groupname+".xlsx")){
			break;
		}
		CommonMethods.getExcel(mPlanner_path+groupname+".xlsx");
		mpPage.validate_detailed_report("DNS","CSV_17",groupname,plan);
		ExcelFile.close();
		
		/** Verifying App CPU Usage **//*
		deleteFile(mPlanner_path+"CPU-Utilization-"+groupname+".xls");
		//Thread.sleep(10000);
		mpPage.get_report("appCPUBlock");	
		while(fileExists(mPlanner_path+"CPU-Utilization-"+groupname+".xls")){
			break;
		}
		CommonMethods.getExcel(mPlanner_path+"CPU-Utilization-"+groupname+".xls");
		mpPage.validate_detailed_report("CPU Usage","CSV_18",groupname,plan);
		ExcelFile.close();*/
		
		/** Verifying IP and DNS **/
		deleteFile(mPlanner_path+groupname+".xlsx");
		mpPage.get_detailed_report("ipAndDnsBlock");
		while(fileExists(mPlanner_path+groupname+".xlsx")){
			break;
		}
		CommonMethods.getExcel(mPlanner_path+groupname+".xlsx");
		mpPage.validate_detailed_report("IP and DNS","CSV_19",groupname,plan);
		ExcelFile.close();
		
		
		/** Verifying App Interconnectivity (All) **/
		deleteFile(mPlanner_path+groupname+".xlsx");
		mpPage.get_detailed_report("appInterconnectivity");
		while(fileExists(mPlanner_path+groupname+".xlsx")){
			break;
		}
		CommonMethods.getExcel(mPlanner_path+groupname+".xlsx");
		mpPage.validate_detailed_report("App Interconnectivity","CSV_20",groupname,plan);	
		ExcelFile.close();
		
		/** Verifying App Interconnectivity **/
		deleteFile(mPlanner_path+groupname+".xlsx");
		mpPage.get_detailed_report("appInterconnectivitySelectedOnly");
		while(fileExists(mPlanner_path+groupname+".xlsx")){
			break;
		}
		CommonMethods.getExcel(mPlanner_path+groupname+".xlsx");
		mpPage.validate_detailed_report("All","CSV_21",groupname,plan);	
		mpPage.validate_detailed_report("To Any Server","CSV_21",groupname,plan);
		mpPage.validate_detailed_report("From Any Client","CSV_21",groupname,plan);
		ExcelFile.close();
		
		/** Verifying Inter-Node Communication **/
		deleteFile(mPlanner_path+groupname+".xlsx");
		mpPage.get_detailed_report("interCommunication");
		while(fileExists(mPlanner_path+groupname+".xlsx")){
			break;
		}
		CommonMethods.getExcel(mPlanner_path+groupname+".xlsx");
		mpPage.validate_detailed_report("Summary","CSV_22",groupname,plan);
		mpPage.validate_detailed_report("Inter-Communicating Nodes","CSV_22",groupname,plan);
		mpPage.validate_detailed_report("Inter-Communicating Servers","CSV_22",groupname,plan);
		mpPage.validate_detailed_report("Inter-Communicating Clients","CSV_22",groupname,plan);
		ExcelFile.close();		
		
	}
  }
		Log1.info("");
		Log1.info("method name : testMigrationPlannerDetailedReports ");
        Log1.info("Overall Test Result : "+getStatus());
		
  }
	
	@Test(priority=7, enabled=true)
	public static void testMigrationSettings() throws AWTException, InterruptedException, IOException, InvalidFormatException{
		
		mpPage = PageFactory.initElements(driver, MigrationPlanner_PG_POF.class);
		List<WebElement> mSettings;
		int rowno = 0;
		String grName = null;
		TestCaseId = "CSV_27";
		
		isprovider = TestCSV.ispname;
		mpPage.get_mPlanner(mpPageStatus);
		deleteFile(mPlanner_path+mSettings_sheet6+".xlsx");
		mpPage.mSettingCSV.click();
		while(fileExists(mPlanner_path+mSettings_sheet6+".xlsx")){
			break;
		}
		getExcel(mPlanner_path+mSettings_sheet6+".xlsx"); 
		ExcelWSheet = ExcelWBook.getSheet(mSettings_sheet6);
		int cols = ExcelWSheet.getRow(2).getPhysicalNumberOfCells();
		int lastrow = ExcelWSheet.getLastRowNum();
		
		lstgroup=mpPage.get_mPlannerGroups();
		for(int i=0;i<lstgroup.size();i++){
			grName = lstgroup.get(i).getText();
			
			//get data from Migration Settings UI
			mSettings = mpPage.getGrElement(grName);
		
		//get col no from excel sheet
		for(int k=1;k<=lastrow;k++){
			String cellData = null;
			if(!(ExcelWSheet.getRow(k).getCell(0).getStringCellValue().isEmpty())){
			cellData = ExcelWSheet.getRow(k).getCell(0).getStringCellValue();
			if(cellData.equals(grName)){
				rowno = k;
				break;	
			}
			}
			else{
				continue;
			}
		}
		//compare ui & csv
		mpPage.compare_MSettings(mSettings, rowno, cols, grName, TestCaseId);
		
		}
		Log1.info(""); 	
		Log1.info("method name : testMigrationSettings ");
		Log1.info("Overall Test Result :"+getStatus());
	}
	
	@Test(priority=8, enabled=false)
	public static void testFirewallDuplicateReport() throws AWTException, InterruptedException, InvalidFormatException, IOException{
		
		mpPage = PageFactory.initElements(driver, MigrationPlanner_PG_POF.class);	
		
		isprovider = TestCSV.ispname;
		mpPage.get_mPlanner(mpPageStatus);
		lstgroup=mpPage.get_mPlannerGroups();
		
		for(int i=0;i<lstgroup.size();i++){
			String groupname = lstgroup.get(i).getText();
	    	lstgroup.get(i).click();
	    	Thread.sleep(3000);
	    	  
		js = (JavascriptExecutor)driver;
		js.executeScript("return document.getElementById('SummaryId').scrollIntoView(true);");
		
		findElement(driver, mpPage.Summary, 120).click();
		
		 deleteFile(mPlanner_path+groupname+".xlsx");
			mpPage.get_detailed_report("firewallRulesBlock");
			while(fileExists(mPlanner_path+groupname+".xlsx")){
				break;
			}
			CommonMethods.getExcel(mPlanner_path+groupname+".xlsx");
			mpPage.validate_firewall_report("Inbound Firewall Rules","CSV_29",groupname);
			mpPage.validate_firewall_report("Outbound Firewall Rules","CSV_30",groupname);
		}
		Log1.info(""); 	
		Log1.info("method name : testFirewallDuplicateReport ");
		Log1.info("Overall Test Result :"+getStatus());
		
	}
		
}
