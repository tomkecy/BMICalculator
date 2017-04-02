package cybulski.tomasz.tomaszcybulskilab1;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *Dodatkowo testy UI w Espresso
 * Dodatkowo obczaić w appium i robotium
 *
 * można obczaić butterknife - służy do redukcji kodu
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("cybulski.tomasz.tomaszcybulskilab1", appContext.getPackageName());
    }
}
