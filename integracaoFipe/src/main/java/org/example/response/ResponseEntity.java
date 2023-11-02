package org.example.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ResponseEntity <T> {

    private Integer statusCode;
    private T response;

}
