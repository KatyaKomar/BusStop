package project;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

public class TimetableModifier {
    private static Logger logger = LogManager.getLogger();
    private List<Entry> entries;

    public TimetableModifier() {
        entries = new ArrayList<>();
    }

    public void parse(List<String> entryLines) {
        for (String line : entryLines) {
            String[] data = line.split(" ");
            String[] departureData = data[1].split(":");
            LocalTime departureTime = LocalTime.of(Integer.parseInt(departureData[0]),
                    Integer.parseInt(departureData[1]));
            String[] arrivalData = data[2].split(":");
            LocalTime arrivalTime = LocalTime.of(Integer.parseInt(arrivalData[0]),
                    Integer.parseInt(arrivalData[1]));
            Entry entry = new Entry(data[0], departureTime, arrivalTime);
            if (entry.lessThenHour()) {
                entries.add(entry);
            }
        }
    }

    public List read(String filename) {
        List<String> lines = new ArrayList<>();
        Path path = Paths.get(filename);
        try {
            lines = Files.lines(path).collect(Collectors.toList());
        } catch (IOException ex) {
            logger.log(Level.ERROR, "Filename isn't correct!");
        }
        return lines;
    }

    public void modifyTable() {
        Collections.sort(entries, new Comparator<Entry>() {
            @Override
            public int compare(Entry o1, Entry o2) {
                return o1.compareTo(o2);
            }
        });
        for (int i = 0; i < entries.size() - 1; ) {
            while (i + 1 < entries.size() && !entries.get(i).getDepartureTime().isBefore(entries.get(i + 1).getDepartureTime())) {
                entries.remove(i + 1);
            }
            i++;
        }
        for (int i = entries.size() - 1; i > 0; ) {
            if (entries.get(i).isOneDay()) {
                i = 0;
            }
            while (i > 0 && !entries.get(i).isOneDay() && !entries.get(i).getArrivalTime().isBefore(entries.get(0).getArrivalTime())) {
                entries.remove(i);
                i--;
            }
            i = 0;
        }
        logger.log(Level.INFO, "Timetable is modified.");
    }

    public void write() {
        try (FileWriter writer = new FileWriter("output.txt", false)) {
            for (Entry entry : entries) {
                if (entry.getBusType().equals("Posh")) {
                    writer.write(entry + "\n");
                }
            }
            for (Entry entry : entries) {
                if (entry.getBusType().equals("Grotty")) {
                    writer.write("\n" + entry);
                }
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, "Filename isn't correct!");
        }
        logger.log(Level.INFO, "Successfully writing data to file!");
    }

    public void run(String path) {
        List<String> lines = read(path);
        parse(lines);
        modifyTable();
        write();
    }
}
