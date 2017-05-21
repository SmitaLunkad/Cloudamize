package Utility;

import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.openqa.selenium.WebDriver;


public class Constants {
	
	public static WebDriver driver =null;
	public static XSSFSheet ExcelWSheet;
    public static XSSFWorkbook ExcelWBook;
    public static XSSFCell Cell;
    public static XSSFRow Row;
    public static String sheetname;
    public static int lastrow;
    public static int lastcol;
    public static FileInputStream ExcelFile;
    public static String file_location = "D:\\Cloudamize\\ProjectFiles";
    public static String chrome_exe = "D:\\Cloudamize\\Jars\\chromedriver.exe";
    public static String isprovider;
    public static long startTime;
	public static long endTime;
    
    //Dashboard CSV Constants
    public static String db_sheet1 = "Summary";
    public static String db_sheet2 = "Compute";
    public static String db_sheet3 = "Storage";
    public static String db_sheet4 = "Network";
    public static int db_sumcompcostcol = 9;
    public static int db_compcostcol = 24;
    public static int db_compcostcol_azure = 29;
    public static int db_compcostcol_google = 23;
    public static int db_sumstoragecostcol = 10;
    public static int db_storagecostcol = 6;
    public static int db_storagecostcol_google = 10;
    public static int db_sumnwcostcol = 11;
    public static int db_nwcostcol = 7;
    public static int db_sumtotcostcol = 8;
    public static int db_compcomponent = 2;
    public static int db_sumcomponent = 2;
    public static String db_path = "D:\\Cloudamize\\ProjectFiles\\mapping.xlsx";
    
    //Migration Planner Constants
    public static String mPlanner_path = "D:\\Cloudamize\\ProjectFiles\\";
    public static String mPlanner_sheet1 = "Summary";
    public static String mPlanner_sheet2 = "Infrastructure Summary";
    public static String mPlanner_sheet3 = "Compute";
    public static String mPlanner_sheet4 = "Storage";
    public static String mPlanner_sheet5 = "Network";
    public static String mSettings_sheet6 = "Migration Settings";
    public static int mp_nodescol = 2;
    public static int mp_totalcostcol = 3;
    public static int mp_computecostcol = 4;
    public static int mp_storagecostcol = 5;
    public static int mp_nwcostcol = 6;
    public static int infsum_totalcostcol = 8;
    public static int infsum_computecostcol = 9;
    public static int infsum_storagecostcol = 10;
    public static int infsum_nwcostcol = 11;
    public static int infsum_component = 2;
    public static int comp_compcostcol_aws = 25;
    public static int comp_compcostcol_azure = 30;
    public static int comp_compcostcol_google = 24;
    public static int st_storagecostcol = 6;
    public static int st_storagecostcol_google = 10;
    public static int nw_networkcostcol = 7;
    
    
    //Test status flags
    public static boolean flag;
    public static boolean success = true;
    public static String TestCaseId;
    public static boolean mpPageStatus;

    
   
    
}
