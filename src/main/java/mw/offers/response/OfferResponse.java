package mw.offers.response;

import mw.offers.entity.Offer;

import java.util.ArrayList;
import java.util.List;

/**
 * Response object which is evaluated by the web service.
 * Allows for a data payload (i.e. offers) to be combined with a HTTP response code defining
 * if the operation succeeded or not.
 */
public class OfferResponse
{
    /**
     * The response type
     */
    private ResponseType type;

    /**
     * The offers stored in the response
     */
    private List<Offer> offers;

    /**
     * Constructor - Multiple offers
     * @param type The response types
     * @param offers The offers to store
     */
    public OfferResponse(ResponseType type, List<Offer> offers)
    {
        this.type = type;
        this.offers = offers;
    }

    /**
     * Constructor - A single offer
     * @param type The response types
     * @param offer The offer to store
     */
    public OfferResponse(ResponseType type, Offer offer)
    {
        this.type = type;
        this.offers = new ArrayList<>();
        offers.add(offer);
    }

    /**
     * Constructor - No offers
     * @param type The response types
     */
    public OfferResponse(ResponseType type)
    {
        this.type = type;
    }

    /**
     * Retrieves the response type of the offer
     * @return The response type
     */
    public ResponseType getResponseType()
    {
        return type;
    }

    /**
     * Retrieves the offers stored in the response
     * @return The offers, or null
     */
    public List<Offer> getOffers()
    {
        return offers;
    }
}
