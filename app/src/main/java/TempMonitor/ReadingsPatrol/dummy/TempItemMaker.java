package tempmonitor.ReadingsPatrol.dummy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class TempItemMaker {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<TemperatureItem> ITEMS = new ArrayList<TemperatureItem>();


    static {
        // Add some sample items.
        addItem(new TemperatureItem("1", "Admin 1st Floor", "Check temperature in hallway, close to kitchen door.", 70.0, 0));
        addItem(new TemperatureItem("2", "Admin 2nd Floor", "Check temperature half-way down the hallway.", 70.0, 0));
        addItem(new TemperatureItem("3", "Admin 3rd Floor", "Check temperature half-way down the hallway.", 70.0, 0));
        addItem(new TemperatureItem("4", "N.W. 1st Floor", "Check temperature half-way down the hallway by office of Contracts.", 70.0, 0));
        addItem(new TemperatureItem("5", "N.W. 2nd Floor", "Check temperature on the hallway, close to the elevator.", 70.0, 0));
        addItem(new TemperatureItem("6", "N.W. 3rd Floor", "Check temperature on the hallway, close to the elevator.", 70.0, 0));
        addItem(new TemperatureItem("7", "N.E. 1st Floor", "Check temperature on the hallway, close to the elevator.", 70.0, 0));
        addItem(new TemperatureItem("8", "N.E. 2nd Floor", "Check temperature on the hallway, close to the elevator.", 70.0, 0));
        addItem(new TemperatureItem("9", "N.E. 3rd Floor", "Check temperature on the hallway, close to the elevator.", 70.0, 0));
        addItem(new TemperatureItem("10", "S.W. Basement", "Check temperature by Scott's office door/water fountain.", 70.0, 0));
        addItem(new TemperatureItem("11", "S.W. 1st Floor", "Check temperature half-way down the hallway, between stairs and elevator.", 70.0, 0));
        addItem(new TemperatureItem("12", "S.W. 2nd Floor", "Check temperature half-way down the hallway, between stairs and elevator.", 70.0, 0));
        addItem(new TemperatureItem("13", "N. Annex Basement", "Check temperature by glass bridge.", 70.0, 0));
        addItem(new TemperatureItem("14", "N. Annex 1st Floor", "Check temperature by glass bridge.", 70.0, 0));
        addItem(new TemperatureItem("15", "District 1 Basement", "Check temperature by boiler door.", 70.0, 0));
        addItem(new TemperatureItem("16", "District 1 1st Floor", "Check temperature on the hallway, next to the break room.", 70.0, 0));
    }

    private static void addItem(TemperatureItem item) {
        ITEMS.add(item);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class TemperatureItem implements Serializable{
        public String id;
        public String content;
        public String details;
        public Double temp;
        public int checked;

        public TemperatureItem(String id, String content, String details, Double temp, int checked) {
            this.id = id;
            this.content = content;
            this.details = details;
            this.temp = temp;
            this.checked = checked;
        }
    }
}
