package nyumbalink.controller;

import nyumbalink.dto.PropertyResponse;
import nyumbalink.service.PropertyService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/properties")
public class AdminPropertyController {

    private final PropertyService propertyService;

    public AdminPropertyController(
            PropertyService propertyService) {

        this.propertyService = propertyService;
    }

    @PostMapping("/{propertyId}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public PropertyResponse approveProperty(
            @PathVariable Long propertyId) {

        return propertyService.approveProperty(
                propertyId
        );
    }
}