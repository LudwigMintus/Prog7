package base;

import java.io.Serializable;

public enum WeaponType implements Serializable {

    HAMMER("Молоток"), SHOTGUN("Дробовик"), MACHINE_GUN("Пулемет");

    private final String title;

    WeaponType(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return title;
    }

    public static WeaponType fromString(String weaponTypeStr) {
        for (WeaponType weaponType : WeaponType.values()) {
            if (weaponType.toString().equalsIgnoreCase(weaponTypeStr))
                return weaponType;
        }
        return null;
    }
}
