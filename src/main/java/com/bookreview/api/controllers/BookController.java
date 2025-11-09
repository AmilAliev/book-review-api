package com.bookreview.api.controllers;

import com.bookreview.api.dto.BookDto;
import com.bookreview.api.models.Book;
import com.bookreview.api.service.BookService;
import com.bookreview.api.service.impl.BookServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("book")
    public ResponseEntity<List<BookDto>> getBooks() {
       return new ResponseEntity<>(bookService.getAllBooks(),HttpStatus.OK);
    }

    @GetMapping("book/{id}")
    public ResponseEntity<Book> bookDetail(@PathVariable int id) {
        return new ResponseEntity<>(new Book(id,"Clean Code", "Robert C. Martin", 15.55), HttpStatus.OK);
    }

    @PostMapping("book/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        return new ResponseEntity<>(bookService.createBook(bookDto),HttpStatus.CREATED);
    }

}
