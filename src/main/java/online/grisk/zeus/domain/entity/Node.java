package online.grisk.zeus.domain.entity;

public class Node {

    private boolean query;
    private boolean output;
    private String color;
    private String expression;
    private String label;
    private Node childrenNegation;
    private Node childrenAfirmation;

    public Node(boolean query, String expression, boolean output) {
        this.query = query;
        this.output = output;
        this.expression = expression;
    }

    public Node(boolean output, String color, String label) {
        this.output = output;
        this.color = color;
        this.label = label;
    }

    public Node(boolean query, boolean output, String color, String label, Node childrenNegation, Node childrenAfirmation) {
        this.query = query;
        this.output = output;
        this.color = color;
        this.label = label;
        this.childrenNegation = childrenNegation;
        this.childrenAfirmation = childrenAfirmation;
    }

    public boolean isQuery() {
        return query;
    }

    public void setQuery(boolean query) {
        this.query = query;
    }

    public boolean isOutput() {
        return output;
    }

    public void setOutput(boolean output) {
        this.output = output;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Node getChildrenNegation() {
        return childrenNegation;
    }

    public void setChildrenNegation(Node childrenNegation) {
        this.childrenNegation = childrenNegation;
    }

    public Node getChildrenAfirmation() {
        return childrenAfirmation;
    }

    public void setChildrenAfirmation(Node childrenAfirmation) {
        this.childrenAfirmation = childrenAfirmation;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Node executeFlowTree(){
        if(output == true){
            return this;
        }
        if(query){
            return this.childrenAfirmation.executeFlowTree();
        }else{
            return this.childrenNegation.executeFlowTree();
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "query=" + query +
                ", output=" + output +
                ", color='" + color + '\'' +
                ", label='" + label + '\'' +
                ", childrenNegation=" + childrenNegation +
                ", childrenAfirmation=" + childrenAfirmation +
                '}';
    }
}
