package nyumbalink.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "units")
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String unitNumber;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HouseType houseType;

    @Setter
    @Column(nullable = false)
    private Integer bedrooms;

    @Setter
    @Column(nullable = false)
    private Double monthlyRent;

    @Setter
    private Double deposit;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnitStatus status = UnitStatus.AVAILABLE;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

}