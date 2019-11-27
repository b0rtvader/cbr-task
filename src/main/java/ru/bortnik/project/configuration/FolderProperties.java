package ru.bortnik.project.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@ConfigurationProperties(prefix = "app.folders")
@Getter
@Setter
public class FolderProperties {

    private Path temp;
    private Path work;
    private Path data;
}
