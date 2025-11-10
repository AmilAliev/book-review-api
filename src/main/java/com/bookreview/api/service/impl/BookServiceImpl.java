package com.bookreview.api.service.impl;

import com.bookreview.api.dto.BookDto;
import com.bookreview.api.dto.BookResponse;
import com.bookreview.api.exceptions.BookNotFoundException;
import com.bookreview.api.models.Book;
import com.bookreview.api.repository.BookRepository;
import com.bookreview.api.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public BookResponse getAllBooks(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Book> books = bookRepository.findAll(pageable);
        List<Book> bookList = books.getContent();
        List<BookDto> content = bookList.stream()
                .map(b -> mapToDto(b))
                .toList();
        BookResponse bookResponse = new BookResponse();
        bookResponse.setContent(content);
        bookResponse.setPageNumber(books.getNumber());
        bookResponse.setPageSize(books.getSize());
        bookResponse.setTotalElements((int) books.getTotalElements());
        bookResponse.setTotalPages(books.getTotalPages());
        bookResponse.setLastPage(books.isLast());

        return bookResponse;
    }

    @Override
    public BookDto getBookById(long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book with id: " + id + " not found"));
        return mapToDto(book);
    }

    @Override
    public BookDto updateBook(BookDto bookDto, long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book with id: " + id + " could not be updated"));
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setPrice(bookDto.getPrice());

        Book updatedBook = bookRepository.save(book);
        return mapToDto(updatedBook);
    }

    @Override
    public void deleteBookById(long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book with id: " + id + " could not be delete"));
        bookRepository.delete(book);
    }


    private Book mapToEntity(BookDto bookDto) {
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
