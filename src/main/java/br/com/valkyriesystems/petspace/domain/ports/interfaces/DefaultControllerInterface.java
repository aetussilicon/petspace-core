package br.com.valkyriesystems.petspace.domain.ports.interfaces;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface DefaultControllerInterface<T extends ResponseType, R extends CreateDtoType, P extends UpdateDtoType> {

        @PostMapping("create")
        ResponseEntity<T> create(@RequestBody @Valid R createDto);

        @PatchMapping("update/{id}")
        ResponseEntity<T> update(@RequestBody @Valid P updateDto, @PathVariable String id);

        @GetMapping("get")
        ResponseEntity<T> list(@RequestParam String method, String param);

        @GetMapping("get/all")
        ResponseEntity<List<T>> listAll(
                @RequestParam(required = false) String method,
                @RequestParam(required = false)String param);

        @DeleteMapping("delete/{id}")
        ResponseEntity<Void>delete(@PathVariable String id);
}
