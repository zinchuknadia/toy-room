package org.example.toyroom.service;

import org.example.entity.Theme;
import org.example.toyroom.models.ThemeInfo;
import org.example.repository.ThemeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ThemeServiceTest {

    private ThemeRepository themeRepository;
    private ThemeService themeService;

    @BeforeEach
    void setUp() {
        themeRepository = mock(ThemeRepository.class);
        themeService = new ThemeService(themeRepository);
    }

    @Test
    void createTheme_shouldSaveNewTheme() {
        themeService.createTheme("Summer", "/images/summer.png");

        ArgumentCaptor<Theme> captor = ArgumentCaptor.forClass(Theme.class);
        verify(themeRepository).save(captor.capture());

        Theme saved = captor.getValue();
        assertEquals("Summer", saved.getName());
        assertEquals("/images/summer.png", saved.getImage());
    }

    @Test
    void getTheme_shouldReturnThemeById() {
        Theme theme = new Theme();
        theme.setId(1L);
        theme.setName("Winter");

        when(themeRepository.findById(1L)).thenReturn(theme);

        Theme result = themeService.getTheme(1L);
        assertSame(theme, result);
    }

    @Test
    void getAllThemes_shouldReturnMappedThemeInfoList() {
        Theme theme1 = new Theme();
        theme1.setName("Theme1");
        Theme theme2 = new Theme();
        theme2.setName("Theme2");

        when(themeRepository.findAll()).thenReturn(List.of(theme1, theme2));

        List<ThemeInfo> themeInfos = themeService.getAllThemes();

        assertEquals(2, themeInfos.size());
        assertEquals("Theme1", themeInfos.get(0).getName());
        assertEquals("Theme2", themeInfos.get(1).getName());
    }

    @Test
    void deleteTheme_shouldReturnTrueWhenDeleted() {
        when(themeRepository.deleteById(1L)).thenReturn(true);
        assertTrue(themeService.deleteTheme(1L));
        verify(themeRepository).deleteById(1L);
    }

    @Test
    void deleteTheme_shouldReturnFalseWhenNotDeleted() {
        when(themeRepository.deleteById(2L)).thenReturn(false);
        assertFalse(themeService.deleteTheme(2L));
        verify(themeRepository).deleteById(2L);
    }

    @Test
    void getThemeByName_shouldReturnThemeByName() {
        Theme theme = new Theme();
        theme.setName("Halloween");

        when(themeRepository.findByName("Halloween")).thenReturn(theme);

        Theme result = themeService.getThemeByName("Halloween");
        assertSame(theme, result);
    }

    @Test
    void getAllThemeNames_shouldReturnListOfNames() {
        Theme t1 = new Theme();
        t1.setName("A");
        Theme t2 = new Theme();
        t2.setName("B");

        when(themeRepository.findAll()).thenReturn(List.of(t1, t2));

        List<String> names = themeService.getAllThemeNames();
        assertEquals(List.of("A", "B"), names);
    }

    @Test
    void saveTheme_shouldConvertAndSaveThemeEntity() {
        ThemeInfo themeInfo = new ThemeInfo();
        themeInfo.setName("NewTheme");
        themeInfo.setImage("/img/new.png");

        // Call the method under test
        themeService.saveTheme(themeInfo);

        // Capture the argument passed to themeRepository.save()
        ArgumentCaptor<Theme> captor = ArgumentCaptor.forClass(Theme.class);
        verify(themeRepository).save(captor.capture());

        Theme saved = captor.getValue();
        assertEquals("NewTheme", saved.getName());
        assertEquals("/img/new.png", saved.getImage());
    }

}
