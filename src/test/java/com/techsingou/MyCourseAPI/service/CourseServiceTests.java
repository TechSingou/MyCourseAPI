package com.techsingou.MyCourseAPI.service;

import com.techsingou.MyCourseAPI.entity.Course;
import com.techsingou.MyCourseAPI.exception.CourseNotFoundException;
import com.techsingou.MyCourseAPI.respository.CourseRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTests {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    @DisplayName("Test the create method")
    @Test
    public void CourseService_CreateCourse_ReturnsCourse() {
        // Arrange
        Course course = Course.builder()
                .name("Mastering Spring Boot")
                .category("Spring")
                .rating(4)
                .description("Mastering Spring Boot intends to teach Spring Boot with  practical examples")
                .build();

        when(courseRepository.save(Mockito.any(Course.class)))
                .thenReturn(course);
        // Act
        Course savedCourse = courseService.createCourse(course);

        // Assert
        Assertions.assertThat(savedCourse).isNotNull();
    }

    @DisplayName("Test the getCourseById method")
    @Test
    public void CourseService_getCourseById_ReturnsCourse() {
        // Arrange
        Course course = Course.builder()
                .id(1L)
                .name("Mastering Spring Boot")
                .category("Spring")
                .rating(4)
                .description("Mastering Spring Boot intends to teach Spring Boot with  practical examples")
                .build();
        when(courseRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(course));
        // Act
        Optional<Course> course1 = courseService.getCourseById(1);

        // Assert
        Assertions.assertThat(course1).isNotNull();
    }

    @DisplayName("Throw CourseNotFoundException in getCourseById")
    @Test
    public void CourseService_getCourseById_Throws_CourseNotFoundException() {
        // Arrange
        when(courseRepository.findById(Mockito.anyLong()))
                .thenThrow(CourseNotFoundException.class);

        // Act
        // Assert
        assertThrows(CourseNotFoundException.class, () -> courseService.getCourseById(2));
    }

    @DisplayName("Test getCoursesByCategory Method")
    @Test
    public void CourseService_getCoursesByCategory_ReturnsSomeCourses() {
        // Arrange
        Course course2 = Course.builder()
                .name("Mastering Spring Boot")
                .category("Spring")
                .rating(4)
                .description("Mastering Spring Boot intends to teach Spring Boot with  practical examples")
                .build();
        Course course1 = Course.builder()
                .name("Mastering Spring Security")
                .category("Spring")
                .rating(3)
                .description("Mastering Spring Security intends to teach you how to secure java based app with  practical examples")
                .build();
        courseRepository.save(course1);
        courseRepository.save(course2);
        when(courseRepository.findAllByCategory(Mockito.anyString())).thenReturn(Arrays.asList(course1, course2));
        // Ac
        List<Course> courses = (List<Course>) courseService.getCoursesByCategory("Spring");
        // Assert
        Assertions.assertThat(courses).isNotNull();
        Assertions.assertThat(courses.size()).isEqualTo(2);
    }

    @DisplayName("Test getCourses Method")
    @Test
    public void CourseService_getCourses_Returns_AllCourses() {
        // Arrange
        Course course2 = Course.builder()
                .name("Mastering Spring Boot")
                .category("Spring")
                .rating(4)
                .description("Mastering Spring Boot intends to teach Spring Boot with  practical examples")
                .build();
        Course course1 = Course.builder()
                .name("Mastering Spring Security")
                .category("Spring")
                .rating(3)
                .description("Mastering Spring Security intends to teach you how to secure java based app with  practical examples")
                .build();
        courseRepository.save(course1);
        courseRepository.save(course2);
        when(courseRepository.findAll()).thenReturn(Arrays.asList(course1, course2));
        // Ac
        List<Course> courses = (List<Course>) courseService.getCourses();
        // Assert
        Assertions.assertThat(courses).isNotNull();
        Assertions.assertThat(courses.size()).isEqualTo(2);
    }

    @DisplayName("Test the updateCourse method")
    @Test
    public void CourseService_updateCourse_ThenCourseUpdated() {

        // Arrange
        Course existingCourse = Course.builder()
                .id(1L)
                .name("Mastering Spring Boot")
                .category("Spring")
                .rating(4)
                .description("Mastering Spring Boot intends to teach Spring Boot with  practical examples")
                .build();

        Course courseUpdatedVersion = Course.builder()
                .id(1L)
                .name("Angular v18 features")
                .category("Angular")
                .rating(4)
                .description("Angular v18 features best features with examples")
                .build();

        when(courseRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(existingCourse));

        when(courseRepository.save(Mockito.any(Course.class))).thenAnswer(new Answer<Course>() {
            @Override
            public Course answer(InvocationOnMock invocationOnMock) throws Throwable {
                return invocationOnMock.getArgument(0);
            }
        });

        // Act
        Course savedCourse = courseService.updateCourse(1, courseUpdatedVersion);


        // Assert
        Assertions.assertThat(savedCourse).isNotNull();
        Assertions.assertThat(savedCourse.getName()).isEqualTo("Angular v18 features");
        Assertions.assertThat(savedCourse.getCategory()).isEqualTo("Angular");
        Assertions.assertThat(savedCourse.getDescription()).isEqualTo("Angular v18 features best features with examples");
        Assertions.assertThat(savedCourse.getRating()).isEqualTo(4);
    }
}
