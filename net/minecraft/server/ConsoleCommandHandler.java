package net.minecraft.server;

import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

public class ConsoleCommandHandler {

    private static Logger a = Logger.getLogger("Minecraft");
    private MinecraftServer b;

    public ConsoleCommandHandler(MinecraftServer minecraftserver) {
        this.b = minecraftserver;
    }

    public void a(ServerCommand servercommand) {
        String s = servercommand.a;
        ICommandListener icommandlistener = servercommand.b;
        String s1 = icommandlistener.c();
        WorldServer worldserver = this.b.e;
        ServerConfigurationManager serverconfigurationmanager = this.b.f;

        if (!s.toLowerCase().startsWith("help") && !s.toLowerCase().startsWith("?")) {
            if (s.toLowerCase().startsWith("list")) {
                icommandlistener.b("Connected players: " + serverconfigurationmanager.c());
            } else if (s.toLowerCase().startsWith("stop")) {
                this.a(s1, "Stopping the server..");
                this.b.a();
            } else if (s.toLowerCase().startsWith("save-all")) {
                this.a(s1, "Forcing save..");
                worldserver.a(true, (IProgressUpdate) null);
                this.a(s1, "Save complete.");
            } else if (s.toLowerCase().startsWith("save-off")) {
                this.a(s1, "Disabling level saving..");
                worldserver.w = true;
            } else if (s.toLowerCase().startsWith("save-on")) {
                this.a(s1, "Enabling level saving..");
                worldserver.w = false;
            } else {
                String s2;

                if (s.toLowerCase().startsWith("op ")) {
                    s2 = s.substring(s.indexOf(" ")).trim();
                    serverconfigurationmanager.e(s2);
                    this.a(s1, "Opping " + s2);
                    serverconfigurationmanager.a(s2, "\u00A7eYou are now op!");
                } else if (s.toLowerCase().startsWith("deop ")) {
                    s2 = s.substring(s.indexOf(" ")).trim();
                    serverconfigurationmanager.f(s2);
                    serverconfigurationmanager.a(s2, "\u00A7eYou are no longer op!");
                    this.a(s1, "De-opping " + s2);
                } else if (s.toLowerCase().startsWith("ban-ip ")) {
                    s2 = s.substring(s.indexOf(" ")).trim();
                    serverconfigurationmanager.c(s2);
                    this.a(s1, "Banning ip " + s2);
                } else if (s.toLowerCase().startsWith("pardon-ip ")) {
                    s2 = s.substring(s.indexOf(" ")).trim();
                    serverconfigurationmanager.d(s2);
                    this.a(s1, "Pardoning ip " + s2);
                } else {
                    EntityPlayer entityplayer;

                    if (s.toLowerCase().startsWith("ban ")) {
                        s2 = s.substring(s.indexOf(" ")).trim();
                        serverconfigurationmanager.a(s2);
                        this.a(s1, "Banning " + s2);
                        entityplayer = serverconfigurationmanager.i(s2);
                        if (entityplayer != null) {
                            entityplayer.a.a("Banned by admin");
                        }
                    } else if (s.toLowerCase().startsWith("pardon ")) {
                        s2 = s.substring(s.indexOf(" ")).trim();
                        serverconfigurationmanager.b(s2);
                        this.a(s1, "Pardoning " + s2);
                    } else {
                        int i;

                        if (s.toLowerCase().startsWith("kick ")) {
                            s2 = s.substring(s.indexOf(" ")).trim();
                            entityplayer = null;

                            for (i = 0; i < serverconfigurationmanager.b.size(); ++i) {
                                EntityPlayer entityplayer1 = (EntityPlayer) serverconfigurationmanager.b.get(i);

                                if (entityplayer1.name.equalsIgnoreCase(s2)) {
                                    entityplayer = entityplayer1;
                                }
                            }

                            if (entityplayer != null) {
                                entityplayer.a.a("Kicked by admin");
                                this.a(s1, "Kicking " + entityplayer.name);
                            } else {
                                icommandlistener.b("Can\'t find user " + s2 + ". No kick.");
                            }
                        } else {
                            String[] astring;
                            EntityPlayer entityplayer2;

                            if (s.toLowerCase().startsWith("tp ")) {
                                astring = s.split(" ");
                                if (astring.length == 3) {
                                    entityplayer = serverconfigurationmanager.i(astring[1]);
                                    entityplayer2 = serverconfigurationmanager.i(astring[2]);
                                    if (entityplayer == null) {
                                        icommandlistener.b("Can\'t find user " + astring[1] + ". No tp.");
                                    } else if (entityplayer2 == null) {
                                        icommandlistener.b("Can\'t find user " + astring[2] + ". No tp.");
                                    } else {
                                        entityplayer.a.a(entityplayer2.locX, entityplayer2.locY, entityplayer2.locZ, entityplayer2.yaw, entityplayer2.pitch);
                                        this.a(s1, "Teleporting " + astring[1] + " to " + astring[2] + ".");
                                    }
                                } else {
                                    icommandlistener.b("Syntax error, please provice a source and a target.");
                                }
                            } else {
                                String s3;

                                if (s.toLowerCase().startsWith("give ")) {
                                    astring = s.split(" ");
                                    if (astring.length != 3 && astring.length != 4) {
                                        return;
                                    }

                                    s3 = astring[1];
                                    entityplayer2 = serverconfigurationmanager.i(s3);
                                    if (entityplayer2 != null) {
                                        try {
                                            int j = Integer.parseInt(astring[2]);

                                            if (Item.byId[j] != null) {
                                                this.a(s1, "Giving " + entityplayer2.name + " some " + j);
                                                int k = 1;

                                                if (astring.length > 3) {
                                                    k = this.a(astring[3], 1);
                                                }

                                                if (k < 1) {
                                                    k = 1;
                                                }

                                                if (k > 64) {
                                                    k = 64;
                                                }

                                                entityplayer2.b(new ItemStack(j, k, 0));
                                            } else {
                                                icommandlistener.b("There\'s no item with id " + j);
                                            }
                                        } catch (NumberFormatException numberformatexception) {
                                            icommandlistener.b("There\'s no item with id " + astring[2]);
                                        }
                                    } else {
                                        icommandlistener.b("Can\'t find user " + s3);
                                    }
                                } else if (s.toLowerCase().startsWith("time ")) {
                                    astring = s.split(" ");
                                    if (astring.length != 3) {
                                        return;
                                    }

                                    s3 = astring[1];

                                    try {
                                        i = Integer.parseInt(astring[2]);
                                        if ("add".equalsIgnoreCase(s3)) {
                                            worldserver.a(worldserver.l() + (long) i);
                                            this.a(s1, "Added " + i + " to time");
                                        } else if ("set".equalsIgnoreCase(s3)) {
                                            worldserver.a((long) i);
                                            this.a(s1, "Set time to " + i);
                                        } else {
                                            icommandlistener.b("Unknown method, use either \"add\" or \"set\"");
                                        }
                                    } catch (NumberFormatException numberformatexception1) {
                                        icommandlistener.b("Unable to convert time value, " + astring[2]);
                                    }
                                } else if (s.toLowerCase().startsWith("say ")) {
                                    s = s.substring(s.indexOf(" ")).trim();
                                    a.info("[" + s1 + "] " + s);
                                    serverconfigurationmanager.a((Packet) (new Packet3Chat("\u00A7d[Server] " + s)));
                                } else if (s.toLowerCase().startsWith("tell ")) {
                                    astring = s.split(" ");
                                    if (astring.length >= 3) {
                                        s = s.substring(s.indexOf(" ")).trim();
                                        s = s.substring(s.indexOf(" ")).trim();
                                        a.info("[" + s1 + "->" + astring[1] + "] " + s);
                                        s = "\u00A77" + s1 + " whispers " + s;
                                        a.info(s);
                                        if (!serverconfigurationmanager.a(astring[1], (Packet) (new Packet3Chat(s)))) {
                                            icommandlistener.b("There\'s no player by that name online.");
                                        }
                                    }
                                } else if (s.toLowerCase().startsWith("whitelist ")) {
                                    this.a(s1, s, icommandlistener);
                                } else {
                                    a.info("Unknown console command. Type \"help\" for help.");
                                }
                            }
                        }
                    }
                }
            }
        } else {
            this.a(icommandlistener);
        }
    }

    private void a(String s, String s1, ICommandListener icommandlistener) {
        String[] astring = s1.split(" ");

        if (astring.length >= 2) {
            String s2 = astring[1].toLowerCase();

            if ("on".equals(s2)) {
                this.a(s, "Turned on white-listing");
                this.b.d.b("white-list", true);
            } else if ("off".equals(s2)) {
                this.a(s, "Turned off white-listing");
                this.b.d.b("white-list", false);
            } else if ("list".equals(s2)) {
                Set set = this.b.f.e();
                String s3 = "";

                String s4;

                for (Iterator iterator = set.iterator(); iterator.hasNext(); s3 = s3 + s4 + " ") {
                    s4 = (String) iterator.next();
                }

                icommandlistener.b("White-listed players: " + s3);
            } else {
                String s5;

                if ("add".equals(s2) && astring.length == 3) {
                    s5 = astring[2].toLowerCase();
                    this.b.f.k(s5);
                    this.a(s, "Added " + s5 + " to white-list");
                } else if ("remove".equals(s2) && astring.length == 3) {
                    s5 = astring[2].toLowerCase();
                    this.b.f.l(s5);
                    this.a(s, "Removed " + s5 + " from white-list");
                } else if ("reload".equals(s2)) {
                    this.b.f.f();
                    this.a(s, "Reloaded white-list from file");
                }
            }
        }
    }

    private void a(ICommandListener icommandlistener) {
        icommandlistener.b("To run the server without a gui, start it like this:");
        icommandlistener.b("   java -Xmx1024M -Xms1024M -jar minecraft_server.jar nogui");
        icommandlistener.b("Console commands:");
        icommandlistener.b("   help  or  ?               shows this message");
        icommandlistener.b("   kick <player>             removes a player from the server");
        icommandlistener.b("   ban <player>              bans a player from the server");
        icommandlistener.b("   pardon <player>           pardons a banned player so that they can connect again");
        icommandlistener.b("   ban-ip <ip>               bans an IP address from the server");
        icommandlistener.b("   pardon-ip <ip>            pardons a banned IP address so that they can connect again");
        icommandlistener.b("   op <player>               turns a player into an op");
        icommandlistener.b("   deop <player>             removes op status from a player");
        icommandlistener.b("   tp <player1> <player2>    moves one player to the same location as another player");
        icommandlistener.b("   give <player> <id> [num]  gives a player a resource");
        icommandlistener.b("   tell <player> <message>   sends a private message to a player");
        icommandlistener.b("   stop                      gracefully stops the server");
        icommandlistener.b("   save-all                  forces a server-wide level save");
        icommandlistener.b("   save-off                  disables terrain saving (useful for backup scripts)");
        icommandlistener.b("   save-on                   re-enables terrain saving");
        icommandlistener.b("   list                      lists all currently connected players");
        icommandlistener.b("   say <message>             broadcasts a message to all players");
        icommandlistener.b("   time <add|set> <amount>   adds to or sets the world time (0-24000)");
    }

    private void a(String s, String s1) {
        String s2 = s + ": " + s1;

        this.b.f.j("\u00A77(" + s2 + ")");
        a.info(s2);
    }

    private int a(String s, int i) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException numberformatexception) {
            return i;
        }
    }
}
