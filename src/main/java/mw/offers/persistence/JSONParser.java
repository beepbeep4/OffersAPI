package mw.offers.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import mw.offers.util.Log;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for JSON read/write operations.
 */
public class JSONParser
{
    /**
     * Jackson mapper instance.
     */
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Deserializes a JSON structure into an array of objects.
     * @param filePath The path of the JSON file.
     * @return The resulting object list.
     * @throws IOException Thrown if the readFromFile operation fails.
     */
    public List<?> readFromFile(String filePath, Class<?> clazz) throws IOException
    {
        File file = new File(filePath);

        checkFileExists(file);

        CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
        List<?> results = mapper.readValue(file, listType);

        return results;
    }

    /**
     * Checks the file to be written to / read from exists, creating it if required.
     * @param file The file to write to / read from.
     * @throws IOException Thrown if the file write fails.
     */
    private void checkFileExists(File file) throws IOException
    {
        if (file.createNewFile())
        {
            byte[] strToBytes = "[]".getBytes();
            Files.write(Paths.get(file.getPath()), strToBytes);
            Log.warning("Offers JSON did not exist and was recreated.");
        }
    }

    /**
     * Deserializes a JSON structure into an array of objects.
     * @param json The JSON string
     * @return The resulting object list.
     * @throws IOException Thrown if the readFromFile operation fails.
     */
    public Object readFromString(String json, Class<?> clazz) throws IOException
    {
        Object result = mapper.readValue(json, clazz);

        return result;
    }

    /**
     * Writes a list of objects to a JSON file.
     * @param filePath The JSON file to write to.
     * @param objects The objects to write.
     * @throws IOException Thrown if the write operation fails.
     */
    public void write(String filePath, List<?> objects) throws IOException
    {
        File file = new File(filePath);
        checkFileExists(file);

        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(file, objects);
    }
}
