package frc.robot.subsystems;

import static frc.robot.Constants.CONTROL_PANEL_MOTOR;

import java.util.List;
import java.util.logging.Logger;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.ColorMatch;

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
    private DoubleSolenoid cpSolenoid = new DoubleSolenoid(5, 4);
    private WPI_VictorSPX cpMotor = new WPI_VictorSPX(CONTROL_PANEL_MOTOR);
    private ControlPanelColorSensor colorSensor = new ControlPanelColorSensor(Port.kOnboard);;
    private DriverStation ds = DriverStation.getInstance();
    private ControlPanelColor turnToColor;

    private List<String> colorNames;
    private List<Color> _colors;
    

    //private LinkedHashMap<String, Color> colors;

    public ControlPanelSubsystem() {
        // colors = new LinkedHashMap<String, Color>();
        // colors.put("B", new Color(0.175, 0.469, 0.356));
        // colors.put("G", new Color(0.188, 0.503, 0.310));
        // colors.put("R", new Color(0.325, 0.453, 0.221));
        // colors.put("Y", new Color(0.269, 0.550, 0.181));

        colorNames.add("B");
        colorNames.add("G");
        colorNames.add("R");
        colorNames.add("Y");

        _colors.add(new Color(0.175, 0.469, 0.356));
        _colors.add(new Color(0.188, 0.503, 0.310));
        _colors.add(new Color(0.325, 0.453, 0.221));
        _colors.add(new Color(0.269, 0.550, 0.181));

    }

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

    public Color getColor() {
        return colorSensor.getRawColor();
    }

    public Color getTurnToColor() {
        return turnToColor.getRawColor();
    }

    @Override
    public void periodic() {
        if (ds.getGameSpecificMessage().length() != 1) return;

        ControlPanelColor color = ControlPanelColor.getColorFromCode(ds.getGameSpecificMessage());

        if (color == null) {
            logger.warning(
                "Game data for control panel color was not R, G, B, or Y. Actual: " + ds.getGameSpecificMessage()
            );
            return;
        }

        if (turnToColor == null) {
            turnToColor = color.getTurnToColor();
        }
    }

}