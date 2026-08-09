package nyumbalink.service;

import nyumbalink.dto.PropertyRequest;
import nyumbalink.dto.PropertyResponse;
import nyumbalink.entity.Property;
import nyumbalink.entity.PropertyStatus;
import nyumbalink.entity.Unit;
import nyumbalink.entity.UnitStatus;
import nyumbalink.exception.ForbiddenException;
import nyumbalink.exception.ResourceNotFoundException;
import nyumbalink.repository.PropertyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    // =========================================================
    // CREATE PROPERTY
    // =========================================================

    public PropertyResponse createProperty(
            PropertyRequest request,
            String email) {

        Property property = new Property();

        property.setName(request.getName());
        property.setDescription(request.getDescription());
        property.setCounty(request.getCounty());
        property.setTown(request.getTown());
        property.setEstate(request.getEstate());
        property.setAddress(request.getAddress());
        property.setLatitude(request.getLatitude());
        property.setLongitude(request.getLongitude());

        /*
         * IMPORTANT:
         * This assumes your Property entity has a method
         * such as setOwnerByEmail() OR that your existing
         * code already handles assigning the logged-in user.
         *
         * If your Property.owner is a User entity, you should
         * retrieve the User from UserRepository instead.
         */

        property.setStatus(PropertyStatus.DRAFT);

        Property saved =
                propertyRepository.save(property);

        return toResponse(saved);
    }


    // =========================================================
    // GET ALL PROPERTIES
    // =========================================================

    @Transactional(readOnly = true)
    public List<PropertyResponse> getProperties() {

        return propertyRepository
                .findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }


    // =========================================================
    // GET PROPERTY BY ID
    // =========================================================

    @Transactional(readOnly = true)
    public PropertyResponse getProperty(Long propertyId) {

        Property property =
                propertyRepository
                        .findById(propertyId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Property not found"
                                ));

        return toResponse(property);
    }


    // =========================================================
    // UPDATE PROPERTY
    // =========================================================

    public PropertyResponse updateProperty(
            Long propertyId,
            PropertyRequest request,
            String email) {

        Property property =
                propertyRepository
                        .findById(propertyId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Property not found"
                                ));

        // Ownership check
        verifyOwnership(property, email);

        property.setName(request.getName());
        property.setDescription(request.getDescription());
        property.setCounty(request.getCounty());
        property.setTown(request.getTown());
        property.setEstate(request.getEstate());
        property.setAddress(request.getAddress());
        property.setLatitude(request.getLatitude());
        property.setLongitude(request.getLongitude());

        Property saved =
                propertyRepository.save(property);

        return toResponse(saved);
    }


    // =========================================================
    // DELETE PROPERTY
    // =========================================================

    public void deleteProperty(
            Long propertyId,
            String email) {

        Property property =
                propertyRepository
                        .findById(propertyId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Property not found"
                                ));

        // Ownership check
        verifyOwnership(property, email);

        propertyRepository.delete(property);
    }


    // =========================================================
    // SUBMIT PROPERTY FOR APPROVAL
    // =========================================================

    public PropertyResponse submitForApproval(
            Long propertyId,
            String email) {

        Property property =
                propertyRepository
                        .findById(propertyId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Property not found"
                                ));

        // Make sure landlord/agent owns the property
        verifyOwnership(property, email);

        // Property must have at least one unit
        if (property.getUnits() == null
                || property.getUnits().isEmpty()) {

            throw new IllegalStateException(
                    "Property must have at least one unit"
            );
        }

        // Only DRAFT properties can be submitted
        if (property.getStatus()
                != PropertyStatus.DRAFT) {

            throw new IllegalStateException(
                    "Only draft properties can be submitted"
            );
        }

        property.setStatus(
                PropertyStatus.PENDING_APPROVAL
        );

        Property saved =
                propertyRepository.save(property);

        return toResponse(saved);
    }


    // =========================================================
    // ADMIN APPROVE PROPERTY
    // =========================================================

    public PropertyResponse approveProperty(
            Long propertyId) {

        Property property =
                propertyRepository
                        .findById(propertyId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Property not found"
                                ));

        // Property must be waiting for approval
        if (property.getStatus()
                != PropertyStatus.PENDING_APPROVAL) {

            throw new IllegalStateException(
                    "Property is not pending approval"
            );
        }

        // Make property visible to tenants
        property.setStatus(
                PropertyStatus.PUBLISHED
        );

        Property saved =
                propertyRepository.save(property);

        return toResponse(saved);
    }


    // =========================================================
    // VERIFY PROPERTY OWNERSHIP
    // =========================================================

    private void verifyOwnership(
            Property property,
            String email) {

        if (property.getOwner() == null) {

            throw new ForbiddenException(
                    "Property has no owner"
            );
        }

        if (property.getOwner()
                .getEmail() == null) {

            throw new ForbiddenException(
                    "Property owner has no email"
            );
        }

        if (!property.getOwner()
                .getEmail()
                .equalsIgnoreCase(email)) {

            throw new ForbiddenException(
                    "You do not own this property"
            );
        }
    }


    // =========================================================
    // CONVERT ENTITY TO RESPONSE
    // =========================================================

    private PropertyResponse toResponse(
            Property property) {

        return new PropertyResponse(
                property.getId(),
                property.getName(),
                property.getDescription(),
                property.getCounty(),
                property.getTown(),
                property.getEstate(),
                property.getAddress(),
                property.getLatitude(),
                property.getLongitude(),
                property.getStatus().name(),
                property.getOwner().getId()
        );
    }
    public List<PropertyResponse> searchProperties(
            String keyword,
            String county,
            String town,
            Double minRent,
            Double maxRent) {

        List<Property> properties = propertyRepository.searchProperties(
                keyword,
                county,
                town,
                minRent,
                maxRent
        );

        return properties.stream()
                .map(this::mapToResponse)
                .toList();
    }
    private PropertyResponse mapToResponse(Property property) {

        return new PropertyResponse(
                property.getId(),
                property.getName(),
                property.getDescription(),
                property.getCounty(),
                property.getTown(),
                property.getEstate(),
                property.getAddress(),
                property.getLatitude(),
                property.getLongitude(),
                property.getStatus() != null
                        ? property.getStatus().name()
                        : null,
                property.getOwner() != null
                        ? property.getOwner().getId()
                        : null
        );
    }
}