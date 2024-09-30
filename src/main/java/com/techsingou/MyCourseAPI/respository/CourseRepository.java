package com.techsingou.MyCourseAPI.respository;

import com.techsingou.MyCourseAPI.entity.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {
    Iterable<Course> findAllByCategory(String category);
}
