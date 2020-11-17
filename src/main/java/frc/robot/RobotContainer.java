/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                                                */
/* Open Source Software - may be modified and shared by FRC teams. The code     */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                                                                                             */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.logging.Logger;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.networktables.LogMessage;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.common.ColorSensor;
import frc.robot.common.Limelight;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.commands.ChargeAutoCommand;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private final Logger logger = Logger.getLogger("Robot");
    private final Limelight limelight = new Limelight();
    private final DriveSubsystem driveSubsystem = new DriveSubsystem();
    private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
    private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings.    Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
     * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {

        // Joysticks
        Joystick leftJoystick = new Joystick(0);
        Joystick rightJoystick = new Joystick(1);
        Joystick altJoystick = new Joystick(2);
        
        JoystickButton launcherSolenoid = new JoystickButton(altJoystick, 1);
        JoystickButton btnIntakeIn = new JoystickButton(altJoystick, 3);
        JoystickButton btnIntakeOut = new JoystickButton(altJoystick, 4);
        JoystickButton btnLauncherMotor = new JoystickButton(altJoystick, 12);
        JoystickButton btnLauncherIdle = new JoystickButton(altJoystick, 10);
        JoystickButton btnLauncherMotorClose = new JoystickButton(altJoystick, 11);
        JoystickButton btnLauncherMotorDynamic = new JoystickButton(altJoystick, 9);
        JoystickButton btnSlowMovement = new JoystickButton(rightJoystick, 1);
        JoystickButton btnIntakeSolenoid = new JoystickButton(altJoystick, 2);
        JoystickButton btnRotationSensitivity = new JoystickButton(rightJoystick, 1);
        JoystickButton btnIntakeBottomOut = new JoystickButton(altJoystick, 6);

        driveSubsystem.setDefaultCommand(new RunCommand(() -> driveSubsystem.drive(altJoystick.getY(), altJoystick.getX()), driveSubsystem));
        
        shooterSubsystem.setDefaultCommand(new RunCommand(() -> shooterSubsystem.shooter(altJoystick.getThrottle()), shooterSubsystem));
        btnIntakeIn.whileHeld(new RunCommand(() -> intakeSubsystem.spin(-1.0)));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        ParallelRaceGroup charge = new ChargeAutoCommand(driveSubsystem, 0.2).withTimeout(1);
        return charge;
    }
}
