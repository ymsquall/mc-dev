package net.minecraft.server;

import java.util.Random;

public class BlockStep extends Block {

    public static final String[] a = new String[] { "stone", "sand", "wood", "cobble"};
    private boolean b;

    public BlockStep(int i, boolean flag) {
        super(i, 6, Material.STONE);
        this.b = flag;
        if (!flag) {
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }

        this.e(255);
    }

    public int a(int i, int j) {
        return j == 0 ? (i <= 1 ? 6 : 5) : (j == 1 ? (i == 0 ? 208 : (i == 1 ? 176 : 192)) : (j == 2 ? 4 : (j == 3 ? 16 : 6)));
    }

    public int a(int i) {
        return this.a(i, 0);
    }

    public boolean a() {
        return this.b;
    }

    public void e(World world, int i, int j, int k) {
        if (this != Block.STEP) {
            super.e(world, i, j, k);
        }

        int l = world.getTypeId(i, j - 1, k);
        int i1 = world.getData(i, j, k);
        int j1 = world.getData(i, j - 1, k);

        if (i1 == j1) {
            if (l == STEP.id) {
                world.e(i, j, k, 0);
                world.b(i, j - 1, k, Block.DOUBLE_STEP.id, i1);
            }
        }
    }

    public int a(int i, Random random) {
        return Block.STEP.id;
    }

    public int a(Random random) {
        return this.b ? 2 : 1;
    }

    protected int b(int i) {
        return i;
    }

    public boolean a(IBlockAccess iblockaccess, int i, int j, int k, int l) {
        if (this != Block.STEP) {
            super.a(iblockaccess, i, j, k, l);
        }

        return l == 1 ? true : (!super.a(iblockaccess, i, j, k, l) ? false : (l == 0 ? true : iblockaccess.getTypeId(i, j, k) != this.id));
    }
}
