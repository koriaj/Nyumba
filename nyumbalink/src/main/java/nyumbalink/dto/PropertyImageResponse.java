package nyumbalink.dto;


public class PropertyImageResponse {

    private Long id;
    private String fileName;
    private String fileUrl;
    private String contentType;
    private Long fileSize;

    public PropertyImageResponse(
            Long id,
            String fileName,
            String fileUrl,
            String contentType,
            Long fileSize) {

        this.id = id;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.contentType = contentType;
        this.fileSize = fileSize;
    }

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public String getContentType() {
        return contentType;
    }

    public Long getFileSize() {
        return fileSize;
    }
}