!-- Objective is to migrate inflight Daily Mail Display Orders to CCI via glue order plugin

Meta:
@author Srinivasa Kuncha
@tags feature: DM Display Orders

Narrative:
In order to migrate inflight Daily Mail Display Orders
As a Sales Representative
I want to load all missing/separately record orders into CCI via glue 

Scenario: Place an order using existing customer account

Meta:
@author Srinivasa Kuncha
@tags feature: DM Display Orders

Given Sales rep is on the Salesfoce home page
When Sales rep login to glue as Mail user by supplying [username] and [password]
When Sales rep search for customer account and place order using csv [file]
Then Sales rep should see customer account with associated orders in glue

Examples:
|username											|password			|file											|
|bev.cheshire@mailnewspapers.co.uk					|Bev02051974		|src\main\resources\datamigration.csv			|