package com.example.amber.musictestapplication;

import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.RemoteViews;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MusicControlUtils {

	public static final String TAG = "MyMusicContorll";

	private static ArrayList<String> mMediaReceiverExcept = new ArrayList<>();
	private static ArrayList<String> mSpecialPlayers = new ArrayList<>();

	static {
		mMediaReceiverExcept.add("com.wandoujia.phoenix2");
		mMediaReceiverExcept.add("com.androturk.radio");
		mMediaReceiverExcept.add("com.infraware.office.link");
		mMediaReceiverExcept.add("com.opera.browser");
		mMediaReceiverExcept.add("tv.twitch.android.viewer");
		mMediaReceiverExcept.add("com.gotv.nflgamecenter.us.lite");
		mMediaReceiverExcept.add("com.jiubang.goscreenlock");
		mMediaReceiverExcept.add("com.ghisler.android.TotalCommander");
		mMediaReceiverExcept.add("com.nbaimd.gametime.nba2011");
		mMediaReceiverExcept.add("com.android.browser");
		mMediaReceiverExcept.add("com.espn.score_center");
		mMediaReceiverExcept.add("com.ted.android");
		mMediaReceiverExcept.add("com.speaktoit.assistant");
		mMediaReceiverExcept.add("com.keek");
		mMediaReceiverExcept.add("com.magicjack");
		mMediaReceiverExcept.add("com.loudtalks");
		mMediaReceiverExcept.add("com.ptculi.tekview");
		mMediaReceiverExcept.add("sg.bigo");
		mMediaReceiverExcept.add("air.com.vudu.air.DownloaderTablet");
		mMediaReceiverExcept.add("com.HBO");
		mMediaReceiverExcept.add("com.wsl.noom");
		mMediaReceiverExcept.add("nl.nos.app");
		mMediaReceiverExcept.add("air.WatchESPN");
		mMediaReceiverExcept.add("com.crunchyroll.crunchyroid");
		mMediaReceiverExcept.add("mobogenie.mobile.market.app.game");
		mMediaReceiverExcept.add("com.acer.c5photo");
		mMediaReceiverExcept.add("net.flixster.android");
		mMediaReceiverExcept.add("com.neverland.alreader");
		mMediaReceiverExcept.add("com.yidio.androidapp");
		mMediaReceiverExcept.add("com.bloomberg.android.plus");
		mMediaReceiverExcept.add("com.pa.caller");
		mMediaReceiverExcept.add("com.flyersoft.moonreaderp");
		mMediaReceiverExcept.add("nl.eredivisie");
		mMediaReceiverExcept.add("net.osmand");
		mMediaReceiverExcept.add("es.lfp.laligatv");
		mMediaReceiverExcept.add("de.maxdome.app.android");
		mMediaReceiverExcept.add("com.pft.starsports.ui");
		mMediaReceiverExcept.add("com.koushikdutta.cast");
		mMediaReceiverExcept.add("tv.cinetrailer.mobile.b");
		mMediaReceiverExcept.add("com.mcafee.vsm_android_dcm");
		mMediaReceiverExcept.add("com.rebelvox.voxer");
		mMediaReceiverExcept.add("com.magine.aliceoid");
		mMediaReceiverExcept.add("com.androidauthority");
		mMediaReceiverExcept.add("com.mentallmedia.gamenewsfull");
		mMediaReceiverExcept.add("com.att.mobiletransfer");
		mMediaReceiverExcept.add("com.verrines.appyvid");
		mMediaReceiverExcept.add("de.netzkino.android.ics");
		mMediaReceiverExcept.add("com.imo.android.imoim");
		mMediaReceiverExcept.add("com.bydeluxe.d3.android.program.starz");
		mMediaReceiverExcept.add("com.aura.ringtones.aurabusiness");
		mMediaReceiverExcept.add("nl.clubmobiel.feyenoord");
		mMediaReceiverExcept.add("com.motorola.audiomonitor");
		mMediaReceiverExcept.add("com.biblia.diario.biblia.de.estudios");
		mMediaReceiverExcept.add("cz.shawn.antelli");
		mMediaReceiverExcept.add("com.Rogers.OneNumber");
		mMediaReceiverExcept.add("com.aura.ringtones.auraanimals");
		mMediaReceiverExcept.add("com.viaplay.android");
		mMediaReceiverExcept.add("air.com.editoraconfianca.revistacartacapital");
		mMediaReceiverExcept.add("mobi.voiceassistant.ru");
		mMediaReceiverExcept.add("com.tencent.mm");
		mMediaReceiverExcept.add("com.cyanogenmod1.rom");
		mMediaReceiverExcept.add("com.bamnetworks.mobile.android.gameday.atbat");
		mMediaReceiverExcept.add("com.air_cheap");
		mMediaReceiverExcept.add("TellMeTheTime.App");
		mMediaReceiverExcept.add("uk.co.neilandtheresa.NewVignette");
		mMediaReceiverExcept.add("com.wSaudileague2014");
		mMediaReceiverExcept.add("com.softenido.audible");
		mMediaReceiverExcept.add("com.overdrive.mobile.android.mediaconsole");
		mMediaReceiverExcept.add("net.monthorin.rttraffic16");
		mMediaReceiverExcept.add("com.udemy.android.sa.ciscoCcnaIn60Days");
		mMediaReceiverExcept.add("com.personal.personalvideo");
		mMediaReceiverExcept.add("com.opera.browser.beta");
		mMediaReceiverExcept.add("com.nfl.now");
		mMediaReceiverExcept.add("com.talksport.tsliveen");
		mMediaReceiverExcept.add("com.hudl.hudroid");
		mMediaReceiverExcept.add("com.gezilmesi.gereken.yerler");
		mMediaReceiverExcept.add("com.woodwing.iba.revistas");
		mMediaReceiverExcept.add("org.mike.meditation");
		mMediaReceiverExcept.add("de.joergjahnke.documentviewer.android.free");
		mMediaReceiverExcept.add("nl.uitzendinggemist");
		mMediaReceiverExcept.add("net.connexionone.android.xone");
		mMediaReceiverExcept.add("com.viraaj11.oneplus_forum");
		mMediaReceiverExcept.add("com.tozelabs.tvshowtime");
		mMediaReceiverExcept.add("com.neulion.smartphone.ufc.android");
		mMediaReceiverExcept.add("com.navea19android");
		mMediaReceiverExcept.add("com.disney.datg.videoplatforms.android.abc");
		mMediaReceiverExcept.add("com.android.deskclock");

		mSpecialPlayers.add("com.perm.kate_new_3");
	}


	/**
	 * 获取那个 音乐播放APP 注册了耳机按钮 receiver
	 * <p/>
	 * 在三星手机上有问题 无法获取
	 */
	public static ComponentName getCurMediaPlayerComponentName(Context context) {
		ComponentName eventReceiver = null;
		try {
			String receiverName = Settings.System.getString(context.getContentResolver(), "media_button_receiver");
//			Log.e(TAG, "MusicControlUtils ComponentName = " + receiverName);
			eventReceiver = ComponentName.unflattenFromString(receiverName);
		} catch (Exception e) {
		}

		if (eventReceiver == null) {
			eventReceiver = new ComponentName("", "");
		}

		return eventReceiver;
	}

	public static MusicMeta getMusicMetaFromNotification(String packageName, Notification notification) {
		MusicMeta musicMeta = null;

		try {
			RemoteViews actionsView = null;
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
				actionsView = notification.bigContentView;
			}

			if (actionsView == null) {
				actionsView = notification.contentView;
			}

			if (actionsView != null) {
				Field actionsField = actionsView.getClass().getDeclaredField("mActions");
				actionsField.setAccessible(true);
				Object actions = actionsField.get(actionsView);
				if (actions instanceof List) {
					List<String> musicInfo = new ArrayList<>();
					Iterator iterator = ((List) actions).iterator();
					while (iterator.hasNext()) {
						Object reflectionAction = iterator.next();
						if (reflectionAction.getClass().getName().equals("android.widget.RemoteViews$ReflectionAction")) {
							Field methodNameField = reflectionAction.getClass().getDeclaredField("methodName");
							methodNameField.setAccessible(true);
							Object methodName = methodNameField.get(reflectionAction);
							if (methodName instanceof String && ("setText").equals(methodName)) {
								Field valueField = reflectionAction.getClass().getDeclaredField("value");
								valueField.setAccessible(true);
								Object value = valueField.get(reflectionAction);
//								LogUtil.i("value:" + value);
								musicInfo.add((String) value);
							}

						}
					}

					if (musicInfo.size() > 0) {
						if (!TextUtils.isEmpty(packageName) && packageName.startsWith("com.mxtech.videoplayer")) {//MX Player track和artist在一行，且切换歌曲会有多个value，最后一个为最新歌曲信息 com.mxtech.videoplayer.pro 和 com.mxtech.videoplayer.ad
							for (int i = musicInfo.size() - 1; i >= 0; i --) {
								if (musicInfo.get(i) != null) {
									musicMeta = new MusicMeta(musicInfo.get(i), null);
									break;
								}
							}
						} else if("com.jrtstudio.music".equals(packageName) && musicInfo.size() > 2){
								musicMeta = new MusicMeta(musicInfo.get(musicInfo.size() - 2), musicInfo.get(musicInfo.size() - 1));
                        } else if ("com.lava.music".equals(packageName) && musicInfo.size() >= 3) {
							musicMeta = new MusicMeta(musicInfo.get(0), musicInfo.get(2));
						} else if (musicInfo.size() == 1) {
							musicMeta = new MusicMeta(musicInfo.get(0), null);
						} else if (musicInfo.size() >= 2) {
							musicMeta = new MusicMeta(musicInfo.get(0), musicInfo.get(1));
						}
					}

				}
			}

		} catch (NoSuchFieldException e) {
//			e.printStackTrace();
		} catch (IllegalAccessException e) {
//			e.printStackTrace();
		} catch (Exception e) {
//			e.printStackTrace();
		}

		return musicMeta;
	}

	public static List<ResolveInfo> getAllMediaReceivers(Context context) {
		try {
			Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
			return context.getPackageManager().queryBroadcastReceivers(intent, 0);
		} catch (Throwable t) {
		    t.printStackTrace();
		}
		return null;
	}

	public static boolean isMusicPlayerPackageName(Context context, String packageName) {
		if (mMediaReceiverExcept.contains(packageName)) {
			return false;
		}

		List<ResolveInfo> mediaReceivers = getAllMediaReceivers(context);
		if (mediaReceivers == null) {
			return false;
		}
		for (ResolveInfo info : mediaReceivers) {
//			Log.i(TAG, "meidaReceiver:" + info.activityInfo.packageName);
			if (info.activityInfo.packageName.equals(packageName)) {
				return true;
			}
		}

		if (mSpecialPlayers.contains(packageName)) {
			return true;
		}

		return false;
	}
}
