package com.api.automation.stepDefs;

import com.api.automation.pojo.APIClient;
import com.api.automation.utils.TestContext;

public class SharedDriver {

    private APIClient apiClient;
    private TestContext testContext;

    public SharedDriver() {
        this.testContext = new TestContext();
    }

    public APIClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(APIClient apiClient) {
        this.apiClient = apiClient;
    }

    public TestContext getTestContext() {
        return testContext;
    }

    public void setTestContext(TestContext testContext) {
        this.testContext = testContext;
    }
}
