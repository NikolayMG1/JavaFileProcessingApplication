import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Editor {

    public static void main(String[] args) throws IOException {
        edit();
    }

    public static void edit() throws IOException {
        System.out.println("Enter the absolute path of the text file you want to edit");
        Scanner scanner = new Scanner(System.in);
        String path;//takes the path of the file
        try{
            path = scanner.nextLine();
        }
        catch (NullPointerException e){
            throw new FileNotFoundException("Wrong input");
        }

        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);//fills the array with each line of the file
            }
        } catch (IOException e) {
            System.err.println("Couldn't read the file");
            return;
        }

        int chosenNumber;

        while (true) {

            System.out.println("Choose an option:");
            System.out.println("1.Switch lines");
            System.out.println("2.Switch words in lines");
            System.out.println("3.Exit");
            chosenNumber = scanner.nextInt();

            switch (chosenNumber) {

                case 1:
                    System.out.println("Enter the indexes of the rows you want to swap");
                    int firstLineIndex = scanner.nextInt();
                    int secondLineIndex = scanner.nextInt();
                    switchLines(lines, firstLineIndex, secondLineIndex);//swaps 2 rows
                    saveToFile(path, lines);//applies changes to the file
                    break;

                case 2:
                    System.out.println("Enter the indexes of the 2 rows and words you want to swap");
                    int LineIndex1 = scanner.nextInt();
                    int firstWordIndex = scanner.nextInt();
                    int LineIndex2 = scanner.nextInt();
                    int secondWordIndex = scanner.nextInt();
                    switchWords(lines, LineIndex1, firstWordIndex, LineIndex2, secondWordIndex);//swaps 2 words from 2 rows
                    saveToFile(path, lines);//applies changes to the file
                    break;

                case 3:
                    scanner.close();
                    return;
            }
        }
    }

    private static void saveToFile(String path, List<String> lines) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (String line : lines) {
                writer.write(line);
                writer.write('\n');
            }
            System.out.println("File saved successfully.");
        } catch (IOException e) {
            throw new IOException("Error saving the file.");
        }

    }

    private static void switchLines(List<String> lines, int index1, int index2) {
        if (index1 < 0 || index2 < 0 || index1 > lines.size() || index2 > lines.size()) {
            throw new IllegalArgumentException("invalid indexes");
        } else {
            Collections.swap(lines, index1, index2);
        }
    }

    private static List<String> switchWords(List<String> lines, int index1, int wIndex1, int index2, int wIndex2) {
        if (index1 < 0 || index2 < 0 || index1 > lines.size() || index2 > lines.size()) {
            throw new IllegalArgumentException("invalid indexes");
        }
        String w1 = lines.get(index1);

        String w2 = lines.get(index2);
        Pair[] p1 = getPairs(w1);
        Pair[] p2 = getPairs(w2);

        Pair for1 = new Pair(p1[wIndex1].key(), p2[wIndex2].value());
        Pair for2 = new Pair(p2[wIndex2].key(), p1[wIndex1].value());

        p1[wIndex1] = for1;
        p2[wIndex2] = for2;

        String l1 = toSt(p1);
        String l2 = toSt(p2);

        lines.set(index1, l1);
        lines.set(index2, l2);
        return lines;
    }

    public static String toSt(Pair[] pairs) {
        String result = "";
        for (Pair p : pairs) {
            if (p != null) {
                result += p.value();
                for (int i = 0; i < p.key(); i++) {
                    result += ' ';
                }
            }
        }
        return result;
    }

    public static Pair[] getPairs(String w) {
        String[] w1Words = w.split("\\s+");
        Pair[] line = new Pair[w1Words.length + 1];

        int tempSpaces = 0;
        int index = 0;
        for (int l = 0; l < w.length(); l++) {

            while (l < w.length() && w.charAt(l) != ' ' && w.charAt(l) != '\t') {
                l++;
            }
            while (l < w.length() && (w.charAt(l) == ' ' || w.charAt(l) == '\t')) {
                if (w.charAt(l) == ' ') {
                    tempSpaces++;
                } else if (w.charAt(l) == '\t') {
                    tempSpaces += 4;
                }
                l++;
            }
            Pair pair1 = new Pair(tempSpaces, w1Words[index]);
            line[index] = pair1;
            index++;
            tempSpaces = 0;

        }

        return line;
    }

}
