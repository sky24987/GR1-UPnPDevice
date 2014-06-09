package com.hoangth.upnp.device;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.binding.annotations.AnnotationLocalServiceBinder;
import org.fourthline.cling.model.DefaultServiceManager;
import org.fourthline.cling.model.meta.DeviceDetails;
import org.fourthline.cling.model.meta.DeviceIdentity;
import org.fourthline.cling.model.meta.Icon;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.LocalService;
import org.fourthline.cling.model.meta.ManufacturerDetails;
import org.fourthline.cling.model.meta.ModelDetails;
import org.fourthline.cling.model.types.DeviceType;
import org.fourthline.cling.model.types.UDADeviceType;
import org.fourthline.cling.model.types.UDN;

public class TVScreenGUI extends Frame implements MouseListener,
		MouseWheelListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image tvOn, tvOff, tvOnVol1, tvOnVol2, tvOnVol3, tvOnVol4,
			tvOnVol5;
	// private LocalService<SwitchPower> switchPowerService = null;
	private LocalService<TVSwitchPowerWithPropertyChangeSupport> tvScreenService = null;
	private LocalService<TVVolumnControl> volumnControlService = null;
	private LocalService<TVChannelControlWithPropertyChangeSupport> channelControlService = null;

	public int WIDTH = 496;
	public int HEIGHT = 309;
	public int X = 200;
	public int Y = 200;

	public UpnpService upnpService = null;
	public LocalDevice device = null;

	public TVScreenGUI() {
		setSize(WIDTH, HEIGHT);
		setLocation(X, Y);
		// setBackground(Color.darkGray);
		setUndecorated(true);
		tvOn = Toolkit.getDefaultToolkit().getImage("TVon.png");
		tvOff = Toolkit.getDefaultToolkit().getImage("TVoff.png");
		tvOnVol1 = Toolkit.getDefaultToolkit().getImage("TVonVol1.png");
		tvOnVol2 = Toolkit.getDefaultToolkit().getImage("TVonVol2.png");
		tvOnVol3 = Toolkit.getDefaultToolkit().getImage("TVonVol3.png");
		tvOnVol4 = Toolkit.getDefaultToolkit().getImage("TVonVol4.png");
		tvOnVol5 = Toolkit.getDefaultToolkit().getImage("TVonVol5.png");

		addWindowListener(new WindowDestroyer());
		addMouseListener(this);
		addMouseWheelListener(this);
	}

	public void paint(Graphics g) {
		if (tvScreenService != null) {
			System.out.println("Found TVScreenService");
			if (tvScreenService.getManager().getImplementation().getStatus() && volumnControlService.getManager().getImplementation()
					.getVolumn() == 1) {
				g.drawImage(tvOnVol1, 0, 0, null);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				g.drawImage(tvOn, 0, 0, null);
			} else if (tvScreenService.getManager().getImplementation().getStatus() && volumnControlService.getManager().getImplementation()
					.getVolumn() == 2) {
				System.out.println("Found VolumnControlService");
					g.drawImage(tvOnVol2, 0, 0, null);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					g.drawImage(tvOn, 0, 0, null);
			} else if (tvScreenService.getManager().getImplementation().getStatus() && volumnControlService.getManager().getImplementation()
					.getVolumn() == 3) {
				System.out.println("Found VolumnControlService");
					g.drawImage(tvOnVol3, 0, 0, null);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					g.drawImage(tvOn, 0, 0, null);
			} else if (tvScreenService.getManager().getImplementation().getStatus() && volumnControlService.getManager().getImplementation()
					.getVolumn() == 4) {
				System.out.println("Found VolumnControlService");
					g.drawImage(tvOnVol4, 0, 0, null);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					g.drawImage(tvOn, 0, 0, null);
			} else if (tvScreenService.getManager().getImplementation().getStatus() && volumnControlService.getManager().getImplementation()
					.getVolumn() == 5) {
				System.out.println("Found VolumnControlService");
					g.drawImage(tvOnVol5, 0, 0, null);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					g.drawImage(tvOn, 0, 0, null);
			} else if (tvScreenService.getManager().getImplementation().getStatus()) {
					g.drawImage(tvOn, 0, 0, null);
			} else {
				g.drawImage(tvOff, 0, 0, null);
			}
		} else {
			System.out.println("Just loading...");
			g.drawImage(tvOff, 0, 0, null);
		}
	}

	public void mouseClicked(MouseEvent event) {
		tvScreenService
				.getManager()
				.getImplementation()
				.setTarget(
						!tvScreenService.getManager().getImplementation()
								.getTarget());
		repaint();
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent arg0) {
	}

	public void mouseReleased(MouseEvent arg0) {
	}

	public LocalDevice createDevice() {
		try {
			DeviceIdentity identity = new DeviceIdentity(
					UDN.uniqueSystemIdentifier("Hoangth UPnP TV"));

			DeviceType type = new UDADeviceType("HTTV", 1);

			DeviceDetails details = new DeviceDetails(
					"Hoang Trinh SmartHome TV", new ManufacturerDetails(
							"HOANGTH"), new ModelDetails("HOANGTH.TV",
							"A demo TV with on/off.", "v1"));

			Icon icon = new Icon("image/png", 48, 48, 8, getClass()
					.getResource("icon.png"));

			tvScreenService = new AnnotationLocalServiceBinder()
					.read(TVSwitchPowerWithPropertyChangeSupport.class);

			tvScreenService.setManager(new DefaultServiceManager(
					tvScreenService,
					TVSwitchPowerWithPropertyChangeSupport.class));

			tvScreenService.getManager().getImplementation().gui = this;

			volumnControlService = new AnnotationLocalServiceBinder()
					.read(TVVolumnControl.class);

			volumnControlService
					.setManager(new DefaultServiceManager<TVVolumnControl>(
							volumnControlService, TVVolumnControl.class));

			volumnControlService.getManager().getImplementation().gui = this;

			// return new LocalDevice(identity, type, details, icon,
			// tvScreenService);
			return new LocalDevice(
					identity,
					type,
					details,
					icon,
					new LocalService[] { tvScreenService, volumnControlService });

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static void main(String[] args) {
		try {
			TVScreenGUI tv = new TVScreenGUI();
			tv.setVisible(true);

			tv.upnpService = new UpnpServiceImpl();
			tv.device = tv.createDevice();
			tv.upnpService.getRegistry().addDevice(tv.device);
		} catch (Exception ex) {
			System.err.println("Exception occured: " + ex);
			ex.printStackTrace(System.err);
			System.exit(1);
		}

		// Thread serverThread = new Thread(hpLight);
		// serverThread.setDaemon(false);
		// serverThread.start();
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		int notches = e.getWheelRotation();
		if (notches < 0) {
			volumnControlService
					.getManager()
					.getImplementation()
					.setVolumn(
							volumnControlService.getManager()
									.getImplementation().getVolumn() + 1);
		} else {
			volumnControlService
					.getManager()
					.getImplementation()
					.setVolumn(
							volumnControlService.getManager()
									.getImplementation().getVolumn() - 1);
		}
		repaint();
	}
}
