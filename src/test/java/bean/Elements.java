package bean;

public class Elements {
    public String id;
    public String description;
    public Locators locator;

    public Locators getLocator() {
        return locator;
    }

    public void setLocator(Locators locator) {
        this.locator = locator;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
