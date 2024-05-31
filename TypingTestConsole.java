import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TypingTestConsole {
    private List<String> sentences;
    private String currentSentence;
    private long startTime;
    private Scanner scanner;

    public TypingTestConsole() {
        sentences = loadSentencesFromFile("sentences.txt");
        scanner = new Scanner(System.in);
        runTest();
    }

    private List<String> loadSentencesFromFile(String fileName) {
        List<String> sentences = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                sentences.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sentences;
    }

    private void runTest() {
        while (true) {
            loadNewSentence();
            System.out.println("Przepisz poniższe zdanie:");
            System.out.println(currentSentence);
            System.out.println("Wpisz swoje zdanie:");

            startTime = System.currentTimeMillis();
            String userInput = scanner.nextLine();
            long endTime = System.currentTimeMillis();
            double timeTaken = (endTime - startTime) / 1000.0;

            int errors = 0;
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < Math.max(userInput.length(), currentSentence.length()); i++) {
                if (i < userInput.length() && i < currentSentence.length() && userInput.charAt(i) == currentSentence.charAt(i)) {
                    result.append(userInput.charAt(i));
                } else {
                    result.append("\033[31m");
                    if (i < userInput.length()) {
                        result.append(userInput.charAt(i));
                    } else {
                        result.append("_");
                    }
                    result.append("\033[0m");
                    errors++;
                }
            }

            double errorPercentage = (double) errors / currentSentence.length() * 100;
            System.out.println("Twoje zdanie: " + result.toString());
            System.out.printf("Błędy: %d (%.2f%%)%n", errors, errorPercentage);
            System.out.printf("Czas: %.2f sekundy%n", timeTaken);

            System.out.println("Czy chcesz spróbować ponownie? (tak/nie):");
            String response = scanner.nextLine();
            if (!response.equalsIgnoreCase("tak")) {
                break;
            }
        }
    }

    private void loadNewSentence() {
        Random random = new Random();
        currentSentence = sentences.get(random.nextInt(sentences.size()));
    }

    public static void main(String[] args) {
        new TypingTestConsole();
    }
}
