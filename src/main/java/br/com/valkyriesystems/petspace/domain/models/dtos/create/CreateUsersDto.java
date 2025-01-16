package br.com.valkyriesystems.petspace.domain.models.dtos.create;

import br.com.valkyriesystems.petspace.domain.models.enums.InterestedIn;
import br.com.valkyriesystems.petspace.domain.models.enums.PetAgeRange;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link br.com.valkyriesystems.petspace.domain.models.entities.Users}
 */
public record CreateUsersDto(@NotBlank String name, @NotBlank String surname, @Email String email, String password,
                             @NotNull InterestedIn interestedIn, PetAgeRange petAgeRange,
                             @NotNull CreateAddressDto location, String username) implements Serializable {
}