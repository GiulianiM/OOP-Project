package it.giuugcola.oop.jsonhandler;

public class JSONOfFolder implements JSONMetadata {
    private String pathDisplay;
    private String pathLower;
    private String name;
    private String tag;
    private String id;

    public JSONOfFolder(String pathDisplay, String pathLower, String name, String tag, String id) {
        this.pathDisplay = pathDisplay;
        this.pathLower = pathLower;
        this.name = name;
        this.tag = tag;
        this.id = id;
    }

    public JSONOfFolder() {
    }

    public String getPathDisplay() {
        return pathDisplay;
    }

    public String getPathLower() {
        return pathLower;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public String getId() {
        return id;
    }


    public void setPathDisplay(String pathDisplay) {
        this.pathDisplay = pathDisplay;
    }

    public void setPathLower(String pathLower) {
        this.pathLower = pathLower;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setId(String id) {
        this.id = id;
    }
}
