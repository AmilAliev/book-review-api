package com.bookreview.api.service;

import com.bookreview.api.dto.BookDto;

import java.util.List;

public interface BookService {

    BookDto createBook(BookDto bookDto);
    List<BookDto> getAllBooks();
    BookDto getBookById(long id);
    BookDto updateBook(BookDto bookDto, long id);
    void deleteBookById(long id);
}
