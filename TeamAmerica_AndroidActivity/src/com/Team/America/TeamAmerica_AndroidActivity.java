package com.Team.America;

import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class TeamAmerica_AndroidActivity extends Activity {

	// ** Methods ** 

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// set the data context for the connection and whatever else might need it
		m_oThisContext = this.getApplicationContext();
		m_oAudioManager = (AudioManager)m_oThisContext.getSystemService(Context.AUDIO_SERVICE);
		m_bIsMicActive = false;
		
		// start service
		startService(new Intent(this, ConnectionWrapper.class));

		// ** Button Listeners **

		final Button startButton = (Button)findViewById(R.id.startButton);
		startButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startSlideShow();
			}
		});

		final Button endButton = (Button)findViewById(R.id.endButton);
		endButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				endSlideShow();
			}
		});

		final Button connectButton = (Button)findViewById(R.id.connectButton);
		connectButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// popup a dialog for the user to choose which connection type to use
				showConnectionDialog();
			}
		});

		final Button prevSlideButton = (Button)findViewById(R.id.prevSlideButton);
		prevSlideButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				goBackSlide();
			}
		});

		final Button nextSlideButton = (Button)findViewById(R.id.nextSlideButton);
		nextSlideButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				advanceSlide();
			}
		});

		final Switch micToggleSwitch = (Switch)findViewById(R.id.micToggleSwitch);
		micToggleSwitch.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				m_bIsMicActive = !m_bIsMicActive;
				toggleMic();
			}
		});
	}

	void toggleMic(){
		m_oAudioManager.setSpeakerphoneOn(m_bIsMicActive);
	}

	void startSlideShow(){
		if(m_iSlideCount > 0){
			// somehow get the name and slide count from 
			// the user, or saved slideshow for the constructor
			m_oSlideShow = new SlideShow(m_iSlideCount, m_sSlideShowName);
			m_oSlideShow.startSlideShow();
		}
	}

	void endSlideShow(){
		if(m_oSlideShow != null){
			m_oSlideShow.endSlideShow();
		}
	}

	void advanceSlide(){
		if(m_oSlideShow != null){
			m_oSlideShow.advanceSlide();
		}
	}

	void goBackSlide(){
		if(m_oSlideShow != null){
			m_oSlideShow.goBackSlide();
		}
	}

	void showConnectionDialog(){

		AlertDialog.Builder oBuilder = new AlertDialog.Builder(this);
		// Add the buttons
		oBuilder.setPositiveButton(R.string.bluetoothlbl, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {

				m_oConnectionType = ConnectionType.BLUETOOTH;
				ConnectionWrapper oBluetoothConnection = new ConnectionWrapper();
				oBluetoothConnection.m_oConnectionType = ConnectionType.BLUETOOTH;
				oBluetoothConnection.connectBluetooth(m_oThisContext);
			}
		});
		
		oBuilder.setNegativeButton(R.string.wifilbl, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {

				m_oConnectionType = ConnectionType.WIFI;
				ConnectionWrapper oWifiConnection = new ConnectionWrapper();
				oWifiConnection.m_oConnectionType = ConnectionType.WIFI;
				oWifiConnection.connectWifi(m_oThisContext);
			}
		});
		
		oBuilder.setMessage("Connection Type");

		// Create the AlertDialog and show it
		AlertDialog oDialog = oBuilder.create();
		oDialog.show();
	}


	// ** Fields **

	AudioManager m_oAudioManager = null;
	SlideShow m_oSlideShow;
	boolean m_bIsConnected;
	boolean m_bIsMicActive;
	ConnectionType m_oConnectionType;
	int m_iSlideCount;
	string m_sSlideShowName;
	Context m_oThisContext;
}
