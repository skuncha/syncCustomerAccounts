package customerAccountsSync.jbehave;

import java.io.IOException;

import net.thucydides.core.annotations.Steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import customerAccountsSync.steps.EndUserSteps;

/**
 * @author srinivasa.kuncha
 *
 */
public class DefinitionSteps {

    @Steps
    EndUserSteps endUser;
   
    @Given("Sales rep is on the Salesforce home page")
    public void givenTheUserIsOnTheWikionaryHomePage() {
        endUser.is_the_home_page();
    }
    @When("Sales rep login to glue as Mail user by supplying $UserName and $Password")
    public void userSupplyLoginCredentials(String username, String password) {
    	endUser.supplyLoginCredientials(username, password);
    }

    @When("Sales rep search for customer account and place order using csv $file")
    public void readCustomerData(String file) throws IOException{
    	endUser.migrationDataFile(file);
    }
    @Then("Sales rep should see customer account with associated orders in glue")
    public void verifyOrderID(){
    	endUser.verfiyOrdersuccessfullyPlaced();
    }
}
