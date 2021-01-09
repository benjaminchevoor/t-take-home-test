package com.bchevoor.incrementer.web;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@TestExecutionListeners(MockitoTestExecutionListener.class)
public class RestApiTest extends AbstractTestNGSpringContextTests {

    /**
     * Helps run our test cases
     */
    private static class TestScenario {

        private static void assertIntegerResponse(int expectedValue, ResultActions resultActions) throws Exception {
            JSONObject expectedJson = new JSONObject();
            expectedJson.put("integer", expectedValue);
            assertJson(expectedJson.toString(), resultActions);
        }

        private static void assertJson(String expectedJson, ResultActions resultActions) throws Exception {
            MvcResult mvcResult = resultActions.andReturn();
            try {
                resultActions.andExpect(content().json(expectedJson, true));
            } catch (AssertionError err) {
                String contentAsString = mvcResult.getResponse()
                                                  .getContentAsString();
                throw new AssertionError(String.format("Expected %s by found %s", expectedJson, contentAsString), err);
            }
        }

        private static void assertReasonResponse(String expectedValue, ResultActions resultActions) throws Exception {
            JSONObject expectedJson = new JSONObject();
            expectedJson.put("reason", expectedValue);
            assertJson(expectedJson.toString(), resultActions);
        }
        public static final String AUTHORIZATION_HEADER = "Authorization";
        private final MockMvc mockMvc;
        private String apiKey;

        public TestScenario(MockMvc mockMvc) {
            this.mockMvc = mockMvc;
        }

        private void checkApiKey() {
            if (apiKey == null) {
                throw new IllegalStateException("API Key not set. Is the user registered?");
            }
        }

        /**
         * Deletes the registered user.
         */
        public void deleteRegisteredUser() throws Exception {
            checkApiKey();

            mockMvc.perform(delete(RestApi.FULL_ENDPOINT_PATH + "/register")
                    .header(AUTHORIZATION_HEADER, "Bearer " + apiKey))
                   .andExpect(status().isOk());
        }

        /**
         * Retrieves the CURRENT value from the service and asserts that the value matches the provided int.
         */
        public void getCurrentAndAssertUnauthorized() throws Exception {
            checkApiKey();

            mockMvc.perform(get(RestApi.FULL_ENDPOINT_PATH + "/current")
                    .header(AUTHORIZATION_HEADER, "Bearer " + apiKey))
                   .andExpect(status().isUnauthorized());
        }

        /**
         * Retrieves the CURRENT value from the service and asserts that the value matches the provided int.
         *
         * @param expectedValue the value expected to be returned by the service.
         */
        public void getCurrentAndAssertValue(int expectedValue) throws Exception {
            checkApiKey();

            ResultActions resultActions = mockMvc
                    .perform(get(RestApi.FULL_ENDPOINT_PATH + "/current")
                            .header(AUTHORIZATION_HEADER, "Bearer " + apiKey))
                    .andExpect(status().isOk());

            assertIntegerResponse(expectedValue, resultActions);
        }

        /**
         * Retrieves the NEXT value from the service and asserts that the value matches the provided int.
         *
         * @param expectedValue the value expected to be returned by the service.
         */
        public void getNextAndAssertValue(int expectedValue) throws Exception {
            checkApiKey();

            ResultActions resultActions = mockMvc
                    .perform(get(RestApi.FULL_ENDPOINT_PATH + "/next")
                            .header(AUTHORIZATION_HEADER, "Bearer " + apiKey))
                    .andExpect(status().isOk());

            assertIntegerResponse(expectedValue, resultActions);
        }

        /**
         * Updates the current value stored by the service.
         *
         * @param value the value to store in the service
         */
        public void putCurrentValue(int value) throws Exception {
            checkApiKey();

            JSONObject requestBody = new JSONObject();
            requestBody.put("integer", value);

            mockMvc.perform(put(RestApi.FULL_ENDPOINT_PATH + "/current")
                    .header(AUTHORIZATION_HEADER, "Bearer " + apiKey)
                    .content(requestBody.toString())
                    .contentType(MediaType.APPLICATION_JSON))
                   .andExpect(status().isOk());
        }

        /**
         * Registers a new user to the service with the provided email and password.
         *
         * @param email    the new user's email
         * @param password the new user's password
         */
        public void registerUser(String email, String password) throws Exception {
            JSONObject requestBody = new JSONObject();
            requestBody.put("email", email);
            requestBody.put("password", password);

            ResultActions resultActions = mockMvc
                    .perform(post(RestApi.FULL_ENDPOINT_PATH + "/register")
                            .content(requestBody.toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
            JSONObject object = new JSONObject(contentAsString);
            this.apiKey = (String) object.get("apiKey");
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }
    }

    @Autowired
    private WebApplicationContext context;

    public TestScenario createTestScenario() {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context)
                                         .build();

        return new TestScenario(mockMvc);
    }

    /**
     * Tests that the a fake user cannot access authorized endpoints.
     */
    @Test
    public void testBadUrl() throws Exception {
        TestScenario ts = createTestScenario();
        ts.setApiKey("fakeApiKey");
        ts.getCurrentAndAssertUnauthorized();
    }

    @Test
    public void testServiceFlow() throws Exception {
        /*
         Test for exercising the flow of the following cases:
         1) Create a user
         2) Get the current value for a user, assert it is 0
         3) Increment the value for the user, assert it is 1
         4) Get the current value for the user, assert it is 1
         5) Set the value for the user to 1000
         7) Get the current value for the user, assert it is 1000
         8) Increment the value for the user, assert it is 1001
         9) Test deleting the user
         */

        TestScenario ts = createTestScenario();
        ts.registerUser("my@email.com", "supersecretpassword");
        ts.getCurrentAndAssertValue(0);
        ts.getNextAndAssertValue(1);
        ts.getCurrentAndAssertValue(1);
        ts.putCurrentValue(1000);
        ts.getCurrentAndAssertValue(1000);
        ts.getNextAndAssertValue(1001);
        ts.deleteRegisteredUser();
    }
}