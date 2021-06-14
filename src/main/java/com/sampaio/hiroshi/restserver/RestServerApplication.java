package com.sampaio.hiroshi.restserver;

import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

enum BananaVariety {
    Red, LadyFinger, BlueJava, Manzano, Burro, Plantain
}

@SpringBootApplication
@EnableDiscoveryClient
public class RestServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestServerApplication.class, args);
    }
}

@RestController
class BananaRestController {
    @GetMapping(path = "/getBananaVariety", produces = "application/json")
    public ResponseEntity<Banana> getBanana(@RequestParam final String variety) {
        final BananaVariety bananaVariety = BananaVariety.valueOf(variety.replace(" ", ""));
        return new ResponseEntity<>(new Banana(bananaVariety), HttpStatus.OK);
    }
}

@ControllerAdvice
class ExceptionHandlingController {
    @ExceptionHandler({Exception.class})
    public ResponseEntity<BananaErrorResponse> handleMyException() {
        return new ResponseEntity<>(new BananaErrorResponse(1, "Banana Error!"), HttpStatus.BAD_REQUEST);
    }
}

@Data
class Banana {
    private final BananaVariety variety;
    private final String whoAmI = "I'm a banana variety";
}

@Data
class BananaErrorResponse {
    private final int code;
    private final String message;
}