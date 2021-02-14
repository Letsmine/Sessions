package eu.letsmine.session;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

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
		objects.put(path, new AtomicReference<V>(value));
	}
	
	/**
	 * 
	 * @param path 
	 * @param update (old) -> old != null ? old : new SessionMember<T>(...)
	 */
	public <V> void set(String path, Function<AtomicReference<V>, AtomicReference<V>> update) {
		var tmp = objects.get(path);
		if (tmp != null) {
			tmp = update.apply(tmp);
		}
		objects.put(path, tmp);
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