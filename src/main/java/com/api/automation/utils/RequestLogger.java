package com.api.automation.utils;

import io.restassured.response.Response;
import org.apache.log4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class RequestLogger {

    private static Logger logger = Logger.getLogger(RequestLogger.class);
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static void logRequest(String method, String url, String body, Map<String, String> headers) {
        logger.info("==================== REQUEST ====================");
        logger.info("Timestamp: " + LocalDateTime.now().format(dtf));
        logger.info("Method: " + method);
        logger.info("URL: " + url);

        if (headers != null && !headers.isEmpty()) {
            logger.info("Headers:");
            headers.forEach((key, value) -> logger.info("  " + key + ": " + value));
        }

        if (body != null && !body.isEmpty()) {
            logger.info("Body: " + body);
        }
        logger.info("================================================");
    }

    public static void logResponse(Response response) {
        logger.info("==================== RESPONSE ====================");
        logger.info("Timestamp: " + LocalDateTime.now().format(dtf));
        logger.info("Status Code: " + response.statusCode());
        logger.info("Response Time: " + response.time() + "ms");
        logger.info("Content-Type: " + response.contentType());
        logger.info("Body: " + response.body().asString());
        logger.info("==================================================");
    }

    public static void logError(Exception exception) {
        logger.error("==================== ERROR ====================");
        logger.error("Timestamp: " + LocalDateTime.now().format(dtf));
        logger.error("Error Message: " + exception.getMessage());

        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw));
        logger.error("Stack Trace:\n" + sw.toString());

        logger.error("==============================================");
    }
}
