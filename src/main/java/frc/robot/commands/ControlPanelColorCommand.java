package frc.robot.commands;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.common.ControlPanelColorSensor.ControlPanelColor;
import frc.robot.subsystems.ControlPanelSubsystem;

public class ControlPanelColorCommand extends CommandBase {
    private ControlPanelSubsystem controlPanel;
    private ControlPanelColor turnToColor;

    public ControlPanelColorCommand(ControlPanelColor turnToColor) {
        // TODO: Why won't this line do what we want? Let's think about it
        controlPanel = new ControlPanelSubsystem();
        this.turnToColor = turnToColor;
    }

    @Override
    public void initialize() {
        // Extend the solenoid if it isn't already
        controlPanel.setSolenoid(true);
    }

    @Override
    public void end(boolean interrupted) {
        controlPanel.setSolenoid(false);
        controlPanel.spin(0);
    }

    @Override
    public boolean isFinished() {
        // TODO: Implement equals for ControlPanelColor and use it here
        return controlPanel.getControlPanelColor().getColor().equals(turnToColor.getColor());
    }

    @Override
    public void execute() {
        double direction = Math.signum(turnToColor.ordinal() - controlPanel.getControlPanelColor().ordinal());
        controlPanel.spin(0.5 * direction);
    }
}
