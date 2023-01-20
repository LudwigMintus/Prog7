package base;

import java.io.Serializable;

public enum Mood implements Serializable {
    SORROW("Печаль"), LONGING("Тоска"), GLOOM("Мрак"), APATHY("Апатия"),
    FRENZY("Безумие");

    private final String title;

    Mood(String title) {
        this.title = title;
    }

    public static String getFromNum(int number) {
        switch (number) {
            case 0:
                return SORROW.title;
            case 1:
                return LONGING.title;
            case 2:
                return GLOOM.title;
            case 3:
                return APATHY.title;
            case 4:
                return FRENZY.title;
            default:
                return SORROW.title;
        }
    }

    @Override
    public String toString() {
        return title;
    }

    public static Mood fromString(String moodstr) {
        for (Mood mood : Mood.values()) {
            if (mood.toString().equalsIgnoreCase(moodstr))
                return mood;
        }
        return null;
    }
}
