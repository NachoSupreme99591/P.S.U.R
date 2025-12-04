package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
@TeleOp
public class genuinlyJustPower extends LinearOpMode {
    private DcMotor left;
    private DcMotor right;
    @Override
    public void runOpMode() throws InterruptedException {


        waitForStart();
        while(opModeIsActive()){
            left = hardwareMap.get(DcMotor.class, "left" );
            right = hardwareMap.get(DcMotor.class, "right");

            left.setPower(gamepad1.right_stick_y);
           right.setPower(left.getPower());

        }
    }
}
