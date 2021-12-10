package it.giuugcola.OOPProject.JSONManage;

public class JSONOfFolder {
    private static String path_display;
    private static String path_lower;
    private static String name;
    private static String tag;
    private static String id;

    public static String getPath_display() {
        return path_display;
    }

    public static void setPath_display(String path_display) {
        JSONOfFolder.path_display = path_display;
    }

    public static String getPath_lower() {
        return path_lower;
    }

    public static void setPath_lower(String path_lower) {
        JSONOfFolder.path_lower = path_lower;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        JSONOfFolder.name = name;
    }

    public static String getTag() {
        return tag;
    }

    public static void setTag(String tag) {
        JSONOfFolder.tag = tag;
    }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        JSONOfFolder.id = id;
    }
}
