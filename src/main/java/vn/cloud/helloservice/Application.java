package vn.cloud.helloservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {
    public final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
    @RestController
    class HelloController {
        private final RestTemplate restTemplate;

        HelloController(RestTemplate restTemplate) {
            this.restTemplate = restTemplate;
        }

        @GetMapping("/hello")
        public String hello() {
            LOGGER.info("Hello method started ---------------------------------");
            LOGGER.error("Hello method started ---------------------------------");
            ResponseEntity<String> responseEntity = restTemplate.postForEntity("https://httpbin.org/post", "Hello, Cloud!", String.class);
            return responseEntity.getBody();
        }

        @ExceptionHandler(value = {IllegalArgumentException.class})
        protected ResponseEntity<String> handleConflict(IllegalArgumentException exception) {
            LOGGER.error(exception.getMessage(), exception);
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
