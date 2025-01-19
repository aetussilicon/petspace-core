package br.com.valkyriesystems.petspace.application.controllers;

import br.com.valkyriesystems.petspace.domain.models.dtos.create.CreateUsersDto;
import br.com.valkyriesystems.petspace.domain.models.dtos.response.UsersDto;
import br.com.valkyriesystems.petspace.domain.models.dtos.update.UpdateUsersDto;
import br.com.valkyriesystems.petspace.domain.ports.controllers.UsersController;
import br.com.valkyriesystems.petspace.domain.ports.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UsersControllerImpl implements UsersController {
    private final UsersService usersService;

    @Override
    public ResponseEntity<UsersDto> create(CreateUsersDto createDto) {
        return new ResponseEntity<>(usersService.create(createDto), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UsersDto> update(UpdateUsersDto updateDto, String id) {
        return new ResponseEntity<>(usersService.update(updateDto, id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UsersDto> list(String method, String param) {
        switch (method) {
            case "id" -> {
                return new ResponseEntity<>(usersService.listById(param), HttpStatus.OK);
            }
            case "username" -> {
                return new ResponseEntity<>(usersService.listByUsername(param), HttpStatus.OK);
            }
            case "email" -> {
               return new ResponseEntity<>(usersService.listByEmail(param), HttpStatus.OK);
            }
            default -> throw new RuntimeException("Invalid method");
        }
    }

    @Override
    public ResponseEntity<List<UsersDto>> listAll(String method, String param) {
        if (method != null) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        return new ResponseEntity<>(usersService.listAll(null, null), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(String id) {
        usersService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
