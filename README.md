# OffersAPI

A RESTful API, allowing for offers to be queried, created and cancelled. Offers are persisted to **/resources/offers.json**. Run **Launcher** to start the web service. Run **TestRunner** to launch automated behaviour driven tests. 

*Please note that the automated tests reset their state after completion and do not use the same offers.JSON file referenced by the main web service.* 

#### /offers/search (GET)
> Returns all offers which are active and not expired.

#### /offers/create (POST)
> Creates an offer, based on offer JSON supplied in the request body.
```
{
  "offerId" : 52,
  "price" : 23.0,
  "description" : "20% off!",
  "expiryDate" : 2019-01-30T23:33:00.000
}
```

#### /offers/cancel (POST)
> Cancels an offer.

```
{
  "offerId" : 53,
}
```
