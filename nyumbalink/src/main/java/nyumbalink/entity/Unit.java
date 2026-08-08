package nyumbalink.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "units")
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String unitNumber;

    @Column(nullable = false)
    private String houseType;

    private Integer bedrooms;

    @Column(nullable = false)
    private Double monthlyRent;

    private Double deposit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnitStatus status = UnitStatus.AVAILABLE;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    // Getters and setters
}