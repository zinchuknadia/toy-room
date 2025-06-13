package org.example.toyroom.service;

import org.example.toyroom.entity.Theme;
import org.example.toyroom.mapper.ThemeMapper;
import org.example.toyroom.models.ThemeInfo;
import org.example.toyroom.repository.ThemeRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ThemeService {
    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public void createTheme(String name, String imagePath) {
        Theme theme = new Theme();
        theme.setName(name);
        theme.setImage(imagePath);
        themeRepository.save(theme);
    }

    public Theme getTheme(Long id) {
        return themeRepository.findById(id);
    }

    public List<ThemeInfo> getAllThemes() {
        return themeRepository.findAll().stream()
                .map(ThemeMapper::toModel)
                .collect(Collectors.toList());
    }

    public void deleteTheme(Long id) {
        themeRepository.deleteById(id);
    }
}
