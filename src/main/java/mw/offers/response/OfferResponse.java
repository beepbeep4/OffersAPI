package mw.offers.response;

import mw.offers.entity.Offer;
import mw.offers.response.containers.DataContainer;
import mw.offers.response.containers.LinkContainer;

import java.util.List;

/**
 * Response object which is evaluated by the web service.
 * Defines a data section, link section and series of response attributes.
 */
public class OfferResponse
{
    /**
     * The response type enum
     */
    private ResponseType responseType;

    /**
     * The HTTP status code
     */
    private int responseCode;

    /**
     * The custom response message
     */
    private String responseMessage;

    /**
     * Data section
     */
    private DataContainer data = new DataContainer();

    /**
     * Links section
     */
    private LinkContainer links = new LinkContainer();

    /**
     * Blank constructor - required for deserialization
     */
    public OfferResponse()
    { }

    /**
     * Sets the response type contained within the container
     * @param responseType The response type to set
     */
    public void setResponseType(ResponseType responseType)
    {
        this.responseType = responseType;
    }

    /**
     * Response type only constructor, usually describing a failure case
     * @param responseType The response type to set
     */
    public OfferResponse(ResponseType responseType)
    {
        setResponseType(responseType);
    }

    /**
     * Constructor to add a response and list of offers to the data container
     * @param responseType The response type to add
     * @param offers The offers to add
     */
    public OfferResponse(ResponseType responseType, List<Offer> offers)
    {
        setResponseType(responseType);
        this.data.setOffers(offers);
    }

    /**
     * Constructor to add a response and single offer to the data container
     * @param responseType The response type to add
     * @param offer The offer to add
     */
    public OfferResponse(ResponseType responseType, Offer offer)
    {
        setResponseType(responseType);
        this.data.addOffer(offer);
    }

    /**
     * Retrieves the response type of the offer
     * @return The response type
     */
    public ResponseType getResponseType()
    {
        return responseType;
    }

    /**
     * Retrieves the links section
     * @return The links section
     */
    public LinkContainer getLinks()
    {
        return links;
    }

    /**
     * Retrieves the data sections
     * @return The data section
     */
    public DataContainer getData()
    {
        return data;
    }

    /**
     * Retrieves the HTTP status code associated with the response.
     * @return The HTTP status code.
     */
    public int getResponseCode()
    {
        return responseType.getHttpStatusCode();
    }

    /**
     * Retrieves the custom message associated with the response.
     * @return The response message;
     */
    public String getResponseMessage()
    {
        return responseType.getMessage();
    }
}
