package nyumbalink.repository;

import nyumbalink.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnitRepository
        extends JpaRepository<Unit, Long> {

    List<Unit> findByPropertyId(Long propertyId);

    List<Unit> findByStatus(
            nyumbalink.entity.UnitStatus status);
}