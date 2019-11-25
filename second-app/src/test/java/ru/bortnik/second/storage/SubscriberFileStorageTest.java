package ru.bortnik.second.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.bortnik.second.dto.SubscriberDto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SubscriberFileStorageTest {

    @Value("${app.directory.prepare}")
    private Path tempPath;
    @Value("${app.directory.work}")
    private Path workPath;

    @Autowired
    private SubscriberFileStorage subscriberFileStorage;
    @Autowired
    private ObjectMapper objectMapper;

    @After
    public void clearFolders() throws IOException {
        deleteFiles(tempPath);
        deleteFiles(workPath);
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
                LocalDate.now());
        subscriberFileStorage.add(dto);

        var tempFiles = Files.list(tempPath).collect(toList());
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
                LocalDate.now());
        subscriberFileStorage.add(dto);
        subscriberFileStorage.commit();

        var tempFiles = Files.list(tempPath).collect(toList());
        assertEquals(0, tempFiles.size());

        var savedFiles = Files.list(workPath).collect(toList());
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
                LocalDate.now());
        subscriberFileStorage.add(dto);
        subscriberFileStorage.clear();

        var tempFiles = Files.list(tempPath).collect(toList());
        assertEquals(0, tempFiles.size());
    }
}
