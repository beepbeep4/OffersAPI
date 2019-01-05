package steps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import mw.offers.response.ResponseType;
import org.junit.Assert;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Glue code for CreateOffer.feature
 */
public class CreateOffer extends StepDefinition
{
    /**
     * TEST DATA
     */
    String validOfferJson =
    " {" +
    "    \"offerId\" : 50,\n" +
    "    \"price\" : 3.99,\n" +
    "    \"description\" : \"20% off!\",\n" +
    "    \"expiryDate\" : \"2019-01-30T23:33:00.000\"\n" +
    " }";
    String noIdOfferJson =
    " {" +
    "    \"price\" : 3.99,\n" +
    "    \"description\" : \"20% off!\",\n" +
    "    \"expiryDate\" : \"2019-01-30T23:33:00.000\"\n" +
    " }";
    String malformedDateJson =
    " {" +
    "    \"offerId\" : 50,\n" +
    "    \"price\" : 3.99,\n" +
    "    \"description\" : \"20% off!\",\n" +
    "    \"expiryDate\" : \"2ab-01-30T23:33:00.000\"\n" +
    " }";
    String malformedJson =
    " {" +
    "    offerId\" : 50,\n" +
    "    \"price\"  3.99,\n" +
    "    \"description\" : 20% off!\",\n" +
    "    \"expiryDate\" : \"2ab-01-30T23:33:00.000\"\n" +
    " }";
    String typeMismatch =
    " {" +
    "    \"offerId\" : \"abc\",\n" +
    "    \"price\" : 3.99,\n" +
    "    \"description\" : \"20% off!\",\n" +
    "    \"expiryDate\" : \"2019-01-30T23:33:00.000\"\n" +
    " }";

    @When("^A user supplies a valid body to the offers/create endpoint$")
    public void a_user_supplies_a_valid_body_to_the_offers_create_endpoint() throws Throwable
    {
       response = simulateEndpoint("/offers/create", RequestMethod.POST, validOfferJson);
    }

    @When("^A user supplies a request with no id to the offers/create endpoint$")
    public void a_user_supplies_a_request_with_no_id_to_the_create_endpoint() throws Throwable
    {
        response = simulateEndpoint("/offers/create", RequestMethod.POST, noIdOfferJson);
    }

    @When("^A user supplies a request with a malformed date to the offers/create endpoint$")
    public void a_user_supplies_a_request_with_a_malformed_date_to_the_create_endpoint() throws Throwable
    {
        response = simulateEndpoint("/offers/create", RequestMethod.POST, malformedDateJson);
    }

    @When("^A user supplies a request with a mismatched type to the offers/create endpoint$")
    public void a_user_supplies_a_request_with_a_mismatched_type_to_the_create_endpoint() throws Throwable
    {
        response = simulateEndpoint("/offers/create", RequestMethod.POST, typeMismatch);
    }

    @When("^A user supplies a request with malformed json to the offers/create endpoint$")
    public void a_user_supplies_a_request_with_a_malformed_json_to_the_create_endpoint() throws Throwable
    {
        response = simulateEndpoint("/offers/create", RequestMethod.POST, malformedJson);
    }

    @When("^A user supplies an empty request to the offers/create endpoint$")
    public void a_user_supplies_an_empty_request_to_the_create_endpoint() throws Throwable
    {
        response = simulateEndpoint("/offers/create", RequestMethod.POST, null);
    }

    @Then("^The offer is successfully created$")
    public void the_offer_is_successfully_created() throws Throwable
    {
        Assert.assertEquals(ResponseType.OFFER_ADDED.getHttpStatusCode(), response.getStatus());

        resetOffers();
    }

    @Then("^The request is rejected$")
    public void the_request_is_rejected() throws Throwable
    {
        Assert.assertEquals(ResponseType.OFFER_MALFORMED.getHttpStatusCode(), response.getStatus());
    }
}
