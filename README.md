## Soap Web Services with Spring Boot

A simple example of Soap Web Service which gives an option to view information about JavaScript and Java courses available and also an option to delete courses.


### Stepwise implementation

1. Create an xsd (XML Schema Definition) file in Resources folder, after deciding the request and response xml structure (for reference you can checkout xml-files folder).
2. Use JAXB2 (Java Architecture for XML Binding) Maven plugin to generate Java classes corresponding to XML elements.
3. Define endpoints for all functionalities suck as getting course information by id, getting all courses information..etc.
4. Configure Spring Web Services using Message Dispatcher Servlet for simplified dispatching of web services messages. Also configure Spring Web Services Configuration class to generate WSDL using DefaultWsdl11Definition class. Define a bean that returns XsdSchema to an endpoint.
5. Inject a service to get course details from the endpoint. Define getCourseById, getCourseDetails,... methods in the service. Call this service in Endpoint class to get course details.
6. For adding security - Download spring-ws-security jar. Create an XwsSecurityInteceptor bean which has a callback handler that defines the action needed to be taken after request interception. Then add it to the list of interceptors. 
