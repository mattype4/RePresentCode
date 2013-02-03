package com.Team.America;
//Another test
import android.R.string;
import android.app.Activity;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class ConnectionWrapper extends Service{

	// ** Constructors **
	
	public ConnectionWrapper(){
		// default
		m_bIsConnected = false;
		m_oBinder = new LocalBinder();
	}
	
	// we should name the connection if we want to save it 
	public ConnectionWrapper(string oName){
		m_sConnectionName = oName;
		m_oBinder = new LocalBinder();
	}
	
	
	// ** Methods **

	private ServiceConnection m_oConnection = new ServiceConnection() {
	    public void onServiceConnected(ComponentName className, IBinder service) {
	        // This is called when the connection with the service has been
	        // established, giving us the service object we can use to
	        // interact with the service.  Because we have bound to a explicit
	        // service that we know is running in our own process, we can
	        // cast its IBinder to a concrete class and directly access it.
	    	m_oBoundService = ((ConnectionWrapper.LocalBinder)service).getService();
	    }

	    public void onServiceDisconnected(ComponentName className) {
	        // This is called when the connection with the service has been
	        // unexpectedly disconnected -- that is, its process crashed.
	        // Because it is running in our same process, we should never
	        // see this happen.
	    	m_oBoundService = null;
	    }
	};

	void doBindService() {
	    // Establish a connection with the service.  We use an explicit
	    // class name because we want a specific service implementation that
	    // we know will be running in our own process (and thus won't be
	    // supporting component replacement by other applications).
	    bindService(new Intent(this, ConnectionWrapper.class), m_oConnection, Context.BIND_AUTO_CREATE);
	    m_bIsBound = true;
	}

	void doUnbindService() {
	    if (m_bIsBound) {
	        // Detach our existing connection.
	        unbindService(m_oConnection);
	        m_bIsBound = false;
	    }
	}
	
	public class LocalBinder extends Binder {
		ConnectionWrapper getService() {
            return ConnectionWrapper.this;
        }
    }
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return m_oBinder;
	}
	
	public void connectBluetooth(Context oContext){
		
//		BluetoothAdapter oBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//		if (oBluetoothAdapter == null) {
//			Toast.makeText(oContext, "This device does not support Bluetooth", 0).show();
//		}
//		
//		if (!oBluetoothAdapter.isEnabled()) {
//		    Intent oEnableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//		    startActivityForResult(oEnableBtIntent, 1);
//		}
		
		// if all goes well, show this and set connection to 'connected'
		Toast.makeText(oContext, "Bluetooth Connected", 0).show();
		m_bIsConnected = true;
	}
	
	public void connectWifi(Context oContext){
		// if all goes well, show this and set connection to 'connected'
		Toast.makeText(oContext, "WiFi Connected", 0).show();
		m_bIsConnected = true;
	}
	
	public string getConnectionName(){
		return m_sConnectionName;
	}
	
	
	// ** Fields **
	
	private ConnectionWrapper m_oBoundService;
	private final IBinder m_oBinder;
	ConnectionType m_oConnectionType;
	string m_sConnectionName;
	public boolean m_bIsConnected;
	boolean m_bIsBound;
}
