package com.muso.utils.json;

public class ObjectDataProvider {
    public <T> T getObjectFromDataFile(String filePath, Class<T> objectClass) {
        final Object object = JsonConverter.fromJsonFile(filePath, objectClass);
        return objectClass.cast(object);
    }
}
