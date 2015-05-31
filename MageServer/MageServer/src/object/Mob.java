package object;
public interface Mob {
	
	public static final int ExplosionDamage=237839;
	public static final int ElectricShock=298147;
	public static final int PlainDamage=9726358;
	
	void Die();
	
	void TakeDamage(int Damage,int Type);
	
}
