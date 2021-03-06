package eu.letsmine.session;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionAPI {
	
	private static Map<UUID, SessionObject> sessions = Collections.synchronizedMap(new HashMap<UUID, SessionObject>());
	
	/**
	 * (Long)
	 * Milliseconds after the Session should be delete itself
	 */
	public static final String SessionKeyTimeout = "timeout";
	
	/**
	 * {@code SessionKeyTimeout} value for new SessionObjects
	 */
	public static long SessionDefaultTimeout = 600L;
	
	public static SessionObject getSessionObject(UUID uuid) {
		synchronized (sessions) {
			var result = sessions.get(uuid);
			if (result == null) {
				result = new SessionObject(uuid);
				result.set(SessionKeyTimeout, SessionDefaultTimeout);
				sessions.put(uuid, result);
			}
			return result;
		}
	}
	
	public static boolean hasSessionObject(UUID uuid) {
		return sessions.containsKey(uuid);
	}
	
	/**
	 * Remove the Session for the specified UUID
	 * @param uuid uuid whose Session is to be removed from the map
	 * @return the previous value associated with {@code key}, or
	 * {@code null} if there was no mapping for {@code key}.
	 */
	public static SessionObject removeSessionObject(UUID uuid) {
		return sessions.remove(uuid);
	}
	
	/**
     * Returns an <a href="Collection.html#unmodview">unmodifiable view</a> of all Sessions.
     * Query operations on the returned map "read through" the map.
     * Attempts to modify the returned map, whether direct or via its collection views, result in an {@code UnsupportedOperationException}.<p>
     * @return an unmodifiable view of the specified map.
     */
	public static Map<UUID, SessionObject> getSessionMap() {
		return Collections.unmodifiableMap(sessions);
	}
	
}