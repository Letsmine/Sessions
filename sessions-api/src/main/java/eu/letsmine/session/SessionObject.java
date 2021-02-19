package eu.letsmine.session;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings({"rawtypes", "unchecked"})
public class SessionObject {
	
	private final UUID uuid;
	private final HashMap<String, AtomicReference> objects = new HashMap<String, AtomicReference>();
	
	public SessionObject(UUID uuid) {
		this.uuid = uuid;
	}
	
	public UUID getUUID() {
		return uuid;
	}
	
	public <V> void set(String path, V value) {
		AtomicReference<V> tmp = objects.get(path);
		if (tmp != null) {
			tmp.set(value);
		} else {
			objects.put(path, new AtomicReference<V>(value));
		}
	}
	
	public <V> AtomicReference<V> remove(String path) {
		return objects.remove(path);
	}
	
	public <V> AtomicReference<V> get(String path) {
		return objects.get(path);
	}
	
	public boolean contains(String path) {
		return objects.containsKey(path);
	}
	
	/**
	 * unmodifiableMap
	 * @return
	 */
	public Map<String, AtomicReference> getSessionMembers() {
		return Collections.unmodifiableMap(objects);
	}
	
}