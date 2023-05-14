package main.java.de.ba;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class CountCharsInDirTask extends RecursiveAction {
    private final String dirPath;
    private final String fileExt;
    private final FileCounter counter;

    CountCharsInDirTask(String dirPath, String fileExt, FileCounter counter) {
        this.dirPath = dirPath;
        this.fileExt = fileExt;
        this.counter = counter;
    }

    @Override
    protected void compute() {
        try (Stream<Path> walk = Files.walk(Paths.get(dirPath))) {
            List<CountCharsInFileTask> fileTasks = walk.parallel()
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(fileExt))
                    .map(path -> new CountCharsInFileTask(path, counter))
                    .collect(Collectors.toList());

            invokeAll(fileTasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
