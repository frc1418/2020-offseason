package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.*;

public class IntakeSubsystem extends SubsystemBase {
    private VictorSPX upperIntakeMotor = new VictorSPX(UPPER_INTAKE_MOTOR);
    private VictorSPX bottomIntakeMotor = new VictorSPX(BOTTOM_INTAKE_MOTOR);
    private DigitalInput intakeSwitch = new DigitalInput(INTAKE_SWITCH);
    private DoubleSolenoid intakeSolenoid = new DoubleSolenoid(DOUBLE_SOLENOID);
    private Trigger intakeSwitchButton = new Trigger(intakeSwitch::get);

    private int ballsCollected = 0;
    private boolean isAlreadyPushed = false;

    public IntakeSubsystem() {
        upperIntakeMotor.follow(bottomIntakeMotor);
        upperIntakeMotor.setInverted(InvertType.OpposeMaster);
        intakeSwitchButton.whenActive(new InstantCommand(() -> ballsCollected++));
    }

    // Solenoid methods

    public void extend() {
        intakeSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void spin(double speed) {
        bottomIntakeMotor.set(VictorSPXControlMode.PercentOutput, speed);
    }



    // @Override
    // public void periodic() {
    //     // This variable is true if the switch is pushed and false if it isn't
    //     boolean isSwitchPushed = intakeSwitch.get();
        
    //     if (isSwitchPushed == true && isAlreadyPushed == false){
    //         ballsCollected++;
    //         isAlreadyPushed = true;
    //     }

    //     if (isSwitchPushed == false) {
    //         isAlreadyPushed = false;
    //     }
    // }

}
