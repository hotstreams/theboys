package com.theboys.to;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomHttpResponse {
    private String status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    public CustomHttpResponse() {
        this.message = null;
        this.status = "OK";
    }
}
