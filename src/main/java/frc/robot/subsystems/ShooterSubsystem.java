package frc.robot.subsystems;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.*;

public class ShooterSubsystem extends SubsystemBase {
    private CANSparkMax shooterMotor1 = new CANSparkMax(SHOOTER_MOTOR_1, MotorType.kBrushed);
    private CANSparkMax shooterMotor2 = new CANSparkMax(SHOOTER_MOTOR_2, MotorType.kBrushed);
    private Solenoid shooterSolenoid = new Solenoid(0);
    private CANPIDController shooterController = shooterMotor1.getPIDController();

    
    public ShooterSubsystem() {
        shooterMotor1.setInverted(true);
        shooterMotor2.follow(shooterMotor1);
    }

    public void activatePiston() {
        shooterSolenoid.set(true);
    }

    public void lowerPiston() {
        shooterSolenoid.set(false);
    }

    /**
     * @param shooterSpeed Velocity of shooter motors in RPM
     */
    public void shoot(double shooterSpeed) {
        // shooterController.setReference(shooterSpeed, ControlType.kVelocity);
        shooterMotor1.set(shooterSpeed);
    }

}
