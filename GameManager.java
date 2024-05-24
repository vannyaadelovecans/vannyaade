import java.io.*;
import java.util.Random;
import java.util.Scanner;

abstract class abstractMonster {
    private String name;
    protected int level;
    protected int hp;
    private int ep;
    protected Element element;
    private int tambahAttack;

    final int levelSebelumEvolusi1 = 20;
    final int levelSebelumEvolusi2 = 50;
    final int levelSebelumEvolusi3 = 80;
    final int epNextLevel = 50;
    final int epEvolusi = 100;
    final int maxLevel = 99;

    public abstractMonster(String name, int level, int hp, int ep, Element element) {
        this.name = name;
        this.level = level;
        this.hp = hp;
        this.ep = ep;
        this.element = element;
        this.tambahAttack = 0;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getEp() {
        return ep;
    }

    public void setEp(int ep) {
        this.ep = ep;
    }

    public Element getElement() {
        return element;
    }

    public int getTambahAttack() {
        return tambahAttack;
    }

    public void setTambahAttack(int tambahAttack) {
        this.tambahAttack = tambahAttack;
    }

    public void increaseEp(int amount) {
        this.ep += amount;
        if (this.ep >= epEvolusi && this.level < maxLevel) {
            evolve();
        }
        if (this.ep >= epNextLevel && this.level < maxLevel) {
            levelUp();
        }
    }

    public void levelUp() {
        if (ep > epNextLevel && level != levelSebelumEvolusi1 && level != levelSebelumEvolusi2 && level != levelSebelumEvolusi3 && level != maxLevel) {
            level++;
        } else if (level == maxLevel) {
            System.out.println("Level berada di level maksimal");
        } else if (ep > epEvolusi && (level == levelSebelumEvolusi1 || level == levelSebelumEvolusi2 || level == levelSebelumEvolusi3)) {
            evolve();
        } else {
            System.out.println("EP tidak mencukupi");
        }
    }

    public abstract void evolve();

    public void receiveDamage(int damage) {
        this.hp -= damage;
        if (this.hp < 0) {
            this.hp = 0;
        }
        System.out.println(this.name + " received " + damage + " damage. Remaining HP: " + this.hp);
    }

    public void heal(int amount) {
        this.hp += amount;
        System.out.println(this.name + " healed " + amount + " HP. Current HP: " + this.hp);
    }

    public void useItem(Item item) {
        if (item.getName().equals("Health Potion level rendah")) {
            hp += 50;
        } else if (item.getName().equals("Health Potion level sedang")) {
            hp += 80;
        } else if (item.getName().equals("Health Potion level tinggi")) {
            hp += 150;
        } else if (item.getName().equals("Element Potion level rendah")) {
            tambahAttack = 10;
        } else if (item.getName().equals("Element Potion level sedang")) {
            tambahAttack = 25;
        } else if (item.getName().equals("Element Potion level tinggi")) {
            tambahAttack = 40;
        }
    }

    public void flee() {
        Random random = new Random();
        boolean kabur = random.nextBoolean();
        if (kabur) {
            System.out.println("Berhasil melarikan diri!");
        } else {
            System.out.println("Gagal melarikan diri!");
        }
    }

    @Override
    public String toString() {
        return "Monster Name: " + name + "\n" +
                "Level: " + level + "\n" +
                "HP: " + hp + "\n" +
                "EP: " + ep + "\n" +
                "Element: " + element + "\n" +
                "Attack Bonus: " + tambahAttack;
    }

    interface AttackInterface {
        void basicAttack(abstractMonster monster, abstractMonster musuh);

        void specialAttack(abstractMonster monster, abstractMonster musuh);

        void elementalAttack(abstractMonster monster, abstractMonster musuh);
    }

    static class CustomException extends Exception {
        public CustomException(String message) {
            super(message);
        }
    }

    static class FireMonster extends abstractMonster implements AttackInterface {

        public FireMonster(String name, int level, int hp, int ep, Element element) {
            super(name, level, hp, ep, element);
        }

        @Override
        public void basicAttack(abstractMonster monster, abstractMonster musuh) {
            if (monster.getLevel() < levelSebelumEvolusi1) {
                musuh.setHp(musuh.getHp() - 3);
            } else if (monster.getLevel() < levelSebelumEvolusi2) {
                musuh.setHp(musuh.getHp() - 6);
            } else {
                musuh.setHp(musuh.getHp() - 10);
            }
        }

        public boolean getRandomBooleanWithBias(Random random, double bias) {
            if (bias < 0.0 || bias > 1.0) {
                throw new IllegalArgumentException("Bias harus di antara 0.0 dan 1.0");
            }
            return random.nextDouble() < bias;
        }

        @Override
        public void specialAttack(abstractMonster monster, abstractMonster musuh) {
            Random random = new Random();
            double bias = 0.8;
            boolean result = getRandomBooleanWithBias(random, bias);
            if (result) {
                if (monster.getLevel() < levelSebelumEvolusi1) {
                    musuh.setHp(musuh.getHp() - 15);
                } else if (monster.getLevel() < levelSebelumEvolusi2) {
                    musuh.setHp(musuh.getHp() - 20);
                } else {
                    musuh.setHp(musuh.getHp() - 30);
                }
                monster.setHp(monster.getHp() - (int) (0.2 * monster.getHp()));
            }
        }

        @Override
        public void elementalAttack(abstractMonster monster, abstractMonster musuh) {
            Element lawan = musuh.getElement();
            int tambahanSerangan = monster.getTambahAttack();
            if (monster.getLevel() < levelSebelumEvolusi1) {
                if (lawan == Element.ICE) {
                    musuh.setHp(musuh.getHp() - (30 + tambahanSerangan));
                } else {
                    musuh.setHp(musuh.getHp() - (20 + tambahanSerangan));
                }
            } else if (monster.getLevel() < levelSebelumEvolusi2) {
                if (lawan == Element.ICE) {
                    musuh.setHp(musuh.getHp() - (45 + tambahanSerangan));
                } else {
                    musuh.setHp(musuh.getHp() - (35 + tambahanSerangan));
                }
            } else {
                if (lawan == Element.ICE) {
                    musuh.setHp(musuh.getHp() - (55 + tambahanSerangan));
                } else {
                    musuh.setHp(musuh.getHp() - (45 + tambahanSerangan));
                }
            }
            monster.setTambahAttack(0);
        }

        @Override
        public void evolve() {
            level++;
            hp *= 2;
            Random random = new Random();
            boolean evolusi = random.nextBoolean();
            if (evolusi) {
                element = Element.WIND;
            } else {
                element = Element.EARTH;
            }
        }
    }

    static class WaterMonster extends abstractMonster implements AttackInterface {

        public WaterMonster(String name, int level, int hp, int ep, Element element) {
            super(name, level, hp, ep, element);
        }

        @Override
        public void basicAttack(abstractMonster monster, abstractMonster musuh) {
            if (monster.getLevel() < levelSebelumEvolusi1) {
                musuh.setHp(musuh.getHp() - 3);
            } else if (
                    monster.getLevel() < levelSebelumEvolusi2) {
                musuh.setHp(musuh.getHp() - 6);
            } else {
                musuh.setHp(musuh.getHp() - 10);
            }
        }

        public boolean getRandomBooleanWithBias(Random random, double bias) {
            if (bias < 0.0 || bias > 1.0) {
                throw new IllegalArgumentException("Bias harus di antara 0.0 dan 1.0");
            }
            return random.nextDouble() < bias;
        }

        @Override
        public void specialAttack(abstractMonster monster, abstractMonster musuh) {
            Random random = new Random();
            double bias = 0.8;
            boolean result = getRandomBooleanWithBias(random, bias);
            if (result) {
                if (monster.getLevel() < levelSebelumEvolusi1) {
                    musuh.setHp(musuh.getHp() - 12);
                } else if (monster.getLevel() < levelSebelumEvolusi2) {
                    musuh.setHp(musuh.getHp() - 17);
                } else {
                    musuh.setHp(musuh.getHp() - 27);
                }
                monster.setHp(monster.getHp() - (int) (0.2 * monster.getHp()));
            }
        }

        @Override
        public void elementalAttack(abstractMonster monster, abstractMonster musuh) {
            Element lawan = musuh.getElement();
            int tambahanSerangan = monster.getTambahAttack();
            if (monster.getLevel() < levelSebelumEvolusi1) {
                if (lawan == Element.FIRE) {
                    musuh.setHp(musuh.getHp() - (27 + tambahanSerangan));
                } else {
                    musuh.setHp(musuh.getHp() - (17 + tambahanSerangan));
                }
            } else if (monster.getLevel() < levelSebelumEvolusi2) {
                if (lawan == Element.FIRE) {
                    musuh.setHp(musuh.getHp() - (42 + tambahanSerangan));
                } else {
                    musuh.setHp(musuh.getHp() - (32 + tambahanSerangan));
                }
            } else {
                if (lawan == Element.FIRE) {
                    musuh.setHp(musuh.getHp() - (52 + tambahanSerangan));
                } else {
                    musuh.setHp(musuh.getHp() - (42 + tambahanSerangan));
                }
            }
            monster.setTambahAttack(0);
        }

        @Override
        public void evolve() {
            level++;
            hp *= 2;
            Random random = new Random();
            boolean evolusi = random.nextBoolean();
            if (evolusi) {
                element = Element.WIND;
            } else {
                element = Element.ICE;
            }
        }
    }

    static class IceMonster extends abstractMonster implements AttackInterface {

        public IceMonster(String name, int level, int hp, int ep, Element element) {
            super(name, level, hp, ep, element);
        }

        @Override
        public void basicAttack(abstractMonster monster, abstractMonster musuh) {
            if (monster.getLevel() < levelSebelumEvolusi1) {
                musuh.setHp(musuh.getHp() - 3);
            } else if (monster.getLevel() < levelSebelumEvolusi2) {
                musuh.setHp(musuh.getHp() - 6);
            } else {
                musuh.setHp(musuh.getHp() - 10);
            }
        }

        public boolean getRandomBooleanWithBias(Random random, double bias) {
            if (bias < 0.0 || bias > 1.0) {
                throw new IllegalArgumentException("Bias harus di antara 0.0 dan 1.0");
            }
            return random.nextDouble() < bias;
        }

        @Override
        public void specialAttack(abstractMonster monster, abstractMonster musuh) {
            Random random = new Random();
            double bias = 0.8;
            boolean result = getRandomBooleanWithBias(random, bias);
            if (result) {
                if (monster.getLevel() < levelSebelumEvolusi1) {
                    musuh.setHp(musuh.getHp() - 15);
                } else if (monster.getLevel() < levelSebelumEvolusi2) {
                    musuh.setHp(musuh.getHp() - 20);
                } else {
                    musuh.setHp(musuh.getHp() - 30);
                }
                monster.setHp(monster.getHp() - (int) (0.2 * monster.getHp()));
            }
        }

        @Override
        public void elementalAttack(abstractMonster monster, abstractMonster musuh) {
            Element lawan = musuh.getElement();
            int tambahanSerangan = monster.getTambahAttack();
            if (monster.getLevel() < levelSebelumEvolusi1) {
                if (lawan == Element.WIND) {
                    musuh.setHp(musuh.getHp() - (30 + tambahanSerangan));
                } else {
                    musuh.setHp(musuh.getHp() - (20 + tambahanSerangan));
                }
            } else if (monster.getLevel() < levelSebelumEvolusi2) {
                if (lawan == Element.WIND) {
                    musuh.setHp(musuh.getHp() - (45 + tambahanSerangan));
                } else {
                    musuh.setHp(musuh.getHp() - (35 + tambahanSerangan));
                }
            } else {
                if (lawan == Element.WIND) {
                    musuh.setHp(musuh.getHp() - (55 + tambahanSerangan));
                } else {
                    musuh.setHp(musuh.getHp() - (45 + tambahanSerangan));
                }
            }
            monster.setTambahAttack(0);
        }

        @Override
        public void evolve() {
            level++;
            hp *= 2;
            Random random = new Random();
            boolean evolusi = random.nextBoolean();
            if (evolusi) {
                element = Element.WATER;
            } else {
                element = Element.EARTH;
            }
        }
    }

    static class EarthMonster extends abstractMonster implements AttackInterface {

        public EarthMonster(String name, int level, int hp, int ep, Element element) {
            super(name, level, hp, ep, element);
        }

        @Override
        public void basicAttack(abstractMonster monster, abstractMonster musuh) {
            if (monster.getLevel() < levelSebelumEvolusi1) {
                musuh.setHp(musuh.getHp() - 3);
            } else if (monster.getLevel() < levelSebelumEvolusi2) {
                musuh.setHp(musuh.getHp() - 6);
            } else {
                musuh.setHp(musuh.getHp() - 10);
            }
        }

        public boolean getRandomBooleanWithBias(Random random, double bias) {
            if (bias < 0.0 || bias > 1.0) {
                throw new IllegalArgumentException("Bias harus di antara 0.0 dan 1.0");
            }
            return random.nextDouble() < bias;
        }

        @Override
        public void specialAttack(abstractMonster monster, abstractMonster musuh) {
            Random random = new Random();
            double bias = 0.8;
            boolean result = getRandomBooleanWithBias(random,bias);
            if (result) {
                if (monster.getLevel() < levelSebelumEvolusi1) {
                    musuh.setHp(musuh.getHp() - 15);
                } else if (monster.getLevel() < levelSebelumEvolusi2) {
                    musuh.setHp(musuh.getHp() - 20);
                } else {
                    musuh.setHp(musuh.getHp() - 30);
                }
                monster.setHp(monster.getHp() - (int) (0.2 * monster.getHp()));
            }
        }

        @Override
        public void elementalAttack(abstractMonster monster, abstractMonster musuh) {
            Element lawan = musuh.getElement();
            int tambahanSerangan = monster.getTambahAttack();
            if (monster.getLevel() < levelSebelumEvolusi1) {
                if (lawan == Element.WATER) {
                    musuh.setHp(musuh.getHp() - (25 + tambahanSerangan));
                } else {
                    musuh.setHp(musuh.getHp() - (15 + tambahanSerangan));
                }
            } else if (monster.getLevel() < levelSebelumEvolusi2) {
                if (lawan == Element.WATER) {
                    musuh.setHp(musuh.getHp() - (40 + tambahanSerangan));
                } else {
                    musuh.setHp(musuh.getHp() - (30 + tambahanSerangan));
                }
            } else {
                if (lawan == Element.WATER) {
                    musuh.setHp(musuh.getHp() - (50 + tambahanSerangan));
                } else {
                    musuh.setHp(musuh.getHp() - (40 + tambahanSerangan));
                }
            }
            monster.setTambahAttack(0);
        }

        @Override
        public void evolve() {
            level++;
            hp *= 2;
            Random random = new Random();
            boolean evolusi = random.nextBoolean();
            if (evolusi) {
                element = Element.FIRE;
            } else {
                element = Element.ICE;
            }
        }
    }

    static class WindMonster extends abstractMonster implements AttackInterface {

        public WindMonster(String name, int level, int hp, int ep, Element element) {
            super(name, level, hp, ep, element);
        }

        @Override
        public void basicAttack(abstractMonster monster, abstractMonster musuh) {
            if (monster.getLevel() < levelSebelumEvolusi1) {
                musuh.setHp(musuh.getHp() - 3);
            } else if (monster.getLevel() < levelSebelumEvolusi2) {
                musuh.setHp(musuh.getHp() - 6);
            } else {
                musuh.setHp(musuh.getHp() - 10);
            }
        }

        public boolean getRandomBooleanWithBias(Random random, double bias) {
            if (bias < 0.0 || bias > 1.0) {
                throw new IllegalArgumentException("Bias harus di antara 0.0 dan 1.0");
            }
            return random.nextDouble() < bias;
        }

        @Override
        public void specialAttack(abstractMonster monster, abstractMonster musuh) {
            Random random = new Random();
            double bias = 0.8;
            boolean result = getRandomBooleanWithBias(random, bias);
            if (result) {
                if (monster.getLevel() < levelSebelumEvolusi1) {
                    musuh.setHp(musuh.getHp() - 15);
                } else if (monster.getLevel() < levelSebelumEvolusi2) {
                    musuh.setHp(musuh.getHp() - 20);
                } else {
                    musuh.setHp(musuh.getHp() - 30);
                }
                monster.setHp(monster.getHp() - (int) (0.2 * monster.getHp()));
            }
        }

        @Override
        public void elementalAttack(abstractMonster monster, abstractMonster musuh) {
            Element lawan = musuh.getElement();
            int tambahanSerangan = monster.getTambahAttack();
            if (monster.getLevel() < levelSebelumEvolusi1) {
                if (lawan == Element.EARTH) {
                    musuh.setHp(musuh.getHp() - (30 + tambahanSerangan));
                } else {
                    musuh.setHp(musuh.getHp() - (20 + tambahanSerangan));
                }
            } else if (monster.getLevel() < levelSebelumEvolusi2) {
                if (lawan == Element.EARTH) {
                    musuh.setHp(musuh.getHp() - (45 + tambahanSerangan));
                } else {
                    musuh.setHp(musuh.getHp() - (35 + tambahanSerangan));
                }
            } else {
                if (lawan == Element.EARTH) {
                    musuh.setHp(musuh.getHp() - (55 + tambahanSerangan));
                } else {
                    musuh.setHp(musuh.getHp() - (45 + tambahanSerangan));
                }
            }
            monster.setTambahAttack(0);
        }

        @Override
        public void evolve() {
            level++;
            hp *= 2;
            Random random = new Random();
            boolean evolusi = random.nextBoolean();
            if (evolusi) {
                element = Element.FIRE;
            } else {
                element = Element.WATER;
            }
        }
    }

    // Kelas Item
    static class Item {
        private String name;
        private ItemType type;

        public Item(String name, ItemType type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public ItemType getType() {
            return type;
        }
    }

    enum ItemType {
        HEALTH_POTION, ELEMENTAL_POTION, OTHER
    }

    enum Element {
        FIRE, WIND, WATER, ICE, EARTH
    }


    static class GameManager {
        private static GameManager instance;
        private abstractMonster playerMonster;
        private abstractMonster enemyMonster;
        private Scanner scanner;

        private GameManager() {
            scanner = new Scanner(System.in);
        }

        public static GameManager getInstance() {
            if (instance == null) {
                instance = new GameManager();
            }
            return instance;
        }

        public void startGame() {
            loadGame();
            showMenu();
        }

        private void showMenu() {
            System.out.println("=== Pokemon Games ===");
            System.out.println("1. Mulai Pertarungan");
            System.out.println("2. Simpan Progress");
            System.out.println("3. Keluar");
            System.out.print("Pilihan: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    initializeBattle();
                    battle();
                    break;
                case 2:
                    saveGame();
                    break;
                case 3:
                    System.out.println("Terima kasih telah bermain!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
                    showMenu();
                    break;
            }
        }

        private void initializeBattle() {
            playerMonster = new FireMonster("Charizard", 50, 2000, 100, Element.FIRE);
            enemyMonster = new WaterMonster("Blastoise", 45, 1800, 80, Element.WATER);
        }

        public void battle() {
            boolean playerTurn = true;
            Scanner scanner = new Scanner(System.in);

            while (playerMonster.getHp() > 0 && enemyMonster.getHp() > 0) {
                if (playerTurn) {
                    playerTurn(scanner);
                    playerTurn = false;
                } else {
                    enemyTurn();
                    playerTurn = true;
                }

                System.out.println("HP Player: " + playerMonster.getHp());
                System.out.println("HP Musuh: " + enemyMonster.getHp());
                System.out.println();
            }

            if (playerMonster.getHp() <= 0) {
                System.out.println("Anda kalah! Monster Anda pingsan.");
            } else {
                System.out.println("Selamat! Anda mengalahkan musuh.");
                playerMonster.setEp(playerMonster.getEp() + 50);
                System.out.println("EP Anda: " + playerMonster.getEp());
                playerMonster.levelUp();
            }

            showMenu();
        }

        private void playerTurn(Scanner scanner) {
            System.out.println("=== Giliran Anda ===");
            System.out.println("1. Basic Attack");
            System.out.println("2. Special Attack");
            System.out.println("3. Elemental Attack");
            System.out.println("4. Gunakan Item");
            System.out.println("5. Melarikan Diri");
            System.out.print("Pilihan: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    ((AttackInterface) playerMonster).basicAttack(playerMonster, enemyMonster);
                    break;
                case 2:
                    ((AttackInterface) playerMonster).specialAttack(playerMonster, enemyMonster);
                    break;
                case 3:
                    ((AttackInterface) playerMonster).elementalAttack(playerMonster, enemyMonster);
                    break;
                case 4:
                    useItem(playerMonster);
                    break;
                case 5:
                    playerMonster.flee();
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
                    break;
            }
        }

        private void enemyTurn() {
            System.out.println("=== Giliran Musuh ===");
            Random random = new Random();
            int choice = random.nextInt(3) + 1;

            switch (choice) {
                case 1:
                    ((AttackInterface) enemyMonster).basicAttack(enemyMonster, playerMonster);
                    break;
                case 2:
                    ((AttackInterface) enemyMonster).specialAttack(enemyMonster, playerMonster);
                    break;
                case 3:
                    ((AttackInterface) enemyMonster).elementalAttack(enemyMonster, playerMonster);
                    break;
            }
        }

        private void useItem(abstractMonster monster) {
            System.out.println("=== Daftar Item ===");
            System.out.println("1. Health Potion level rendah");
            System.out.println("2. Health Potion level sedang");
            System.out.println("3. Health Potion level tinggi");
            System.out.println("4. Element Potion level rendah");
            System.out.println("5. Element Potion level sedang");
            System.out.println("6. Element Potion level tinggi");
            System.out.print("Pilihan: ");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    monster.useItem(new Item("Health Potion level rendah", ItemType.HEALTH_POTION));
                    break;
                case 2:
                    monster.useItem(new Item("Health Potion level sedang", ItemType.HEALTH_POTION));
                    break;
                case 3:
                    monster.useItem(new Item("Health Potion level tinggi", ItemType.HEALTH_POTION));
                    break;
                case 4:
                    monster.useItem(new Item("Element Potion level rendah", ItemType.ELEMENTAL_POTION));
                    break;
                case 5:
                    monster.useItem(new Item("Element Potion level sedang", ItemType.ELEMENTAL_POTION));
                    break;
                case 6:
                    monster.useItem(new Item("Element Potion level tinggi", ItemType.ELEMENTAL_POTION));
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
                    break;
            }
        }

        public void saveGame() {
            try {
                FileWriter fileWriter = new FileWriter("game_progress.txt");
                fileWriter.write("Player Monster: " + playerMonster.getName() + ", Level: " + playerMonster.getLevel() + ", HP: " + playerMonster.getHp() + ", EP: " + playerMonster.getEp() + "\n");
                fileWriter.write("Enemy Monster: " + enemyMonster.getName() + ", Level: " + enemyMonster.getLevel() + ", HP: " + enemyMonster.getHp() + "\n");
                fileWriter.close();
                System.out.println("Progress game berhasil disimpan.");
            } catch (IOException e) {
                System.out.println("Terjadi kesalahan saat menyimpan progress game: " + e.getMessage());
            }
        }

        public void loadGame() {
            try {
                FileReader fileReader = new FileReader("game_progress.txt");
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                    if (line.startsWith("Player Monster:")) {
                        String[] parts = line.split(", ");
                        String name = parts[1].split(": ")[1];
                        int level = Integer.parseInt(parts[2].split(": ")[1]);
                        int hp = Integer.parseInt(parts[3].split(": ")[1]);
                        int ep = Integer.parseInt(parts[4].split(": ")[1]);
                        Element element = getElementFromString(parts[5].split(": ")[1]);
                        playerMonster = createMonsterFromDetails(name, level, hp, ep, element);
                    } else if (line.startsWith("Enemy Monster:")) {
                        String[] parts = line.split(", ");
                        String name = parts[1].split(": ")[1];
                        int level = Integer.parseInt(parts[2].split(": ")[1]);
                        int hp = Integer.parseInt(parts[3].split(": ")[1]);
                        Element element = getElementFromString(parts[4].split(": ")[1]);
                        enemyMonster = createMonsterFromDetails(name, level, hp, 0, element);
                    }
                }
                bufferedReader.close();
                System.out.println("Progress game berhasil dimuat.");
            } catch (IOException e) {
                System.out.println("Terjadi kesalahan saat memuat progress game: " + e.getMessage());
            } catch (CustomException e) {
                System.out.println(e.getMessage());
            }
        }

        private abstractMonster createMonsterFromDetails(String name, int level, int hp, int ep, Element element) throws CustomException {
            switch (element) {
                case FIRE:
                    return new FireMonster(name, level, hp, ep, element);
                case WATER:
                    return new WaterMonster(name, level, hp, ep, element);
                case ICE:
                    return new IceMonster(name, level, hp, ep, element);
                case EARTH:
                    return new EarthMonster(name, level, hp, ep, element);
                case WIND:
                    return new WindMonster(name, level, hp, ep, element);
                default:
                    throw new CustomException("Elemen monster tidak valid!");
            }
        }

        private Element getElementFromString(String elementString) throws CustomException {
            switch (elementString) {
                case "FIRE":
                    return Element.FIRE;
                case "WATER":
                    return Element.WATER;
                case "ICE":
                    return Element.ICE;
                case "EARTH":
                    return Element.EARTH;
                case "WIND":
                    return Element.WIND;
                default:
                    throw new CustomException("Elemen monster tidak valid!");
            }
        }
    }

    public static class Main {
        public static void main(String[] args) {
            GameManager gameManager = GameManager.getInstance();
            gameManager.startGame();
        }
    }


    // list item
            Item potionhealth1 = new Item("Health Potion level rendah", ItemType.HEALTH_POTION);
            Item potionhealth2 = new Item("Health Potion level sedang", ItemType.HEALTH_POTION);
            Item potionhealth3 = new Item("Health Potion level tinggi", ItemType.HEALTH_POTION);
            Item potionelement1 = new Item("Element Potion level rendah", ItemType.ELEMENTAL_POTION);
            Item potionelement2 = new Item("Element Potion level sedang", ItemType.ELEMENTAL_POTION);
            Item potionelement3 = new Item("Element Potion level tinggi", ItemType.ELEMENTAL_POTION);

            //Firemonster
            FireMonster firemonster1 = new FireMonster("Chairmander", 1, 350, 0, Element.FIRE);
            FireMonster firemonster3 = new FireMonster("FireBoom", 20, 860, 0, Element.FIRE);
            FireMonster firemonster5 = new FireMonster("Scorbunny", 54, 2520, 0, Element.FIRE);

            //Icemonster
            IceMonster icemonster1 = new IceMonster("Cryogonal", 4, 430, 0, Element.ICE);
            IceMonster icemonster3 = new IceMonster("Regice", 25, 1100, 0, Element.ICE);
            IceMonster icemonster5 = new IceMonster("Glaceon", 88, 4102, 0, Element.ICE);

            //Watermonster
            WaterMonster Watermonster1 = new WaterMonster("Wallace", 2, 350, 0, Element.WATER);
            WaterMonster Watermonster3 = new WaterMonster("Cress", 29, 900, 0, Element.WATER);
            WaterMonster Watermonster5 = new WaterMonster("Misty", 58, 3100, 0, Element.WATER);

            //Windmonster
            WindMonster Windmonster1 = new WindMonster("Tornadus", 5, 470, 0, Element.WIND);
            WindMonster Windmonster2 = new WindMonster("Ayawind", 31, 920, 0, Element.WIND);
            WindMonster Windmonster4 = new WindMonster("Shrinae", 74, 2700, 0, Element.WIND);

            //Earthmonster
            EarthMonster Earthmonster1 = new EarthMonster("Rhydon", 4, 500, 0, Element.EARTH);
            EarthMonster Earthmonster2 = new EarthMonster("Vulpix", 26, 900, 0, Element.EARTH);
            EarthMonster Earthmonster4 = new EarthMonster("Litten", 97, 7500, 0, Element.EARTH);
        }