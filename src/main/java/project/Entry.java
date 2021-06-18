package project;

import java.time.LocalTime;
import java.util.Objects;

public class Entry implements Comparable<Entry> {
    private String busType;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private boolean isOneDay;

    public Entry(String busType, LocalTime departureTime, LocalTime arrivalTime) {
        this.busType = busType;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.isOneDay = departureTime.isBefore(arrivalTime);
    }

    public String getBusType() {
        return busType;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public boolean isOneDay() {
        return isOneDay;
    }

    public boolean lessThenHour() {
        if (isOneDay) {
            if (arrivalTime.getHour() == departureTime.getHour()) {
                return true;
            } else if (arrivalTime.getHour() - departureTime.getHour() == 1 && arrivalTime.getMinute() <= departureTime.getMinute()) {
                return true;
            }
        } else {
            if (departureTime.getHour() == 23 && arrivalTime.getHour() == 0) {
                if (arrivalTime.getMinute() <= departureTime.getMinute()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return busType + ' ' + departureTime + ' ' + arrivalTime;
    }

    @Override
    public int compareTo(Entry o) {
        if (isOneDay == o.isOneDay) {
            if (!arrivalTime.equals(o.arrivalTime)) {
                return arrivalTime.compareTo(o.arrivalTime);
            }
            if (!departureTime.equals(o.departureTime)) {
                return -departureTime.compareTo(o.departureTime);
            }
            if (!busType.equals(o.busType)) {
                if (busType.equals("Posh")) {
                    return -1;
                } else {
                    return 1;
                }
            }
            return 0;
        } else if (isOneDay) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entry)) return false;
        Entry entry = (Entry) o;
        return isOneDay() == entry.isOneDay() &&
                getBusType().equals(entry.getBusType()) &&
                getDepartureTime().equals(entry.getDepartureTime()) &&
                getArrivalTime().equals(entry.getArrivalTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBusType(), getDepartureTime(), getArrivalTime(), isOneDay());
    }
}
