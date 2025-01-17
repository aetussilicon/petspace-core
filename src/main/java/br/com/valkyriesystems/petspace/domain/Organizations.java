package br.com.valkyriesystems.petspace.domain;

import br.com.valkyriesystems.petspace.domain.models.entities.Address;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.br.CNPJ;

import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "organizations")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Organizations {

    @Id
    private UUID ongId;
    private String ongName;

    @CNPJ(message = "Invalid CNPJ")
    private String taxNumber;
    private String description;
    private String website;
    private String email;
    private String password;
    private String contactEmail;
    private String contactPhone;

    @ElementCollection
    @CollectionTable(name = "organization_social_media", joinColumns = @JoinColumn(name = "ong_id"))
    @MapKeyColumn(name = "platform")
    @Column(name = "handle")
    private Map<String, String> socialMediaHandles;

    private Boolean verified = false;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location")
    private Address location;
    private String workingHours;
    private String donationInfo;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

}
