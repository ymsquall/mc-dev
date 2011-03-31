package net.minecraft.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChunkProviderServer implements IChunkProvider {

    private Set a = new HashSet();
    private Chunk b;
    private IChunkProvider c;
    private IChunkLoader d;
    private Map e = new HashMap();
    private List f = new ArrayList();
    private WorldServer g;

    public ChunkProviderServer(WorldServer worldserver, IChunkLoader ichunkloader, IChunkProvider ichunkprovider) {
        this.b = new EmptyChunk(worldserver, new byte['\u8000'], 0, 0);
        this.g = worldserver;
        this.d = ichunkloader;
        this.c = ichunkprovider;
    }

    public boolean a(int i, int j) {
        return this.e.containsKey(Integer.valueOf(ChunkCoordIntPair.a(i, j)));
    }

    public void d(int i, int j) {
        ChunkCoordinates chunkcoordinates = this.g.m();
        int k = i * 16 + 8 - chunkcoordinates.a;
        int l = j * 16 + 8 - chunkcoordinates.c;
        short short1 = 128;

        if (k < -short1 || k > short1 || l < -short1 || l > short1) {
            this.a.add(Integer.valueOf(ChunkCoordIntPair.a(i, j)));
        }
    }

    public Chunk c(int i, int j) {
        int k = ChunkCoordIntPair.a(i, j);

        this.a.remove(Integer.valueOf(k));
        Chunk chunk = (Chunk) this.e.get(Integer.valueOf(k));

        if (chunk == null) {
            chunk = this.e(i, j);
            if (chunk == null) {
                if (this.c == null) {
                    chunk = this.b;
                } else {
                    chunk = this.c.b(i, j);
                }
            }

            this.e.put(Integer.valueOf(k), chunk);
            this.f.add(chunk);
            if (chunk != null) {
                chunk.c();
                chunk.d();
            }

            if (!chunk.n && this.a(i + 1, j + 1) && this.a(i, j + 1) && this.a(i + 1, j)) {
                this.a(this, i, j);
            }

            if (this.a(i - 1, j) && !this.b(i - 1, j).n && this.a(i - 1, j + 1) && this.a(i, j + 1) && this.a(i - 1, j)) {
                this.a(this, i - 1, j);
            }

            if (this.a(i, j - 1) && !this.b(i, j - 1).n && this.a(i + 1, j - 1) && this.a(i, j - 1) && this.a(i + 1, j)) {
                this.a(this, i, j - 1);
            }

            if (this.a(i - 1, j - 1) && !this.b(i - 1, j - 1).n && this.a(i - 1, j - 1) && this.a(i, j - 1) && this.a(i - 1, j)) {
                this.a(this, i - 1, j - 1);
            }
        }

        return chunk;
    }

    public Chunk b(int i, int j) {
        Chunk chunk = (Chunk) this.e.get(Integer.valueOf(ChunkCoordIntPair.a(i, j)));

        return chunk == null ? (this.g.r ? this.c(i, j) : this.b) : chunk;
    }

    private Chunk e(int i, int j) {
        if (this.d == null) {
            return null;
        } else {
            try {
                Chunk chunk = this.d.a(this.g, i, j);

                if (chunk != null) {
                    chunk.r = this.g.l();
                }

                return chunk;
            } catch (Exception exception) {
                exception.printStackTrace();
                return null;
            }
        }
    }

    private void a(Chunk chunk) {
        if (this.d != null) {
            try {
                this.d.b(this.g, chunk);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private void b(Chunk chunk) {
        if (this.d != null) {
            try {
                chunk.r = this.g.l();
                this.d.a(this.g, chunk);
            } catch (IOException ioexception) {
                ioexception.printStackTrace();
            }
        }
    }

    public void a(IChunkProvider ichunkprovider, int i, int j) {
        Chunk chunk = this.b(i, j);

        if (!chunk.n) {
            chunk.n = true;
            if (this.c != null) {
                this.c.a(ichunkprovider, i, j);
                chunk.f();
            }
        }
    }

    public boolean a(boolean flag, IProgressUpdate iprogressupdate) {
        int i = 0;

        for (int j = 0; j < this.f.size(); ++j) {
            Chunk chunk = (Chunk) this.f.get(j);

            if (flag && !chunk.p) {
                this.a(chunk);
            }

            if (chunk.a(flag)) {
                this.b(chunk);
                chunk.o = false;
                ++i;
                if (i == 24 && !flag) {
                    return false;
                }
            }
        }

        if (flag) {
            if (this.d == null) {
                return true;
            }

            this.d.b();
        }

        return true;
    }

    public boolean a() {
        if (!this.g.w) {
            for (int i = 0; i < 100; ++i) {
                if (!this.a.isEmpty()) {
                    Integer integer = (Integer) this.a.iterator().next();
                    Chunk chunk = (Chunk) this.e.get(integer);

                    chunk.e();
                    this.b(chunk);
                    this.a(chunk);
                    this.a.remove(integer);
                    this.e.remove(integer);
                    this.f.remove(chunk);
                }
            }

            if (this.d != null) {
                this.d.a();
            }
        }

        return this.c.a();
    }

    public boolean b() {
        return !this.g.w;
    }
}
