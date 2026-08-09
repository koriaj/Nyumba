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

    @PutMapping("/{propertyId}")
    @PreAuthorize("hasAnyRole('LANDLORD', 'AGENT')")
    public PropertyResponse updateProperty(
            @PathVariable Long propertyId,
            @Valid @RequestBody PropertyRequest request,
            Authentication authentication) {

        return propertyService.updateProperty(
                propertyId,
                request,
                authentication.getName()
        );
    }
    @DeleteMapping("/{propertyId}")
    @PreAuthorize("hasAnyRole('LANDLORD', 'AGENT')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProperty(
            @PathVariable Long propertyId,
            Authentication authentication) {

        propertyService.deleteProperty(
                propertyId,
                authentication.getName()
        );
    }

/*
    @GetMapping
    public List<PropertyResponse> getProperties() {

        return propertyService
                .getPublishedProperties();
    }*/
@GetMapping
public List<PropertyResponse> searchProperties(

        @RequestParam(required = false)
        String county,

        @RequestParam(required = false)
        String town,

        @RequestParam(required = false)
        String houseType,

        @RequestParam(required = false)
        Double minRent,

        @RequestParam(required = false)
        Double maxRent) {

    return propertyService.searchProperties(
            county,
            town,
            houseType,
            minRent,
            maxRent
    );
}

    @GetMapping("/{id}")
    public PropertyResponse getProperty(
            @PathVariable Long id) {

        return propertyService.getProperty(id);
    }
    @PostMapping("/{propertyId}/submit")
    @PreAuthorize("hasAnyRole('LANDLORD', 'AGENT')")
    public PropertyResponse submitForApproval(
            @PathVariable Long propertyId,
            Authentication authentication) {

        return propertyService.submitForApproval(
                propertyId,
                authentication.getName()
        );
    }
    @PostMapping("/admin/properties/{propertyId}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public PropertyResponse approveProperty(
            @PathVariable Long propertyId) {

        return propertyService.approveProperty(propertyId);
    }
}