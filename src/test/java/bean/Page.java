package bean;

import java.util.List;

public class Page {
    public List<Elements> elements;
    public List<Actions> actions;

    public List<Elements> getElements() {
        return elements;
    }

    public void setElements(List<Elements> elements) {
        this.elements = elements;
    }

    public List<Actions> getActions() {
        return actions;
    }

    public void setActions(List<Actions> actions) {
        this.actions = actions;
    }
}
