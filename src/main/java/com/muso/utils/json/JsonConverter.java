package com.muso.utils.json;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public final class JsonConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonConverter.class);

    // declare empty private constructor to avoid instantiation
    private JsonConverter() {
    }

    /**
     * Convert a given object in a json string representation
     * 
     * @param obj
     * @return
     */
    public static String toJsonString(Object obj) {
        final Gson gson = new Gson();

        // convert java object to JSON format,
        // and returned as JSON formatted string
        final String json = gson.toJson(obj);

        return json;
    }

    /**
     * Convert a given object to json representation and write the json string
     * in the specified file
     * 
     * @param obj
     * @param filePath
     */
    public static void toJsonFile(Object obj, String filePath) {
        // write converted json data to a file named
        try {
            final FileWriter writer = new FileWriter(filePath);
            writer.write(toJsonString(obj));
            writer.close();
        } catch (IOException e) {
            LOGGER.error("IOException encountered.", e);
        }
    }

    /**
     * Construct an object from the given json string representation of the
     * object
     * 
     * @param jsonString
     * @param objectClass
     * @return
     */
    public static <T> Object fromJsonString(String jsonString, Class<T> objectClass) {
        final Gson gson = new Gson();
        T obj = null;

        try {
            // convert the json string back to object
            obj = gson.fromJson(jsonString, objectClass);
        } catch (Exception e) {
            LOGGER.error("Exception encountered.", e);
        }

        return obj;
    }

    /**
     * Construct an object from the given json string representation of the
     * object
     * 
     * @param jsonString
     * @param objectClass
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> Object[] fromJsonStringArray(String jsonString, Class<T> objectClass) {
        final Class<? extends Object> arrayType = Array.newInstance(objectClass, 0).getClass();

        final Gson gson = new Gson();
        final T[] objects = (T[]) gson.fromJson(jsonString, arrayType);

        return objects;
    }

    /**
     * Construct an object from the given file containing a valid json
     * representation of the object
     * 
     * @param jsonFilePath
     * @param objectClass
     * @return
     */
    public static <T> Object fromJsonFile(String jsonFilePath, Class<T> objectClass) {
        final Gson gson = new Gson();
        T obj = null;

        LOGGER.trace("Json file path: {}", jsonFilePath);

        try {
            final InputStream jsonInputStream = JsonConverter.class.getResourceAsStream(jsonFilePath);
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(jsonInputStream, "UTF-8"));

            // convert the json string back to object
            obj = gson.fromJson(bufferedReader, objectClass);
        } catch (Exception e) {
            LOGGER.error("Error when importing the file", e);
        }

        Assert.assertNotNull("Error while trying to read data from file:'" + jsonFilePath + "' and convert it to:'" + objectClass.getCanonicalName()
                + "' object type", obj);
        return obj;
    }
}
