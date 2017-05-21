package Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import ExecutionEngine.MigrationPlanner;

public class MigrationPlanner_PG_POF{
	
	WebDriver driver=null;
	static String reptitle = null;
	static String sheetname=null;
	static WebElement report;
	int lastrow=0;
	static int status=0;
	String gname=null;
	int lastcol=0;
	 
	@FindBy(how = How.XPATH, using = "//span[@id='MigrationPlannerButtonId']")
 
	public WebElement migrationPlanner;
 
	@FindBy(how = How.XPATH, using = "//div[@class='name-label asset-label-box']")
	 
	public List<WebElement> groupName;
	
	@FindBy(how = How.XPATH, using = "//div[@title='Microsoft Outlook']")
	 
	public WebElement grMicrosoftOutlook;

	@FindBy(how = How.ID, using = "SummaryId")
	 
	public WebElement Summary;
	
	@FindBy(how = How.XPATH, using = "//span[@id='DashboardButtonId']")
	 
	public WebElement Dashboard;
	
	@FindBy(how = How.XPATH, using = "//select[@id='plansDropDownListId']")
	 
	public WebElement plan;
	
	@FindBy(how = How.XPATH, using = "//select[@id='plansDropDownListId']/option")
	 
	public List<WebElement> planList;
	
	@FindBy(how = How.XPATH, using = "//div[text()='Download All']")
	//div[@class='GKOKAFUBOT GKOKAFUBAU' and text()='Download All'] | //div[@class='GKOKAFUBLT GKOKAFUBNT' and text()='Download All']"
	public  WebElement downloadAll;
	
	@FindBy(how = How.XPATH, using = "//div[@id='noOfNodesId']")
	 
	public WebElement nodes;
	
	@FindBy(how = How.ID, using = "planTotalCostId")
	 
	public WebElement totalCost;
	
	@FindBy(how = How.ID, using = "planComputeCostId")
	 
	public WebElement computeCost;
	
	@FindBy(how = How.ID, using = "planStorageCostId")
	 
	public WebElement storageCost;
	
	@FindBy(how = How.ID, using = "planNetworkCostId")
	 
	public WebElement networkCost;
	
	@FindBy(how = How.ID, using = "//div[text()='Detailed']")
	
	public WebElement detailed;
	
	@FindBy(how = How.XPATH, using = "//div[@class='downloadMSCSV' and text()='Download Excel']")
	 
	public WebElement mSettingCSV;
	
	
	public MigrationPlanner_PG_POF(WebDriver driver){
 
		this.driver = driver;
 
		}
	
	 public List<WebElement> getGrElement(String grName){
		    List<WebElement> mSettingData = driver.findElements(By.xpath("//div[@class='action-grid-data-cell-group-name' and text()='"+grName+"']/ ../ ../ ../ ../ ../ ../td//div[contains(@class,'action-grid-data-cell')]"));
		    return mSettingData;
		    }
	
	public void get_detailed_report(String reportid) throws InterruptedException{
		try{
		report = driver.findElement(By.xpath("//div[@id='"+reportid+"']"));
		reptitle=report.findElement(By.xpath("//div[@id='"+reportid+"']/div")).getText();
		try{
			driver.findElement(By.xpath("//div[@id='"+reportid+"']//div[text()='Detailed']")).click();
		}
		catch(WebDriverException e){
			Thread.sleep(10000);
			driver.findElement(By.xpath("//div[@id='"+reportid+"']//div[text()='Detailed']")).click();
		}
		
		}
		catch(ElementNotVisibleException e){
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("return document.getElementById('interCommunication').scrollIntoView(true);");
			Thread.sleep(5000);
			report = driver.findElement(By.xpath("//div[@id='"+reportid+"']"));
			reptitle=report.findElement(By.xpath("//div[@id='"+reportid+"']/div")).getText();
			try{
				driver.findElement(By.xpath("//div[@id='"+reportid+"']//div[text()='Detailed']")).click();
			}
			catch(WebDriverException e1){
				Thread.sleep(10000);
				driver.findElement(By.xpath("//div[@id='"+reportid+"']//div[text()='Detailed']")).click();
			}
		}
	}
	
	public void validate_detailed_report(String sheetid,String TestCaseId, String groupname , String plan) throws InterruptedException{
		System.out.println("sheetid "+sheetid);
		Constants.ExcelWSheet = Constants.ExcelWBook.getSheet(sheetid);
		
		lastrow = Constants.ExcelWSheet.getLastRowNum();
		Log1.info("");
		Log1.info("ISProvider : "+Constants.isprovider);
	 	Log1.info("Migrator Group : "+groupname+" --> Plan : "+plan);
	 	Log1.info("Sheet : "+sheetid);
		Log1.info("Verify Detailed Report : "+reptitle+" --> for null values and no data");
		status = 1;
			if(lastrow>3){
			for(int i=4;i<lastrow;i++){
				if(status==1){
				for(int j=0;j<6;j++){
				
					try{
					boolean b=Constants.ExcelWSheet.getRow(i).getCell(j).getStringCellValue().equalsIgnoreCase("null");
					if(b){
						Log1.info(sheetid+" has null values");
						Log1.info(reptitle+" Test Failed ***"+TestCaseId+"***");
						Constants.flag=false;
						status=0;
						break;
					}
					
						}
					catch(NullPointerException e){
						continue;
					  }
				}//j
				}
				if(status==0){
					break;
				}
		    	}//i
			if(status==1){
				Log1.info(sheetid+" --> has data and does not contain null values");
				Log1.info(reptitle+" Test Passed ***"+TestCaseId+"***");
				Constants.flag=true;
				
			}
			}
				else{
					Log1.info(sheetid+" --> has no data");
					Log1.info(reptitle+" Test Failed ***"+TestCaseId+"***");
					Constants.flag=false;
				}	
			 if(Constants.success!=false){
		        	Constants.success = Constants.flag;
			 }
			 if(Constants.flag==true){				
			 		Log1.info("Success");
			 	}
			 	else
			 	{
			 		Log1.info("Failed");	
			 	}
			
	}	
	
	public void get_mPlanner(boolean mpPageStatus) throws InterruptedException{
		MigrationPlanner.mpPageStatus=mpPageStatus;
		WebElement ele = null;
		
		if(MigrationPlanner.mpPageStatus==false){
			try{
				ele = CommonMethods.findElement(driver, migrationPlanner, 200);
			}
			catch(NullPointerException n){
			while(ele==null){
				try{
					Thread.sleep(1000);	
					ele =driver.findElement(By.xpath("//span[@id='MigrationPlannerButtonId']"));
					}
			catch(NullPointerException n1){
				continue;
			}
			}
			}
			
			while(ele!=null){
				try{
					Thread.sleep(1000);
					driver.findElement(By.xpath("//span[@id='MigrationPlannerButtonId']")).click();
				}
				catch(NullPointerException e){
					continue;
				}
				catch(NoSuchElementException e){
					break;	
				}
				catch(WebDriverException w){
					continue;
				}
			}
				
			MigrationPlanner.mpPageStatus=true;
			}
	}
	
	public List<WebElement> get_mPlannerGroups() throws InterruptedException{
		
		List<WebElement> lstgroup1=null;
		List<WebElement> lstgroup = new ArrayList<WebElement>();
		
		if(CommonMethods.findenabledElement(driver, groupName, 30)){
			lstgroup1 = groupName;
			for(int i=0;i<lstgroup1.size();i++){
				if(!lstgroup1.get(i).getText().isEmpty() && !lstgroup1.get(i).getText().equals("no agent")){
					lstgroup.add(lstgroup1.get(i));
						}				
					}
				}
		return lstgroup;
	}
	
	public void compare_CSVSummary(String sheetname, int rowno,int colno, String uival,String condition,String TestCaseId, String groupname, String plan ){
		
		Constants.ExcelWSheet = Constants.ExcelWBook.getSheet(sheetname);
		lastrow = Constants.ExcelWSheet.getLastRowNum();
	 	Log1.info("");
	 	Log1.info("ISProvider : "+Constants.isprovider);
	 	Log1.info("Migrator Group : "+groupname+" --> Plan : "+plan);
	 	Log1.info("Sheet : "+sheetname);
	 	Log1.info("Verify --> "+condition);
	 	if(lastrow>=3){	
	 		
	 		String csvval = Constants.ExcelWSheet.getRow(rowno).getCell(colno).getStringCellValue();
        Log1.info("Compare MigrationPlanner_"+condition+" : "+uival+" Compare CSV_"+condition+" : "+csvval); 
        if(uival.equals(csvval)){
        	
        	Log1.info("MigrationPlanner_"+condition+" and CSV_"+condition+" match : true ***"+TestCaseId+"***");
        	Constants.flag=true;
        }
        else{
        	Log1.error("MigrationPlanner_"+condition+" and CSV_"+condition+" match : false ***"+TestCaseId+"***");
        	Constants.flag=false;
        }
	 	}
	 	else{
			Log1.info(sheetname+" Sheet has no data");	
			Log1.info(sheetname+" Test : Failed ***"+TestCaseId+"***");
			Constants.flag=false;
		}
        if(Constants.success!=false){
        	Constants.success = Constants.flag;
	 	}
        if(Constants.flag==true){				
	 		Log1.info("Success");
	 	}
	 	else
	 	{
	 		Log1.info("Failed");	
	 	}
        
}
	
	public void compare_CSVInfra(String sheetname, int rowno,int colno, String cost,String condition,String TestCaseId,String groupname, String plan){
		
		float uival = 0;
		uival = CommonMethods.convert_cost("\\d*[.]\\d*|\\d*[^W^\\D]",cost);
		Constants.ExcelWSheet = Constants.ExcelWBook.getSheet(sheetname);
		lastrow = Constants.ExcelWSheet.getLastRowNum();
	 	Log1.info("");
	 	Log1.info("ISProvider : "+Constants.isprovider);
	 	Log1.info("Migrator Group : "+groupname+" --> Plan : "+plan);
	 	Log1.info("Sheet : "+sheetname);
	 	Log1.info("Verify --> "+condition);
	 	
	 	if(lastrow>=3){	
        float actcost = 0;
        float value2 = 0;
        for(int i=rowno;i<=Constants.ExcelWSheet.getLastRowNum();i++){
        	
        	String CellData = Constants.ExcelWSheet.getRow(i).getCell(colno).getStringCellValue();
        	actcost=Float.parseFloat(CellData);
        	value2 = value2+actcost;
        }
        if(Math.abs(uival-value2)<=500){
        	Log1.info("Compare MigrationPlanner_"+condition+" : "+uival+" Compare CSV_"+condition+" : "+value2); 
        	Log1.info("MigrationPlanner_"+condition+" and CSV_"+condition+" match : true ***"+TestCaseId+"***");
        	Constants.flag=true;
        }
        else{
        	Log1.info("Compare MigrationPlanner_"+condition+" : "+uival+" Compare CSV_"+condition+" : "+value2);
        	Log1.error("MigrationPlanner_"+condition+" and CSV_"+condition+" match : false ***"+TestCaseId+"***");
        	Constants.flag=false;
        }
	 	}
	 	else{
			Log1.info(sheetname+" Sheet has no data");	
			Log1.info(sheetname+" Test : Failed ***"+TestCaseId+"***");
			Constants.flag=false;
		}
	 	 if(Constants.flag==true){				
		 		Log1.info("Success");
		 	}
		 	else
		 	{
		 		Log1.info("Failed");	
		 	}
        if(Constants.success!=false){
        	Constants.success = Constants.flag;
	 	}
}
	
	public void compare_MSettings(List<WebElement> lst, int rowno, int cols, String grName,String TestCaseId ){
		for(int j=0;j<cols;j++){
			if(j>8){
				TestCaseId="CSV_28";
			}
			String compareVal = Constants.ExcelWSheet.getRow(2).getCell(j).getStringCellValue();
			String mSettingsDataCSV = Constants.ExcelWSheet.getRow(rowno).getCell(j).getStringCellValue();
			String mSettingsDataUI = lst.get(j).getText();
			Log1.info("");
			Log1.info("ISProvider : "+Constants.isprovider);
			Log1.info("Migrator Group : "+grName);
			Log1.info("Verify --> Migration Settings");
			Log1.info("Compare UI_"+compareVal+" : "+mSettingsDataUI+" Compare CSV_"+compareVal+" : "+mSettingsDataCSV );
			
			if(mSettingsDataUI.equals(mSettingsDataCSV)){
				Log1.info("UI_"+compareVal+" & CSV_"+compareVal+" Match : true ***"+TestCaseId+"***");
				Constants.flag=true;
		 		}
		 	else{
		 		Log1.info("UI_"+compareVal+" & CSV_"+compareVal+" Match : false ***"+TestCaseId+"***");
		 		Constants.flag=false;
		 		}
		 	if(Constants.flag==true){
		 		Log1.info("Success");
		 		}
		 	else{
		 		Log1.info("Failed");	
		 		}
		 	if(Constants.success!=false){
		 		Constants.success = Constants.flag;
		 		}		
			}
	}
	 
	public void validate_firewall_report(String sheetid,String TestCaseId, String groupname){
		Constants.ExcelWSheet = Constants.ExcelWBook.getSheet(sheetid);
		sheetname = Constants.ExcelWSheet.getSheetName();
		lastrow = Constants.ExcelWSheet.getLastRowNum();
		String data=null; 
		String val, val1;
		Constants.flag=true;
		
		Log1.info("");
		Log1.info("ISProvider : "+Constants.isprovider);
		Log1.info("Migrator Group : "+groupname);
		Log1.info("Verify --> Firewalls Report for Duplicate Records");
		Log1.info("Sheet : "+sheetid);
		int lastcol = Constants.ExcelWSheet.getRow(4).getPhysicalNumberOfCells();
		for(int i=4; i<(lastrow-1); i++){
			val=null;
			for(int j=0;j<lastcol;j++){
				data = Constants.ExcelWSheet.getRow(i).getCell(j).getStringCellValue();
				val=val+" "+data; 
			}
			for(int m=i+1; m<lastrow; m++){
				val1=null;
				for(int n=0;n<lastcol;n++){
					data = Constants.ExcelWSheet.getRow(m).getCell(n).getStringCellValue();
					val1=val1+" "+data; 
				}
				if(val.equals(val1)){
					Constants.flag=false;
					Log1.info("Duplicate records at row no "+m);
				}	
			}
		}
		if(Constants.flag==false){
			Log1.info("Failed");
		}
		else if(Constants.flag==true){
			Log1.info("Passed");
		}
		if(Constants.success!=false){
	 		Constants.success = Constants.flag;
	 		}	
	}
	
	public void testDuplicateComponent(String sheetname, int startrow, int colno,String TestCaseId, String groupname, String plan) throws InvalidFormatException, IOException{
		  
		  Log1.info("");
		  Log1.info("ISProvider : "+Constants.isprovider);
		  Log1.info("Migrator Group : "+groupname+" --> Plan : "+plan);
		  Log1.info("Sheet : "+sheetname);
		  Log1.info("Verify --> Duplicate Instance Name ");
		  
		  CommonMethods.check_duplicate_records(sheetname, startrow, colno);
		  if(Constants.flag==true){
			  	Log1.info("All instance names are unique");
	 	 		Log1.info("Duplicate Instance Name Test Passed ***"+TestCaseId+"***");
		 		Log1.info("Success");
		 	}
		 	else
		 	{
		 		Log1.info("Duplicate Instance Name Test Failed ***"+TestCaseId+"***");
		 		Log1.info("Failed");	
		 	}
	 	 	if(Constants.success!=false){
				Constants.success = Constants.flag;
			}	
		  
		  
	  }
	
	
}
