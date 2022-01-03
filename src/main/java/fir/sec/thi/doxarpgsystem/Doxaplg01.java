package fir.sec.thi.doxarpgsystem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;


public final class Doxaplg01 extends JavaPlugin implements Listener, CommandExecutor {

    public Stat s = new Stat();
    public GUI SGui = new GUI();
    public Attack A = new Attack();

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Doxa On!");
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("스탯").setExecutor(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Doxa Off!");
        // Plugin shutdown logic
    }

    //스탯 명령어 clrl + o
    public boolean onCommand(CommandSender talker, Command command, String label, String[] args) {
        if(talker instanceof Player){
            if (label.equals("스탯")) {
                if (label.equals("스탯")) {
                    SGui.StatusG((Player) talker);
                    return true;
                }
            }
        }
        return false;
    }

    @EventHandler
    public void onInvC(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        if (e.getClickedInventory().equals("[ Status ]")){
            SGui.StatusGui(e);
        }
    }

    @EventHandler
    public void EntityAttack(EntityDamageByEntityEvent event){
        A.Attack(event);
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent j){
        s.CreateNewStat(j.getPlayer().getUniqueId().toString());
    }
}
