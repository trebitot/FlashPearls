package me.znickq.flash;

import java.io.PrintStream;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Main extends JavaPlugin
  implements Listener
{
  private int intensity;
  private int last1;
  private int last2;
  private int last3;

  public void onEnable()
  {
    getServer().getPluginManager().registerEvents(this, this);
    getConfig().addDefault("effect-seconds", Integer.valueOf(4));
    getConfig().addDefault("intensity", Integer.valueOf(1));
    getConfig().addDefault("Last1", Integer.valueOf(4));
    getConfig().addDefault("Last2", Integer.valueOf(8));
    getConfig().addDefault("Last3", Integer.valueOf(12));
    getConfig().options().copyDefaults(true);
    saveConfig();
    this.intensity = getConfig().getInt("intensity");
    this.last1 = getConfig().getInt("Last1");
    this.last2 = getConfig().getInt("Last2");
    this.last3 = getConfig().getInt("Last3");
    addRecipe();
  }
  
 
  private void addRecipe() {
	    ShapelessRecipe Flash0 = new ShapelessRecipe(new ItemStack(Material.ENDER_PEARL, 1, (short) 1));
	    ShapelessRecipe Flash1 = new ShapelessRecipe(new ItemStack(Material.ENDER_PEARL, 1, (short) 2));
	    ShapelessRecipe Flash2 = new ShapelessRecipe(new ItemStack(Material.ENDER_PEARL, 1, (short) 3));
	    Flash0.addIngredient(Material.GLOWSTONE_DUST).addIngredient(Material.REDSTONE).addIngredient(Material.FLINT).addIngredient(Material.SULPHUR);
	    Flash1.addIngredient(1, Material.ENDER_PEARL, 1).addIngredient(Material.GLOWSTONE_DUST);
	    Flash2.addIngredient(1, Material.ENDER_PEARL, 2).addIngredient(Material.GLOWSTONE_DUST);
	    getServer().addRecipe(Flash0);
	    getServer().addRecipe(Flash1);
	    getServer().addRecipe(Flash2);
	  }
  
  @EventHandler
  public void onTeleport(PlayerTeleportEvent event) {
    if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
      byte dura = event.getPlayer().getItemInHand().getData().getData();
      System.out.println("Got " + dura + "!");
      int lastSeconds = 0;
      if (dura == 1)
        lastSeconds = this.last1;
      if (dura == 2)
        lastSeconds = this.last2;
      if (dura == 3) {
        lastSeconds = this.last3;
      }
      event.setCancelled(true);
      Location location = event.getTo();
      for (Player player : Bukkit.getOnlinePlayers()) {
        if ((!player.getWorld().getName().equals(event.getPlayer().getWorld().getName())) || 
          (player.getLocation().distance(location) > 5.0D)) continue;
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * lastSeconds, this.intensity));
      }
    }
  }
  public void onDisable()
  {
  }
}