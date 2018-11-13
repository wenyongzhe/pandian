package com.supoin.commoninventory.util;
import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
	public class PlaySoundPool {
	private Context context;
	public PlaySoundPool(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		initSounds();
	}
	//��Ч������ 
	int streamVolume; 
	
	//����SoundPool ���� 
	private SoundPool soundPool; 
	
	//����HASH�� 
	private HashMap<Integer, Integer> soundPoolMap; 
	
	 
	public void initSounds() { 
		//��ʼ��soundPool ����,��һ�������������ж��ٸ�������ͬʱ����,��2����������������,������������������Ʒ�� 
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100); 
		
		//��ʼ��HASH�� 
		soundPoolMap = new HashMap<Integer, Integer>(); 
		
		//��������豸���豸���� 
		AudioManager mgr = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE); 
		streamVolume = mgr.getStreamVolume(AudioManager.STREAM_MUSIC); 
	} 
	
	 
	public void loadSfx(int raw, int ID) { 
	//����Դ�е���Ч���ص�ָ����ID(���ŵ�ʱ��Ͷ�Ӧ�����ID���ž�����) 
		soundPoolMap.put(ID, soundPool.load(context, raw, ID)); 
	} 
	
	 
	public void play(final int sound, final int uLoop) { 
		
		soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener(){
			@Override
			public void onLoadComplete(SoundPool arg0, int arg1, int arg2) {
				soundPool.play(soundPoolMap.get(sound), streamVolume, streamVolume, 1, uLoop, 1f); 
			}
			});		
//		soundPool.play(soundPoolMap.get(sound), streamVolume, streamVolume, 1, uLoop, 1f); 
	} 
}