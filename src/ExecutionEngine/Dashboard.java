package ExecutionEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import Utility.CommonMethods;
import Utility.Dashboard_PG_POF;
import Utility.Log1;

public class Dashboard extends CommonMethods{
	 public static Dashboard_PG_POF dbPage;
	 public static boolean downloadStatus = false;
	
	@Test(priority=1)
	public static void testDashboard() throws InterruptedException, IOException, InvalidFormatException{
		dbPage = PageFactory.initElements(driver, Dashboard_PG_POF.class);
		float actCost = 0;
		WebElement pHeader;
		String winid = null;
		
		isprovider = TestCSV.ispname;
		dbPage.dashboardPlanlst.click();
		List<WebElement> baseplans1 = dbPage.dashboardPlanoptions;
		List<WebElement> baseplans2 = dbPage.dashboardPlan;
		
		List<WebElement> baseplans = new ArrayList<WebElement>();
		baseplans.addAll(baseplans2);
		baseplans.addAll(baseplans1);
		
		for(int i=0;i<baseplans.size();i++){
			System.out.println(baseplans.get(i).getText());
		}
		
		for(int i=0;i<baseplans.size();i++){
		try{
		baseplans.get(i).click();
		}
		catch(WebDriverException e){
			Thread.sleep(10000);
			baseplans.get(i).click();
		}
		pHeader = CommonMethods.findElement(driver, dbPage.pgHeader, 120);
		/** Downloading Excel File **/
		deleteFile(db_path);
		try{
		findElement(driver, dbPage.csvfile, 30).click();
		}
		catch(WebDriverException e){
			Thread.sleep(10000);
			findElement(driver, dbPage.csvfile, 30).click();
		}
		while(fileExists(db_path)){
			break;
		}
		if(browser.equalsIgnoreCase("chrome")){
		winid = get_winid();
		driver.switchTo().window(winid);
		driver.close();
		winid = get_winid();
		driver.switchTo().window(winid);
		}
		
		CommonMethods.getExcel(db_path);
		
		//Compare Compute Cost
		actCost = dbPage.get_dashboard_data(dbPage.computeCost);
		dbPage.verifyDashboardData(actCost, "ComputeCost", db_sheet1, db_sumcompcostcol,pHeader,"CSV_1");
		if(isprovider.contains("Azure")){
			dbPage.verifyDashboardData(actCost, "ComputeCost", db_sheet2, db_compcostcol_azure,pHeader, "CSV_2");
		}
		else if(isprovider.contains("Google")){
			dbPage.verifyDashboardData(actCost, "ComputeCost", db_sheet2, db_compcostcol_google,pHeader, "CSV_2");
		}
		else{
			dbPage.verifyDashboardData(actCost, "ComputeCost", db_sheet2, db_compcostcol,pHeader, "CSV_2");	
		}
		//Compare Storage Cost
		actCost = dbPage.get_dashboard_data(dbPage.storageCost);
		dbPage.verifyDashboardData(actCost, "StorageCost", db_sheet1, db_sumstoragecostcol,pHeader, "CSV_3");
		if(isprovider.contains("Google")){
			dbPage.verifyDashboardData(actCost, "StorageCost", db_sheet3, db_storagecostcol_google,pHeader, "CSV_4");
		}
		else{
			dbPage.verifyDashboardData(actCost, "StorageCost", db_sheet3, db_storagecostcol,pHeader, "CSV_4");
		}
		//Compare Network Cost
		actCost = dbPage.get_dashboard_data(dbPage.networkCost);
		dbPage.verifyDashboardData(actCost, "NetworkCost", db_sheet1, db_sumnwcostcol,pHeader, "CSV_5");
		dbPage.verifyDashboardData(actCost, "NetworkCost", db_sheet4, db_nwcostcol,pHeader, "CSV_6");
		
		//Compare Total Cost
		actCost = dbPage.get_dashboard_data(dbPage.totalCost);
		dbPage.verifyDashboardData(actCost, "TotalCost", db_sheet1, db_sumtotcostcol,pHeader, "CSV_7");
		dbPage.getGreenSphereCost(actCost, "TotalCost", pHeader,"CSV_7");
		
		if(isprovider.contains("Azure")){
			dbPage.testAdditionalCost(db_sheet2, pHeader,"CSV_31");
		}
		
		//Compare Duplicate Cost
		getExcel(db_path);
		dbPage.testDuplicateComponent(db_sheet1, 3, db_sumcomponent, pHeader, "CSV_32");
		
		}
		
		if(success!=false){
			success = flag;
		}
		Log1.info(""); 	
		Log1.info("method name : testDashboard ");
		Log1.info("Overall Test Result :"+getStatus());
		
	}

	}

