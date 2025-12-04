package org.firstinspires.ftc.teamcode.utils;


public  class angleFInder3000  {
    double x;
    double y;
    double targetAngle;
    double power;
    PID anglePId = new PID(0,0,0);

    /** xyxy

     * @param startPosX
     * @param startPosY
     * @param endPosX
     * @param endPosY
     * @return
     */
    public double findAngle(double startPosX, double startPosY, double endPosX, double endPosY, double currentAngle){
        x = endPosX - startPosX;
        y = endPosY - startPosY;
       targetAngle =  Math.toDegrees(Math.atan2(y,x));

       power = anglePId.usePID(currentAngle,targetAngle);

        return power;
    }
}
