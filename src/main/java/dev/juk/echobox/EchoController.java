package dev.juk.echobox;

import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("echo")
class EchoController {

	private static final Logger log = LoggerFactory.getLogger(EchoController.class);

	@Nullable
	private static HttpStatus resolveStatus(String status) {
		int statusCode = 0;
		try {
			statusCode = Integer.parseInt(status);
		} catch (NumberFormatException _) {/* ~ */}
		return HttpStatus.resolve(statusCode);
	}

	private static MediaType parseMediaType(String mediaType) {
		try {
			var mt = MediaType.valueOf(mediaType);
			if (!mt.isWildcardType() && !mt.isWildcardSubtype()) {
				return mt;
			}
		} catch (InvalidMediaTypeException e) {/* ~ */}
		return MediaType.APPLICATION_OCTET_STREAM;
	}

	@GetMapping
	ResponseEntity<?> echoGet(@RequestParam(defaultValue = "200") String status) {
		log.debug("Echo GET with status {}", status);
		return buildGetResponse(status);
	}

	@GetMapping("{status}")
	ResponseEntity<?> echoPathGet(@PathVariable String status) {
		log.debug("Echo GET on path {}", status);
		return buildGetResponse(status);
	}

	private static ResponseEntity<?> buildGetResponse(String status) {
		var httpStatus = resolveStatus(status);
		if (httpStatus == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.contentType(MediaType.TEXT_PLAIN)
					.body("Invalid status code: " + status);
		}
		return ResponseEntity.status(httpStatus).build();
	}

	@PostMapping
	ResponseEntity<?> echoPost(
			@RequestHeader(value = HttpHeaders.CONTENT_TYPE, defaultValue = MediaType.APPLICATION_JSON_VALUE) String contentType,
			@RequestParam(defaultValue = "200") String status, @RequestBody String body
	) {
		log.debug("Echo POST with status {}, content type {}, body {}", status, contentType, body);
		return buildPostResponse(status, contentType, body);
	}

	@PostMapping("{status}")
	ResponseEntity<?> echoPathPost(
			@RequestHeader(value = HttpHeaders.CONTENT_TYPE, defaultValue = MediaType.APPLICATION_JSON_VALUE) String contentType,
			@PathVariable String status, @RequestBody Object body
	) {
		log.debug("Echo POST on path {} with content type {}, body {}", status, contentType, body);
		return buildPostResponse(status, contentType, body);
	}

	private static ResponseEntity<?> buildPostResponse(String status, String contentType, Object body) {
		var httpStatus = resolveStatus(status);
		if (httpStatus == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.contentType(MediaType.TEXT_PLAIN)
					.body("Invalid status code: " + status);
		}
		return ResponseEntity.status(httpStatus).contentType(parseMediaType(contentType)).body(body);
	}

}
