package org.firstinspires.ftc.teamcode.autoUtils;

public class PID {
double Kp =.15;
//toddo start on the Ki
double Ki =00;

double Kd=0;

double output;

public PID(double setKp, double setKi, double setKd){
    Kp = setKp;
    Ki = setKi;
    Kd = setKd;
}

    public double error;
    double integral = 0;
    double derivative = 0;
    double prevError = 0;
    public void reset(){
        integral =0;
        derivative = 0;
        prevError = 0;
    }
    public double usePID(double targetAngle, double currentAngle){
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
