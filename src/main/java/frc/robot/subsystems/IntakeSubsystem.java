package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import static frc.robot.Constants.*;

public class IntakeSubsystem extends SubsystemBase {
    private WPI_VictorSPX upperIntakeMotor = new WPI_VictorSPX(UPPER_INTAKE_MOTOR);
    private WPI_VictorSPX bottomIntakeMotor = new WPI_VictorSPX(BOTTOM_INTAKE_MOTOR);
    private DigitalInput intakeSwitch = new DigitalInput(INTAKE_SWITCH);
    private DoubleSolenoid intakeSolenoid = new DoubleSolenoid(INTAKE_SOLENOID_FWD, INTAKE_SOLENOID_REV);
    private Trigger intakeSwitchButton = new Trigger(intakeSwitch::get);

    private int ballsCollected = 0;
    private boolean isAlreadyPushed = false;

    public IntakeSubsystem() {
        upperIntakeMotor.follow(bottomIntakeMotor);
        upperIntakeMotor.setInverted(InvertType.OpposeMaster);
        intakeSwitchButton.whenActive(new InstantCommand(() -> { 
            ballsCollected++;
            System.out.println("Ball count: " + ballsCollected);
        }));
    }

    public void spin(double spinSpeed) {
        bottomIntakeMotor.set(ControlMode.PercentOutput, spinSpeed);
    }

    // Solenoid methods

    public void extend() {
        intakeSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void retract() {
        intakeSolenoid.set(DoubleSolenoid.Value.kForward);
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
