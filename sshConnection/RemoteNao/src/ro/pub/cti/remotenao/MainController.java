package ro.pub.cti.remotenao;

import ro.pub.cti.communication.CommunicationInfo;
import ro.pub.cti.listeners.CommandsManagementButtonListeners;
import ro.pub.cti.listeners.PersonalitiesButtonListeners;
import ro.pub.cti.listeners.PostureButtonsListeners;
import ro.pub.cti.listeners.SimpleMovesButtonListeners;
import ro.pub.cti.listeners.StatusButtonsListeners;
import ro.pub.cti.listeners.TouchListener;
import ro.pub.cti.utils.Maps;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainController extends Activity {
	CommunicationInfo communicationInfo;
	private String robotComponent = null;
	
	private class SpinListeners implements OnItemSelectedListener {
		Activity activity;
		public SpinListeners(Activity activity) {
			this.activity = activity;
		}
		
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
			        R.array.main_menu_options, android.R.layout.simple_spinner_item);
			if (parent.getItemAtPosition(position).equals(adapter.getItem(0))) {
				activity.findViewById(R.id.basic_movements_layout).setVisibility(View.VISIBLE);
				activity.findViewById(R.id.configurable_movements_layout).setVisibility(View.GONE);
				activity.findViewById(R.id.personalities_layout).setVisibility(View.GONE);
			} else if (parent.getItemAtPosition(position).equals(adapter.getItem(1))) {
				activity.findViewById(R.id.basic_movements_layout).setVisibility(View.GONE);
				activity.findViewById(R.id.configurable_movements_layout).setVisibility(View.VISIBLE);
				activity.findViewById(R.id.personalities_layout).setVisibility(View.GONE);
			} else if (parent.getItemAtPosition(position).equals(adapter.getItem(4))) {
				activity.findViewById(R.id.basic_movements_layout).setVisibility(View.GONE);
				activity.findViewById(R.id.configurable_movements_layout).setVisibility(View.GONE);
				activity.findViewById(R.id.personalities_layout).setVisibility(View.VISIBLE);
			} else {
				activity.findViewById(R.id.basic_movements_layout).setVisibility(View.GONE);
				activity.findViewById(R.id.configurable_movements_layout).setVisibility(View.GONE);
				activity.findViewById(R.id.personalities_layout).setVisibility(View.GONE);
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			
		}
	}
	
	private class BodySpinListeners implements OnItemSelectedListener {
		Activity activity;
		public BodySpinListeners(Activity activity) {
			this.activity = activity;
		}
		
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			robotComponent = Maps.bodyComponents.get(parent.getItemAtPosition(position));
			if (robotComponent == null) {
				Toast.makeText(activity, "NULL", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(activity, robotComponent, Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_controller);
		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			System.out.println("extrass = NULL");
			finishActivity(RESULT_CANCELED);
		}
		
		System.out.println("............");
		communicationInfo = CommunicationInfo.getCommunicationInfo();
		
		TextView intro_text_view = (TextView) findViewById(R.id.intro_text_view);
		StringBuffer text = new StringBuffer("You are now connected to Nao.\n");
		text.append("connection type:" + communicationInfo.protocol + "\n");
		text.append("server ip:" + communicationInfo.ip + "\n");
		text.append("port:" + communicationInfo.port + "\n");
		intro_text_view.setText(text);
		
		Spinner main_commands_spinner = (Spinner) findViewById(R.id.main_commands);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.main_menu_options, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		main_commands_spinner.setAdapter(adapter);
		main_commands_spinner.setOnItemSelectedListener(new SpinListeners(this));
		
		Spinner body_components_spinner = (Spinner) findViewById(R.id.robot_body_component);
		ArrayAdapter<CharSequence> body_adapter = ArrayAdapter.createFromResource(this,
		        R.array.robot_body_components, android.R.layout.simple_spinner_item);
		
		body_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		body_components_spinner.setAdapter(body_adapter);
		body_components_spinner.setOnItemSelectedListener(new BodySpinListeners(this));
		
		findViewById(R.id.moveBackwardButton).setOnClickListener(new SimpleMovesButtonListeners(this));
		findViewById(R.id.moveForwardButton).setOnClickListener(new SimpleMovesButtonListeners(this));
		findViewById(R.id.moveLeftButton).setOnClickListener(new SimpleMovesButtonListeners(this));
		findViewById(R.id.moveRightButton).setOnClickListener(new SimpleMovesButtonListeners(this));
		findViewById(R.id.turnLeftButton).setOnClickListener(new SimpleMovesButtonListeners(this));
		findViewById(R.id.turnRightButton).setOnClickListener(new SimpleMovesButtonListeners(this));
		findViewById(R.id.executeMoveButton).setOnClickListener(new CommandsManagementButtonListeners(this));
		findViewById(R.id.addMoveButton).setOnClickListener(new CommandsManagementButtonListeners(this));
		findViewById(R.id.motorsOffButton).setOnClickListener(new SimpleMovesButtonListeners(this));
		findViewById(R.id.motorsOnButton).setOnClickListener(new SimpleMovesButtonListeners(this));
		
		findViewById(R.id.standButton).setOnClickListener(new PostureButtonsListeners(this));
		findViewById(R.id.standInitButton).setOnClickListener(new PostureButtonsListeners(this));
		findViewById(R.id.standZeroButton).setOnClickListener(new PostureButtonsListeners(this));
		findViewById(R.id.sitButton).setOnClickListener(new PostureButtonsListeners(this));
		findViewById(R.id.sitRelaxButton).setOnClickListener(new PostureButtonsListeners(this));
		findViewById(R.id.crouchButton).setOnClickListener(new PostureButtonsListeners(this));
		findViewById(R.id.lyingBackButton).setOnClickListener(new PostureButtonsListeners(this));
		findViewById(R.id.lyingBellyButton).setOnClickListener(new PostureButtonsListeners(this));
		
		findViewById(R.id.aurelNiresteanButton).setOnClickListener(new PersonalitiesButtonListeners(this));
		findViewById(R.id.dineshBhugraButton).setOnClickListener(new PersonalitiesButtonListeners(this));
		findViewById(R.id.eliotSorelButton).setOnClickListener(new PersonalitiesButtonListeners(this));
		findViewById(R.id.hellenHerrmanButton).setOnClickListener(new PersonalitiesButtonListeners(this));
		findViewById(R.id.masatoshiTakedaButton).setOnClickListener(new PersonalitiesButtonListeners(this));
		
		findViewById(R.id.elimianBratuButton).setOnClickListener(new PersonalitiesButtonListeners(this));
		findViewById(R.id.gheorgheDucaButton).setOnClickListener(new PersonalitiesButtonListeners(this));
		findViewById(R.id.gheorgheLazarButton).setOnClickListener(new PersonalitiesButtonListeners(this));
		findViewById(R.id.mateiDraghiceanuButton).setOnClickListener(new PersonalitiesButtonListeners(this));
		findViewById(R.id.nicolaeVasilescuKarpenButton).setOnClickListener(new PersonalitiesButtonListeners(this));
		findViewById(R.id.serbanSolacoluButton).setOnClickListener(new PersonalitiesButtonListeners(this));
		findViewById(R.id.serbanTitieicaButton).setOnClickListener(new PersonalitiesButtonListeners(this));
		findViewById(R.id.faceRecogButton).setOnClickListener(new PersonalitiesButtonListeners(this));
		
		StatusButtonsListeners sbl = new StatusButtonsListeners(this);
		findViewById(R.id.batteryStatus).setOnClickListener(sbl);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_controller, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	

}
