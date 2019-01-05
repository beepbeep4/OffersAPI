package mw.offers.entity;

import mw.offers.response.OfferResponse;

/**
 * Defines the standard operations that the offer entity should adhere to.
 */
public interface OfferDAO
{
    /**
     * Retrieves all valid offers known to the system
     * @return The offer response object.
     */
    OfferResponse findAllValidOffers();

    /**
     * Retrieves all offers known to the system, regardless of validity.
     * @return The offer response object.
     */
    OfferResponse findAllOffers();

    /**
     * Retrieves an offer by offer id.
     * @param id The offer id.
     * @return The offer response object.
     */
    OfferResponse getOfferById(long id);

    /**
     * Retrieves one or more offer(s) by id(s).
     * @param ids The offer id(s).
     * @return The offer response object.
     */
    OfferResponse getOffersById(long... ids);

    /**
     * Adds an offer to the system.
     * @param offer The offer to add.
     * @return The offer response object.
     */
    OfferResponse addOffer(Offer offer);

    /**
     * Sets an offer as cancelled.
     * @param offerId The offer to cancel.
     * @return The offer response object.
     */
    OfferResponse cancelOffer(long offerId);
}