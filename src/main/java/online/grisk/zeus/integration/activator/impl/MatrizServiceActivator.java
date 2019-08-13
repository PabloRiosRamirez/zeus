package online.grisk.zeus.integration.activator.impl;

import online.grisk.zeus.domain.service.TreeService;
import online.grisk.zeus.integration.activator.Activator;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class MatrizServiceActivator implements Activator {

    public Map<String, Object> invoke(@Payload Map<String, Object> payload, @Headers Map<String, Object> header) {
        Map<String, Object> businessTree = ((Map)payload.get("businessTree"));
        TreeService treeService = new TreeService();
        treeService.populateTree(businessTree);
        List values = new ArrayList();
        values.add(treeService.executeFlowTree());
        businessTree.put("values", values);
        businessTree.put("listNames", treeService.getListNode());
        return payload;
    }
}
