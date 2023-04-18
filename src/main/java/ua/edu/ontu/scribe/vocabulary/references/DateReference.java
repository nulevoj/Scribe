package ua.edu.ontu.scribe.vocabulary.references;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;

public class DateReference {

    private static final ZoneId ZONE_UA = ZoneId.of("Europe/Kiev");

    private final LocalDate date;

    private final Map<String, String> map;

    public DateReference(Map<String, String> map) {
        this.map = map;
        date = LocalDate.now(ZONE_UA);
    }

    public void putDateReferences() {
        map.put("year", getYear());
        map.put("date", getDate());
        map.put("today", getToday());
    }

    private String getYear() {
        return String.valueOf(date.getYear());
    }

    private String getDate() {
        return date.getDayOfMonth() + " " + getGenitiveMonthUA(date.getMonth().getValue());
    }

    private String getToday() {
        return date.getDayOfMonth() + "." + String.format("%02d", date.getMonth().getValue()) + "." + date.getYear();
    }

    private static String getGenitiveMonthUA(int month) {
        return switch (month) {
            case 1 -> "січня";
            case 2 -> "лютого";
            case 3 -> "березня";
            case 4 -> "квітня";
            case 5 -> "травня";
            case 6 -> "червня";
            case 7 -> "липня";
            case 8 -> "серпня";
            case 9 -> "вересня";
            case 10 -> "жовтня";
            case 11 -> "листопада";
            case 12 -> "грудня";
            default -> "________";
        };
    }

}
