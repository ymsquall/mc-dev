package net.minecraft.server;

public class InventoryCrafting implements IInventory {

    private ItemStack[] a;
    private int b;
    private Container c;

    public InventoryCrafting(Container container, int i, int j) {
        int k = i * j;

        this.a = new ItemStack[k];
        this.c = container;
        this.b = i;
    }

    public int m_() {
        return this.a.length;
    }

    public ItemStack c_(int i) {
        return i >= this.m_() ? null : this.a[i];
    }

    public ItemStack b(int i, int j) {
        if (i >= 0 && i < this.b) {
            int k = i + j * this.b;

            return this.c_(k);
        } else {
            return null;
        }
    }

    public String c() {
        return "Crafting";
    }

    public ItemStack a(int i, int j) {
        if (this.a[i] != null) {
            ItemStack itemstack;

            if (this.a[i].count <= j) {
                itemstack = this.a[i];
                this.a[i] = null;
                this.c.a((IInventory) this);
                return itemstack;
            } else {
                itemstack = this.a[i].a(j);
                if (this.a[i].count == 0) {
                    this.a[i] = null;
                }

                this.c.a((IInventory) this);
                return itemstack;
            }
        } else {
            return null;
        }
    }

    public void a(int i, ItemStack itemstack) {
        this.a[i] = itemstack;
        this.c.a((IInventory) this);
    }

    public int n_() {
        return 64;
    }

    public void h() {}

    public boolean a_(EntityHuman entityhuman) {
        return true;
    }
}
