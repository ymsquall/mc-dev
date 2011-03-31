package net.minecraft.server;

public class ItemSword extends Item {

    private int a;

    public ItemSword(int i, EnumToolMaterial enumtoolmaterial) {
        super(i);
        this.maxStackSize = 1;
        this.d(enumtoolmaterial.a());
        this.a = 4 + enumtoolmaterial.c() * 2;
    }

    public float a(ItemStack itemstack, Block block) {
        return 1.5F;
    }

    public boolean a(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1) {
        itemstack.a(1, entityliving1);
        return true;
    }

    public boolean a(ItemStack itemstack, int i, int j, int k, int l, EntityLiving entityliving) {
        itemstack.a(2, entityliving);
        return true;
    }

    public int a(Entity entity) {
        return this.a;
    }
}
