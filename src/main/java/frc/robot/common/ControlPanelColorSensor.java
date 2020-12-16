package frc.robot.common;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.util.Color;
import java.util.List;

public class ControlPanelColorSensor {

  private ColorMatch colorMatcher = new ColorMatch();
  private ColorSensorV3 colorSensor;

  public ControlPanelColorSensor(Port port, List<ControlPanelColor> matchableColors) {
    colorSensor = new ColorSensorV3(port);
    matchableColors.stream()
        .map(ControlPanelColor::getColor)
        .forEach(colorMatcher::addColorMatch);
  }

  public ControlPanelColor getDetectedColor() {
    Color rawColor = colorSensor.getColor();
    return ControlPanelColor.valueOf(colorMatcher.matchClosestColor(rawColor).color);
  }
}