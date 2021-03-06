package eu.letsmine.session;

import java.lang.invoke.VarHandle;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings({"rawtypes", "unchecked"})
public class SessionObject {
	
	private final UUID uuid;
	private final Map<String, AtomicReference> objects = Collections.synchronizedMap(new HashMap<String, AtomicReference>());
	
	public SessionObject(UUID uuid) {
		this.uuid = uuid;
	}
	
	public UUID getUUID() {
		return uuid;
	}

    /**
     * Create a new Session Entry or update the existing.
     * Sets the value to {@code newValue}, with memory effects as specified by {@link VarHandle#setVolatile}.
     *
     * @param newValue the new value
     */
	public <V> void set(String key, V newValue) {
		AtomicReference<V> tmp = objects.get(key);
		if (tmp != null) {
			tmp.set(newValue);
		} else {
			objects.put(key, new AtomicReference<V>(newValue));
		}
	}
	
	public <V> AtomicReference<V> remove(String key) {
		return objects.remove(key);
	}
	
	public <V> AtomicReference<V> get(String key) {
		return objects.get(key);
	}
	
	public boolean contains(String key) {
		return objects.containsKey(key);
	}

	/**
     * Returns an <a href="Collection.html#unmodview">unmodifiable view</a> of all Session Entrys.
     * Query operations on the returned map "read through" the map.
     * Attempts to modify the returned map, whether direct or via its collection views, result in an {@code UnsupportedOperationException}.<p>
     * @return an unmodifiable view of the specified map.
     */
	public Map<String, AtomicReference> getSessionMembers() {
		return Collections.unmodifiableMap(objects);
	}
	
}