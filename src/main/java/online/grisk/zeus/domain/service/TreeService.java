package online.grisk.zeus.domain.service;

import online.grisk.zeus.domain.entity.Node;
import online.grisk.zeus.integration.utils.MathOperator;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TreeService {

    private Node node;
    private List listNode;
    private int cantOutput;
    private int cantLeaves;

    public void populateTree(Map payload, Map riskTree) {
        List<Map<String, Object>> variables = (List<Map<String, Object>>) ((Map) payload.get("dataintegration")).get("values");
        List<Map<String, Object>> nodeCollection = (List) ((Map) riskTree.get("configuration")).get("businessTreeNodeCollection");

        Map<String, Node> nodes = new HashMap<>();
        for (Map<String, Object> node : nodeCollection) {
            boolean isOutput = (boolean) node.getOrDefault("output", false);
            if (isOutput) {
                nodes.put(node.getOrDefault("idBusinessTreeNode", "default").toString(),
                        new Node(
                                isOutput,
                                node.getOrDefault("color", "#FFFFF").toString(),
                                node.getOrDefault("labelOutput", "").toString()));
            } else {
                String expression = node.getOrDefault("expression", "").toString();
                Pattern p = Pattern.compile(Pattern.quote("{") + "(.*?)" + Pattern.quote("}"));
                Matcher m = p.matcher(expression);
                String variableEnOperacion = null;
                while (m.find()) {
                    variableEnOperacion = m.group(1);
                }

                String valorVariable = null;
                for (Map variable : variables) {
                    if (variable.get("code").toString().equalsIgnoreCase(variableEnOperacion)) {
                        valorVariable = variable.get("value").toString();
                    }
                }
                expression = expression.replace(variableEnOperacion, valorVariable).replace("{", "").replace("}", "").replaceAll(" ", "");
                nodes.put(node.getOrDefault("idBusinessTreeNode", "default").toString(),
                        new Node(MathOperator.orquestadorOperacion(expression) == 0 ? false : true, expression, false));
            }

        }

        for (int i = nodeCollection.size() - 1; i >= 0; i--) {
            Map<String, Object> nodeMap = nodeCollection.get(i);
            boolean isOutput = (boolean) nodeMap.getOrDefault("output", false);
            if (!isOutput) {
                Node node = nodes.get(nodeMap.getOrDefault("idBusinessTreeNode", "default").toString());
                node.setChildrenNegation(nodes.get(nodeMap.getOrDefault("childrenNegation", "default").toString()));
                node.setChildrenAffirmation(nodes.get(nodeMap.getOrDefault("childrenAffirmation", "default").toString()));
            }
        }
        List<List> nameNodes = new ArrayList();
        for (Node node : nodes.values()) {

            if (node.getChildrenNegation() != null) {
                List nameInterNodes = new ArrayList();
                if (node.isOutput()) {
                    nameInterNodes.add(node.getLabel());
                } else {
                    nameInterNodes.add(node.getExpression());
                }
                if (node.getChildrenNegation().isOutput()) {
                    nameInterNodes.add(node.getChildrenNegation().getLabel());
                } else {
                    nameInterNodes.add(node.getChildrenNegation().getExpression());
                }
                nameNodes.add(nameInterNodes);
            }
            if (node.getChildrenAffirmation() != null) {
                List nameInterNodes = new ArrayList();
                if (node.isOutput()) {
                    nameInterNodes.add(node.getLabel());
                } else {
                    nameInterNodes.add(node.getExpression());
                }
                if (node.getChildrenAffirmation().isOutput()) {
                    nameInterNodes.add(node.getChildrenAffirmation().getLabel());
                } else {
                    nameInterNodes.add(node.getChildrenAffirmation().getExpression());
                }
                nameNodes.add(nameInterNodes);
            }

        }
        this.listNode = nameNodes;
        this.node = nodes.get(nodeCollection.get(0).getOrDefault("idBusinessTreeNode", "default").toString());
    }

    private void countNodes(Node reco) {
        if (reco != null) {
            if (reco.getChildrenAffirmation() == null && reco.getChildrenNegation() == null) {
                cantLeaves++;
                if (reco.isOutput()) {
                    cantOutput++;
                }
            }
            countNodes(reco.getChildrenAffirmation());
            countNodes(reco.getChildrenNegation());
        }
    }

    private void countNodesLeaves() {
        cantLeaves = 0;
        cantOutput = 0;
        countNodes(node);
    }

    public Node executeFlowTree() {
        return node.executeFlowTree();
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public int getCantOutput() {
        return cantOutput;
    }

    public void setCantOutput(int cantOutput) {
        this.cantOutput = cantOutput;
    }

    public int getCantLeaves() {
        return cantLeaves;
    }

    public void setCantLeaves(int cantLeaves) {
        this.cantLeaves = cantLeaves;
    }

    public List getListNode() {
        return listNode;
    }

    public void setListNode(List listNode) {
        this.listNode = listNode;
    }
}
