package org.example.toyroom.service;

import org.example.entity.Theme;
import org.example.toyroom.mappers.ThemeMapper;
import org.example.toyroom.models.ThemeInfo;
import org.example.repository.ThemeRepository;

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

//    public List<ThemeInfo> getAllThemes() {
//        return themeRepository.findAll().stream()
//                .map(ThemeMapper::toModel)
//                .collect(Collectors.toList());
//    }

    public List<ThemeInfo> getAllThemes() {
        return themeRepository.findAll().stream()
                .map(ThemeMapper::toModel)
                .collect(Collectors.toList());
    }

    public boolean deleteTheme(Long id) {
        if(themeRepository.deleteById(id)){
            return true;
        }
        return false;
    }

    public Theme getThemeByName(String themeName) {
        return themeRepository.findByName(themeName);
    }

    public List<String> getAllThemeNames() {
        return themeRepository.findAll().stream()
                .map(Theme::getName)
                .collect(Collectors.toList());
    }

    public void saveTheme(ThemeInfo theme) {
        Theme themeEntity = ThemeMapper.toEntity(theme);
        themeRepository.save(themeEntity);
    }
}
