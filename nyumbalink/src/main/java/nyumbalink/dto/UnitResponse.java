package nyumbalink.dto;

import nyumbalink.entity.HouseType;

public class UnitResponse {

    private Long id;
    private Long propertyId;
    private String unitNumber;
    private HouseType houseType;
    private Integer bedrooms;
    private Double monthlyRent;
    private Double deposit;
    private String status;

    public UnitResponse(
            Long id,
            Long propertyId,
            String unitNumber,
            HouseType houseType,
            Integer bedrooms,
            Double monthlyRent,
            Double deposit,
            String status) {

        this.id = id;
        this.propertyId = propertyId;
        this.unitNumber = unitNumber;
        this.houseType = houseType;
        this.bedrooms = bedrooms;
        this.monthlyRent = monthlyRent;
        this.deposit = deposit;
        this.status = status;
    }

    // Getters
}