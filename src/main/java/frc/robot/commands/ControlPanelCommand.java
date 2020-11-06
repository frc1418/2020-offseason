package frc.robot.commands;


import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.util.Color;

import frc.robot.subsystems.ControlPanelSubsystem;

public class ControlPanelCommand extends CommandBase {

    ControlPanelSubsystem controlPanel;

    public ControlPanelCommand(){
        controlPanel = new ControlPanelSubsystem();
    }


    @Override
    public void execute(){

        if(controlPanel.ds.getGameSpecificMessage().length() > 0 && controlPanel.fmsColor != null){
            controlPanel.getFMSColor();
        }

        if(controlPanel.solenoidState == DoubleSolenoid.Value.kForward){
            try{
                Color readColor = controlPanel.colorSensor.getColor();
                com.revrobotics.ColorMatchResult resultColor = controlPanel.colorMatcher.matchClosestColor(readColor);

                if (!resultColor.color.equals(Color.kBlack)){

                    controlPanel.detectedColor = resultColor.color;
                    double lowestDistance = ControlPanelSubsystem.calculateDistance(controlPanel.detectedColor, controlPanel.colors.get(0));
                    
                    //Iterates through controlPanel.colors and sets cotrolPanel.detectedColor to the color it is closet to
                    for(Color color : controlPanel.colors){
                        if(ControlPanelSubsystem.calculateDistance(controlPanel.detectedColor, controlPanel.colors.get(0)) < lowestDistance){
                            lowestDistance = ControlPanelSubsystem.calculateDistance(controlPanel.detectedColor, controlPanel.colors.get(0));
                            controlPanel.detectedColor = color;
                        }
                    }
                }
            }
            catch (Exception e){
                return;
            }
        }
        controlPanel.cpMotor.set(VictorSPXControlMode.Velocity, (double)controlPanel.speed);
        controlPanel.cpSolenoid.set(controlPanel.solenoidState);
    }
}
