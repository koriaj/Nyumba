package nyumbalink.controller;

import nyumbalink.dto.UnitRequest;
import nyumbalink.dto.UnitResponse;
import nyumbalink.service.UnitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UnitController {

    private final UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @PostMapping("/properties/{propertyId}/units")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('LANDLORD', 'AGENT')")
    public UnitResponse createUnit(
            @PathVariable Long propertyId,
            @Valid @RequestBody UnitRequest request,
            Authentication authentication) {

        return unitService.createUnit(
                propertyId,
                request,
                authentication.getName()
        );
    }

    @GetMapping("/properties/{propertyId}/units")
    public List<UnitResponse> getUnits(
            @PathVariable Long propertyId) {

        return unitService.getUnits(propertyId);
    }

    @GetMapping("/units/{unitId}")
    public UnitResponse getUnit(
            @PathVariable Long unitId) {

        return unitService.getUnit(unitId);
    }

    @PutMapping("/units/{unitId}")
    @PreAuthorize("hasAnyRole('LANDLORD', 'AGENT')")
    public UnitResponse updateUnit(
            @PathVariable Long unitId,
            @Valid @RequestBody UnitRequest request,
            Authentication authentication) {

        return unitService.updateUnit(
                unitId,
                request,
                authentication.getName()
        );
    }

    @DeleteMapping("/units/{unitId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('LANDLORD', 'AGENT')")
    public void deleteUnit(
            @PathVariable Long unitId,
            Authentication authentication) {

        unitService.deleteUnit(
                unitId,
                authentication.getName()
        );
    }
}