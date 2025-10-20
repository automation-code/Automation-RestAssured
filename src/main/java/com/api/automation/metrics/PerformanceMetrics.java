package com.api.automation.metrics;

import io.restassured.response.Response;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PerformanceMetrics {

    private static Logger logger = Logger.getLogger(PerformanceMetrics.class);
    private List<Long> responseTimes;
    private Map<String, Long> endpointMetrics;

    public PerformanceMetrics() {
        this.responseTimes = new ArrayList<>();
        this.endpointMetrics = new HashMap<>();
    }

    public void recordResponseTime(long timeInMillis) {
        responseTimes.add(timeInMillis);
        logger.debug("Response time recorded: " + timeInMillis + "ms");
    }

    public void recordEndpointPerformance(String endpoint, Response response) {
        long responseTime = response.time();
        endpointMetrics.put(endpoint, responseTime);
        recordResponseTime(responseTime);
    }

    public double getAverageResponseTime() {
        return responseTimes.stream().mapToLong(Long::longValue).average().orElse(0);
    }

    public long getMaxResponseTime() {
        return responseTimes.stream().mapToLong(Long::longValue).max().orElse(0);
    }

    public long getMinResponseTime() {
        return responseTimes.stream().mapToLong(Long::longValue).min().orElse(0);
    }

    public void printPerformanceSummary() {
        logger.info("==================== PERFORMANCE METRICS ====================");
        logger.info("Total Requests: " + responseTimes.size());
        logger.info("Average: " + String.format("%.2f", getAverageResponseTime()) + "ms");
        logger.info("Max: " + getMaxResponseTime() + "ms");
        logger.info("Min: " + getMinResponseTime() + "ms");
        logger.info("============================================================");
    }

    public void clearMetrics() {
        responseTimes.clear();
        endpointMetrics.clear();
    }
}
