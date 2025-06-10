package org.example.toyroom;

import org.example.toyroom.models.Color;
import org.example.toyroom.models.Size;
import org.example.toyroom.models.Toy;
import org.example.toyroom.repository.ToyRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ToyRoomTest {

    @Test
    public void testGetToyRepository_ReturnsInjectedRepository() {
        ToyRepository mockRepository = mock(ToyRepository.class);
        ToyRoom toyRoom = new ToyRoom(mockRepository);
        assertSame(mockRepository, toyRoom.getToyRepository());
    }

    @Test
    public void testReadToysFromFile() throws IOException {
        // Створюємо тимчасовий файл
        Path tempFile = Files.createTempFile("test-toys", ".txt");
        Files.write(tempFile, List.of("car,medium,#ff0000,plastic", "doll,small,#00ff00,fabric"));

        ToyRoom toyRoom = new ToyRoom();
        List<Toy> toys = toyRoom.readToysFromFile(tempFile.toString());

        assertEquals(2, toys.size());

        Toy first = toys.get(0);
        assertEquals("car", first.getType());
        assertEquals(Size.MEDIUM, first.getSize());
        assertEquals("#ff0000", first.getColor().getHexCode());
        assertEquals("plastic", first.getMaterial());

        // Прибираємо тимчасовий файл
        Files.deleteIfExists(tempFile);
    }

    @Test
    public void testImportToysFromFile_CallsRepositorySave() throws IOException {
        // Arrange
        Path tempFile = Files.createTempFile("test-toys", ".txt");
        Files.write(tempFile, List.of("robot,large,#0000ff,metal"));

        ToyRepository mockRepository = Mockito.mock(ToyRepository.class);
        ToyRoom toyRoom = new ToyRoom(mockRepository);

        // Act
        toyRoom.importToysFromFile(tempFile.toString());

        // Assert
        verify(mockRepository, times(1)).saveToys(anyList());

        // Cleanup
        Files.deleteIfExists(tempFile);
    }

    @Test
    public void testParseSize_ValidSizes() {
        assertEquals(Size.LARGE, ToyRoom.parseSize("large"));
        assertEquals(Size.MEDIUM, ToyRoom.parseSize("medium"));
        assertEquals(Size.SMALL, ToyRoom.parseSize("small"));
    }

    @Test
    public void testParseSize_InvalidSize_ThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            ToyRoom.parseSize("gigantic");
        });
        assertTrue(exception.getMessage().contains("Invalid Size"));
    }

}
