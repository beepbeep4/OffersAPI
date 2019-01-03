# OffersAPI

A RESTful API, allowing for offers to be queried, created and cancelled. Offers are stored in JSON format. Uses Spring, Cucumber, Mockito and JUnit. Offers are persisted to **/resources/offers.json**. Run **Launcher** to start the web service. Run **TestRunner** to launch automated behaviour driven tests. 

*Please note that the automated tests reset their state after completion and do not use the same offers.JSON file referenced by the main web service.* 

An offer consists of an offer id, price, description, cancelled state and expiry date. E.g:
```
{
  "offerId" : 412,
  "price" : 9.99,
  "description" : "Huge discount!",
  "cancelled" : false
  "expiryDate" : 1546471980000
}
```
## Endpoints:

#### /offers/search (GET)
> Returns all offers which are active, i.e. not cancelled and before their expiry date.

#### /offers/create (POST)
> Creates an offer, based on offer JSON supplied in the request body. E.g.:
```
{
  "offerId" : 52,
  "price" : 23.0,
  "description" : "20% off!",
  "expiryDate" : 2019-01-30T23:33:00.000
}
```

#### /offers/cancel (POST)
> Cancels an offer, based on an offer Id supplied in the request body. E.g.:

```
53
```
