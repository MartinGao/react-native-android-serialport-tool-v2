using ReactNative.Bridge;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace React.Native.Android.Serialport.Tool.V2.RNReactNativeAndroidSerialportToolV2
{
    /// <summary>
    /// A module that allows JS to share data.
    /// </summary>
    class RNReactNativeAndroidSerialportToolV2Module : NativeModuleBase
    {
        /// <summary>
        /// Instantiates the <see cref="RNReactNativeAndroidSerialportToolV2Module"/>.
        /// </summary>
        internal RNReactNativeAndroidSerialportToolV2Module()
        {

        }

        /// <summary>
        /// The name of the native module.
        /// </summary>
        public override string Name
        {
            get
            {
                return "RNReactNativeAndroidSerialportToolV2";
            }
        }
    }
}
