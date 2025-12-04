package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.utils.angleFInder3000;
import org.firstinspires.ftc.teamcode.utils.forwardTest;

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

        waitForStart();
        while(opModeIsActive()){

            while(drive.drivePid.error > 5){
                DriveToPositon(0,0);
            }
            drive.drivePid.reset();
            while(drive.drivePid.error > 5){
                DriveToPositon(7, 8);
            }









        }




    }

    public double DriveToPositon(double tarX, double tarY){
        double xPos = sensor.getPosition().x;
        double yPos = sensor.getPosition().y;
        double heading = sensor.getPosition().h;

        if (drive.drivePid.error > 5){
            return drive.drive(xPos, yPos, tarX, tarY );
        }else{
       return 0;
        }
    }
}
