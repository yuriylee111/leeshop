package com.lee.shop.exception;

import com.lee.shop.Constants;

import java.util.Collections;
import java.util.Map;

public class InvalidUserInputException extends RuntimeException {

    private final String jspName;

    private final Map<String, String> errorsMap;

    private final Map<String, Object> models;

    public InvalidUserInputException(String jspName, Map<String, String> errorsMap) {
        this(jspName, errorsMap, Collections.emptyMap());
    }

    public InvalidUserInputException(String jspName, Map<String, String> errorsMap, Object dto) {
        this.jspName = jspName;
        this.errorsMap = errorsMap;
        this.models = Collections.singletonMap(Constants.DTO, dto);
    }

    public InvalidUserInputException(String jspName, Map<String, String> errorsMap, Map<String, Object> models) {
        this.jspName = jspName;
        this.errorsMap = errorsMap;
        this.models = models;
    }

    public String getJspName() {
        return jspName;
    }

    public Map<String, String> getErrorsMap() {
        return errorsMap;
    }

    public Map<String, Object> getModels() {
        return models;
    }
}
