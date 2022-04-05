package bean;

import java.util.List;
import java.util.Map;

public class Page {
    public List<Elements> elements;
    public Map<String,Actions> mapActions;

    public Map<String, Actions> getMapActions() {
        return mapActions;
    }

    public void setMapActions(Map<String, Actions> mapActions) {
        this.mapActions = mapActions;
    }

    public List<Elements> getElements() {
        return elements;
    }

    public void setElements(List<Elements> elements) {
        this.elements = elements;
    }

}
