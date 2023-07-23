package com.electronic.store.helper;

import lombok.*;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ApiResponse {
    String message;
    boolean success;
    HttpStatus Status;
}
