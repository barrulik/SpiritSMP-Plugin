package me.barrulik.spiritsmp;

import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class SpiritSMP extends JavaPlugin implements Listener {
    SpiritSMP instance;
    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);

        System.out.println("SpiritSMP by Barrulik");


        ItemManager.init();
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event)
    {
        Player p = event.getPlayer();
        if (isSpirit(p)) {
            turnToSpirit(p);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPlayedBefore()) {
            Player p = event.getPlayer();
            if ((int) (Math.random() * 2) == 0){
                // making player into a spirit
                turnToSpirit(p);
            }
        }
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("rasputin")) {
            Player p = (Player) sender;
            p.sendMessage("Ra ra rasputin");
        }
        return true;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            if (event.getItem() != null) {
                if (event.getItem().getItemMeta().equals(ItemManager.transformer.getItemMeta())) {
                    Player p = event.getPlayer();
                    event.getItem().setAmount(event.getItem().getAmount() - 1);
                    if (isSpirit(p)) {
                        turnToHuman(p);
                        getServer().broadcastMessage(ChatColor.GOLD + p.getName() + " Just became a Human");
                    } else {
                        getServer().broadcastMessage(ChatColor.AQUA + p.getName() + " Just became a Spirit");
                        turnToSpirit(p);
                    }
                }
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }







    private void turnToSpirit(Player p){
        getGenericMaxHealth(p).setBaseValue(24);
        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE-1, 1));
        p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Integer.MAX_VALUE-1, 1));
    }

    private void turnToHuman(Player p){
        getGenericMaxHealth(p).setBaseValue(20);
        p.removePotionEffect(PotionEffectType.INVISIBILITY);
        p.removePotionEffect(PotionEffectType.WEAKNESS);
    }

    private AttributeInstance getGenericMaxHealth(Player p){
        return p.getAttribute(Attribute.GENERIC_MAX_HEALTH);
    }

    private boolean isSpirit(Player p){
        return getGenericMaxHealth(p).getValue() == 24;
    }


}

