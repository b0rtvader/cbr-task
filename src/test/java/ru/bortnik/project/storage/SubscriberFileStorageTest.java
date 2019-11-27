package ru.bortnik.project.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.bortnik.project.configuration.FolderProperties;
import ru.bortnik.project.dto.SubscriberDto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SubscriberFileStorageTest {

    @Autowired
    private FolderProperties folders;
    @Autowired
    private SubscriberFileStorage subscriberFileStorage;
    @Autowired
    private ObjectMapper objectMapper;

    @After
    public void clearFolders() throws IOException {
        deleteFiles(folders.getTemp());
        deleteFiles(folders.getWork());
    }

    private void deleteFiles(Path path) throws IOException {
        Files.list(path)
                .filter(p -> !Files.isDirectory(p))
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @Test
    public void add_should_saveInTemp() throws IOException {
        SubscriberDto dto = new SubscriberDto("Ivanov",
                "Ivan",
                "111-123-45-66",
                "111-234-56-78",
                "example@mail.ru",
                LocalDate.now(),
                null);
        subscriberFileStorage.add(dto);

        var tempFiles = Files.list(folders.getTemp()).collect(toList());
        assertEquals(1, tempFiles.size());

        Path firstFile = tempFiles.get(0);
        assertEquals(dto, objectMapper.readValue(firstFile.toFile(), SubscriberDto.class));
    }

    @Test
    public void commit_should_saveInWork() throws IOException {
        SubscriberDto dto = new SubscriberDto("Ivanov",
                "Ivan",
                "111-123-45-66",
                "111-234-56-78",
                "example@mail.ru",
                LocalDate.now(),
                null);
        subscriberFileStorage.add(dto);
        subscriberFileStorage.commit();

        var tempFiles = Files.list(folders.getTemp()).collect(toList());
        assertEquals(0, tempFiles.size());

        var savedFiles = Files.list(folders.getWork()).collect(toList());
        assertEquals(2, savedFiles.size());

        Path txtFile = savedFiles.stream()
                .filter(p -> p.toString().endsWith(".txt"))
                .findFirst()
                .orElseThrow(RuntimeException::new);
        assertEquals(dto, objectMapper.readValue(txtFile.toFile(), SubscriberDto.class));
    }

    @Test
    public void clear_should_removeTemp() throws IOException {
        SubscriberDto dto = new SubscriberDto("Ivanov",
                "Ivan",
                "111-123-45-66",
                "111-234-56-78",
                "example@mail.ru",
                LocalDate.now(),
                null);
        subscriberFileStorage.add(dto);
        subscriberFileStorage.clear();

        var tempFiles = Files.list(folders.getTemp()).collect(toList());
        assertEquals(0, tempFiles.size());
    }
}
