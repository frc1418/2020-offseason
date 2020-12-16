package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ControlPanelSubsystem;

public class ControlPanelColorCommand extends CommandBase {
  private ControlPanelSubsystem controlPanel;
  private float direction;

    public ControlPanelColorCommand() {
        // TODO: Why won't this line do what we want? Let's think about it
        controlPanel = new ControlPanelSubsystem();
    }

  @Override
  public void initialize() {
    controlPanel.setSolenoid(true);
    direction = Math.signum(controlPanel.getTurnToColor()
        .ordinal() - controlPanel.getDetectedColor()
        .ordinal());
  }

  @Override
  public void end(boolean interrupted) {
    controlPanel.setSolenoid(false);
    controlPanel.spin(0);
  }

  @Override
  public boolean isFinished() {
    return controlPanel.getDetectedColor()
        .equals(controlPanel.getTurnToColor());
  }

  @Override
  public void execute() {
    controlPanel.spin(0.5 * direction);
  }
}
