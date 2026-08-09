package nyumbalink.dto;

import lombok.Getter;
import lombok.Setter;
import nyumbalink.entity.HouseType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Setter
@Getter
public class UnitRequest {

    @NotBlank
    private String unitNumber;

    @NotNull
    private HouseType houseType;

    @NotNull
    @PositiveOrZero
    private Integer bedrooms;

    @NotNull
    @PositiveOrZero
    private Double monthlyRent;

    @PositiveOrZero
    private Double deposit;

    // Getters and setters

}