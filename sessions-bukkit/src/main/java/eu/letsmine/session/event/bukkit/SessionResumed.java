package eu.letsmine.session.event.bukkit;

import java.util.UUID;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import eu.letsmine.session.SessionAPI;
import eu.letsmine.session.SessionObject;

public class SessionResumed extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final UUID uuid;
    
    public SessionResumed(UUID uuid) {
    	this.uuid = uuid;
	}
    
    public SessionObject getSessionObject() {
    	return SessionAPI.getSessionObject(uuid);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}