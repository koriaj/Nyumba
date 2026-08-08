package nyumbalink.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(nullable = false)
    private String description;

    @Setter
    @Column(nullable = false)
    private String county;

    @Setter
    @Column(nullable = false)
    private String town;

    @Setter
    private String estate;

    @Setter
    private String address;

    @Setter
    private Double latitude;

    @Setter
    private Double longitude;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PropertyStatus status;

    @Setter
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

}