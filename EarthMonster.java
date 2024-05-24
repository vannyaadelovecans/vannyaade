import java.util.Random;

public class EarthMonster extends Monster implements ActionInterface  {

    public EarthMonster(String name, int level, int hp, int ep, Element element) {
        super(name, level, hp, ep, element);
    }

    @Override
    public void basicAttack(Monster monster, Monster musuh) {
        if (monster.level < levelsebelumevolusi1) {
            musuh.hp -= 3;
        } else if (monster.level < levelsebelumevolusi2) {
            musuh.hp -= 6;
        } else {
            musuh.hp -= 10;
        }
    }

    public boolean getRandomBooleanWithBias(Random random, double bias) {
        if (bias < 0.0 || bias > 1.0) {
            throw new IllegalArgumentException("Bias harus di antara 0.0 dan 1.0");
        }
        return random.nextDouble() < bias;
    }

    @Override
    public void specialAttack(Monster monster, Monster musuh) {
        Random random = new Random();
        double bias = 0.8;
        boolean result = getRandomBooleanWithBias(random, bias);
            if (result == true) {
                if (monster.level < levelsebelumevolusi1) {
                        musuh.hp -= 15;
                } else if (monster.level < levelsebelumevolusi2) {
                    musuh.hp -= 20; }
                else {
                    musuh.hp -= 30; }
            }
        }

    @Override
    public void elementalAttack(Monster monster, Monster musuh) {
        Element lawan = musuh.element;
        int tambahanserangan = monster.tambahattack;
        if (monster.level < levelsebelumevolusi1) {
            if (lawan == Element.WATER) {
                musuh.hp -= (25 + tambahanserangan);
            } else {
            musuh.hp -= (15 + tambahanserangan); }
        } else if (monster.level < levelsebelumevolusi2) {
            if (lawan == Element.WATER) {
                musuh.hp -= (40 + tambahanserangan);
            } else {
            musuh.hp -= (30 + tambahanserangan); }
        } else {
            if (lawan == Element.WATER) {
                musuh.hp -= (50 + tambahanserangan);
            } else {
            musuh.hp -= (40 + tambahanserangan); }
        }
        monster.tambahattack = 0;
    }

    @Override
    public void evolve(Monster monster) {
        monster.level++;
        monster.hp *= 2;
        Random random = new Random();
        boolean evolusi = random.nextBoolean();
        if (evolusi == true) {
            monster.element = Element.FIRE;
        } else {
            monster.element = Element.ICE;
        }
    }
    
}
