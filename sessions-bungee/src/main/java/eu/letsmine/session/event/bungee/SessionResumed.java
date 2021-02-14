package eu.letsmine.session.event.bungee;

import java.util.UUID;

import eu.letsmine.session.SessionAPI;
import eu.letsmine.session.SessionObject;
import net.md_5.bungee.api.plugin.Event;

public class SessionResumed extends Event {
    private final UUID uuid;
    
    public SessionResumed(UUID uuid) {
    	this.uuid = uuid;
	}
    
    public SessionObject getSessionObject() {
    	return SessionAPI.getSessionObject(uuid);
    }
    
}