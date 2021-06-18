package com.jaoow;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class Generator {

    private static final String FIRST_HEX = "#ff1100";
    private static float hue = 0;

    public static void main(String[] args) {

        LinkedList<String> queue = new LinkedList<>();
        LinkedList<String> inputs = new LinkedList<>();

        System.out.println("Type the text you want to color:");
        String message = new Scanner(System.in).nextLine();

        long start = System.currentTimeMillis();
        System.out.println("Creating rainbow animated text, please wait...");

        while (!(queue.size() >= 1 && (queue.get(0).equals(FIRST_HEX) && inputs.size() > 1))) {

            if (queue.size() >= message.length()) {
                queue.poll();
            }

            while (queue.size() != message.length()) {
                Color color = getRainbowColor();
                String hex = toHex(color);
                queue.add(hex);
            }

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("- '");
            for (int i = 0; i < queue.size(); i++) {
                stringBuilder.append(queue.get(i)).append(message.charAt(i));
            }
            stringBuilder.append("'");

            inputs.add(stringBuilder.append("\n").toString());
        }

        File file = new File("texts/" + toFileName(message.toLowerCase()) + ".txt");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();

        try (FileWriter writer = new FileWriter(file)) {

            for (String input : inputs) {
                writer.append(input);
            }
            writer.flush();

            System.out.printf("Rainbow animated text successfully created! (Took %sms)", (System.currentTimeMillis() - start));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String toFileName(String str) {
        return str.replace(" ", "_").replaceAll("[^a-zA-Z0-9\\\\.\\-_]", "");
    }

    private static String toHex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    private static Color getRainbowColor() {
        hue = (hue + 4) % 362;
        return Color.getHSBColor(hue / 360F, 1.0F, 1.0F);
    }
}
