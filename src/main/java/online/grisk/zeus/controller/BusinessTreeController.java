package online.grisk.zeus.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
		Map<String, Object> attribute = new HashMap<>();
		for (int i = 0; i < new Random().nextInt(35) + 20; i++) {
			attribute.put("variable_" + i, new Random().nextInt(35000) + 1 + "");
		}
		((Map) payload.get("businessTree")).put("values", attribute);

		ObjectMapper mapper = new ObjectMapper();
		
		Map<String, Object> jsonMap = mapper.convertValue(payload, Map.class);
		
		return new ResponseEntity<Map<String, Object>>(jsonMap, HttpStatus.OK);
	}
}
