package dev.juk.echobox;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@Hidden
@RestController
public class HomeController {

	@GetMapping
	public ResponseEntity<?> home() throws URISyntaxException {
		var docs = new URI("/swagger-ui.html");
		return ResponseEntity.status(HttpStatus.FOUND).location(docs).build();
	}

	@GetMapping("ping")
	public ResponseEntity<String> ping() {
		return ResponseEntity.ok("pong");
	}

}
