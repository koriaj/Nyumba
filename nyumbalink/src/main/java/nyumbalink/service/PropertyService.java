package nyumbalink.service;

import nyumbalink.dto.PropertyRequest;
import nyumbalink.dto.PropertyResponse;
import nyumbalink.entity.Property;
import nyumbalink.entity.PropertyStatus;
import nyumbalink.entity.User;
import nyumbalink.repository.PropertyRepository;
import nyumbalink.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    public PropertyService(
            PropertyRepository propertyRepository,
            UserRepository userRepository) {

        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
    }

    public PropertyResponse createProperty(
            PropertyRequest request,
            String email) {

        User owner = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Property property = new Property();

        property.setName(request.getName());
        property.setDescription(request.getDescription());
        property.setCounty(request.getCounty());
        property.setTown(request.getTown());
        property.setEstate(request.getEstate());
        property.setAddress(request.getAddress());
        property.setLatitude(request.getLatitude());
        property.setLongitude(request.getLongitude());

        property.setOwner(owner);
        property.setStatus(PropertyStatus.DRAFT);

        Property saved =
                propertyRepository.save(property);

        return toResponse(saved);
    }

    public List<PropertyResponse> getPublishedProperties() {

        return propertyRepository
                .findByStatus(PropertyStatus.PUBLISHED)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public PropertyResponse getProperty(Long id) {

        Property property =
                propertyRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Property not found"));

        return toResponse(property);
    }

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
}
