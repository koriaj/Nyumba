package nyumbalink.dto;

public class PropertyResponse {

    private Long id;
    private String name;
    private String description;
    private String county;
    private String town;
    private String estate;
    private String address;
    private Double latitude;
    private Double longitude;
    private String status;
    private Long ownerId;

    public PropertyResponse(
            Long id,
            String name,
            String description,
            String county,
            String town,
            String estate,
            String address,
            Double latitude,
            Double longitude,
            String status,
            Long ownerId) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.county = county;
        this.town = town;
        this.estate = estate;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.ownerId = ownerId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCounty() {
        return county;
    }

    public String getTown() {
        return town;
    }

    public String getEstate() {
        return estate;
    }

    public String getAddress() {
        return address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getStatus() {
        return status;
    }

    public Long getOwnerId() {
        return ownerId;
    }
}