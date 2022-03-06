package fir.sec.thi.doxarpgsystem;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

@SuppressWarnings("unused")
public final class Doxaplg01 extends JavaPlugin implements Listener, CommandExecutor {

    public Stat s = new Stat();
    public GUI SGui = new GUI();
    public Attack A = new Attack();
    public DamageCalculator DC = new DamageCalculator();

    public HashMap<String, Integer> attack = new HashMap<>();
    public HashMap<String, Integer> defense = new HashMap<>();
    public HashMap<String, Integer> critical = new HashMap<>();
    public HashMap<String, Integer> health = new HashMap<>();
    public HashMap<String, Integer> regen = new HashMap<>();
    public HashMap<String, Integer> movespeed = new HashMap<>();
    public HashMap<String, Integer> level = new HashMap<>();


    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Doxa On!");
        getServer().getPluginManager().registerEvents(A, this);
        getServer().getPluginManager().registerEvents(new Level(), this);
        getServer().getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(getCommand("스탯")).setExecutor(this);
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for (Player player :Bukkit.getOnlinePlayers()) {

                    if (Objects.requireNonNull(player.getEquipment()).getItemInMainHand().getType() == Material.AIR || ! player.getEquipment().getItemInMainHand().hasItemMeta() || ! Objects.requireNonNull(player.getEquipment().getItemInMainHand().getItemMeta()).hasLore()){
                        attack.clear();
                    } else {

                        ArrayList<String> loremainhand = ((ArrayList<String>) Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(player).getEquipment()).getItemInMainHand().getItemMeta()).getLore());

                        int indexattack = Objects.requireNonNull(loremainhand).indexOf("공격력");
                        String ia = loremainhand.get(indexattack);
                        int indexcritical = Objects.requireNonNull(loremainhand).indexOf("치명타 확률");
                        String ic = loremainhand.get(indexcritical);

                        if (ia.contains("근접")) {
                            attack.put("근접 공격력", Integer.valueOf(ChatColor.stripColor(ia.replace("근접 공격력 : ", ""))));
                            attack.remove("원거리 공격력");
                            attack.remove("마법 공격력");
                        }
                        if (ia.contains("원거리")) {
                            attack.put("원거리 공격력", Integer.valueOf(ChatColor.stripColor(ia.replace("원거리 공격력 : ", ""))));
                            attack.remove("근접 공격력");
                            attack.remove("마법 공격력");
                        }
                        if (ia.contains("마법")) {
                            attack.put("원거리 공격력", Integer.valueOf(ChatColor.stripColor(ia.replace("마법 공격력 : ", ""))));
                            attack.remove("원거리 공격력");
                            attack.remove("근접 공격력");
                        }
                        critical.put("치명타 확률", Integer.valueOf(ChatColor.stripColor(ia.replace("치명타 확률 : ", ""))));
                    }
                    if (Objects.requireNonNull(player.getEquipment().getHelmet()).getType() == Material.AIR || ! player.getEquipment().getHelmet().hasItemMeta() || ! Objects.requireNonNull(player.getEquipment().getHelmet().getItemMeta()).hasLore()){
                        defense.put("투구", 0);
                        health.put("투구", 0);
                        regen.put("투구", 0);
                        movespeed.put("투구", 0);
                    } else {

                        ArrayList<String> lorehelmet = ((ArrayList<String>) Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(player.getEquipment()).getHelmet()).getItemMeta()).getLore());

                        int hindexdefense = Objects.requireNonNull(lorehelmet).indexOf("방어력");
                        String hid = lorehelmet.get(hindexdefense);
                        int hindexhealth = Objects.requireNonNull(lorehelmet).indexOf("체력 증가");
                        String hih = lorehelmet.get(hindexhealth);
                        int hindexregen = Objects.requireNonNull(lorehelmet).indexOf("체력 재생");
                        String hir = lorehelmet.get(hindexregen);
                        int hindexmovespeed = Objects.requireNonNull(lorehelmet).indexOf("이동 속도");
                        String him = lorehelmet.get(hindexmovespeed);

                        defense.put("투구", Integer.valueOf(ChatColor.stripColor(hid.replace("방어력 : ", ""))));
                        health.put("투구", Integer.valueOf(ChatColor.stripColor(hih.replace("체력 증가 : ", ""))));
                        regen.put("투구", Integer.valueOf(ChatColor.stripColor(hir.replace("체력 재생 : ", ""))));
                        movespeed.put("투구", Integer.valueOf(ChatColor.stripColor(him.replace("이동 속도", ""))));
                    }
                    if (Objects.requireNonNull(player.getEquipment().getChestplate()).getType() == Material.AIR || ! player.getEquipment().getChestplate().hasItemMeta() || ! Objects.requireNonNull(player.getEquipment().getChestplate().getItemMeta()).hasLore()){
                        defense.put("흉갑", 0);
                        health.put("흉갑", 0);
                        regen.put("흉갑", 0);
                        movespeed.put("흉갑", 0);
                    } else {

                        ArrayList<String> lorechestplate = ((ArrayList<String>) Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(player.getEquipment()).getChestplate()).getItemMeta()).getLore());

                        int cindexdefense = Objects.requireNonNull(lorechestplate).indexOf("방어력");
                        String cid = lorechestplate.get(cindexdefense);
                        int cindexhealth = Objects.requireNonNull(lorechestplate).indexOf("체력 증가");
                        String cih = lorechestplate.get(cindexhealth);
                        int cindexregen = Objects.requireNonNull(lorechestplate).indexOf("체력 재생");
                        String cir = lorechestplate.get(cindexregen);
                        int cindexmovespeed = Objects.requireNonNull(lorechestplate).indexOf("이동 속도");
                        String cim = lorechestplate.get(cindexmovespeed);

                        defense.put("흉갑", Integer.valueOf(ChatColor.stripColor(cid.replace("방어력 : ", ""))));
                        health.put("흉갑", Integer.valueOf(ChatColor.stripColor(cih.replace("체력 증가 : ", ""))));
                        regen.put("흉갑", Integer.valueOf(ChatColor.stripColor(cir.replace("체력 재생 : ", ""))));
                        movespeed.put("흉갑", Integer.valueOf(ChatColor.stripColor(cim.replace("이동 속도", ""))));
                    }
                    if (Objects.requireNonNull(player.getEquipment().getLeggings()).getType() == Material.AIR || ! player.getEquipment().getLeggings().hasItemMeta() || ! Objects.requireNonNull(player.getEquipment().getLeggings().getItemMeta()).hasLore()){
                        defense.put("각반", 0);
                        health.put("각반", 0);
                        regen.put("각반", 0);
                        movespeed.put("각반", 0);
                    } else {

                        ArrayList<String> loreleggings = ((ArrayList<String>) Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(player.getEquipment()).getLeggings()).getItemMeta()).getLore());

                        int lindexdefense = Objects.requireNonNull(loreleggings).indexOf("방어력");
                        String lid = loreleggings.get(lindexdefense);
                        int lindexhealth = Objects.requireNonNull(loreleggings).indexOf("체력 증가");
                        String lih = loreleggings.get(lindexhealth);
                        int lindexregen = Objects.requireNonNull(loreleggings).indexOf("체력 재생");
                        String lir = loreleggings.get(lindexregen);
                        int lindexmovespeed = Objects.requireNonNull(loreleggings).indexOf("이동 속도");
                        String lim = loreleggings.get(lindexmovespeed);

                        defense.put("각반", Integer.valueOf(ChatColor.stripColor(lid.replace("방어력 : ", ""))));
                        health.put("각반", Integer.valueOf(ChatColor.stripColor(lih.replace("체력 증가 : ", ""))));
                        regen.put("각반", Integer.valueOf(ChatColor.stripColor(lir.replace("체력 재생 : ", ""))));
                        movespeed.put("각반", Integer.valueOf(ChatColor.stripColor(lim.replace("이동 속도", ""))));
                    }
                    if (Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR || ! player.getEquipment().getBoots().hasItemMeta() || ! Objects.requireNonNull(player.getEquipment().getBoots().getItemMeta()).hasLore()){
                        defense.put("신발", 0);
                        health.put("신발", 0);
                        regen.put("신발", 0);
                        movespeed.put("신발", 0);
                    } else {

                        ArrayList<String> loreboots = ((ArrayList<String>) Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(player.getEquipment()).getBoots()).getItemMeta()).getLore());

                        int bindexdefense = Objects.requireNonNull(loreboots).indexOf("방어력");
                        String bid = loreboots.get(bindexdefense);
                        int bindexhealth = Objects.requireNonNull(loreboots).indexOf("체력 증가");
                        String bih = loreboots.get(bindexhealth);
                        int bindexregen = Objects.requireNonNull(loreboots).indexOf("체력 재생");
                        String bir = loreboots.get(bindexregen);
                        int bindexmovespeed = Objects.requireNonNull(loreboots).indexOf("이동 속도");
                        String bim = loreboots.get(bindexmovespeed);

                        defense.put("신발", Integer.valueOf(ChatColor.stripColor(bid.replace("방어력 : ", ""))));
                        health.put("신발", Integer.valueOf(ChatColor.stripColor(bih.replace("체력 증가 : ", ""))));
                        regen.put("신발", Integer.valueOf(ChatColor.stripColor(bir.replace("체력 재생 : ", ""))));
                        movespeed.put("신발", Integer.valueOf(ChatColor.stripColor(bim.replace("이동 속도", ""))));
                    }

                    long[] stat;
                    stat = s.getStat(player.getUniqueId().toString());



                    stat[13] = attack.get("근접 공격력");
                    stat[14] = attack.get("원거리 공격력");
                    stat[15] = attack.get("마법 공격력");
                    stat[31] = attack.get("치명타 확률");

                    stat[18] = 100 + movespeed.get("투구") + movespeed.get("흉갑") + movespeed.get("각반") + movespeed.get("신발");                     // 이동 속도
                    stat[17] = stat[5] + (stat[6] * 5) + health.get("투구") + health.get("흉갑") + health.get("각반") + health.get("신발");             // 체력
                    stat[11] = defense.get("투구") + defense.get("흉갑") + defense.get("각반") + defense.get("신발");                                             // 방어력
                    double speed = stat[18]*0.001;
                    Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(stat[17]);
                    Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(speed);
                    s.setStat(player.getUniqueId().toString(), stat);
        /*/ if (Objects.requireNonNull(Objects.requireNonNull(player.getEquipment()).getItemInMainHand()).getType() == Material.AIR ||
                ! player.getEquipment().getItemInMainHand().hasItemMeta() || ! Objects.requireNonNull(player.getEquipment().getItemInMainHand().getItemMeta()).hasLore()){
            stat[13] = 0;
            stat[14] = 0;
            stat[15] = 0;
            stat[31] = 0;
            s.setStat(player.getUniqueId().toString(), stat);
            if (player.getEquipment().getHelmet() == null || Objects.requireNonNull(player.getEquipment().getHelmet()).getType() == Material.AIR ||
                    ! player.getEquipment().getHelmet().hasItemMeta()){
                stat[19] = 0;
                stat[23] = 0;
                stat[27] = 0;
                s.setStat(player.getUniqueId().toString(), stat);
                if (player.getEquipment().getChestplate() == null || Objects.requireNonNull(player.getEquipment().getChestplate()).getType() == Material.AIR ||
                        ! player.getEquipment().getChestplate().hasItemMeta()){
                    stat[20] = 0;
                    stat[24] = 0;
                    stat[28] = 0;
                    s.setStat(player.getUniqueId().toString(), stat);
                    if (player.getEquipment().getLeggings() == null || Objects.requireNonNull(player.getEquipment().getLeggings()).getType() == Material.AIR ||
                            ! player.getEquipment().getLeggings().hasItemMeta()){
                        stat[21] = 0;
                        stat[25] = 0;
                        stat[29] = 0;
                        s.setStat(player.getUniqueId().toString(), stat);
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList<String> lorelistb = (ArrayList<String>) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = lorelistb.get(3);
                                String s2 = lorelistb.get(4);
                                String s3 = lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    } else {
                        ItemStack l = Objects.requireNonNull(player.getEquipment()).getLeggings();
                        ArrayList<String> lorelistl = (ArrayList<String>) Objects.requireNonNull(l.getItemMeta()).getLore();
                        for (Object string : Objects.requireNonNull(lorelistl)){
                            String s1 = lorelistl.get(3);
                            String s2 = lorelistl.get(4);
                            String s3 = lorelistl.get(5);
                            stat[25] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                            stat[21] = Long.parseLong(s2.replace("방어력 : ", ""));
                            stat[29] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                            s.setStat(player.getUniqueId().toString(), stat);
                        }
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList<String> lorelistb = (ArrayList<String>) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = lorelistb.get(3);
                                String s2 = lorelistb.get(4);
                                String s3 = lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    }
                } else {
                    ItemStack c = Objects.requireNonNull(player.getEquipment()).getChestplate();
                    ArrayList<String> lorelistc = (ArrayList<String>) Objects.requireNonNull(c.getItemMeta()).getLore();
                    for (Object string : Objects.requireNonNull(lorelistc)){
                        String s1 = lorelistc.get(3);
                        String s2 = lorelistc.get(4);
                        String s3 = lorelistc.get(5);
                        stat[24] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                        stat[20] = Long.parseLong(s2.replace("방어력 : ", ""));
                        stat[28] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                        s.setStat(player.getUniqueId().toString(), stat);
                    }
                    if (player.getEquipment().getLeggings() == null || Objects.requireNonNull(player.getEquipment().getLeggings()).getType() == Material.AIR ||
                            ! player.getEquipment().getLeggings().hasItemMeta()){
                        stat[21] = 0;
                        stat[25] = 0;
                        stat[29] = 0;
                        s.setStat(player.getUniqueId().toString(), stat);
                    } else {
                        ItemStack l = Objects.requireNonNull(player.getEquipment()).getLeggings();
                        ArrayList<String> lorelistl = (ArrayList<String>) Objects.requireNonNull(l.getItemMeta()).getLore();
                        for (Object string : Objects.requireNonNull(lorelistl)){
                            String s1 = lorelistl.get(3);
                            String s2 = lorelistl.get(4);
                            String s3 = lorelistl.get(5);
                            stat[25] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                            stat[21] = Long.parseLong(s2.replace("방어력 : ", ""));
                            stat[29] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                            s.setStat(player.getUniqueId().toString(), stat);
                        }
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList<String> lorelistb = (ArrayList<String>) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = lorelistb.get(3);
                                String s2 = lorelistb.get(4);
                                String s3 = lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    }
                }
            } else {
                ItemStack h = Objects.requireNonNull(player.getEquipment()).getHelmet();
                ArrayList<String> lorelisth = (ArrayList<String>) Objects.requireNonNull(h.getItemMeta()).getLore();
                for (Object string : Objects.requireNonNull(lorelisth)){
                    String s1 = lorelisth.get(3);
                    String s2 = lorelisth.get(4);
                    String s3 = lorelisth.get(5);
                    stat[23] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                    stat[19] = Long.parseLong(s2.replace("방어력 : ", ""));
                    stat[27] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                    s.setStat(player.getUniqueId().toString(), stat);
                }
                if (player.getEquipment().getChestplate() == null || Objects.requireNonNull(player.getEquipment().getChestplate()).getType() == Material.AIR ||
                        ! player.getEquipment().getChestplate().hasItemMeta()){
                    stat[20] = 0;
                    stat[24] = 0;
                    stat[28] = 0;
                    s.setStat(player.getUniqueId().toString(), stat);
                    if (player.getEquipment().getLeggings() == null || Objects.requireNonNull(player.getEquipment().getLeggings()).getType() == Material.AIR ||
                            ! player.getEquipment().getLeggings().hasItemMeta()){
                        stat[21] = 0;
                        stat[25] = 0;
                        stat[29] = 0;
                        s.setStat(player.getUniqueId().toString(), stat);
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList<String> lorelistb = (ArrayList<String>) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = lorelistb.get(3);
                                String s2 = lorelistb.get(4);
                                String s3 = lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    } else {
                        ItemStack l = Objects.requireNonNull(player.getEquipment()).getLeggings();
                        ArrayList<String> lorelistl = (ArrayList<String>) Objects.requireNonNull(l.getItemMeta()).getLore();
                        for (Object string : Objects.requireNonNull(lorelistl)){
                            String s1 = lorelistl.get(3);
                            String s2 = lorelistl.get(4);
                            String s3 = lorelistl.get(5);
                            stat[25] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                            stat[21] = Long.parseLong(s2.replace("방어력 : ", ""));
                            stat[29] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                            s.setStat(player.getUniqueId().toString(), stat);
                        }
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList<String> lorelistb = (ArrayList<String>) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = lorelistb.get(3);
                                String s2 = lorelistb.get(4);
                                String s3 = lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    }
                } else {
                    ItemStack c = Objects.requireNonNull(player.getEquipment()).getChestplate();
                    ArrayList<String> lorelistc = (ArrayList<String>) Objects.requireNonNull(c.getItemMeta()).getLore();
                    for (Object string : Objects.requireNonNull(lorelistc)){
                        String s1 = lorelistc.get(3);
                        String s2 = lorelistc.get(4);
                        String s3 = lorelistc.get(5);
                        stat[24] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                        stat[20] = Long.parseLong(s2.replace("방어력 : ", ""));
                        stat[28] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                        s.setStat(player.getUniqueId().toString(), stat);
                    }
                    if (player.getEquipment().getLeggings() == null || Objects.requireNonNull(player.getEquipment().getLeggings()).getType() == Material.AIR ||
                            ! player.getEquipment().getLeggings().hasItemMeta()){
                        stat[21] = 0;
                        stat[25] = 0;
                        stat[29] = 0;
                        s.setStat(player.getUniqueId().toString(), stat);
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList<String> lorelistb = (ArrayList<String>) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = lorelistb.get(3);
                                String s2 = lorelistb.get(4);
                                String s3 = lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    } else {
                        ItemStack l = Objects.requireNonNull(player.getEquipment()).getLeggings();
                        ArrayList<String> lorelistl = (ArrayList<String>) Objects.requireNonNull(l.getItemMeta()).getLore();
                        for (Object string : Objects.requireNonNull(lorelistl)){
                            String s1 = lorelistl.get(3);
                            String s2 = lorelistl.get(4);
                            String s3 = lorelistl.get(5);
                            stat[25] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                            stat[21] = Long.parseLong(s2.replace("방어력 : ", ""));
                            stat[29] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                            s.setStat(player.getUniqueId().toString(), stat);
                        }
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList<String> lorelistb = (ArrayList<String>) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = lorelistb.get(3);
                                String s2 = lorelistb.get(4);
                                String s3 = lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    }
                }
            }
        } else {
            ItemStack w = Objects.requireNonNull(player.getEquipment()).getItemInMainHand();
            ArrayList<String> lorelistw = (ArrayList<String>) Objects.requireNonNull(w.getItemMeta()).getLore();
            for (Object string : Objects.requireNonNull(lorelistw)){
                player.sendMessage(String.valueOf(lorelistw));
                String s1 = lorelistw.get(3);
                String s2 = lorelistw.get(4);
                if (s1.contains("근접")) {
                    stat[13] = Long.parseLong(s1.replace("근접 공격력 : ", ""));
                    if (s2.contains("치명타")){
                        stat[31] = Long.parseLong(s2.replace("치명타 확률 : ", ""));
                    }
                }
                else if (s1.contains("원거리")) {
                    stat[14] = Long.parseLong(s1.replace("원거리 공격력 : ", ""));
                    if (s2.contains("치명타")){
                        stat[31] = Long.parseLong(s2.replace("치명타 확률 : ", ""));
                    }
                }
                else if (s1.contains("마법")) {
                    stat[15] = Long.parseLong(s1.replace("마법 공격력 : ", ""));
                    if (s2.contains("치명타")){
                        stat[31] = Long.parseLong(s2.replace("치명타 확률 : ", ""));
                    }
                }
                s.setStat(player.getUniqueId().toString(), stat);
            }
            if (player.getEquipment().getHelmet() == null || Objects.requireNonNull(player.getEquipment().getHelmet()).getType() == Material.AIR ||
                    ! player.getEquipment().getHelmet().hasItemMeta()){
                stat[19] = 0;
                stat[23] = 0;
                stat[27] = 0;
                s.setStat(player.getUniqueId().toString(), stat);
                if (player.getEquipment().getChestplate() == null || Objects.requireNonNull(player.getEquipment().getChestplate()).getType() == Material.AIR ||
                        ! player.getEquipment().getChestplate().hasItemMeta()){
                    stat[20] = 0;
                    stat[24] = 0;
                    stat[28] = 0;
                    s.setStat(player.getUniqueId().toString(), stat);
                    if (player.getEquipment().getLeggings() == null || Objects.requireNonNull(player.getEquipment().getLeggings()).getType() == Material.AIR ||
                            ! player.getEquipment().getLeggings().hasItemMeta()){
                        stat[21] = 0;
                        stat[25] = 0;
                        stat[29] = 0;
                        s.setStat(player.getUniqueId().toString(), stat);
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList<String> lorelistb = (ArrayList<String>) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = lorelistb.get(3);
                                String s2 = lorelistb.get(4);
                                String s3 = lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    } else {
                        ItemStack l = Objects.requireNonNull(player.getEquipment()).getLeggings();
                        ArrayList<String> lorelistl = (ArrayList<String>) Objects.requireNonNull(l.getItemMeta()).getLore();
                        for (Object string : Objects.requireNonNull(lorelistl)){
                            String s1 = lorelistl.get(3);
                            String s2 = lorelistl.get(4);
                            String s3 = lorelistl.get(5);
                            stat[25] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                            stat[21] = Long.parseLong(s2.replace("방어력 : ", ""));
                            stat[29] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                            s.setStat(player.getUniqueId().toString(), stat);
                        }
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList<String> lorelistb = (ArrayList<String>) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = lorelistb.get(3);
                                String s2 = lorelistb.get(4);
                                String s3 = lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    }
                } else {
                    ItemStack c = Objects.requireNonNull(player.getEquipment()).getChestplate();
                    ArrayList<String> lorelistc = (ArrayList<String>) Objects.requireNonNull(c.getItemMeta()).getLore();
                    for (Object string : Objects.requireNonNull(lorelistc)){
                        String s1 = lorelistc.get(3);
                        String s2 = lorelistc.get(4);
                        String s3 = lorelistc.get(5);
                        stat[24] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                        stat[20] = Long.parseLong(s2.replace("방어력 : ", ""));
                        stat[28] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                        s.setStat(player.getUniqueId().toString(), stat);
                    }
                    if (player.getEquipment().getLeggings() == null || Objects.requireNonNull(player.getEquipment().getLeggings()).getType() == Material.AIR ||
                            ! player.getEquipment().getLeggings().hasItemMeta()){
                        stat[21] = 0;
                        stat[25] = 0;
                        stat[29] = 0;
                        s.setStat(player.getUniqueId().toString(), stat);
                    } else {
                        ItemStack l = Objects.requireNonNull(player.getEquipment()).getLeggings();
                        ArrayList<String> lorelistl = (ArrayList<String>) Objects.requireNonNull(l.getItemMeta()).getLore();
                        for (Object string : Objects.requireNonNull(lorelistl)){
                            String s1 = lorelistl.get(3);
                            String s2 = lorelistl.get(4);
                            String s3 = lorelistl.get(5);
                            stat[25] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                            stat[21] = Long.parseLong(s2.replace("방어력 : ", ""));
                            stat[29] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                            s.setStat(player.getUniqueId().toString(), stat);
                        }
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList<String> lorelistb = (ArrayList<String>) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = lorelistb.get(3);
                                String s2 = lorelistb.get(4);
                                String s3 = lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    }
                }
            } else {
                ItemStack h = Objects.requireNonNull(player.getEquipment()).getHelmet();
                ArrayList<String> lorelisth = (ArrayList<String>) Objects.requireNonNull(h.getItemMeta()).getLore();
                for (Object string : Objects.requireNonNull(lorelisth)){
                    String s1 = lorelisth.get(3);
                    String s2 = lorelisth.get(4);
                    String s3 = lorelisth.get(5);
                    stat[23] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                    stat[19] = Long.parseLong(s2.replace("방어력 : ", ""));
                    stat[27] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                    s.setStat(player.getUniqueId().toString(), stat);
                }
                if (player.getEquipment().getChestplate() == null || Objects.requireNonNull(player.getEquipment().getChestplate()).getType() == Material.AIR ||
                        ! player.getEquipment().getChestplate().hasItemMeta()){
                    stat[20] = 0;
                    stat[24] = 0;
                    stat[28] = 0;
                    s.setStat(player.getUniqueId().toString(), stat);
                    if (player.getEquipment().getLeggings() == null || Objects.requireNonNull(player.getEquipment().getLeggings()).getType() == Material.AIR ||
                            ! player.getEquipment().getLeggings().hasItemMeta()){
                        stat[21] = 0;
                        stat[25] = 0;
                        stat[29] = 0;
                        s.setStat(player.getUniqueId().toString(), stat);
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList<String> lorelistb = (ArrayList<String>) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = lorelistb.get(3);
                                String s2 = lorelistb.get(4);
                                String s3 = lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    } else {
                        ItemStack l = Objects.requireNonNull(player.getEquipment()).getLeggings();
                        ArrayList<String> lorelistl = (ArrayList<String>) Objects.requireNonNull(l.getItemMeta()).getLore();
                        for (Object string : Objects.requireNonNull(lorelistl)){
                            String s1 = lorelistl.get(3);
                            String s2 = lorelistl.get(4);
                            String s3 = lorelistl.get(5);
                            stat[25] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                            stat[21] = Long.parseLong(s2.replace("방어력 : ", ""));
                            stat[29] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                            s.setStat(player.getUniqueId().toString(), stat);
                        }
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList<String> lorelistb = (ArrayList<String>) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = lorelistb.get(3);
                                String s2 = lorelistb.get(4);
                                String s3 = lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    }
                } else {
                    ItemStack c = Objects.requireNonNull(player.getEquipment()).getChestplate();
                    ArrayList<String> lorelistc = (ArrayList<String>) Objects.requireNonNull(c.getItemMeta()).getLore();
                    for (Object string : Objects.requireNonNull(lorelistc)){
                        String s1 = lorelistc.get(3);
                        String s2 = lorelistc.get(4);
                        String s3 = lorelistc.get(5);
                        stat[24] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                        stat[20] = Long.parseLong(s2.replace("방어력 : ", ""));
                        stat[28] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                        s.setStat(player.getUniqueId().toString(), stat);
                    }
                    if (player.getEquipment().getLeggings() == null || Objects.requireNonNull(player.getEquipment().getLeggings()).getType() == Material.AIR ||
                            ! player.getEquipment().getLeggings().hasItemMeta()){
                        stat[21] = 0;
                        stat[25] = 0;
                        stat[29] = 0;
                        s.setStat(player.getUniqueId().toString(), stat);
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList<String> lorelistb = (ArrayList<String>) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = lorelistb.get(3);
                                String s2 = lorelistb.get(4);
                                String s3 = lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    } else {
                        ItemStack l = Objects.requireNonNull(player.getEquipment()).getLeggings();
                        ArrayList<String> lorelistl = (ArrayList<String>) Objects.requireNonNull(l.getItemMeta()).getLore();
                        for (Object string : Objects.requireNonNull(lorelistl)){
                            String s1 = lorelistl.get(3);
                            String s2 = lorelistl.get(4);
                            String s3 = lorelistl.get(5);
                            stat[25] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                            stat[21] = Long.parseLong(s2.replace("방어력 : ", ""));
                            stat[29] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                            s.setStat(player.getUniqueId().toString(), stat);
                        }
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList<String> lorelistb = (ArrayList<String>) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = lorelistb.get(3);
                                String s2 = lorelistb.get(4);
                                String s3 = lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    }
                }
            }
        }
                    stat[18] = 100 + stat[9] + stat[27] + stat[28] + stat[29] + stat[30];
                    stat[17] = stat[5] + (stat[6] * 5) + stat[23] + stat[24] + stat[25] + stat [26];
                    stat[11] = stat[19]+stat[20]+stat[21]+stat[22];
                    double speed = stat[18]*0.001;
                    Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(stat[17]);
                    Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(speed);
                    s.setStat(player.getUniqueId().toString(), stat);   /*/
                }
            }
        },0L , 10L);
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()){
                    long[] stat;
                    stat = s.getStat(player.getUniqueId().toString());
                    double regeneration = regen.get("투구") + regen.get("흉갑") + regen.get("각반") + regen.get("신발") + stat[6]*2;
                    player.setHealth(player.getHealth() + regeneration);
                }
            }
        },0L, 100L);
    }

    @Override
    public void onDisable() {
        getLogger().info("Doxa Off!");
        // Plugin shutdown logic
    }

    @EventHandler
    public void loadPS(PlayerMoveEvent p) {
        Player player = p.getPlayer();
        long[] stat;
        stat = s.getStat(player.getUniqueId().toString());







        /*/ if (Objects.requireNonNull(Objects.requireNonNull(player.getEquipment()).getItemInMainHand()).getType() == Material.AIR ||
                ! player.getEquipment().getItemInMainHand().hasItemMeta() || ! Objects.requireNonNull(player.getEquipment().getItemInMainHand().getItemMeta()).hasLore()){
            stat[13] = 0;
            stat[14] = 0;
            stat[15] = 0;
            stat[31] = 0;
            s.setStat(player.getUniqueId().toString(), stat);
            if (player.getEquipment().getHelmet() == null || Objects.requireNonNull(player.getEquipment().getHelmet()).getType() == Material.AIR ||
                    ! player.getEquipment().getHelmet().hasItemMeta()){
                stat[19] = 0;
                stat[23] = 0;
                stat[27] = 0;
                s.setStat(player.getUniqueId().toString(), stat);
                if (player.getEquipment().getChestplate() == null || Objects.requireNonNull(player.getEquipment().getChestplate()).getType() == Material.AIR ||
                        ! player.getEquipment().getChestplate().hasItemMeta()){
                    stat[20] = 0;
                    stat[24] = 0;
                    stat[28] = 0;
                    s.setStat(player.getUniqueId().toString(), stat);
                    if (player.getEquipment().getLeggings() == null || Objects.requireNonNull(player.getEquipment().getLeggings()).getType() == Material.AIR ||
                            ! player.getEquipment().getLeggings().hasItemMeta()){
                        stat[21] = 0;
                        stat[25] = 0;
                        stat[29] = 0;
                        s.setStat(player.getUniqueId().toString(), stat);
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList<String> lorelistb = (ArrayList<String>) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = lorelistb.get(3);
                                String s2 = lorelistb.get(4);
                                String s3 = lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    } else {
                        ItemStack l = Objects.requireNonNull(player.getEquipment()).getLeggings();
                        ArrayList<String> lorelistl = (ArrayList<String>) Objects.requireNonNull(l.getItemMeta()).getLore();
                        for (Object string : Objects.requireNonNull(lorelistl)){
                            String s1 = lorelistl.get(3);
                            String s2 = lorelistl.get(4);
                            String s3 = lorelistl.get(5);
                            stat[25] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                            stat[21] = Long.parseLong(s2.replace("방어력 : ", ""));
                            stat[29] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                            s.setStat(player.getUniqueId().toString(), stat);
                        }
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList<String> lorelistb = (ArrayList<String>) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = lorelistb.get(3);
                                String s2 = lorelistb.get(4);
                                String s3 = lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    }
                } else {
                    ItemStack c = Objects.requireNonNull(player.getEquipment()).getChestplate();
                    ArrayList<String> lorelistc = (ArrayList<String>) Objects.requireNonNull(c.getItemMeta()).getLore();
                    for (Object string : Objects.requireNonNull(lorelistc)){
                        String s1 = lorelistc.get(3);
                        String s2 = lorelistc.get(4);
                        String s3 = lorelistc.get(5);
                        stat[24] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                        stat[20] = Long.parseLong(s2.replace("방어력 : ", ""));
                        stat[28] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                        s.setStat(player.getUniqueId().toString(), stat);
                    }
                    if (player.getEquipment().getLeggings() == null || Objects.requireNonNull(player.getEquipment().getLeggings()).getType() == Material.AIR ||
                            ! player.getEquipment().getLeggings().hasItemMeta()){
                        stat[21] = 0;
                        stat[25] = 0;
                        stat[29] = 0;
                        s.setStat(player.getUniqueId().toString(), stat);
                    } else {
                        ItemStack l = Objects.requireNonNull(player.getEquipment()).getLeggings();
                        ArrayList<String> lorelistl = (ArrayList<String>) Objects.requireNonNull(l.getItemMeta()).getLore();
                        for (Object string : Objects.requireNonNull(lorelistl)){
                            String s1 = lorelistl.get(3);
                            String s2 = lorelistl.get(4);
                            String s3 = lorelistl.get(5);
                            stat[25] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                            stat[21] = Long.parseLong(s2.replace("방어력 : ", ""));
                            stat[29] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                            s.setStat(player.getUniqueId().toString(), stat);
                        }
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList<String> lorelistb = (ArrayList<String>) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = lorelistb.get(3);
                                String s2 = lorelistb.get(4);
                                String s3 = lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    }
                }
            } else {
                ItemStack h = Objects.requireNonNull(player.getEquipment()).getHelmet();
                ArrayList<String> lorelisth = (ArrayList<String>) Objects.requireNonNull(h.getItemMeta()).getLore();
                for (Object string : Objects.requireNonNull(lorelisth)){
                    String s1 = lorelisth.get(3);
                    String s2 = lorelisth.get(4);
                    String s3 = lorelisth.get(5);
                    stat[23] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                    stat[19] = Long.parseLong(s2.replace("방어력 : ", ""));
                    stat[27] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                    s.setStat(player.getUniqueId().toString(), stat);
                }
                if (player.getEquipment().getChestplate() == null || Objects.requireNonNull(player.getEquipment().getChestplate()).getType() == Material.AIR ||
                        ! player.getEquipment().getChestplate().hasItemMeta()){
                    stat[20] = 0;
                    stat[24] = 0;
                    stat[28] = 0;
                    s.setStat(player.getUniqueId().toString(), stat);
                    if (player.getEquipment().getLeggings() == null || Objects.requireNonNull(player.getEquipment().getLeggings()).getType() == Material.AIR ||
                            ! player.getEquipment().getLeggings().hasItemMeta()){
                        stat[21] = 0;
                        stat[25] = 0;
                        stat[29] = 0;
                        s.setStat(player.getUniqueId().toString(), stat);
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList<String> lorelistb = (ArrayList<String>) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = lorelistb.get(3);
                                String s2 = lorelistb.get(4);
                                String s3 = lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    } else {
                        ItemStack l = Objects.requireNonNull(player.getEquipment()).getLeggings();
                        ArrayList<String> lorelistl = (ArrayList<String>) Objects.requireNonNull(l.getItemMeta()).getLore();
                        for (Object string : Objects.requireNonNull(lorelistl)){
                            String s1 = lorelistl.get(3);
                            String s2 = lorelistl.get(4);
                            String s3 = lorelistl.get(5);
                            stat[25] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                            stat[21] = Long.parseLong(s2.replace("방어력 : ", ""));
                            stat[29] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                            s.setStat(player.getUniqueId().toString(), stat);
                        }
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList<String> lorelistb = (ArrayList<String>) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = lorelistb.get(3);
                                String s2 = lorelistb.get(4);
                                String s3 = lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    }
                } else {
                    ItemStack c = Objects.requireNonNull(player.getEquipment()).getChestplate();
                    ArrayList<String> lorelistc = (ArrayList<String>) Objects.requireNonNull(c.getItemMeta()).getLore();
                    for (Object string : Objects.requireNonNull(lorelistc)){
                        String s1 = lorelistc.get(3);
                        String s2 = lorelistc.get(4);
                        String s3 = lorelistc.get(5);
                        stat[24] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                        stat[20] = Long.parseLong(s2.replace("방어력 : ", ""));
                        stat[28] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                        s.setStat(player.getUniqueId().toString(), stat);
                    }
                    if (player.getEquipment().getLeggings() == null || Objects.requireNonNull(player.getEquipment().getLeggings()).getType() == Material.AIR ||
                            ! player.getEquipment().getLeggings().hasItemMeta()){
                        stat[21] = 0;
                        stat[25] = 0;
                        stat[29] = 0;
                        s.setStat(player.getUniqueId().toString(), stat);
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList<String> lorelistb = (ArrayList<String>) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = lorelistb.get(3);
                                String s2 = lorelistb.get(4);
                                String s3 = lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    } else {
                        ItemStack l = Objects.requireNonNull(player.getEquipment()).getLeggings();
                        ArrayList<String> lorelistl = (ArrayList<String>) Objects.requireNonNull(l.getItemMeta()).getLore();
                        for (Object string : Objects.requireNonNull(lorelistl)){
                            String s1 = lorelistl.get(3);
                            String s2 = lorelistl.get(4);
                            String s3 = lorelistl.get(5);
                            stat[25] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                            stat[21] = Long.parseLong(s2.replace("방어력 : ", ""));
                            stat[29] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                            s.setStat(player.getUniqueId().toString(), stat);
                        }
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList<String> lorelistb = (ArrayList<String>) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = lorelistb.get(3);
                                String s2 = lorelistb.get(4);
                                String s3 = lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    }
                }
            }
        } else {
            ItemStack w = Objects.requireNonNull(player.getEquipment()).getItemInMainHand();
            ArrayList<String> lorelistw = (ArrayList<String>) Objects.requireNonNull(w.getItemMeta()).getLore();
            for (Object string : Objects.requireNonNull(lorelistw)){
                player.sendMessage(String.valueOf(lorelistw));
                String s1 = lorelistw.get(3);
                String s2 = lorelistw.get(4);
                if (s1.contains("근접")) {
                    stat[13] = Long.parseLong(s1.replace("근접 공격력 : ", ""));
                    if (s2.contains("치명타")){
                        stat[31] = Long.parseLong(s2.replace("치명타 확률 : ", ""));
                    }
                }
                else if (s1.contains("원거리")) {
                    stat[14] = Long.parseLong(s1.replace("원거리 공격력 : ", ""));
                    if (s2.contains("치명타")){
                        stat[31] = Long.parseLong(s2.replace("치명타 확률 : ", ""));
                    }
                }
                else if (s1.contains("마법")) {
                    stat[15] = Long.parseLong(s1.replace("마법 공격력 : ", ""));
                    if (s2.contains("치명타")){
                        stat[31] = Long.parseLong(s2.replace("치명타 확률 : ", ""));
                    }
                }
                s.setStat(player.getUniqueId().toString(), stat);
            }
            if (player.getEquipment().getHelmet() == null || Objects.requireNonNull(player.getEquipment().getHelmet()).getType() == Material.AIR ||
                    ! player.getEquipment().getHelmet().hasItemMeta()){
                stat[19] = 0;
                stat[23] = 0;
                stat[27] = 0;
                s.setStat(player.getUniqueId().toString(), stat);
                if (player.getEquipment().getChestplate() == null || Objects.requireNonNull(player.getEquipment().getChestplate()).getType() == Material.AIR ||
                        ! player.getEquipment().getChestplate().hasItemMeta()){
                    stat[20] = 0;
                    stat[24] = 0;
                    stat[28] = 0;
                    s.setStat(player.getUniqueId().toString(), stat);
                    if (player.getEquipment().getLeggings() == null || Objects.requireNonNull(player.getEquipment().getLeggings()).getType() == Material.AIR ||
                            ! player.getEquipment().getLeggings().hasItemMeta()){
                        stat[21] = 0;
                        stat[25] = 0;
                        stat[29] = 0;
                        s.setStat(player.getUniqueId().toString(), stat);
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList<String> lorelistb = (ArrayList<String>) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = lorelistb.get(3);
                                String s2 = lorelistb.get(4);
                                String s3 = lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    } else {
                        ItemStack l = Objects.requireNonNull(player.getEquipment()).getLeggings();
                        ArrayList<String> lorelistl = (ArrayList<String>) Objects.requireNonNull(l.getItemMeta()).getLore();
                        for (Object string : Objects.requireNonNull(lorelistl)){
                            String s1 = lorelistl.get(3);
                            String s2 = lorelistl.get(4);
                            String s3 = lorelistl.get(5);
                            stat[25] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                            stat[21] = Long.parseLong(s2.replace("방어력 : ", ""));
                            stat[29] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                            s.setStat(player.getUniqueId().toString(), stat);
                        }
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList<String> lorelistb = (ArrayList<String>) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = lorelistb.get(3);
                                String s2 = lorelistb.get(4);
                                String s3 = lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    }
                } else {
                    ItemStack c = Objects.requireNonNull(player.getEquipment()).getChestplate();
                    ArrayList<String> lorelistc = (ArrayList<String>) Objects.requireNonNull(c.getItemMeta()).getLore();
                    for (Object string : Objects.requireNonNull(lorelistc)){
                        String s1 = lorelistc.get(3);
                        String s2 = lorelistc.get(4);
                        String s3 = lorelistc.get(5);
                        stat[24] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                        stat[20] = Long.parseLong(s2.replace("방어력 : ", ""));
                        stat[28] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                        s.setStat(player.getUniqueId().toString(), stat);
                    }
                    if (player.getEquipment().getLeggings() == null || Objects.requireNonNull(player.getEquipment().getLeggings()).getType() == Material.AIR ||
                            ! player.getEquipment().getLeggings().hasItemMeta()){
                        stat[21] = 0;
                        stat[25] = 0;
                        stat[29] = 0;
                        s.setStat(player.getUniqueId().toString(), stat);
                    } else {
                        ItemStack l = Objects.requireNonNull(player.getEquipment()).getLeggings();
                        ArrayList<String> lorelistl = (ArrayList<String>) Objects.requireNonNull(l.getItemMeta()).getLore();
                        for (Object string : Objects.requireNonNull(lorelistl)){
                            String s1 = lorelistl.get(3);
                            String s2 = lorelistl.get(4);
                            String s3 = lorelistl.get(5);
                            stat[25] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                            stat[21] = Long.parseLong(s2.replace("방어력 : ", ""));
                            stat[29] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                            s.setStat(player.getUniqueId().toString(), stat);
                        }
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList<String> lorelistb = (ArrayList<String>) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = lorelistb.get(3);
                                String s2 = lorelistb.get(4);
                                String s3 = lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    }
                }
            } else {
                ItemStack h = Objects.requireNonNull(player.getEquipment()).getHelmet();
                ArrayList<String> lorelisth = (ArrayList<String>) Objects.requireNonNull(h.getItemMeta()).getLore();
                for (Object string : Objects.requireNonNull(lorelisth)){
                    String s1 = lorelisth.get(3);
                    String s2 = lorelisth.get(4);
                    String s3 = lorelisth.get(5);
                    stat[23] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                    stat[19] = Long.parseLong(s2.replace("방어력 : ", ""));
                    stat[27] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                    s.setStat(player.getUniqueId().toString(), stat);
                }
                if (player.getEquipment().getChestplate() == null || Objects.requireNonNull(player.getEquipment().getChestplate()).getType() == Material.AIR ||
                        ! player.getEquipment().getChestplate().hasItemMeta()){
                    stat[20] = 0;
                    stat[24] = 0;
                    stat[28] = 0;
                    s.setStat(player.getUniqueId().toString(), stat);
                    if (player.getEquipment().getLeggings() == null || Objects.requireNonNull(player.getEquipment().getLeggings()).getType() == Material.AIR ||
                            ! player.getEquipment().getLeggings().hasItemMeta()){
                        stat[21] = 0;
                        stat[25] = 0;
                        stat[29] = 0;
                        s.setStat(player.getUniqueId().toString(), stat);
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList<String> lorelistb = (ArrayList<String>) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = lorelistb.get(3);
                                String s2 = lorelistb.get(4);
                                String s3 = lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    } else {
                        ItemStack l = Objects.requireNonNull(player.getEquipment()).getLeggings();
                        ArrayList<String> lorelistl = (ArrayList<String>) Objects.requireNonNull(l.getItemMeta()).getLore();
                        for (Object string : Objects.requireNonNull(lorelistl)){
                            String s1 = lorelistl.get(3);
                            String s2 = lorelistl.get(4);
                            String s3 = lorelistl.get(5);
                            stat[25] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                            stat[21] = Long.parseLong(s2.replace("방어력 : ", ""));
                            stat[29] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                            s.setStat(player.getUniqueId().toString(), stat);
                        }
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList<String> lorelistb = (ArrayList<String>) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = lorelistb.get(3);
                                String s2 = lorelistb.get(4);
                                String s3 = lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    }
                } else {
                    ItemStack c = Objects.requireNonNull(player.getEquipment()).getChestplate();
                    ArrayList<String> lorelistc = (ArrayList<String>) Objects.requireNonNull(c.getItemMeta()).getLore();
                    for (Object string : Objects.requireNonNull(lorelistc)){
                        String s1 = lorelistc.get(3);
                        String s2 = lorelistc.get(4);
                        String s3 = lorelistc.get(5);
                        stat[24] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                        stat[20] = Long.parseLong(s2.replace("방어력 : ", ""));
                        stat[28] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                        s.setStat(player.getUniqueId().toString(), stat);
                    }
                    if (player.getEquipment().getLeggings() == null || Objects.requireNonNull(player.getEquipment().getLeggings()).getType() == Material.AIR ||
                            ! player.getEquipment().getLeggings().hasItemMeta()){
                        stat[21] = 0;
                        stat[25] = 0;
                        stat[29] = 0;
                        s.setStat(player.getUniqueId().toString(), stat);
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList<String> lorelistb = (ArrayList<String>) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = lorelistb.get(3);
                                String s2 = lorelistb.get(4);
                                String s3 = lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    } else {
                        ItemStack l = Objects.requireNonNull(player.getEquipment()).getLeggings();
                        ArrayList<String> lorelistl = (ArrayList<String>) Objects.requireNonNull(l.getItemMeta()).getLore();
                        for (Object string : Objects.requireNonNull(lorelistl)){
                            String s1 = lorelistl.get(3);
                            String s2 = lorelistl.get(4);
                            String s3 = lorelistl.get(5);
                            stat[25] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                            stat[21] = Long.parseLong(s2.replace("방어력 : ", ""));
                            stat[29] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                            s.setStat(player.getUniqueId().toString(), stat);
                        }
                        if (player.getEquipment().getBoots() == null || Objects.requireNonNull(player.getEquipment().getBoots()).getType() == Material.AIR ||
                                ! player.getEquipment().getBoots().hasItemMeta()){
                            stat[22] = 0;
                            stat[26] = 0;
                            stat[30] = 0;
                            s.setStat(player.getUniqueId().toString(), stat);
                        } else {
                            ItemStack b = Objects.requireNonNull(player.getEquipment()).getBoots();
                            ArrayList<String> lorelistb = (ArrayList<String>) Objects.requireNonNull(b.getItemMeta()).getLore();
                            for (Object string : Objects.requireNonNull(lorelistb)){
                                String s1 = lorelistb.get(3);
                                String s2 = lorelistb.get(4);
                                String s3 = lorelistb.get(5);
                                stat[26] = Long.parseLong(s1.replace("체력 증가 : ", ""));
                                stat[22] = Long.parseLong(s2.replace("방어력 : ", ""));
                                stat[30] = Long.parseLong(s3.replace("이동 속도 : ", ""));
                                s.setStat(player.getUniqueId().toString(), stat);
                            }
                        }
                    }
                }
            }
        }
        stat[18] = 100 + stat[9] + stat[27] + stat[28] + stat[29] + stat[30];
        stat[17] = stat[5] + (stat[6] * 5) + stat[23] + stat[24] + stat[25] + stat [26];
        stat[11] = stat[19]+stat[20]+stat[21]+stat[22];
        double speed = stat[18]*0.001;
        Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(stat[17]);
        Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(speed);
        s.setStat(player.getUniqueId().toString(), stat); /*/

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
                    long[] stat;
                    stat = s.getStat(p.getUniqueId().toString());
                    try {
                        money = Integer.parseInt(args[0]);
                    } catch (Exception e) {
                        p.sendMessage("Error");
                    }
                    if (money < 0) {
                        talker.sendMessage(ChatColor.DARK_AQUA + "[ Ercanel ]" + ChatColor.WHITE + "음수는 제공되지 않습니다 고객님 ^^7");
                    } else {
                        if (stat[4] > money) {
                            ItemStack paper = new ItemStack(Material.PAPER);
                            ItemMeta im = paper.getItemMeta();
                            assert im != null;
                            im.setDisplayName(ChatColor.DARK_AQUA + "[ Ercanel ] " + ChatColor.WHITE + args[0] + " 골드");
                            paper.setItemMeta(im);
                            p.getInventory().addItem(paper);
                            stat[4] = stat[4] - money;
                            s.setStat(p.getUniqueId().toString(), stat);
                        }
                        else {
                            talker.sendMessage(ChatColor.DARK_AQUA + "[ Ercanel ]" + ChatColor.WHITE + "돈이 부족합니다.");
                        }
                    }
                }
                if (args.length == 2) {
                    int money = 0;
                    int num = 0;
                    Player p = (Player) talker;
                    long[] stat;
                    stat = s.getStat(p.getUniqueId().toString());
                    try {
                        money = Integer.parseInt(args[0]);
                        num = Integer.parseInt(args[1]);
                    } catch (Exception e) {
                        p.sendMessage("Error");
                    }
                    if (money < 0) {
                        talker.sendMessage(ChatColor.DARK_AQUA + "[ Ercanel ]" + ChatColor.WHITE + "음수는 제공되지 않습니다 고객님 ^^7");
                    } else {
                        if (money < stat[4]){
                            ItemStack paper = new ItemStack(Material.PAPER);
                            ItemMeta im = paper.getItemMeta();
                            assert im != null;
                            im.setDisplayName(ChatColor.DARK_AQUA + "[ Ercanel ] " + ChatColor.WHITE + args[0] + " 골드");
                            paper.setItemMeta(im);
                            for (int i = 0; i < num; i++) {
                                p.getInventory().addItem(paper);
                            }
                            stat[4] = stat[4] - ((long) money * num);
                            s.setStat(p.getUniqueId().toString(), stat);
                        }else {
                            talker.sendMessage(ChatColor.DARK_AQUA + "[ Ercanel ]" + ChatColor.WHITE + "돈이 부족합니다.");
                        }
                    }
                }
            }
            if (label.equals("장비")){
                if (args.length < 3){
                    talker.sendMessage("/장비 이름 메테리얼 종류 등급 설명 장비능력치");
                                        //    0    1      2   3   4     5
                }
                else {
                    if (args[2].equals("보조")){
                        switch (args[3]){
                            case "일반":{
                                ItemStack lt = new ItemStack(Material.valueOf(args[1]));
                                ItemMeta tool = lt.getItemMeta();
                                Objects.requireNonNull(tool).setDisplayName(args[0]);
                                if (tool.getDisplayName().contains("_")){
                                    tool.setDisplayName(tool.getDisplayName().replace("_", " "));
                                }
                                tool.setLore(Arrays.asList("[ 보조 ]"+ "        "+ "[ 일반 ]", args[4],"레벨 제한 : " + args[5], "[ 미감정 마법서 ]"));
                                lt.setItemMeta(tool);
                                ((Player) talker).getInventory().addItem(lt);
                                return false;
                            }
                            case "레어":{
                                ItemStack lt = new ItemStack(Material.valueOf(args[1]));
                                ItemMeta tool = lt.getItemMeta();
                                Objects.requireNonNull(tool).setDisplayName(args[0]);
                                if (tool.getDisplayName().contains("_")){
                                    tool.setDisplayName(tool.getDisplayName().replace("_", " "));
                                }
                                tool.setLore(Arrays.asList("[ 보조 ]"+ "        "+ "[ 레어 ]", args[4],"레벨 제한 : " + args[5], "[ 미감정 마법서 ]"));
                                lt.setItemMeta(tool);
                                ((Player) talker).getInventory().addItem(lt);
                                return false;
                            }
                            case "에픽":{
                                ItemStack lt = new ItemStack(Material.valueOf(args[1]));
                                ItemMeta tool = lt.getItemMeta();
                                Objects.requireNonNull(tool).setDisplayName(args[0]);
                                if (tool.getDisplayName().contains("_")){
                                    tool.setDisplayName(tool.getDisplayName().replace("_", " "));
                                }
                                tool.setLore(Arrays.asList("[ 보조 ]"+ "        "+ "[ 에픽 ]", args[4],"레벨 제한 : " + args[5], "[ 미감정 마법서 ]"));
                                lt.setItemMeta(tool);
                                ((Player) talker).getInventory().addItem(lt);
                                return false;
                            }
                            case "전설":{
                                ItemStack lt = new ItemStack(Material.valueOf(args[1]));
                                ItemMeta tool = lt.getItemMeta();
                                Objects.requireNonNull(tool).setDisplayName(args[0]);
                                if (tool.getDisplayName().contains("_")){
                                    tool.setDisplayName(tool.getDisplayName().replace("_", " "));
                                }
                                tool.setLore(Arrays.asList("[ 보조 ]"+ "        "+ "[ 전설 ]", args[4],"레벨 제한 : " + args[5], "[ 미감정 마법서 ]"));
                                lt.setItemMeta(tool);
                                ((Player) talker).getInventory().addItem(lt);
                                return false;
                            }
                        }
                    }
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
    public void RightClick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        Action a = e.getAction();
        if (a == Action.LEFT_CLICK_AIR) {
            if (Objects.requireNonNull(p.getEquipment()).getItemInMainHand().getType() == Material.STICK) {
                if (p.getEquipment().getItemInOffHand().getType() != Material.BLAZE_ROD) {
                    p.sendMessage(ChatColor.DARK_AQUA + "[ Ercanel ]" + ChatColor.WHITE + "마법서를 왼 손에 들어주세요.");
                }
            }
        } else if(a == Action.RIGHT_CLICK_AIR){
            if (Objects.requireNonNull(p.getEquipment()).getItemInMainHand().getType() == Material.BLAZE_ROD){
                ArrayList<String> List = new ArrayList<>(Objects.requireNonNull(Objects.requireNonNull(p.getEquipment().getItemInMainHand().getItemMeta()).getLore()));
                if (List.contains("미감정")){
                    if (List.contains("일반")){
                        p.getEquipment().getItemInMainHand().getItemMeta().getLore().clear();
                        int r = DC.Random(1, 3);
                        switch (r) {
                            case 1: {
                                p.getEquipment().getItemInMainHand().getItemMeta().setLore(Collections.singletonList(ChatColor.WHITE+"[ 일반 마법 ]" +ChatColor.RED+" 불타는 총알"));
                            }
                            case 2: {
                                p.getEquipment().getItemInMainHand().getItemMeta().setLore(Collections.singletonList(ChatColor.WHITE+"[ 일반 마법 ]" +ChatColor.DARK_AQUA+" 정전기 방출"));
                            }
                            case 3: {
                                p.getEquipment().getItemInMainHand().getItemMeta().setLore(Collections.singletonList(ChatColor.WHITE+"[ 일반 마법 ]" +ChatColor.GREEN+" 날카로운 바람"));
                            }
                        }
                    }
                }
            }
            else if (p.getEquipment().getItemInMainHand().getType() == Material.PAPER){
                ItemStack total = p.getEquipment().getItemInMainHand();
                ItemMeta paper = total.getItemMeta();
                if (Objects.requireNonNull(paper).getDisplayName().contains("골드")) {
                    paper.setDisplayName(paper.getDisplayName().replace("[ Ercanel ] ", "").replace(" 골드", ""));
                    int money = Integer.parseInt(ChatColor.stripColor(paper.getDisplayName()));
                    long[] stat;
                    stat = s.getStat(p.getUniqueId().toString());
                    stat[4] = stat[4] + money;
                    s.setStat(p.getUniqueId().toString(), stat);
                    ItemStack is = p.getInventory().getItemInMainHand();
                    p.sendMessage(String.valueOf(is.getAmount()));
                    if (is.getAmount() > 1){
                        is.setAmount(is.getAmount()-1);
                    }else {
                        is.setType(Material.AIR);
                    }
                    p.getInventory().setItemInHand(is);
                    p.updateInventory();
                }
            }
        }
    }

    @EventHandler
    public void onInvC(InventoryClickEvent e) {
        e.getWhoClicked();
        if (ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("[ Status ]")) {
            SGui.StatusGui(e);
        }
    }

    @EventHandler
    public void FirstJoin(PlayerJoinEvent event){
        if (event.getPlayer().getLevel() == 0) {
            s.CreateNewStat(event.getPlayer().getUniqueId().toString());
        }
    }
}

