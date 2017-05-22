package Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.xml.DOMConfigurator;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import Utility.Log1;

public class CommonMethods extends Constants{
	public static String browser = null;
	public static String ispname = null;
	    @BeforeTest
		public static void login() throws InterruptedException, IOException{
	    	startTime = System.currentTimeMillis();
	    	browser = System.getProperty("browser");
	    	//browser="chrome";
	    	if(browser.equalsIgnoreCase("firefox")){
	    		CommonMethods.useFirefox();	
	    	}
	    	else if(browser.equalsIgnoreCase("chrome")) {
	    		CommonMethods.useChrome();
	    	}
	    	else{
	    		CommonMethods.useFirefox();
	    	}
			driver.manage().window().setSize(new Dimension(1000,500));
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
			
			//************Initialize Logs***********//
			DOMConfigurator.configure("log4j.xml");
			
			/** Logging to cloudamize MainPage **/
			String uid = System.getProperty("uid");
			driver.get("https://qa.cloudamize.com/login.jsp");
			driver.findElement(By.id("j_username")).sendKeys(uid);
			driver.findElement(By.id("j_password")).sendKeys("cc");
			driver.findElement(By.id("loginSubmit")).click();
			Thread.sleep(5000);
		}
	    
	    public static void useFirefox() {
	    	
	    	FirefoxProfile fp = new FirefoxProfile();
			fp.setPreference("browser.download.folderList",2);
			//fp.setPreference("browser.download.manager.showWhenStarting",false);
			fp.setPreference("browser.download.dir",file_location);
			fp.setPreference("browser.download.lastDir",file_location);
			fp.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/plain, application/vnd.ms-excel, text/csv, text/comma-separated-values, application/octet-stream");
			driver = new FirefoxDriver(fp);
	    }
	    
	    public static void useChrome() {
	       
	        System.setProperty("webdriver.chrome.driver","D:\\Cloudamize\\Jars\\chromedriver.exe");
	        String downloadFilepath = file_location;
	  
	        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
	        chromePrefs.put("profile.default_content_settings.popups", 0);
	        chromePrefs.put("download.default_directory", downloadFilepath);
	        chromePrefs.put("credentials_enable_service", false);
	        chromePrefs.put("profile.password_manager_enabled", false);
	        chromePrefs.put("download.extensions_to_open", "");
	        chromePrefs.put("download.prompt_for_download", false);
	        ChromeOptions options = new ChromeOptions();
	        HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
	        options.setExperimentalOption("prefs", chromePrefs);
	        options.addArguments("--test-type");
	        options.addArguments("--disable-extensions"); //to disable browser extension popup
	   
	        DesiredCapabilities cap = DesiredCapabilities.chrome();
	        cap.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
	        cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	        cap.setCapability(ChromeOptions.CAPABILITY, options);
	        driver = new ChromeDriver(cap);  
	               
	         }
	    
	    public static boolean fileExists(String path) throws InterruptedException{
	    	File file = new File(path);
	    	while (!file.exists()) {
	    	    Thread.sleep(1000);
	    	}
			return true;	
	    }
		 
	    public static String get_winid(){
	    	String winid = null;
	    	for(String id : driver.getWindowHandles() ){
	    		winid = id;
	    	}
			return winid;
	    }
	    
	     public static void getExcel(String path) throws IOException, InvalidFormatException{
	    	 
	     	ExcelFile = new FileInputStream(path);
	     	ExcelWBook = new XSSFWorkbook(ExcelFile);
	     	//ExcelWBook = WorkbookFactory.create(ExcelFile);
	     }
	     
	     public static String match_pattern(String exp, String txt){
				Pattern p = Pattern.compile(exp);
				Matcher m = p.matcher(txt);
				String s=null ;
				while(m.find()){
				s= m.group();
				}
				return s;
	     }
	     
	     public static float convert_cost(String exp, String txt){
	    	 float f = 0;
		 	 String s = null;
		 	 
		 	 s= match_pattern(exp,txt);
		 		if(txt.contains("k")){
		 				f = Float.parseFloat(s);
		 				f = f*1000;
		 			}
		 		else{
		 				f = Float.parseFloat(s);
		 			}
	    	 
			return f;
	    	 
	     }
	     	     
	     public static String switch_window(){
	    	 String winid = "null";
	 		for(String win:driver.getWindowHandles()){
	 			winid=win;
	 		}
			return winid;
	     }
	     
	     public static String getStatus(){
	    	 if(success == true){
	 	 		return("Success");
	 	 	}
	 	 	else{
	 	 		return("Failed");
	 	 	} 
	     }
	     
	     public static void deleteFile(String filepath) throws InterruptedException{
			 File file = new File(filepath);
			 file.delete();
			 //Thread.sleep(10000);	
			 
		 }
	     
	     public static WebElement findElement(final WebDriver driver, final WebElement we, final int timeoutSeconds) {
	 	    FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
	 	            .withTimeout(timeoutSeconds, TimeUnit.SECONDS)
	 	            .pollingEvery(500, TimeUnit.MILLISECONDS)
	 	            .ignoring(NoSuchElementException.class);
	 	   
	 	    	return wait.until(ExpectedConditions.elementToBeClickable(we));
	 	    	
	 }
	     
	     public static Boolean findenabledElement(final WebDriver driver, final List<WebElement> lst, final int timeoutSeconds) {
	    		
		 	    FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
		 	            .withTimeout(timeoutSeconds, TimeUnit.SECONDS)
		 	            .pollingEvery(500, TimeUnit.MILLISECONDS);
		 	            
		 	    return wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOfAllElements(lst))) ;
		 }
	     
	     public static void check_duplicate_records(String sheetid, int startrowno, int colno ) throws InvalidFormatException, IOException{
	 		Constants.ExcelWSheet = Constants.ExcelWBook.getSheet(sheetid);
	 		sheetname = Constants.ExcelWSheet.getSheetName();
	 		lastrow = Constants.ExcelWSheet.getLastRowNum();
	 		lastcol = Constants.ExcelWSheet.getRow(startrowno).getPhysicalNumberOfCells();
	 		String val, val1=null;
	 		
	 		for(int i=startrowno; i<(lastrow-1); i++){
	 			
	 			val = Constants.ExcelWSheet.getRow(i).getCell(colno).getStringCellValue();
	 				
	 			for(int m=i+1; m<lastrow; m++){
	 				
	 					val1 = Constants.ExcelWSheet.getRow(m).getCell(colno).getStringCellValue();
	 					 
	 				}
	 				if(val.equals(val1)){
	 					Constants.flag=false;
	 					Log1.info("Duplicate instance name found for "+val);
	 				}	
	 				else{
	 					Constants.flag=true;
	 				}
	 			}
	 	}
	    
	 
	     @AfterTest
	     public static void logout(){
	    	 Log1.info("");
	    	 Log1.info("Test process complete");
	    	 endTime = System.currentTimeMillis();
	    	 Log1.info("Total time taken "+(Math.round(((endTime-startTime)*0.001)/60))+"min");
	    	// driver.close();
	    	
	     }

}
