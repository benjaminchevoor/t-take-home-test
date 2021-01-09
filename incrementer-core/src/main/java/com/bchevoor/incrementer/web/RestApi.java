package com.bchevoor.incrementer.web;

import com.bchevoor.incrementer.User;
import com.bchevoor.incrementer.auth.AuthenticationFailureException;
import com.bchevoor.incrementer.auth.UserAlreadyExistsException;
import com.bchevoor.incrementer.auth.UserManagementService;
import com.bchevoor.incrementer.model.IncrementingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(RestApi.FULL_ENDPOINT_PATH)
public class RestApi {

    public static final String API_V1 = "/v1";
    public static final String FULL_ENDPOINT_PATH = RestApi.API_V1;
    public static final String BEARER_PREFIX = "Bearer ";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    private IncrementingService incrementingService;

    @Autowired
    private UserManagementService userManagementService;

    @PostMapping("register")
    @Operation(summary = "Creates a new user.")
    public @ResponseBody
    CreateNewUserResponseBody createNewUser(
            @RequestBody CreateNewUserRequestBody body) throws UserAlreadyExistsException {

        if (body == null) {
            throw new IllegalArgumentException("Invalid create user request body");
        }

        User newUser = userManagementService.createNewUser(body.getEmail(), body.getPassword());

        return new CreateNewUserResponseBody()
                .setEmail(body.getEmail())
                .setApiKey(newUser.getApiKey());
    }

    @DeleteMapping("register")
    @Operation(summary = "Deletes a user. This is an authenticated endpoint")
    public void deleteUser(@RequestHeader(value = AUTHORIZATION_HEADER) String authorizationValue)
            throws AuthenticationFailureException {

        User user = getUser(authorizationValue);
        userManagementService.deleteUser(user.getApiKey());
    }

    @GetMapping("current")
    @Operation(summary = "Returns the current value for the user. This is an authenticated endpoint.")
    public @ResponseBody
    IntegerBody getCurrentInteger(
            @RequestHeader(value = AUTHORIZATION_HEADER) String authorizationValue)
            throws AuthenticationFailureException {

        User user = getUser(authorizationValue);
        int val = incrementingService.currentIntegerForUser(user);

        //return it to them
        return new IntegerBody()
                .setInteger(val);
    }

    @GetMapping("next")
    @Operation(summary = "Increments the user stored value and returns that value. This is an authenticated endpoint.")
    public @ResponseBody
    IntegerBody getNextInteger(
            @RequestHeader(value = AUTHORIZATION_HEADER) String authorizationValue)
            throws AuthenticationFailureException {

        User user = getUser(authorizationValue);
        int val = incrementingService.incrementIntegerForUser(user);

        //return it to them
        return new IntegerBody()
                .setInteger(val);
    }

    private User getUser(@RequestHeader(AUTHORIZATION_HEADER) String authorizationValue) throws AuthenticationFailureException {
        if (authorizationValue == null || !authorizationValue.startsWith(BEARER_PREFIX)) {
            //if the auth header value is null or doesnt start with our expected "Bearer " value, then fail out
            throw new AuthenticationFailureException();
        }

        //remove the "Bearer " prefix from the auth value so we can get our API key
        String apiKey = authorizationValue.substring(BEARER_PREFIX.length());

        //retrieve the user and their next incremented integer value
        User user = userManagementService.authenticate(apiKey);
        if (user == null) {
            //if the user is null, then we should not allow them to use our service
            throw new AuthenticationFailureException();
        }

        return user;
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponseBody handleIllegalArgException(UserAlreadyExistsException e) {
        return new ErrorResponseBody().setReason("User already exists");
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponseBody handleMissingArgHeader(MissingRequestHeaderException e) {
        return new ErrorResponseBody().setReason(e.getMessage());
    }

    @PutMapping(value = "current", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Stores the provided integer in the service. The integer MUST be non-negative. " +
                         "This is an authenticated endpoint.")
    public @ResponseBody
    void putCurrentInteger(
            @RequestHeader(value = AUTHORIZATION_HEADER) String authorizationValue,
            @RequestBody IntegerBody integerBody)
            throws AuthenticationFailureException {

        User user = getUser(authorizationValue);

        if (integerBody == null) {
            throw new IllegalArgumentException("Invalid json body");
        }

        incrementingService.putIntegerForUser(user, integerBody.getInteger());
    }


}
