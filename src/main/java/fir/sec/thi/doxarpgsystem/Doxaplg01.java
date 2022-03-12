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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

@SuppressWarnings("unused")
public final class Doxaplg01 extends JavaPlugin implements Listener, CommandExecutor {

    public Stat s = new Stat();
    public GUI SGui = new GUI();
    public DamageCalculator DC = new DamageCalculator();

    public static HashMap<String, Integer> attack = new HashMap<>();
    public static HashMap<String, Integer> defense = new HashMap<>();
    public static HashMap<String, Integer> critical = new HashMap<>();
    public static HashMap<String, Integer> health = new HashMap<>();
    public static HashMap<String, Integer> regen = new HashMap<>();
    public static HashMap<String, Integer> movespeed = new HashMap<>();
    public static HashMap<String, Integer> level = new HashMap<>();


    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Doxa On!");
        getServer().getPluginManager().registerEvents(new Attack(), this);
        getServer().getPluginManager().registerEvents(new Level(), this);
        getServer().getPluginManager().registerEvents(new Quest(), this);
        getServer().getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(getCommand("스탯")).setExecutor(this);
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for (Player player :Bukkit.getOnlinePlayers()) {
                    if (Objects.requireNonNull(player.getEquipment()).getItemInMainHand().getType() == Material.AIR || ! player.getEquipment().getItemInMainHand().hasItemMeta() || ! Objects.requireNonNull(player.getEquipment().getItemInMainHand().getItemMeta()).hasLore()){
                        attack.put("근접 공격력", 0);
                        attack.put("원거리 공격력", 0);
                        attack.put("마법 공격력", 0);
                        critical.put("치명타 확률", 0);
                        level.put("무기", 0);
                    } else {
                        ArrayList<String> loremainhand = ((ArrayList<String>) Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(player).getEquipment()).getItemInMainHand().getItemMeta()).getLore());
                        for (int i = 0; i < Objects.requireNonNull(loremainhand).size(); i++){
                            if (loremainhand.get(i).contains("공격력")){
                                String ia = loremainhand.get(i);
                                if (ia.contains("근접")) {
                                    attack.put("근접 공격력", Integer.valueOf(ChatColor.stripColor(ia.replace("근접 공격력 : ", ""))));
                                    attack.put("원거리 공격력", 0);
                                    attack.put("마법 공격력", 0);
                                }
                                else if (ia.contains("원거리")) {
                                    attack.put("원거리 공격력", Integer.valueOf(ChatColor.stripColor(ia.replace("원거리 공격력 : ", ""))));
                                    attack.put("마법 공격력", 0);
                                    attack.put("근접 공격력", 0);
                                }
                                else if (ia.contains("마법")) {
                                    attack.put("원거리 공격력", Integer.valueOf(ChatColor.stripColor(ia.replace("마법 공격력 : ", ""))));
                                    attack.put("마법 공격력", 0);
                                    attack.put("근접 공격력", 0);
                                }
                            }
                            if (loremainhand.get(i).contains("치명타")){
                                String ic = loremainhand.get(i);
                                critical.put("치명타 확률", Integer.valueOf(ChatColor.stripColor(ic.replace("치명타 확률 : ", ""))));
                            }
                            if (loremainhand.get(i).contains("레벨 제한")){
                                String il = loremainhand.get(i);
                                level.put("무기", Integer.valueOf(ChatColor.stripColor(il.replace("레벨 제한 : ", ""))));
                            }
                        }
                    }
                    if (player.getEquipment().getHelmet() == null || Objects.requireNonNull(player.getEquipment().getHelmet()).getType() == Material.AIR ||
                            ! player.getEquipment().getHelmet().hasItemMeta()){
                        defense.put("투구", 0);
                        health.put("투구", 0);
                        regen.put("투구", 0);
                        movespeed.put("투구", 0);
                        level.put("투구", 0);
                    } else {

                        ArrayList<String> lorehelmet = ((ArrayList<String>) Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(player.getEquipment()).getHelmet()).getItemMeta()).getLore());

                        for (int i = 0; i < Objects.requireNonNull(lorehelmet).size(); i++){
                            if (lorehelmet.get(i).contains("방어력")){
                                String hid = lorehelmet.get(i);
                                defense.put("투구", Integer.valueOf(ChatColor.stripColor(hid.replace("방어력 : ", ""))));
                            }
                            if (lorehelmet.get(i).contains("체력 증가")) {
                                String hih = lorehelmet.get(i);
                                health.put("투구", Integer.valueOf(ChatColor.stripColor(hih.replace("체력 증가 : ", ""))));
                            }
                            if (lorehelmet.get(i).contains("체력 재생")){
                                String hir = lorehelmet.get(i);
                                regen.put("투구", Integer.valueOf(ChatColor.stripColor(hir.replace("체력 재생 : ", ""))));
                            }
                            if (lorehelmet.get(i).contains("이동 속도")){
                                String him = lorehelmet.get(i);
                                movespeed.put("투구", Integer.valueOf(ChatColor.stripColor(him.replace("이동 속도 : ", ""))));
                            }
                            if (lorehelmet.get(i).contains("레벨 제한")){
                                String hil = lorehelmet.get(i);
                                level.put("투구", Integer.valueOf(ChatColor.stripColor(hil.replace("레벨 제한 : ", ""))));
                            }
                        }
                    }
                    if (player.getEquipment().getChestplate() == null || player.getEquipment().getChestplate().getType() == Material.AIR ||
                            ! player.getEquipment().getChestplate().hasItemMeta()){
                        defense.put("흉갑", 0);
                        health.put("흉갑", 0);
                        regen.put("흉갑", 0);
                        movespeed.put("흉갑", 0);
                        level.put("흉갑", 0);
                    } else {

                        ArrayList<String> lorechestplate = ((ArrayList<String>) Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(player.getEquipment()).getChestplate()).getItemMeta()).getLore());

                        for (int i = 0; i < Objects.requireNonNull(lorechestplate).size(); i++){
                            if (lorechestplate.get(i).contains("방어력")){
                                String cid = lorechestplate.get(i);
                                defense.put("흉갑", Integer.valueOf(ChatColor.stripColor(cid.replace("방어력 : ", ""))));
                            }
                            if (lorechestplate.get(i).contains("체력 증가")){
                                String cih = lorechestplate.get(i);
                                health.put("흉갑", Integer.valueOf(ChatColor.stripColor(cih.replace("체력 증가 : ", ""))));
                            }
                            if (lorechestplate.get(i).contains("체력 재생")){
                                String cir = lorechestplate.get(i);
                                regen.put("흉갑", Integer.valueOf(ChatColor.stripColor(cir.replace("체력 재생 : ", ""))));
                            }
                            if (lorechestplate.get(i).contains("이동 속도")){
                                String cim = lorechestplate.get(i);
                                movespeed.put("흉갑", Integer.valueOf(ChatColor.stripColor(cim.replace("이동 속도 : ", ""))));
                            }
                            if (lorechestplate.get(i).contains("레벨 제한")){
                                String cil = lorechestplate.get(i);
                                level.put("흉갑", Integer.valueOf(ChatColor.stripColor(cil.replace("레벨 제한 : ", ""))));
                            }
                        }
                    }
                    if (player.getEquipment().getLeggings() == null || player.getEquipment().getLeggings().getType() == Material.AIR ||
                            ! player.getEquipment().getLeggings().hasItemMeta()){
                        defense.put("각반", 0);
                        health.put("각반", 0);
                        regen.put("각반", 0);
                        movespeed.put("각반", 0);
                        level.put("각반", 0);
                    } else {

                        ArrayList<String> loreleggings = ((ArrayList<String>) Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(player.getEquipment()).getLeggings()).getItemMeta()).getLore());

                        for (int i = 0; i < Objects.requireNonNull(loreleggings).size(); i++){
                            if (loreleggings.get(i).contains("방어력")){
                                String lid = loreleggings.get(i);
                                defense.put("각반", Integer.valueOf(ChatColor.stripColor(lid.replace("방어력 : ", ""))));
                            }
                            if (loreleggings.get(i).contains("체력 증가")){
                                String lih = loreleggings.get(i);
                                health.put("각반", Integer.valueOf(ChatColor.stripColor(lih.replace("체력 증가 : ", ""))));
                            }
                            if (loreleggings.get(i).contains("체력 재생")){
                                String lir = loreleggings.get(i);
                                regen.put("각반", Integer.valueOf(ChatColor.stripColor(lir.replace("체력 재생 : ", ""))));
                            }
                            if (loreleggings.get(i).contains("이동 속도")){
                                String lim = loreleggings.get(i);
                                movespeed.put("각반", Integer.valueOf(ChatColor.stripColor(lim.replace("이동 속도 : ", ""))));
                            }
                            if (loreleggings.get(i).contains("레벨 제한")){
                                String lil = loreleggings.get(i);
                                level.put("각반", Integer.valueOf(ChatColor.stripColor(lil.replace("레벨 제한 : ", ""))));
                            }
                        }
                    }
                    if (player.getEquipment().getBoots() == null || player.getEquipment().getBoots().getType() == Material.AIR ||
                            ! player.getEquipment().getBoots().hasItemMeta()){
                        defense.put("신발", 0);
                        health.put("신발", 0);
                        regen.put("신발", 0);
                        movespeed.put("신발", 0);
                        level.put("신발", 0);
                    } else {

                        ArrayList<String> loreboots = ((ArrayList<String>) Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(player.getEquipment()).getBoots()).getItemMeta()).getLore());

                        for (int i = 0; i < Objects.requireNonNull(loreboots).size(); i++){
                            if (loreboots.get(i).contains("방어력")){
                                String bid = loreboots.get(i);
                                defense.put("신발", Integer.valueOf(ChatColor.stripColor(bid.replace("방어력 : ", ""))));
                            }
                            if (loreboots.get(i).contains("체력 증가")){
                                String bih = loreboots.get(i);
                                health.put("신발", Integer.valueOf(ChatColor.stripColor(bih.replace("체력 증가 : ", ""))));
                            }
                            if (loreboots.get(i).contains("체력 재생")){
                                String bir = loreboots.get(i);
                                regen.put("신발", Integer.valueOf(ChatColor.stripColor(bir.replace("체력 재생 : ", ""))));
                            }
                            if (loreboots.get(i).contains("이동 속도")){
                                String bim = loreboots.get(i);
                                movespeed.put("신발", Integer.valueOf(ChatColor.stripColor(bim.replace("이동 속도 : ", ""))));
                            }
                            if (loreboots.get(i).contains("레벨 제한")){
                                String bil = loreboots.get(i);
                                level.put("신발", Integer.valueOf(ChatColor.stripColor(bil.replace("레벨 제한 : ", ""))));
                            }
                        }
                    }

                    ItemStack air = new ItemStack(Material.AIR);
                    if (level.get("투구") > player.getLevel()){
                        ItemStack helmet = player.getEquipment().getHelmet();
                        player.getEquipment().setHelmet(air);
                        player.getInventory().addItem(helmet);
                        player.sendMessage(ChatColor.DARK_AQUA + "[ Ercanel ]" + ChatColor.WHITE + "갑옷 착용 조건이 충족되지 않았습니다!");
                        player.updateInventory();
                    }
                    if (level.get("흉갑") > player.getLevel()){
                        ItemStack chestplate = player.getEquipment().getChestplate();
                        player.getEquipment().setHelmet(air);
                        player.getInventory().addItem(chestplate);
                        player.sendMessage(ChatColor.DARK_AQUA + "[ Ercanel ]" + ChatColor.WHITE + "갑옷 착용 조건이 충족되지 않았습니다!");
                        player.updateInventory();
                    }
                    if (level.get("각반") > player.getLevel()){
                        ItemStack leggings = player.getEquipment().getLeggings();
                        player.getEquipment().setHelmet(air);
                        player.getInventory().addItem(leggings);
                        player.sendMessage(ChatColor.DARK_AQUA + "[ Ercanel ]" + ChatColor.WHITE + "갑옷 착용 조건이 충족되지 않았습니다!");
                        player.updateInventory();
                    }
                    if (level.get("신발") > player.getLevel()){
                        ItemStack boots = player.getEquipment().getBoots();
                        player.getEquipment().setHelmet(air);
                        player.getInventory().addItem(boots);
                        player.sendMessage(ChatColor.DARK_AQUA + "[ Ercanel ]" + ChatColor.WHITE + "갑옷 착용 조건이 충족되지 않았습니다!");
                        player.updateInventory();
                    }

                    long[] stat;
                    stat = s.getStat(player.getUniqueId().toString());

                    int exp = (int) stat[2];
                    int maxexp = (int) stat[3];
                    double perexp = (double) stat[2] / stat[3];
                    player.setExp(Float.parseFloat(String.format("%.2f", perexp)));
                    player.setLevel((int) stat[0]);

                    stat[18] = 100 + movespeed.get("투구") + movespeed.get("흉갑") + movespeed.get("각반") + movespeed.get("신발");                     // 이동 속도
                    stat[17] = stat[5] + (stat[6] * 5) + health.get("투구") + health.get("흉갑") + health.get("각반") + health.get("신발");             // 체력
                    stat[11] = defense.get("투구") + defense.get("흉갑") + defense.get("각반") + defense.get("신발");                                             // 방어력
                    double speed = stat[18]*0.001;
                    Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(stat[17]);
                    Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(speed);
                    s.setStat(player.getUniqueId().toString(), stat);
                }
            }
        },0L , 10L);
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()){
                    double regeneration = (regen.get("투구") + regen.get("흉갑") + regen.get("각반") + regen.get("신발") + 1);
                    if (player.getMaxHealth() > player.getHealth() + regeneration) {
                        player.setHealth(Math.round(player.getHealth()) + regeneration);
                    }
                    else{
                        player.setHealth(player.getMaxHealth());
                    }
                }
            }
        },0L, 100L);
    }

    @Override
    public void onDisable() {
        getLogger().info("Doxa Off!");
        // Plugin shutdown logic
    }

    //스탯 명령어 ctrl + o
    public boolean onCommand(CommandSender talker, Command command, String label, String[] args) {
        if (talker instanceof Player) {
            long[] Stat;
            Stat = s.getStat(((Player) talker).getUniqueId().toString());
            if (label.equals("sure")) {
                talker.sendMessage(ChatColor.DARK_AQUA + "[ Ercanel ]" + ChatColor.WHITE + "퀘스트를 수락하였습니다! 도전과제를 확인하여 퀘스트 내용을 확인하세요!");
                Stat[13] = Stat[13] + 1;
                Stat[14] = 0;
            }
            if (label.equals("cancel")) {
                talker.sendMessage(ChatColor.DARK_AQUA + "[ Ercanel ]" + ChatColor.WHITE + "퀘스트를 거절하였습니다!");
                Stat[14] = 0;
            }

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
                            im.setLore(Arrays.asList(ChatColor.GRAY+"전 지역에서 활발하게 사용되는 화폐이다.",ChatColor.GRAY+"재질은 아르킨 제국의 히프의 털로 만들어져 부드럽다."));
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
                            im.setLore(Arrays.asList(ChatColor.GRAY+"전 지역에서 활발하게 사용되는 화폐이다.",ChatColor.GRAY+"재질은 아르킨 제국의 히프의 털로 만들어져 부드럽다."));
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
            s.setStat(((Player) talker).getUniqueId().toString(), Stat);
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
                    ItemStack air = new ItemStack(Material.AIR);
                    if (is.getAmount() > 1){
                        is.setAmount(is.getAmount()-1);
                    }else {
                        p.getEquipment().setItemInMainHand(air);
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

