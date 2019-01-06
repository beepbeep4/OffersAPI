# OffersAPI

A RESTful API, allowing for offers to be queried, created and cancelled. Uses Spring, Jackson, Cucumber and JUnit. Offers are persisted to **/resources/offers.json**. Two valid offers and one invalid offer are included by default. To run the program from a clean slate, simply delete offers.json.

Run **Launcher** to start the web service. Run **TestRunner** to launch automated behaviour driven tests. 

*Note that the automated tests reset their state after completion and do not use the same offers.JSON file as the main web service. Also note that if the test data contained within test/resources/clean_offers.json is edited, automated tests may fail.* 

An offer consists of an offer id, price, description, cancelled state and expiry date.

## Endpoints:

#### /offers/search (GET)
> Returns all offers, or offers matching the "id" parameter which are active, i.e. not cancelled and before their expiry date. E.g.:
```
localhost:8080/offers/search
```
```
localhost:8080/offers/search?id=2
```
```
localhost:8080/offers/search?id=2&id=6
```

A response will contain response information, a data section and a links section. E.g:

```
{
   "responseType":"OK",
   "responseCode":200,
   "responseMessage":"Received",
   "data":{
      "offers":[
         {
            "offerId":2,
            "price":41.0,
            "description":"Buy one get one free!",
            "cancelled":false,
            "expiryDate":1546471980000
         },
         {
            "offerId":3,
            "price":12.0,
            "description":"50% off!",
            "cancelled":false,
            "expiryDate":1544620260000
         }
      ]
   },
   "links":{
      "self":"/offers/search"
   }
}
```

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
*Note that an offer cannot be created as cancelled.*

#### /offers/cancel (POST)
> Cancels an offer, based on an offer Id supplied in the request body. E.g.:

```
53
```
