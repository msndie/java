package edu.school21.testClassTwo;

import java.util.StringJoiner;

public class Car {
	private String brand;
	private String model;
	private int speed;

	public Car() {
		this.brand = "Default brand";
		this.model = "Default model";
		this.speed = 0;
	}

	public Car(String brand, String model, int speed) {
		this.brand = brand;
		this.model = model;
		this.speed = speed;
	}

	public int speedUp(int value) {
		this.speed += value;
		return speed;
	}

	@Override
	public String toString() {
	return new StringJoiner(", ", Car.class.getSimpleName() + "[", "]")
			.add("brand='" + brand + "'")
			.add("model='" + model + "'")
			.add("speed=" + speed)
			.toString();
	}
}
