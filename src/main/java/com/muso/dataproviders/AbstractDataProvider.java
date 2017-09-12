package com.muso.dataproviders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muso.utils.json.ObjectDataProvider;

/**
 * Process and build data from json files as object of the provided runtime type
 * T
 * 
 * 
 * @param <T>
 */
public class AbstractDataProvider<T> {
    protected ObjectDataProvider dataProvider;
    final private Class<T> dataObjectType;
    final private String jsonFilePath;

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDataProvider.class);

    /**
     * At runtime initialize this class with the path to the json data file and
     * the type of object to be constructed form this data file
     * 
     * @param jsonFilePath
     * @param dataObjectType
     */
    public AbstractDataProvider(String jsonFilePath, Class<T> dataObjectType) {
        dataProvider = new ObjectDataProvider();
        this.jsonFilePath = jsonFilePath;
        this.dataObjectType = dataObjectType;
    }

    /**
     * Returns a single object of the type provided at runtime
     * 
     * @return
     */
    public T getData() {
        return dataProvider.getObjectFromDataFile(jsonFilePath, dataObjectType);
    }

}
