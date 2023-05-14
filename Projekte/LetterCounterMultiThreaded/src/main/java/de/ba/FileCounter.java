package main.java.de.ba;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class FileCounter {
    final ConcurrentHashMap<Character, AtomicInteger> charCountMap = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 2) {
            System.out.println("Missing arguments! <rootPath> <fileExtension>");
            return;
        }
        String startDir = args[0];
        String fileExt = args[1];
        ForkJoinPool pool = new ForkJoinPool();

        FileCounter counter = new FileCounter();
        pool.invoke(new CountCharsInDirTask(startDir, fileExt, counter));

        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);

        counter.printResults();
    }

    public void printResults() {
        charCountMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> System.out.println(entry.getKey().toString().toUpperCase() + ": " + entry.getValue()));
    }
}
