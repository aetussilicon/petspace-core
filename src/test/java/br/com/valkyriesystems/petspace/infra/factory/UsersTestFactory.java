package br.com.valkyriesystems.petspace.infra.factory;

import br.com.valkyriesystems.petspace.domain.models.dtos.create.CreateUsersDto;
import br.com.valkyriesystems.petspace.domain.models.dtos.response.UsersDto;
import br.com.valkyriesystems.petspace.domain.models.entities.Address;
import br.com.valkyriesystems.petspace.domain.models.entities.Users;
import br.com.valkyriesystems.petspace.domain.models.enums.InterestedIn;
import br.com.valkyriesystems.petspace.domain.models.enums.PetAgeRange;

import java.sql.Timestamp;
import java.util.UUID;

public class UsersTestFactory {
    public static Users create() {
        Address address = AddressTestFactory.create();
        return new Users(
                UUID.randomUUID(),
                "John",
                "Doe",
                "johndoe",
                "john.doe@example.com",
                "password123",
                "1234567890",
                InterestedIn.ADOPT,
                PetAgeRange.ADULT,
                address,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis())
        );
    }

    public static UsersDto response() {
        Address address = AddressTestFactory.create();
        return new UsersDto(
                UUID.randomUUID(),
                "John",
                "Doe",
                "john.doe@example.com",
                "password123",
                "1234567890",
                InterestedIn.ADOPT,
                PetAgeRange.ADULT,
                address,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                "johndoe"
        );
    }

    public static CreateUsersDto createDto() {
        return new CreateUsersDto(
                "John",
                "Doe",
                "john.doe@example.com",
                "password123",
                InterestedIn.ADOPT,
                PetAgeRange.ADULT,
                AddressTestFactory.createDto(),
                "johndoe"
        );
    }

}

