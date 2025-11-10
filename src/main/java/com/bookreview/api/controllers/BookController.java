package com.bookreview.api.controllers;

import com.bookreview.api.dto.BookDto;
import com.bookreview.api.dto.BookResponse;
import com.bookreview.api.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("book")
    public ResponseEntity<BookResponse> getBooks(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize) {
       return new ResponseEntity<>(bookService.getAllBooks(pageNumber,pageSize),HttpStatus.OK);
    }

    @GetMapping("book/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable long id) {
        return new ResponseEntity<>(bookService.getBookById(id),HttpStatus.OK);
    }

    @PostMapping("book/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        return new ResponseEntity<>(bookService.createBook(bookDto),HttpStatus.CREATED);
    }

    @PutMapping("book/{id}/update")
    public ResponseEntity<BookDto> updateBook(@PathVariable("id") long bookId, @RequestBody BookDto bookDto) {
        return new ResponseEntity<>(bookService.updateBook(bookDto,bookId),HttpStatus.OK);
    }

    @DeleteMapping("book/{id}/delete")
    public ResponseEntity<String>  deleteBookById(@PathVariable long id) {
        bookService.deleteBookById(id);
        return new ResponseEntity<>("Book with id: " + id + " was deleted",HttpStatus.OK);
    }

}
