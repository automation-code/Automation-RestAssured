package com.api.automation.metrics;

import io.restassured.response.Response;
import org.apache.log4j.Logger;

import java.util.function.Supplier;

public class RetryPolicy {

    private static Logger logger = Logger.getLogger(RetryPolicy.class);
    private int maxRetries;
    private long delayMillis;

    public RetryPolicy(int maxRetries, long delayMillis) {
        this.maxRetries = maxRetries;
        this.delayMillis = delayMillis;
    }

    public Response executeWithRetry(Supplier<Response> requestSupplier, int... acceptableStatusCodes) {
        Response response = null;
        Exception lastException = null;

        for (int attempt = 1; attempt <= maxRetries + 1; attempt++) {
            try {
                response = requestSupplier.get();

                if (isAcceptableStatus(response.statusCode(), acceptableStatusCodes)) {
                    logger.info("Request successful on attempt " + attempt);
                    return response;
                } else if (attempt <= maxRetries) {
                    logger.warn("Status " + response.statusCode() + " on attempt " + attempt + ". Retrying...");
                    Thread.sleep(delayMillis);
                }
            } catch (Exception e) {
                lastException = e;
                if (attempt <= maxRetries) {
                    logger.warn("Request failed on attempt " + attempt + ". Retrying...");
                    try {
                        Thread.sleep(delayMillis);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        if (lastException != null) {
            throw new RuntimeException("Request failed after " + (maxRetries + 1) + " attempts", lastException);
        }
        return response;
    }

    private boolean isAcceptableStatus(int statusCode, int... acceptableCodes) {
        if (acceptableCodes.length == 0) {
            return statusCode >= 200 && statusCode < 300;
        }
        for (int code : acceptableCodes) {
            if (statusCode == code) return true;
        }
        return false;
    }
}
