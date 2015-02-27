package customerAccountsSync.pages;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import net.thucydides.core.Thucydides;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.annotations.findby.FindBy;
import net.thucydides.core.csv.CSVTestDataSource;
import net.thucydides.core.pages.PageObject;
import net.thucydides.core.pages.WebElementFacade;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.DoubleClickAction;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.thoughtworks.selenium.webdriven.commands.DoubleClick;

/**
 * @author srinivasa.kuncha
 */
@DefaultUrl("https://test.salesforce.com/")
public class GlueAccountPage extends PageObject {
	
	long timeNow = System.currentTimeMillis();
	String url = "https://dmgsalescloud--prodmirror.cs7.my.salesforce.com/";
	
	String order,rowNum;
	String billingType = "Agency";
	static String CCIAccountExist = "Problem creating CCI customer:-This account already exists in CCI";
	static String rcs;
    @FindBy(xpath="//div/input")
    private WebElementFacade searchTerms;

    @FindBy(id="phSearchButton")
    private WebElementFacade lookupButton;

    private WebElementFacade username() 			{ return element(By.id("username"));															}
	private WebElementFacade password() 			{ return element(By.id("password"));															}
	private WebElementFacade loginbutton() 			{ return element(By.id("Login"));																}
	private WebElementFacade CCICustomerMail()      { return element(By.cssSelector("input[value='Create CCI Customer-Mail']"));					}
//	private WebElementFacade create_CCIaccount() 	{ return element(By.name("create_cciaccount_mail"));											}
	private WebElementFacade DoCCIint()      		{ return element(By.xpath("//*[@id='00ND0000005jgg8_chkbox']"));}
	private WebElementFacade DoCCIint1()      		{ return element(By.cssSelector("#00ND0000005jgg8_ilecell"));}
	private WebElementFacade Save()      			{ return element(By.xpath("//*[@id='topButtonRow']/input[1]"));									}
	private WebElementFacade DoIntcheckbox()		{ return element(By.id("00ND0000005jgg8"));	}

	//*[@id="topButtonRow"]/input[1]
	
	public void supplyLogin_Credientials(String username, String password) {
		waitFor(3).seconds();
		getDriver().manage().window().maximize();
		username().type(username);
		password().type(password);
		waitFor(1).second();
	}

	public void clickOnLogin() {
		
		loginbutton().click();
		waitFor(3).seconds();
	}

public void CCIMailIntegration(String num) {
    	
    	CCICustomerMail().click();
    	System.out.print("                    " + num);
    		waitFor(3).seconds();
    	getDriver().switchTo().alert().accept(); 
    	waitFor(4).seconds();
    	if (getDriver().switchTo().alert().getText().equals(CCIAccountExist)){
    		System.out.println("   :  " +CCIAccountExist);
    	}
    	else{
			waitFor(12).seconds();
		System.out.println("   :  " +getDriver().switchTo().alert().getText());
		getDriver().switchTo().alert().accept();
    	}
			waitFor(5).seconds();
    }
	public void readfile(String fileloc) throws IOException {

		File filePath = new File(fileloc);
		if (filePath.isFile()) {
			System.out.println("\n");
			System.out.println("         PLEASE SEE STATUS OF CUSTOMER ACCOUNTS SYNC DETAILS ");
			System.out.println("\n");
			String file = filePath.getAbsolutePath();
			CSVTestDataSource testDataSrc = new CSVTestDataSource(file);
			waitFor(5).seconds();
			for (Map<String, String> record : testDataSrc.getData()) {
				try {
					rowNum = record.get("recordNo");
					String clinetuniqueID = record.get("uniqueID");
					String clientURL = url.concat(clinetuniqueID);
					getDriver().get(clientURL);
					waitFor(8).seconds();
					Thucydides.takeScreenshot();
//					CCIMailIntegration(rowNum);
					DoCCIint().click();
					waitFor(2).seconds();
					Actions act = new Actions(getDriver());
					act.doubleClick(getDriver().findElement(By.xpath("//*[@id='00ND0000005jgg8_chkbox']"))).build().perform();
					waitFor(1).seconds();
					DoIntcheckbox().click();
					waitFor(1).seconds();
					Save().click();
					System.out.println("                       "+rowNum + " . CCI Sync Success");
					waitFor(6).seconds();
				} catch (Exception e) {
					System.out.println("                       " + rowNum +"   : ---> SORRY, Please try again (Latency Issue)");
				}
				try {
			    	 WebDriverWait wait1 = new WebDriverWait(getDriver(), 3);
			    	 if(wait1.until(ExpectedConditions.alertIsPresent())!=null)
			    	      getDriver().switchTo().alert().accept();
			    	 }
			    	 catch (Exception x) {}
			}
		}
	}
}