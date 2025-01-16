package br.com.valkyriesystems.petspace.domain.models.dtos.response;

import br.com.valkyriesystems.petspace.domain.models.enums.InterestedIn;
import br.com.valkyriesystems.petspace.domain.models.enums.PetAgeRange;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * DTO for {@link br.com.valkyriesystems.petspace.domain.models.entities.Users}
 */
public record UsersDto(UUID userId, String name, String surname, String email, String password, String mobilePhone,
                       InterestedIn interestedIn, PetAgeRange petAgeRange, AddressDto location, Timestamp createdAt,
                       Timestamp updatedAt, String username) implements Serializable {
}