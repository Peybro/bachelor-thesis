package main.java.de.ba;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Missing arguments! <rootPath> <fileExtension>");
            return;
        }
        String path = args[0];
        String extension = args[1];

        LetterCounter counter = new LetterCounter(path, extension);
        try {
            counter.countLetters();

            System.out.println("Sorted frequency of letters:");
            List<Map.Entry<Character, Integer>> sortedCounts = counter.getSortedCounts();
            for (Map.Entry<Character, Integer> entry : sortedCounts) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
