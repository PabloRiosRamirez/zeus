package online.grisk.zeus.controller;

import online.grisk.zeus.integration.gateway.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class MainController {
    @Autowired
    GatewayService gatewayService;

    @PostMapping(value = "/api/zeus/businessTree")
    public Map<String, Object> report(@RequestBody Map payload) {
        return gatewayService.process(MessageBuilder.withPayload(payload).build());
    }
}
