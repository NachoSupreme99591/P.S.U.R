package org.firstinspires.ftc.teamcode.autoUtils;

public class forwardTest{
    public PID drivePid = new PID(1,0,0);
    public double drive(double currentPosX,double currentPosY, double targetPosX, double targetPosY){
        double distanceFromTarPoss = Math.hypot((targetPosX - currentPosX), (targetPosY - currentPosY));
        System.out.println(distanceFromTarPoss); //todo: debug this and make sure the distance is correctish

        double power = drivePid.usePID(0, distanceFromTarPoss);

        return power;
    }

}
