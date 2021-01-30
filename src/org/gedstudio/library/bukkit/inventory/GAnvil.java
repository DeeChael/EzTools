package org.gedstudio.library.bukkit.inventory;

public interface GAnvil extends GInventory {

    void openInventory();

    String getTitle();

    class Slot {

        private static final int[] values = new int[] { Slot.INPUT_LEFT, Slot.INPUT_RIGHT, Slot.RESULT };

        public static final int INPUT_LEFT = 0;

        public static final int INPUT_RIGHT = 1;

        public static final int RESULT = 2;

        public static int[] values() {
            return values;
        }

    }

}
