package br.com.valkyriesystems.petspace.application.services;

import br.com.valkyriesystems.petspace.domain.models.dtos.create.CreateUsersDto;
import br.com.valkyriesystems.petspace.domain.models.dtos.response.UsersDto;
import br.com.valkyriesystems.petspace.domain.models.dtos.update.UpdateUsersDto;
import br.com.valkyriesystems.petspace.domain.models.entities.Address;
import br.com.valkyriesystems.petspace.domain.models.entities.Users;
import br.com.valkyriesystems.petspace.domain.ports.mappers.UsersMapper;
import br.com.valkyriesystems.petspace.domain.ports.repositories.UsersRepository;
import br.com.valkyriesystems.petspace.domain.ports.services.AddressService;
import br.com.valkyriesystems.petspace.domain.ports.services.UsersService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;
    private final AddressService addressService;

    @Override
    public UsersDto listByEmail(String email) {
        return usersMapper.toDto(usersRepository.findByEmail(email).orElseThrow(RuntimeException::new));
    }

    @Override
    public UsersDto listByUsername(String username) {
        return usersMapper.toDto(usersRepository.findByUsername(username).orElseThrow(RuntimeException::new));
    }

    @Override
    public UsersDto create(CreateUsersDto createDto) {
        usersRepository.findByEmail(createDto.email()).ifPresent(user -> {
            throw new RuntimeException("User with email " + createDto.email() + " already exists");
        });

        Address address = addressService.create(createDto.location());

        Users user = usersMapper.toEntity(createDto);
        user.setLocation(address);
        user = usersRepository.save(user);

        return usersMapper.toDto(user);
    }

    @Override
    public UsersDto update(UpdateUsersDto updateDto, String id) {
        Users toUpdate = usersRepository.findById(UUID.fromString(id)).orElseThrow(RuntimeException::new);
        toUpdate = usersMapper.partialUpdate(updateDto, toUpdate);
        toUpdate = usersRepository.save(toUpdate);

        if (updateDto.location() != null) {
            String locationId = toUpdate.getLocation().getLocationId().toString();

            addressService.update(updateDto.location(), locationId);
        }

        return usersMapper.toDto(toUpdate);
    }

    @Override
    public UsersDto listById(String id) {
        return usersMapper.toDto(usersRepository.findById(UUID.fromString(id)).orElseThrow(RuntimeException::new));
    }

    @Override
    public List<UsersDto> listAll(@Nullable String method, @Nullable String param) {
        return usersMapper.toDto(usersRepository.findAll());
    }

    @Override
    public void delete(String id) {
        Users user = usersRepository.findById(UUID.fromString(id)).orElseThrow(RuntimeException::new);
        usersRepository.delete(user);
        log.info("User with id {} deleted", id);
    }
}
