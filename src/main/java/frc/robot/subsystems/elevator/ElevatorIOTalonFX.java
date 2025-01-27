package frc.robot.subsystems.elevator;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class ElevatorIOTalonFX {
  private TalonFX leader = new TalonFX(30);
  // private ShuffleboardTab tab = Shuffleboard.getTab("Shuffleboard");
  // private GenericEntry kS = tab.add("kS", 0).getEntry();
  // private GenericEntry kV = tab.add("kV", 0).getEntry();
  // private GenericEntry kG = tab.add("kG", 0).getEntry();
  // private GenericEntry kP = tab.add("kP", 0).getEntry();
  // private GenericEntry kI = tab.add("kI", 0).getEntry();
  // private GenericEntry kD = tab.add("kD", 0).getEntry();
  // private GenericEntry cruiseVelocity = tab.add("cruiseVelocity", 0).getEntry();
  // private GenericEntry acceleration = tab.add("acceleration", 0).getEntry();
  // private GenericEntry jerk = tab.add("jerk", 0).getEntry();
  private double kS = 0;
  private double kV = 0.1;
  private double kG = 0.2;
  private double kP = 1.75;
  private double kI = 0;
  private double kD = 0;
  private double cruiseVelocity = 30;
  private double acceleration = 60;
  private double jerk = 200;
  private TalonFXConfiguration talonFXConfig = new TalonFXConfiguration();
  private Slot0Configs slot0Configs = talonFXConfig.Slot0;
  private MotionMagicConfigs motionMagicConfigs = talonFXConfig.MotionMagic;

  public ElevatorIOTalonFX() {
    updateInputs();
  }

  public void updateInputs() {
    // slot0Configs.kS = kS.getDouble(0);  
    // slot0Configs.kV = kV.getDouble(0);  
    // slot0Configs.kG = kG.getDouble(0);  
    // slot0Configs.kP = kP.getDouble(0);  
    // slot0Configs.kI = kI.getDouble(0); 
    // slot0Configs.kD = kD.getDouble(0);

    // motionMagicConfigs.MotionMagicCruiseVelocity = cruiseVelocity.getDouble(0);
    // motionMagicConfigs.MotionMagicAcceleration = acceleration.getDouble(0);
    // motionMagicConfigs.MotionMagicJerk = jerk.getDouble(0);
    
    slot0Configs.kS = kS;  
    slot0Configs.kV = kV;  
    slot0Configs.kG = kG;  
    slot0Configs.kP = kP;  
    slot0Configs.kI = kI; 
    slot0Configs.kD = kD;
    slot0Configs.GravityType = GravityTypeValue.Elevator_Static;

    motionMagicConfigs.MotionMagicCruiseVelocity = cruiseVelocity;
    motionMagicConfigs.MotionMagicAcceleration = acceleration;
    motionMagicConfigs.MotionMagicJerk = jerk;

    leader.getConfigurator().apply(talonFXConfig);
    leader.setNeutralMode(NeutralModeValue.Brake);
    }

  public double getPosition() {
    return leader.getPosition().getValueAsDouble();
  }

  public void setPosition(double position) {
    System.out.println("Set position called with position: " + position);
    var request = new MotionMagicDutyCycle(0, true, 0, 0, false, false, false);
    leader.setControl(request.withPosition(position));
    System.out.println("Control request should've been sent");
  }

  public void stop() {
    leader.stopMotor();
  }

  public void resetRotationCount() {
    leader.setPosition(0);
  }

  public void setNeutralMode(NeutralModeValue mode) {
    leader.setNeutralMode(mode);
  }

  public TalonFX getMotor() {
    return leader;
  }
}
