package com.bookreview.api.service.impl;

import com.bookreview.api.dto.BookDto;
import com.bookreview.api.models.Book;
import com.bookreview.api.repository.BookRepository;
import com.bookreview.api.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public BookDto createBook(BookDto bookDto) {
        Book book = mapToEntity(bookDto);
        Book savedBook = bookRepository.save(book);
        return mapToDto(savedBook);
    }

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(b -> mapToDto(b))
                .collect(Collectors.toList());
    }


    private Book  mapToEntity(BookDto bookDto) {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setPrice(bookDto.getPrice());
        return book;
    }

    private BookDto mapToDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setPrice(book.getPrice());
        return bookDto;
    }
}
