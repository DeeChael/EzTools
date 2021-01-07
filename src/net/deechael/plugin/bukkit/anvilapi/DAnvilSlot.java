package net.deechael.plugin.bukkit.anvilapi;

public enum DAnvilSlot {

    INPUT_LEFT(0),
    INPUT_RIGHT(1),
    OUTPUT(2);

    private int slot;

    DAnvilSlot(int slot) {
        this.slot = slot;
    }

    public static DAnvilSlot bySlot(int slot) {
        for (DAnvilSlot anvilSlot : values()) {
            if (anvilSlot.getSlot() == slot) {
                return anvilSlot;
            }
        }

        return null;
    }

    public int getSlot() {
        return slot;
    }

}
