package main.java.de.ba;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicInteger;

class CountCharsInFileTask extends RecursiveAction {
    private static final int BUFFER_SIZE = 1024 * 1024; // 1 MB buffer
    private final Path filePath;
    private final FileCounter counter;

    CountCharsInFileTask(Path filePath, FileCounter counter) {
        this.filePath = filePath;
        this.counter = counter;
    }

    @Override
    protected void compute() {
        try (FileChannel channel = FileChannel.open(filePath)) {
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

            while (channel.read(buffer) > 0) {
                buffer.flip();
                for (int i = 0; i < buffer.limit(); i++) {
                    char c = (char) buffer.get();
                    if (Character.isLetter(c)) {
                        counter.charCountMap.computeIfAbsent(Character.toLowerCase(c), k -> new AtomicInteger()).incrementAndGet();
                    }
                }
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}