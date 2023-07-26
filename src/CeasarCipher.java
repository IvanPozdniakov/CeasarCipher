import java.io.*;
import java.util.Scanner;

public class CeasarCipher {
    //Створив змінну-константу ALPHABET для шифрування та рошифрування тексту криптоаналізу
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz.,!?: ()";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть шлях до текстового файлу, який хочете зашифрувати: ");
        String filePath = scanner.nextLine();
        System.out.print("Введіть криптографічний ключ: ");
        int key = Integer.parseInt(scanner.nextLine());

        String originalText = readFile(filePath);
        System.out.println("Вихідний текст: " + originalText);

        String encryptedText = encrypt(originalText, key);
        System.out.println("Зашифрований текст: " + encryptedText);
        writeFile("encrypted.txt", encryptedText);

        String decryptedText = decrypt(encryptedText, key);
        System.out.println("Розшифрований текст: " + decryptedText);
        writeFile("decrypted.txt", decryptedText);

        System.out.println("Пошук ключа brute force:");
        bruteForceDecrypt(encryptedText);
    }

    //Метод для читання тексту з файлу

    private static String readFile(String filePath) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    //Метод для запису файлу

    private static void writeFile(String filePath, String content) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Метод шифрування

    private static String encrypt(String text, int key) {
        StringBuilder encrypted = new StringBuilder();
        for (char c : text.toCharArray()) {
            int charIndex = ALPHABET.indexOf(c);
            if (charIndex != -1) {
                int encryptedIndex = (charIndex + key) % ALPHABET.length();
                char encryptedChar = ALPHABET.charAt(encryptedIndex);
                encrypted.append(encryptedChar);
            } else {
                encrypted.append(c);
            }
        }
        return encrypted.toString();
    }

    //Метод розшифрування

    private static String decrypt(String encryptedText, int key) {
        return encrypt(encryptedText, ALPHABET.length() - key);
    }

    //Криптоаналіз методом brute force

    private static void bruteForceDecrypt(String encryptedText) {
        for (int key = 1; key < ALPHABET.length(); key++) {
            String decryptedText = decrypt(encryptedText, key);
            System.out.println("Ключ " + key + ": " + decryptedText);
        }
    }
}