package net.minecraft.server;

public class SlotResult extends Slot {

    private final IInventory d;
    private EntityHuman e;

    public SlotResult(EntityHuman entityhuman, IInventory iinventory, IInventory iinventory1, int i, int j, int k) {
        super(iinventory1, i, j, k);
        this.e = entityhuman;
        this.d = iinventory;
    }

    public boolean b(ItemStack itemstack) {
        return false;
    }

    public void a(ItemStack itemstack) {
        this.e.a(StatisticList.z[itemstack.id], 1);
        if (itemstack.id == Block.WORKBENCH.id) {
            this.e.a((Statistic) AchievementList.d, 1);
        }

        for (int i = 0; i < this.d.q_(); ++i) {
            ItemStack itemstack1 = this.d.c_(i);

            if (itemstack1 != null) {
                this.d.a(i, 1);
                if (itemstack1.a().h()) {
                    this.d.a(i, new ItemStack(itemstack1.a().g()));
                }
            }
        }
    }

    public boolean d() {
        return true;
    }
}
