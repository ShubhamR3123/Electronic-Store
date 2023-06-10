package com.electronic.store.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageableResponse <T>{


    private List<T>content;
    private int pageSize;
    private int pageNumber;

    private long totalElements;

    private int totalPages;

    private boolean lastPage;
}
