package nyumbalink.controller;

import nyumbalink.dto.PropertyImageResponse;
import nyumbalink.service.PropertyImageService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class PropertyImageController {

    private final PropertyImageService imageService;

    public PropertyImageController(
            PropertyImageService imageService) {

        this.imageService = imageService;
    }

    @PostMapping("/{propertyId}/images")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('LANDLORD', 'AGENT')")
    public PropertyImageResponse uploadImage(
            @PathVariable Long propertyId,
            @RequestParam("file")
            MultipartFile file,
            Authentication authentication) {

        return imageService.uploadImage(
                propertyId,
                file,
                authentication.getName()
        );
    }

    @GetMapping("/{propertyId}/images")
    public List<PropertyImageResponse> getImages(
            @PathVariable Long propertyId) {

        return imageService.getImages(propertyId);
    }

    @DeleteMapping("/images/{imageId}")
    @PreAuthorize("hasAnyRole('LANDLORD', 'AGENT')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImage(
            @PathVariable Long imageId,
            Authentication authentication) {

        imageService.deleteImage(
                imageId,
                authentication.getName()
        );
    }
}