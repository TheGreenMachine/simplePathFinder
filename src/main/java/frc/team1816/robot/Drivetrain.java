package frc.team1816.robot;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Drivetrain extends Subsystem{

    public WPI_TalonSRX rightMain;
    private WPI_TalonSRX rightSlaveOne;
    private WPI_TalonSRX rightSlaveTwo;
    public WPI_TalonSRX leftMain;
    private WPI_TalonSRX leftSlaveOne;
    private WPI_TalonSRX leftSlaveTwo;
    private DifferentialDrive drive;

    Drivetrain(){
        rightMain = new WPI_TalonSRX(util.RIGHT_MAIN_MOTOR);
        rightSlaveOne = new WPI_TalonSRX(util.RIGHT_SLAVE_MOTOR1);
        rightSlaveTwo = new WPI_TalonSRX(util.RIGHT_SLAVE_MOTOR2);
        rightSlaveOne.follow(rightMain);
        rightSlaveTwo.follow(rightMain);

        leftMain = new WPI_TalonSRX(util.LEFT_MAIN_MOTOR);
        leftSlaveOne = new WPI_TalonSRX(util.LEFT_SLAVE_MOTOR1);
        leftSlaveTwo = new WPI_TalonSRX(util.LEFT_SLAVE_MOTOR2);
        leftSlaveOne.follow(leftMain);
        leftSlaveTwo.follow(leftMain);

        drive = new DifferentialDrive(leftMain,rightMain);
    }

    public void reset() {
        stop();
        rightMain.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 20);
        leftMain.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 20);
        leftMain.setSelectedSensorPosition(0,0,20);
        rightMain.setSelectedSensorPosition(0,0,20);

    }

    public void stop(){
        drive.stopMotor();
    }

    public void tank(double leftSpeed, double rightSpeed) {
        drive.tankDrive(leftSpeed,rightSpeed);
    }

    @Override
    protected void initDefaultCommand() {

    }
}
