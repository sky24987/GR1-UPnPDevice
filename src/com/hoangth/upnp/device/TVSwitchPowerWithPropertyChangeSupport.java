package com.hoangth.upnp.device;

import java.beans.PropertyChangeSupport;

import org.fourthline.cling.binding.annotations.UpnpAction;
import org.fourthline.cling.binding.annotations.UpnpInputArgument;
import org.fourthline.cling.binding.annotations.UpnpOutputArgument;
import org.fourthline.cling.binding.annotations.UpnpService;
import org.fourthline.cling.binding.annotations.UpnpServiceId;
import org.fourthline.cling.binding.annotations.UpnpServiceType;
import org.fourthline.cling.binding.annotations.UpnpStateVariable;

@UpnpService(serviceId = @UpnpServiceId("SwitchPower"), serviceType = @UpnpServiceType(value = "SwitchPower", version = 1))
public class TVSwitchPowerWithPropertyChangeSupport {
	public TVScreenGUI gui = null;

	private final PropertyChangeSupport propertyChangeSupport;

	public TVSwitchPowerWithPropertyChangeSupport() {
		this.propertyChangeSupport = new PropertyChangeSupport(this);
	}

	public PropertyChangeSupport getPropertyChangeSupport() {
		return propertyChangeSupport;
	}

	@UpnpStateVariable(defaultValue = "0", sendEvents = true)
	private boolean target = false;

	@UpnpStateVariable(defaultValue = "0")
	private boolean status = false;

	@UpnpAction
	public void setTarget(
			@UpnpInputArgument(name = "NewTargetValue") boolean newTargetValue) {

		boolean targetOldValue = target;
		target = newTargetValue;
		boolean statusOldValue = status;
		status = newTargetValue;

		// These have no effect on the UPnP monitoring but it's JavaBean
		// compliant
		// getPropertyChangeSupport().firePropertyChange("target",
		// targetOldValue, target);
		// getPropertyChangeSupport().firePropertyChange("status",
		// statusOldValue, Status);

		// This will send a UPnP event, it's the name of a state variable that
		// triggers events
		getPropertyChangeSupport().firePropertyChange("Status", statusOldValue,
				status);
		getPropertyChangeSupport().firePropertyChange("Target", targetOldValue,
				target);

		if (status == true) {
			System.out.println("TV is: on");
		} else {
			System.out.println("TV is: off");
		}
		if (gui != null)
			gui.repaint();
	}

	@UpnpAction(out = @UpnpOutputArgument(name = "RetTargetValue"))
	public boolean getTarget() {
		return target;
	}

	@UpnpAction(out = @UpnpOutputArgument(name = "ResultStatus"))
	public boolean getStatus() {
		return status;
	}

}
