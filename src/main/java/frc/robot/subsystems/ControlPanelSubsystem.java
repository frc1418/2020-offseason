package frc.robot.subsystems;

// import java.util.HashMap;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatch;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.util.Color;

public class ControlPanelSubsystem extends SubsystemBase {

    private VictorSPX cpMotor;
    private DoubleSolenoid cpSolenoid;
    private ColorSensorV3 colorSensor;
    private DriverStation ds;
    private ColorMatch colorMatcher;

    private Color turnToColor = null;
    private Color detectedColor = null;
    private Color fmsColor = null;
    private Color lastColor = null;

    private List<String> colorNames;
    private List<Color> _colors;
    

    //private LinkedHashMap<String, Color> colors;

    private int speed = 0;
    private Value solenoidState = Value.kReverse;

    public ControlPanelSubsystem(){
        cpMotor = new VictorSPX(2);
        cpSolenoid = new DoubleSolenoid(5, 4);
        colorSensor = new ColorSensorV3(Port.kOnboard);
        ds = DriverStation.getInstance();
        colorMatcher = new ColorMatch();

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

        for(Color color : _colors){
            colorMatcher.addColorMatch(color);
        }
        colorMatcher.addColorMatch(Color.kBlack);
    }

    public void spin(int speed){
        this.speed = speed;
    }

    public void setSolenoid(boolean extend){
        if (extend){
            solenoidState = Value.kForward;
        }
            
        else{
            solenoidState = Value.kReverse;
        }
    }

    public Color getFMSColor(){
        int fmsColorIndex =  _colors.indexOf(ds.getGameSpecificMessage());
        fmsColor = _colors.get(fmsColorIndex);

        turnToColor = _colors.get((fmsColorIndex + 2) % _colors.size());

        return fmsColor;
    }

    public DriverStation getDS() {
        return ds;
    }

    public ColorSensorV3 getColorSensor() {
        return colorSensor;
    }

    public ColorMatch getColorMatcher() {
        return colorMatcher;
    }

    public Color getDetectedColor(){
        return detectedColor;
    }

    public void setDetectedColor(Color detectedColor) {
        this.detectedColor = detectedColor;
    }

    public VictorSPX getCPMoter(){
        return cpMotor;
    }

    public DoubleSolenoid getCPSolenoid(){
        return cpSolenoid;
    }

    public int getSpeed(){
        return speed;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    public Value getSolenoidState(){
        return solenoidState;
    }

    public List<Color> getColors(){
        return _colors;
    }
}