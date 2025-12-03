package com.bookreview.api.service;

import com.bookreview.api.dto.BookDto;
import com.bookreview.api.dto.BookResponse;

public interface BookService {

    BookDto createBook(BookDto bookDto);

    BookResponse getAllBooks(int pageNumber, int pageSize);

    BookDto getBookById(long id);

    BookDto updateBook(BookDto bookDto, long id);

    void deleteBookById(long id);
}
