package com.librarymanagment.springbootlibrary.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Test
    void viewHomePage() throws Exception {
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/");
//        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
////        assertEquals("index",mvcResult.getResponse().getContentAsString());
    }

    @Test
    void enterNewBookPage() {
    }

    @Test
    void saveBook() {
    }

    @Test
    void saveBookEdit() {
    }

    @Test
    void editBook() {
    }

    @Test
    void deleteBook() {
    }
}