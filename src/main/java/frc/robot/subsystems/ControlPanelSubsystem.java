package frc.robot.subsystems;

import java.util.List;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatch;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.util.Color;

public class ControlPanelSubsystem extends SubsystemBase {

    public VictorSPX cpMotor;
    public DoubleSolenoid cpSolenoid;
    public ColorSensorV3 colorSensor;
    public DriverStation ds;
    public ColorMatch colorMatcher;

    Color turnToColor = null;
    public Color detectedColor = null;
    public Color fmsColor = null;
    Color lastColor = null;

    List<String> colorNames;
    public List<Color> colors;

    //TODO: Both of these were "will_reset_to" variables in python, but I couldn't find a java alternative
    public int speed = 0;
    public DoubleSolenoid.Value solenoidState = DoubleSolenoid.Value.kReverse;

    public ControlPanelSubsystem(){
        cpMotor = new VictorSPX(2);
        cpSolenoid = new DoubleSolenoid(5, 4);
        colorSensor = new ColorSensorV3(Port.kOnboard);
        ds = DriverStation.getInstance();
        colorMatcher = new ColorMatch();

        colorNames.add("B");
        colorNames.add("G");
        colorNames.add("R");
        colorNames.add("Y");

        colors.add(new Color(0.175, 0.469, 0.356));
        colors.add(new Color(0.188, 0.503, 0.310));
        colors.add(new Color(0.325, 0.453, 0.221));
        colors.add(new Color(0.269, 0.550, 0.181));
    }

    public void spin(int speed){
        this.speed = speed;
    }

    public void setSolenoid(boolean extend){
        if (extend)
            solenoidState = DoubleSolenoid.Value.kForward;
        else
        solenoidState = DoubleSolenoid.Value.kReverse;
    }

    public void getFMSColor(){
        int fmsColorIndex = colorNames.indexOf(ds.getGameSpecificMessage());
        fmsColor = colors.get(fmsColorIndex);

        turnToColor = colors.get((fmsColorIndex + 2) % colors.size());
    }

    public static double calculateDistance(Color color1, Color color2){
        double redDiff = color1.red - color2.red;
        double greenDiff = color1.green - color2.green;
        double blueDiff = color1.blue - color2.blue;

        return Math.sqrt((redDiff * redDiff + greenDiff * greenDiff + blueDiff * blueDiff) / 2);
    }
}