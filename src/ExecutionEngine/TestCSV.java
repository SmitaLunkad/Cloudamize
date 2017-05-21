package ExecutionEngine;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import Utility.CommonMethods;
import Utility.MigrationPlanner_PG_POF;

public class TestCSV extends CommonMethods {
	public static String ispname = null;
		
	@Test
	 public void verify_CSV() throws InterruptedException, IOException, AWTException, InvalidFormatException{
			
	    	List<WebElement> lstisp = driver.findElements(By.xpath("//button[text()='View Details']"));
	    	for(int i=0;i<lstisp.size();i++){
	    		lstisp = driver.findElements(By.xpath("//button[text()='View Details']"));
	    		lstisp.get(i).sendKeys(Keys.ENTER);
	    		ispname = lstisp.get(i).getAttribute("id");
	    		if(ispname.contains("Aws")){
	    			ispname = "Aws";
	    		}
	    		else if(ispname.contains("Azure")){
	    			ispname = "Azure";
	    		}
	    		else if(ispname.contains("Google")){
	    			ispname = "Google";
	    		}
	    		mpPageStatus = false;
	    	
	    		Dashboard.testDashboard();
	    		/*MigrationPlanner.testMigrationPlannerDownloadAll();
	    		MigrationPlanner.testMigrationPlannerDetailedReports();
	    		MigrationPlanner.testMigrationSettings();
	    		*/
	    		driver.findElement(By.xpath("//div[@id='cloudamizeHeaderLogoImg']")).click();
	    		
	    	}
	}	    	
}
