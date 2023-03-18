package ua.edu.ontu.scribe.reference;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;

public class DateReference {

    private static final ZoneId ZONE_UA = ZoneId.of("Europe/Kiev");

    private LocalDate date;

    public Map<String, String> getDateReferences() {
        date = LocalDate.now(ZONE_UA);
        Reference reference = new Reference();
        reference.putUnique("year", getYear());
        reference.putUnique("date", getDate());
        reference.putUnique("full date", getFullDate());
        return reference.getMap();
    }

    private String getYear() {
        return String.valueOf(date.getYear());
    }

    private String getDate() {
        return date.getDayOfMonth() + " " + GenitiveMonthUA.getMonth(date.getMonth().getValue());
    }

    private String getFullDate() {
        return date.getDayOfMonth() + "." + String.format("%02d", date.getMonth().getValue()) + "." + date.getYear();
    }

    private enum GenitiveMonthUA {
        JANUARY("січня"),
        FEBRUARY("лютого"),
        MARCH("березня"),
        APRIL("квітня"),
        MAY("травня"),
        JUNE("червня"),
        JULY("липня"),
        AUGUST("серпня"),
        SEPTEMBER("вересня"),
        OCTOBER("жовтня"),
        NOVEMBER("листопада"),
        DECEMBER("грудня");

        private final String name;

        GenitiveMonthUA(String name) {
            this.name = name;
        }

        public static String getMonth(int month) {
            for (GenitiveMonthUA m : GenitiveMonthUA.values()) {
                if (m.ordinal() == month - 1) {
                    return m.name;
                }
            }
            return "________";
        }
    }
}
