package bean;

import java.util.List;

public class Actions {
    public String action_id;
    public String description;
    public List<ActionElements> list;

    public String getAction_id() {
        return action_id;
    }

    public void setAction_id(String action_id) {
        this.action_id = action_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ActionElements> getList() {
        return list;
    }

    public void setList(List<ActionElements> list) {
        this.list = list;
    }
}
