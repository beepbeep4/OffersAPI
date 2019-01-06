package steps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import javafx.util.Pair;
import mw.offers.entity.Offer;
import mw.offers.persistence.JSONParser;
import mw.offers.response.OfferResponse;
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

    boolean singleIdSearch = false;
    long validOrderId = 3;
    long cancelledOrderId = 4;
    long expiredOrderId = 5;
    String malformedId = "ab";

    @When("^A user navigates to the offers/search endpoint without parameters$")
    public void a_user_navigates_to_the_offers_search_endpoint_without_parameters() throws Throwable
    {
        response = simulateEndpoint("/offers/search", RequestMethod.GET, "");
    }

    @When("^A user navigates to the offers/search endpoint with valid parameters$")
    public void a_user_navigates_to_the_offers_search_endpoint_with_valid_parameters() throws Throwable
    {
        singleIdSearch = true;
        Pair<String,String> params = new Pair<>("id", validOrderId + "");
        response = simulateEndpoint("/offers/search", RequestMethod.GET,"", params);
    }

    @When("^A user navigates to the offers/search endpoint with malformed parameters$")
    public void a_user_navigates_to_the_offers_search_endpoint_with_malformed_parameters() throws Throwable
    {
        Pair<String,String> params = new Pair<>("id", malformedId + "");
        response = simulateEndpoint("/offers/search", RequestMethod.GET,"", params);
    }

    @When("^A user navigates to the offers/search endpoint with an expired id$")
    public void a_user_navigates_to_the_offers_search_endpoint_with_an_expired_id() throws Throwable
    {
        resetOffers();
        Pair<String,String> params = new Pair<>("id", expiredOrderId + "");
        response = simulateEndpoint("/offers/search", RequestMethod.GET,"", params);
    }

    @When("^A user navigates to the offers/search endpoint with a cancelled id$")
    public void a_user_navigates_to_the_offers_search_endpoint_with_a_cancelled_id() throws Throwable
    {
        resetOffers();
        Pair<String,String> params = new Pair<>("id", cancelledOrderId + "");
        response = simulateEndpoint("/offers/search", RequestMethod.GET,"", params);
    }

    @Then("^The search returns an error$")
    public void the_search_returns_an_error() throws Throwable
    {
        Assert.assertEquals(ResponseType.OFFER_MALFORMED.getHttpStatusCode(), response.getStatus());
    }

    @Then("^The user is warned that the offer has expired$")
    public void the_user_is_warned_that_the_offer_has_expired() throws Throwable
    {
        String responseText = response.getContentAsString();
        OfferResponse offerResponse = (OfferResponse) new JSONParser().readFromString(responseText, OfferResponse.class);

        // Check that the response was as expected
        Assert.assertTrue(offerResponse.getResponseType().getMessage().equals(ResponseType.OFFER_EXPIRED.getMessage()));
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Then("^The user is warned that the offer is cancelled$")
    public void the_user_is_warned_that_the_offer_is_cancelled() throws Throwable
    {
        String responseText = response.getContentAsString();
        OfferResponse offerResponse = (OfferResponse) new JSONParser().readFromString(responseText, OfferResponse.class);

        // Check that the response was as expected
        Assert.assertTrue(offerResponse.getResponseType().getMessage().equals(ResponseType.OFFER_ALREADY_CANCELLED.getMessage()));
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Then("^All valid offers are returned$")
    public void all_valid_offers_are_returned() throws Throwable
    {
        String responseText = response.getContentAsString();

        OfferResponse response = (OfferResponse) new JSONParser().readFromString(responseText, OfferResponse.class);
        List<Offer> offers = response.getData().getOffers();

        // Ensure offers are actually valid
        for (Offer offer : offers)
        {
            // Id search check
            if (singleIdSearch)
            {
                Assert.assertTrue(offer.getOfferId() == validOrderId);
                singleIdSearch = false;
            }

            // Not cancelled
            Assert.assertEquals(offer.getCancelled(), false);
            // And within date
            Assert.assertEquals(offer.getExpiryDate().after(Calendar.getInstance().getTime()), false);
        }

        Assert.assertEquals(HttpStatus.OK.value(), response.getResponseType().getHttpStatusCode());
    }
}
