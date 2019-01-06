package mw.offers.response.containers;

/**
 * Represents the link object within a response. Stores link information.
 */
public class LinkContainer
{
    /**
     * Stores the self link URL
     */
    private String self = "";

    /**
     * Getter for the 'self' link
     * @return The self link
     */
    public String getSelf()
    {
        return self;
    }

    /**
     * Setter for the 'self' link
     * @param self The value to set
     */
    public void setSelf(String self)
    {
        this.self = self;
    }
}
