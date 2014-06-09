package com.hoangth.upnp.device;


import java.util.Map;

import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.controlpoint.*;
import org.fourthline.cling.model.action.*;
import org.fourthline.cling.model.gena.*;
import org.fourthline.cling.model.message.*;
import org.fourthline.cling.model.message.header.*;
import org.fourthline.cling.model.meta.*;
import org.fourthline.cling.model.state.*;
import org.fourthline.cling.model.types.*;
import org.fourthline.cling.registry.*;

public class BinaryLightClient implements Runnable {

	@SuppressWarnings("rawtypes")
	class SetTargetActionInvocation extends ActionInvocation {

	    @SuppressWarnings({ "unchecked" })
		SetTargetActionInvocation(Service service) {
	        super(service.getAction("SetTarget"));
	        try {

	            // Throws InvalidValueException if the value is of wrong type
	            setInput("NewTargetValue", true);

	        } catch (InvalidValueException ex) {
	            System.err.println(ex.getMessage());
	            System.exit(1);
	        }
	    }
	}
	
    public static void main(String[] args) throws Exception {
        // Start a user thread that runs the UPnP stack
        Thread clientThread = new Thread(new BinaryLightClient());
        clientThread.setDaemon(false);
        clientThread.start();

    }

	void executeAction(UpnpService upnpService, Service switchPowerService) {

        ActionInvocation setTargetInvocation =
                new SetTargetActionInvocation(switchPowerService);

        // Executes asynchronous in the background
        upnpService.getControlPoint().execute(
                new ActionCallback(setTargetInvocation) {

                    @Override
                    public void success(ActionInvocation invocation) {
                        assert invocation.getOutput().length == 0;
                        System.out.println("Successfully called action!");
                    }

                    @Override
                    public void failure(ActionInvocation invocation,
                                        UpnpResponse operation,
                                        String defaultMsg) {
                        System.err.println(defaultMsg);
                    }
                }
        );

	}
	
	void executeGENAAction(UpnpService upnpService, Service switchPowerService) {
		SubscriptionCallback callback = new SubscriptionCallback(switchPowerService, 6) {

		    @Override
		    public void established(GENASubscription sub) {
		        System.out.println("Established: " + sub.getSubscriptionId());
		    }

		    @Override
		    protected void failed(GENASubscription subscription,
		                          UpnpResponse responseStatus,
		                          Exception exception,
		                          String defaultMsg) {
		        System.err.println(defaultMsg);
		    }

		    @Override
		    public void ended(GENASubscription sub,
		                      CancelReason reason,
		                      UpnpResponse response) {
		        //assertNull(reason);
		    }

		    @Override
		    public void eventReceived(GENASubscription sub) {

		        System.out.println("Event: " + sub.getCurrentSequence().getValue());

		        Map<String, StateVariableValue> values = sub.getCurrentValues();
		        StateVariableValue status = values.get("Status");

		        //assertEquals(status.getDatatype().getClass(), BooleanDatatype.class);
		        //assertEquals(status.getDatatype().getBuiltin(), Datatype.Builtin.BOOLEAN);

		        System.out.println("Status is: " + status.toString());

		    }

		    @Override
		    public void eventsMissed(GENASubscription sub, int numberOfMissedEvents) {
		        System.out.println("Missed events: " + numberOfMissedEvents);
		    }

		    /*
		    @Override
		    protected void invalidMessage(RemoteGENASubscription sub,
		                                  UnsupportedDataException ex) {
		        // Log/send an error report?
		    }
		    */
		};

		upnpService.getControlPoint().execute(callback);
	}
    
    RegistryListener createRegistryListener(final UpnpService upnpService) {
        return new DefaultRegistryListener() {

            ServiceId serviceId = new UDAServiceId("SwitchPower");

            @Override
            public void remoteDeviceAdded(Registry registry, RemoteDevice device) {

                @SuppressWarnings("rawtypes")
				Service switchPower;
                if ((switchPower = device.findService(serviceId)) != null) {

                    System.out.println("Service discovered: " + switchPower);
                    System.out.println("Device name: " + switchPower.getDevice().getDetails().getFriendlyName());
                    //executeAction(upnpService, switchPower);
                    executeGENAAction(upnpService, switchPower);

                }

            }

            @Override
            public void remoteDeviceRemoved(Registry registry, RemoteDevice device) {
                @SuppressWarnings("rawtypes")
				Service switchPower;
                if ((switchPower = device.findService(serviceId)) != null) {
                    System.out.println("Service disappeared: " + switchPower);
                }
            }

        };
    }
    
    public void run() {
        try {

            UpnpService upnpService = new UpnpServiceImpl();

            // Add a listener for device registration events
            upnpService.getRegistry().addListener(
                    createRegistryListener(upnpService)
            );

            // Broadcast a search message for all devices
            upnpService.getControlPoint().search(
                    new STAllHeader()
            );

        } catch (Exception ex) {
            System.err.println("Exception occured: " + ex);
            System.exit(1);
        }
    }

}