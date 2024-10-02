package com.techsingou.MyCourseAPI.controller;

import com.techsingou.MyCourseAPI.entity.Course;
import com.techsingou.MyCourseAPI.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/courses/")
public class CourseRestController {
    @Autowired
    private CourseService courseService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Course> getAllCourses() {
        return courseService.getCourses();
    }

    @GetMapping("{id}")
    public Optional<Course> getCourseById(@PathVariable("id") long courseId) {
        return courseService.getCourseById(courseId);
    }

    @GetMapping("category/{name}")
    public Iterable<Course> getCoursesByCategory(@PathVariable("name") String category) {
        return courseService.getCoursesByCategory(category);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Course createCourse(@RequestBody Course course) {
        return courseService.createCourse(course);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Course updateCourse(@PathVariable("id") long courseId, @RequestBody Course course) {
        return courseService.updateCourse(courseId, course);
    }

    @DeleteMapping("{id}")
    public void deleteCourseById(@PathVariable("id") long courseId) {
        courseService.deleteCourseById(courseId);
    }

    @DeleteMapping
    public void deleteAllCourses() {
        courseService.deleteCourses();
    }
}
