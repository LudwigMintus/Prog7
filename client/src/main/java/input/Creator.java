package input;

import base.*;

import java.util.Date;

public class Creator {

    private final Typer typer;

    public Creator(Typer typer) {
        this.typer = typer;
    }

    public HumanBeing createHumanBeing() {

        String name = typer.typeName();

        Coordinates coordinates = typer.typeCoordinates();

        boolean realHero = typer.typeRealHero(); // изменить

        Boolean hasToothPick = typer.typeHasToothpick();

        Double impactSpeed = typer.typeImpactSpeed();

        WeaponType weaponType = typer.typeWeaponType();

        Mood mood = typer.typeMood();

        Car car = typer.typeCar() ;

        return new HumanBeing(name, coordinates,new Date(),realHero, hasToothPick, impactSpeed, weaponType, mood, car);
    }

}
