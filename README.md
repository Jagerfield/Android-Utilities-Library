# Android Utilities Library

While developing Andorid apps, I gathered the functions that I repeatedly used and kept them in a utility class. The number of these functions grew, in addition there was need to manage the “Permissions” in a clean easy way. Therefore, I created a library which currently provides the following utilities: **Permissions**, **Memory**, **Battery**, **Device Info**, **Network**, **SoftKeyboard**.

* The Permissions utility can be used for SDK versions 15-25 check.
* If the SDK version is < 23, then it will check for the existance of the permission in the Manifest.
* The library utilities' functions will return the name of the missing permission.

The images below are taken from running the app on a simulator.

<img src="https://github.com/Jagerfield/Android-Utilities-Library/blob/master/msc/pictures/utilities/permissions.png" width="160"/>  &#160; <img src="https://github.com/Jagerfield/Android-Utilities-Library/blob/master/msc/pictures/utilities/memory.png" width="160"/> &#160; <img src="https://github.com/Jagerfield/Android-Utilities-Library/blob/master/msc/pictures/utilities/network.png" width="160"/> &#160; <img src="https://github.com/Jagerfield/Android-Utilities-Library/blob/master/msc/pictures/utilities/device.png" width="160"/>  &#160;  <img src="https://github.com/Jagerfield/Android-Utilities-Library/blob/master/msc/pictures/utilities/battery.png" width="160"/>

The sample app for **Android Utilities Library** is available on Google Play:

<a href='https://play.google.com/store/apps/details?id=jagerfield.utilities'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png' width="193" height="75"/></a>

## Library Installation

In the app build.gradle add the following:

  Add JitPack repository at the end of repositories

  ```
  repositories
  {
     maven { url 'https://jitpack.io' }
  }
 ```
 Add the following dependency

  ```
  dependencies
  {
     compile 'com.github.Jagerfield:Android-Utilities-library:v2.3'
  }
  ```

##Checking Permissions
To test the Permissions utility:
```
1. Image 1
   a.In Settings->Apps->Android Utilities->App info->Permissions turn all the permission switches off.
2. Image 2-3
   a.In the first tab "Permissions".
   b.Click on "Check Permissions" button.
   c.And deny all permissions.
3. Image 4
   a.Click on "Get" button.
   b.Deny the first two permissions.
   c.For the last two permissions, check the boxes "Never Show Again", then deny them.
4. Image 5
   a.Click on "Get" button, and accept the two permissions showing in the denied permissions section.
5. Image 5
   a.Click then on the "Settings" button.
6. Image 6
   a.Go to the app permissions, switch all permissions on.
7. Image 7
   a.Click on "Check Permissions" button and check the final status.
```

<img src="https://github.com/Jagerfield/Android-Utilities-Library/blob/master/msc/pictures/permissions/1.png" width="160"/> &#160; <img src="https://github.com/Jagerfield/Android-Utilities-Library/blob/master/msc/pictures/permissions/2.png" width="160"/> &#160; <img src="https://github.com/Jagerfield/Android-Utilities-Library/blob/master/msc/pictures/permissions/3.png" width="160"/> &#160; <img src="https://github.com/Jagerfield/Android-Utilities-Library/blob/master/msc/pictures/permissions/4.png" width="160"/>  &#160; <img src="https://github.com/Jagerfield/Android-Utilities-Library/blob/master/msc/pictures/permissions/5.png" width="160"/>  &#160; <img src="https://github.com/Jagerfield/Android-Utilities-Library/blob/master/msc/pictures/permissions/6.png" width="160"/>  &#160; <img src="https://github.com/Jagerfield/Android-Utilities-Library/blob/master/msc/pictures/permissions/7.png" width="160"/>


##Permissions Util

PermissionsUtil contains the following functions:

| Value      | Function's Name | Returns  |
| :--------- |:--------------  | :------- |
| Permissions Request Code Id | ``` getPermissionsReqCodeId() ```| int |
|  Request Permissions     | ``` requestPermissions(String permissionsItem) ```| void |
|  Request Permissions     | ``` requestPermissions(String[] permissionsArray) ```| void |
|  Is Permission Granted     | ``` isPermissionGranted(String permission) ```| IGetPermissionResult |
|  Permission Results     | ``` getPermissionResults(String permissionItem) ```| IGetPermissionResult |
|  Permission Results     | ``` getPermissionResults(String[] permissionsArray) ```| IGetPermissionResult |

IGetPermissionResult contains the following functions:

| Value      | Function's Name | Returns  |
| :--------- |:--------------  | :------- |
| Is Granted | ``` isGranted() ```| boolean |
| Granted Permissions List | ``` getGrantedPermissionsList() ```| ArrayList<String>  |
| UserDenied Permissions List | ``` getUserDeniedPermissionsList() ```| ArrayList<String>  |
| Never Ask Again Permissions List | ``` getNeverAskAgainPermissionsList() ```| ArrayList<String> |
| List of Manifest Missing Permissions Sdk less M  | ``` getMissingInManifest_ForSdkBelowM() ```| ArrayList<String> |
| Requested Permission Status | ``` getRequestedPermissionStatus(String permissionName) ```| String |
| Requested Permissions And StatusMap | ``` getRequestedPermissionsAndStatusMap() ```| HashMap<String, String> |

### Managing permissions

- First, check the status of your app permissions.
The functions (getPermissionResults) and (requestPermissions) can either take an array or a string as an argument.

```
  PermissionsUtil permissionsUtil = AppUtilities.getPermissionUtil(activity);
  IGetPermissionResult result = permissionsUtil.getPermissionResults(PERMISSIONS_ARRAY);

        if (result.isGranted())
        {
            //Add your code here
        }
        else if (!result.isGranted() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            //There are missing permissions ask for them
            permissionsUtil.requestPermissions(PERMISSIONS_ARRAY);
        }
        else if (!result.isGranted() && Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
            //For SDK less than M, there are permissions missing in the manifest
            String missingPermissions = TextUtils.join(", ", result.getMissingInManifest_ForSdkBelowM()).trim();
            Toast.makeText(activity, "Following permissions are missing : " + missingPermissions, Toast.LENGTH_LONG).show();
            Log.e(C.TAG_LIB, "Following permissions are missing : " + missingPermissions);
        }
```

- The function (requestPermissions) in the previous paragraph will call the (checkSelfPermission), and the user will get to choose to accept or deny permissions. The system will then make a callback in (onRequestPermissionsResult) in the MainActivity.

```
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        PermissionsUtil permissionsUtil = AppUtilities.getPermissionUtil(activity);

          if (requestCode == permissionsUtil.getPermissionsReqCodeId())
          {
             IGetPermissionResult result = null;
             result = permissionsUtil.getPermissionResults(permissions);

             if (result == null) { return; }

             if (result.isGranted())
              {
                 //Add your code here
              }
             else
              {
                 //Write your code here
                 //For SDK >= M, there are permissions missing and you can get them.
                 String deniedPermissions = TextUtils.join(", ", result.getUserDeniedPermissionsList()).trim();
                 String neverAskAgainPermissions = TextUtils.join(", ", result.getNeverAskAgainPermissionsList()).trim();
              }
          }
     }
```

- For SDK >= M, if you want to let the library to show the user a dialog to manage missing permissions, then use this code:  

```
@Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        PermissionsUtil permissionsUtil = AppUtilities.getPermissionUtil(this);

        if (requestCode == permissionsUtil.getPermissionsReqCodeId())
        {
            IGetPermissionResult result = null;
            result = permissionsUtil.getPermissionResults(permissions);

            if (result.isGranted())
            {
                //Write your code here
                return;
            }

            PermissionsManager.getNewInstance(this, result, permissions, new PermissionsManager.PermissionsManagerCallback()
            {
                @Override
                public void onPermissionsGranted(IGetPermissionResult result) {

                    /**
                     * User accepted all requested permissions
                     */
                     
                    //Write your code here
                }

                @Override
                public void onPermissionsMissing(IGetPermissionResult result)
                {
                    //Write your code here
                    Toast.makeText(MainActivity.this, "User didn't accept all permissions", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
```

##Memory Util

Accessing a utility fucntion example:
```
AppUtilities.getMemoryUtil().getTotalRAM();
```
Available functions:

| Value      | Function's Name | Returns  |
| :------------- |:-------------| :-----|
| Total RAM      | ```getTotalRAM()```| long |
| Available External Memory Size      | ```getAvailableExternalMemorySize()``` | long |
| Has External SD Card      | ```isHasExternalSDCard()``` | boolean |
| Available Internal Memory Size      | ```getAvailableInternalMemorySize()``` | long |
| Total Internal Memory Size      | ```getTotalInternalMemorySize()``` | long |
| Total External Memory Size      | ```getTotalExternalMemorySize()``` | String |


##Battery Util

Accessing a utility fucntion example:
```
AppUtilities.getBatteryUtil().isBatteryPresent();
```
Available functions:

| Value         | Function Name | Returns  |
| :------------- |:-------------| :-----|
| Is Battery Present   |```isBatteryPresent()``` | boolean |
| Battery Temperature      | ```getBatteryTemperature()``` | float |
| Battery Percent      | ```getBatteryPercent()``` | int |
| Is Phone Charging      | ```isPhoneCharging()``` | boolean |
| Battery Health      | ```getBatteryHealth()``` | String |
| Battery Voltage      |```getBatteryVoltage()``` | int |
| Charging Source      | ```getChargingSource()``` | String |
| Battery Technology      | ```getBatteryTechnology()``` | String |


##Device Info Util

Accessing a utility fucntion example:
```
AppUtilities.getDeviceUtil().getDeviceName();
```
Available functions:

| Value         | Function Name | Returns  |
| :------------- |:-------------| :-----|
| Device Name | ```getDeviceName()``` | String |
| Version Name   | ```getVersionName(activity)``` | String |
| Serial Number| ```getSerial()``` | String |
| Sdk Version   | ```getSdkVersion()``` | int |
| Is Running On Emulator | ```isRunningOnEmulator()``` | boolean |
| Release Build Version  | ```getReleaseBuildVersion()``` | String |
| Package Name   | ```getPackageName(activity)``` | String |
| Build Version Code Name| ```getBuildVersionCodeName``` | String |
| Manufacturer  | ```getManufacturer()``` | String |
| Model    | ```getModel()``` | String |
| Product  | ```getProduct()``` | String |
| Finger print | ```getFingerprint()``` | String |
| Hardware | ```getHardware()``` | String |
| RadioVer | ```getRadioVer()``` | String |
| Device   | ```getDevice()``` | String |
| Board    | ```getBoard()``` | String |
| Display Version  | ```getDisplayVersion()``` | String |
| Build Host  | ```getBuildHost()``` | String |
| Build Time  | ```getBuildTime()``` | long |
| Build User  | ```getBuildUser()``` | String |
| OS Version  | ```getOsVersion()``` | String |
| Language    | ```getLanguage()``` | String |
| Screen Density | ```getScreenDensity(activity)``` | String |
| Screen Height  | ```getScreenHeight(activity)``` | int |
| Screen Width   | ```getScreenWidth(activity)``` | int |
| Version Code   | ```getVersionCode(activity)``` | Integer |
| Activity Name  | ```getActivityName(activity)``` | String |
| App Name  | ```getAppName(activity)``` | String |
| Device Ringer Mode  | ```getDeviceRingerMode(activity)``` | String |
| Is Device Rooted    | ```isDeviceRooted()``` | boolean |
| Android Id   | ```getAndroidId(activity)``` | String |
| Installation Source | ```getInstallSource(activity)``` | String |
| User Agent | ```getUserAgent(activity)``` | String |
| GSF Id | ```getDeviceUtil().getGSFId(activity)``` | String |


##Network Util

Accessing a utility fucntion example:

```
AppUtilities.getDeviceUtil().getDeviceName();
```
Available functions:

| Value         | Function Name | Returns  |
| :------------- |:-------------| :-----|
| IMEI      | ```getIMEI(activity)``` | String |
| IMSI      | ```getIMSI(activity)``` | String |
| Phone Type      | ```getPhoneType(activity)``` | String |
| Phone Number      | ```getPhoneNumber(activity)``` | String |
| Operator      | ```getOperator(activity)``` | String |
| SIM Serial      | ```getsIMSerial(activity)``` | String |
| Is SIM Locked      | ```isSimNetworkLocked(activity)``` | boolean |
| Is Wifi Enabled      | ```isWifiEnabled(activity)``` | boolean |
| Network Class      | ```getNetworkClass(activity)``` | String |
| Is Nfc Present      | ```isNfcPresent(activity)``` | boolean |
| Is Nfc Enabled      | ```isNfcEnabled(activity)``` | boolean |
| Internet Connection Type      | ```getInternetConnectionType(activity)``` | String |
| Has Internet Connection      | ```hasInternetConnection(activity)``` | boolean |
| Bluetooth MAC    | ```getBluetoothMAC(activity)``` | String |
| Wifi Mac Address   | ```getWifiMacAddress(activity)``` | String |


##SoftKeyborad Util

Accessing a utility fucntion example:

```
AppUtilities.setSoftKeyboard(Activity activity, boolean mode);
```
Available functions:

| Value         | Function Name | Returns  |
| :------------- |:-------------| :-----|
| Set Soft Keyboard      | ```setSoftKeyboard(Activity activity, boolean mode)``` | void |


## How to use?

The library's utilities can be either accessed individually, examples:
```
NetworkUtil networkUtil = NetworkUtil.newInstance(activity);
MemoryUtil memoryUtil = MemoryUtil.newInstance(activity);
```
Or through the "AppUtilities" class which serves as a container for for the utilities.
```
public class AppUtilities
{
    public static void getSoftKeyboard() { SoftKeyboardUtil.newInstance(); }

    public static PermissionsUtil getPermissionUtil(Activity activity) { return PermissionsUtil.newInstance(activity);}

    public static NetworkUtil getNetworkUtil() { return NetworkUtil.newInstance(); }

    public static MemoryUtil getMemoryUtil() { return MemoryUtil.newInstance(); }

    public static DeviceUtil getDeviceUtil() { return DeviceUtil.newInstance(); }

    public static BatteryUtil getDBatteryUtil() { return BatteryUtil.newInstance(); }
}
```

## Privacy Policy

This app sample will request the follwoing permissions below which require user approval and are used for demonstration purposes only. No data is shared or used outside this app.

* WRITE_EXTERNAL_STORAGE
* BODY_SENSORS
* READ_PHONE_STATE
* ACCESS_COARSE_LOCATION

Additionaly, utilities in this library require a number of permissions. These permissions are mentioned in the Manifest file and don't need user approval:

* ACCESS_NETWORK_STATE
* BLUETOOTH
* ACCESS_WIFI_STATE
* READ_GSERVICES


