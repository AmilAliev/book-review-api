package com.bookreview.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class BookResponse {
    private List<BookDto> content;
    private int pageNumber;
    private int pageSize;
    private int totalElements;
    private int totalPages;
    private boolean lastPage;
}
