package it.giuugcola.OOPProject.metaData;

public class Folder {

    private final String id;
    private final String path;
    private final String name;
    private final String type;

    public Folder(String id, String name, String path, String type) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Folder{" +
                "id='" + id + '\'' +
                ", path='" + path + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
