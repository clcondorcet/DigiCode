package ketao.eu.org.code;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener{

	public PlayerListener(Main plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void signchange(SignChangeEvent e) {
		if(e.getLine(0).equalsIgnoreCase("[code]")){
			if(Main.permscrea == "true"){
				if(!e.getPlayer().hasPermission("code.create")){
					File fichierc = new File(Main.path + "/config.yml");
					FileConfiguration configc = YamlConfiguration.loadConfiguration(fichierc);
					e.getPlayer().sendMessage(ChatColor.RED + configc.getString("noPermission"));
					return;
				}
			}
		File fichierc = new File(Main.path + "/config.yml");
		FileConfiguration configc = YamlConfiguration.loadConfiguration(fichierc);
		Location loc = e.getBlock().getLocation();
		Location locA = loc;
		locA.setX(locA.getBlockX() + 1);
		Material block = locA.getBlock().getType();
		int i = 0;
		if(block == Material.IRON_BLOCK){
			i = i + 1;
		}
		locA.setX(locA.getBlockX()  + - 2);
		block = locA.getBlock().getType();
		if(block  == Material.IRON_BLOCK){
			i = i + 1;
		}
		locA.setX(locA.getBlockX() + 1);
		locA.setZ(locA.getBlockZ() + 1);
		block = locA.getBlock().getType();
		if(block  == Material.IRON_BLOCK){
			i = i + 1;
		}
		locA.setZ(locA.getBlockZ() + - 2);
		block = locA.getBlock().getType();
		if(block  == Material.IRON_BLOCK){
			i = i + 1;
		}
		if(i == 0){
			e.getPlayer().sendMessage(ChatColor.RED + configc.getString("needironblock"));
			return;
		}
		if(i >= 2){
			e.getPlayer().sendMessage(ChatColor.RED + configc.getString("oneironblock"));
			return;
		}
			String st = e.getLine(1);
			String name = e.getLine(2);
			if(st.length() != 4){
				e.setLine(0, ChatColor.RED + "CODE");
				e.setLine(1, ChatColor.RED + "ERREUR");
				e.setLine(2, ChatColor.RED + "");
				e.setLine(3, ChatColor.RED + "");
				e.getPlayer().sendMessage(ChatColor.RED + configc.getString("passwordoffour"));
				return;
			}
			try{
				Integer.parseInt(st);
			}catch(Exception ex){
				e.setLine(0, ChatColor.RED + "CODE");
				e.setLine(1, ChatColor.RED + "ERREUR");
				e.setLine(2, ChatColor.RED + "");
				e.setLine(3, ChatColor.RED + "");
				e.getPlayer().sendMessage(ChatColor.RED + configc.getString("passwordoffour"));
				return;
			}
			File fichier = new File(Main.path + "/code.yml");
			FileConfiguration config = YamlConfiguration.loadConfiguration(fichier);
			try{
				if(config.get(name).equals(null)){
				}else{
					e.setLine(0, ChatColor.RED + "CODE");
					e.setLine(1, ChatColor.RED + "ERREUR");
					e.setLine(2, ChatColor.RED + "");
					e.setLine(3, ChatColor.RED + "");
					e.getPlayer().sendMessage(ChatColor.RED + configc.getString("nameexist"));
					return;
				}
			}catch(Exception ex){}
			Location loc2 = e.getBlock().getLocation();
			config.set("code+"+loc2.getWorld().toString()+loc2.getBlockX()+loc2.getBlockY()+loc2.getBlockZ() , st);
			config.set(name, loc2.getWorld().toString()+loc2.getBlockX()+loc2.getBlockY()+loc2.getBlockZ());
			try {
				config.save(Main.path + "/code.yml");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.setLine(0, ChatColor.AQUA + "Code");
			e.setLine(1, ChatColor.AQUA + "_ _ _ _");
			e.setLine(2, name);
			e.setLine(3, ChatColor.RED + configc.getString("accessno"));
		}
			
		
	}
	
	@EventHandler
	public void PlayerInteractevent(PlayerInteractEvent e) {
		try{
			e.getClickedBlock().getType();
		}catch(Exception ex){return;}
		try{
		if (((String)Code.status.get(e.getPlayer())).equalsIgnoreCase("delete")){
			File fichierc = new File(Main.path + "/config.yml");
			FileConfiguration configc = YamlConfiguration.loadConfiguration(fichierc);
			if(e.getClickedBlock().getType().equals(Material.WALL_SIGN) || e.getClickedBlock().getType().equals(Material.SIGN) || e.getClickedBlock().getType().equals(Material.SIGN_POST)){
				Sign s = (Sign) e.getClickedBlock().getState();
				if(s.getLine(0).equals(ChatColor.AQUA + "Code")){
					File fichier = new File(Main.path + "/code.yml");
					FileConfiguration config = YamlConfiguration.loadConfiguration(fichier);
					String name = config.getString(s.getLine(2));
					config.set(s.getLine(2), null);
					config.set("code+"+name, null);
					try {
						config.save(Main.path + "/code.yml");
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					e.getClickedBlock().setType(Material.AIR);
					Code.status.put(e.getPlayer(), "");
					e.getPlayer().sendMessage(ChatColor.RED + configc.getString("Digicodedelete"));
					e.getPlayer().sendMessage(ChatColor.RED + configc.getString("quitmodedelete"));
					return;
				}
			}
			e.setCancelled(true);
			e.getPlayer().sendMessage(ChatColor.RED + configc.getString("docodedelete"));
			return;
		}
		}catch(Exception ex){}
		if(e.getClickedBlock().getType().equals(Material.WALL_SIGN) || e.getClickedBlock().getType().equals(Material.SIGN) || e.getClickedBlock().getType().equals(Material.SIGN_POST)){
			Sign s = (Sign) e.getClickedBlock().getState();
			if(s.getLine(0).equals(ChatColor.AQUA + "Code")) {
				if(s.getLine(3).equals(ChatColor.GREEN + "Accès Accepté")){
					s.setLine(3, ChatColor.RED + "Accès Refusé");
					s.update();
					Location loc = e.getClickedBlock().getLocation();
					Location locA = loc;
					locA.setX(locA.getBlockX() + 1);
					Material block = locA.getBlock().getType();
					if(block == Material.IRON_BLOCK){
						locA.setX(locA.getBlockX() + 1);
						locA.getBlock().setType(Material.AIR);
						return;
					}
					locA.setX(locA.getBlockX()  + - 2);
					block = locA.getBlock().getType();
					if(block == Material.IRON_BLOCK){
						locA.setX(locA.getBlockX() + - 1);
						locA.getBlock().setType(Material.AIR);
						return;
					}
					locA.setX(locA.getBlockX() + 1);
					locA.setZ(locA.getBlockZ() + 1);
					block = locA.getBlock().getType();
					if(block == Material.IRON_BLOCK){
						locA.setZ(locA.getBlockZ() + 1);
						locA.getBlock().setType(Material.AIR);
						return;
					}
					locA.setZ(locA.getBlockZ() + - 2);
					block = locA.getBlock().getType();
					if(block == Material.IRON_BLOCK){
						locA.setZ(locA.getBlockZ() + - 1);
						locA.getBlock().setType(Material.AIR);
						return;
					}
				}else if(s.getLine(3).equals(ChatColor.RED + "Accès Refusé")){
					File fichier3 = new File(Main.path + "/digi" + ".yml");
					FileConfiguration config3 = YamlConfiguration.loadConfiguration(fichier3);
					File fichier = new File(Main.path + "/code" + ".yml");
					FileConfiguration config = YamlConfiguration.loadConfiguration(fichier);
					try {
						if(config.get(s.getLine(2).toString()).equals("blablablablablablablabla")){
							
						}
					}catch(Exception ex){
						s.setLine(0, ChatColor.RED + "CODE");
						s.setLine(1, ChatColor.RED + "ERREUR");
						s.setLine(2, ChatColor.RED + "");
						s.setLine(3, ChatColor.RED + "");
						s.update();
						return;
					}
					Inventory Menu = Bukkit.createInventory(null, 45, s.getLine(2));
					ItemStack itemm[] = new ItemStack[45];
					int i = 0;
					while (i <= 44){
						itemm[i] = config3.getItemStack(Integer.toString(i));
					  	i = i + 1;
					}
					Menu.setContents(itemm);
					e.getPlayer().openInventory(Menu);
					Code.loc.put(e.getPlayer(), e.getClickedBlock().getLocation());
				}else{
					s.setLine(0, ChatColor.RED + "CODE");
					s.setLine(1, ChatColor.RED + "ERREUR");
					File fichierc = new File(Main.path + "/config.yml");
					FileConfiguration configc = YamlConfiguration.loadConfiguration(fichierc);
					e.getPlayer().sendMessage(ChatColor.RED + configc.getString("error"));
				}
			}
		}
	}
	
	@EventHandler
	public void Playerbreak (BlockBreakEvent e){
		if(e.getBlock().getType().equals(Material.SIGN) || e.getBlock().getType().equals(Material.WALL_SIGN) || e.getBlock().getType().equals(Material.SIGN_POST)){
			Sign s = (Sign) e.getBlock().getState();
			if(s.getLine(0).equals(ChatColor.AQUA + "Code")){
				e.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void invclick(InventoryClickEvent e){
		File fichierc = new File(Main.path + "/config.yml");
		FileConfiguration configc = YamlConfiguration.loadConfiguration(fichierc);
		if(e.getInventory().getSize() == 45){
			int cli = e.getSlot();
// 1
			if(cli == 10){
				try{
					if(e.getInventory().getItem(15).equals(null)){}
					try{
						if(e.getInventory().getItem(25).equals(null)){}
						try{
							if(e.getInventory().getItem(33).equals(null)){}
							try{
								if(e.getInventory().getItem(23).equals(null)){}
								e.getWhoClicked().sendMessage(ChatColor.RED + configc.getString("nuberdispo"));
							}catch(Exception ex){
								e.getInventory().setItem(23, e.getInventory().getItem(10));}
						}catch(Exception ex){
							e.getInventory().setItem(33, e.getInventory().getItem(10));}
					}catch(Exception ex){
						e.getInventory().setItem(25, e.getInventory().getItem(10));}
				}catch(Exception ex){
					e.getInventory().setItem(15, e.getInventory().getItem(10));}
// 2		
			}else if(cli == 11){
				try{
					if(e.getInventory().getItem(15).equals(null)){}
					try{
						if(e.getInventory().getItem(25).equals(null)){}
						try{
							if(e.getInventory().getItem(33).equals(null)){}
							try{
								if(e.getInventory().getItem(23).equals(null)){}
								e.getWhoClicked().sendMessage(ChatColor.RED + configc.getString("nuberdispo"));
							}catch(Exception ex){
								e.getInventory().setItem(23, e.getInventory().getItem(11));}
						}catch(Exception ex){
							e.getInventory().setItem(33, e.getInventory().getItem(11));}
					}catch(Exception ex){
						e.getInventory().setItem(25, e.getInventory().getItem(11));}
				}catch(Exception ex){
					e.getInventory().setItem(15, e.getInventory().getItem(11));}
// 3		
			}else if(cli == 12){
				try{
					if(e.getInventory().getItem(15).equals(null)){}
					try{
						if(e.getInventory().getItem(25).equals(null)){}
						try{
							if(e.getInventory().getItem(33).equals(null)){}
							try{
								if(e.getInventory().getItem(23).equals(null)){}
								e.getWhoClicked().sendMessage(ChatColor.RED + configc.getString("nuberdispo"));
							}catch(Exception ex){
								e.getInventory().setItem(23, e.getInventory().getItem(12));}
						}catch(Exception ex){
							e.getInventory().setItem(33, e.getInventory().getItem(12));}
					}catch(Exception ex){
						e.getInventory().setItem(25, e.getInventory().getItem(12));}
				}catch(Exception ex){
					e.getInventory().setItem(15, e.getInventory().getItem(12));}
// 4
			}else if(cli == 19){
				try{
					if(e.getInventory().getItem(15).equals(null)){}
					try{
						if(e.getInventory().getItem(25).equals(null)){}
						try{
							if(e.getInventory().getItem(33).equals(null)){}
							try{
								if(e.getInventory().getItem(23).equals(null)){}
								e.getWhoClicked().sendMessage(ChatColor.RED + configc.getString("nuberdispo"));
							}catch(Exception ex){
								e.getInventory().setItem(23, e.getInventory().getItem(19));}
						}catch(Exception ex){
							e.getInventory().setItem(33, e.getInventory().getItem(19));}
					}catch(Exception ex){
						e.getInventory().setItem(25, e.getInventory().getItem(19));}
				}catch(Exception ex){
					e.getInventory().setItem(15, e.getInventory().getItem(19));}
// 5
			}else if(cli == 20){
				try{
					if(e.getInventory().getItem(15).equals(null)){}
					try{
						if(e.getInventory().getItem(25).equals(null)){}
						try{
							if(e.getInventory().getItem(33).equals(null)){}
							try{
								if(e.getInventory().getItem(23).equals(null)){}
								e.getWhoClicked().sendMessage(ChatColor.RED + configc.getString("nuberdispo"));
							}catch(Exception ex){
								e.getInventory().setItem(23, e.getInventory().getItem(20));}
						}catch(Exception ex){
							e.getInventory().setItem(33, e.getInventory().getItem(20));}
					}catch(Exception ex){
						e.getInventory().setItem(25, e.getInventory().getItem(20));}
				}catch(Exception ex){
					e.getInventory().setItem(15, e.getInventory().getItem(20));}
// 6
			}else if(cli == 21){
				try{
					if(e.getInventory().getItem(15).equals(null)){}
					try{
						if(e.getInventory().getItem(25).equals(null)){}
						try{
							if(e.getInventory().getItem(33).equals(null)){}
							try{
								if(e.getInventory().getItem(23).equals(null)){}
								e.getWhoClicked().sendMessage(ChatColor.RED + configc.getString("nuberdispo"));
							}catch(Exception ex){
								e.getInventory().setItem(23, e.getInventory().getItem(21));}
						}catch(Exception ex){
							e.getInventory().setItem(33, e.getInventory().getItem(21));}
					}catch(Exception ex){
						e.getInventory().setItem(25, e.getInventory().getItem(21));}
				}catch(Exception ex){
					e.getInventory().setItem(15, e.getInventory().getItem(21));}
// 7
			}else if(cli == 28){
				try{
					if(e.getInventory().getItem(15).equals(null)){}
					try{
						if(e.getInventory().getItem(25).equals(null)){}
						try{
							if(e.getInventory().getItem(33).equals(null)){}
							try{
								if(e.getInventory().getItem(23).equals(null)){}
								e.getWhoClicked().sendMessage(ChatColor.RED + configc.getString("nuberdispo"));
							}catch(Exception ex){
								e.getInventory().setItem(23, e.getInventory().getItem(28));}
						}catch(Exception ex){
							e.getInventory().setItem(33, e.getInventory().getItem(28));}
					}catch(Exception ex){
						e.getInventory().setItem(25, e.getInventory().getItem(28));}
				}catch(Exception ex){
					e.getInventory().setItem(15, e.getInventory().getItem(28));}
// 8
				}else if(cli == 29){
					try{
						if(e.getInventory().getItem(15).equals(null)){}
						try{
							if(e.getInventory().getItem(25).equals(null)){}
							try{
								if(e.getInventory().getItem(33).equals(null)){}
								try{
									if(e.getInventory().getItem(23).equals(null)){}
									e.getWhoClicked().sendMessage(ChatColor.RED + configc.getString("nuberdispo"));
								}catch(Exception ex){
									e.getInventory().setItem(23, e.getInventory().getItem(29));}
							}catch(Exception ex){
								e.getInventory().setItem(33, e.getInventory().getItem(29));}
						}catch(Exception ex){
							e.getInventory().setItem(25, e.getInventory().getItem(29));}
					}catch(Exception ex){
						e.getInventory().setItem(15, e.getInventory().getItem(29));}
// 9
				}else if(cli == 30){
					try{
						if(e.getInventory().getItem(15).equals(null)){}
						try{
							if(e.getInventory().getItem(25).equals(null)){}
							try{
								if(e.getInventory().getItem(33).equals(null)){}
								try{
									if(e.getInventory().getItem(23).equals(null)){}
									e.getWhoClicked().sendMessage(ChatColor.RED + configc.getString("nuberdispo"));
								}catch(Exception ex){
									e.getInventory().setItem(23, e.getInventory().getItem(30));}
							}catch(Exception ex){
								e.getInventory().setItem(33, e.getInventory().getItem(30));}
						}catch(Exception ex){
							e.getInventory().setItem(25, e.getInventory().getItem(30));}
					}catch(Exception ex){
						e.getInventory().setItem(15, e.getInventory().getItem(30));}
// 0
				}else if(cli == 38){
					try{
						if(e.getInventory().getItem(15).equals(null)){}
						try{
							if(e.getInventory().getItem(25).equals(null)){}
							try{
								if(e.getInventory().getItem(33).equals(null)){}
								try{
									if(e.getInventory().getItem(23).equals(null)){}
									e.getWhoClicked().sendMessage(ChatColor.RED + configc.getString("nuberdispo"));
								}catch(Exception ex){
									e.getInventory().setItem(23, e.getInventory().getItem(38));}
							}catch(Exception ex){
								e.getInventory().setItem(33, e.getInventory().getItem(38));}
						}catch(Exception ex){
							e.getInventory().setItem(25, e.getInventory().getItem(38));}
					}catch(Exception ex){
						e.getInventory().setItem(15, e.getInventory().getItem(38));}
// case1
				}else if(cli == 15){
					e.getInventory().clear(15);
// case2
				}else if(cli == 23){
					e.getInventory().clear(23);
// case3
				}else if(cli == 25){
					e.getInventory().clear(25);
// case4
				}else if(cli == 33){
					e.getInventory().clear(33);
// valider
				}else if(cli == 24){
					String name = e.getInventory().getName();
					File fichier = new File(Main.path + "/code.yml");
					FileConfiguration config = YamlConfiguration.loadConfiguration(fichier);
					String code = config.getString("code+"+config.getString(name));
					String codeenter = "";
					try{
					codeenter = e.getInventory().getItem(15).getItemMeta().getDisplayName();
					codeenter = codeenter + e.getInventory().getItem(25).getItemMeta().getDisplayName();
					codeenter = codeenter + e.getInventory().getItem(33).getItemMeta().getDisplayName();
					codeenter = codeenter + e.getInventory().getItem(23).getItemMeta().getDisplayName();
					config.set("code", code);
					config.set("coden", codeenter);
					config.save(Main.path + "/code.yml");
					
					}catch(Exception ex){
						e.getWhoClicked().sendMessage(ChatColor.RED + configc.getString("passwordoffour"));
						e.setCancelled(true);
						return;
					}
					int codei = Integer.parseInt(config.getString("code"));
					int codeenteri = Integer.parseInt(config.getString("coden"));
					if(codei == codeenteri){
						Sign s = (Sign) Code.loc.get(e.getWhoClicked()).getBlock().getState();
						s.setLine(3, ChatColor.GREEN + configc.getString("accessyes"));
						s.update();
						e.getWhoClicked().closeInventory();

						Location locA = Code.loc.get(e.getWhoClicked());
						locA.setX(locA.getBlockX() + 1);
						Material block = locA.getBlock().getType();
						if(block == Material.IRON_BLOCK){
							locA.setX(locA.getBlockX() + 1);
							locA.getBlock().setType(Material.REDSTONE_BLOCK);
							return;
						}
						locA.setX(locA.getBlockX()  + - 2);
						block = locA.getBlock().getType();
						if(block == Material.IRON_BLOCK){
							locA.setX(locA.getBlockX() + - 1);
							locA.getBlock().setType(Material.REDSTONE_BLOCK);
							return;
						}
						locA.setX(locA.getBlockX() + 1);
						locA.setZ(locA.getBlockZ() + 1);
						block = locA.getBlock().getType();
						if(block == Material.IRON_BLOCK){
							locA.setZ(locA.getBlockZ() + 1);
							locA.getBlock().setType(Material.REDSTONE_BLOCK);
							return;
						}
						locA.setZ(locA.getBlockZ() + - 2);
						block = locA.getBlock().getType();
						if(block == Material.IRON_BLOCK){
							locA.setZ(locA.getBlockZ() + - 1);
							locA.getBlock().setType(Material.REDSTONE_BLOCK);
							return;
						}
						return;
					}else{
						e.getWhoClicked().sendMessage(ChatColor.RED + configc.getString("wrongpass"));
						e.getWhoClicked().closeInventory();
						return;
					}
				}
			e.setCancelled(true);
		}
	}

}
