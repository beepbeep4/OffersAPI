package steps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import mw.offers.response.ResponseType;
import org.junit.Assert;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Glue code for CancelOffer.feature
 */
public class CancelOffer extends StepDefinition
{
    /**
     * TEST DATA
     */
    float validOfferId = 1;
    float invalidOfferId = 99999;

    @When("^A user supplies a valid id to the offers/cancel endpoint$")
    public void a_user_supplies_a_valid_id_to_the_offers_cancel_endpoint() throws Throwable
    {
        response = simulateEndpoint("/offers/cancel", RequestMethod.POST, validOfferId + "");
    }

    @When("^A user supplies an invalid id to the offers/cancel endpoint$")
    public void a_user_supplies_a_invalid_id_to_the_offers_cancel_endpoint() throws Throwable
    {
        response = simulateEndpoint("/offers/cancel", RequestMethod.POST, invalidOfferId + "");
    }

    @When("^A user supplies no id to the offers/cancel endpoint$")
    public void a_user_supplies_no_id_to_the_offers_cancel_endpoint() throws Throwable
    {
        response = simulateEndpoint("/offers/cancel", RequestMethod.POST, "");
    }

    @Then("^The offer is successfully cancelled$")
    public void the_offer_is_successfully_cancelled() throws Throwable
    {
        Assert.assertEquals(ResponseType.OFFER_CANCELLED.getHttpStatusCode(), response.getStatus());

        resetOffers();
    }

    @Then("^An error is returned$")
    public void an_error_is_returned() throws Throwable
    {
        Assert.assertEquals(ResponseType.OFFER_ID_NOT_FOUND.getHttpStatusCode(), response.getStatus());
    }

}
