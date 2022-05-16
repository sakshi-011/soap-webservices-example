package com.soap.webservices.example.soap.service;

import com.soap.webservices.example.soap.Course;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

@Component
public class CourseDetailsService {

    public enum Status{
        SUCCESS, FAILURE;
    }

    public static List<Course> courses = new ArrayList<Course>();
    public static List<Course> deletedCourses = new ArrayList<Course>();

    static{
        Course course1 = new Course(1001, "Spring Web Services and RESTful API","Master Java Web Services and REST API using Spring Boot.");
        courses.add(course1);

        Course course2 = new Course(1002, "Master Microservices with Spring Boot","Java, Spring Boot, Spring Cloud, Docker and Kubernetes.");
        courses.add(course2);

        Course course3 = new Course(1003,"The complete JavaScript Course", "Advanced modern JavaScript course from scratch");
        courses.add(course3);

        Course course4 = new Course(1004,"Web Development Bootcamp","Master front-end and back-end developmrnt");
        courses.add(course4);
    }

    public Course findById(int id){
        for(Course course: courses){
            if(course.getId() == id)
                return course;
        }
        return null;
    }

    public List<Course> findAllCourses(){
        return courses;
    }

    public Status deleteById(int id){
        Iterator<Course> iterator = courses.iterator();
        while(iterator.hasNext()){
            Course course = iterator.next();
            if(course.getId() == id) {
                deletedCourses.add(course);
                iterator.remove();
                return Status.SUCCESS;
            }
        }
        return Status.FAILURE;
    }
}
