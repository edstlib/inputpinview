# InputPINView

![InputPINView](https://i.ibb.co/X8tqXDk/inputpinview.png)
## Setup
### Gradle

Add this to your project level `build.gradle`:
```groovy
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```
Add this to your app `build.gradle`:
```groovy
dependencies {
    implementation 'com.github.edtslib:inputpinview:latest'
}
```
# Usage

The InputPINView is very easy to use. Just add it to your layout like any other view.
##### Via XML

Here's a basic implementation.

```xml
    <id.co.edtslib.inputpinview.InputPINView
    android:id="@+id/inputPinView"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:pinShape="@drawable/shape_pin"
    app:pinLength="4"
    app:pinWidth="@dimen/dimen_40dp"
    app:pinHeight="@dimen/dimen_50dp"
    app:pinTextColor="#000000"
    app:pinType="passwordWithEye"
    app:pinMargin="8dp" />
```
### Attributes information

An example is shown below.

```xml
    app:pinShape="@drawable/shape_pin"
    app:pinLength="4"
    app:pinWidth="@dimen/dimen_40dp"
    app:pinHeight="@dimen/dimen_50dp"
    app:pinTextColor="#000000"
    app:pinMargin="8dp"
    app:pinType="passwordWithEye"
    app:pinEyePassword="@drawable/img_password_eye"
```

##### _app:pinType_
[enum]: input type of PIN
1. number: pin show number
2. password: pin show *
3. passwordWithEye: number/passwword combination with icon eye

##### _app:pinEyePassword_
[reference]: eye of password if pinType = passwordWithEye
```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:drawable="@drawable/ic_visible" android:state_selected="true" />
    <item android:drawable="@drawable/ic_invisible" />
</selector>
```

##### _app:pinPasswordSymbol_
[string]: password symbol

##### _app:pinPasswordAnimate_
[integer]: delay time (millisecond) typed to password symbol

##### _app:pinShape_
[reference]: shape of input pin area, default

```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item>
        <shape android:shape="rectangle">
            <stroke android:color="#DCDEE3" android:width="1dp" />
            <solid android:color="#EFF3F6" />
        </shape>
    </item>
</selector>
```

##### _app:pinLength_
[integer]: length of pin, default 4

##### _app:pinWidth_
[dimension]: width of pin input area, default 40dp

##### _app:pinHeight_
[dimension]: height of pin input area, default 50dp

##### _app:pinTextStyle_
[reference]: text style of pin, default not set

##### _app:pinTextColor_
[color]: text color of pin, default not set

##### _app:pinMargin_
[dimension]: space between pin input, default 8dp

### Listener when input completed
```kotlin
      findViewById<InputPINView>(R.id.inputPinView).delegate = object : InputPINDelegate {
    override fun onCompleted(pin: String) {
        Toast.makeText(this@MainActivity, pin, Toast.LENGTH_SHORT).show()
    }
}
```





