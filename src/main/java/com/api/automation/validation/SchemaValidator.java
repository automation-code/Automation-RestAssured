package com.api.automation.validation;

import io.restassured.response.Response;
import org.apache.log4j.Logger;

import java.util.Map;

public class SchemaValidator {

    private static Logger logger = Logger.getLogger(SchemaValidator.class);

    public static boolean validateSchema(Response response, String... requiredFields) {
        try {
            Map<String, Object> responseMap = response.jsonPath().getMap("$");

            for (String field : requiredFields) {
                if (!responseMap.containsKey(field)) {
                    logger.error("Required field missing: " + field);
                    return false;
                }
            }
            logger.info("Schema validation passed");
            return true;
        } catch (Exception e) {
            logger.error("Schema validation failed", e);
            return false;
        }
    }

    public static boolean validateFieldType(Response response, String fieldPath, Class<?> expectedType) {
        try {
            Object value = response.jsonPath().get(fieldPath);
            if (value == null) {
                logger.error("Field not found: " + fieldPath);
                return false;
            }

            boolean isValid = expectedType.isInstance(value);
            if (isValid) {
                logger.info("Field type valid: " + fieldPath);
            } else {
                logger.error("Field type mismatch: " + fieldPath);
            }
            return isValid;
        } catch (Exception e) {
            logger.error("Field type validation failed", e);
            return false;
        }
    }
}
