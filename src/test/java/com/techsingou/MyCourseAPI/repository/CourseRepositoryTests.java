// This class intends to test the methods tat we use in our repository: save, findAll, findById, delete, deleteAll

package com.techsingou.MyCourseAPI.repository;

import com.techsingou.MyCourseAPI.entity.Course;
import com.techsingou.MyCourseAPI.respository.CourseRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CourseRepositoryTests {
    @Autowired
    private CourseRepository courseRepository;

    @DisplayName("Test save Method")
    @Test
    public void CourseRepository_Save_ReturnSavedCourse() {
        // Arrange
        Course course = Course.builder()
                .name("Mastering Spring Boot")
                .category("Spring")
                .rating(4)
                .description("Mastering Spring Boot intends to teach Spring Boot with  practical examples")
                .build();

        // Act
        Course savedCourse = courseRepository.save(course);

        // Assert
        Assertions.assertThat(savedCourse).isNotNull();
        Assertions.assertThat(savedCourse.getId()).isGreaterThan(0);
    }

    @DisplayName("Test findAll Method")
    @Test
    public void CourseRepository_GetAll_ReturnMoreThanOneCourse() {
        // Arrange
        Course course1 = Course.builder()
                .name("Mastering Spring Boot")
                .category("Spring")
                .rating(4)
                .description("Mastering Spring Boot intends to teach Spring Boot with  practical examples")
                .build();
        Course course2 = Course.builder()
                .name("Mastering Angular")
                .category("Angular")
                .rating(5)
                .description("Mastering Angular intends to teach Angular framework with  practical examples")
                .build();
        courseRepository.save(course1);
        courseRepository.save(course2);

        // Act
        List<Course> courseList = (List<Course>) courseRepository.findAll();

        // Assert
        Assertions.assertThat(courseList).isNotNull();
        Assertions.assertThat(courseList.size()).isEqualTo(2);
    }

    @DisplayName("Test findById Method")
    @Test
    public void CourseRepository_FindById_ReturnsCourse() {
        // Arrange
        Course course = Course.builder()
                .name("Mastering Spring Boot")
                .category("Spring")
                .rating(4)
                .description("Mastering Spring Boot intends to teach Spring Boot with  practical examples")
                .build();

        courseRepository.save(course);

        // Act
        Course existingCourse = courseRepository.findById(course.getId()).get();

        // Assert
        Assertions.assertThat(existingCourse).isNotNull();
    }

    @DisplayName("Test deleteById Method")
    @Test
    public void CourseRepository_DeleteById_Then_CourseOneDelete() {
        // Arrange
        Course course = Course.builder()
                .name("Mastering Spring Boot")
                .category("Spring")
                .rating(4)
                .description("Mastering Spring Boot intends to teach Spring Boot with  practical examples")
                .build();
        Course savedCourse =  courseRepository.save(course);

        // Act
        courseRepository.deleteById(savedCourse.getId());
        List<Course> courses = (List<Course>) courseRepository.findAll();

        // Assert
        Assertions.assertThat(courses.size()).isEqualTo(0);
    }

    @DisplayName("deleteAll Method")
    @Test
    public void CourseRepository_DeleteAll_Then_CoursesDelete() {
        // Arrange
        Course course1 = Course.builder()
                .name("Mastering Spring Boot")
                .category("Spring")
                .rating(4)
                .description("Mastering Spring Boot intends to teach Spring Boot with  practical examples")
                .build();
        Course course2 = Course.builder()
                .name("Mastering Angular")
                .category("Angular")
                .rating(5)
                .description("Mastering Angular intends to teach Angular framework with  practical examples")
                .build();
        Course course3 = Course.builder()
                .name("Mastering Flutter")
                .category("Flutter")
                .rating(9)
                .description("Mastering Flutter intends to teach Flutter with  practical examples")
                .build();
        courseRepository.save(course1);
        courseRepository.save(course2);
        courseRepository.save(course3);

        // Act
        courseRepository.deleteAll();
        List<Course> courses = (List<Course>) courseRepository.findAll();

        // Assert
        Assertions.assertThat(courses.size()).isEqualTo(0);
    }
}
