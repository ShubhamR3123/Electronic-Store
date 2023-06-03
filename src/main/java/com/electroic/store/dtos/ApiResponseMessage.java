package com.electroic.store.dtos;

import lombok.*;
import org.aspectj.bridge.Message;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponseMessage {

    private HttpStatus status;
    private boolean success;

    private String message;
}
