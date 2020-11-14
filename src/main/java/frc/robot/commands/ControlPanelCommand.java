package frc.robot.commands;

import com.revrobotics.ColorMatchResult;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.util.Color;

import frc.robot.subsystems.ControlPanelSubsystem;

public class ControlPanelCommand extends CommandBase {

    ControlPanelSubsystem controlPanel;

    public ControlPanelCommand() {
        controlPanel = new ControlPanelSubsystem();
    }


    @Override
    public void execute() {

        if(controlPanel.getDS().getGameSpecificMessage().length() > 0 && controlPanel.getFMSColor() != null) {
            controlPanel.getFMSColor();
        }

        if(controlPanel.getSolenoidState() == DoubleSolenoid.Value.kForward) {
                Color readColor = controlPanel.getColorSensor().getColor();
                ColorMatchResult resultColor = controlPanel.getColorMatcher().matchClosestColor(readColor);
                controlPanel.setDetectedColor(resultColor.color);
        }
        // controlPanel.getCPMoter().set(VictorSPXControlMode.Velocity, (double)controlPanel.getSpeed());
    }
}
