package org.firstinspires.ftc.teamcode;

public class PIDAngle {
double Kp;

double Ki;

double Kd;

double output;

    double error;
    double integral = 0;
    double derivative = 0;
    double prevError = 0;
    public double  pidAngle(double targetAngle, double currentAngle){
        error = targetAngle - currentAngle;
        integral = integral + error;
        derivative = error - prevError;
        prevError = error;

        output = Kp*error + Ki*integral + Kd*derivative;

        if(output >1){
            output = 1;

        } else if (output < -1){
            output = -1;
        }


        return output;

    }
}
