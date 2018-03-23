package hu.uni.miskolc.iit.ilona.bluetooth.proximity.model;

import android.content.Context;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.MediumTest;

import com.example.android.test.R;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

/**
 * Created by iasatan on 2018.03.21..
 */
@RunWith(AndroidJUnit4.class)
@MediumTest
public class SearchResultTest {

    SearchResult result;
    Resources res;
    private Context instrumentationCtx;

    @Before
    public void setUp() throws Exception {
        instrumentationCtx = InstrumentationRegistry.getContext();
        res = getInstrumentation().getTargetContext().getResources();
        result = new SearchResult(res.getDrawable(R.drawable.sa0), "Satan Adam", "Student", 1);
    }

    @Test
    public void getName() throws Exception {
        String actual = result.getName();
        String expected = "Satan Adam";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getTitle() throws Exception {
        String actual = result.getTitle();
        String expected = "Student";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getRoomId() throws Exception {
        int actual = result.getRoomId();
        int expected = 1;
        Assert.assertEquals(expected, actual);
    }

}