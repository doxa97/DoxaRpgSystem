package fir.sec.thi.doxarpgsystem;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
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

    private String lore;

    @EventHandler
    public void loadPS(PlayerMoveEvent p) {
        Player player = p.getPlayer();
        long[] stat;
        stat = s.getStat(player.getUniqueId().toString());
        stat[18] = (long) (0.1 + stat[9] * 0.001)+stat[27]+stat[28]+stat[29]+stat[30];
        stat[17] = stat[5] + stat[6] * 5 +stat[23]+stat[24]+stat[25]+stat[26];
        stat[11] = stat[19] + stat[20] + stat[21] + stat [22];
        Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(stat[17]);
        Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(stat[18]);

        ItemStack h = Objects.requireNonNull(player.getEquipment()).getHelmet();
        ItemStack c = Objects.requireNonNull(player.getEquipment()).getChestplate();
        ItemStack l = Objects.requireNonNull(player.getEquipment()).getLeggings();
        ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
        ArrayList lorelisth = (ArrayList) Objects.requireNonNull(Objects.requireNonNull(h).getItemMeta()).getLore();
        ArrayList lorelistc = (ArrayList) Objects.requireNonNull(Objects.requireNonNull(c).getItemMeta()).getLore();
        ArrayList lorelistl = (ArrayList) Objects.requireNonNull(Objects.requireNonNull(l).getItemMeta()).getLore();
        ArrayList lorelistb = (ArrayList) Objects.requireNonNull(Objects.requireNonNull(b).getItemMeta()).getLore();
        for (Object string : Objects.requireNonNull(lorelisth)){
            String s1 = String.valueOf(Objects.requireNonNull(h.getItemMeta().getLore()).contains("체력 증가"));
            String s2 = String.valueOf(h.getItemMeta().getLore().contains("방어력"));
            String s3 = String.valueOf(h.getItemMeta().getLore().contains("이동 속도"));
            if (((String) string).length() >= s1.length() && ((String) string).substring(0, s1.length()).equalsIgnoreCase(s1)){
                int len = s1.length();
                stat[19] = Long.parseLong(s1.replace("체력 증가 : ", ""));
            }if (((String) string).length() >= s2.length() && ((String) string).substring(0, s2.length()).equalsIgnoreCase(s2)){
                int len = s2.length();
                stat[23] = Long.parseLong(s2.replace("방어력 : ", ""));
            }if (((String) string).length() >= s3.length() && ((String) string).substring(0, s3.length()).equalsIgnoreCase(s3)){
                int len = s3.length();
                stat[27] = Long.parseLong(s3.replace("이동 속도 증가 : ", ""));
            }
        }
        for (Object string : Objects.requireNonNull(lorelistc)){
            String s1 = String.valueOf(Objects.requireNonNull(c.getItemMeta().getLore()).contains("체력 증가"));
            String s2 = String.valueOf(c.getItemMeta().getLore().contains("방어력"));
            String s3 = String.valueOf(c.getItemMeta().getLore().contains("이동 속도"));
            if (((String) string).length() >= s1.length() && ((String) string).substring(0, s1.length()).equalsIgnoreCase(s1)){
                int len = s1.length();
                stat[20] = Long.parseLong(s1.replace("체력 증가 : ", ""));
            }else if (((String) string).length() >= s2.length() && ((String) string).substring(0, s2.length()).equalsIgnoreCase(s2)){
                int len = s2.length();
                stat[24] = Long.parseLong(s2.replace("방어력 : ", ""));
            }else if (((String) string).length() >= s3.length() && ((String) string).substring(0, s3.length()).equalsIgnoreCase(s3)){
                int len = s3.length();
                stat[28] = Long.parseLong(s3.replace("이동 속도 증가 : ", ""));
            }
        }
        for (Object string : Objects.requireNonNull(lorelistl)){
            String s1 = String.valueOf(Objects.requireNonNull(l.getItemMeta().getLore()).contains("체력 증가"));
            String s2 = String.valueOf(l.getItemMeta().getLore().contains("방어력"));
            String s3 = String.valueOf(l.getItemMeta().getLore().contains("이동 속도"));
            if (((String) string).length() >= s1.length() && ((String) string).substring(0, s1.length()).equalsIgnoreCase(s1)){
                int len = s1.length();
                stat[21] = Long.parseLong(s1.replace("체력 증가 : ", ""));
            }else if (((String) string).length() >= s2.length() && ((String) string).substring(0, s2.length()).equalsIgnoreCase(s2)){
                int len = s2.length();
                stat[25] = Long.parseLong(s2.replace("방어력 : ", ""));
            }else if (((String) string).length() >= s3.length() && ((String) string).substring(0, s3.length()).equalsIgnoreCase(s3)){
                int len = s3.length();
                stat[29] = Long.parseLong(s3.replace("이동 속도 증가 : ", ""));
            }
        }
        for (Object string : Objects.requireNonNull(lorelistb)){
            String s1 = String.valueOf(Objects.requireNonNull(b.getItemMeta().getLore()).contains("체력 증가"));
            String s2 = String.valueOf(b.getItemMeta().getLore().contains("방어력"));
            String s3 = String.valueOf(b.getItemMeta().getLore().contains("이동 속도"));
            if (((String) string).length() >= s1.length() && ((String) string).substring(0, s1.length()).equalsIgnoreCase(s1)){
                int len = s1.length();
                stat[22] = Long.parseLong(s1.replace("체력 증가 : ", ""));
            }else if (((String) string).length() >= s2.length() && ((String) string).substring(0, s2.length()).equalsIgnoreCase(s2)){
                int len = s2.length();
                stat[26] = Long.parseLong(s2.replace("방어력 : ", ""));
            }else if (((String) string).length() >= s3.length() && ((String) string).substring(0, s3.length()).equalsIgnoreCase(s3)){
                int len = s3.length();
                stat[30] = Long.parseLong(s3.replace("이동 속도 증가 : ", ""));
            }
        }

    }

    //스탯 명령어 ctrl + o
    public boolean onCommand(CommandSender talker, Command command, String label, String[] args) {
        if (talker instanceof Player) {
            if (label.equals("스탯")) {
                SGui.StatusG((Player) talker);
                return true;
            }
            if (label.equals("수표")) {
                if (args.length == 0) {
                    talker.sendMessage(ChatColor.DARK_AQUA + "[ Ercanel ]" + ChatColor.WHITE + "/수표 [금액] [개수]");
                    return true;
                }
                if (args.length == 1) {
                    int money = 0;
                    Player p = (Player) talker;
                    try {
                        money = Integer.parseInt(args[0]);
                    } catch (Exception e) {
                        p.sendMessage("Error");
                    }
                    if (money < 0) {
                        talker.sendMessage(ChatColor.DARK_AQUA + "[ Ercanel ]" + ChatColor.WHITE + "음수는 제공되지 않습니다 고객님 ^^7");
                    } else {
                        ItemStack paper = new ItemStack(Material.PAPER);
                        ItemMeta im = paper.getItemMeta();
                        assert im != null;
                        im.setDisplayName(ChatColor.DARK_AQUA + "[ Ercanel ]" + ChatColor.WHITE + args[0] + "골드");
                        paper.setItemMeta(im);
                        p.getInventory().addItem(paper);
                    }
                }
                if (args.length == 2) {
                    int money = 0;
                    int num = 0;
                    Player p = (Player) talker;
                    try {
                        money = Integer.parseInt(args[0]);
                        num = Integer.parseInt(args[1]);
                    } catch (Exception e) {
                        p.sendMessage("Error");
                    }
                    if (money < 0) {
                        talker.sendMessage(ChatColor.DARK_AQUA + "[ Ercanel ]" + ChatColor.WHITE + "음수는 제공되지 않습니다 고객님 ^^7");
                    } else {
                        ItemStack paper = new ItemStack(Material.PAPER);
                        ItemMeta im = paper.getItemMeta();
                        assert im != null;
                        im.setDisplayName(ChatColor.DARK_AQUA + "[ Ercanel ]" + ChatColor.WHITE + args[0] + "골드");
                        paper.setItemMeta(im);
                        for (int i = 0; i < num; i++) {
                            p.getInventory().addItem(paper);
                        }
                    }
                }
            }
            if (label.equals("mobspawn")) {
                if (args.length != 6) {
                    talker.sendMessage("Error");
                    return true;
                }
                EntityType e = EntityType.valueOf(args[2]);
                int s = Integer.parseInt(args[3]);
                int h = Integer.parseInt(args[5]);
                int a = Integer.parseInt(args[4]);
                SpawnRPGEntity(((Player) talker).getLocation(), ((Player) talker).getWorld(), e, s, h, a, ChatColor.DARK_RED + "LV." + args[1] + " " + ChatColor.GRAY + args[0]);
            }
            if (label.equals("장비")){
                if (args.length < 3){
                    talker.sendMessage("/장비 이름 메테리얼 종류 등급 설명 장비능력치");
                                        //    0    1      2   3   4     5
                }
                else {
                    if (args[2].equals("무기")){
                        switch (args[3]) {
                            case "일반": {
                                switch (args[6]) {
                                    case "근접": {
                                        ItemStack cw = new ItemStack(Material.valueOf(args[1]));
                                        ItemMeta weapon = cw.getItemMeta();
                                        Objects.requireNonNull(weapon).setDisplayName(args[0]);
                                        if (weapon.getDisplayName().contains("_")){
                                            weapon.setDisplayName(weapon.getDisplayName().replace("_", " "));
                                        }
                                        weapon.setLore(Arrays.asList("[ 무기 ]"+ "        "+ "[ 일반 ]", args[4],"레벨 제한 : " + args[5], "근접 공격력 : " + args[7], "치명타 확률 : " + args[8]));
                                        cw.setItemMeta(weapon);
                                        ((Player) talker).getInventory().addItem(cw);
                                        return false;
                                    }
                                    case "원거리": {
                                        ItemStack cw = new ItemStack(Material.valueOf(args[1]));
                                        ItemMeta weapon = cw.getItemMeta();
                                        Objects.requireNonNull(weapon).setDisplayName(args[0]);
                                        if (weapon.getDisplayName().contains("_")) {
                                            weapon.setDisplayName(weapon.getDisplayName().replace("_", " "));
                                        }
                                        weapon.setLore(Arrays.asList("[ 무기 ]"+ "        "+ "[ 일반 ]", args[4],"레벨 제한 : " + args[5], "원거리 공격력 : " + args[7], "치명타 확률 : " + args[8]));
                                        cw.setItemMeta(weapon);
                                        ((Player) talker).getInventory().addItem(cw);
                                        return false;
                                    }
                                    case "마법": {
                                        ItemStack cw = new ItemStack(Material.valueOf(args[1]));
                                        ItemMeta weapon = cw.getItemMeta();
                                        Objects.requireNonNull(weapon).setDisplayName(args[0]);
                                        if (weapon.getDisplayName().contains("_")) {
                                            weapon.setDisplayName(weapon.getDisplayName().replace("_", " "));
                                        }
                                        weapon.setLore(Arrays.asList("[ 무기 ]"+ "        "+ "[ 일반 ]", args[4],"레벨 제한 : " + args[5], "마법 공격력 : " + args[7], "치명타 확률 : " + args[8]));
                                        cw.setItemMeta(weapon);
                                        ((Player) talker).getInventory().addItem(cw);
                                        return false;
                                    }
                                }
                            }
                            case "레어": {
                                switch (args[6]) {
                                    case "근접": {
                                        ItemStack cw = new ItemStack(Material.valueOf(args[1]));
                                        ItemMeta weapon = cw.getItemMeta();
                                        Objects.requireNonNull(weapon).setDisplayName(args[0]);
                                        if (weapon.getDisplayName().contains("_")) {
                                            weapon.setDisplayName(weapon.getDisplayName().replace("_", " "));
                                        }
                                        weapon.setLore(Arrays.asList("[ 무기 ]"+ "        "+ "[ 레어 ]", args[4],"레벨 제한 : " + args[5], "근접 공격력 : " + args[7], "치명타 확률 : " + args[8]));
                                        cw.setItemMeta(weapon);
                                        ((Player) talker).getInventory().addItem(cw);
                                        return false;
                                    }
                                    case "원거리": {
                                        ItemStack cw = new ItemStack(Material.valueOf(args[1]));
                                        ItemMeta weapon = cw.getItemMeta();
                                        Objects.requireNonNull(weapon).setDisplayName(args[0]);
                                        if (weapon.getDisplayName().contains("_")) {
                                            weapon.setDisplayName(weapon.getDisplayName().replace("_", " "));
                                        }
                                        weapon.setLore(Arrays.asList("[ 무기 ]"+ "        "+ "[ 레어 ]", args[4],"레벨 제한 : " + args[5], "원거리 공격력 : " + args[7], "치명타 확률 : " + args[8]));
                                        cw.setItemMeta(weapon);
                                        ((Player) talker).getInventory().addItem(cw);
                                        return false;
                                    }
                                    case "마법": {
                                        ItemStack cw = new ItemStack(Material.valueOf(args[1]));
                                        ItemMeta weapon = cw.getItemMeta();
                                        Objects.requireNonNull(weapon).setDisplayName(args[0]);
                                        weapon.setLore(Arrays.asList("[ 무기 ]"+ "        "+ "[ 레어 ]", args[4],"레벨 제한 : " + args[5], "마법 공격력 : " + args[7], "치명타 확률 : " + args[8]));
                                        cw.setItemMeta(weapon);
                                        ((Player) talker).getInventory().addItem(cw);
                                        return false;
                                    }
                                }
                            }
                            case "에픽": {
                                switch (args[6]) {
                                    case "근접": {
                                        ItemStack cw = new ItemStack(Material.valueOf(args[1]));
                                        ItemMeta weapon = cw.getItemMeta();
                                        Objects.requireNonNull(weapon).setDisplayName(args[0]);
                                        if (weapon.getDisplayName().contains("_")) {
                                            weapon.setDisplayName(weapon.getDisplayName().replace("_", " "));
                                        }
                                        weapon.setLore(Arrays.asList("[ 무기 ]"+ "        "+ "[ 에픽 ]", args[4],"레벨 제한 : " + args[5], "근접 공격력 : " + args[7], "치명타 확률 : " + args[8]));
                                        cw.setItemMeta(weapon);
                                        ((Player) talker).getInventory().addItem(cw);
                                        return false;
                                    }
                                    case "원거리": {
                                        ItemStack cw = new ItemStack(Material.valueOf(args[1]));
                                        ItemMeta weapon = cw.getItemMeta();
                                        Objects.requireNonNull(weapon).setDisplayName(args[0]);
                                        if (weapon.getDisplayName().contains("_")) {
                                            weapon.setDisplayName(weapon.getDisplayName().replace("_", " "));
                                        }
                                        weapon.setLore(Arrays.asList("[ 무기 ]"+ "        "+ "[ 에픽 ]", args[4],"레벨 제한 : " + args[5], "원거리 공격력 : " + args[7], "치명타 확률 : " + args[8]));
                                        cw.setItemMeta(weapon);
                                        ((Player) talker).getInventory().addItem(cw);
                                        return false;
                                    }
                                    case "마법": {
                                        ItemStack cw = new ItemStack(Material.valueOf(args[1]));
                                        ItemMeta weapon = cw.getItemMeta();
                                        Objects.requireNonNull(weapon).setDisplayName(args[0]);
                                        if (weapon.getDisplayName().contains("_")) {
                                            weapon.setDisplayName(weapon.getDisplayName().replace("_", " "));
                                        }
                                        weapon.setLore(Arrays.asList("[ 무기 ]"+ "        "+ "[ 에픽 ]", args[4],"레벨 제한 : " + args[5], "마법 공격력 : " + args[7], "치명타 확률 : " + args[8]));
                                        cw.setItemMeta(weapon);
                                        ((Player) talker).getInventory().addItem(cw);
                                        return false;
                                    }
                                }
                            }
                            case "전설": {
                                switch (args[6]) {
                                    case "근접": {
                                        ItemStack cw = new ItemStack(Material.valueOf(args[1]));
                                        ItemMeta weapon = cw.getItemMeta();
                                        Objects.requireNonNull(weapon).setDisplayName(args[0]);
                                        if (weapon.getDisplayName().contains("_")) {
                                            weapon.setDisplayName(weapon.getDisplayName().replace("_", " "));
                                        }
                                        weapon.setLore(Arrays.asList("[ 무기 ]"+ "        "+ "[ 전설 ]", args[4],"레벨 제한 : " + args[5], "근접 공격력 : " + args[7], "치명타 확률 : " + args[8]));
                                        cw.setItemMeta(weapon);
                                        ((Player) talker).getInventory().addItem(cw);
                                        return false;
                                    }
                                    case "원거리": {
                                        ItemStack cw = new ItemStack(Material.valueOf(args[1]));
                                        ItemMeta weapon = cw.getItemMeta();
                                        Objects.requireNonNull(weapon).setDisplayName(args[0]);
                                        if (weapon.getDisplayName().contains("_")) {
                                            weapon.setDisplayName(weapon.getDisplayName().replace("_", " "));
                                        }
                                        weapon.setLore(Arrays.asList("[ 무기 ]"+ "        "+ "[ 전설 ]", args[4],"레벨 제한 : " + args[5], "원거리 공격력 : " + args[7], "치명타 확률 : " + args[8]));
                                        cw.setItemMeta(weapon);
                                        ((Player) talker).getInventory().addItem(cw);
                                        return false;
                                    }
                                    case "마법": {
                                        ItemStack cw = new ItemStack(Material.valueOf(args[1]));
                                        ItemMeta weapon = cw.getItemMeta();
                                        Objects.requireNonNull(weapon).setDisplayName(args[0]);
                                        if (weapon.getDisplayName().contains("_")) {
                                            weapon.setDisplayName(weapon.getDisplayName().replace("_", " "));
                                        }
                                        weapon.setLore(Arrays.asList("[ 무기 ]"+ "        "+ "[ 전설 ]", args[4],"레벨 제한 : " + args[5], "마법 공격력 : " + args[7], "치명타 확률 : " + args[8]));
                                        cw.setItemMeta(weapon);
                                        ((Player) talker).getInventory().addItem(cw);
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                    else if (args[2].equals("갑옷")){
                        switch (args[3]) {
                            case "일반": {
                                ItemStack ca = new ItemStack(Material.valueOf(args[1]));
                                ItemMeta armor = ca.getItemMeta();
                                Objects.requireNonNull(armor).setDisplayName(args[0]);
                                if (armor.getDisplayName().contains("_")) {
                                    armor.setDisplayName(armor.getDisplayName().replace("_", " "));
                                }
                                armor.setLore(Arrays.asList("[ 갑옷 ]"+ "        "+ "[ 일반 ]", args[4], "레벨 제한 : "+args[5], "체력 증가 : "+args[6], "방어력 : "+args[7], "이동 속도 : " + args[8]));
                                ca.setItemMeta(armor);
                                ((Player) talker).getInventory().addItem(ca);
                                return false;
                            }
                            case "레어": {
                                ItemStack ca = new ItemStack(Material.valueOf(args[1]));
                                ItemMeta armor = ca.getItemMeta();
                                Objects.requireNonNull(armor).setDisplayName(args[0]);
                                if (armor.getDisplayName().contains("_")) {
                                    armor.setDisplayName(armor.getDisplayName().replace("_", " "));
                                }
                                armor.setLore(Arrays.asList("[ 갑옷 ]"+ "        "+ "[ 레어 ]", args[4], "레벨 제한 : "+args[5], "체력 증가 : "+args[6], "방어력 : "+args[7], "이동 속도 : " + args[8]));
                                ca.setItemMeta(armor);
                                ((Player) talker).getInventory().addItem(ca);
                                return false;
                            }
                            case "에픽": {
                                ItemStack ca = new ItemStack(Material.valueOf(args[1]));
                                ItemMeta armor = ca.getItemMeta();
                                Objects.requireNonNull(armor).setDisplayName(args[0]);
                                if (armor.getDisplayName().contains("_")) {
                                    armor.setDisplayName(armor.getDisplayName().replace("_", " "));
                                }
                                armor.setLore(Arrays.asList("[ 갑옷 ]"+ "        "+ "[ 에픽 ]", args[4], "레벨 제한 : "+args[5], "체력 증가 : "+args[6], "방어력 : "+args[7], "이동 속도 : " + args[8]));
                                ca.setItemMeta(armor);
                                ((Player) talker).getInventory().addItem(ca);
                                return false;
                            }
                            case "전설": {
                                ItemStack ca = new ItemStack(Material.valueOf(args[1]));
                                ItemMeta armor = ca.getItemMeta();
                                Objects.requireNonNull(armor).setDisplayName(args[0]);
                                if (armor.getDisplayName().contains("_")) {
                                    armor.setDisplayName(armor.getDisplayName().replace("_", " "));
                                }
                                armor.setLore(Arrays.asList("[ 갑옷 ]"+ "        "+ "[ 전설 ]", args[4], "레벨 제한 : "+args[5], "체력 증가 : "+args[6], "방어력 : "+args[7], "이동 속도 : " + args[8]));
                                ca.setItemMeta(armor);
                                ((Player) talker).getInventory().addItem(ca);
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @EventHandler
    public void onInvC(InventoryClickEvent e) {
        e.getWhoClicked();
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("[ Status ]")) {
            SGui.StatusGui(e);
        }
    }

    @EventHandler
    public void RangeAttack(EntityShootBowEvent event) {
        A.RangeAttack(event);}

    @EventHandler
    public void EntityAttack(EntityDamageByEntityEvent event) {
        A.Attack(event);
    }

    @EventHandler
    public void FirstJoin(PlayerJoinEvent event){
        s.CreateNewStat(event.getPlayer().getUniqueId().toString());
    }

    @EventHandler
    public void MK(EntityDeathEvent event) {
        L.MonsterKill(event);
    }

    public void SpawnRPGEntity(Location location, World world, EntityType entityType, double speed, double attack, double health, String name) {
        LivingEntity entity = (LivingEntity) world.spawnEntity(location, entityType);
        entity.setCustomName(name);
        entity.setCustomNameVisible(true);
        Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(speed * 0.2);
        Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(health);
        Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)).setBaseValue(attack);
        entity.setHealth(health);
    }

}
