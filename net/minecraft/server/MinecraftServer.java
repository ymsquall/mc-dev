package net.minecraft.server;

import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MinecraftServer implements Runnable, ICommandListener {

    public static Logger a = Logger.getLogger("Minecraft");
    public static HashMap b = new HashMap();
    public NetworkListenThread c;
    public PropertyManager d;
    public WorldServer e;
    public ServerConfigurationManager f;
    private ConsoleCommandHandler o;
    private boolean p = true;
    public boolean g = false;
    int h = 0;
    public String i;
    public int j;
    private List q = new ArrayList();
    private List r = Collections.synchronizedList(new ArrayList());
    public EntityTracker k;
    public boolean l;
    public boolean m;
    public boolean n;

    public MinecraftServer() {
        new ThreadSleepForever(this);
    }

    private boolean d() {
        this.o = new ConsoleCommandHandler(this);
        ThreadCommandReader threadcommandreader = new ThreadCommandReader(this);

        threadcommandreader.setDaemon(true);
        threadcommandreader.start();
        ConsoleLogManager.a();
        a.info("Starting minecraft server version Beta 1.4");
        if (Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L) {
            a.warning("**** NOT ENOUGH RAM!");
            a.warning("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
        }

        a.info("Loading properties");
        this.d = new PropertyManager(new File("server.properties"));
        String s = this.d.a("server-ip", "");

        this.l = this.d.a("online-mode", true);
        this.m = this.d.a("spawn-animals", true);
        this.n = this.d.a("pvp", true);
        InetAddress inetaddress = null;

        if (s.length() > 0) {
            inetaddress = InetAddress.getByName(s);
        }

        int i = this.d.a("server-port", 25565);

        a.info("Starting Minecraft server on " + (s.length() == 0 ? "*" : s) + ":" + i);

        try {
            this.c = new NetworkListenThread(this, inetaddress, i);
        } catch (IOException ioexception) {
            a.warning("**** FAILED TO BIND TO PORT!");
            a.log(Level.WARNING, "The exception was: " + ioexception.toString());
            a.warning("Perhaps a server is already running on that port?");
            return false;
        }

        if (!this.l) {
            a.warning("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
            a.warning("The server will make no attempt to authenticate usernames. Beware.");
            a.warning("While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose.");
            a.warning("To change this, set \"online-mode\" to \"true\" in the server.settings file.");
        }

        this.f = new ServerConfigurationManager(this);
        this.k = new EntityTracker(this);
        long j = System.nanoTime();
        String s1 = this.d.a("level-name", "world");
        String s2 = this.d.a("level-seed", "");
        long k = (new Random()).nextLong();

        if (s2.length() > 0) {
            try {
                k = Long.parseLong(s2);
            } catch (NumberFormatException numberformatexception) {
                k = (long) s2.hashCode();
            }
        }

        a.info("Preparing level \"" + s1 + "\"");
        this.a(new WorldLoaderServer(new File(".")), s1, k);
        a.info("Done (" + (System.nanoTime() - j) + "ns)! For help, type \"help\" or \"?\"");
        return true;
    }

    private void a(Convertable convertable, String s, long i) {
        if (convertable.a(s)) {
            a.info("Converting map!");
            convertable.a(s, new ConvertProgressUpdater(this));
        }

        a.info("Preparing start region");
        this.e = new WorldServer(this, new ServerNBTManager(new File("."), s, true), s, this.d.a("hellworld", false) ? -1 : 0, i);
        this.e.a(new WorldManager(this));
        this.e.j = this.d.a("spawn-monsters", true) ? 1 : 0;
        this.e.a(this.d.a("spawn-monsters", true), this.m);
        this.f.a(this.e);
        short short1 = 196;
        long j = System.currentTimeMillis();
        ChunkCoordinates chunkcoordinates = this.e.m();

        for (int k = -short1; k <= short1 && this.p; k += 16) {
            for (int l = -short1; l <= short1 && this.p; l += 16) {
                long i1 = System.currentTimeMillis();

                if (i1 < j) {
                    j = i1;
                }

                if (i1 > j + 1000L) {
                    int j1 = (short1 * 2 + 1) * (short1 * 2 + 1);
                    int k1 = (k + short1) * (short1 * 2 + 1) + l + 1;

                    this.a("Preparing spawn area", k1 * 100 / j1);
                    j = i1;
                }

                this.e.u.c(chunkcoordinates.a + k >> 4, chunkcoordinates.c + l >> 4);

                while (this.e.f() && this.p) {
                    ;
                }
            }
        }

        this.e();
    }

    private void a(String s, int i) {
        this.i = s;
        this.j = i;
        a.info(s + ": " + i + "%");
    }

    private void e() {
        this.i = null;
        this.j = 0;
    }

    private void f() {
        a.info("Saving chunks");
        this.e.a(true, (IProgressUpdate) null);
        this.e.t();
    }

    private void g() {
        a.info("Stopping server");
        if (this.f != null) {
            this.f.d();
        }

        if (this.e != null) {
            this.f();
        }
    }

    public void a() {
        this.p = false;
    }

    public void run() {
        try {
            if (this.d()) {
                long i = System.currentTimeMillis();

                for (long j = 0L; this.p; Thread.sleep(1L)) {
                    long k = System.currentTimeMillis();
                    long l = k - i;

                    if (l > 2000L) {
                        a.warning("Can\'t keep up! Did the system time change, or is the server overloaded?");
                        l = 2000L;
                    }

                    if (l < 0L) {
                        a.warning("Time ran backwards! Did the system time change?");
                        l = 0L;
                    }

                    j += l;
                    i = k;
                    if (this.e.s()) {
                        this.h();
                        j = 0L;
                    } else {
                        while (j > 50L) {
                            j -= 50L;
                            this.h();
                        }
                    }
                }
            } else {
                while (this.p) {
                    this.b();

                    try {
                        Thread.sleep(10L);
                    } catch (InterruptedException interruptedexception) {
                        interruptedexception.printStackTrace();
                    }
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            a.log(Level.SEVERE, "Unexpected exception", throwable);

            while (this.p) {
                this.b();

                try {
                    Thread.sleep(10L);
                } catch (InterruptedException interruptedexception1) {
                    interruptedexception1.printStackTrace();
                }
            }
        } finally {
            try {
                this.g();
                this.g = true;
            } catch (Throwable throwable1) {
                throwable1.printStackTrace();
            } finally {
                System.exit(0);
            }
        }
    }

    private void h() {
        ArrayList arraylist = new ArrayList();
        Iterator iterator = b.keySet().iterator();

        while (iterator.hasNext()) {
            String s = (String) iterator.next();
            int i = ((Integer) b.get(s)).intValue();

            if (i > 0) {
                b.put(s, Integer.valueOf(i - 1));
            } else {
                arraylist.add(s);
            }
        }

        int j;

        for (j = 0; j < arraylist.size(); ++j) {
            b.remove(arraylist.get(j));
        }

        AxisAlignedBB.a();
        Vec3D.a();
        ++this.h;
        if (this.h % 20 == 0) {
            this.f.a((Packet) (new Packet4UpdateTime(this.e.l())));
        }

        this.e.h();

        while (this.e.f()) {
            ;
        }

        this.e.e();
        this.c.a();
        this.f.b();
        this.k.a();

        for (j = 0; j < this.q.size(); ++j) {
            ((IUpdatePlayerListBox) this.q.get(j)).a();
        }

        try {
            this.b();
        } catch (Exception exception) {
            a.log(Level.WARNING, "Unexpected exception while parsing console command", exception);
        }
    }

    public void a(String s, ICommandListener icommandlistener) {
        this.r.add(new ServerCommand(s, icommandlistener));
    }

    public void b() {
        while (this.r.size() > 0) {
            ServerCommand servercommand = (ServerCommand) this.r.remove(0);

            this.o.a(servercommand);
        }
    }

    public void a(IUpdatePlayerListBox iupdateplayerlistbox) {
        this.q.add(iupdateplayerlistbox);
    }

    public static void main(String[] astring) {
        try {
            MinecraftServer minecraftserver = new MinecraftServer();

            if (!GraphicsEnvironment.isHeadless() && (astring.length <= 0 || !astring[0].equals("nogui"))) {
                ServerGUI.a(minecraftserver);
            }

            (new ThreadServerApplication("Server thread", minecraftserver)).start();
        } catch (Exception exception) {
            a.log(Level.SEVERE, "Failed to start the minecraft server", exception);
        }
    }

    public File a(String s) {
        return new File(s);
    }

    public void b(String s) {
        a.info(s);
    }

    public void c(String s) {
        a.warning(s);
    }

    public String c() {
        return "CONSOLE";
    }

    public static boolean a(MinecraftServer minecraftserver) {
        return minecraftserver.p;
    }
}
