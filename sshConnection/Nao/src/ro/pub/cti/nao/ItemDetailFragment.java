package ro.pub.cti.nao;

import ro.pub.cti.communication.Update;
import ro.pub.cti.listeners.AutomaticUpdateListeners;
import ro.pub.cti.nao.dummy.DummyContent;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

/**
 * A fragment representing a single Item detail screen. This fragment is either
 * contained in a {@link ItemListActivity} in two-pane mode (on tablets) or a
 * {@link ItemDetailActivity} on handsets.
 */
public class ItemDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private DummyContent.DummyItem mItem;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ItemDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = DummyContent.ITEM_MAP.get(getArguments().getString(
					ARG_ITEM_ID));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(DummyContent.ITEM_MAP.get(mItem.id).content,
				container, false);
		System.out.println(rootView);

		if (DummyContent.ITEM_MAP.get(mItem.id).content == R.layout.fragment_item_detail) {
			//imi fac o lista globala unde imi tin threadurile si daca lista de tipul respectiv
			// are deja un thread ignor.
			final ToggleButton tb = (ToggleButton) rootView.findViewById(R.id.connectionStatus);
			final AutomaticUpdateListeners listener = new AutomaticUpdateListeners() {
				
				@Override
				public boolean onHover(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public void update(boolean state) {
					tb.setChecked(state);
					
				}
			}; 
			tb.setOnHoverListener(listener);
			new Update(listener).start();
		}
		
		

		return rootView;
	}
}
