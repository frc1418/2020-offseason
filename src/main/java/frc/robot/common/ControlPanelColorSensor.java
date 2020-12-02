package frc.robot.common;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.util.Color;

public class ControlPanelColorSensor {

    public static enum ControlPanelColor {
        RED(new Color(0.325, 0.453, 0.221)),
        GREEN(new Color(0.269, 0.550, 0.181)),
        BLUE(new Color(0.175, 0.469, 0.356)),
        YELLOW(new Color(0.188, 0.503, 0.310));

        private Color rawColor;

        private ControlPanelColor(Color rawColor) {
            this.rawColor = rawColor;
        }

        public Color getRawColor() {
            return rawColor;
        }

        public static ControlPanelColor getColorFromCode(String colorKey) {
            if (colorKey.equals("R")) {
                return RED;
            } else if (colorKey.equals("Y")) {
                return YELLOW;
            } else if (colorKey.equals("G")) {
                return GREEN;
            } else if (colorKey.equals("B")) {
                return BLUE;
            }
    
            return null;
        }

        public ControlPanelColor getTurnToColor() {
            int totalColors = ControlPanelColor.values().length;
            return ControlPanelColor.values()[(this.ordinal() + 2) % totalColors]; 
        }
    }

    private ColorMatch colorMatcher = new ColorMatch();
    private ColorSensorV3 colorSensor;

    public ControlPanelColorSensor(Port port) {
        colorSensor = new ColorSensorV3(port);

        for (ControlPanelColor color : ControlPanelColor.values()) {
            colorMatcher.addColorMatch(color.getRawColor());
        }
    }

    public Color getRawColor() {
        return colorSensor.getColor();
    }

    public Color getMatchedColor() {
        Color sensedColor = getRawColor();
        return colorMatcher.matchClosestColor(sensedColor).color;
    }
}
