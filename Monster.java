import java.util.Random;

public abstract class Monster{
    protected String name;
    protected int level;
    protected int hp;
    protected int ep;
    protected Element element;
    protected int tambahattack;
    final int levelsebelumevolusi1 = 20;
    final int levelsebelumevolusi2 = 50;
    final int levelsebelumevolusi3 = 80;
    final int epnextlevel = 50;
    final int epevolusi = 100;
    final int maxlevel = 99;

    public Monster(String name, int level, int hp, int ep, Element element){
        this.name = name;
        this.level = level;
        this.hp = hp;
        this.ep = ep;
        this.element = element;
        this.tambahattack = 0;
    }

    public void levelup(Monster monster){
        if ( monster.ep > epnextlevel && monster.level != levelsebelumevolusi1 && monster.level != levelsebelumevolusi2 && monster.level != levelsebelumevolusi3 && monster.level != maxlevel) {
            level++;
        } else if (monster.level == maxlevel) {
            System.out.println("Level berada di level maksimal");
        }
        else if (monster.ep > epevolusi && monster.level == levelsebelumevolusi1 && monster.level == levelsebelumevolusi2 && monster.level == levelsebelumevolusi3 ) {
            evolve(monster);
        } else {
            System.out.println("ep tidak mencukupi");
        }
    }

    public abstract void evolve(Monster monster);
    public void useItem(Item item){
        if (item.name.equals("Health Potion level rendah")) {
            hp += 50;
        } else if (item.name.equals("Health Potion level sedang")) {
            hp += 80;
        } else if (item.name.equals("Health Potion level tinggi")) {
            hp += 150;
        } else if (item.name.equals("Element Potion level rendah")) {
            tambahattack = 10;
        } else if (item.name.equals("Element Potion level sedang")) {
            tambahattack = 25;
        } else if (item.name.equals("Element Potion level tinggi")) {
            tambahattack = 40;
        }
    }

    public void flee(){
        Random random = new Random();
        boolean kabur = random.nextBoolean();
        if (kabur) {
            dungeon();
        }
    }
}