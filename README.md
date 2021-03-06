
# react-native-react-native-android-serialport-tool-v2

## Getting started

`$ npm install react-native-react-native-android-serialport-tool-v2 --save`

### Mostly automatic installation

`$ react-native link react-native-react-native-android-serialport-tool-v2`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-react-native-android-serialport-tool-v2` and add `RNReactNativeAndroidSerialportToolV2.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNReactNativeAndroidSerialportToolV2.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNReactNativeAndroidSerialportToolV2Package;` to the imports at the top of the file
  - Add `new RNReactNativeAndroidSerialportToolV2Package()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-react-native-android-serialport-tool-v2'
  	project(':react-native-react-native-android-serialport-tool-v2').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-react-native-android-serialport-tool-v2/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-react-native-android-serialport-tool-v2')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNReactNativeAndroidSerialportToolV2.sln` in `node_modules/react-native-react-native-android-serialport-tool-v2/windows/RNReactNativeAndroidSerialportToolV2.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using React.Native.Android.Serialport.Tool.V2.RNReactNativeAndroidSerialportToolV2;` to the usings at the top of the file
  - Add `new RNReactNativeAndroidSerialportToolV2Package()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNReactNativeAndroidSerialportToolV2 from 'react-native-react-native-android-serialport-tool-v2';

// TODO: What to do with the module?
RNReactNativeAndroidSerialportToolV2;
```
  