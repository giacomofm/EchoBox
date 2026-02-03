package dev.juk.echobox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("echo")
class EchoController {

	private static final Logger log = LoggerFactory.getLogger(EchoController.class);

	@GetMapping
	ResponseEntity<String> echoGet(@RequestParam(defaultValue = "200") int status) {
		log.debug("Echo GET with status {}", status);
		return ResponseEntity.status(status).build();
	}

	@GetMapping("{status}")
	ResponseEntity<Void> echoPathGet(@PathVariable int status) {
		log.debug("Echo GET on path {}", status);
		return ResponseEntity.status(status).build();
	}

	@PostMapping
	ResponseEntity<String> echoPost(
			@RequestHeader(value = HttpHeaders.CONTENT_TYPE, defaultValue = MediaType.APPLICATION_JSON_VALUE) String contentType,
			@RequestParam(defaultValue = "200") int status, @RequestBody String body
	) {
		log.debug("Echo POST with status {}, content type {}, body {}", status, contentType, body);
		return ResponseEntity.status(status).contentType(parseMediaType(contentType)).body(body);
	}

	private static MediaType parseMediaType(String mt) {
		try {
			var mediaType = MediaType.valueOf(mt);
			if (mediaType.isWildcardType() || mediaType.isWildcardSubtype())
				mediaType = MediaType.APPLICATION_OCTET_STREAM;
			return mediaType;
		} catch (InvalidMediaTypeException e) {
			return MediaType.APPLICATION_OCTET_STREAM;
		}
	}

}
