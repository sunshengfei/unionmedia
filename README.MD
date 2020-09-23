
### 引入

```gradle
implementation 'com.github.sunshengfei:unionmedia:1.0.1'
```

如依赖未能同步，请导入jitpack仓库配置，

**build.gradle(project)**

```gradle
buildscript {
    repositories {
        jcenter()
        //...
        maven { url 'https://jitpack.io' }
    }
}
```

###1 引入外部aar包至app项目

    thirdparty文件夹中

1. Baidu_MobAds_SDK-release.aar
2. oaid_sdk_1.0.22.aar

###2 Add these code to files
 
####2.1 app:AndroidManifest.xml

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

####2.2 network_security_config.xml

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

###3 Usage

> 设置 Bridge.AppId = "你的BSSP sid" 

> 初始化Fragment，ContentFrameFragment fragment = new ContentFrameFragment()

> 使用fragment即可

> * 修改频道：
    改变BaiDuProvider.userMaps即可，该值为 List<ChannelMap> 


