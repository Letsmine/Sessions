package eu.letsmine.session.bukkit;

import java.util.UUID;
import java.util.Vector;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import eu.letsmine.session.SessionAPI;
import eu.letsmine.session.event.bukkit.SessionResumed;

public class SessionPlugin extends JavaPlugin implements Listener {
	
	private final Vector<UUID> delList;
	
	public SessionPlugin() {
		delList = new Vector<UUID>();
	}
	
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable() {
		
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (delList.remove(event.getPlayer().getUniqueId())) {
			Bukkit.getPluginManager().callEvent(new SessionResumed(event.getPlayer().getUniqueId()));
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		onPlayerLeave(event.getPlayer().getUniqueId());
	}
	
	private void onPlayerLeave(UUID uuid) {
		if (SessionAPI.hasSessionObject(uuid)) {
			var so = SessionAPI.getSessionObject(uuid);
			var hs = so.<Long>get(SessionAPI.SessionKeyTimeout);
			if (hs != null) {
				var timeout = hs.get();
				delList.add(uuid);
				getServer().getScheduler().runTaskLater(this, () -> {
					if (delList.remove(uuid)) {
						SessionAPI.removeSessionObject(uuid);
					}
				}, timeout * 20);
			} else {
				SessionAPI.removeSessionObject(uuid);
			}
		}
	}
	
}