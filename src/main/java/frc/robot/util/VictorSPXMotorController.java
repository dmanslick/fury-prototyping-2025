package frc.robot.util;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class VictorSPXMotorController implements MotorController {
    private VictorSPX motor;

    public VictorSPXMotorController(int id) {
        motor = new VictorSPX(id);
    }

    public void set(double speed) {
        motor.set(ControlMode.PercentOutput, speed);
    }

    public void disable() {
        motor.set(ControlMode.PercentOutput, 0);
    }

    public void stop() {
        motor.set(ControlMode.PercentOutput, 0);
    }

    public double get() {
        return motor.getMotorOutputPercent();
    }

    public void setInverted(boolean invert) {
        motor.setInverted(invert);
    }

    public boolean getInverted() {
        return motor.getInverted();
    }

    public void stopMotor() {
        disable();
    }

    public VictorSPX getMotor() {
        return motor;
    }
}
