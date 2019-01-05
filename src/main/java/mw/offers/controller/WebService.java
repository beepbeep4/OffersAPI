package mw.offers.controller;

import mw.offers.entity.Offer;
import mw.offers.response.OfferResponse;
import mw.offers.response.ResponseType;
import mw.offers.services.DAOService;
import mw.offers.util.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

/**
 * Spring RestController which defines all endpoints for the API:
 *
 *   /
 *   /offers/search
 *   /offers/create
 *   /offers/cancel
 */
@RestController
public class WebService
{
    /**
     * Default landing page endpoint.
     * @return The landing page message.
     */
    @RequestMapping("/")
    public String index()
    {
        Log.debug("ENDPOINT: /offers/search:");
        return "Welcome to the offers API!";
    }

    /**
     * Search endpoints. Allows for valid offers to be queried, in full or by offer id(s)
     * @param request The incoming request from the client.
     * @return The response returned to the client.
     */
    @RequestMapping(path="/offers/search", method=RequestMethod.GET)
    public ResponseEntity<?> searchOffers(HttpServletRequest request)
    {
        Log.debug("Endpoint: /offers/search:");

        OfferResponse response;

        // Retrieve GET parameters
        Map<String, String[]> parameters = request.getParameterMap();

        // Check if we are going to be searching by ID
        if (parameters.containsKey("id"))
        {
            try
            {
                // Find all the requested ids, removing duplicates
                long[] ids = Arrays.asList(parameters.get("id")).stream().
                        distinct().mapToLong(Long::parseLong).toArray();

                response = DAOService.getInstance().findOffersByIds(ids);
            }
            catch (NumberFormatException ex)
            {
                // One or more ids were not longs
                response = new OfferResponse(ResponseType.OFFER_MALFORMED);
            }
        }
        // Otherwise search normally
        else
        {
            response = DAOService.getInstance().findAllValidOffers();
        }

        ResponseType responseType = response.getResponseType();
        printResponse(response);

        // Return response to client
        return ResponseEntity.status(responseType.getHttpStatusCode())
                .body(response.getOffers() != null ? response.getOffers() : responseType.getMessage());
    }

    /**
     * Create endpoint - Allows for offers to be created.
     * @param offer The offer contained in the client's request body.
     * @return The response returned to the client.
     */
    @RequestMapping(value="/offers/create", method = RequestMethod.POST, consumes =  MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> createOffer(@RequestBody Offer offer)
    {
        Log.debug("Endpoint: /offers/create:");

        // Process the request
        OfferResponse offerResponse = DAOService.getInstance().addOffer(offer);
        printResponse(offerResponse);

        // Return response to client
        ResponseType responseType = offerResponse.getResponseType();
        return ResponseEntity.status(responseType.getHttpStatusCode()).body(responseType.getMessage());
    }

    /**
     * Cancellation endpoint - Allows for offers to be cancelled.
     * @param offerId The id of the offer to cancel, contained in the client's request body.
     * @return The response returned to the client.
     */
    @RequestMapping(path="/offers/cancel", method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody ResponseEntity<?> cancelOffer(@RequestBody long offerId)
    {
        Log.debug("Endpoint: /offers/cancel: (offerId = " + offerId + ")");

        // Process the request
        OfferResponse offerResponse = DAOService.getInstance().cancelOffer(offerId);
        printResponse(offerResponse);

        // Return response to client
        ResponseType responseType = offerResponse.getResponseType();
        return ResponseEntity.status(responseType.getHttpStatusCode()).body(responseType.getMessage());
    }

    /**
     * Utility method to print the response objects returned by the offer DAO.
     * @param response The response object to print.
     */
    private void printResponse(OfferResponse response)
    {
        Log.debug("\nResponse:");
        Log.debug("Type: " + response.getResponseType().name() + " (" + response.getResponseType().getHttpStatusCode() + ")");
        Log.debug("Message: " + response.getResponseType().getMessage() + "\n");
    }
}