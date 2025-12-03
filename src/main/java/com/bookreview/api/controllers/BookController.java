package com.bookreview.api.controllers;

import com.bookreview.api.dto.BookDto;
import com.bookreview.api.dto.BookResponse;
import com.bookreview.api.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<BookResponse> getBooks(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {

        log.info("GET /api/book called with pageNumber={}, pageSize={}", pageNumber, pageSize);

        BookResponse response = bookService.getAllBooks(pageNumber, pageSize);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable long id) {

        log.info("GET /api/book/{} called", id);

        BookDto dto = bookService.getBookById(id);

        log.debug("Book fetched: {}", dto);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {

        log.info("POST /api/book/create called");
        log.debug("Book create request body: {}", bookDto);

        BookDto created = bookService.createBook(bookDto);

        log.info("Book created with ID {}", created.getId());

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable("id") long bookId, @RequestBody BookDto bookDto) {

        log.info("PUT /api/book/{}/update called", bookId);
        log.debug("Book update body: {}", bookDto);

        BookDto updated = bookService.updateBook(bookDto, bookId);

        log.info("Book updated: ID={}", updated.getId());

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable long id) {

        log.info("DELETE /api/book/{}/delete called", id);

        bookService.deleteBookById(id);

        log.warn("Book deleted with ID {}", id);

        return new ResponseEntity<>("Book with id: " + id + " was deleted", HttpStatus.OK);
    }
}
