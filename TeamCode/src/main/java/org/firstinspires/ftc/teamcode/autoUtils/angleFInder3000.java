package org.firstinspires.ftc.teamcode.autoUtils;


public  class angleFInder3000  {
    double x;
    double y;
    double targetAngle;
    double power;
    public PID anglePId = new PID(0.15,0,0);

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
       targetAngle =  Math.toDegrees(Math.atan2(x, y));

       power = anglePId.usePID(currentAngle,targetAngle);

        return power;
    }
}
