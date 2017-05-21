package Utility;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

public class Login_PG_POF {
	
	WebDriver driver;
	 
	@FindBy(how = How.ID, using = "j_username")
	 
	public  WebElement userName;
	
	@FindBy(how = How.ID, using = "j_password")
	 
	public  WebElement password;	
	@FindBy(how = How.ID, using = "loginSubmit")
	 
	public  WebElement login;
	
	@FindBy(how = How.ID, using = "showAwsDetailButton")
	 
	public  WebElement aws;

 
	public Login_PG_POF(WebDriver driver){
 
		this.driver = driver;
 
		}
	
		@Test
		public  void login_cloudamize(){
			Login_PG_POF loginPage; 
			loginPage = PageFactory.initElements(driver, Login_PG_POF.class);
			System.setProperty("webdriver.chrome.driver","D:\\Cloudamize\\Jars\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("user-data-dir=C:\\Users\\Admin\\AppData\\Local\\Google\\Chrome\\User Data\\");
			loginPage.driver = new ChromeDriver(options);
		
			driver.manage().window().maximize();
			//driver.manage().window().setSize(new Dimension(1000,500));
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
				
		System.out.println("Entered Login");
		driver.get("https://qa.cloudamize.com/login.jsp");
		//driver.findElement(By.id("j_username")).sendKeys("gggggg");
		userName.sendKeys("deepak-demo@cloudamize.com");
		password.sendKeys("cc");
		login.click();
		
	}
	
	 
}
