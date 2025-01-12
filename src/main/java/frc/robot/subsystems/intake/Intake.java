package frc.robot.subsystems.intake;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

public class Intake {
    private TalonFX talon = new TalonFX(9);
    private TalonFXConfiguration config = new TalonFXConfiguration();
    // private double speed = 0.375;
    // private DutyCycleOut dutyCycleOut = new DutyCycleOut(0);
    private VoltageOut intakeVolts = new VoltageOut(4.5);
    private VoltageOut outtakeVolts = new VoltageOut(-4.5);

    public Intake() {
        // dutyCycleOut.EnableFOC = true;
        intakeVolts.EnableFOC = true;
        outtakeVolts.EnableFOC = true;
        config.CurrentLimits.SupplyCurrentLimit = 20;
        talon.getConfigurator().apply(config);
    }

    public void intake() {
        // dutyCycleOut.withOutput(speed);
        // talon.setControl(dutyCycleOut);
        // voltageOut.withOutput(4.5);
        // talon.setControl(voltageOut);
        talon.setControl(intakeVolts);
    }

    public void outtake() {
        // dutyCycleOut.withOutput(-speed);
        // talon.setControl(dutyCycleOut);
        // voltageOut.withOutput(-4.5);
        // talon.setControl(voltageOut);
        talon.setControl(outtakeVolts);
    }

    public void stop() {
        // talon.stopMotor();
        talon.setControl(new VoltageOut(0));
    }
}
