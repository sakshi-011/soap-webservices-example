package com.soap.webservices.example.soap;

import com.soap.webservices.example.exception.CourseNotFoundException;
import example.webservices.soap.courses.DeleteCourseDetailsRequest;
import example.webservices.soap.courses.DeleteCourseDetailsResponse;
import example.webservices.soap.courses.GetCourseDetailsRequest;
import example.webservices.soap.courses.GetCourseDetailsResponse;
import example.webservices.soap.courses.GetAllCourseDetailsRequest;
import example.webservices.soap.courses.GetAllCourseDetailsResponse;
import example.webservices.soap.courses.CourseDetails;
import com.soap.webservices.example.soap.service.CourseDetailsService;
import com.soap.webservices.example.soap.service.CourseDetailsService.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class CourseDetailsEndpoint {

    @Autowired
    CourseDetailsService service;

    @PayloadRoot(namespace = "http://soap.webservices.example/courses", localPart = "GetCourseDetailsRequest")
    @ResponsePayload
    public GetCourseDetailsResponse processCourseDetailsRequest(@RequestPayload GetCourseDetailsRequest request){
        Course course = service.findById(request.getId());

        if(course == null)
            throw new CourseNotFoundException("Invalid course Id " + request.getId());

        return mapCourseDetails(course);
    }

    @PayloadRoot(namespace = "http://soap.webservices.example/courses", localPart = "GetAllCourseDetailsRequest")
    @ResponsePayload
    public GetAllCourseDetailsResponse processAllCourseDetailsRequest(@RequestPayload GetAllCourseDetailsRequest request){
        List<Course> courses = service.findAllCourses();
        return mapAllCourseDetails(courses);
    }

    @PayloadRoot(namespace = "http://soap.webservices.example/courses", localPart = "DeleteCourseDetailsRequest")
    @ResponsePayload
    public DeleteCourseDetailsResponse processDeleteCourseDetailsRequest(@RequestPayload DeleteCourseDetailsRequest request){
        Status status = service.deleteById(request.getId());
        DeleteCourseDetailsResponse response = new DeleteCourseDetailsResponse();
        response.setStatus(mapStatus(status));
        return response;
    }

    private GetCourseDetailsResponse mapCourseDetails(Course course){
        GetCourseDetailsResponse response = new GetCourseDetailsResponse();
        CourseDetails courseDetails = mapCourse(course);
        response.setCourseDetails(courseDetails);
        return response;
    }

    private GetAllCourseDetailsResponse mapAllCourseDetails(List<Course> courses){
        GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();
        for(Course course : courses){
            CourseDetails mappedCourse = mapCourse(course);
            response.getCourseDetails().add(mappedCourse);
        }
        return response;
    }

    private CourseDetails mapCourse(Course course) {
        CourseDetails courseDetails = new CourseDetails();
        courseDetails.setId(course.getId());
        courseDetails.setName(course.getName());
        courseDetails.setDescription(course.getDescription());
        return courseDetails;
    }

    private example.webservices.soap.courses.Status mapStatus(Status status){
        if(status == Status.SUCCESS)
            return example.webservices.soap.courses.Status.SUCCESS;
        return example.webservices.soap.courses.Status.FAILURE;
    }

}
