package mw.offers.services;

import mw.offers.entity.Offer;
import mw.offers.entity.OfferDAO;
import mw.offers.entity.OfferDaoImpl;
import mw.offers.response.OfferResponse;

/**
 * Singleton class used to retrieve DAO objects. Potentially unnecessary at the moment, but
 * would certainly be required if more DAOs were added.
 */
public class DAOService
{
    /**
     * The singleton instance
     */
    private static DAOService DAOService;

    /**
     * Implementation of the OfferDAO
     */
    private OfferDAO offerDAO;

    /**
     * Retrieves the singleton instance.
     * @return The singleton instances.
     */
    public static DAOService getInstance()
    {
        if (DAOService == null)
        {
            DAOService = new DAOService();
        }

        return DAOService;
    }

    /**
     * Constructor, initialises the DAO(s)
     */
    public DAOService()
    {
        offerDAO = new OfferDaoImpl();
    }

    /**
     * Retrieves all valid offers known to the system
     * @return The offer response object.
     */
    public OfferResponse findAllValidOffers()
    {
        return offerDAO.findAllValidOffers();
    }

    /**
     * Retrieves an offer by offer id.
     * @param id The offer id.
     * @return The offer response object.
     */
    public OfferResponse findOffersById(long id)
    {
        return offerDAO.getOfferById(id);
    }

    /**
     * Retrieves one or more offer(s) by id(s).
     * @param ids The offer id(s).
     * @return The offer response object.
     */
    public OfferResponse findOffersByIds(long... ids)
    {
        // If only one ID is specified, we will call a DAO function that returns
        // more information than normal (i.e. if offer is expired or cancelled)
        if (ids.length == 1)
        {
            return offerDAO.getOfferById(ids[0]);
        }
        // Otherwise we will be performing a "bulk" search
        else
        {
            return offerDAO.getOffersById(ids);
        }
    }

    /**
     * Adds an offer to the system.
     * @param offer The offer to add.
     * @return The offer response object.
     */
    public OfferResponse addOffer(Offer offer)
    {
         return offerDAO.addOffer(offer);
    }

    /**
     * Sets an offer as cancelled.
     * @param offerId The offer to cancel.
     * @return The offer response object.
     */
    public OfferResponse cancelOffer(long offerId)
    {
        return offerDAO.cancelOffer(offerId);
    }
}
