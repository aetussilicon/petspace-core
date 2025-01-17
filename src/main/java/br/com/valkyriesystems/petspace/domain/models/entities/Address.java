package br.com.valkyriesystems.petspace.domain.models.entities;

import br.com.valkyriesystems.petspace.domain.ports.interfaces.ResponseType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "address")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Address implements ResponseType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq")
    @SequenceGenerator(name = "address_seq", sequenceName = "address_seq", allocationSize = 1)
    private Long locationId;

    private String country;
    private String city;
    private String state;
    private String district;
    private String street;
    private String number;
    private String complement;
    private String zip;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}