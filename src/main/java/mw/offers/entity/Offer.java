package mw.offers.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Data object class to represent an offer.
 */
public class Offer
{
    /**
     * Blank constructor - required for JSON deserialization.
     */
    public Offer() { }

    /**
     * Constructor - Creates an offer in its entirety.
     * @param offerId The id of the offer.
     * @param price The price of the good on offer.
     * @param description The offer description.
     * @param expiryDate The expiry date of the offer.
     */
    public Offer(long offerId, float price, String description, Date expiryDate)
    {
        this.offerId = offerId;
        this.price = price;
        this.description = description;
        this.expiryDate = expiryDate;
        this.cancelled = false;
    }

    /**
     * Holds the id of the offer.
     */
    @JsonProperty("offerId")
    private Long offerId;

    /**
     * Holds the price of the good on offer.
     */
    @JsonProperty("price")
    private Float price;

    /**
     * Holds the merchant's description of the offer.
     */
    @JsonProperty("description")
    private String description;

    /**
     * Holds the cancelled state for the offer.
     */
    @JsonProperty("cancelled")
    private Boolean cancelled = false;

    /**
     * Holds the expiry date of the offer in the following format: yyyy-MM-ddTHH24:MM:SS.sss
     */
    @JsonProperty("expiryDate")
    private Date expiryDate;

    /**
     * Getter for the offer id.
     * @return The id of the offer.
     */
    public Long getOfferId()
    {
        return offerId;
    }

    /**
     * Setter for the offer id.
     * @param offerId The id of the offer.
     */
    public void setOfferId(long offerId)
    {
        this.offerId = offerId;
    }

    /**
     * Getter for the offer price.
     * @return The offer price.
     */
    public Float getPrice()
    {
        return price;
    }

    /**
     * Setter for the good id.
     * @param price The id of the good to set on offer.
     */
    public void setPrice(Float price)
    {
        this.price = price;
    }

    /**
     * Getter for the description.
     * @return The description of the offer.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Setter for the description.
     * @param description The desired description of the offer.
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Getter for the expiry date.
     * @return The expiry date of the offer.
     */
    public Date getExpiryDate()
    {
        return expiryDate;
    }

    /**
     * Setter for the expiry date.
     * @param expiryDate The desired expiry date of the offer.
     */
    public void setExpiryDate(Date expiryDate)
    {
        this.expiryDate = expiryDate;
    }

    /**
     * Getter for the offer's cancelled state.
     * @return True if the offer has been marked as cancelled.
     */
    public Boolean getCancelled()
    {
        return cancelled;
    }

    /**
     * Setter for the offer's cancelled state.
     * @param cancelled True to set the offer has cancelled, otherwise false.
     */
    public void setCancelled(Boolean cancelled)
    {
        this.cancelled = cancelled;
    }
}