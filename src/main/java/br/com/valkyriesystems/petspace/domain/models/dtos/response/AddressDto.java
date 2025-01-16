package br.com.valkyriesystems.petspace.domain.models.dtos.response;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DTO for {@link br.com.valkyriesystems.petspace.domain.models.entities.Address}
 */
public record AddressDto(Long locationId, String country, String city, String state, String district, String street,
                         String number, String complement, String zip, Timestamp createdAt,
                         Timestamp updatedAt) implements Serializable {
}