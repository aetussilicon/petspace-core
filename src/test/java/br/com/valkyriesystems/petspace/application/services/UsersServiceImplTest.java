package br.com.valkyriesystems.petspace.application.services;

import br.com.valkyriesystems.petspace.domain.models.dtos.create.CreateUsersDto;
import br.com.valkyriesystems.petspace.domain.models.dtos.response.UsersDto;
import br.com.valkyriesystems.petspace.domain.models.dtos.update.UpdateAddressDto;
import br.com.valkyriesystems.petspace.domain.models.dtos.update.UpdateUsersDto;
import br.com.valkyriesystems.petspace.domain.models.entities.Users;
import br.com.valkyriesystems.petspace.domain.models.enums.InterestedIn;
import br.com.valkyriesystems.petspace.domain.models.enums.PetAgeRange;
import br.com.valkyriesystems.petspace.domain.ports.mappers.UsersMapper;
import br.com.valkyriesystems.petspace.domain.ports.repositories.UsersRepository;
import br.com.valkyriesystems.petspace.domain.ports.services.AddressService;
import br.com.valkyriesystems.petspace.infra.factory.UsersTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

class UsersServiceImplTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private UsersMapper usersMapper;

    @Mock
    private AddressService addressService;

    @InjectMocks
    private UsersServiceImpl usersService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listByEmail_Success() {
        Users user = UsersTestFactory.create();
        UsersDto userDto = UsersTestFactory.response();

        when(usersRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(usersMapper.toDto(user)).thenReturn(userDto);

        UsersDto result = usersService.listByEmail(user.getEmail());

        assertNotNull(result);
        assertEquals(userDto, result);
        verify(usersRepository, times(1)).findByEmail(user.getEmail());
    }

    @Test
    void listByEmail_Failure_UserNotFound() {
        String email = "nonexistent@example.com";

        when(usersRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            usersService.listByEmail(email);
        });

        verify(usersRepository, times(1)).findByEmail(email);
    }

    @Test
    void listByUsername_Success() {
        Users user = UsersTestFactory.create();
        UsersDto userDto = UsersTestFactory.response();

        when(usersRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(usersMapper.toDto(user)).thenReturn(userDto);

        UsersDto result = usersService.listByUsername(user.getUsername());

        assertNotNull(result);
        assertEquals(userDto, result);
        verify(usersRepository, times(1)).findByUsername(user.getUsername());
    }

    @Test
    void listByUsername_Failure_UserNotFound() {
        String username = "nonexistentuser";

        when(usersRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            usersService.listByUsername(username);
        });

        verify(usersRepository, times(1)).findByUsername(username);
    }

    @Test
    void create_Success() {
        CreateUsersDto createDto = UsersTestFactory.createDto();
        Users user = UsersTestFactory.create();
        UsersDto userDto = UsersTestFactory.response();

        when(usersRepository.findByEmail(createDto.email())).thenReturn(Optional.empty());
        when(addressService.create(createDto.location())).thenReturn(user.getLocation());
        when(usersMapper.toEntity(createDto)).thenReturn(user);
        when(usersRepository.save(any(Users.class))).thenReturn(user);
        when(usersMapper.toDto(user)).thenReturn(userDto);

        UsersDto result = usersService.create(createDto);

        assertNotNull(result);
        assertEquals(userDto, result);
        verify(usersRepository, times(1)).findByEmail(createDto.email());
        verify(usersRepository, times(1)).save(user);
    }

    @Test
    void create_Failure_UserAlreadyExists() {
        CreateUsersDto createDto = UsersTestFactory.createDto();
        Users existingUser = UsersTestFactory.create();

        when(usersRepository.findByEmail(createDto.email())).thenReturn(Optional.of(existingUser));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usersService.create(createDto);
        });

        assertEquals("User with email " + createDto.email() + " already exists", exception.getMessage());
        verify(usersRepository, times(1)).findByEmail(createDto.email());
        verify(usersRepository, never()).save(any(Users.class));
    }

    @Test
    void update_Success() {
        UpdateUsersDto updateDto = new UpdateUsersDto(
                "Jane",
                "Doe",
                "jane.doe@example.com",
                "newpassword123",
                "9876543210",
                InterestedIn.ADOPT,
                PetAgeRange.ADULT,
                new UpdateAddressDto("USA", "Los Angeles", "CA", "Beverly Hills", "Rodeo Drive", "456", "Suite 789", "90210"),
                "janedoe"
        );
        Users existingUser = UsersTestFactory.create();
        Users updatedUser = new Users(
                existingUser.getUserId(),
                "Jane",
                "Doe",
                "janedoe",
                "jane.doe@example.com",
                "newpassword123",
                "9876543210",
                InterestedIn.ADOPT,
                PetAgeRange.ADULT,
                existingUser.getLocation(),
                existingUser.getCreatedAt(),
                new Timestamp(System.currentTimeMillis())
        );

        UsersDto updatedUserDto = new UsersDto(
                updatedUser.getUserId(),
                "Jane",
                "Doe",
                "jane.doe@example.com",
                "newpassword123",
                "9876543210",
                InterestedIn.ADOPT,
                PetAgeRange.ADULT,
                updatedUser.getLocation(),
                updatedUser.getCreatedAt(),
                updatedUser.getUpdatedAt(),
                "janedoe"
        );

        when(usersRepository.findById(existingUser.getUserId())).thenReturn(Optional.of(existingUser));
        when(usersMapper.partialUpdate(updateDto, existingUser)).thenReturn(updatedUser);
        when(usersRepository.save(updatedUser)).thenReturn(updatedUser);
        when(usersMapper.toDto(updatedUser)).thenReturn(updatedUserDto);

        UsersDto result = usersService.update(updateDto, existingUser.getUserId().toString());

        assertNotNull(result);
        assertEquals("Jane", result.name());
        assertEquals("jane.doe@example.com", result.email());
        verify(usersRepository, times(1)).findById(existingUser.getUserId());
        verify(usersRepository, times(1)).save(updatedUser);
        verify(usersMapper, times(1)).partialUpdate(updateDto, existingUser);
        verify(usersMapper, times(1)).toDto(updatedUser);
    }

    @Test
    void update_Failure_UserNotFound() {
        // Arrange
        UpdateUsersDto updateDto = new UpdateUsersDto(
                "Jane",
                "Doe",
                "jane.doe@example.com",
                "newpassword123",
                null,
                InterestedIn.ADOPT,
                PetAgeRange.SENIOR,
                null,
                "janedoe"
        );
        UUID userId = UUID.randomUUID();

        when(usersRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> usersService.update(updateDto, userId.toString()));

        verify(usersRepository, times(1)).findById(userId);
        verify(usersRepository, never()).save(any(Users.class));
        verify(usersMapper, never()).partialUpdate(any(UpdateUsersDto.class), any(Users.class));
        verify(usersMapper, never()).toDto(any(Users.class));
    }


    @Test
    void listById_Failure_UserNotFound() {
        UUID userId = UUID.randomUUID();

        when(usersRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            usersService.listById(userId.toString());
        });

        verify(usersRepository, times(1)).findById(userId);
    }

    @Test
    void listAll_Success() {
        List<Users> usersList = List.of(UsersTestFactory.create(), UsersTestFactory.create());
        List<UsersDto> usersDtoList = List.of(UsersTestFactory.response(), UsersTestFactory.response());

        when(usersRepository.findAll()).thenReturn(usersList);
        when(usersMapper.toDto(usersList)).thenReturn(usersDtoList);

        List<UsersDto> result = usersService.listAll(null, null);

        assertNotNull(result);
        assertEquals(usersDtoList, result);
        verify(usersRepository, times(1)).findAll();
    }

    @Test
    void delete_Success() {
        Users user = UsersTestFactory.create();
        UUID userId = user.getUserId();

        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));

        usersService.delete(userId.toString());

        verify(usersRepository, times(1)).findById(userId);
        verify(usersRepository, times(1)).delete(user);
    }

    @Test
    void delete_Failure_UserNotFound() {
        UUID userId = UUID.randomUUID();

        when(usersRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            usersService.delete(userId.toString());
        });

        verify(usersRepository, times(1)).findById(userId);
        verify(usersRepository, never()).delete(any(Users.class));
    }
}