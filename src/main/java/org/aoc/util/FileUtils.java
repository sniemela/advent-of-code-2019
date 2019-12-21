package org.aoc.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtils {
    
    public static List<String> readAllLines(Class<?> klass, String... path) {
        try {
            return Files.readAllLines(toPath(klass, path));
        } catch (IOException | URISyntaxException e) {
            return Collections.emptyList();
        }
    }

    private static Path toPath(Class<?> klass, String... path) throws URISyntaxException {
        String folderPath = Arrays.stream(path).collect(Collectors.joining("/"));
        
        if (path.length == 1) {
            folderPath = klass.getSimpleName().toLowerCase() + "/" + folderPath;
        }
        
        return Path.of(klass.getResource(folderPath).toURI());
    }
}
