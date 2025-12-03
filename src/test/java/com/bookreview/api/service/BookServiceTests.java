package com.bookreview.api.service;

import com.bookreview.api.dto.BookDto;
import com.bookreview.api.dto.BookResponse;
import com.bookreview.api.models.Book;
import com.bookreview.api.repository.BookRepository;
import com.bookreview.api.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void BookService_CreateBook_ReturnBookDto() {

        Book book = Book.builder()
                .title("The Stranger").author("Albert Camus").price(14.99)
                .build();

        BookDto bookDto = BookDto.builder()
                .title("The Stranger").author("Albert Camus").price(14.99)
                .build();

        when(bookRepository.save(Mockito.any(Book.class)))
                .thenReturn(book);

        BookDto savedBook = bookService.createBook(bookDto);

        Assertions.assertNotNull(savedBook);
    }

    @Test
    public void BookService_GetAllBook_ReturnResponseDto() {

        Page<Book> books = Mockito.mock(Page.class);

        when(bookRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(books);

        BookResponse saveBook = bookService.getAllBooks(1,10);

        Assertions.assertNotNull(saveBook);
    }

    @Test
    public void BookService_GetBookById_ReturnBookDto() {

        Book book = Book.builder()
                .title("The Stranger").author("Albert Camus").price(14.99)
                .build();

        when(bookRepository.findById(1L))
                .thenReturn(Optional.ofNullable(book));

        BookDto savedBook = bookService.getBookById(1L);

        Assertions.assertNotNull(savedBook);
    }

    @Test
    public void BookService_UpdateBook_ReturnBookDto() {

        Book book = Book.builder()
                .title("The Stranger").author("Albert Camus").price(14.99)
                .build();

        BookDto bookDto = BookDto.builder()
                .title("The Stranger").author("Albert Camus").price(14.99)
                .build();
        when(bookRepository.findById(1L))
                .thenReturn(Optional.ofNullable(book));
        when(bookRepository.save(Mockito.any(Book.class)))
                .thenReturn(book);

        BookDto savedBook = bookService.updateBook(bookDto,1L);

        Assertions.assertNotNull(savedBook);
    }

    @Test
    public void BookService_DeleteBookById_ReturnBookDto() {

        Book book = Book.builder()
                .title("The Stranger").author("Albert Camus").price(14.99)
                .build();

        when(bookRepository.findById(1L))
                .thenReturn(Optional.ofNullable(book));

        Assertions.assertAll(() -> bookService.deleteBookById(1L));
    }
}
