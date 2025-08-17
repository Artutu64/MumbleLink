package fr.artutu.mumblelink.utils;

import fr.artutu.mumblelink.mumble.MumbleUser;

public interface MuteChecker {
	
	public  boolean needToMuteWithoutBypass(MumbleUser user, String pseudo);

}
