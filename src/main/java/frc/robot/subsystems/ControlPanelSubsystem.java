package frc.robot.subsystems;

import static frc.robot.Constants.CONTROL_PANEL_MOTOR;
import static frc.robot.Constants.CONTROL_PANEL_SOLENOID_FWD;
import static frc.robot.Constants.CONTROL_PANEL_SOLENOID_REV;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.common.ControlPanelColor;
import frc.robot.common.ControlPanelColorSensor;
import java.util.Arrays;
import java.util.logging.Logger;

public class ControlPanelSubsystem extends SubsystemBase {

  private Logger logger = Logger.getLogger("ControlPanelSubsystem");
  private DoubleSolenoid cpSolenoid = new DoubleSolenoid(CONTROL_PANEL_SOLENOID_FWD,
      CONTROL_PANEL_SOLENOID_REV);
  private WPI_VictorSPX cpMotor = new WPI_VictorSPX(CONTROL_PANEL_MOTOR);
  private ControlPanelColorSensor colorSensor = new ControlPanelColorSensor(Port.kOnboard,
      Arrays.asList(ControlPanelColor.getValidColors()));
  private DriverStation ds = DriverStation.getInstance();
  private ControlPanelColor turnToColor;

  public void spin(double speed) {
    cpMotor.set(ControlMode.PercentOutput, speed);
  }

  public void setSolenoid(boolean extend) {
    if (extend) {
      cpSolenoid.set(Value.kForward);
    } else {
      cpSolenoid.set(Value.kReverse);
    }
    //        cpSolenoid.set(extend ? Value.kForward : Value.kReverse);
  }

  public ControlPanelColor getDetectedColor() {
    return colorSensor.getDetectedColor();
  }

  public ControlPanelColor getTurnToColor() {
    return turnToColor;
  }

  private ControlPanelColor calculateTurnToColor() {
    ControlPanelColor dsColor = ControlPanelColor.valueOf(ds.getGameSpecificMessage());
    int totalColors = ControlPanelColor.getValidColors().length;
    return ControlPanelColor.getValidColors()[(dsColor.ordinal() + 2) % totalColors];
  }

  @Override
  public void periodic() {
    if (ds.getGameSpecificMessage()
        .isEmpty()) {
      return;
      a
    } if (this.turnToColor == ControlPanelColor.UNKNOWN) {
      this.turnToColor = calculateTurnToColor();
    }
  }
}