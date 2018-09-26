package com.javarush.task.task36.task3609;

public class CarController {
    private CarModel model;
    private SpeedometerView view;

    public CarController(CarModel model, SpeedometerView view) {
        this.model = model;
        this.view = view;
    }

    public void speedUp(int seconds) {
        int speed = getCarSpeed();
        int maxSpeed = getCarMaxSpeed();
        if (speed < maxSpeed) {
            setCarSpeed((int) (3.5 * seconds));
        }
        if (speed > maxSpeed) {
            setCarSpeed(maxSpeed);
        }
    }

    public void speedDown(int seconds) {
        int speed = getCarSpeed();
        if (speed > 0) {
            setCarSpeed((int)(12 * seconds));
        }
        if (speed < 0) {
            setCarSpeed(0);
        }
    }

    public String getCarBrand() {
        return model.getBrand();
    }

    public String getCarModel() {
        return model.getModel();
    }

    public void setCarSpeed(int speed) {
        model.setSpeed(speed);
    }

    public int getCarSpeed() {
        return model.getSpeed();
    }

    public int getCarMaxSpeed() {
        return model.getMaxSpeed();
    }

    public void updateView() {
        view.printCarDetails(getCarBrand(), getCarModel(), getCarSpeed());
    }
}