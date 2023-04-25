import necesse.engine.Screen;
import necesse.engine.network.PacketReader;
import necesse.engine.registries.DamageTypeRegistry;
import necesse.engine.sound.SoundEffect;
import necesse.entity.mobs.AttackAnimMob;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.attackHandler.DeathRipperAttackHandler;
import necesse.entity.projectile.Projectile;
import necesse.entity.projectile.bulletProjectile.BulletProjectile;
import necesse.entity.projectile.bulletProjectile.SniperBulletProjectile;
import necesse.gfx.GameResources;
import necesse.gfx.gameFont.FontManager;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.InventoryItem;
import necesse.inventory.PlayerInventorySlot;
import necesse.inventory.item.toolItem.projectileToolItem.gunProjectileToolItem.GunProjectileToolItem;
import necesse.level.maps.Level;

public class DeadMansTale extends GunProjectileToolItem {
    private int roundCount;
    private int magSize;
    private long reloadStartTime;
    private long reloadLength;

    private boolean reloading;

    public DeadMansTale(){
        super(NORMAL_AMMO_TYPES, 1000);
        this.rarity = Rarity.EPIC;
        this.animSpeed = 400;
        this.attackDmg = new GameDamage(DamageTypeRegistry.RANGED, 28.0F);
        this.cooldown = 100;
        this.attackXOffset = 8;
        this.attackYOffset = 10;
        this.attackRange = 1600;
        this.velocity = 650;

        this.roundCount = 0;
        this.magSize = 14;
        this.reloadLength = 100000L;
        this.reloading = false;
    }

    public void playFireSound(AttackAnimMob mob){
        Screen.playSound(GameResources.sniperrifle, SoundEffect.effect(mob));
    }

    private boolean isReloading(PlayerMob player){
        if(this.reloading){
            this.reloading = false;
            return true;
        }
        return false;
        //return (player.getWorldEntity().getWorldTime() - this.reloadStartTime) > this.reloadLength;
    }

    public Projectile getNormalProjectile(float x, float y, float targetX, float targetY, float velocity, int range, GameDamage toolItemDamage, int knockback, Mob owner) {
        return new SniperBulletProjectile(x, y, targetX, targetY, velocity, range, toolItemDamage, knockback, owner);
    }

    public String canAttack(Level level, int x, int y, PlayerMob player, InventoryItem item) {
        return this.getAvailableAmmo(player) > 0 && !this.isReloading(player) ? null : "";
    }

    public InventoryItem onAttack(Level level, int x, int y, PlayerMob player, int attackHeight, InventoryItem item, PlayerInventorySlot slot, int animAttack, int seed, PacketReader contentReader) {
        if(this.canAttack(level, x, y, player, item) != null){
            return item;
        }

        this.roundCount++;
        if(this.roundCount > this.magSize) {
            this.reloading = true;
            this.reloadStartTime = player.getWorldEntity().getWorldTime();
            this.roundCount = 0;
            return item; //THIS IS THE ONLY THING HERE THAT EVEN KINDA WORKS :(
        }
        return super.onAttack(level, x, y, player, attackHeight, item, slot, animAttack, seed, contentReader);
    }

    @Override
    protected void loadItemTextures() {
        this.itemTexture = GameTexture.fromFile("items/antiquerifle");
    }
}
