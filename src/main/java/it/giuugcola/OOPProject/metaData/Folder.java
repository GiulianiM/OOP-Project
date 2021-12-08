package it.giuugcola.OOPProject.metaData;

public class Folder {

    private final String path_lower;
    private final String name;
    private final String tag;
    private final String id;

    public Folder(String path_lower, String name, String tag, String id) {
        this.path_lower = path_lower;
        this.name = name;
        this.tag = tag;
        this.id = id;
    }

    public String getPath_lower() {
        return path_lower;
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
                ", path_lower='" + path_lower + '\'' +
                ", name='" + name + '\'' +
                ", tag='" + tag + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
