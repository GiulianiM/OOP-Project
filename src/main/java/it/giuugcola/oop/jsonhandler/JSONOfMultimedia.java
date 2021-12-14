package it.giuugcola.oop.jsonhandler;

public class JSONOfMultimedia implements JSONMetadata {
    private String pathDisplay;
    private String rev;
    private long size;
    private String serverModified;
    private String pathLower;
    private boolean isDownloadable;
    private String name;
    private String id;
    private String contentHash;
    private String clientModified;
    private String tag;
    private long width;
    private long height;


    public JSONOfMultimedia(String pathDisplay, String rev, long size, String serverModified, String pathLower,
                            boolean isDownloadable, String name, String id, String contentHash,
                            String clientModified, String tag, long width, long height) {
        this.pathDisplay = pathDisplay;
        this.rev = rev;
        this.size = size;
        this.serverModified = serverModified;
        this.pathLower = pathLower;
        this.isDownloadable = isDownloadable;
        this.name = name;
        this.id = id;
        this.contentHash = contentHash;
        this.clientModified = clientModified;
        this.tag = tag;
        this.width = width;
        this.height = height;
    }

    public JSONOfMultimedia() {
    }

    public String getPathDisplay() {
        return pathDisplay;
    }

    public String getRev() {
        return rev;
    }

    public long getSize() {
        return size;
    }

    public String getServerModified() {
        return serverModified;
    }

    public String getPathLower() {
        return pathLower;
    }

    public boolean IsDownloadable() {
        return isDownloadable;
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

    public String getContentHash() {
        return contentHash;
    }

    public String getClientModified() {
        return clientModified;
    }

    public Long getWidth() {
        return width;
    }

    public Long getHeight() {
        return height;
    }

    public void setPathDisplay(String pathDisplay) {
        this.pathDisplay = pathDisplay;
    }

    public void setRev(String rev) {
        this.rev = rev;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setServerModified(String serverModified) {
        this.serverModified = serverModified;
    }

    public void setPathLower(String pathLower) {
        this.pathLower = pathLower;
    }

    public void setDownloadable(boolean downloadable) {
        isDownloadable = downloadable;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setContentHash(String contentHash) {
        this.contentHash = contentHash;
    }

    public void setClientModified(String clientModified) {
        this.clientModified = clientModified;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    public void setHeight(Long height) {
        this.height = height;
    }
}
