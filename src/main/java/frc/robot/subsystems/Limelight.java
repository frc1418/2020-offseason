package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {
    private NetworkTableInstance ntInstance = NetworkTableInstance.getDefault();
    private NetworkTable table = ntInstance.getTable("/limelight");

    private NetworkTableEntry yaw = table.getEntry("/tx");
    private NetworkTableEntry pitch = table.getEntry("/ty");
    private NetworkTableEntry lightMode = table.getEntry("/ledMode");
    private NetworkTableEntry validTarget = table.getEntry("/tv");
    private NetworkTableEntry skew = table.getEntry("/ts");
    private NetworkTableEntry cameraMode = table.getEntry("/camMode");
    private NetworkTableEntry pipeline = table.getEntry("/pipeline");
    private NetworkTableEntry targetState = table.getEntry("/target_state");
    private NetworkTableEntry poseData = table.getEntry("/camtran");

    public static class Constants {
        // LED MODES /limelight/ledMode
        public static final int LED_MODE_FROM_PIPELINE = 0;
        public static final int LED_MODE_FORCE_OFF = 1;
        public static final int LED_MODE_FORCE_BLINK = 2;
        public static final int LED_MODE_FORCE_ON = 3;

        // pipeline numbers
        public static final int PIPELINE_TELEOP = 0;
        public static final int PIPELINE_GET_POS = 1;
    }

    // change with new robot; UNIT = inches
    private static final double CAMERA_ELEVATION = 16.5;
    private static final double TARGET_ELEVATION= 98.25;
    // UNIT = degrees;
    private static final double CAMERA_ANGLE = 13.5;

    private double averageX = 0;
    private double averageY = 0;
    private double averageRot = 0;

    /**
     * Constructor
     */
    public Limelight() {
        yaw.setDefaultDouble(0);
        pitch.setDefaultDouble(0);
        lightMode.setDefaultNumber(0);
        validTarget.setDefaultNumber(0);
        skew.setDefaultDouble(0);
        cameraMode.setDefaultNumber(0);
        pipeline.setDefaultNumber(0);
        targetState.setDefaultNumber(0);
        poseData.setDefaultDoubleArray(new double[6]);
    }

    public double getYaw() {
        return this.yaw.getDouble());
    }

    public double getPitch() {
        return pitch.getDouble();
    }
 
    public int getLightMode() {
        return lightMode.getNumber().intValue();
    }

    public void setLightMode(int lightMode) {
        this.lightMode.setNumber(lightMode);
    }
    
    public boolean getValidTarget() {
        return validTarget.getNumber() == 1;
    }

    public double getSkew() {
        return skew.getDouble();
    }

    public int getCameraMode() {
        return cameraMode.getNumber().intValue();
    }

    public void setCameraMode(int cameraMode) {
        this.cameraMode.setNumber(cameraMode);
    }

    public void setPipeline(int pipeline) {
        this.pipeline.setNumber(pipeline);
    }

    public int getPipeline() {
        return pipeline.getNumber().intValue();
    }
        
    /**
     * @return The plane distance (along horizonntal plane) to the target or -1 if no target is found.
     */
    public double getPlaneDistance() {
        if (!getValidTarget()) {
            return -1;
        }
        
        return (TARGET_ELEVATION - CAMERA_ELEVATION) / Math.tan(Math.toRadians(CAMERA_ANGLE + getPitch()));
    }

    /**
     * @return The distance to the target or -1 if no target is found.
     */
    public double getDistance() {
        if(!getValidTarget()){
            return -1;
        }
        
        return (TARGET_ELEVATION - CAMERA_ELEVATION) / Math.sin(Math.toRadians(CAMERA_ANGLE + getPitch()));
    }

    public Pose2d getPose() {
        if (getPipeline() != PIPELINE_GET_POS) {
            throw new IllegalArgumentException("The limelight must be set to the right pipeline to get run getPose()");
        }

        if (!getValidTarget()) {
            return null;
        }

        double rot = Rotation2d.fromDegrees((poseData[4] + 180) % 360);
        //TODO: figure out what "15" is. Maybe distance of center of robot to limelight?
        double x = 15 * math.cos(rot.degrees()) - poseData[2];
        double y = 15 * math.cos(90 - rot.degrees()) + poseData[0];
        // Change unit from inches to meters
        return Pose2d(x / 39.37, y / 39.37, rot);

    }

    private void averagePose() {
        Pose2d pose = getPose();

        if (pose == null) {
            return;
        }

        averageX = ((9 * averageX) + pose.getTranslation().getX()) / 10;
        averageY = ((9 * averageY) + pose.getTranslation().getY()) / 10;
        averageRot = ((9 * averageRot) + pose.getRotation().getDegrees()) / 10;
    }

    public Pose2d getAveragedPose(){
        return Pose2d(averageX, averageY, Rotation2d.fromDegrees(averageRot));
    }
}