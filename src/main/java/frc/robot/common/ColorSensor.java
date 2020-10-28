package frc.robot.common;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.util.Color;

public class ColorSensor {

    private static final Color RED_TARGET = new Color(0.325, 0.453, 0.221);
    private static final Color YELLOW_TARGET = new Color(0.269, 0.550, 0.181);
    private static final Color BLUE_TARGET = new Color(0.175, 0.469, 0.356);
    private static final Color GREEN_TARGET = new Color(0.188, 0.503, 0.310);

    private ColorMatch colorMatcher = new ColorMatch();
    private ColorSensorV3 colorSensor;

    public ColorSensor(Port port) {
        colorSensor = new ColorSensorV3(port);

        colorMatcher.addColorMatch(RED_TARGET);
        colorMatcher.addColorMatch(YELLOW_TARGET);
        colorMatcher.addColorMatch(GREEN_TARGET);
        colorMatcher.addColorMatch(BLUE_TARGET);
    }

    public String getColor() {
        Color sensedColor = colorSensor.getColor();
        ColorMatchResult match = colorMatcher.matchClosestColor(sensedColor);

        if (match.color == RED_TARGET) {
            return "R";
        } else if (match.color == YELLOW_TARGET) {
            return "Y";
        } else if (match.color == GREEN_TARGET) {
            return "G";
        } else if (match.color == BLUE_TARGET) {
            return "B";
        } else {
            return "Unknown";
        }
    }

    public static Color BART(String colorKey) {
        if (colorKey.equals("R")) {
            return RED_TARGET;
        } else if (colorKey.equals("Y")) {
            return YELLOW_TARGET;
        } else if (colorKey.equals("G")) {
            return GREEN_TARGET;
        } else if (colorKey.equals("B")) {
            return BLUE_TARGET;
        }

        return null;
    }
}
