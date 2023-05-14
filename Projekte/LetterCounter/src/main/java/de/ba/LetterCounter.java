package main.java.de.ba;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class LetterCounter {
    private final String path;
    private final String extension;
    private final Map<Character, Integer> counts;

    public LetterCounter(String path, String extension) {
        this.path = path;
        this.extension = extension;
        counts = new HashMap<>();
        for (char c = 'A'; c <= 'Z'; c++) {
            counts.put(c, 0);
        }
    }

    public void countLetters() throws IOException {
        File directory = new File(this.path);
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Not a directory: " + this.path);
        }
        countLettersInDirectory(directory);
    }

    private void countLettersInDirectory(File directory) throws IOException {
        File[] requireNonNull = Objects.requireNonNull(directory.listFiles());
        for (File file : requireNonNull) {
            if (file.isDirectory()) {
                LetterCounter worker = new LetterCounter(file.getAbsolutePath(), this.extension);
                worker.countLettersInDirectory(file);

                //System.out.println("Directory " + worker.getPath() + ": ");
                //for (Map.Entry<Character, Integer> entry : worker.getCounts().entrySet()) {
                //    System.out.print(entry.getKey() + ":" + entry.getValue() + ", ");
                //}
                //System.out.println();
                addCounts(worker.getCounts());
            } else if (file.getName().toLowerCase().endsWith("." + this.extension)) {
                countLettersInFile(file);
            }
        }
    }

    private void countLettersInFile(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                for (char c : line.toUpperCase().toCharArray()) {
                    if (Character.isLetter(c)) {
                        if (counts.containsKey(c)) {
                            counts.put(c, counts.get(c) + 1);
                        } else {
                            counts.put(c, 1);
                        }
                    }
                }
            }
        }
    }

    private synchronized void addCounts(Map<Character, Integer> newCounts) {
        for (Map.Entry<Character, Integer> entry : newCounts.entrySet()) {
            char key = entry.getKey();
            int value = entry.getValue();
            if (counts.containsKey(key)) {
                counts.put(key, counts.get(key) + value);
            } else {
                counts.put(key, value);
            }
        }
    }

    public List<Map.Entry<Character, Integer>> getSortedCounts() {
        List<Map.Entry<Character, Integer>> sortedCounts = new ArrayList<>(counts.entrySet());
        sortedCounts.sort(Map.Entry.comparingByKey());
        return sortedCounts;
    }

    public Map<Character, Integer> getCounts() {
        return this.counts;
    }

    public String getPath() {
        return this.path;
    }
}