public class main {
    public static void main(String[] args) {

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
}
