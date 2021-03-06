package net.minecraft.server;

import java.util.List;

public abstract class EntityLiving extends Entity {

    public int maxNoDamageTicks = 20;
    public float D;
    public float E;
    public float F = 0.0F;
    public float G = 0.0F;
    protected float H;
    protected float I;
    protected float J;
    protected float K;
    protected boolean L = true;
    protected String texture = "/mob/char.png";
    protected boolean N = true;
    protected float O = 0.0F;
    protected String P = null;
    protected float Q = 1.0F;
    protected int R = 0;
    protected float S = 0.0F;
    public boolean T = false;
    public float U;
    public float V;
    public int health = 10;
    public int X;
    private int a;
    public int hurtTicks;
    public int Z;
    public float aa = 0.0F;
    public int deathTicks = 0;
    public int attackTicks = 0;
    public float ad;
    public float ae;
    protected boolean af = false;
    public int ag = -1;
    public float ah = (float) (Math.random() * 0.8999999761581421D + 0.10000000149011612D);
    public float ai;
    public float aj;
    public float ak;
    protected int al;
    protected double am;
    protected double an;
    protected double ao;
    protected double ap;
    protected double aq;
    float ar = 0.0F;
    protected int lastDamage = 0;
    protected int at = 0;
    protected float au;
    protected float av;
    protected float aw;
    protected boolean ax = false;
    protected float ay = 0.0F;
    protected float az = 0.7F;
    private Entity b;
    protected int aA = 0;

    public EntityLiving(World world) {
        super(world);
        this.aD = true;
        this.E = (float) (Math.random() + 1.0D) * 0.01F;
        this.a(this.locX, this.locY, this.locZ);
        this.D = (float) Math.random() * 12398.0F;
        this.yaw = (float) (Math.random() * 3.1415927410125732D * 2.0D);
        this.bm = 0.5F;
    }

    protected void a() {}

    public boolean e(Entity entity) {
        return this.world.a(Vec3D.b(this.locX, this.locY + (double) this.q(), this.locZ), Vec3D.b(entity.locX, entity.locY + (double) entity.q(), entity.locZ)) == null;
    }

    public boolean d_() {
        return !this.dead;
    }

    public boolean e_() {
        return !this.dead;
    }

    public float q() {
        return this.width * 0.85F;
    }

    public int c() {
        return 80;
    }

    public void K() {
        String s = this.e();

        if (s != null) {
            this.world.a(this, s, this.i(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
        }
    }

    public void L() {
        this.U = this.V;
        super.L();
        if (this.random.nextInt(1000) < this.a++) {
            this.a = -this.c();
            this.K();
        }

        if (this.N() && this.E()) {
            this.a((Entity) null, 1);
        }

        if (this.by || this.world.isStatic) {
            this.fireTicks = 0;
        }

        int i;

        if (this.N() && this.a(Material.WATER) && !this.b_()) {
            --this.airTicks;
            if (this.airTicks == -20) {
                this.airTicks = 0;

                for (i = 0; i < 8; ++i) {
                    float f = this.random.nextFloat() - this.random.nextFloat();
                    float f1 = this.random.nextFloat() - this.random.nextFloat();
                    float f2 = this.random.nextFloat() - this.random.nextFloat();

                    this.world.a("bubble", this.locX + (double) f, this.locY + (double) f1, this.locZ + (double) f2, this.motX, this.motY, this.motZ);
                }

                this.a((Entity) null, 2);
            }

            this.fireTicks = 0;
        } else {
            this.airTicks = this.maxAirTicks;
        }

        this.ad = this.ae;
        if (this.attackTicks > 0) {
            --this.attackTicks;
        }

        if (this.hurtTicks > 0) {
            --this.hurtTicks;
        }

        if (this.noDamageTicks > 0) {
            --this.noDamageTicks;
        }

        if (this.health <= 0) {
            ++this.deathTicks;
            if (this.deathTicks > 20) {
                this.Q();
                this.D();

                for (i = 0; i < 20; ++i) {
                    double d0 = this.random.nextGaussian() * 0.02D;
                    double d1 = this.random.nextGaussian() * 0.02D;
                    double d2 = this.random.nextGaussian() * 0.02D;

                    this.world.a("explode", this.locX + (double) (this.random.nextFloat() * this.length * 2.0F) - (double) this.length, this.locY + (double) (this.random.nextFloat() * this.width), this.locZ + (double) (this.random.nextFloat() * this.length * 2.0F) - (double) this.length, d0, d1, d2);
                }
            }
        }

        this.K = this.J;
        this.G = this.F;
        this.lastYaw = this.yaw;
        this.lastPitch = this.pitch;
    }

    public void M() {
        for (int i = 0; i < 20; ++i) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            double d3 = 10.0D;

            this.world.a("explode", this.locX + (double) (this.random.nextFloat() * this.length * 2.0F) - (double) this.length - d0 * d3, this.locY + (double) (this.random.nextFloat() * this.width) - d1 * d3, this.locZ + (double) (this.random.nextFloat() * this.length * 2.0F) - (double) this.length - d2 * d3, d0, d1, d2);
        }
    }

    public void o_() {
        super.o_();
        this.H = this.I;
        this.I = 0.0F;
    }

    public void f_() {
        super.f_();
        this.r();
        double d0 = this.locX - this.lastX;
        double d1 = this.locZ - this.lastZ;
        float f = MathHelper.a(d0 * d0 + d1 * d1);
        float f1 = this.F;
        float f2 = 0.0F;

        this.H = this.I;
        float f3 = 0.0F;

        if (f > 0.05F) {
            f3 = 1.0F;
            f2 = f * 3.0F;
            f1 = (float) Math.atan2(d1, d0) * 180.0F / 3.1415927F - 90.0F;
        }

        if (this.V > 0.0F) {
            f1 = this.yaw;
        }

        if (!this.onGround) {
            f3 = 0.0F;
        }

        this.I += (f3 - this.I) * 0.3F;

        float f4;

        for (f4 = f1 - this.F; f4 < -180.0F; f4 += 360.0F) {
            ;
        }

        while (f4 >= 180.0F) {
            f4 -= 360.0F;
        }

        this.F += f4 * 0.3F;

        float f5;

        for (f5 = this.yaw - this.F; f5 < -180.0F; f5 += 360.0F) {
            ;
        }

        while (f5 >= 180.0F) {
            f5 -= 360.0F;
        }

        boolean flag = f5 < -90.0F || f5 >= 90.0F;

        if (f5 < -75.0F) {
            f5 = -75.0F;
        }

        if (f5 >= 75.0F) {
            f5 = 75.0F;
        }

        this.F = this.yaw - f5;
        if (f5 * f5 > 2500.0F) {
            this.F += f5 * 0.2F;
        }

        if (flag) {
            f2 *= -1.0F;
        }

        while (this.yaw - this.lastYaw < -180.0F) {
            this.lastYaw -= 360.0F;
        }

        while (this.yaw - this.lastYaw >= 180.0F) {
            this.lastYaw += 360.0F;
        }

        while (this.F - this.G < -180.0F) {
            this.G -= 360.0F;
        }

        while (this.F - this.G >= 180.0F) {
            this.G += 360.0F;
        }

        while (this.pitch - this.lastPitch < -180.0F) {
            this.lastPitch -= 360.0F;
        }

        while (this.pitch - this.lastPitch >= 180.0F) {
            this.lastPitch += 360.0F;
        }

        this.J += f2;
    }

    protected void b(float f, float f1) {
        super.b(f, f1);
    }

    public void b(int i) {
        if (this.health > 0) {
            this.health += i;
            if (this.health > 20) {
                this.health = 20;
            }

            this.noDamageTicks = this.maxNoDamageTicks / 2;
        }
    }

    public boolean a(Entity entity, int i) {
        if (this.world.isStatic) {
            return false;
        } else {
            this.at = 0;
            if (this.health <= 0) {
                return false;
            } else {
                this.aj = 1.5F;
                boolean flag = true;

                if ((float) this.noDamageTicks > (float) this.maxNoDamageTicks / 2.0F) {
                    if (i <= this.lastDamage) {
                        return false;
                    }

                    this.c(i - this.lastDamage);
                    this.lastDamage = i;
                    flag = false;
                } else {
                    this.lastDamage = i;
                    this.X = this.health;
                    this.noDamageTicks = this.maxNoDamageTicks;
                    this.c(i);
                    this.hurtTicks = this.Z = 10;
                }

                this.aa = 0.0F;
                if (flag) {
                    this.world.a(this, (byte) 2);
                    this.W();
                    if (entity != null) {
                        double d0 = entity.locX - this.locX;

                        double d1;

                        for (d1 = entity.locZ - this.locZ; d0 * d0 + d1 * d1 < 1.0E-4D; d1 = (Math.random() - Math.random()) * 0.01D) {
                            d0 = (Math.random() - Math.random()) * 0.01D;
                        }

                        this.aa = (float) (Math.atan2(d1, d0) * 180.0D / 3.1415927410125732D) - this.yaw;
                        this.a(entity, i, d0, d1);
                    } else {
                        this.aa = (float) ((int) (Math.random() * 2.0D) * 180);
                    }
                }

                if (this.health <= 0) {
                    if (flag) {
                        this.world.a(this, this.g(), this.i(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                    }

                    this.a(entity);
                } else if (flag) {
                    this.world.a(this, this.f(), this.i(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                }

                return true;
            }
        }
    }

    protected void c(int i) {
        this.health -= i;
    }

    protected float i() {
        return 1.0F;
    }

    protected String e() {
        return null;
    }

    protected String f() {
        return "random.hurt";
    }

    protected String g() {
        return "random.hurt";
    }

    public void a(Entity entity, int i, double d0, double d1) {
        float f = MathHelper.a(d0 * d0 + d1 * d1);
        float f1 = 0.4F;

        this.motX /= 2.0D;
        this.motY /= 2.0D;
        this.motZ /= 2.0D;
        this.motX -= d0 / (double) f * (double) f1;
        this.motY += 0.4000000059604645D;
        this.motZ -= d1 / (double) f * (double) f1;
        if (this.motY > 0.4000000059604645D) {
            this.motY = 0.4000000059604645D;
        }
    }

    public void a(Entity entity) {
        if (this.R >= 0 && entity != null) {
            entity.c(this, this.R);
        }

        this.af = true;
        if (!this.world.isStatic) {
            this.p();
        }

        this.world.a(this, (byte) 3);
    }

    protected void p() {
        int i = this.h();

        if (i > 0) {
            int j = this.random.nextInt(3);

            for (int k = 0; k < j; ++k) {
                this.b(i, 1);
            }
        }
    }

    protected int h() {
        return 0;
    }

    protected void a(float f) {
        int i = (int) Math.ceil((double) (f - 3.0F));

        if (i > 0) {
            this.a((Entity) null, i);
            int j = this.world.getTypeId(MathHelper.b(this.locX), MathHelper.b(this.locY - 0.20000000298023224D - (double) this.height), MathHelper.b(this.locZ));

            if (j > 0) {
                StepSound stepsound = Block.byId[j].stepSound;

                this.world.a(this, stepsound.c(), stepsound.a() * 0.5F, stepsound.b() * 0.75F);
            }
        }
    }

    public void a(float f, float f1) {
        double d0;

        if (this.g_()) {
            d0 = this.locY;
            this.a(f, f1, 0.02F);
            this.c(this.motX, this.motY, this.motZ);
            this.motX *= 0.800000011920929D;
            this.motY *= 0.800000011920929D;
            this.motZ *= 0.800000011920929D;
            this.motY -= 0.02D;
            if (this.aW && this.b(this.motX, this.motY + 0.6000000238418579D - this.locY + d0, this.motZ)) {
                this.motY = 0.30000001192092896D;
            }
        } else if (this.V()) {
            d0 = this.locY;
            this.a(f, f1, 0.02F);
            this.c(this.motX, this.motY, this.motZ);
            this.motX *= 0.5D;
            this.motY *= 0.5D;
            this.motZ *= 0.5D;
            this.motY -= 0.02D;
            if (this.aW && this.b(this.motX, this.motY + 0.6000000238418579D - this.locY + d0, this.motZ)) {
                this.motY = 0.30000001192092896D;
            }
        } else {
            float f2 = 0.91F;

            if (this.onGround) {
                f2 = 0.54600006F;
                int i = this.world.getTypeId(MathHelper.b(this.locX), MathHelper.b(this.boundingBox.b) - 1, MathHelper.b(this.locZ));

                if (i > 0) {
                    f2 = Block.byId[i].frictionFactor * 0.91F;
                }
            }

            float f3 = 0.16277136F / (f2 * f2 * f2);

            this.a(f, f1, this.onGround ? 0.1F * f3 : 0.02F);
            f2 = 0.91F;
            if (this.onGround) {
                f2 = 0.54600006F;
                int j = this.world.getTypeId(MathHelper.b(this.locX), MathHelper.b(this.boundingBox.b) - 1, MathHelper.b(this.locZ));

                if (j > 0) {
                    f2 = Block.byId[j].frictionFactor * 0.91F;
                }
            }

            if (this.n()) {
                this.fallDistance = 0.0F;
                if (this.motY < -0.15D) {
                    this.motY = -0.15D;
                }

                if (this.Z() && this.motY < 0.0D) {
                    this.motY = 0.0D;
                }
            }

            this.c(this.motX, this.motY, this.motZ);
            if (this.aW && this.n()) {
                this.motY = 0.2D;
            }

            this.motY -= 0.08D;
            this.motY *= 0.9800000190734863D;
            this.motX *= (double) f2;
            this.motZ *= (double) f2;
        }

        this.ai = this.aj;
        d0 = this.locX - this.lastX;
        double d1 = this.locZ - this.lastZ;
        float f4 = MathHelper.a(d0 * d0 + d1 * d1) * 4.0F;

        if (f4 > 1.0F) {
            f4 = 1.0F;
        }

        this.aj += (f4 - this.aj) * 0.4F;
        this.ak += this.aj;
    }

    public boolean n() {
        int i = MathHelper.b(this.locX);
        int j = MathHelper.b(this.boundingBox.b);
        int k = MathHelper.b(this.locZ);

        return this.world.getTypeId(i, j, k) == Block.LADDER.id || this.world.getTypeId(i, j + 1, k) == Block.LADDER.id;
    }

    public void a(NBTTagCompound nbttagcompound) {
        nbttagcompound.a("Health", (short) this.health);
        nbttagcompound.a("HurtTime", (short) this.hurtTicks);
        nbttagcompound.a("DeathTime", (short) this.deathTicks);
        nbttagcompound.a("AttackTime", (short) this.attackTicks);
    }

    public void b(NBTTagCompound nbttagcompound) {
        this.health = nbttagcompound.d("Health");
        if (!nbttagcompound.b("Health")) {
            this.health = 10;
        }

        this.hurtTicks = nbttagcompound.d("HurtTime");
        this.deathTicks = nbttagcompound.d("DeathTime");
        this.attackTicks = nbttagcompound.d("AttackTime");
    }

    public boolean N() {
        return !this.dead && this.health > 0;
    }

    public boolean b_() {
        return false;
    }

    public void r() {
        if (this.al > 0) {
            double d0 = this.locX + (this.am - this.locX) / (double) this.al;
            double d1 = this.locY + (this.an - this.locY) / (double) this.al;
            double d2 = this.locZ + (this.ao - this.locZ) / (double) this.al;

            double d3;

            for (d3 = this.ap - (double) this.yaw; d3 < -180.0D; d3 += 360.0D) {
                ;
            }

            while (d3 >= 180.0D) {
                d3 -= 360.0D;
            }

            this.yaw = (float) ((double) this.yaw + d3 / (double) this.al);
            this.pitch = (float) ((double) this.pitch + (this.aq - (double) this.pitch) / (double) this.al);
            --this.al;
            this.a(d0, d1, d2);
            this.c(this.yaw, this.pitch);
        }

        if (this.p_()) {
            this.ax = false;
            this.au = 0.0F;
            this.av = 0.0F;
            this.aw = 0.0F;
        } else if (!this.T) {
            this.c_();
        }

        boolean flag = this.g_();
        boolean flag1 = this.V();

        if (this.ax) {
            if (flag) {
                this.motY += 0.03999999910593033D;
            } else if (flag1) {
                this.motY += 0.03999999910593033D;
            } else if (this.onGround) {
                this.I();
            }
        }

        this.au *= 0.98F;
        this.av *= 0.98F;
        this.aw *= 0.9F;
        this.a(this.au, this.av);
        List list = this.world.b((Entity) this, this.boundingBox.b(0.20000000298023224D, 0.0D, 0.20000000298023224D));

        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); ++i) {
                Entity entity = (Entity) list.get(i);

                if (entity.e_()) {
                    entity.h(this);
                }
            }
        }
    }

    protected boolean p_() {
        return this.health <= 0;
    }

    protected void I() {
        this.motY = 0.41999998688697815D;
    }

    protected boolean s() {
        return true;
    }

    protected void c_() {
        ++this.at;
        EntityHuman entityhuman = this.world.a(this, -1.0D);

        if (this.s() && entityhuman != null) {
            double d0 = entityhuman.locX - this.locX;
            double d1 = entityhuman.locY - this.locY;
            double d2 = entityhuman.locZ - this.locZ;
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;

            if (d3 > 16384.0D) {
                this.D();
            }

            if (this.at > 600 && this.random.nextInt(800) == 0) {
                if (d3 < 1024.0D) {
                    this.at = 0;
                } else {
                    this.D();
                }
            }
        }

        this.au = 0.0F;
        this.av = 0.0F;
        float f = 8.0F;

        if (this.random.nextFloat() < 0.02F) {
            entityhuman = this.world.a(this, (double) f);
            if (entityhuman != null) {
                this.b = entityhuman;
                this.aA = 10 + this.random.nextInt(20);
            } else {
                this.aw = (this.random.nextFloat() - 0.5F) * 20.0F;
            }
        }

        if (this.b != null) {
            this.a(this.b, 10.0F, (float) this.n_());
            if (this.aA-- <= 0 || this.b.dead || this.b.g(this) > (double) (f * f)) {
                this.b = null;
            }
        } else {
            if (this.random.nextFloat() < 0.05F) {
                this.aw = (this.random.nextFloat() - 0.5F) * 20.0F;
            }

            this.yaw += this.aw;
            this.pitch = this.ay;
        }

        boolean flag = this.g_();
        boolean flag1 = this.V();

        if (flag || flag1) {
            this.ax = this.random.nextFloat() < 0.8F;
        }
    }

    protected int n_() {
        return 10;
    }

    public void a(Entity entity, float f, float f1) {
        double d0 = entity.locX - this.locX;
        double d1 = entity.locZ - this.locZ;
        double d2;

        if (entity instanceof EntityLiving) {
            EntityLiving entityliving = (EntityLiving) entity;

            d2 = this.locY + (double) this.q() - (entityliving.locY + (double) entityliving.q());
        } else {
            d2 = (entity.boundingBox.b + entity.boundingBox.e) / 2.0D - (this.locY + (double) this.q());
        }

        double d3 = (double) MathHelper.a(d0 * d0 + d1 * d1);
        float f2 = (float) (Math.atan2(d1, d0) * 180.0D / 3.1415927410125732D) - 90.0F;
        float f3 = (float) (Math.atan2(d2, d3) * 180.0D / 3.1415927410125732D);

        this.pitch = -this.b(this.pitch, f3, f1);
        this.yaw = this.b(this.yaw, f2, f);
    }

    public boolean O() {
        return this.b != null;
    }

    public Entity P() {
        return this.b;
    }

    private float b(float f, float f1, float f2) {
        float f3;

        for (f3 = f1 - f; f3 < -180.0F; f3 += 360.0F) {
            ;
        }

        while (f3 >= 180.0F) {
            f3 -= 360.0F;
        }

        if (f3 > f2) {
            f3 = f2;
        }

        if (f3 < -f2) {
            f3 = -f2;
        }

        return f + f3;
    }

    public void Q() {}

    public boolean b() {
        return this.world.a(this.boundingBox) && this.world.a((Entity) this, this.boundingBox).size() == 0 && !this.world.b(this.boundingBox);
    }

    protected void R() {
        this.a((Entity) null, 4);
    }

    public Vec3D S() {
        return this.b(1.0F);
    }

    public Vec3D b(float f) {
        float f1;
        float f2;
        float f3;
        float f4;

        if (f == 1.0F) {
            f1 = MathHelper.b(-this.yaw * 0.017453292F - 3.1415927F);
            f2 = MathHelper.a(-this.yaw * 0.017453292F - 3.1415927F);
            f3 = -MathHelper.b(-this.pitch * 0.017453292F);
            f4 = MathHelper.a(-this.pitch * 0.017453292F);
            return Vec3D.b((double) (f2 * f3), (double) f4, (double) (f1 * f3));
        } else {
            f1 = this.lastPitch + (this.pitch - this.lastPitch) * f;
            f2 = this.lastYaw + (this.yaw - this.lastYaw) * f;
            f3 = MathHelper.b(-f2 * 0.017453292F - 3.1415927F);
            f4 = MathHelper.a(-f2 * 0.017453292F - 3.1415927F);
            float f5 = -MathHelper.b(-f1 * 0.017453292F);
            float f6 = MathHelper.a(-f1 * 0.017453292F);

            return Vec3D.b((double) (f4 * f5), (double) f6, (double) (f3 * f5));
        }
    }

    public int j() {
        return 4;
    }

    public boolean F() {
        return false;
    }
}
