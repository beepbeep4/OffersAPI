package mw.offers.entity;

import mw.offers.persistence.JSONParser;
import mw.offers.response.OfferResponse;
import mw.offers.response.ResponseType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DAO implementation which is responsible for data retrieval.
 */
public class OfferDaoImpl implements OfferDAO
{
    /**
     * The file location of offers.json
     */
    private String offerJson = System.getProperty("user.dir") + "\\resources\\offers.json";

    /**
     * Retrieves all offers known to the system, regardless of validity.
     * @return The offer response object.
     */
    @Override
    public OfferResponse findAllOffers()
    {
        try {
            List<Offer> results = (List<Offer>) new JSONParser().readFromFile(offerJson, Offer.class);

            if (results.size() == 0)
            {
                return new OfferResponse(ResponseType.NO_OFFERS);
            }

            // Return the offers found
            return new OfferResponse(ResponseType.OK, results);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        // Error case
        return new OfferResponse(ResponseType.ERROR);
    }

    /**
     * Retrieves all valid offers known to the system
     * @return The offer response object.
     */
    @Override
    public OfferResponse findAllValidOffers()
    {
        OfferResponse response = findAllOffers();

        // If find all offers returned an error, we want to propagate that back up
        if (response.getResponseType() != ResponseType.OK)
        {
            return response;
        }

        // Collect all valid offers
        List<Offer> validOffers = response.getOffers().stream()
          .filter(o -> !o.getCancelled())
            .filter(o -> o.getExpiryDate().before(Calendar.getInstance().getTime()))
              .collect(Collectors.toCollection(ArrayList::new));

        // And return them
        return new OfferResponse(ResponseType.OK, validOffers);
    }

    /**
     * Retrieves an offer by offer id.
     * @param id The offer id.
     * @return The offer response object.
     */
    @Override
    public OfferResponse getOfferById(long id)
    {
        OfferResponse response = findAllOffers();

        // If find all offers returned an error, we want to propagate that back up
        if (response.getResponseType() != ResponseType.OK)
        {
            return response;
        }

        for (Offer offer : response.getOffers())
        {
            // A single offer id search will return more information than normal
            if (offer.getOfferId() == id)
            {
                // Such as whether it has been cancelled
                if (offer.getCancelled())
                {
                    return new OfferResponse(ResponseType.OFFER_ALREADY_CANCELLED);
                }
                // Or whether it has expired
                if (offer.getExpiryDate().after(Calendar.getInstance().getTime()))
                {
                    return new OfferResponse(ResponseType.OFFER_EXPIRED);
                }

                return new OfferResponse(ResponseType.OK, offer);
            }
        }

        return new OfferResponse(ResponseType.OFFER_NOT_FOUND);
    }

    /**
     * Retrieves one or more offer(s) by id(s).
     * @param ids The offer id(s).
     * @return The offer response object.
     */
    @Override
    public OfferResponse getOffersById(long... ids)
    {
        List<Offer> matchingOffers = new ArrayList<>();

        OfferResponse response = findAllOffers();

        // If find all offers returned an error, we want to propagate that back up
        if (response.getResponseType() != ResponseType.OK)
        {
            return response;
        }

        // Find all offers matching the ID
        for (int i=0; i<ids.length; i++)
        {
            for (Offer offer : response.getOffers())
            {
                if (offer.getOfferId() == ids[i])
                {
                    matchingOffers.add(offer);
                }
            }
        }

        // Return all matching offers
        return new OfferResponse(ResponseType.OK, matchingOffers);
    }

    /**
     * Adds an offer to the system.
     * @param offer The offer to add.
     * @return The offer response object.
     */
    @Override
    public OfferResponse addOffer(Offer offer)
    {
        // An offer must have all fields populated
        if (offer.getExpiryDate() == null || offer.getOfferId() == null || offer.getPrice() == null
            || offer.getDescription() == null)
        {
            return new OfferResponse(ResponseType.OFFER_MALFORMED);
        }

        List<Offer> offers = findAllOffers().getOffers();

        // Check if the offer ID already exists
        if (offers != null && offers.stream().filter(o -> o.getOfferId() == offer.getOfferId()).count() > 0)
        {
            return new OfferResponse(ResponseType.OFFER_ID_EXISTS);
        }

        offers.add(offer);

        // Return the result from the offer write attempt
        return writeOffers(offers);
    }

    /**
     * Sets an offer as cancelled.
     * @param offerId The offer to cancel.
     * @return The offer response object.
     */
    @Override
    public OfferResponse cancelOffer(long offerId)
    {
        OfferResponse response = findAllOffers();

        // If find all offers returned an error, we want to propagate that back up
        if (response.getResponseType() != ResponseType.OK)
        {
            return response;
        }

        // Attempt to locate the offer to cancel
        boolean found = false;
        for (Offer offer : response.getOffers())
        {
            if (offer.getOfferId() == offerId)
            {
                // Check it wasn't already cancelled
                if (offer.getCancelled())
                {
                    return new OfferResponse(ResponseType.OFFER_ALREADY_CANCELLED);
                }

                // Otherwise cancel it
                offer.setCancelled(true);
                found = true;
            }
        }

        if (!found)
        {
            return new OfferResponse(ResponseType.OFFER_ID_NOT_FOUND);
        }

        // Attempt to write the response back to disk
        if (writeOffers(response.getOffers()).getResponseType() == ResponseType.OFFER_ADDED)
        {
            return new OfferResponse(ResponseType.OFFER_CANCELLED);
        }
        else
        {
            return new OfferResponse(ResponseType.FILE_WRITE_ERROR);
        }
    }

    /**
     * Writes offers to disk
     * @param offers The offers to write
     * @return The offer response.
     */
    private OfferResponse writeOffers(List<Offer> offers)
    {
        try
        {
            new JSONParser().write(offerJson, offers);
            return new OfferResponse(ResponseType.OFFER_ADDED);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            return new OfferResponse(ResponseType.FILE_WRITE_ERROR);
        }
    }
}
