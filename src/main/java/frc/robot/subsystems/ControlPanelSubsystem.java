package frc.robot.subsystems;

import static frc.robot.Constants.*;

import java.util.logging.Logger;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.common.ControlPanelColorSensor;
import frc.robot.common.ControlPanelColorSensor.ControlPanelColor;

public class ControlPanelSubsystem extends SubsystemBase {

    private Logger logger = Logger.getLogger("ControlPanelSubsystem");
    private DoubleSolenoid cpSolenoid = new DoubleSolenoid(CONTROL_PANEL_SOLENOID_FWD, CONTROL_PANEL_SOLENOID_REV);
    private WPI_VictorSPX cpMotor = new WPI_VictorSPX(CONTROL_PANEL_MOTOR);
    private ControlPanelColorSensor colorSensor = new ControlPanelColorSensor(Port.kOnboard);
    private DriverStation ds = DriverStation.getInstance();
    private ControlPanelColor turnToColor;

    public void spin(double speed) {
        cpMotor.set(ControlMode.PercentOutput, speed);
    }

    public void setSolenoid(boolean extend) {
        if (extend) {
            cpSolenoid.set(Value.kForward);
        } else{
            cpSolenoid.set(Value.kReverse);
        }
    }

    public ControlPanelColor getControlPanelColor() {
        return ControlPanelColor.getColorFromMatch(colorSensor.getMatchedColor());
    }

    public ControlPanelColor getTurnToColor() {
        return turnToColor;
    }

    @Override
    public void periodic() {
        if (ds.getGameSpecificMessage().isEmpty()) return;

        // TODO: Once getColorFromCode throws an IllegalArgumentException, we should try catch it here
        turnToColor = ControlPanelColor.getColorFromCode(ds.getGameSpecificMessage()).getTurnToColor();
    }

}