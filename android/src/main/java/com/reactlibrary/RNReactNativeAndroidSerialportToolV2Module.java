package com.reactlibrary;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;

import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;

import com.facebook.react.modules.core.DeviceEventManagerModule;

import cn.shorr.serialport.SerialPortConfig;
import cn.shorr.serialport.SerialPortFinder;
import cn.shorr.serialport.SerialPortUtil;
import cn.shorr.serialport.SerialRead;
import cn.shorr.serialport.SerialWrite;

import android.content.pm.ActivityInfo;
import android.util.Log;
import android.text.TextUtils;

import java.lang.String;
import java.util.*;
import java.util.ArrayList;

public class RNReactNativeAndroidSerialportToolV2Module extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  private byte[] buffer;
  private String cache = "init";
  private ArrayList<String> cacheArray = new ArrayList<String>();

  public RNReactNativeAndroidSerialportToolV2Module(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNReactNativeAndroidSerialportToolV2";
  }

  @Override
  public boolean canOverrideExistingModule() {
    return true;
  }

  @ReactMethod
  public void setPortrait(final Promise promise) {
    reactContext.getCurrentActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
  }

  @ReactMethod
  public void setReversePortrait(final Promise promise) {
    reactContext.getCurrentActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
  }

  @ReactMethod
  public void setLandscape(final Promise promise) {
    reactContext.getCurrentActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
  }

  @ReactMethod
  public void getAllDevices(final Promise promise) {
    // SerialPortFinder serialPortFinder = new SerialPortFinder();
    // String[] devices = serialPortFinder.getAllDevicesPath();
    // if (devices == null) { 
    //   promise.resolve("NO DEVICE"); 
    // } else {
    //   StringBuilder builder = new StringBuilder();
    //   for(String s : devices) { builder.append(s); }
    //   promise.resolve("HAS DEVICE :" + String.valueOf(devices.length) + builder.toString());
    //   this.startSerialPortConnect();
    // }
    this.startSerialPortConnect();
  }

  @ReactMethod
  public void readDataFromCache(final Promise promise) {
    promise.resolve(cache);
  }

  @ReactMethod
  public void sendData(final String payload, final Promise promise) {
    // SerialWrite.sendData(reactContext.getCurrentActivity(), 0, payload.getBytes());
    SerialWrite.sendData(reactContext.getCurrentActivity(), 0, decode(payload));
  }

  public static byte[] decode(String src) {
      if (null == src || 0 == src.length()) {
          return null;
      }
      byte[] ret = new byte[src.length() / 2];
      byte[] tmp = src.getBytes();
      for (int i = 0; i < (tmp.length / 2); i++) {
          ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
      }
      return ret;
  }

  public static byte uniteBytes(byte src0, byte src1) {
      byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
              .byteValue();
      _b0 = (byte) (_b0 << 4);
      byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
              .byteValue();
      byte ret = (byte) (_b0 ^ _b1);
      return ret;
  }

  public static String encode(byte[] str) {
      String hexString = "0123456789ABCDEF";
      // 根据默认编码获取字节数组
      byte[] bytes = str;
      StringBuilder sb = new StringBuilder(bytes.length * 2);
      // 将字节数组中每个字节拆解成2位16进制整数
      for (int i = 0; i < bytes.length; i++) {
          sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
          sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
      }
      return sb.toString();
  }

// 5AA501030100000000000307
// 5AA501FF0100000000000303

  //1.开始串口连接
  private void startSerialPortConnect() {
      //配置串口参数
      // SerialPortUtil serialPortUtil = new SerialPortUtil(reactContext.getCurrentActivity(), new SerialPortConfig("/dev/ttyS3", 9600));
      SerialPortUtil serialPortUtil = new SerialPortUtil(reactContext.getCurrentActivity(), new SerialPortConfig("/dev/ttyS0", 19200));
      //绑定串口服务
      serialPortUtil.bindService();
      SerialRead serial0Read = new SerialRead(reactContext.getCurrentActivity());
      serial0Read.registerListener(0/*默认为0，此参数可省略*/, new Serial0ReadListener());
      // reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("onReadData", "Start" + " " + "/dev/ttyS3" + " " + "9600");
  }

  private class Serial0ReadListener implements SerialRead.ReadDataListener {
    @Override
    public void onReadData(byte[] data) {
      // String str = new String(data);
      String str = encode(data);
      Log.i("Serial", "onReadData " + " str: " + str);
      cacheArray.add(str);
      String foo = checkCacheArray();
      if (foo != "STOP") {
        cache = foo;
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("SerialOnReadData", foo);
      } 
    }

    public String checkCacheArray() {
      String foo =  TextUtils.join("", cacheArray);
      Log.i("Serial", "checkCacheArray at begin " + " foo: " + foo + " size: " + foo.length());
      if (foo.length() < 10) {
        return "STOP";
      } else {
        String head = foo.substring(0, 2);
        String tail = foo.substring(foo.length() - 8);
        Log.i("Serial", "checkCacheArray " + " foo: " + foo + " head: " + head + " tail: " + tail);
        if (head.equals("AA") && tail.equals("CC33C33C")) {
          Log.i("Serial", "checkCacheArray GOOD" + " return foo: " + foo);
          cacheArray.clear();
          return foo;
        } else {
          Log.i("Serial", "checkCacheArray NOT GOOD???");
          return "STOP";
        }
      }
    }
  }

  // private class Serial0ReadListener implements SerialRead.ReadDataListener {
  //   @Override
  //   public void onReadData(byte[] data) {
  //     // String str = new String(data);
  //     String str = encode(data);
  //     cache = str;
  //     reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit("SerialOnReadData", str);
  //   }
  // }
}
