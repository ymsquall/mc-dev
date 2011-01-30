package net.minecraft.server;

import java.util.Random;

public class BlockGrass extends Block {

    protected BlockGrass(int i) {
        super(i, Material.EARTH);
        this.textureId = 3;
        this.a(true);
    }

    public void a(World world, int i, int j, int k, Random random) {
        if (!world.isStatic) {
            if (world.j(i, j + 1, k) < 4 && world.getMaterial(i, j + 1, k).blocksLight()) {
                if (random.nextInt(4) != 0) {
                    return;
                }

                world.e(i, j, k, Block.DIRT.id);
            } else if (world.j(i, j + 1, k) >= 9) {
                int l = i + random.nextInt(3) - 1;
                int i1 = j + random.nextInt(5) - 3;
                int j1 = k + random.nextInt(3) - 1;

                if (world.getTypeId(l, i1, j1) == Block.DIRT.id && world.j(l, i1 + 1, j1) >= 4 && !world.getMaterial(l, i1 + 1, j1).blocksLight()) {
                    world.e(l, i1, j1, Block.GRASS.id);
                }
            }
        }
    }

    public int a(int i, Random random) {
        return Block.DIRT.a(0, random);
    }
}
