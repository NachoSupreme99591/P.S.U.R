package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

public class testTheMotors extends LinearOpMode {
    private DcMotor left;
    private DcMotor right;
    private SparkFunOTOS sensor;
    angleFInder3000 findAngle = new angleFInder3000();
    PIDAngle pidAngle  = new PIDAngle();
    @Override
    public void runOpMode() throws InterruptedException {
left = hardwareMap.get(DcMotor.class, "left" );
right = hardwareMap.get(DcMotor.class, "right");
sensor = hardwareMap.get(SparkFunOTOS.class, "sensorOtos");
        while(opModeIsActive());{
            findAngle.findAngle(sensor.getPosition().x, sensor.getPosition().y, 0, 0);
            left.setPower(pidAngle.pidAngle(sensor.getPosition().h,  findAngle.findAngle(sensor.getPosition().x, sensor.getPosition().y, 0, 0)));
            right.setPower(-left.getPower());

        }
    }
}
