package com.bookreview.api.repository;

import com.bookreview.api.models.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BookRepositoryTests {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void BookRepository_SaveAll_ReturnSavedBook() {

        Book book = Book.builder()
                .title("The Stranger").author("Albert Camus").price(14.99)
                .build();

        Book savedBook = bookRepository.save(book);
        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getId()).isGreaterThan(0);
    }

    @Test
    public void BookRepository_GetAll_ReturnMoreThenOneBook() {

        Book book1 = Book.builder()
                .title("The Stranger").author("Albert Camus").price(14.99)
                .build();

        Book book2 = Book.builder()
                .title("The Fall").author("Albert Camus").price(14.99)
                .build();

        bookRepository.save(book1);
        bookRepository.save(book2);

        List<Book> books = bookRepository.findAll();

        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(2);
    }

    @Test
    public void BookRepository_FindById_ReturnBook() {

        Book book = Book.builder()
                .title("The Stranger").author("Albert Camus").price(14.99)
                .build();

        bookRepository.save(book);

        Book bookSave = bookRepository.findById(book.getId()).get();

        assertThat(bookSave).isNotNull();
    }

    @Test
    public void BookRepository_FindByTitle_ReturnBookNotNull() {

        Book book = Book.builder()
                .title("The Stranger").author("Albert Camus").price(14.99)
                .build();

        bookRepository.save(book);

        Book bookSave = bookRepository.findByTitle(book.getTitle()).get();

        assertThat(bookSave).isNotNull();
    }

    @Test
    public void BookRepository_UpdateBook_ReturnBookNotNull() {

        Book book = Book.builder()
                .title("The Stranger").author("Albert Camus").price(14.99)
                .build();

        bookRepository.save(book);

        Book bookSave = bookRepository.findById(book.getId()).get();
        bookSave.setTitle("The Fall");
        bookSave.setAuthor("Albert Camus");
        bookSave.setPrice(9.99);

        Book updatedBook = bookRepository.save(bookSave);

        assertThat(updatedBook.getAuthor()).isNotNull();
        assertThat(updatedBook.getTitle()).isNotNull();
        assertThat(updatedBook.getPrice()).isNotNull();
    }

    @Test
    public void BookRepository_BookDelete_ReturnBookIsEmpty() {

        Book book = Book.builder()
                .title("The Stranger").author("Albert Camus").price(14.99)
                .build();

        bookRepository.save(book);

        bookRepository.deleteById(book.getId());
        Optional<Book> bookOptional = bookRepository.findById(book.getId());

        assertThat(bookOptional).isEmpty();
    }
}
