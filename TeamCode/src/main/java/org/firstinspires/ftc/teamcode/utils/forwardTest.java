package org.firstinspires.ftc.teamcode.utils;

public class forwardTest{
    public PID drivePid = new PID(0,0,0);
    public double drive(double currentPosX,double currentPosY, double targetPosX, double targetPosY){
        double distanceFromTarPoss = Math.hypot((targetPosX - currentPosX), (targetPosY - currentPosY));

        double power = drivePid.usePID(0, distanceFromTarPoss);

        return power;
    }

}
