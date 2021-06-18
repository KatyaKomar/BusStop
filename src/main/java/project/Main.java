package project;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TimetableModifier timetableModifier = new TimetableModifier();
        Scanner in = new Scanner(System.in);
        String path = in.next();
        timetableModifier.run(path);
    }
}
