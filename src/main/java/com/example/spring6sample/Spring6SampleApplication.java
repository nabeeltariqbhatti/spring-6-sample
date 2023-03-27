package com.example.spring6sample;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@SpringBootApplication
public class Spring6SampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spring6SampleApplication.class, args);
    }

    @RestController
    public class GreetingController {

        @GetMapping("/greetings/{name}")
        public ResponseEntity<Greeting> greetings(@PathVariable("name") String name) throws IllegalAccessException {
            if (!StringUtils.hasText(name) || !Character.isUpperCase(name.charAt(0))) {
                throw new IllegalArgumentException("name must is not starting with capital letter ");
            }
            return ResponseEntity.ok(new Greeting("Hello " + name + "!"));
        }
    }

    public record Greeting(String name) {
    }


    @ControllerAdvice
    public class GreetingControllerAdvice {


        @ExceptionHandler
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ResponseEntity<ProblemDetail> handleBadRequest(IllegalArgumentException ex, HttpServletRequest httpServletRequest) {
            httpServletRequest.getAttributeNames()
                    .asIterator().forEachRemaining(att -> System.out.println("attribute name is " + att));
            return ResponseEntity.of(Optional.of(
                    ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(402), ex.getLocalizedMessage()
                    )));
        }
    }

}
