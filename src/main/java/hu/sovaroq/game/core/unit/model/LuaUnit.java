package hu.sovaroq.game.core.unit.model;

import lombok.AllArgsConstructor;

/**
 * Created by balazs_horvath on 3/14/2017.
 */
@AllArgsConstructor
public class LuaUnit {
    private int id;
    private double x, y;
    private double speed;
    private int hp, attack;

    public int id() {
        return id;
    }

    public void id(int id) {
        this.id = id;
    }

    public double x() {
        return x;
    }

    public void x(double x) {
        this.x = x;
    }

    public double y() {
        return y;
    }

    public void y(double y) {
        this.y = y;
    }

    public int hp() {
        return hp;
    }

    public void hp(int hp) {
        this.hp = hp;
    }

    public int attack() {
        return attack;
    }

    public void attackttack(int attack) {
        this.attack = attack;
    }

    public double speed() {
        return speed;
    }

    public void speed(double speed) {
        this.speed = speed;
    }
}
