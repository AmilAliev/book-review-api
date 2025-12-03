package com.bookreview.api.controller;

import com.bookreview.api.controllers.BookController;
import com.bookreview.api.dto.BookDto;
import com.bookreview.api.dto.BookResponse;
import com.bookreview.api.dto.ReviewDto;
import com.bookreview.api.models.Book;
import com.bookreview.api.models.Review;
import com.bookreview.api.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = BookController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class BookControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    private Book book;
    private Review review;
    private ReviewDto reviewDto;
    private BookDto bookDto;

    @BeforeEach
    public void init() {
        book = Book.builder().title("The Stranger").author("Albert Camus").price(14.99).build();
        bookDto = BookDto.builder().title("The Fall").author("Albert Camus").price(14.99).build();
        review = Review.builder().title("title").content("content").stars(5).build();
        reviewDto = ReviewDto.builder().title("review title").content("test content").stars(5).build();
    }

    @Test
    public void BookController_CreateBook_ReturnCreatedBook() throws Exception {
        given(bookService.createBook(ArgumentMatchers.any()))
                .willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/api/book/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(bookDto.getAuthor()));

    }

    @Test
    public void BookController_GetAllBook_ReturnResponseDto() throws Exception {
        BookResponse responseDto = BookResponse.builder().pageSize(10).lastPage(true).pageNumber(1).content(Arrays.asList(bookDto)).build();
        when(bookService.getAllBooks(1,10)).thenReturn(responseDto);

        ResultActions response = mockMvc.perform(get("/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNumber","1")
                .param("pageSize", "10"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()", CoreMatchers.is(responseDto.getContent().size())));
    }

    @Test
    public void BookController_BookDetail_ReturnBookDto() throws Exception {
        int pokemonId = 1;
        when(bookService.getBookById(1)).thenReturn(bookDto);

        ResultActions response = mockMvc.perform(get("/api/book/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(bookDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author", CoreMatchers.is(bookDto.getAuthor())));
    }

    @Test
    public void BookController_UpdateBook_ReturnBookDto() throws Exception {
        int bookId = 1;
        when(bookService.updateBook(bookDto,bookId)).thenReturn(bookDto);

        ResultActions response = mockMvc.perform(put("/api/book/1/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(bookDto.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author", CoreMatchers.is(bookDto.getAuthor())));
    }

    @Test
    public void BookController_DeleteBook_ReturnBookDto() throws Exception {
        int bookId = 1;
        doNothing().when(bookService).deleteBookById(1);

        ResultActions response = mockMvc.perform(delete("/api/book/1/delete")
                .contentType(MediaType.APPLICATION_JSON)) ;

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
