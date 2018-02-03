package frc.team1816.robot;

import com.thingworx.communications.client.ClientConfigurator;
import edu.wpi.first.wpilibj.IterativeRobot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Robot extends IterativeRobot{

    private static final Logger LOG = LoggerFactory.getLogger(IterativeRobot.class);

    @Override
    public void robotInit() {
        System.out.println("robotInit START");
        ClientConfigurator config = new ClientConfigurator();
        config.setUri("ws://172.22.11.1:8080/Thingworx/WS");
        config.setAppKey("xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx");
        config.ignoreSSLErrors(true); // All self signed certs

        try {

            // Create our client.
            RobotThing client = new RobotThing(config);

            // Start the client. The client will connect to the server and authenticate
            // using the ApplicationKey specified above.
            client.start();

            // Wait for the client to connect.
            if (client.waitForConnection(30000)) {
                LOG.info("The client is now connected.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("robotInit END");
    }

    @Override
    public void disabledInit() { }

    @Override
    public void autonomousInit() { }

    @Override
    public void teleopInit() { }

    @Override
    public void testInit() { }

    @Override
    public void disabledPeriodic() { }
    
    @Override
    public void autonomousPeriodic() { }

    @Override
    public void teleopPeriodic() { }

    @Override
    public void testPeriodic() { }

}