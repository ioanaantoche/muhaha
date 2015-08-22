package ro.pub.cti.remotenao;

import java.util.ArrayList;
import java.util.List;

import ro.pub.cti.listeners.VerifyConnectionButtonListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class SshConnectionActivity extends Activity {
	private List<String> oldPortsList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ssh_connection);
		
		((Button) findViewById(R.id.verify_url_request_button)).setOnClickListener(new VerifyConnectionButtonListener(this));
		
		Button submit_button = (Button) findViewById(R.id.verify_request_button);
        submit_button.setOnClickListener(new VerifyConnectionButtonListener(this));
        
        Spinner port_spinner = (Spinner) findViewById(R.id.ports);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		        R.array.main_menu_options, oldPortsList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		port_spinner.setAdapter(adapter);
		//main_commands_spinner.setOnItemSelectedListener(new SpinListeners(this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ssh_connection, menu);
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  if (resultCode == RESULT_OK) {
		  Toast.makeText(this, "Back to SshConnection Activity", Toast.LENGTH_SHORT).show();
	  } else {
		  Toast.makeText(this, "Back to SshConnection Activity :(:( :(", Toast.LENGTH_SHORT).show();
	  }
	} 
}
