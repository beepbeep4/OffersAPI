package steps;

import javafx.util.Pair;
import mw.offers.controller.WebService;
import mw.offers.util.Log;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Defines common behaviour found in a step definition.
 */
public abstract class StepDefinition
{
    /**
     * Response object used by child tests.
     */
    protected MockHttpServletResponse response;

    /**
     * Allows for the offers JSON to be reset to a known "clean" state, ensuring that tests are repeatable.
     * @throws IOException Thrown upon file readFromFile failure.
     */
    protected void resetOffers() throws IOException
    {
        Log.debug("Resetting offers...");

        FileChannel source = null;
        FileChannel destination = null;

        try {

            File cleanOfferJson = new File(System.getProperty("user.dir") + "\\resources\\clean_offers.json");
            File dirtyOfferJson = new File(System.getProperty("user.dir") + "\\resources\\offers.json");

            // Copy the clean file into the current test JSON
            source = new FileInputStream(cleanOfferJson).getChannel();
            destination = new FileOutputStream(dirtyOfferJson).getChannel();
            destination.transferFrom(source, 0, source.size());

            Log.debug("Offers reset.");
        }
        finally
        {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }

    /**
     * Allows a test to simulate a GET or POST call to an endpoint
     * @param endPoint The endpoint location, e.g. /offers/create
     * @param method GET or POST
     * @param content The content body
     * @return The response
     * @throws Exception Thrown if the mocking framework encounters an error.
     */
    protected MockHttpServletResponse simulateEndpoint(String endPoint, RequestMethod method,
           String content, Pair<String,String>... params) throws Exception
    {
        RequestBuilder requestBuilder;
        MockMvc mockMvc;

        // Protect against null content
        content = content == null ? "" : content;

        if (method == RequestMethod.POST)
        {
            requestBuilder = MockMvcRequestBuilders.post(endPoint).accept(MediaType.APPLICATION_JSON)
              .content(content).contentType(MediaType.APPLICATION_JSON);
        }
        else if (method == RequestMethod.GET)
        {
            requestBuilder = MockMvcRequestBuilders.get(endPoint).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

            // Evaluate GET parameters
            for (Pair<String,String> kvPair : params)
            {
                ((MockHttpServletRequestBuilder) requestBuilder).param(kvPair.getKey(), kvPair.getValue());
            }
        }
        else
        {
            Log.error("Unhandled request method: " + method.name());
            return null;
        }

        // Create and return the mock response
        mockMvc = MockMvcBuilders.standaloneSetup(new WebService()).build();
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        return response;
    }
}
