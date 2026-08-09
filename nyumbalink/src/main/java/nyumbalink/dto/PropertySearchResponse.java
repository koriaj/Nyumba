package nyumbalink.dto;


import java.util.List;

public class PropertySearchResponse {

    private Long id;
    private String name;
    private String description;
    private String county;
    private String town;
    private String estate;
    private String address;
    private Double latitude;
    private Double longitude;
    private List<UnitResponse> availableUnits;

    public PropertySearchResponse(
            Long id,
            String name,
            String description,
            String county,
            String town,
            String estate,
            String address,
            Double latitude,
            Double longitude,
            List<UnitResponse> availableUnits) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.county = county;
        this.town = town;
        this.estate = estate;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.availableUnits = availableUnits;
    }

    // Getters
}