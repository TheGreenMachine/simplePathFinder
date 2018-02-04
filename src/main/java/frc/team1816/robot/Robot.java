package frc.team1816.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

import java.io.File;

public class Robot extends IterativeRobot {

    private Drivetrain _driveTrain;
    private static double MAX_VELOCITY = 1.414;

    private EncoderFollower left;
    private EncoderFollower right;

    private AHRS ahrs = new AHRS(I2C.Port.kMXP);

    @SuppressWarnings("unused")
    private void CalculateTrajectories(){
        Waypoint[] points = {
                new Waypoint(-4.0, 3.0, 0.0),
                new Waypoint(-0.5, 3.0, Pathfinder.d2r(0.0)),
                new Waypoint(0.0, 0.0, Pathfinder.d2r(-90.0))
        };
        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,Trajectory.Config.SAMPLES_HIGH,0.05, MAX_VELOCITY,1.885, 60.0);
        Trajectory trajectory = Pathfinder.generate(points,config);
        TankModifier modifier = new TankModifier(trajectory).modify(0.86);
        left = new EncoderFollower(modifier.getLeftTrajectory());
        right = new EncoderFollower(modifier.getRightTrajectory());
        Pathfinder.writeToCSV(new File("/tmp/left.csv"),modifier.getLeftTrajectory());
        Pathfinder.writeToCSV(new File("/tmp/right.csv"),modifier.getRightTrajectory());
        System.out.println(trajectory.segments.length);
    }

    @Override
    public void robotInit() {
        _driveTrain = new Drivetrain();
        Trajectory leftT = Pathfinder.readFromCSV(new File("/home/lvuser/PL_LS_LT.csv"));
        Trajectory rightT = Pathfinder.readFromCSV(new File("/home/lvuser/PL_LS_RT.csv"));
        left = new EncoderFollower(leftT);
        right = new EncoderFollower(rightT);
    }

    @Override
    public void disabledInit() { }

    @Override
    public void autonomousInit() {
        _driveTrain.reset();
        left.reset();
        right.reset();
        ahrs.reset();

        left.configureEncoder(0, util.TICKS_PER_REV, 0.33333);
        left.configurePIDVA(0.02, 0.0, 0.0, 1 / MAX_VELOCITY, 0.0);

        right.configureEncoder(0, util.TICKS_PER_REV, 0.33333);
        right.configurePIDVA(0.02, 0.0, 0.0, 1 / MAX_VELOCITY, 0.0);


    }

    @Override
    public void teleopInit() { }

    @Override
    public void testInit() { }


    @Override
    public void disabledPeriodic() { }

    @Override
    public void autonomousPeriodic() {

        Scheduler.getInstance().run();

        int frontLeftPosition = _driveTrain.leftMain.getSelectedSensorPosition(0) * -1;
        int frontRightPosition = _driveTrain.rightMain.getSelectedSensorPosition(0) * -1;

        double leftOut = left.calculate(frontLeftPosition);
        double rightOut = right.calculate(frontRightPosition);

        if (left.isFinished()) {
            _driveTrain.tank(0.0, 0.0);
        } else {

            double angleDiff = Pathfinder.boundHalfDegrees(Pathfinder.r2d(left.getHeading()) - ahrs.getAngle());
            double turn = 0.004 * angleDiff;

            System.out.print("left: " + leftOut);
            System.out.print(" right: " + rightOut);
            System.out.print(" angle: " + turn);
            System.out.print(" left output: " + (leftOut-turn));
            System.out.println(" right output: " + (rightOut + turn));

            _driveTrain.tank(leftOut - turn, rightOut + turn);
        }
    }

    @Override
    public void teleopPeriodic() { }

    @Override
    public void testPeriodic() { }
}