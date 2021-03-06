package eu.letsmine.session.bungee;

import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import eu.letsmine.session.SessionAPI;
import eu.letsmine.session.event.bungee.SessionResumed;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class SessionPlugin extends Plugin implements Listener {
	private final Vector<UUID> delList;
	
	public SessionPlugin() {
		delList = new Vector<UUID>();
	}
	
	@Override
	public void onEnable() {
		getProxy().getPluginManager().registerListener(this, this);
	}
	
	@EventHandler
	public void onPlayerJoin(PostLoginEvent event) {
		if (delList.remove(event.getPlayer().getUniqueId())) {
			getProxy().getPluginManager().callEvent(new SessionResumed(event.getPlayer().getUniqueId()));
		}
	}
	
	@EventHandler
	public void onPlayerDisconnect(PlayerDisconnectEvent event) {
		onPlayerLeave(event.getPlayer().getUniqueId());
	}
	
	private void onPlayerLeave(UUID uuid) {
		if (SessionAPI.hasSessionObject(uuid)) {
			delList.add(uuid);
			var so = SessionAPI.getSessionObject(uuid);
			var hs = so.<Long>get(SessionAPI.SessionKeyTimeout);
			if (hs != null) {
				var timeout = hs.get();
				getProxy().getScheduler().schedule(this, () -> {
					if (delList.remove(uuid)) {
						SessionAPI.removeSessionObject(uuid);
					}
				}, timeout, TimeUnit.SECONDS);
			} else {
				SessionAPI.removeSessionObject(uuid);
			}
		}
	}
	
}