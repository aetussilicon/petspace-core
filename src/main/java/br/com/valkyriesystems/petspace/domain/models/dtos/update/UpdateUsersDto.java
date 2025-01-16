package br.com.valkyriesystems.petspace.domain.models.dtos.update;

import br.com.valkyriesystems.petspace.domain.models.enums.InterestedIn;
import br.com.valkyriesystems.petspace.domain.models.enums.PetAgeRange;
import jakarta.validation.constraints.Email;

import java.io.Serializable;

/**
 * DTO for {@link br.com.valkyriesystems.petspace.domain.models.entities.Users}
 */
public record UpdateUsersDto(String name, String surname, @Email String email, String password, String mobilePhone,
                             InterestedIn interestedIn, PetAgeRange petAgeRange,
                             UpdateAddressDto location, String username) implements Serializable {
}