package nyumbalink.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PropertyRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String county;

    @NotBlank
    private String town;

    private String estate;

    private String address;

    private Double latitude;

    private Double longitude;

    // Getters and setters

}