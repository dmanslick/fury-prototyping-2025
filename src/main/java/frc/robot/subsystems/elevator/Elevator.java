package frc.robot.subsystems.elevator;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Elevator extends SubsystemBase {
  private ElevatorIOTalonFX io = new ElevatorIOTalonFX();

  public Elevator(ElevatorIOTalonFX io) {
    this.io = io;
  }

  @Override 
  public void periodic() {
    io.updateInputs();
  }

  public void setPosition(double position) {
    if (io.getPosition() < -1) {
      io.stop();
    } else {
      io.setPosition(position);
    }
  }

  public void raise() {
    if (io.getPosition() < -1) {
      io.stop();
    } else {
      setPosition(io.getPosition() + 0.5);
    }
  }

  public void lower() {
    if (io.getPosition() <= 0) {
      io.stop();
    } else {
      setPosition(io.getPosition() - 0.5);
    }
  }

  public void stop() {
    io.stop();
  }

  public void resetRotationCount() {
    io.resetRotationCount();
  }

  public double getPosition() {
    return io.getPosition();
  }

  public void setNeutralMode(NeutralModeValue mode) {
    io.setNeutralMode(mode);
  }

  public TalonFX getMotor() {
    return io.getMotor();
  }
}
