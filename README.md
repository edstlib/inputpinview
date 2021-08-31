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
        app:shape="@drawable/shape_pin"
        app:length="4"
        app:width="@dimen/dimen_40dp"
        app:height="@dimen/dimen_50dp"
        app:textStyle="@style/pinstyle"
        app:textColor="#000000"
        app:margin="8dp" />
```
### Attributes information

An example is shown below.

```xml
        app:shape="@drawable/shape_pin"
        app:length="4"
        app:width="@dimen/dimen_40dp"
        app:height="@dimen/dimen_50dp"
        app:textStyle="@style/pinstyle"
        app:textColor="#000000"
        app:margin="8dp"
```

##### _app:shape_
[reference]: shape of input pin area, defaul

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

##### _app:length_
[integer]: length of pin, default 4

##### _app:width_
[dimension]: width of pin input area, default 40dp

##### _app:height_
[dimension]: height of pin input area, default 50dp

##### _app:textStyle_
[reference]: text style of pin, default not set

##### _app:textColor_
[color]: text color of pin, default not set

##### _app:margin_
[dimension]: space between pin input, default 8dp

### Listener when input completed
```kotlin
      findViewById<InputPINView>(R.id.inputPinView).delegate = object : InputPINDelegate {
            override fun send(pin: String) {
                Toast.makeText(this@MainActivity, pin, Toast.LENGTH_SHORT).show()
            }
        }
```





