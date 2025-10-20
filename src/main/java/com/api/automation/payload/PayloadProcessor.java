package com.api.automation.payload;

import com.google.gson.*;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

public class PayloadProcessor {

    private static Logger logger = Logger.getLogger(PayloadProcessor.class);

    public String processPayloadWithVariables(String payload, Map<String, String> variables) {
        String processedPayload = payload;
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            String key = "{{" + entry.getKey() + "}}";
            String value = entry.getValue();
            processedPayload = processedPayload.replace(key, value);
            logger.debug("Replaced variable: " + key);
        }
        logger.info("Payload processed with variables");
        return processedPayload;
    }

    public String extractFieldFromPayload(String payload, String fieldPath) {
        try {
            JsonElement element = JsonParser.parseString(payload);
            JsonObject jsonObject = element.getAsJsonObject();
            String[] path = fieldPath.split("\\.");
            JsonElement current = jsonObject;

            for (String key : path) {
                current = current.getAsJsonObject().get(key);
            }
            logger.info("Field extracted: " + fieldPath);
            return current.getAsString();
        } catch (Exception e) {
            logger.error("Error extracting field: " + fieldPath, e);
            throw new RuntimeException("Failed to extract field", e);
        }
    }

    public String mergePayloads(String payload1, String payload2) {
        try {
            JsonObject json1 = JsonParser.parseString(payload1).getAsJsonObject();
            JsonObject json2 = JsonParser.parseString(payload2).getAsJsonObject();
            json2.entrySet().forEach(entry -> json1.add(entry.getKey(), entry.getValue()));
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String merged = gson.toJson(json1);
            logger.info("Payloads merged successfully");
            return merged;
        } catch (Exception e) {
            logger.error("Error merging payloads", e);
            throw new RuntimeException("Failed to merge payloads", e);
        }
    }

    public boolean validatePayloadStructure(String payload, List<String> requiredFields) {
        try {
            JsonObject jsonObject = JsonParser.parseString(payload).getAsJsonObject();
            for (String field : requiredFields) {
                if (!jsonObject.has(field)) {
                    logger.warn("Required field missing: " + field);
                    return false;
                }
            }
            logger.info("Payload structure validated");
            return true;
        } catch (Exception e) {
            logger.error("Error validating structure", e);
            return false;
        }
    }

    public String formatPayload(String payload) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonElement element = JsonParser.parseString(payload);
            logger.debug("Payload formatted");
            return gson.toJson(element);
        } catch (Exception e) {
            logger.error("Error formatting payload", e);
            throw new RuntimeException("Failed to format payload", e);
        }
    }

    public void logPayload(String payload) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonElement element = JsonParser.parseString(payload);
            logger.info("Payload:\n" + gson.toJson(element));
        } catch (Exception e) {
            logger.error("Error logging payload", e);
        }
    }
}
