package ru.bortnik.second.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.everit.json.schema.Schema;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.bortnik.second.dto.SubscriberDto;
import ru.bortnik.second.exception.StorageException;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.util.stream.Collectors.*;

/**
 * Хранилище абонентов в файловой системе
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SubscriberFileStorage implements SubscriberStorage {

    @Value("${app.directory.prepare}")
    private Path tempPath;
    @Value("${app.directory.work}")
    private Path workPath;

    private final DateTimeFormatter formatter;
    private final ObjectMapper mapper;
    private final Schema schema;

    @PostConstruct
    public void init() {
        createSubDirectories(workPath);
        checkIfDirectory(workPath);

        createSubDirectories(tempPath);
        checkIfDirectory(tempPath);
    }

    @Override
    public void add(SubscriberDto subscriberDto) {
        var filename = buildFilename("data", LocalDateTime.now(), "json");
        var filePath = tempPath.resolve(filename);
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
            var allFiles = Files.list(tempPath).collect(toList());
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
            Files.list(tempPath)
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
        var jsonTokener = new JSONTokener(new FileInputStream(path.toFile()));
        schema.validate(new JSONObject(jsonTokener));
    }

    private void copyToWorkPath(Path src) throws IOException {
        LocalDateTime now = LocalDateTime.now();

        String targetFilename = buildFilename("json", now, "txt");
        String readyFilename = buildFilename("json", now, "ready");

        Path targetPath = workPath.resolve(targetFilename);
        Path readyPath = workPath.resolve(readyFilename);

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
