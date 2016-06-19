package jp.co.skill.spider.ss01.common.imple;

import jp.co.skill.spider.model.Vehicle;

@FunctionalInterface
public interface VehicleDriver {

	public Vehicle getVehicle();

	public default void driveVehicle() {
		getVehicle().drive();
	}

	public default void cleanVehicle() {
		getVehicle().clean();
	}
}
