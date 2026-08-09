package nyumbalink.repository;

import nyumbalink.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Query("""
        SELECT DISTINCT p
        FROM Property p
        LEFT JOIN p.units u
        WHERE
            (:keyword IS NULL OR
                LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')))
            AND (:county IS NULL OR LOWER(p.county) = LOWER(:county))
            AND (:town IS NULL OR LOWER(p.town) = LOWER(:town))
            AND (:minRent IS NULL OR u.monthlyRent >= :minRent)
            AND (:maxRent IS NULL OR u.monthlyRent <= :maxRent)
        """)
    List<Property> searchProperties(
            @Param("keyword") String keyword,
            @Param("county") String county,
            @Param("town") String town,
            @Param("minRent") Double minRent,
            @Param("maxRent") Double maxRent
    );
}