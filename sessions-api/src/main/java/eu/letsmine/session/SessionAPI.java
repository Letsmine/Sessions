package eu.letsmine.session;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionAPI {
	
	private static HashMap<UUID, SessionObject> sessionList = new HashMap<UUID, SessionObject>();
	
	public static SessionObject getSessionObject(UUID uuid) {
		synchronized (sessionList) {
			var result = sessionList.get(uuid);
			if (result == null) {
				result = new SessionObject(uuid);
				result.set("timeout", 600L);
				sessionList.put(uuid, result);
			}
			return result;
		}
	}
	
	public static boolean hasSessionObject(UUID uuid) {
		synchronized (sessionList) {
			return sessionList.containsKey(uuid);
		}
	}
	
	public static SessionObject removeSessionObject(UUID uuid) {
		synchronized (sessionList) {
			return sessionList.remove(uuid);
		}
	}
	
	/**
	 * Gets a unmodifiableMap
	 * @return
	 */
	public static Map<UUID, SessionObject> getSessionList() {
		return Collections.unmodifiableMap(sessionList);
	}
	
}