package frc.robot.subsystems.pneumatics;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

public class Pneumatics {
    // private final PneumaticsControlModule pcm = new PneumaticsControlModule(0);
    private final Solenoid piston = new Solenoid(PneumaticsModuleType.CTREPCM, 0);

    public void toggle() {
        System.out.println("toggling piston");
        piston.set(!piston.get());
    }
}
