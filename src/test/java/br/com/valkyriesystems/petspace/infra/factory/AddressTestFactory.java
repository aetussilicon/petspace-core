package br.com.valkyriesystems.petspace.infra.factory;

import br.com.valkyriesystems.petspace.domain.models.dtos.create.CreateAddressDto;
import br.com.valkyriesystems.petspace.domain.models.entities.Address;

import java.sql.Timestamp;

public class AddressTestFactory {
    public static Address create() {
        return new Address(
                1L,
                "USA",
                "New York",
                "NY",
                "Manhattan",
                "5th Avenue",
                "123",
                "Apt 4B",
                "10001",
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis())
        );
    }

    public static CreateAddressDto createDto() {
        return new CreateAddressDto(
                "USA",
                "New York",
                "NY",
                "Manhattan",
                "5th Avenue",
                "123",
                "Apt 4B",
                "10001"
        );
    }
}
