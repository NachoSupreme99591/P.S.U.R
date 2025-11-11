package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public  class angleFInder3000  {
    double x;
    double y;
    double targetAngle;

    /** xyxy

     * @param startPosX
     * @param startPosY
     * @param endPosX
     * @param endPosY
     * @return
     */
    public double findAngle(double startPosX, double startPosY, double endPosX, double endPosY){
        x = endPosX - startPosX;
        y = endPosY - startPosY;
       targetAngle = Math.tan(y/x);

        return targetAngle;
    }
}
