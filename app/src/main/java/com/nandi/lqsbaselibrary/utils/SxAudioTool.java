package com.nandi.lqsbaselibrary.utils;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.media.MediaRecorder;

import java.io.IOException;

/**
 * Date:2018/4/25
 * Time:下午2:09
 * 音频工具类
 *
 * @author qingsong
 */
public class SxAudioTool {
    public static MediaRecorder mRecorder = null;
    public static MediaPlayer mPlayer = null;
    public static String mFileName;
    /**
     * 采样率
     */
    public static int SAMPLE_RATE_IN_HZ = 8000;

    /**
     * @param path      文件路径
     * @param extension 文件格式 如 .mp3、.arm、.3gp
     */
    @SuppressLint("NewApi")
    public static void startRecord(String path, String extension) {
        mFileName = path + extension;
        // 实例化MediaRecorder
        if (mRecorder == null) {
            mRecorder = new MediaRecorder();
        }
        // 设置音频源为MIC
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        // 设置输出文件的格式
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        //		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        // 设置输出文件的名称
        mRecorder.setOutputFile(mFileName);
        //设置音频的编码格式
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        //		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
        //设置采样率
        mRecorder.setAudioSamplingRate(SAMPLE_RATE_IN_HZ);

        try {
            //得到设置的音频来源，编码器，文件格式等等内容，在start()之前调用
            mRecorder.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            //开始录音
            mRecorder.start();
        } catch (Exception e) {
            mRecorder = null;
            mRecorder = new MediaRecorder();
        }
    }

    /**
     * 停止录音
     * @return 文件路径
     */
    public static String stopRecord() {

        try {
            mRecorder.stop();
        } catch (Exception e) {
            //释放资源
            mRecorder = null;
            mRecorder = new MediaRecorder();
        }
        //释放资源
        mRecorder.release();
        mRecorder = null;

        return mFileName;
    }

    /**
     * 开始播放
     * @param Filename Filename
     */
    public static void startPlaying(String Filename) {

        if (mPlayer == null) {
            mPlayer = new MediaPlayer();
        }

        try {
            mPlayer.setDataSource(Filename);
            mPlayer.prepare();
            mPlayer.start();
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mPlayer.release();
                    mPlayer = null;
                }
            });
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止播放
     */
    public static void stopPlaying() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }
}
