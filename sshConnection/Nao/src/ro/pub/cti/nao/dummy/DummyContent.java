package ro.pub.cti.nao.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ro.pub.cti.nao.R;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

	/**
	 * An array of sample (dummy) items.
	 */
	public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();

	/**
	 * A map of sample (dummy) items, by ID.
	 */
	public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

	static {
		// Add 3 sample items.
		addItem(new DummyItem("Robot Status", R.layout.fragment_item_detail));
		addItem(new DummyItem("Robot Control", R.layout.fragment_item_robot_controllers));
		addItem(new DummyItem("Volume Controllers", R.layout.fragment_item_volume_controllers));
		addItem(new DummyItem("Speech", R.layout.fragment_item_speech_controller));
		addItem(new DummyItem("Leds Controllers", R.layout.fragment_item_leds_controller));
	}

	private static void addItem(DummyItem item) {
		ITEMS.add(item);
		ITEM_MAP.put(item.id, item);
	}

	/**
	 * A dummy item representing a piece of content.
	 */
	public static class DummyItem {
		public String id;
		public Integer content;

		public DummyItem(String id, Integer content) {
			this.id = id;
			this.content = content;
		}

		@Override
		public String toString() {
			return id;
		}
	}
}
