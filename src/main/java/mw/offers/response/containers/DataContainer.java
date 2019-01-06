package mw.offers.response.containers;

import mw.offers.entity.Offer;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the data object within a response. Stores offer information.
 */
public class DataContainer
{
    /**
     * The offers stored in the response
     */
    private List<Offer> offers = new ArrayList<>();

    /**
     * Blank constructor - Automatically created by an offer response
     */
    public DataContainer()
    { }

    /**
     * Adds an offer to the container
     * @param offer The offer to add
     */
    public void addOffer(Offer offer)
    {
        this.offers.add(offer);
    }

    /**
     * Retrieves all container's offers
     * @return All offers within the container
     */
    public List<Offer> getOffers()
    {
        return offers;
    }

    /**
     * Sets all the container's offers
     * @param offers The offers to set
     */
    public void setOffers(List<Offer> offers)
    {
        this.offers = offers;
    }


}
