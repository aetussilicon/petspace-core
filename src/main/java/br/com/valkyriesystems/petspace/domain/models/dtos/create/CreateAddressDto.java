package br.com.valkyriesystems.petspace.domain.models.dtos.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link br.com.valkyriesystems.petspace.domain.models.entities.Address}
 */
public record CreateAddressDto(@NotBlank String country, @NotBlank String city, @NotBlank String state,
                               @NotBlank String district, @NotBlank String street, String number, String complement,
                               @Size(max = 8) @NotBlank String zip) implements Serializable {
}