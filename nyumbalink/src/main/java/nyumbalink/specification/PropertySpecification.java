package nyumbalink.specification;

import nyumbalink.entity.HouseType;
import nyumbalink.entity.Property;
import nyumbalink.entity.PropertyStatus;
import nyumbalink.entity.Unit;
import nyumbalink.entity.UnitStatus;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PropertySpecification {

    public static Specification<Property> search(
            String county,
            String town,
            String houseType,
            Double minRent,
            Double maxRent) {

        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates =
                    new ArrayList<>();

            // Only published properties
            predicates.add(
                    criteriaBuilder.equal(
                            root.get("status"),
                            PropertyStatus.PUBLISHED
                    )
            );

            // Join Property -> Unit
            Join<Property, Unit> unit =
                    root.join(
                            "units",
                            JoinType.INNER
                    );

            // Only available units
            predicates.add(
                    criteriaBuilder.equal(
                            unit.get("status"),
                            UnitStatus.AVAILABLE
                    )
            );

            // County
            if (county != null && !county.isBlank()) {

                predicates.add(
                        criteriaBuilder.equal(
                                criteriaBuilder.lower(
                                        root.get("county")
                                ),
                                county.toLowerCase()
                        )
                );
            }

            // Town
            if (town != null && !town.isBlank()) {

                predicates.add(
                        criteriaBuilder.equal(
                                criteriaBuilder.lower(
                                        root.get("town")
                                ),
                                town.toLowerCase()
                        )
                );
            }

            // House type
            if (houseType != null
                    && !houseType.isBlank()) {

                HouseType type;

                try {
                    type = HouseType.valueOf(
                            houseType.toUpperCase()
                    );
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException(
                            "Invalid house type: "
                                    + houseType
                    );
                }

                predicates.add(
                        criteriaBuilder.equal(
                                unit.get("houseType"),
                                type
                        )
                );
            }

            // Minimum rent
            if (minRent != null) {

                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(
                                unit.get("monthlyRent"),
                                minRent
                        )
                );
            }

            // Maximum rent
            if (maxRent != null) {

                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(
                                unit.get("monthlyRent"),
                                maxRent
                        )
                );
            }

            query.distinct(true);

            return criteriaBuilder.and(
                    predicates.toArray(
                            new Predicate[0]
                    )
            );
        };
    }
}