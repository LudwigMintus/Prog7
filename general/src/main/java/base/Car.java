package base;

import java.io.Serializable;

public class Car implements Serializable {
    private Boolean cool; //Поле не может быть null

    public Car(Boolean cool) {
        this.cool = cool;
    }
    public Car() {
    }
    public Boolean getCool() {
        return cool;
    }

    public void setCool(Boolean cool) {
        this.cool = cool;
    }


    @Override
    public String toString() {
        return "(" + "cool=" + cool + ')';
    }


}