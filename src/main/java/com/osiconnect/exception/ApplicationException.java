package com.osiconnect.exception;

import javax.validation.constraints.NotNull;

import com.osiconnect.constants.ApplicationConstants;
import lombok.Getter;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException{

    @Getter
    private final HttpStatus httpStatus;
    @Getter private final String errorKey;


    /**
     * Instantiates a new Application exception.
     *
     * @param message the message
     * @param errorKey the error key
     * @param httpStatus the http status
     */
    public ApplicationException(
        @NotNull final String message,
        @NotNull final String errorKey,
        @NotNull final HttpStatus httpStatus) {
        super(message);
        this.errorKey = errorKey;
        this.httpStatus = httpStatus;
    }


    @Override
    public String getMessage() {
        JSONObject errObj = new JSONObject();
        errObj.put(ApplicationConstants.MSG, super.getMessage());
        errObj.put(ApplicationConstants.SUCCESS, false);
        return errObj.toString();
    }


}
