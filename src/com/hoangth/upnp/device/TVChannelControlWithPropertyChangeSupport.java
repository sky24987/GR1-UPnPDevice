package com.hoangth.upnp.device;

import java.beans.PropertyChangeSupport;

import org.fourthline.cling.binding.annotations.UpnpAction;
import org.fourthline.cling.binding.annotations.UpnpInputArgument;
import org.fourthline.cling.binding.annotations.UpnpOutputArgument;
import org.fourthline.cling.binding.annotations.UpnpService;
import org.fourthline.cling.binding.annotations.UpnpServiceId;
import org.fourthline.cling.binding.annotations.UpnpServiceType;
import org.fourthline.cling.binding.annotations.UpnpStateVariable;

@UpnpService(serviceId = @UpnpServiceId("TVChannelControl"), serviceType = @UpnpServiceType(value = "TVChannelControl", version = 1))
public class TVChannelControlWithPropertyChangeSupport {
	public TVScreenGUI gui = null;

	private final PropertyChangeSupport propertyChangeSupport;

	public TVChannelControlWithPropertyChangeSupport() {
		this.propertyChangeSupport = new PropertyChangeSupport(this);
	}

	public PropertyChangeSupport getPropertyChangeSupport() {
		return propertyChangeSupport;
	}

	@UpnpStateVariable(defaultValue = "1")
	private int channel = 1;

	@UpnpAction
	public void setVolumn(
			@UpnpInputArgument(name = "NewVolumnValue") int newChannelValue) {

		int channelOldValue = channel;
		if (newChannelValue >= 1 && newChannelValue <= 99) {
			channel = newChannelValue;
		} else if (newChannelValue < 1) {
			channel = 1;
		} else {
			channel = 99;
		}

		// These have no effect on the UPnP monitoring but it's JavaBean
		// compliant
		// getPropertyChangeSupport().firePropertyChange("target",
		// targetOldValue, target);
		// getPropertyChangeSupport().firePropertyChange("status",
		// statusOldValue, Status);

		// This will send a UPnP event, it's the name of a state variable that
		// triggers events
		getPropertyChangeSupport().firePropertyChange("Volumn", channelOldValue,
				channel);

		System.out.println("TV channel is: " + channel);
		if (gui != null)
			gui.repaint();
	}

	@UpnpAction(out = @UpnpOutputArgument(name = "RetChannelValue"))
	public int getChannel() {
		return channel;
	}

}

