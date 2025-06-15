package org.example.toyroom.service;

import org.example.toyroom.entity.Theme;
import org.example.toyroom.entity.Type;
import org.example.toyroom.mapper.TypeMapper;
import org.example.toyroom.models.TypeInfo;
import org.example.toyroom.repository.TypeRepository;

import java.util.List;
import java.util.stream.Collectors;

public class TypeService {
    private final TypeRepository repository;

    public TypeService(TypeRepository repository) {
        this.repository = repository;
    }

    public void createType(String name, String image) {
        Type type = new Type();
        type.setName(name);
        type.setImage(image);
        repository.save(type);
    }

    public Type getById(Long id) {
        return repository.findById(id);
    }

    public List<TypeInfo> getAllTypes() {
        return repository.findAll().stream()
                .map(TypeMapper::toModel)
                .collect(Collectors.toList());
    }

    public List<String> getAllTypeNames() {
        return repository.findAll().stream()
                .map(Type::getName)
                .collect(Collectors.toList());
    }

    public Type getTypeByName(String name) {
        return repository.findByName(name);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
