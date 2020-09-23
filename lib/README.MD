Add these code to files
 
### app:AndroidManifest.xml

```xml

 <uses-permission android:name="android.permission.INTERNET" />
 <application
        android:networkSecurityConfig="@xml/network_security_config"
        android:theme="@style/AppTheme"
        tools:targetApi="n">

        <uses-library android:name="org.apache.http.legacy" android:required="false" />
        <!-- ...        -->

        <activity
            android:name="com.baidu.mobads.AppActivity"
            android:configChanges="keyboard|keyboardHidden|orientation"
            android:theme="@android:style/Theme.Translucent.NoTitleBar" />

        <provider
            android:name="com.baidu.mobads.openad.BdFileProvider"
            android:authorities="${applicationId}.bd.provider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/bd_file_paths" />
        </provider>
    </application>

```

### network_security_config.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <base-config cleartextTrafficPermitted="true">
        <trust-anchors>
            <certificates src="system" />
        </trust-anchors>
    </base-config>
    <debug-overrides>
        <trust-anchors>
            <certificates src="user" />
        </trust-anchors>
    </debug-overrides>
</network-security-config>
```

### Usage

> 设置 Bridge.AppId = "你的BSSP sid" 

> 初始化Fragment，ContentFrameFragment fragment = new ContentFrameFragment()

> 使用fragment即可

> * 修改频道：
    改变BaiDuProvider.userMaps即可，该值为 List<ChannelMap> 


