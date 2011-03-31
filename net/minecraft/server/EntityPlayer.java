package net.minecraft.server;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class EntityPlayer extends EntityHuman implements ICrafting {

    public NetServerHandler a;
    public MinecraftServer b;
    public ItemInWorldManager c;
    public double d;
    public double e;
    public List f = new LinkedList();
    public Set g = new HashSet();
    private int bE = -99999999;
    private int bF = 60;
    private ItemStack[] bG = new ItemStack[] { null, null, null, null, null};
    private int bH = 0;
    public boolean h;

    public EntityPlayer(MinecraftServer minecraftserver, World world, String s, ItemInWorldManager iteminworldmanager) {
        super(world);
        ChunkCoordinates chunkcoordinates = world.m();
        int i = chunkcoordinates.a;
        int j = chunkcoordinates.c;
        int k = chunkcoordinates.b;

        if (!world.m.e) {
            i += this.random.nextInt(20) - 10;
            k = world.e(i, j);
            j += this.random.nextInt(20) - 10;
        }

        this.c((double) i + 0.5D, (double) k, (double) j + 0.5D, 0.0F, 0.0F);
        this.b = minecraftserver;
        this.bm = 0.0F;
        iteminworldmanager.a = this;
        this.name = s;
        this.c = iteminworldmanager;
        this.height = 0.0F;
    }

    public void m() {
        this.activeContainer.a((ICrafting) this);
    }

    public ItemStack[] k_() {
        return this.bG;
    }

    protected void l_() {
        this.height = 0.0F;
    }

    public float q() {
        return 1.62F;
    }

    public void f_() {
        this.c.a();
        --this.bF;
        this.activeContainer.a();

        for (int i = 0; i < 5; ++i) {
            ItemStack itemstack = this.b_(i);

            if (itemstack != this.bG[i]) {
                this.b.k.a(this, new Packet5EntityEquipment(this.id, i, itemstack));
                this.bG[i] = itemstack;
            }
        }
    }

    public ItemStack b_(int i) {
        return i == 0 ? this.inventory.b() : this.inventory.b[i - 1];
    }

    public void a(Entity entity) {
        this.inventory.h();
    }

    public boolean a(Entity entity, int i) {
        if (this.bF > 0) {
            return false;
        } else {
            if (!this.b.n) {
                if (entity instanceof EntityHuman) {
                    return false;
                }

                if (entity instanceof EntityArrow) {
                    EntityArrow entityarrow = (EntityArrow) entity;

                    if (entityarrow.b instanceof EntityHuman) {
                        return false;
                    }
                }
            }

            return super.a(entity, i);
        }
    }

    public void b(int i) {
        super.b(i);
    }

    public void a(boolean flag) {
        super.f_();
        if (flag && !this.f.isEmpty()) {
            ChunkCoordIntPair chunkcoordintpair = (ChunkCoordIntPair) this.f.get(0);

            if (chunkcoordintpair != null) {
                boolean flag1 = false;

                if (this.a.b() < 2) {
                    flag1 = true;
                }

                if (flag1) {
                    this.f.remove(chunkcoordintpair);
                    this.a.b((Packet) (new Packet51MapChunk(chunkcoordintpair.a * 16, 0, chunkcoordintpair.b * 16, 16, 128, 16, this.b.e)));
                    List list = this.b.e.d(chunkcoordintpair.a * 16, 0, chunkcoordintpair.b * 16, chunkcoordintpair.a * 16 + 16, 128, chunkcoordintpair.b * 16 + 16);

                    for (int i = 0; i < list.size(); ++i) {
                        this.a((TileEntity) list.get(i));
                    }
                }
            }
        }

        if (this.health != this.bE) {
            this.a.b((Packet) (new Packet8UpdateHealth(this.health)));
            this.bE = this.health;
        }
    }

    private void a(TileEntity tileentity) {
        if (tileentity != null) {
            Packet packet = tileentity.e();

            if (packet != null) {
                this.a.b(packet);
            }
        }
    }

    public void r() {
        super.r();
    }

    public void b(Entity entity, int i) {
        if (!entity.dead) {
            if (entity instanceof EntityItem) {
                this.b.k.a(entity, new Packet22Collect(entity.id, this.id));
            }

            if (entity instanceof EntityArrow) {
                this.b.k.a(entity, new Packet22Collect(entity.id, this.id));
            }
        }

        super.b(entity, i);
        this.activeContainer.a();
    }

    public void m_() {
        if (!this.p) {
            this.q = -1;
            this.p = true;
            this.b.k.a(this, new Packet18ArmAnimation(this, 1));
        }
    }

    public void t() {}

    public EnumBedError a(int i, int j, int k) {
        EnumBedError enumbederror = super.a(i, j, k);

        if (enumbederror == EnumBedError.OK) {
            this.b.k.a(this, new Packet17(this, 0, i, j, k));
        }

        return enumbederror;
    }

    public void a(boolean flag, boolean flag1, boolean flag2) {
        if (this.F()) {
            this.b.k.b(this, new Packet18ArmAnimation(this, 3));
        }

        super.a(flag, flag1, flag2);
        this.a.a(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
    }

    public void b(Entity entity) {
        super.b(entity);
        this.a.b((Packet) (new Packet39AttachEntity(this, this.vehicle)));
        this.a.a(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
    }

    protected void a(double d0, boolean flag) {}

    public void b(double d0, boolean flag) {
        super.a(d0, flag);
    }

    private void aa() {
        this.bH = this.bH % 100 + 1;
    }

    public void b(int i, int j, int k) {
        this.aa();
        this.a.b((Packet) (new Packet100OpenWindow(this.bH, 1, "Crafting", 9)));
        this.activeContainer = new ContainerWorkbench(this.inventory, this.world, i, j, k);
        this.activeContainer.f = this.bH;
        this.activeContainer.a((ICrafting) this);
    }

    public void a(IInventory iinventory) {
        this.aa();
        this.a.b((Packet) (new Packet100OpenWindow(this.bH, 0, iinventory.c(), iinventory.q_())));
        this.activeContainer = new ContainerChest(this.inventory, iinventory);
        this.activeContainer.f = this.bH;
        this.activeContainer.a((ICrafting) this);
    }

    public void a(TileEntityFurnace tileentityfurnace) {
        this.aa();
        this.a.b((Packet) (new Packet100OpenWindow(this.bH, 2, tileentityfurnace.c(), tileentityfurnace.q_())));
        this.activeContainer = new ContainerFurnace(this.inventory, tileentityfurnace);
        this.activeContainer.f = this.bH;
        this.activeContainer.a((ICrafting) this);
    }

    public void a(TileEntityDispenser tileentitydispenser) {
        this.aa();
        this.a.b((Packet) (new Packet100OpenWindow(this.bH, 3, tileentitydispenser.c(), tileentitydispenser.q_())));
        this.activeContainer = new ContainerDispenser(this.inventory, tileentitydispenser);
        this.activeContainer.f = this.bH;
        this.activeContainer.a((ICrafting) this);
    }

    public void a(Container container, int i, ItemStack itemstack) {
        if (!(container.a(i) instanceof SlotResult)) {
            if (!this.h) {
                this.a.b((Packet) (new Packet103SetSlot(container.f, i, itemstack)));
            }
        }
    }

    public void a(Container container, List list) {
        this.a.b((Packet) (new Packet104WindowItems(container.f, list)));
        this.a.b((Packet) (new Packet103SetSlot(-1, -1, this.inventory.j())));
    }

    public void a(Container container, int i, int j) {
        this.a.b((Packet) (new Packet105CraftProgressBar(container.f, i, j)));
    }

    public void a(ItemStack itemstack) {}

    public void u() {
        this.a.b((Packet) (new Packet101CloseWindow(this.activeContainer.f)));
        this.w();
    }

    public void v() {
        if (!this.h) {
            this.a.b((Packet) (new Packet103SetSlot(-1, -1, this.inventory.j())));
        }
    }

    public void w() {
        this.activeContainer.a((EntityHuman) this);
        this.activeContainer = this.defaultContainer;
    }

    public void a(float f, float f1, boolean flag, boolean flag1, float f2, float f3) {
        this.au = f;
        this.av = f1;
        this.ax = flag;
        this.e(flag1);
        this.pitch = f2;
        this.yaw = f3;
    }
}
