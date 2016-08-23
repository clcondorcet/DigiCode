package ketao.eu.org.code;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public static String path;
	public static String permscrea;
	public static String permsdelete;
	
	public void onEnable(){
		new PlayerListener(this);
		saveDefaultConfig();
		getCommand("code").setExecutor(new Code());
		Main.path = getDataFolder().toString();
		File digi = new File(Main.path + "/digi.yml");
		if(!digi.exists()){
			load();
		}
	    File fichier = new File(Main.path + "/config.yml");
		FileConfiguration configc = YamlConfiguration.loadConfiguration(fichier);
	    Main.permscrea = configc.getString("permissionsforcreate");
	    Main.permsdelete = configc.getString("permissionsfordelete");
	  }
	public void onDisable(){
	}
	
	public void load(){
		ItemStack item[] = new ItemStack[45];
		
		int u = 0;
		while (u <= 44){
			if(u <= 9 || u == 13 || u == 14 || (u >= 16 && u <= 18) || u == 22 || u == 26 || u == 27 || u == 31 || u == 32 || (u >= 34 && u <= 37) || (u >= 39 && u <= 44)){
				ItemStack mitem = new ItemStack(Material.STAINED_GLASS_PANE);
				short sh = 15;
				mitem.setDurability(sh);
				ItemMeta meta = mitem.getItemMeta();
				meta.setDisplayName(" ");
				mitem.setItemMeta(meta);
				item[u] = mitem;
			}else if(u == 38){
				ItemStack mitem = new ItemStack(Material.DOUBLE_PLANT);
				ItemMeta meta = mitem.getItemMeta();
				meta.setDisplayName("0");
				mitem.setItemMeta(meta);
				item[u] = mitem;
			}else if(u == 10){
				ItemStack mitem = new ItemStack(Material.DOUBLE_PLANT);
				ItemMeta meta = mitem.getItemMeta();
				meta.setDisplayName("1");
				mitem.setItemMeta(meta);
				item[u] = mitem;
			}else if(u == 11){
				ItemStack mitem = new ItemStack(Material.DOUBLE_PLANT);
				ItemMeta meta = mitem.getItemMeta();
				meta.setDisplayName("2");
				mitem.setItemMeta(meta);
				item[u] = mitem;
			}else if(u == 12){
				ItemStack mitem = new ItemStack(Material.DOUBLE_PLANT);
				ItemMeta meta = mitem.getItemMeta();
				meta.setDisplayName("3");
				mitem.setItemMeta(meta);
				item[u] = mitem;
			}else if(u == 19){
				ItemStack mitem = new ItemStack(Material.DOUBLE_PLANT);
				ItemMeta meta = mitem.getItemMeta();
				meta.setDisplayName("4");
				mitem.setItemMeta(meta);
				item[u] = mitem;
			}else if(u == 20){
				ItemStack mitem = new ItemStack(Material.DOUBLE_PLANT);
				ItemMeta meta = mitem.getItemMeta();
				meta.setDisplayName("5");
				mitem.setItemMeta(meta);
				item[u] = mitem;
			}else if(u == 21){
				ItemStack mitem = new ItemStack(Material.DOUBLE_PLANT);
				ItemMeta meta = mitem.getItemMeta();
				meta.setDisplayName("6");
				mitem.setItemMeta(meta);
				item[u] = mitem;
			}else if(u == 28){
				ItemStack mitem = new ItemStack(Material.DOUBLE_PLANT);
				ItemMeta meta = mitem.getItemMeta();
				meta.setDisplayName("7");
				mitem.setItemMeta(meta);
				item[u] = mitem;
			}else if(u == 29){
				ItemStack mitem = new ItemStack(Material.DOUBLE_PLANT);
				ItemMeta meta = mitem.getItemMeta();
				meta.setDisplayName("8");
				mitem.setItemMeta(meta);
				item[u] = mitem;
			}else if(u == 30){
				ItemStack mitem = new ItemStack(Material.DOUBLE_PLANT);
				ItemMeta meta = mitem.getItemMeta();
				meta.setDisplayName("9");
				mitem.setItemMeta(meta);
				item[u] = mitem;
			}else if(u == 24){
				ItemStack mitem = new ItemStack(Material.STAINED_GLASS_PANE);
				short sh = 5;
				mitem.setDurability(sh);
				ItemMeta meta = mitem.getItemMeta();
				meta.setDisplayName(ChatColor.GREEN + "Valider");
				mitem.setItemMeta(meta);
				item[u] = mitem;
			}
			u = u + 1;
		}
		
		File fichier = new File(Main.path + "/digi.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(fichier);
		int i = 0;
		while (i <= 44){
			config.set(Integer.toString(i), item[i]);
			i = i + 1;
		}
		try {
			config.save(Main.path + "/digi.yml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
