package com.hoangth.upnp.device;

import java.beans.PropertyChangeSupport;

import org.fourthline.cling.binding.annotations.UpnpAction;
import org.fourthline.cling.binding.annotations.UpnpInputArgument;
import org.fourthline.cling.binding.annotations.UpnpOutputArgument;
import org.fourthline.cling.binding.annotations.UpnpService;
import org.fourthline.cling.binding.annotations.UpnpServiceId;
import org.fourthline.cling.binding.annotations.UpnpServiceType;
import org.fourthline.cling.binding.annotations.UpnpStateVariable;

@UpnpService(serviceId = @UpnpServiceId("TVVolumnControl"), serviceType = @UpnpServiceType(value = "TVVolumnControl", version = 1))
public class TVVolumnControlWithPropertyChangeSupport {
	public TVScreenGUI gui = null;

	private final PropertyChangeSupport propertyChangeSupport;

	public TVVolumnControlWithPropertyChangeSupport() {
		this.propertyChangeSupport = new PropertyChangeSupport(this);
	}

	public PropertyChangeSupport getPropertyChangeSupport() {
		return propertyChangeSupport;
	}

	@UpnpStateVariable(defaultValue = "0")
	private int volumn = 0;

	@UpnpAction
	public void setVolumn(
			@UpnpInputArgument(name = "NewVolumnValue") int newVolumnValue) {

		int volumnOldValue = volumn;
		if (newVolumnValue >= 1 && newVolumnValue <= 5) {
			volumn = newVolumnValue;
		} else if (newVolumnValue < 1) {
			volumn = 1;
		} else {
			volumn = 5;
		}

		// These have no effect on the UPnP monitoring but it's JavaBean
		// compliant
		// getPropertyChangeSupport().firePropertyChange("target",
		// targetOldValue, target);
		// getPropertyChangeSupport().firePropertyChange("status",
		// statusOldValue, Status);

		// This will send a UPnP event, it's the name of a state variable that
		// triggers events
		getPropertyChangeSupport().firePropertyChange("Volumn", volumnOldValue,
				volumn);

		if (volumn == 0) {
			System.out.println("TV is: mute");
		} else {
			System.out.println("TV volumn is: " + volumn);
		}
		if (gui != null)
			gui.repaint();
	}

	@UpnpAction(out = @UpnpOutputArgument(name = "RetVolumnValue"))
	public int getVolumn() {
		return volumn;
	}

}
