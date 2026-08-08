package nyumbalink.repository;

import nyumbalink.entity.Property;
import nyumbalink.entity.PropertyStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRepository
        extends JpaRepository<Property, Long> {

    List<Property> findByStatus(PropertyStatus status);

    List<Property> findByCountyIgnoreCase(String county);

    List<Property> findByTownIgnoreCase(String town);

    List<Property> findByOwnerId(Long ownerId);
}