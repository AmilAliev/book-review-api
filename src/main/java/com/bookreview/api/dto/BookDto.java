package com.bookreview.api.dto;

import lombok.Data;

@Data
public class BookDto {

    private long id;
    private String title;
    private String author;
    private double price;

}
