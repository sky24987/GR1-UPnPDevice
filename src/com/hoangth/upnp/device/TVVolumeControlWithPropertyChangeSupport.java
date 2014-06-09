package com.hoangth.upnp.device;

import java.beans.PropertyChangeSupport;

import org.fourthline.cling.binding.annotations.UpnpAction;
import org.fourthline.cling.binding.annotations.UpnpInputArgument;
import org.fourthline.cling.binding.annotations.UpnpOutputArgument;
import org.fourthline.cling.binding.annotations.UpnpService;
import org.fourthline.cling.binding.annotations.UpnpServiceId;
import org.fourthline.cling.binding.annotations.UpnpServiceType;
import org.fourthline.cling.binding.annotations.UpnpStateVariable;

@UpnpService(serviceId = @UpnpServiceId("TVVolumeControl"), serviceType = @UpnpServiceType(value = "TVVolumeControl", version = 1))
public class TVVolumeControlWithPropertyChangeSupport {
	public TVScreenGUI gui = null;

	private final PropertyChangeSupport propertyChangeSupport;

	public TVVolumeControlWithPropertyChangeSupport() {
		this.propertyChangeSupport = new PropertyChangeSupport(this);
	}

	public PropertyChangeSupport getPropertyChangeSupport() {
		return propertyChangeSupport;
	}

	@UpnpStateVariable(defaultValue = "0")
	private int volume = 0;

	@UpnpAction
	public void setVolume(
			@UpnpInputArgument(name = "NewVolumeValue") int newVolumeValue) {

		int volumeOldValue = volume;
		if (newVolumeValue >= 1 && newVolumeValue <= 5) {
			volume = newVolumeValue;
		} else if (newVolumeValue < 1) {
			volume = 1;
		} else {
			volume = 5;
		}

		// These have no effect on the UPnP monitoring but it's JavaBean
		// compliant
		// getPropertyChangeSupport().firePropertyChange("target",
		// targetOldValue, target);
		// getPropertyChangeSupport().firePropertyChange("status",
		// statusOldValue, Status);

		// This will send a UPnP event, it's the name of a state variable that
		// triggers events
		getPropertyChangeSupport().firePropertyChange("Volume", volumeOldValue,
				volume);

		if (volume == 0) {
			System.out.println("TV is: mute");
		} else {
			System.out.println("TV volume is: " + volume);
		}
		if (gui != null)
			gui.repaint();
	}

	@UpnpAction(out = @UpnpOutputArgument(name = "RetVolumeValue"))
	public int getVolume() {
		return volume;
	}

}
