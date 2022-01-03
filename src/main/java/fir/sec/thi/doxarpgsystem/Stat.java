package fir.sec.thi.doxarpgsystem;

import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Stat {
    public void CreateNewStat(String player){
        File filename = new File("plugins/RPGStat/Stat/" + player + ".yml");
        File folder_Location1 = new File("plugins/RPGStat/");
        File folder_Location2 = new File("plugins/RPGStat/Stat");
        try {
            if (! filename.exists()){
                folder_Location1.mkdir();
                folder_Location2.mkdir();
                filename.createNewFile();
            }
            BufferedWriter w = new BufferedWriter(new FileWriter(filename));
            w.append("레벨 : 1" + "\r\n" + "잔여 스텟 : 0" + "\r\n" + "경험치 : 0" +
                    "\r\n" + "최대 경험치 : 0" + "\r\n" + "돈 : 0" + "\r\n" + "기본 체력 : 5" +
                    "\r\n" + "체력 스텟 : 0" + "\r\n" + "힘 스텟 : 0" + "\r\n" + "명중 스텟 : 0" +
                    "\r\n" + "민첩 스텟 : 0" + "\r\n" + "지력 스텟 : 0");
            w.flush();
            w.close();
        }
        catch (IOException localIoExeption){
        }
    }

    public long[] getStat(String player){
        File filename = new File("plugins/RPGStat/Stat/" + player + ".yml");
        File folder_Location1 = new File("plugins/RPGStat/");
        File folder_Location2 = new File("plugins/RPGStat/Stat");
        long[] stat = new long[11];
        try {
            if (!filename.exists()) {
                folder_Location1.mkdir();
                folder_Location2.mkdir();
                filename.createNewFile();
            }
            BufferedReader R = new BufferedReader(new FileReader(filename));
            List list = new ArrayList();
            String s;
            while ((s = R.readLine()) != null) {
                list.add(Long.valueOf(Cutter(s)));
            }
            R.close();
            for (int count = 0; count < 10; count++) {
                stat[count] = ((Long) list.get(count)).longValue();
            }
            return stat;
        }
        catch (IOException localIoException){
        }
        return stat;
    }

    public long Cutter(String line){
        String[] cut = line.split(":");
        return Long.parseLong(cut[1]);
    }

    public void setStat(String player, long[] stat){
        File filename = new File("plugins/RPGStat/Stat/" + player + ".yml");
        File folder_Location1 = new File("plugins/RPGStat/");
        File folder_Location2 = new File("plugins/RPGStat/Stat");
        try {
            if (!filename.exists()) {
                folder_Location1.mkdir();
                folder_Location2.mkdir();
                filename.createNewFile();
            }
            BufferedWriter W = new BufferedWriter(new FileWriter(filename));
            W.append("레벨 : "+stat[0] + "\r\n" + "잔여 스텟 : "+stat[1] + "\r\n" +
                    "경험치 : "+stat[2] + "\r\n" + "최대 경험치 : "+stat[3] + "\r\n" + "돈 : "+stat[4] + "\r\n" +
                    "기본 체력 : "+stat[5] + "\r\n" + "체력 스텟 : "+stat[6] + "\r\n" + "힘 스텟 : "+stat[7] + "\r\n" +
                    "명중 스텟 : "+stat[8] + "\r\n" + "민첩 스텟 : "+stat[9] + "\r\n" + "지력 스텟 : "+stat[10]);
            W.flush();
            W.close();
        }
        catch (IOException localIoException){
        }
    }

    public void StatUp(long[] stat, Player player, int num) {
        if (stat[1] > 0) {
            stat[num] = stat[5] + 1;
            stat[1] = stat[1] - 1;
            setStat(player.getUniqueId().toString(), stat);
        }
        else {
            player.sendMessage("[Ercanel] : 잔여 스텟이 부족합니다.");
        }
    }
}
