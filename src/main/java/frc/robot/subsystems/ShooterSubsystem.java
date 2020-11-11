package frc.robot.subsystems;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.*;

public class ShooterSubsystem extends SubsystemBase {
    private CANSparkMax shooterMotor1 = new CANSparkMax(SHOOTER_MOTOR_1, MotorType.kBrushless);
    private CANSparkMax shooterMotor2 = new CANSparkMax(SHOOTER_MOTOR_2, MotorType.kBrushless);
    private CANPIDController shooterController = shooterMotor1.getPIDController();

    
    public ShooterSubsystem() {
        shooterMotor2.follow(shooterMotor1);
    }

    public void activatePiston() {
        
    }

    /**
     * @param shooterSpeed Velocity of shooter motors in RPM
     */
    public void shooter(double shooterSpeed) {
        shooterController.setReference(shooterSpeed, ControlType.kVelocity);
    }

}
