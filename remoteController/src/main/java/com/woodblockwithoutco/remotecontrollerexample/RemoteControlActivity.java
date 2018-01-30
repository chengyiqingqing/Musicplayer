package com.woodblockwithoutco.remotecontrollerexample;

import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.MediaMetadataRetriever;
import android.media.RemoteControlClient;
import android.media.RemoteController;
import android.media.RemoteController.MetadataEditor;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import java.util.List;

public class RemoteControlActivity extends Activity {

	public final static String START_REMOTE_CONTROLLER_ACTION = "com.woodblockwithoutco.remotecontrollerexample.START_REMOTE_CONTROLLER";

	//Views in the Activity
	protected ImageButton mPrevButton;
	protected ImageButton mPlayPauseButton;
	protected ImageButton mNextButton;
	protected TextView mArtistText;
	protected TextView mTitleText;
	protected TextView mAlbumText;
	protected SeekBar mScrubBar;
	protected ImageView mArtwork;

	
	protected RemoteControlService mRCService;
	protected boolean mBound = false; //flag indicating if service is bound to Activity
	
	protected Handler mHandler = new Handler();

	protected boolean mIsPlaying = false; //flag indicating if music is playing
	protected long mSongDuration = 1; 

	private RemoteController.OnClientUpdateListener mClientUpdateListener = new RemoteController.OnClientUpdateListener() {

		private boolean mScrubbingSupported = false; //is scrubbing supported?
		
		private boolean isScrubbingSupported(int flags) {
			//if "flags" bitmask contains certain bits it means that srcubbing is supported
			return (flags & RemoteControlClient.FLAG_KEY_MEDIA_POSITION_UPDATE) != 0; 
		}

		@Override
		public void onClientTransportControlUpdate(int transportControlFlags) {
			mScrubbingSupported = isScrubbingSupported(transportControlFlags);
			if(mScrubbingSupported) {
				//if scrubbing is supported, we shoul let user use scrubbing SeekBar
				mScrubBar.setEnabled(true);
				//start SeekBar updater
				mHandler.post(mUpdateSeekBar);
			} else {
				//disabling SeekBar and SeekBar updater
				mScrubBar.setEnabled(false);
				mHandler.removeCallbacks(mUpdateSeekBar);
			}
		}

		@Override
		public void onClientPlaybackStateUpdate(int state, long stateChangeTimeMs, long currentPosMs, float speed) {
			switch(state) {
			case RemoteControlClient.PLAYSTATE_PLAYING:
				//if music started playing, we should start updating our SeekBar position
				//also, update the play/pause icon
				if(mScrubbingSupported) mHandler.post(mUpdateSeekBar);
				mIsPlaying = true;
				mPlayPauseButton.setImageResource(android.R.drawable.ic_media_pause);
				break;
			default:
				//if music isn't playing, we should stop updating of SeekBar
				mHandler.removeCallbacks(mUpdateSeekBar);
				mIsPlaying = false;
				mPlayPauseButton.setImageResource(android.R.drawable.ic_media_play);
				break;
			}
			
			mScrubBar.setProgress((int) (currentPosMs * mScrubBar.getMax() / mSongDuration));
		}

		@Override
		public void onClientPlaybackStateUpdate(int state) {
			switch(state) {
			case RemoteControlClient.PLAYSTATE_PLAYING:
				if(mScrubbingSupported) mHandler.post(mUpdateSeekBar);
				mIsPlaying = true;
				mPlayPauseButton.setImageResource(android.R.drawable.ic_media_pause);
				break;
			default:
				mHandler.removeCallbacks(mUpdateSeekBar);
				mIsPlaying = false;
				mPlayPauseButton.setImageResource(android.R.drawable.ic_media_play);
				break;
			}
		}

		@Override
		public void onClientMetadataUpdate(MetadataEditor editor) {

			//some players write artist name to METADATA_KEY_ALBUMARTIST instead of METADATA_KEY_ARTIST, so we should double-check it
			mArtistText.setText(editor.getString(MediaMetadataRetriever.METADATA_KEY_ARTIST, 
					editor.getString(MediaMetadataRetriever.METADATA_KEY_ALBUMARTIST, getString(R.string.unknown))
					));
			
			mTitleText.setText(editor.getString(MediaMetadataRetriever.METADATA_KEY_TITLE, getString(R.string.unknown)));
			mAlbumText.setText(editor.getString(MediaMetadataRetriever.METADATA_KEY_ALBUM, getString(R.string.unknown)));
			
			mSongDuration = editor.getLong(MediaMetadataRetriever.METADATA_KEY_DURATION, 1);
			mArtwork.setImageBitmap(editor.getBitmap(MetadataEditor.BITMAP_KEY_ARTWORK, null));
		}

		@Override
		public void onClientChange(boolean clearing) {

		}
	};

	private OnClickListener mClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch(v.getId()) {
			case R.id.prev_button:
				if(mBound) {
					mRCService.sendPreviousKey();
				}
				break;
			case R.id.next_button:
				if(mBound) {
					mRCService.sendNextKey();
				}
				break;
			case R.id.play_pause_button:
				if(mBound) {
					if(mIsPlaying) {
						mRCService.sendPauseKey();
					} else {
						mRCService.sendPlayKey();
					}
				}
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_remote_control);

		mPrevButton = (ImageButton)findViewById(R.id.prev_button);
		mPlayPauseButton = (ImageButton)findViewById(R.id.play_pause_button);
		mNextButton = (ImageButton)findViewById(R.id.next_button);

		mTitleText = (TextView)findViewById(R.id.title_text);
		mAlbumText = (TextView)findViewById(R.id.album_text);
		mArtistText = (TextView)findViewById(R.id.artist_text);
		
		mArtwork = (ImageView)findViewById(R.id.album_art);

		mScrubBar = (SeekBar)findViewById(R.id.track_scrubber);

		mPrevButton.setOnClickListener(mClickListener);
		mNextButton.setOnClickListener(mClickListener);
		mPlayPauseButton.setOnClickListener(mClickListener);
		
		
		mScrubBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if(mBound && fromUser) {
					mRCService.seekTo(mSongDuration * progress/seekBar.getMax());
				}
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				mHandler.removeCallbacks(mUpdateSeekBar);
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				mHandler.post(mUpdateSeekBar);
			}
		});
	}

	@Override
	public void onStart() {
		super.onStart();
		final Intent intent = new Intent();
		intent.setAction("com.woodblockwithoutco.remotecontrollerexample.BIND_RC_CONTROL_SERVICE");
		final Intent eintent = new Intent(createExplicitFromImplicitIntent(this,intent));
		bindService(eintent, mConnection, Context.BIND_AUTO_CREATE);
	}

	@Override
	public void onStop() {
		super.onStop();
		if(mBound) {
			mRCService.setRemoteControllerDisabled();
		}
		unbindService(mConnection);
	}

	public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
		// Retrieve all services that can match the given intent
		PackageManager pm = context.getPackageManager();
		List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

		// Make sure only one match was found
		if (resolveInfo == null || resolveInfo.size() != 1) {
			return null;
		}

		// Get component info and create ComponentName
		ResolveInfo serviceInfo = resolveInfo.get(0);
		String packageName = serviceInfo.serviceInfo.packageName;
		String className = serviceInfo.serviceInfo.name;
		ComponentName component = new ComponentName(packageName, className);

		// Create a new intent. Use the old one for extras and such reuse
		Intent explicitIntent = new Intent(implicitIntent);

		// Set the component to be explicit
		explicitIntent.setComponent(component);

		return explicitIntent;
	}

	private ServiceConnection mConnection = new ServiceConnection() {	       
		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {
			//Getting the binder and activating RemoteController instantly
			RemoteControlService.RCBinder binder = (RemoteControlService.RCBinder) service;
			mRCService = binder.getService();
			mRCService.setRemoteControllerEnabled();
			mRCService.setClientUpdateListener(mClientUpdateListener);
			mBound = true;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mBound = false;
		}
	};
	
	private Runnable mUpdateSeekBar = new Runnable() {
		@Override
		public void run() {
			if(mBound) {
				//if service is bound to our activity, we update our position seekbar
				mScrubBar.setProgress((int) (mRCService.getEstimatedPosition() * mScrubBar.getMax() / mSongDuration));
				mHandler.postDelayed(this, 1000); //setting up update event after one second
			}
		}
	};

}
