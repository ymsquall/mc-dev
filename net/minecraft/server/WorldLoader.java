package net.minecraft.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class WorldLoader implements Convertable {

    protected final File a;

    public WorldLoader(File file1) {
        if (!file1.exists()) {
            file1.mkdirs();
        }

        this.a = file1;
    }

    public WorldData b(String s) {
        File file1 = new File(this.a, s);

        if (!file1.exists()) {
            return null;
        } else {
            File file2 = new File(file1, "level.dat");

            if (file2.exists()) {
                try {
                    NBTTagCompound nbttagcompound = CompressedStreamTools.a((InputStream) (new FileInputStream(file2)));
                    NBTTagCompound nbttagcompound1 = nbttagcompound.k("Data");

                    return new WorldData(nbttagcompound1);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

            return null;
        }
    }

    protected static void a(File[] afile) {
        for (int i = 0; i < afile.length; ++i) {
            if (afile[i].isDirectory()) {
                a(afile[i].listFiles());
            }

            afile[i].delete();
        }
    }

    public IDataManager a(String s, boolean flag) {
        return new PlayerNBTManager(this.a, s, flag);
    }

    public boolean a(String s) {
        return false;
    }

    public boolean a(String s, IProgressUpdate iprogressupdate) {
        return false;
    }
}
