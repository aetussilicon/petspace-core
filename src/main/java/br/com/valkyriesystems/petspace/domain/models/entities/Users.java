package br.com.valkyriesystems.petspace.domain.models.entities;

import br.com.valkyriesystems.petspace.domain.models.enums.InterestedIn;
import br.com.valkyriesystems.petspace.domain.models.enums.PetAgeRange;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    private UUID userId;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;
    private String mobilePhone;

    @Enumerated(EnumType.STRING)
    private InterestedIn interestedIn;

    @Enumerated(EnumType.STRING)
    private PetAgeRange petAgeRange;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "location_id")
    private Address location;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}
