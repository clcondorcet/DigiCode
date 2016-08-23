package ketao.eu.org.code;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Code implements CommandExecutor {
	public static Map<Player, String> status = new HashMap<Player, String>();
	public static Map<String, String> save = new HashMap<String, String>();
	public static Map<Player, Location> loc = new HashMap<Player, Location>();

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if(args.length == 0){
			s.sendMessage(ChatColor.AQUA + "/code delete");
			s.sendMessage(ChatColor.AQUA + "/code mdelete [nom du digicode]");
			s.sendMessage(ChatColor.AQUA + "/code reload");
			return true;
		}else if(args[0].length() != 0){
			if (args[0].equalsIgnoreCase("delete")){
				if(Main.permsdelete == "true"){
					if(!s.hasPermission("code.create")){
						s.sendMessage(ChatColor.RED + "Tu n'as pas la permission");
						return true;
					}
				}
					if (Code.status.get(Bukkit.getPlayer(s.getName())) == null) {
		            	Code.status.put(Bukkit.getPlayer(s.getName()), "");
		            }
		            if (!((String)Code.status.get(Bukkit.getPlayer(s.getName()))).equalsIgnoreCase("delete")){
		            	Code.status.put(Bukkit.getPlayer(s.getName()), "delete");
		            	s.sendMessage(ChatColor.AQUA + "Tu est en mode de supression");
		            	s.sendMessage(ChatColor.AQUA + "Appuie sur le digicode que tu veut enlever");
		            }else{
		            	Code.status.put(Bukkit.getPlayer(s.getName()), "");
		            	s.sendMessage(ChatColor.AQUA + "Tu n'est plus en mode de supression");
		            }
		        return true;

			}else if(args[0].equalsIgnoreCase("mdelete")){
				if(Main.permsdelete == "true"){
					if(!s.hasPermission("code.create")){
						s.sendMessage(ChatColor.RED + "Tu n'as pas la permission");
						return true;
					}
				}
						if(args.length == 1){
							s.sendMessage(ChatColor.AQUA + "/code mdelete [nom du digicode]");
							return true;
						}
						try{
						if (args.length == 2){
							File fichier = new File(Main.path + "/code.yml");
							FileConfiguration config = YamlConfiguration.loadConfiguration(fichier);
							config.set("code+" + config.getString(args[1]), null);
							config.set(args[1], null);
							config.save(Main.path + "/code.yml");
							s.sendMessage(ChatColor.AQUA + "le digicode \"" + args[1] + "\" a bien été suprimer");
							return true;
						}
						}catch(Exception ex){}
			}else if(args[0].equalsIgnoreCase("reload")){
				if(!s.hasPermission("code.reload")){
					s.sendMessage(ChatColor.RED + "Tu n'as pas la permission");
					return true;
				}
				File fichier = new File(Main.path + "/config.yml");
				FileConfiguration config = YamlConfiguration.loadConfiguration(fichier);
				Main.permscrea = config.getString("permissionsforcreate");
			    Main.permsdelete = config.getString("permissionsfordelete");
			    s.sendMessage(ChatColor.GREEN + "Le plugin est rechargé");
			}
				
		}
		return true;
	}

}
