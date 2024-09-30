package com.techsingou.MyCourseAPI.service;

import com.techsingou.MyCourseAPI.entity.Course;
import com.techsingou.MyCourseAPI.exception.CourseNotFoundException;
import com.techsingou.MyCourseAPI.respository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Optional<Course> getCourseById(long courseId) {
        return courseRepository.findById(courseId);
    }

    @Override
    public Iterable<Course> getCoursesByCategory(String category) {
        return courseRepository.findAllByCategory(category);
    }

    @Override
    public Iterable<Course> getCourses() {
        return courseRepository.findAll();
    }

    @Override
    public void updateCourse(long courseId, Course course) {
       /* courseRepository.findById(courseId).ifPresent(dbcourse -> {
            dbcourse.setName(course.getName());
            dbcourse.setCategory(course.getCategory());
            dbcourse.setDescription(course.getDescription());
            dbcourse.setRating(course.getRating());
            courseRepository.save(dbcourse);
        });*/
        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(String.format("No Course with id %s is available", courseId)));
        existingCourse.setName(course.getName());
        existingCourse.setCategory(course.getCategory());
        existingCourse.setCategory(course.getDescription());
        existingCourse.setRating(course.getRating());
        courseRepository.save(existingCourse);
    }

    @Override
    public void deleteCourseById(long courseId) {
        courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(String.format("No course with id %s is available",courseId)));
        courseRepository.deleteById(courseId);
    }

    @Override
    public void deleteCourses() {
        courseRepository.deleteAll();
    }
}
