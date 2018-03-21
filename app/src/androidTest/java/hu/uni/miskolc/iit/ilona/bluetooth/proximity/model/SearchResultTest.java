package hu.uni.miskolc.iit.ilona.bluetooth.proximity.model;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
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
        res.getDrawable(R.drawable.sa0);
        result = new SearchResult(res.getDrawable(R.drawable.sa0), "Satan Adam", "Student", 1);
    }

    @Test
    public void getImage() throws Exception {
        Drawable actual = result.getImage();
        Drawable expected = res.getDrawable(R.drawable.sa0);
        //Assert.assertEquals(actual, expected);
    }

    @Test
    public void getName() throws Exception {
        String actual = result.getName();
        String expected = "Satan Adam";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getTitle() throws Exception {
    }

    @Test
    public void getRoomId() throws Exception {
    }

}