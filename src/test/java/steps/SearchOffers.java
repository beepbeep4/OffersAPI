package steps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import javafx.util.Pair;
import mw.offers.entity.Offer;
import mw.offers.persistence.JSONParser;
import mw.offers.response.ResponseType;
import org.junit.Assert;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Calendar;
import java.util.List;

/**
 * Glue code for SearchOffers.feature
 */
public class SearchOffers extends StepDefinition
{
    boolean idSearch = false;
    long searchId = 3;
    String malformedId = "ab";

    @When("^A user navigates to the offers/search endpoint without parameters$")
    public void a_user_navigates_to_the_offers_search_endpoint_without_parameters() throws Throwable
    {
        response = simulateEndpoint("/offers/search", RequestMethod.GET, "");
        idSearch = false;
    }

    @When("^A user navigates to the offers/search endpoint with valid parameters$")
    public void a_user_navigates_to_the_offers_search_endpoint_with_valid_parameters() throws Throwable
    {
        Pair<String,String> validId = new Pair<>("id", searchId + "");
        response = simulateEndpoint("/offers/search", RequestMethod.GET,"", validId);
        idSearch = true;
    }

    @When("^A user navigates to the offers/search endpoint with malformed parameters$")
    public void a_user_navigates_to_the_offers_search_endpoint_with_malformed_parameters() throws Throwable
    {
        Pair<String,String> validId = new Pair<>("id", malformedId + "");
        response = simulateEndpoint("/offers/search", RequestMethod.GET,"", validId);
        idSearch = true;
    }

    @Then("^The search returns an error$")
    public void the_search_returns_an_error() throws Throwable
    {
        Assert.assertEquals(ResponseType.OFFER_MALFORMED.getHttpStatusCode(), response.getStatus());
    }

    @Then("^All valid offers are returned$")
    public void all_valid_offers_are_returned() throws Throwable
    {
        String responseText = response.getContentAsString();

        List<Offer> offers = (List<Offer>) new JSONParser().readFromString(responseText, Offer.class);

        // Ensure offers are actually valid
        for (Offer offer : offers)
        {
            if (idSearch)
            {
                Assert.assertTrue(offer.getOfferId() == searchId);
            }

            // Not cancelled
            Assert.assertEquals(offer.getCancelled(), false);
            // And within date
            Assert.assertEquals(offer.getExpiryDate().after(Calendar.getInstance().getTime()), false);
        }

        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}
