package org.firstinspires.ftc.teamcode;

public class PID {
double Kp =1;

double Ki =0;

double Kd=0;

double output;

    double error;
    double integral = 0;
    double derivative = 0;
    double prevError = 0;
    public double  pid(double targetAngle, double currentAngle){
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
