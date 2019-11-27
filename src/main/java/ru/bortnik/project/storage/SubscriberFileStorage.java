package ru.bortnik.project.storage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bortnik.project.configuration.FolderProperties;
import ru.bortnik.project.dto.SubscriberDto;
import ru.bortnik.project.exception.StorageException;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.util.stream.Collectors.toList;

/**
 * Хранилище абонентов в файловой системе
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SubscriberFileStorage implements SubscriberStorage {

    private final FolderProperties folders;
    private final DateTimeFormatter formatter;
    private final ObjectMapper mapper;
    private final JsonSchema jsonSchema;

    @PostConstruct
    public void init() {
        createSubDirectories(folders.getWork());
        checkIfDirectory(folders.getWork());

        createSubDirectories(folders.getTemp());
        checkIfDirectory(folders.getTemp());
    }

    @Override
    public void add(SubscriberDto subscriberDto) {
        var filename = buildFilename("data", LocalDateTime.now(), "json");
        var filePath = folders.getTemp().resolve(filename);
        log.info("Save subscriber {} to {}", subscriberDto, filePath);
        try {
            mapper.writeValue(filePath.toFile(), subscriberDto);
        } catch (IOException e) {
            log.error("Add to " + filePath.toString() + " failed", e);
            throw new StorageException(e);
        }
    }

    @Override
    public int commit() {
        try {
            var allFiles = Files.list(folders.getTemp()).collect(toList());
            for (Path path : allFiles) {
                validateContent(path);
                copyToWorkPath(path);
                delete(path.toFile());
            }
            return allFiles.size();
        } catch (IOException e) {
            log.error("Commit failed", e);
            throw new StorageException(e);
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(folders.getTemp())
                    .map(Path::toFile)
                    .forEach(this::delete);
        } catch (IOException e) {
            log.error("clear failed", e);
            throw new StorageException(e);
        }
    }

    private void delete(File file) {
        log.info("Delete file {}", file);
        file.delete();
    }

    private void createSubDirectories(Path path) {
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkIfDirectory(Path path) {
        if (!Files.isDirectory(path)) {
            throw new RuntimeException(path + " must be a directory");
        }
    }

    private void validateContent(Path path) throws IOException {
        log.info("Validate file {}", path);
        JsonNode jsonNode = JsonLoader.fromFile(path.toFile());

        try {
            if (!jsonSchema.validInstance(jsonNode)) {
                throw new RuntimeException("File " + path + " is not valid");
            }
        } catch (ProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void copyToWorkPath(Path src) throws IOException {
        LocalDateTime now = LocalDateTime.now();

        String targetFilename = buildFilename("json", now, "txt");
        String readyFilename = buildFilename("json", now, "ready");

        Path targetPath = folders.getWork().resolve(targetFilename);
        Path readyPath = folders.getWork().resolve(readyFilename);

        log.info("Copy file from {} to {}", src, targetPath);
        Files.copy(src, targetPath);
        Files.createFile(readyPath);
    }

    private String buildFilename(String prefix, LocalDateTime datetime, String ext) {
        return prefix + "_"
                + formatter.format(datetime)
                + "." + ext;
    }
}
