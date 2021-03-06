package rpg;

import java.util.*;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.ImageIcon;

/**
 * 
 * @author Jeff
 */
public class Entity extends RPGObject implements Comparable<Entity> {

	private static final long serialVersionUID = 1L;
	private int maxHealth;
	private int currentHealth;
	private int maxMana;
	private int currentMana;
	private int attack;
	private int defense;
	private int speed;
	protected boolean hasMoved = true; // for Grid Movement only
	private String behaviorType; 
	private String name;
	// is this entity object a player
	public boolean isPlayer = false;
	// experience value for monsters, accumulated experience for players
	private int exp;
    private int level;
	private int levelUpPoints;
	ArrayList<Ability> abilities = new ArrayList<Ability>();
	ArrayList<Ability> fullAbilities = new ArrayList<Ability>();

	private Item equippedItem; // for now they may only have one equipped item
								// at a time
	
	private Random randomNumberGenerator = new Random();

	public Entity(int id, ImageIcon image, String name, String behaviorType, boolean isPlayer,
			Item equippedItem, int currentHealth, int maxHealth,
			int currentMana, int maxMana, int attack, int defense, int speed,
			int points, int level) {

		this.id = id;
		this.image = image;
		this.equippedItem = equippedItem;
		this.maxHealth = maxHealth;
		this.currentHealth = currentHealth;
		this.maxMana = maxMana;
		this.currentMana = currentMana;
		this.attack = attack;
		this.defense = defense;
		this.speed = speed;
		this.levelUpPoints = points;
		this.name = name;
		this.isPlayer = isPlayer;
        this.level = level;
        this.behaviorType = behaviorType;
	}

	/**Adds ability to entity. If character is high enough level,
	 * unlocks the ability
	 * @param a
	 */
	public void addAbility(Ability a){
		this.fullAbilities.add(a);
		if(a.getLevel() <= this.getLevel()){
			a.unlock();
			this.abilities.add(a);
		}
	}
	
	/**Called after character levels up to unlock new abilities
	 * if characters level is high enough.
	 * @return
	 * returns String arraylist of newly learned ability names
	 */
	public ArrayList<String> unlockAbilities(){
		ArrayList<String> unlockedAbilityNames = new ArrayList<String>();
		for(Ability a : this.fullAbilities){
			if((a.getLevel() <= this.getLevel()) && a.isLocked()){
				a.unlock();
				this.abilities.add(a);
				unlockedAbilityNames.add(a.getName());
			}
		}
		return unlockedAbilityNames;
	}
	
	public boolean hasHealingAbility() {
		boolean returnVal = false;
		if (!this.abilities.isEmpty()) {
			for (Ability a : this.abilities) {
				if (a.getType() == 1) {
					returnVal = true;
				}
			}
		}
		return returnVal;
	}

	public boolean alive() {
		if (this.getCurrentHealth() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public int compareTo(Entity other) {
		return (getSpeed() - other.getSpeed());
	}

	/**
	 * @return the attack
	 */
	public int getAttack() {
		if(equippedItem != null)
			return attack + equippedItem.getAttack();
		return attack;
	}

	/**
	 * @param equipt
	 *            item the item equipped
	 * 
	 */
	public void setEquippedItem(Item item) 
	{
		if(item!=null)
		{
			if(!item.isConsumable())
			{
				
				this.equippedItem = item;
				
			}
		}
		else{
			this.equippedItem = null;
		}
		
	}
	
	public void useItem(Item item)
	{
		if(item.isConsumable())
		{
		// apply modifiers to the entity but do not replace equipped item
					maxHealth += item.getMaxHealth();
					setCurrentHealth(currentHealth += item.getCurrentHealth());
					maxMana += item.getMaxMana();
					setCurrentMana(currentMana += item.getCurrentMana());
					attack += item.getAttack();
					defense += item.getDefense();
					speed += item.getSpeed();
		}		
	}
	
	public int getBaseHealth()
	{
		return this.maxHealth;
	}
	
	public int getBaseMana()
	{
		return this.maxMana;
	}
	
	public int getBaseAttack()
	{
		return this.attack;
	}
	
	public int getBaseDefense()
	{
		return this.defense;
	}
	
	public int getBaseSpeed()
	{
		return this.speed;
	}

	/**
	 * @param attack
	 *            the attack to set
	 */
	public void setAttack(int attack) {
		this.attack = attack;
	}

	/**
	 * @return the defense
	 */
	public int getDefense() {
		if(equippedItem != null)
			return defense + equippedItem.getDefense();
		return defense;
	}

	/**
	 * @param defense
	 *            the defense to set
	 */
	public void setDefense(int defense) {
		this.defense = defense;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the speed
	 */
	public int getSpeed() {
		if(equippedItem != null)
			return speed + equippedItem.getSpeed();
		return speed;
	}

	/**
	 * @param speed
	 *            the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * @return the maxHealth
	 */
	public int getMaxHealth() {
		if(equippedItem != null)
			return maxHealth + equippedItem.getMaxHealth();
		return maxHealth;
	}

	/**
	 * @param maxHealth
	 *            the maxHealth to set
	 */
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	/**
	 * @return the currentHealth
	 */
	public int getCurrentHealth() {
		if(equippedItem != null)
			return currentHealth + equippedItem.getCurrentHealth();
		return currentHealth;
	}

	/**
	 * @return the equipped item
	 */
	public Item getEquipped() {
		return this.equippedItem;
	}

	/**
	 * @param currentHealth
	 *            the currentHealth to set
	 */
	public void setCurrentHealth(int currentHealth) {
		if(currentHealth >= this.getMaxHealth())
			this.currentHealth = this.getMaxHealth();
		else if(currentHealth <= 0)
			this.currentHealth = 0;
		else	
			this.currentHealth = currentHealth;
	}

	public Ability getAbilityByName(String name) {
		Ability returnVal = null;
		for (Ability a : abilities) {
			if (a.getName().equals(name)) {
				returnVal = a;
			}
		}
		return returnVal;

	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public void incExp(int exp) {
		this.exp += exp;
	}

	public int getMaxMana() {
		if(equippedItem != null)
			return maxMana + equippedItem.getMaxMana();
		return maxMana;
	}

	public void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
	}

	public int getCurrentMana() {
		if(equippedItem != null)
			return currentMana + equippedItem.getCurrentMana();
		return currentMana;
	}

	public void setCurrentMana(int currentMana) {
		if(currentMana >= this.getMaxMana())
			this.currentMana = this.getMaxMana();
		else if(currentMana <= 0)
			this.currentMana = 0;
		else	
			this.currentMana = currentMana;
	}

	public int getLevelUpPoints() {
		return levelUpPoints;
	}

	void setLevelUpPoints(int points) {
		this.levelUpPoints = points;
	}

	int getLevel() {
		return level;
	}
	
	void incLevel(){
		level++;
	}
	
	//returns random healing ability
	public Ability findHealingAbility(){
		Ability returnVal = null;
		ArrayList<Ability> found = new ArrayList<Ability>();
		
		for(Ability a : abilities){
			if(a.friendly()){
				found.add(a);
			}
		}
		
		if(!found.isEmpty()){
			int random = randomNumberGenerator.nextInt(found.size());
			returnVal = found.get(random);
		}
		return returnVal;
	}
	
	//returns random damaging ability
	public Ability findDamagingAbility(){
		Ability returnVal = null;
		ArrayList<Ability> found = new ArrayList<Ability>();
		
		for(Ability a : abilities){
			if(!a.friendly()){
				found.add(a);
			}
		}
		
		if(!found.isEmpty()){
			int random = randomNumberGenerator.nextInt(found.size());
			returnVal = found.get(random);
		}
		return returnVal;
	}
	
	/**
	 * There are many different types of behavior. Behavior determines
	 * the movement on the grid, and the attack style in battle for
	 * enemies. Players are not affected by behaviors.
	 * @return behaviorType
	 */
	public String getBehaviorType()
	{
		return this.behaviorType;
	}
	
	/**
	 * There are many different types of behavior. Behavior determines
	 * the movement on the grid, and the attack style in battle for
	 * enemies. Players are not affected by behaviors.
	 * @param type
	 */
	public void setBehaviorType(String type)
	{
		this.behaviorType = type;
	}
}
