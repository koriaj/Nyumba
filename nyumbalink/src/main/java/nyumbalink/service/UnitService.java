package nyumbalink.service;

import nyumbalink.dto.UnitRequest;
import nyumbalink.dto.UnitResponse;
import nyumbalink.entity.Property;
import nyumbalink.entity.Unit;
import nyumbalink.repository.PropertyRepository;
import nyumbalink.repository.UnitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitService {

    private final UnitRepository unitRepository;
    private final PropertyRepository propertyRepository;

    public UnitService(
            UnitRepository unitRepository,
            PropertyRepository propertyRepository) {

        this.unitRepository = unitRepository;
        this.propertyRepository = propertyRepository;
    }

    public UnitResponse createUnit(
            Long propertyId,
            UnitRequest request,
            String email) {

        Property property =
                getPropertyOwnedByUser(propertyId, email);

        if (unitRepository
                .existsByPropertyIdAndUnitNumber(
                        propertyId,
                        request.getUnitNumber())) {

            throw new RuntimeException(
                    "Unit number already exists"
            );
        }

        Unit unit = new Unit();

        unit.setUnitNumber(
                request.getUnitNumber()
        );

        unit.setHouseType(
                request.getHouseType()
        );

        unit.setBedrooms(
                request.getBedrooms()
        );

        unit.setMonthlyRent(
                request.getMonthlyRent()
        );

        unit.setDeposit(
                request.getDeposit()
        );

        unit.setProperty(property);

        Unit saved = unitRepository.save(unit);

        return toResponse(saved);
    }

    public List<UnitResponse> getUnits(
            Long propertyId) {

        return unitRepository
                .findByPropertyId(propertyId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public UnitResponse getUnit(Long unitId) {

        Unit unit = unitRepository
                .findById(unitId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Unit not found"
                        ));

        return toResponse(unit);
    }

    public UnitResponse updateUnit(
            Long unitId,
            UnitRequest request,
            String email) {

        Unit unit = unitRepository
                .findById(unitId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Unit not found"
                        ));

        verifyOwnership(
                unit.getProperty(),
                email
        );

        unit.setUnitNumber(
                request.getUnitNumber()
        );

        unit.setHouseType(
                request.getHouseType()
        );

        unit.setBedrooms(
                request.getBedrooms()
        );

        unit.setMonthlyRent(
                request.getMonthlyRent()
        );

        unit.setDeposit(
                request.getDeposit()
        );

        Unit updated =
                unitRepository.save(unit);

        return toResponse(updated);
    }

    public void deleteUnit(
            Long unitId,
            String email) {

        Unit unit = unitRepository
                .findById(unitId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Unit not found"
                        ));

        verifyOwnership(
                unit.getProperty(),
                email
        );

        unitRepository.delete(unit);
    }

    private Property getPropertyOwnedByUser(
            Long propertyId,
            String email) {

        Property property =
                propertyRepository
                        .findById(propertyId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Property not found"
                                ));

        verifyOwnership(property, email);

        return property;
    }

    private void verifyOwnership(
            Property property,
            String email) {

        if (!property.getOwner()
                .getEmail()
                .equalsIgnoreCase(email)) {

            throw new RuntimeException(
                    "You do not have permission to manage this property"
            );
        }
    }

    private UnitResponse toResponse(Unit unit) {

        return new UnitResponse(
                unit.getId(),
                unit.getProperty().getId(),
                unit.getUnitNumber(),
                unit.getHouseType(),
                unit.getBedrooms(),
                unit.getMonthlyRent(),
                unit.getDeposit(),
                unit.getStatus().name()
        );
    }
}
