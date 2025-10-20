package com.api.automation.payload;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

public class ComplexPayloadBuilder {

    private static Logger logger = Logger.getLogger(ComplexPayloadBuilder.class);
    private JsonObject rootObject;
    private Gson gson;

    public ComplexPayloadBuilder() {
        this.rootObject = new JsonObject();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        logger.debug("ComplexPayloadBuilder initialized");
    }

    public ComplexPayloadBuilder addString(String key, String value) {
        rootObject.addProperty(key, value);
        logger.debug("Added String - " + key + ": " + value);
        return this;
    }

    public ComplexPayloadBuilder addNumber(String key, Number value) {
        rootObject.addProperty(key, value);
        logger.debug("Added Number - " + key + ": " + value);
        return this;
    }

    public ComplexPayloadBuilder addBoolean(String key, Boolean value) {
        rootObject.addProperty(key, value);
        logger.debug("Added Boolean - " + key + ": " + value);
        return this;
    }

    public ComplexPayloadBuilder addNestedObject(String key, Map<String, Object> nestedData) {
        JsonObject nestedObj = new JsonObject();
        nestedData.forEach((k, v) -> {
            if (v instanceof String) nestedObj.addProperty(k, (String) v);
            else if (v instanceof Number) nestedObj.addProperty(k, (Number) v);
            else if (v instanceof Boolean) nestedObj.addProperty(k, (Boolean) v);
        });
        rootObject.add(key, nestedObj);
        logger.debug("Added Nested Object - " + key);
        return this;
    }

    public ComplexPayloadBuilder addStringArray(String key, List<String> values) {
        JsonArray jsonArray = new JsonArray();
        values.forEach(jsonArray::add);
        rootObject.add(key, jsonArray);
        logger.debug("Added String Array - " + key + ": " + values.size() + " items");
        return this;
    }

    public ComplexPayloadBuilder addObjectArray(String key, List<Map<String, Object>> arrayData) {
        JsonArray jsonArray = new JsonArray();
        arrayData.forEach(item -> {
            JsonObject itemObj = new JsonObject();
            item.forEach((k, v) -> {
                if (v instanceof String) itemObj.addProperty(k, (String) v);
                else if (v instanceof Number) itemObj.addProperty(k, (Number) v);
                else if (v instanceof Boolean) itemObj.addProperty(k, (Boolean) v);
            });
            jsonArray.add(itemObj);
        });
        rootObject.add(key, jsonArray);
        logger.debug("Added Object Array - " + key);
        return this;
    }

    public ComplexPayloadBuilder merge(ComplexPayloadBuilder other) {
        other.rootObject.entrySet().forEach(entry -> rootObject.add(entry.getKey(), entry.getValue()));
        logger.debug("Payloads merged");
        return this;
    }

    public String build() {
        String payload = gson.toJson(rootObject);
        logger.info("Payload built successfully");
        return payload;
    }

    public JsonObject buildAsJsonObject() {
        logger.info("Payload built as JsonObject");
        return rootObject;
    }

    public void clear() {
        rootObject = new JsonObject();
        logger.debug("Payload cleared");
    }

    public int getSize() {
        return rootObject.size();
    }
}
