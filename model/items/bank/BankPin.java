package main.model.items.bank;

import main.model.players.Client;

/**
 * 
 * @author Jason MacKeigan
 * @date July 10th, 2014, 2:41:21 PM
 */
public class BankPin {

	private Client c;
	private String pin = "";
	private boolean locked = true;
	private long cancellationDelay = -1;
	private boolean appendingCancellation;
	private int attempts;
	private PinState pinState;

	public BankPin(Client Client) {
		this.c = Client;
	}

	public enum PinState {
		CREATE_NEW, UNLOCK, CANCEL_PIN, CANCEL_REQUEST
	};

	public void open(int state) {
		c.getPA().sendFrame126("", 59507);
		switch (state) {
		case 1:
			pinState = PinState.CREATE_NEW;
			c.getPA().sendFrame126("You do not have a pin set.", 59503);
			c.getPA().sendFrame126("Choose any 4-8 character combination.", 59504);
			c.getPA().sendFrame126("Make sure caps lock isn't enabled.", 59505);
			c.getPA().sendFrame126("Press enter to continue", 59506);
			c.getPA().sendFrame171(1, 59511);
			break;
		case 2:
			pinState = PinState.UNLOCK;
			c.getPA().sendFrame126("You currently have a pin set.", 59503);
			c.getPA().sendFrame126("Type in your 4-8 character combination.", 59504);
			c.getPA().sendFrame126("Hit enter after you've typed your pin.", 59505);
			c.getPA().sendFrame126("Press the button to continue", 59506);
			c.getPA().sendFrame171(1, 59511);
			break;
		case 3:
			pinState = PinState.CANCEL_PIN;
			c.getPA().sendFrame126("If you wish to cancel your pin, ", 59503);
			c.getPA().sendFrame126("click the button below. If not", 59504);
			c.getPA().sendFrame126("click the x button in the corner.", 59505);
			c.getPA().sendFrame126("Press the button to continue", 59506);
			c.getPA().sendFrame171(0, 59511);
			break;
		case 4:
			pinState = PinState.CANCEL_REQUEST;
			c.getPA().sendFrame126("Your current pin cancellation is", 59503);
			c.getPA().sendFrame126("pending. Press continue to cancel", 59504);
			c.getPA().sendFrame126("this and keep your bank pin.", 59505);
			c.getPA().sendFrame126("Press the button to continue", 59506);
			c.getPA().sendFrame171(1, 59511);
			break;
		}
		c.getPA().showInterface(59500);
	}

	public void create(String pin) {
		if (this.pin.length() > 0) {
			c.sendMessage("You already have a pin, you cannot create another one.");
			return;
		}
		if (pin.length() < 4) {
			c.sendMessage("Your pin must be atleast 4 characters in length.");
			return;
		}
		if (pin.length() > 8) {
			c.sendMessage("Your pin cannot be longer than 8 characters in length.");
			return;
		}
		if (!pin.matches("[A-Za-z0-9]+")) {
			c.sendMessage("Your bank pin contains illegal characters. Pins can only contain numbers,");
			c.sendMessage("and uppercase, and lowercase case letters.");
			return;
		}
		if (pin.contains(" ")) {
			c.sendMessage("Your bank pin contains 1 or more spaces, bank pins cannot contain spaces.");
			return;
		}
		if (pin.equalsIgnoreCase(c.playerName)) {
			c.sendMessage("Your bank pin cannot match your username.");
			return;
		}
		c.sendMessage("You have sucessfully created a bank pin. We urge you to keep this combination");
		c.sendMessage("to yourself as sharing it may jepordize the items you have in your bank.");
		this.pin = pin;
		this.locked = true;
		this.attempts = 0;
	}

	public void unlock(String pin) {
		if (!this.locked) {
			return;
		}
		if (!this.pin.equals(pin)) {
			c.sendMessage("The pin you entered does not match your current bank pin, please try again.");
			this.attempts++;
			return;
		}
		this.c.getPA().closeAllWindows();
		this.attempts = 0;
		this.locked = false;
		this.c.playerStun = false;
		//this.c.attackable = false;
		//this.c.inTask = false;
		c.sendMessage("You have successfully entered your " + this.pin.length() + " character pin");
		//c.aggressionTolerance.reset();
		// this.update();
	}

	public void cancel(String pin) {
		if (!this.pin.equals(pin)) {
			c.sendMessage("The pin you entered does not match your current bank pin, please try again.");
			this.attempts++;
			return;
		}
		if (this.pinState == PinState.CANCEL_PIN) {
			this.setAppendingCancellation(true);
			this.update();
		} else if (this.pinState == PinState.CANCEL_REQUEST) {
			this.setAppendingCancellation(false);
			this.cancellationDelay = -1;
			this.c.sendMessage("Your pin is no longer going to be cancelled.");
		}
	}

	public void update() {
		if (this.appendingCancellation) {
			this.pin = "";
			this.cancellationDelay = -1;
			this.attempts = 0;
			this.locked = false;
			this.appendingCancellation = false;
			this.setAppendingCancellation(false);
			c.sendMessage("Your pin has successfully been reset. If you wish to set another pin, you may do so.");
		} else
			c.sendMessage("Your pin is still pending its cancellation and will be reset 3 days after the initial date.");
	}

	public boolean requiresUnlock() {
		return locked && pin.length() > 0;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public long getCancellationDelay() {
		return cancellationDelay;
	}

	public void setCancellationDelay(long cancellationDelay) {
		this.cancellationDelay = cancellationDelay;
	}

	public boolean isAppendingCancellation() {
		return appendingCancellation;
	}

	public void setAppendingCancellation(boolean appendingCancellation) {
		this.appendingCancellation = appendingCancellation;
	}

	public PinState getPinState() {
		return pinState;
	}

	public void setPinState(PinState pinState) {
		this.pinState = pinState;
	}

}
