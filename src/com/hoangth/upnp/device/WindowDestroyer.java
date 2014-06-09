package com.hoangth.upnp.device;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/* End the program and close the Frame if the user click the close window button. */
public class WindowDestroyer extends WindowAdapter {
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}
}
