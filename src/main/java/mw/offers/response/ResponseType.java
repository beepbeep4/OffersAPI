package mw.offers.response;

import org.springframework.http.HttpStatus;

/**
 * Enum to describe the various response states of offers.
 * Contains the HTTP status code to return, as well as a message.
 */
public enum ResponseType
{
    /**
     *  POSITIVE
     */
    OK(HttpStatus.OK.value(), "Received"),
    OFFER_ADDED(HttpStatus.OK.value(), "Offer successfully created."),
    OFFER_CANCELLED(HttpStatus.OK.value(), "Offer successfully cancelled."),

    /**
     * NEGATIVE
     */
    ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occured while processing the request."),
    NO_OFFERS(HttpStatus.OK.value(), "There are currently no valid offers."),
    FILE_WRITE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Could not write to JSON file"),
    OFFER_ID_EXISTS(HttpStatus.BAD_REQUEST.value(), "An offer already exists with the supplied id"),
    OFFER_ID_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "The supplied offer id could not be found"),
    OFFER_ALREADY_CANCELLED(HttpStatus.BAD_REQUEST.value(), "The offer was already cancelled"),
    OFFER_MALFORMED(HttpStatus.BAD_REQUEST.value(), "The offer request could not be parsed."),
    OFFER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "The offer could not be found"),
    OFFER_EXPIRED(HttpStatus.BAD_REQUEST.value(), "This offer has already expired.");

    /**
     * The HTTP status code
     */
    private int httpStatusCode;

    /**
     * The message to add the response body.
     */
    private String message;

    /**
     * Constructor
     * @param httpStatusCode The HTTP status code
     * @param message The message to add the response body.
     */
    ResponseType(int httpStatusCode, String message)
    {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }

    /**
     * Returns the HTTP Status code
     * @return The associated status code
     */
    public int getHttpStatusCode()
    {
        return httpStatusCode;
    }

    /**
     * Returns the response body message.
     * @return The associated message.
     */
    public String getMessage()
    {
        return message;
    }
}
