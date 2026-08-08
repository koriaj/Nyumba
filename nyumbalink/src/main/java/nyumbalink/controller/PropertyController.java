package nyumbalink.controller;

import nyumbalink.dto.PropertyRequest;
import nyumbalink.dto.PropertyResponse;
import nyumbalink.service.PropertyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(
            PropertyService propertyService) {

        this.propertyService = propertyService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('LANDLORD', 'AGENT')")
    public PropertyResponse createProperty(
            @Valid @RequestBody PropertyRequest request,
            Authentication authentication) {

        return propertyService.createProperty(
                request,
                authentication.getName()
        );
    }

    @GetMapping
    public List<PropertyResponse> getProperties() {

        return propertyService
                .getPublishedProperties();
    }

    @GetMapping("/{id}")
    public PropertyResponse getProperty(
            @PathVariable Long id) {

        return propertyService.getProperty(id);
    }
}