package net.goldtreeservers.worldguardextraflags.wg.handlers;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.session.MoveType;
import com.sk89q.worldguard.session.Session;
import com.sk89q.worldguard.session.handler.Handler;

import net.goldtreeservers.worldguardextraflags.WorldGuardExtraFlagsPlugin;
import net.goldtreeservers.worldguardextraflags.flags.Flags;
import net.goldtreeservers.worldguardextraflags.wg.WorldGuardUtils;

public class TeleportOnEntryFlagHandler extends Handler
{
	public static final Factory FACTORY = new Factory();
    public static class Factory extends Handler.Factory<TeleportOnEntryFlagHandler>
    {
        @Override
        public TeleportOnEntryFlagHandler create(Session session)
        {
            return new TeleportOnEntryFlagHandler(session);
        }
    }
	    
	protected TeleportOnEntryFlagHandler(Session session)
	{
		super(session);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCrossBoundary(Player player, Location from, Location to, ApplicableRegionSet toSet, Set<ProtectedRegion> entered, Set<ProtectedRegion> exited, MoveType moveType)
	{
		if (!player.hasMetadata(WorldGuardUtils.PREVENT_TELEPORT_LOOP_META))
		{
			com.sk89q.worldedit.Location location = WorldGuardUtils.queryValue(player, to.getWorld(), entered, Flags.TELEPORT_ON_ENTRY);
			if (location != null)
			{
				player.setMetadata(WorldGuardUtils.PREVENT_TELEPORT_LOOP_META, new FixedMetadataValue(WorldGuardExtraFlagsPlugin.getPlugin(), true));
				player.teleport(BukkitUtil.toLocation(location));
				
				return false;
			}
		}
		
		return true;
	}
}
