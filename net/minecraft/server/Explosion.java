package net.minecraft.server;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Explosion {

    public Explosion() {}

    public void a(World world, Entity entity, double d0, double d1, double d2, float f) {
        world.a(d0, d1, d2, "random.explode", 4.0F, (1.0F + (world.m.nextFloat() - world.m.nextFloat()) * 0.2F) * 0.7F);
        HashSet hashset = new HashSet();
        float f1 = f;
        byte b0 = 16;

        int i;
        int j;
        int k;
        double d3;
        double d4;
        double d5;

        for (i = 0; i < b0; ++i) {
            for (j = 0; j < b0; ++j) {
                for (k = 0; k < b0; ++k) {
                    if (i == 0 || i == b0 - 1 || j == 0 || j == b0 - 1 || k == 0 || k == b0 - 1) {
                        double d6 = (double) ((float) i / ((float) b0 - 1.0F) * 2.0F - 1.0F);
                        double d7 = (double) ((float) j / ((float) b0 - 1.0F) * 2.0F - 1.0F);
                        double d8 = (double) ((float) k / ((float) b0 - 1.0F) * 2.0F - 1.0F);
                        double d9 = Math.sqrt(d6 * d6 + d7 * d7 + d8 * d8);

                        d6 /= d9;
                        d7 /= d9;
                        d8 /= d9;
                        float f2 = f * (0.7F + world.m.nextFloat() * 0.6F);

                        d3 = d0;
                        d4 = d1;
                        d5 = d2;

                        for (float f3 = 0.3F; f2 > 0.0F; f2 -= f3 * 0.75F) {
                            int l = MathHelper.b(d3);
                            int i1 = MathHelper.b(d4);
                            int j1 = MathHelper.b(d5);
                            int k1 = world.a(l, i1, j1);

                            if (k1 > 0) {
                                f2 -= (Block.n[k1].a(entity) + 0.3F) * f3;
                            }

                            if (f2 > 0.0F) {
                                hashset.add(new ChunkPosition(l, i1, j1));
                            }

                            d3 += d6 * (double) f3;
                            d4 += d7 * (double) f3;
                            d5 += d8 * (double) f3;
                        }
                    }
                }
            }
        }

        f *= 2.0F;
        i = MathHelper.b(d0 - (double) f - 1.0D);
        j = MathHelper.b(d0 + (double) f + 1.0D);
        k = MathHelper.b(d1 - (double) f - 1.0D);
        int l1 = MathHelper.b(d1 + (double) f + 1.0D);
        int i2 = MathHelper.b(d2 - (double) f - 1.0D);
        int j2 = MathHelper.b(d2 + (double) f + 1.0D);
        List list = world.b(entity, AxisAlignedBB.b((double) i, (double) k, (double) i2, (double) j, (double) l1, (double) j2));
        Vec3D vec3d = Vec3D.b(d0, d1, d2);

        double d10;
        double d11;
        double d12;

        for (int k2 = 0; k2 < list.size(); ++k2) {
            Entity entity1 = (Entity) list.get(k2);
            double d13 = entity1.e(d0, d1, d2) / (double) f;

            if (d13 <= 1.0D) {
                d3 = entity1.l - d0;
                d4 = entity1.m - d1;
                d5 = entity1.n - d2;
                d11 = (double) MathHelper.a(d3 * d3 + d4 * d4 + d5 * d5);
                d3 /= d11;
                d4 /= d11;
                d5 /= d11;
                d10 = (double) world.a(vec3d, entity1.v);
                d12 = (1.0D - d13) * d10;
                entity1.a(entity, (int) ((d12 * d12 + d12) / 2.0D * 8.0D * (double) f + 1.0D));
                entity1.o += d3 * d12;
                entity1.p += d4 * d12;
                entity1.q += d5 * d12;
            }
        }

        f = f1;
        ArrayList arraylist = new ArrayList();

        arraylist.addAll(hashset);

        for (int l2 = arraylist.size() - 1; l2 >= 0; --l2) {
            ChunkPosition chunkposition = (ChunkPosition) arraylist.get(l2);
            int i3 = chunkposition.a;
            int j3 = chunkposition.b;
            int k3 = chunkposition.c;
            int l3 = world.a(i3, j3, k3);

            for (int i4 = 0; i4 < 1; ++i4) {
                d5 = (double) ((float) i3 + world.m.nextFloat());
                d11 = (double) ((float) j3 + world.m.nextFloat());
                d10 = (double) ((float) k3 + world.m.nextFloat());
                d12 = d5 - d0;
                double d14 = d11 - d1;
                double d15 = d10 - d2;
                double d16 = (double) MathHelper.a(d12 * d12 + d14 * d14 + d15 * d15);

                d12 /= d16;
                d14 /= d16;
                d15 /= d16;
                double d17 = 0.5D / (d16 / (double) f + 0.1D);

                d17 *= (double) (world.m.nextFloat() * world.m.nextFloat() + 0.3F);
                d12 *= d17;
                d14 *= d17;
                d15 *= d17;
                world.a("explode", (d5 + d0 * 1.0D) / 2.0D, (d11 + d1 * 1.0D) / 2.0D, (d10 + d2 * 1.0D) / 2.0D, d12, d14, d15);
                world.a("smoke", d5, d11, d10, d12, d14, d15);
            }

            if (l3 > 0) {
                Block.n[l3].a(world, i3, j3, k3, world.b(i3, j3, k3), 0.3F);
                world.d(i3, j3, k3, 0);
                Block.n[l3].c(world, i3, j3, k3);
            }
        }
    }
}
