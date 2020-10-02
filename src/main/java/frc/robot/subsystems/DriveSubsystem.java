/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                                                */
/* Open Source Software - may be modified and shared by FRC teams. The code     */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                                                                                             */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.*;

public class DriveSubsystem extends SubsystemBase { 
    private CANSparkMax frontLeftMotor = new CANSparkMax(FRONT_LEFT_MOTOR, MotorType.kBrushless);
    private CANSparkMax frontRightMotor = new CANSparkMax(FRONT_RIGHT_MOTOR, MotorType.kBrushless);
    private CANSparkMax rearLeftMotor = new CANSparkMax(REAR_LEFT_MOTOR, MotorType.kBrushless);
    private CANSparkMax rearRightMotor = new CANSparkMax(REAR_RIGHT_MOTOR, MotorType.kBrushless);

    private DifferentialDrive differentialDrive = new DifferentialDrive(
        new SpeedControllerGroup(frontLeftMotor, rearLeftMotor),
        new SpeedControllerGroup(frontRightMotor, rearRightMotor)
    );
    /**
     * Creates a new DriveSubsystem.
     */
    public DriveSubsystem() {
        
    }

    public void drive(double speed, double rotation) {
        differentialDrive.arcadeDrive(speed, rotation);
    }
}
