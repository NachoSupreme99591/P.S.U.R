package org.firstinspires.ftc.teamcode;

import android.util.Size;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

@TeleOp
public class testTheMotors extends LinearOpMode {
    private DcMotor left;
    private DcMotor right;
    private SparkFunOTOS sensor;
    angleFInder3000 findAngle = new angleFInder3000();
    PID pidAngle  = new PID();
    PID pidMove = new PID();

     double targetX = 0;
     double targetY = 0;
    @Override
    public void runOpMode() throws InterruptedException {
left = hardwareMap.get(DcMotor.class, "left" );
right = hardwareMap.get(DcMotor.class, "right");
sensor = hardwareMap.get(SparkFunOTOS.class, "sensorOtos");
left.setDirection(DcMotorSimple.Direction.REVERSE);

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

        while(opModeInInit()) {
            AprilTagDetection tag = null;
            if (tagProcessor.getDetections().size() > 0) {
                tag = tagProcessor.getDetections().get(0);
                telemetry.addData("x", tag.ftcPose.x);
                telemetry.addData("roll", tag.ftcPose.roll);
                telemetry.addData("y", tag.ftcPose.y);
                telemetry.addData("pitch", tag.ftcPose.pitch);
                telemetry.addData("z", tag.ftcPose.z);
                telemetry.addData("yaw", tag.ftcPose.yaw);
            }
            if(tag != null){
            SparkFunOTOS.Pose2D startPos = new SparkFunOTOS.Pose2D(tag.ftcPose.x, tag.ftcPose.y, tag.ftcPose.yaw);
            sensor.setPosition(startPos);
        } else throw new RuntimeException("cannot see april tag");
waitForStart();

        while(opModeIsActive()) {



            double xPos = sensor.getPosition().x;
            double yPos = sensor.getPosition().y;


            findAngle.findAngle(sensor.getPosition().x, sensor.getPosition().y, 0, 0);
            //TODO: test and then hopefully tune the "K" values

            double test = 0;
            if (gamepad1.a) {
                test = pidAngle.pid(//inputs of the loop:
                        findAngle.findAngle(xPos, yPos, targetX, targetY), /*uses the trig-found in angleFInder3000-
                         to calculate the angle it needs to be at, using the sensor to get the bot x and y, as well as the manually entered target position*/
                        sensor.getPosition().h //gets the heading from the inbuilt imu
                );
                //sets the Power to the output of the PID loop
                left.setPower(test

                );
                right.setPower(-left.getPower());
            } else {
                right.setPower(0);
                left.setPower(0);
            }
            if (gamepad1.b) {
                left.setPower(pidMove.pid(
                        Math.sqrt(Math.pow(targetX, 2) * Math.pow(targetY, 2)),// Pythagorean theorem to find distance from the center of the target pos
                        Math.sqrt(Math.pow(xPos, 2) * Math.pow(yPos, 2))//finds distance of current pos from the center
                ));
                right.setPower(left.getPower());
            } else {
                right.setPower(0);
                left.setPower(0);
            }
            telemetry.addData("targetX", targetX);
            telemetry.addData("targetY", targetY);
            telemetry.addData("X", xPos);
            telemetry.addData("Y", yPos);
            telemetry.addLine("gamepad inputs");
            telemetry.addData("X:", gamepad1.x);
            telemetry.addData("Y:", gamepad1.y);
            telemetry.addData("A:", gamepad1.a);
            telemetry.addData("B:", gamepad1.b);
            telemetry.addLine("motor powers");
            telemetry.addData("Left:", left.getPower());
            telemetry.addData("Right: ", right.getPower());
            telemetry.addLine("PID Numbers");
            telemetry.addData("Error", pidAngle.error);
            telemetry.addData("alleged motor poower", test);
            telemetry.update();

        }
    }
}
