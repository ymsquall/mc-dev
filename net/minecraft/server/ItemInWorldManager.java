package net.minecraft.server;

public class ItemInWorldManager {

    private World b;
    public EntityHuman a;
    private float c = 0.0F;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private boolean i;
    private int j;
    private int k;
    private int l;
    private int m;

    public ItemInWorldManager(World world) {
        this.b = world;
    }

    public void a() {
        ++this.h;
        if (this.i) {
            int i = this.h - this.m;
            int j = this.b.getTypeId(this.j, this.k, this.l);

            if (j != 0) {
                Block block = Block.byId[j];
                float f = block.a(this.a) * (float) (i + 1);

                if (f >= 1.0F) {
                    this.i = false;
                    this.d(this.j, this.k, this.l);
                }
            } else {
                this.i = false;
            }
        }
    }

    public void a(int i, int j, int k) {
        this.d = this.h;
        int l = this.b.getTypeId(i, j, k);

        if (l > 0) {
            Block.byId[l].b(this.b, i, j, k, this.a);
        }

        if (l > 0 && Block.byId[l].a(this.a) >= 1.0F) {
            this.d(i, j, k);
        } else {
            this.e = i;
            this.f = j;
            this.g = k;
        }
    }

    public void b(int i, int j, int k) {
        if (i == this.e && j == this.f && k == this.g) {
            int l = this.h - this.d;
            int i1 = this.b.getTypeId(i, j, k);

            if (i1 != 0) {
                Block block = Block.byId[i1];
                float f = block.a(this.a) * (float) (l + 1);

                if (f >= 1.0F) {
                    this.d(i, j, k);
                } else if (!this.i) {
                    this.i = true;
                    this.j = i;
                    this.k = j;
                    this.l = k;
                    this.m = this.d;
                }
            }
        }

        this.c = 0.0F;
    }

    public boolean c(int i, int j, int k) {
        Block block = Block.byId[this.b.getTypeId(i, j, k)];
        int l = this.b.getData(i, j, k);
        boolean flag = this.b.e(i, j, k, 0);

        if (block != null && flag) {
            block.b(this.b, i, j, k, l);
        }

        return flag;
    }

    public boolean d(int i, int j, int k) {
        int l = this.b.getTypeId(i, j, k);
        int i1 = this.b.getData(i, j, k);
        boolean flag = this.c(i, j, k);
        ItemStack itemstack = this.a.A();

        if (itemstack != null) {
            itemstack.a(l, i, j, k, this.a);
            if (itemstack.count == 0) {
                itemstack.a(this.a);
                this.a.B();
            }
        }

        if (flag && this.a.b(Block.byId[l])) {
            Block.byId[l].a(this.b, this.a, i, j, k, i1);
            ((EntityPlayer) this.a).a.b((Packet) (new Packet53BlockChange(i, j, k, this.b)));
        }

        return flag;
    }

    public boolean a(EntityHuman entityhuman, World world, ItemStack itemstack) {
        int i = itemstack.count;
        ItemStack itemstack1 = itemstack.a(world, entityhuman);

        if (itemstack1 == itemstack && (itemstack1 == null || itemstack1.count == i)) {
            return false;
        } else {
            entityhuman.inventory.a[entityhuman.inventory.c] = itemstack1;
            if (itemstack1.count == 0) {
                entityhuman.inventory.a[entityhuman.inventory.c] = null;
            }

            return true;
        }
    }

    public boolean a(EntityHuman entityhuman, World world, ItemStack itemstack, int i, int j, int k, int l) {
        int i1 = world.getTypeId(i, j, k);

        return i1 > 0 && Block.byId[i1].a(world, i, j, k, entityhuman) ? true : (itemstack == null ? false : itemstack.a(entityhuman, world, i, j, k, l));
    }
}
