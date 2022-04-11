package bean;

import java.util.List;
import java.util.Map;

public class Page {
    public List<Elements> elements;
    public Map<String, ActionsTest> mapActions;

    public Map<String, ActionsTest> getMapActions() {
        return mapActions;
    }

    public void setMapActions(Map<String, ActionsTest> mapActions) {
        this.mapActions = mapActions;
    }

    public List<Elements> getElements() {
        return elements;
    }

    public void setElements(List<Elements> elements) {
        this.elements = elements;
    }

}
