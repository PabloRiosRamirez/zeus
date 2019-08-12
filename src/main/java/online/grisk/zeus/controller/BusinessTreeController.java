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
	public ResponseEntity<?> businessTree(@RequestBody Map payload) throws IOException {
		Map<String, Object> attribute = new HashMap<>();
		for (int i = 0; i < new Random().nextInt(35) + 20; i++) {
			attribute.put("nivel_" + i, new Random().nextInt(35000) + 1 + "");
		}
		HashMap<String, Object> tree = new HashMap<>();
		tree.put("nivels", attribute);
		HashMap output= new HashMap();
		output.put("color", "#A32234");
		output.put("label", "Rechazado");
		tree.put("output", output);
		((Map) payload.get("businessTree")).put("values", tree);

		
		return new ResponseEntity<Map<String, Object>>(payload, HttpStatus.OK);
	}
}
