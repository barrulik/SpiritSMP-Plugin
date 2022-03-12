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
        } else {
            turnToHuman(p);
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
            p.sendMessage("Hey, hey, hey, hey, hey, hey, hey, hey\n" +
                    "Hey, hey, hey, hey, hey, hey, hey, hey\n" +
                    "Hey, hey, hey, hey, hey, hey, hey, hey\n\n" +
                    "There lived a certain man in Russia long ago\n" +
                    "He was big and strong, in his eyes a flaming glow\n" +
                    "Most people looked at him with terror and with fear\n" +
                    "But to Moscow chicks he was such a lovely dear\n" +
                    "He could preach the Bible like a preacher\n" +
                    "Full of ecstasy and fire\n" +
                    "But he also was the kind of teacher\n" +
                    "Women would desire\n\n" +
                    "Ra ra Rasputin\n" +
                    "Lover of the Russian queen\n" +
                    "There was a cat that really was gone\n" +
                    "Ra ra Rasputin\n" +
                    "Russia's greatest love machine\n" +
                    "It was a shame how he carried on\n\n" +
                    "He ruled the Russian land and never mind the Czar\n" +
                    "But the kazachok he danced really wunderbar\n" +
                    "In all affairs of state he was the man to please\n" +
                    "But he was real great when he had a girl to squeeze\n" +
                    "For the queen he was no wheeler dealer\n" +
                    "Though she'd heard the things he'd done\n" +
                    "She believed he was a holy healer\n" +
                    "Who would heal her son\n\n" +
                    "Ra ra Rasputin\n" +
                    "Lover of the Russian queen\n" +
                    "There was a cat that really was gone\n" +
                    "Ra ra Rasputin\n" +
                    "Russia's greatest love machine\n" +
                    "It was a shame how he carried on\n\n" +
                    "But when his drinking and lusting\n" +
                    "And his hunger for power\n" +
                    "Became known to more and more people\n" +
                    "The demands to do something\n" +
                    "About this outrageous man\n" +
                    "Became louder and louder\n\n" +
                    "Hey, hey, hey, hey, hey, hey, hey, hey\n" +
                    "Hey, hey, hey, hey, hey, hey, hey, hey\n" +
                    "Hey, hey, hey, hey, hey, hey, hey, hey\n" +
                    "Hey, hey, hey, hey, hey, hey, hey, hey\n\n" +
                    "\"This man's just got to go\", declared his enemies\n" +
                    "But the ladies begged, \"Don't you try to do it, please\"\n" +
                    "No doubt this Rasputin had lots of hidden charms\n" +
                    "Though he was a brute, they just fell into his arms\n" +
                    "Then one night some men of higher standing\n" +
                    "Set a trap, they're not to blame\n" +
                    "\"Come to visit us\", they kept demanding\n" +
                    "And he really came\n\n" +
                    "Ra ra Rasputin\n" +
                    "Lover of the Russian queen\n" +
                    "They put some poison into his wine\n" +
                    "Ra ra Rasputin\n" +
                    "Russia's greatest love machine\n" +
                    "He drank it all and said, \"I feel fine\"\n\n" +
                    "Ra ra Rasputin\n" +
                    "Lover of the Russian queen\n" +
                    "They didn't quit, they wanted his head\n" +
                    "Ra ra Rasputin\n" +
                    "Russia's greatest love machine\n" +
                    "And so they shot him 'til he was dead");
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
        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE-1, 1,false,false));
        p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Integer.MAX_VALUE-1, 1,false,false));
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
        return getGenericMaxHealth(p).getBaseValue() == 24;
    }


}
