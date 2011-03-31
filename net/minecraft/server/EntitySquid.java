package net.minecraft.server;

public class EntitySquid extends EntityWaterAnimal {

    public float a = 0.0F;
    public float b = 0.0F;
    public float c = 0.0F;
    public float f = 0.0F;
    public float g = 0.0F;
    public float h = 0.0F;
    public float i = 0.0F;
    public float j = 0.0F;
    private float k = 0.0F;
    private float l = 0.0F;
    private float m = 0.0F;
    private float n = 0.0F;
    private float o = 0.0F;
    private float p = 0.0F;

    public EntitySquid(World world) {
        super(world);
        this.texture = "/mob/squid.png";
        this.b(0.95F, 0.95F);
        this.l = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
    }

    protected String e() {
        return null;
    }

    protected String f() {
        return null;
    }

    protected String g() {
        return null;
    }

    protected float i() {
        return 0.4F;
    }

    protected int h() {
        return 0;
    }

    protected void p() {
        int i = this.random.nextInt(3) + 1;

        for (int j = 0; j < i; ++j) {
            this.a(new ItemStack(Item.INK_SACK, 1, 0), 0.0F);
        }
    }

    public boolean a(EntityHuman entityhuman) {
        return false;
    }

    public boolean g_() {
        return this.world.a(this.boundingBox.b(0.0D, -0.6000000238418579D, 0.0D), Material.WATER, this);
    }

    public void r() {
        super.r();
        this.b = this.a;
        this.f = this.c;
        this.h = this.g;
        this.j = this.i;
        this.g += this.l;
        if (this.g > 6.2831855F) {
            this.g -= 6.2831855F;
            if (this.random.nextInt(10) == 0) {
                this.l = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
            }
        }

        if (this.g_()) {
            float f;

            if (this.g < 3.1415927F) {
                f = this.g / 3.1415927F;
                this.i = MathHelper.a(f * f * 3.1415927F) * 3.1415927F * 0.25F;
                if ((double) f > 0.75D) {
                    this.k = 1.0F;
                    this.m = 1.0F;
                } else {
                    this.m *= 0.8F;
                }
            } else {
                this.i = 0.0F;
                this.k *= 0.9F;
                this.m *= 0.99F;
            }

            if (!this.T) {
                this.motX = (double) (this.n * this.k);
                this.motY = (double) (this.o * this.k);
                this.motZ = (double) (this.p * this.k);
            }

            f = MathHelper.a(this.motX * this.motX + this.motZ * this.motZ);
            this.F += (-((float) Math.atan2(this.motX, this.motZ)) * 180.0F / 3.1415927F - this.F) * 0.1F;
            this.yaw = this.F;
            this.c += 3.1415927F * this.m * 1.5F;
            this.a += (-((float) Math.atan2((double) f, this.motY)) * 180.0F / 3.1415927F - this.a) * 0.1F;
        } else {
            this.i = MathHelper.e(MathHelper.a(this.g)) * 3.1415927F * 0.25F;
            if (!this.T) {
                this.motX = 0.0D;
                this.motY -= 0.08D;
                this.motY *= 0.9800000190734863D;
                this.motZ = 0.0D;
            }

            this.a = (float) ((double) this.a + (double) (-90.0F - this.a) * 0.02D);
        }
    }

    public void a(float f, float f1) {
        this.c(this.motX, this.motY, this.motZ);
    }

    protected void c_() {
        if (this.random.nextInt(50) == 0 || !this.bv || this.n == 0.0F && this.o == 0.0F && this.p == 0.0F) {
            float f = this.random.nextFloat() * 3.1415927F * 2.0F;

            this.n = MathHelper.b(f) * 0.2F;
            this.o = -0.1F + this.random.nextFloat() * 0.2F;
            this.p = MathHelper.a(f) * 0.2F;
        }
    }
}
