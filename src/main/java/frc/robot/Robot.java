// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.elevator.Elevator;
import frc.robot.subsystems.elevator.ElevatorIOTalonFX;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.pneumatics.Pneumatics;
import frc.robot.util.TalonSRXMotorController;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private final Joystick leftJoystick = new Joystick(0);
  private final Joystick rightJoystick = new Joystick(1);
  private Elevator elevator = new Elevator(new ElevatorIOTalonFX());
  private Pneumatics pneumatics = new Pneumatics();
  private Intake intake = new Intake();
  private final TalonSRXMotorController rightLeader = new TalonSRXMotorController(Constants.rightLeaderId);
  private final TalonSRXMotorController rightFollower = new TalonSRXMotorController(Constants.rightFollowerId);
  private final TalonSRXMotorController leftLeader = new TalonSRXMotorController(Constants.leftLeaderId);
  private final TalonSRXMotorController leftFollower = new TalonSRXMotorController(Constants.leftFollowerId);
  private final DifferentialDrive differentialDrive = new DifferentialDrive(rightLeader::set, leftLeader::set);
  private Command m_autonomousCommand;
  // private RobotContainer m_robotContainer;
  private double maxSpeed = 1;

  @Override
  public void robotInit() {
    // m_robotContainer = new RobotContainer();
    leftFollower.getMotor().follow(leftLeader.getMotor());
    rightFollower.getMotor().follow(rightLeader.getMotor());
    leftLeader.setInverted(true);
    leftFollower.setInverted(true);
    elevator.resetRotationCount();
    elevator.getMotor().clearStickyFaults();
    // ShuffleboardTab tab = Shuffleboard.getTab("Shuffleboard");
    // tab.add("kS", 0);
    // tab.add("kV", 0);
    // tab.add("kG", 0);
    // tab.add("kP", 0);
    // tab.add("kI", 0);
    // tab.add("kD", 0);
    // tab.add("cruiseVelocity", 0);
    // tab.add("acceleration", 0);
    // tab.add("jerk", 0);
  }

  @Override
  public void robotPeriodic() {
    // CommandScheduler.getInstance().run();
    Shuffleboard.update();
  }
  
  @Override
  public void disabledInit() {
    // elevator.setNeutralMode(NeutralModeValue.Coast);
  }

  @Override
  public void disabledPeriodic() {}

  @Override
  public void autonomousInit() {
    // m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // if (m_autonomousCommand != null) {
    //   m_autonomousCommand.schedule();
    // }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }

    elevator.setNeutralMode(NeutralModeValue.Brake);
  }

  @Override
  public void teleopPeriodic() {
    differentialDrive.arcadeDrive(
      MathUtil.clamp(maxSpeed, -maxSpeed, leftJoystick.getY()), 
      MathUtil.clamp(maxSpeed, -maxSpeed, -rightJoystick.getX())
    );
    System.out.println("elevator position: " + elevator.getPosition());
    // System.out.println("elevator motor voltage: " + elevator.getMotor().getMotorVoltage().getValueAsDouble());
    // System.out.println("elevator motor supply voltage: " + elevator.getMotor().getSupplyVoltage().getValueAsDouble());
    // System.out.println("elevator motor stator amps: " + elevator.getMotor().getStatorCurrent().getValueAsDouble());
    // System.out.println("elevator motor supply amps: " + elevator.getMotor().getSupplyCurrent().getValueAsDouble());

    if (rightJoystick.getRawButton(1)) {
      System.out.println("left joystick button 2 being held");
      intake.intake();
    } else if (leftJoystick.getRawButton(1)) {
      System.out.println("right joystick button 2 being held");
      intake.outtake();
    } else {
      intake.stop();
    }
    
    // if (leftJoystick.getRawButtonPressed(1)) {
    //   pneumatics.toggle();
    // }

    if (rightJoystick.getRawButtonPressed(8)) {
      System.out.println("right joystick button 8 pressed");
      elevator.resetRotationCount();
    }

    if (rightJoystick.getPOV() == 0) {
      elevator.raise();
    } else if (rightJoystick.getPOV() == 180) {
      elevator.lower();
    }

    if (leftJoystick.getRawButtonPressed(4)) {
      pneumatics.toggle();
    } else if (leftJoystick.getRawButton(8)) {
      elevator.setPosition(0);
    }


    // if (rightJoystick.getRawButton(3)) {
    //   elevator.raise();
    // } else if (rightJoystick.getRawButton(4)) {
    //   elevator.lower();
    // } else if (rightJoystick.getRawButton(5)) {
    //   elevator.setPosition(0);
    // } 
    
    if (rightJoystick.getRawButtonPressed(7)) {
      elevator.setPosition(Constants.ElevatorSetpoints.one);
    } else if (rightJoystick.getRawButtonPressed(9)) {
      elevator.setPosition(Constants.ElevatorSetpoints.two);
    } else if (rightJoystick.getRawButtonPressed(11)) {
      elevator.setPosition(Constants.ElevatorSetpoints.three);
    } else if (rightJoystick.getRawButtonPressed(6)) {
      elevator.setPosition(Constants.ElevatorSetpoints.feeder);
    }
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {
    // System.out.println("left joystick: " + leftJoystick.getY());
    // System.out.println("right joystick: " + rightJoystick.getY());
  }
}
