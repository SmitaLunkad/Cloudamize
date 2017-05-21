package Utility;

import java.util.ArrayList;
import java.util.List;

import org.testng.CommandLineArgs;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.beust.jcommander.JCommander;
import org.testng.CommandLineArgs;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.CommandLineArgs;
import org.testng.TestNG;
import org.testng.collections.Lists;
import org.testng.xml.XmlClass;
public class MainTest {
		    public static void main(String[] args){
		    	TestListenerAdapter tla = new TestListenerAdapter();
		    	TestNG testng = new TestNG();
		    	List<String> suites = Lists.newArrayList();
		    	suites.add("D:\\EclipseWorkspace\\Cloudamize\\MigrationPlannerCSVReportsTest\\testng.xml");
		    	testng.setTestSuites(suites);
		    	testng.run();

		    }
}
