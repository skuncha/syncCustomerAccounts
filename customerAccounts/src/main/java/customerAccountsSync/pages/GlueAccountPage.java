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

/**
 * @author srinivasa.kuncha
 */
@DefaultUrl("https://eu1.salesforce.com/")
public class GlueAccountPage extends PageObject {
	
	long timeNow = System.currentTimeMillis();
	String url = "https://eu1.salesforce.com/";
	
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

	public void CCIMailIntegration() {
    	
    	CCICustomerMail().click();
    		waitFor(4).seconds();
    	getDriver().switchTo().alert().accept(); 
			waitFor(16).seconds();
		System.out.println("   :  " +getDriver().switchTo().alert().getText());
		getDriver().switchTo().alert().accept();
			waitFor(3).seconds();
    }
	public void readfile(String fileloc) throws IOException {

		File filePath = new File(fileloc);
		if (filePath.isFile()) {
			System.out.println("\n");
			System.out
					.println("         OREDRS THAT ARE SUCCESSFULLY CREATED ARE ");
			String file = filePath.getAbsolutePath();
			CSVTestDataSource testDataSrc = new CSVTestDataSource(file);
			for (Map<String, String> record : testDataSrc.getData()) {
				try {
					waitFor(3).seconds();
					rowNum = record.get("recordNo");
					String clinetuniqueID = record.get("uniqueID");
					String clientURL = url.concat(clinetuniqueID);
					getDriver().get(clientURL);
					waitFor(8).seconds();
					Thucydides.takeScreenshot();
					System.out.print("                       " + rowNum);
		/**********************************************************************************************/
					/*create_CCIaccount().click();
			    	waitFor(4).seconds();
					getDriver().switchTo().alert().accept();
					waitFor(6).seconds();*/
						
		/**********************************************************************************************/
					CCIMailIntegration();
				} catch (Exception e) {
					System.out.println("   :                 "
							+ "Sorry either Latency/Account Issue");
				}
			}
		}
	}
}