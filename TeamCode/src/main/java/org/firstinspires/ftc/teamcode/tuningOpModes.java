package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

//see  https://learn.sparkfun.com/tutorials/calibrating-your-odometry-sensor/all for tutorial
@TeleOp
public class tuningOpModes extends LinearOpMode {
    private DcMotor right;
    private DcMotor left;
    private SparkFunOTOS sensor;
    public void runOpMode() {
        right = hardwareMap.get(DcMotor.class, "right");
        left = hardwareMap.get(DcMotor.class, "left");
        sensor = hardwareMap.get(SparkFunOTOS.class, "sensorOtos");
        sensor.setLinearUnit(DistanceUnit.INCH);
        sensor.setAngularScalar(0.989);
        sensor.setLinearScalar(1.0);
        sensor.calibrateImu();

        while (opModeIsActive()) {


            right.setPower((gamepad1.right_stick_x + gamepad1.left_stick_y));
            left.setPower((gamepad1.right_stick_x - gamepad1.left_stick_y));
            telemetry.addData("x", sensor.getPosition().x);
            telemetry.addData("y", sensor.getPosition().y);
            telemetry.addData("h", sensor.getPosition().h);
            telemetry.update();
        }
    }
}
