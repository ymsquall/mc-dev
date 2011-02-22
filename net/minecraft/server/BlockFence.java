package net.minecraft.server;

public class BlockFence extends Block {

    public BlockFence(int i, int j) {
        super(i, j, Material.WOOD);
    }

    public boolean a(World world, int i, int j, int k) {
        return world.getTypeId(i, j - 1, k) == this.id ? false : (!world.getMaterial(i, j - 1, k).isBuildable() ? false : super.a(world, i, j, k));
    }

    public AxisAlignedBB d(World world, int i, int j, int k) {
        return AxisAlignedBB.b((double) i, (double) j, (double) k, (double) (i + 1), (double) ((float) j + 1.5F), (double) (k + 1));
    }

    public boolean a() {
        return false;
    }
}
