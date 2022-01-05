package fir.sec.thi.doxarpgsystem;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;


public final class Doxaplg01 extends JavaPlugin implements Listener, CommandExecutor {

    public Stat s = new Stat();
    public GUI SGui = new GUI();
    public Attack A = new Attack();
    public Level L = new Level();

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Doxa On!");
        getServer().getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(getCommand("스탯")).setExecutor(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Doxa Off!");
        // Plugin shutdown logic
    }

    @EventHandler
    public void loadPS(PlayerMoveEvent p){
        Player player = p.getPlayer();
        long[] stat;
        stat = s.getStat(player.getUniqueId().toString());
        Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(stat[5] + stat[6]*5);
        Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(0.1 + stat[9]*0.001);

    }

    //스탯 명령어 ctrl + o
    public boolean onCommand(CommandSender talker, Command command, String label, String[] args) {
        if(talker instanceof Player){
            if (label.equals("스탯")) {
                SGui.StatusG((Player) talker);
                return true;
            }
            if (label.equals("수표")){
                if (args.length != 2){
                    talker.sendMessage("more");
                    return true;
                }
                int money = 0;
                int num = 0;
                Player p = (Player) talker;
                try {
                    money = Integer.parseInt(args[0]);
                    num = Integer.parseInt(args[1]);
                }catch (Exception e){
                    p.sendMessage("Error");
                }
                ItemStack paper = new ItemStack(Material.PAPER);
                ItemMeta im = paper.getItemMeta();
                assert im != null;
                im.setDisplayName(ChatColor.DARK_AQUA+"[ Ercanel ]"+ ChatColor.WHITE +args[0] + "골드");
                paper.setItemMeta(im);
                for (int i = 0; i < num; i++){
                    p.getInventory().addItem(paper);
                }

            }
        }
        return false;
    }

    @EventHandler
    public void onInvC(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("[ Status ]")){
            SGui.StatusGui(e);
        }
    }

    @EventHandler
    public void RangeAttack(EntityShootBowEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            Player player = (Player) event.getEntity();
            long[] stat;
            stat = s.getStat(player.getUniqueId().toString());
            stat[12] = (int) (event.getForce());
            s.setStat(player.getUniqueId().toString(), stat);
        }
    }

    @EventHandler
    public void EntityAttack(EntityDamageByEntityEvent event){
        A.Attack(event);
    }

    @EventHandler
    public void MK(EntityDeathEvent event){
        L.MonsterKill(event);
    }
}
