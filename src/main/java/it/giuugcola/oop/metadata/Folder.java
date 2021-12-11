package it.giuugcola.oop.metadata;

public class Folder implements Metadata{

    private final String pathLower;
    private final String name;
    private final String tag;
    private final String id;

    public Folder(String pathLower, String name, String tag, String id) {
        this.pathLower = pathLower;
        this.name = name;
        this.tag = tag;
        this.id = id;
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

    @Override
    public String toString() {
        return "Folder{" +
                ", path_lower='" + pathLower + '\'' +
                ", name='" + name + '\'' +
                ", tag='" + tag + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
