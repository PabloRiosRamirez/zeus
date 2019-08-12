package online.grisk.zeus.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class BusinessTreeController {

	@PostMapping(value = "/api/zeus/businessTree")
	public ResponseEntity<?> businessTree(@RequestBody JsonNode payload) throws IOException {

		ObjectMapper mapper = new ObjectMapper();
		
		Map<String, Object> jsonMap = mapper.convertValue(payload, Map.class);
		
		return new ResponseEntity<Map<String, Object>>(jsonMap, HttpStatus.OK);
	}
}
