package nyumbalink.repository;


import nyumbalink.entity.Unit;
import nyumbalink.entity.UnitStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnitRepository
        extends JpaRepository<Unit, Long> {

    List<Unit> findByPropertyId(Long propertyId);

    List<Unit> findByStatus(UnitStatus status);

    boolean existsByPropertyIdAndUnitNumber(
            Long propertyId,
            String unitNumber
    );
}