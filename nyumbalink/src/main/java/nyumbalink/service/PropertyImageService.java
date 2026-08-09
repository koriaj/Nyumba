package nyumbalink.service;

import nyumbalink.dto.PropertyImageResponse;
import nyumbalink.entity.Property;
import nyumbalink.entity.PropertyImage;
import nyumbalink.repository.PropertyImageRepository;
import nyumbalink.repository.PropertyRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class PropertyImageService {

    private final PropertyImageRepository imageRepository;
    private final PropertyRepository propertyRepository;
    private final FileStorageService fileStorageService;

    public PropertyImageService(
            PropertyImageRepository imageRepository,
            PropertyRepository propertyRepository,
            FileStorageService fileStorageService) {

        this.imageRepository = imageRepository;
        this.propertyRepository = propertyRepository;
        this.fileStorageService = fileStorageService;
    }

    public PropertyImageResponse uploadImage(
            Long propertyId,
            MultipartFile file,
            String email) {

        Property property =
                propertyRepository
                        .findById(propertyId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Property not found"
                                ));

        // Ownership check
        if (!property.getOwner()
                .getEmail()
                .equalsIgnoreCase(email)) {

            throw new RuntimeException(
                    "You do not own this property"
            );
        }

        String storedFileName =
                fileStorageService.store(file);

        PropertyImage image =
                new PropertyImage();

        image.setFileName(
                file.getOriginalFilename()
        );

        image.setFileUrl(
                "/uploads/property-images/"
                        + storedFileName
        );

        image.setContentType(
                file.getContentType()
        );

        image.setFileSize(
                file.getSize()
        );

        image.setProperty(property);

        PropertyImage saved =
                imageRepository.save(image);

        return toResponse(saved);
    }

    public List<PropertyImageResponse> getImages(
            Long propertyId) {

        return imageRepository
                .findByPropertyId(propertyId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public void deleteImage(
            Long imageId,
            String email) {

        PropertyImage image =
                imageRepository
                        .findById(imageId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Image not found"
                                ));

        if (!image.getProperty()
                .getOwner()
                .getEmail()
                .equalsIgnoreCase(email)) {

            throw new RuntimeException(
                    "You do not own this property"
            );
        }

        fileStorageService.delete(
                extractFileName(
                        image.getFileUrl()
                )
        );

        imageRepository.delete(image);
    }

    private String extractFileName(
            String fileUrl) {

        return fileUrl.substring(
                fileUrl.lastIndexOf("/") + 1
        );
    }

    private PropertyImageResponse toResponse(
            PropertyImage image) {

        return new PropertyImageResponse(
                image.getId(),
                image.getFileName(),
                image.getFileUrl(),
                image.getContentType(),
                image.getFileSize()
        );
    }
}