package com.bookreview.api.service.impl;

import com.bookreview.api.dto.BookDto;
import com.bookreview.api.dto.BookResponse;
import com.bookreview.api.exceptions.BookNotFoundException;
import com.bookreview.api.models.Book;
import com.bookreview.api.repository.BookRepository;
import com.bookreview.api.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public BookDto createBook(BookDto bookDto) {
        log.info("Creating new book: {}", bookDto.getTitle());

        Book book = mapToEntity(bookDto);
        Book savedBook = bookRepository.save(book);

        log.info("Book created successfully with id {}", savedBook.getId());
        return mapToDto(savedBook);
    }

    @Override
    public BookResponse getAllBooks(int pageNumber, int pageSize) {
        log.info("Fetching all books: page={}, size={}", pageNumber, pageSize);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Book> books = bookRepository.findAll(pageable);

        List<BookDto> content = books.getContent().stream()
                .map(this::mapToDto)
                .toList();

        log.info("Fetched {} books", content.size());

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
        log.info("Fetching book with id {}", id);

        Book book = findBookOrThrow(id);

        log.info("Book found: {}", id);
        return mapToDto(book);
    }

    @Override
    public BookDto updateBook(BookDto bookDto, long id) {
        log.info("Updating book with id {}", id);

        Book book = findBookOrThrow(id);

        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setPrice(bookDto.getPrice());

        Book updatedBook = bookRepository.save(book);

        log.info("Book updated successfully: {}", id);
        return mapToDto(updatedBook);
    }

    @Override
    public void deleteBookById(long id) {
        log.warn("Deleting book with id {}", id);

        Book book = findBookOrThrow(id);
        bookRepository.delete(book);

        log.warn("Book deleted: {}", id);
    }

    private Book findBookOrThrow(long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Book not found with id {}", id);
                    return new BookNotFoundException("Book with id: " + id + " not found");
                });
    }

    private Book mapToEntity(BookDto bookDto) {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setPrice(bookDto.getPrice());
        return book;
    }

    private BookDto mapToDto(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setPrice(book.getPrice());
        return dto;
    }
}
