package br.com.valkyriesystems.petspace.domain.models.dtos.update;

import br.com.valkyriesystems.petspace.domain.ports.interfaces.UpdateDtoType;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link br.com.valkyriesystems.petspace.domain.models.entities.Address}
 */
public record UpdateAddressDto(String country, String city, String state, String district, String street, String number,
                               String complement, @Size(max = 8) String zip) implements Serializable, UpdateDtoType {
}