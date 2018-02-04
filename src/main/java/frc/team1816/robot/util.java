package frc.team1816.robot;


import edu.wpi.first.wpilibj.Joystick;

public class util {

    // Joysticks
    private static int LEFT_STICK_PORT = 0;

    // Talon IDs
    public static final int RIGHT_MAIN_MOTOR = 7;
    public static final int RIGHT_SLAVE_MOTOR1 = 8;
    public static final int RIGHT_SLAVE_MOTOR2 = 9;
    public static final int LEFT_MAIN_MOTOR = 1;
    public static final int LEFT_SLAVE_MOTOR1 = 2;
    public static final int LEFT_SLAVE_MOTOR2 = 3;
    public static final int TICKS_PER_REV = 9029;

    public static Joystick LeftJoystick = new Joystick(LEFT_STICK_PORT);

}
