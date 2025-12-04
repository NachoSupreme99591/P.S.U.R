package org.firstinspires.ftc.teamcode;

import android.util.Size;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.autoUtils.angleFInder3000;
import org.firstinspires.ftc.teamcode.autoUtils.forwardTest;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

@Autonomous
public class firstRealAuto extends LinearOpMode {
    private SparkFunOTOS sensor;
   //define drive motors
private DcMotor left;
private DcMotor right;


    //define the auxillary stuff


    //look at my PIDs im so good at control
    forwardTest drive = new forwardTest();
    angleFInder3000 turn = new angleFInder3000();

    @Override
    public void runOpMode() throws InterruptedException {


        sensor = hardwareMap.get(SparkFunOTOS.class, "sensorOtos");
        left = hardwareMap.get(DcMotor.class, "left");
        right = hardwareMap.get(DcMotor.class, "right");
        AprilTagProcessor tagProcessor = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .build();


        VisionPortal visionPortal = new VisionPortal.Builder()
                .addProcessor(tagProcessor)
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .setCameraResolution(new Size(640, 480))
                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
                .enableLiveView(true)
                .setAutoStopLiveView(true)


                .build();

        boolean isPoseSet = false;
//check this stuff bc it is AI vibe-coding
        waitForStart();
        while(opModeIsActive()){
            if(!isPoseSet) {
                AprilTagDetection tag = null;
                if (tagProcessor.getDetections().size() > 0) {
                    tag = tagProcessor.getDetections().get(0);
                    telemetry.addData("x", tag.robotPose.getPosition().x);
                    telemetry.addData("roll", tag.ftcPose.roll);
                    telemetry.addData("y", tag.ftcPose.y);
                    telemetry.addData("pitch", tag.ftcPose.pitch);
                    telemetry.addData("z", tag.ftcPose.z);
                    telemetry.addData("yaw", tag.ftcPose.yaw);
                    telemetry.update();
                }System.out.println(sensor.getPosition().x);
                if (tag != null) {
                    SparkFunOTOS.Pose2D startPos = new SparkFunOTOS.Pose2D(tag.ftcPose.x, tag.ftcPose.y, tag.ftcPose.yaw);
                    sensor.setPosition(startPos);
                    isPoseSet = true;
                } else {
                    System.out.println("cannot see april tag");
                    //commented out because it might break the graphing

                }


            }
            double drivePower = DriveToPositon(0,0);
            left.setPower(drivePower);
            right.setPower(-drivePower);

            while(drive.drivePid.error > 5){
System.out.println("in fist loop");
               left.setPower( drivePower);
               right.setPower(left.getPower());
                drivePower = DriveToPositon(0,0);
            }
            while (turn.anglePId.error > 5) {
                double power = TurnToAngle(50, 50);
                left.setPower(power);
                right.setPower(-left.getPower());
            }
            turn.anglePId.reset();
            drive.drivePid.reset();
            while(drive.drivePid.error > 5){
                DriveToPositon(50, 50);
            }









        }




    }
    public double DriveToPositon(double tarX, double tarY){
        double xPos = sensor.getPosition().x;
        double yPos = sensor.getPosition().y;
        double heading = sensor.getPosition().h;
        System.out.println("trying to drive");

        if (drive.drivePid.error > 5){
            return drive.drive(xPos, yPos, tarX, tarY );
        }else{
            System.out.println("reached setpoint");
            System.out.println(sensor.getPosition().x);
            return 0;
        }
    }

    public double TurnToAngle(double tarX, double tarY){
        double xPos = sensor.getPosition().x;
        double yPos = sensor.getPosition().y;
        double heading = sensor.getPosition().h;
System.out.println("Trying to turn");
        if(turn.anglePId.error > 5){
            return turn.findAngle(xPos, yPos, tarX,  tarY, heading);
        } else{
            return 0;
        }
    }

}
