package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import static frc.robot.Constants.*;

public class IntakeSubsystem {
    private VictorSPX upperIntakeMotor = new VictorSPX(UPPER_INTAKE_MOTOR);
    private VictorSPX bottomIntakeMotor = new VictorSPX(BOTTOM_INTAKE_MOTOR);

    public IntakeSubsystem() {
        upperIntakeMotor.follow(bottomIntakeMotor);
        upperIntakeMotor.setInverted(InvertType.OpposeMaster);
    }

    public void succySuccy(double succySpeed) {
        bottomIntakeMotor.set(VictorSPXControlMode.PercentOutput, succySpeed);
    }

}
