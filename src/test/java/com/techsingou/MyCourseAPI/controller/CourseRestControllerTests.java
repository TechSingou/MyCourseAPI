package com.techsingou.MyCourseAPI.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techsingou.MyCourseAPI.entity.Course;
import com.techsingou.MyCourseAPI.service.CourseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
public class CourseRestControllerTests {

    @MockBean
    private CourseService courseService;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("Test createCourse Endpoint")
    @Test
    public void CourseRestController_createCourse_Returns_CourseCreated() throws Exception {
        Course course = Course.builder()
                .name("Mastering Spring Security")
                .category("Spring")
                .rating(3)
                .description("Mastering Spring Security intends to teach you how to secure java based app with  practical examples")
                .build();
        given(courseService.createCourse(any(Course.class))).willAnswer((invocationOnMock -> invocationOnMock.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/courses/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(course)));
        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Mastering Spring Security"))
                .andExpect(jsonPath("$.category").value("Spring"))
                .andExpect(jsonPath("$.rating").value(3));
    }

    @DisplayName("Test getAllCourses Endpoint")
    @Test
    public void CourseRestController_getAllCourses_Returns_AllCourses() throws Exception {
        Course course1 = Course.builder()
                .name("Mastering Spring Boot")
                .category("Spring")
                .rating(4)
                .description("Mastering Spring Boot intends to teach Spring Boot with  practical examples")
                .build();
        Course course2 = Course.builder()
                .name("Mastering Spring Security")
                .category("Spring")
                .rating(3)
                .description("Mastering Spring Security intends to teach you how to secure java based app with  practical examples")
                .build();
        Course course3 = Course.builder()
                .name("Angular v18 features")
                .category("Angular")
                .rating(4)
                .description("Angular v18 features best features with examples")
                .build();

        when(courseService.getCourses()).thenReturn(Arrays.asList(course1, course2, course3));
        ResultActions response = mockMvc.perform(get("/courses/")
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @DisplayName("Test updateCourse Endpoint")
    @Test
    public void CourseRestController_updateCourse_Returns_updatedCourse() throws Exception {
        Course course = Course.builder()
                .name("Mastering Spring Boot")
                .category("Spring")
                .rating(4)
                .description("Mastering Spring Boot intends to teach Spring Boot with  practical examples")
                .build();
        given(courseService.updateCourse(any(Long.class), any(Course.class))).willAnswer((invocationOnMock -> invocationOnMock.getArgument(1)));
        ResultActions response = mockMvc.perform(put("/courses/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(course)));
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mastering Spring Boot"))
                .andExpect(jsonPath("$.category").value("Spring"))
                .andExpect(jsonPath("$.rating").value(4));
    }


}
