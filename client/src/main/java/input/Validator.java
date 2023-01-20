package input;

import base.*;

public class Validator {

    private Validator() {
    }

    public static Long validateId(String arg) {
        long id;
        try {
            id = Long.parseLong(arg);
            if (id <= 0)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            return null;
        }
        return id;
    }

    public static String validateName(String arg) {
        if (arg.isEmpty()) {
            return null;
        } else {
            return arg;
        }
    }

    public static Coordinates validateCoordinates(String arg) {
        String[] coordinates = arg.trim().split(";");

        if (coordinates.length != 2) {
            return null;
        }

        String xLine = coordinates[0];
        String yLine = coordinates[1];
        double x, y;

        try {
            x = Double.parseDouble(xLine);
            y = Double.parseDouble(yLine);
            if (x <= (Double) HumanBeing.getLimitation().get("coordinateX")) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return new Coordinates(x, y);
    }

    public static Double validateTypeImpactSpeed(String arg) {
        Double typeImpactSpeed;
        try {
            typeImpactSpeed = Double.parseDouble(arg);
        } catch (NumberFormatException e) {
            return null;
        }
        return typeImpactSpeed;
    }

    public static WeaponType validateWeaponType(String arg) {
        int weaponType;
        try {
            weaponType = Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            return null;
        }
        if (weaponType < 1 || weaponType > WeaponType.values().length)
            return null;
        return WeaponType.values()[weaponType - 1];
    }

    public static Mood validateMood(String arg) {
        int mood;
        try {
            mood = Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            return null;
        }
        if (mood < 1 || mood > Mood.values().length)
            return null;
        return Mood.values()[mood - 1];
    }
}
