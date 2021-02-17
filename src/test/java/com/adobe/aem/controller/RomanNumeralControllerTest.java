package com.adobe.aem.controller;

import com.adobe.aem.service.RomanNumeralService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.server.ResponseStatusException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RomanNumeralController.class)
class RomanNumeralControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RomanNumeralService service;

    @Test
    void testIndex() throws Exception {
        String expected = "Clarence Siu's Adobe & AEM Engineering Test.<br>GET http://localhost:8080/romannumeral?query={integer}";
        mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(equalTo(expected)));
    }

    @Test
    void testConvertToRomanNumeral_HappyPath() throws Exception {
        when(service.convert("5", 1, 3999)).thenReturn("V");
        String expected = "{\"input\":\"5\",\"output\":\"V\"}";
        mockMvc.perform(get("/romannumeral?query=5")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(equalTo(expected)));
    }

    @Test
    void testConvertToRomanNumeral_HappyPathWithSpaces() throws Exception {
        when(service.convert("3999", 1, 3999)).thenReturn("MMMCMXCIX");
        String expected = "{\"input\":\" 3999 \",\"output\":\"MMMCMXCIX\"}";
        mockMvc.perform(get("/romannumeral?query= 3999 ")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(equalTo(expected)));
    }

    @Test
    void testConvertToRomanNumeral_EmptyQuery() throws Exception {
        String expected = "400 BAD_REQUEST \"Query must contain a value\"";
        mockMvc.perform(get("/romannumeral?query=   ")).andDo(print()).andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                .andExpect(result -> assertEquals(expected, result.getResolvedException().getMessage()));
    }

    @Test
    void testConvertToRomanNumeral_OutOfRange() throws Exception {
        String expected = "400 BAD_REQUEST \"Query must be within range of 1-3999\"";
        mockMvc.perform(get("/romannumeral?query=4000")).andDo(print()).andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                .andExpect(result -> assertEquals(expected, result.getResolvedException().getMessage()));
    }

    @Test
    void testConvertToRomanNumeral_NonInteger() throws Exception {
        String expected = "400 BAD_REQUEST \"Query must be an integer\"";
        mockMvc.perform(get("/romannumeral?query=abc")).andDo(print()).andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseStatusException))
                .andExpect(result -> assertEquals(expected, result.getResolvedException().getMessage()));
    }

    @Test
    void testConvertToRomanNumeral_MissingQuery() throws Exception {
        String expected = "Required String parameter 'query' is not present";
        mockMvc.perform(get("/romannumeral")).andDo(print()).andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MissingServletRequestParameterException))
                .andExpect(result -> assertEquals(expected, result.getResolvedException().getMessage()));
    }

    @Test
    void testConvertToRomanNumeral_UnsupportedMethod() throws Exception {
        String expected = "Request method 'POST' not supported";
        mockMvc.perform(post("/romannumeral?query=5")).andDo(print()).andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException))
                .andExpect(result -> assertEquals(expected, result.getResolvedException().getMessage()));
    }

}