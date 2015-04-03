package JavaFiles.Characters;

import java.util.List;

import JavaFiles.Characters.Character;

/**
 * Created by AlexC on 3/10/2015.
 */
public class Effect {
    private int base_damage;
    private List<Character> status_effects;

    // constructor for an instance of type
    public Effect(int base_damage, List<Character> status_effects) {
        this.base_damage = base_damage;
        this.status_effects = status_effects;
    }

    // get the base damage of this attack
    public int getBase_damage() {
        return this.base_damage;
    }

    // get the status effects that this attack inflicts
    public List<Character> getStatus_effects() {
        return this.status_effects;
    }

    // change the damage of this attack
    public void setBase_damage(int base_damage)
    {
        this.base_damage = base_damage;
    }
}
