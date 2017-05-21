package Utility;

import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class Dashboard_PG_POF{
	
	final WebDriver driver;
	 
	@FindBy(how = How.XPATH, using = "//div[@id='totalPhysicalComputeCostId' or @id='totalComputeCostId']")
 
	public List<WebElement> computeCost;
 
	@FindBy(how = How.XPATH, using = "//div[@id='totalPhysicalStorageCostId' or @id='totalStorageCostId']")
	 
	public List<WebElement> storageCost;

	@FindBy(how = How.XPATH, using = "//div[@id='totalPhysicalNetworkCostId' or @id='totalNetworkCostId']")
	 
	public List<WebElement> networkCost;
	
	@FindBy(how = How.XPATH, using = "//div[@id='totalPhysicalCostId' or @id='totalCostId']")
	 
	public List<WebElement> totalCost;
	
	@FindBy(how = How.XPATH, using = "//div[@id='costSavingValue']")
	 
	public List<WebElement> greenSphereCost;
	
	@FindBy(how = How.ID, using = "downloadCSVId")
	 
	public  WebElement csvfile;
	
	@FindBy(how = How.XPATH, using = "//div[contains(@class,'menuItem-hydra')]")
	
	public List<WebElement> dashboardPlan;
	
	@FindBy(how = How.XPATH, using = "//div[@class='dropdownmask-planning-hyd GKOKAFUBOR']//select")
	
	public WebElement dashboardPlanlst;
	
	@FindBy(how = How.XPATH, using = "//div[@class='dropdownmask-planning-hyd GKOKAFUBOR']//select//option")
	
	public List<WebElement> dashboardPlanoptions;
	
	@FindBy(how = How.XPATH, using = "//div[@class='GKOKAFUBEY']")
	
	public WebElement pgHeader;

	@FindBy(how = How.XPATH, using = "//img[contains(@src,'azure_logo_white.png')]")
	
	public WebElement IAASProviderHeader;
	
	
	public Dashboard_PG_POF(WebDriver driver){
 
		this.driver = driver;
 
		}
	
	 public float get_dashboard_data(List<WebElement> lst) throws InterruptedException{
	    	List<WebElement> cost = lst;
	 		float actCost=0;
	 		float dbCost = 0;
	 		
	 		for(WebElement tc:cost){
	 			actCost = CommonMethods.convert_cost("\\d*[.]\\d*|\\d*[^W^\\D]",tc.getText());
	 			dbCost=dbCost+actCost;
	 		}	
			return dbCost;
	     }
	 
	  
	 public float get_csv_data(String sheet, int cols ) throws IOException, InvalidFormatException{
    	 
	    	//CommonMethods.getExcel(path);
	 	 	Constants.ExcelWSheet = Constants.ExcelWBook.getSheet(sheet);
	 	 	int iNumber=Constants.ExcelWSheet.getLastRowNum()+1;
	 	 	String CellData=null;
	 	 	Float f1;
	 	 	float totalcost=0;
	 	 	
	 	 	for(int i=3;i<iNumber;i++){
	 	 		CellData = Constants.ExcelWSheet.getRow(i).getCell(cols).getStringCellValue();
	 	 		f1=Float.parseFloat(CellData);
	 	 		totalcost = totalcost+f1;
	 	 	}
			return totalcost;
	     }
	
	 
	  public void verifyDashboardData(float actCost, String costType, String sheetname, int colno, WebElement pHeader, String TestCaseId) throws IOException, InvalidFormatException{
		 
			float csvcost = get_csv_data(sheetname, colno);
			//WebElement pHeader = CommonMethods.findElement(driver, pgHeader, 120);
			
			Log1.info("");
			Log1.info("ISProvider --> "+Constants.isprovider);
			Log1.info("Dashboard Plan --> "+pHeader.getText());	
			Log1.info("Sheet : "+sheetname);
	 		Log1.info("Verify --> "+costType);
	 		Log1.info("Compare Dashboard_"+costType+" : "+actCost+"  Compare "+sheetname+"Sheet_"+costType+" : "+csvcost);
		 	
		 	if((Math.abs(actCost-csvcost))<=500){
		 		
		 		Log1.info("Dashboard_"+costType+" & "+sheetname+"Sheet_"+costType+" Match : true  ***"+TestCaseId+"***");
		 		Constants.flag=true;
		 	}
		 	else{
		 		Log1.info("Dashboard_"+costType+" & "+sheetname+"Sheet_"+costType+" Match : false  ***"+TestCaseId+"***");
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
	  
	  public void getGreenSphereCost(float actCost, String costType, WebElement pHeader, String TestCaseId) throws InterruptedException{
		  
		float greenspheretotcost = get_dashboard_data(greenSphereCost);
		
		Log1.info("");
		Log1.info("ISProvider --> "+Constants.isprovider);
		Log1.info("Dashboard Plan --> "+pHeader.getText());	
		Log1.info("Verify --> "+costType);
	 	Log1.info("Compare Dashboard_"+costType+" : "+actCost+" Compare GreenSphere_AnnualCost : "+greenspheretotcost);
	 		
		if((Math.abs(actCost-greenspheretotcost))<=500){
		Log1.info("Dashboard_"+costType+" & GreenSphere_AnnualCost Match : true ***"+TestCaseId+"***");
		Constants.flag=true;
		}
		else{
		Log1.info("Dashboard_"+costType+" & GreenSphere_AnnualCost Match : false ***"+TestCaseId+"***");
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
	  
	  public void testAdditionalCost(String sheetname, WebElement pHeader, String TestCaseId) throws IOException, InvalidFormatException{
		  	String SQLEdition;
		  	String Instance;
		  	String AdditionalCost;
		  	Constants.flag=true;
		  	
		  	//CommonMethods.getExcel(path);
	 	 	Constants.ExcelWSheet = Constants.ExcelWBook.getSheet(sheetname);
	 	 	int iNumber=Constants.ExcelWSheet.getLastRowNum();
	 	 	
	 	 	Log1.info("");
			Log1.info("ISProvider --> "+Constants.isprovider);
			Log1.info("Dashboard Plan --> "+pHeader.getText());	
			Log1.info("Sheet : "+sheetname);
			Log1.info("Verify --> Addition Cost for Azure");
	 	 	for(int i=3;i<iNumber;i++){
	 	 		SQLEdition = Constants.ExcelWSheet.getRow(i).getCell(5).getStringCellValue();

	 	 		if((SQLEdition.isEmpty() || SQLEdition.contains("-"))){
	 	 			continue;
	 	 		}
	 	 		else{
	 	 			AdditionalCost = Constants.ExcelWSheet.getRow(i).getCell(30).getStringCellValue();
	 	 			if(AdditionalCost.equals("0.0")){
	 	 				Instance = Constants.ExcelWSheet.getRow(i).getCell(Constants.db_compcomponent).getStringCellValue();
	 	 				Log1.info("No Additional Platform Cost found for "+Instance);
	 	 				Constants.flag = false;
	 	 			}
	 	 			
	 	 			}
	 	 		}
	 	 	if(Constants.flag==true){
	 	 		Log1.info("Additional Platform Cost Test Passed ***"+TestCaseId+"***");
		 		Log1.info("Success");
		 	}
		 	else
		 	{
		 		Log1.info("Additional Platform Cost Test Failed ***"+TestCaseId+"***");
		 		Log1.info("Failed");	
		 	}
	 	 	if(Constants.success!=false){
 				Constants.success = Constants.flag;
 			}	
	  }
	  
	  public void testDuplicateComponent(String sheetname, int startrow, int colno, WebElement pHeader, String TestCaseId) throws InvalidFormatException, IOException{
		  
		  Log1.info("");
		  Log1.info("ISProvider --> "+Constants.isprovider);
		  Log1.info("Dashboard Plan --> "+pHeader.getText());
		  Log1.info("Sheet : "+sheetname);
		  Log1.info("Verify --> Duplicate Instance Name");
		  
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
