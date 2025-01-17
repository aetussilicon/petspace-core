package br.com.valkyriesystems.petspace.domain.models.dtos.create;

import br.com.valkyriesystems.petspace.domain.ports.interfaces.CreateDtoType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link br.com.valkyriesystems.petspace.domain.Organizations}
 */
public record CreatedOrganizationsDto(@NotBlank String ongName, @Size(max = 14) @NotBlank String taxNumber,
                                      @Email @NotBlank String email, @NotBlank String password,
                                      @NotNull CreateAddressDto location) implements Serializable, CreateDtoType {
}