package org.fpezzato.lib.util;

import android.view.View;

import org.fpezzato.lib.BuildConfig;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.fpezzato.lib.util.FakeViewGroupMarginLayoutParams.aFakeViewGroupMarginLayoutParams;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class OverlappingDetectorTest {

    @Rule public MockitoRule mockito = MockitoJUnit.rule();

    @Spy
    View spyView1 = new View(RuntimeEnvironment.application);

    @Spy
    View spyView2 = new View(RuntimeEnvironment.application);


    @Test
    public void shouldDetectOverlapWhenBottomOfView1PartiallyAboveMarginOfView2() throws Exception {
        setTop(spyView1, 30, 0);
        setBottom(spyView1, 40, 0);

        setTop(spyView2, 50, 10); // 50-10 = 40 from top!
        setBottom(spyView2, 100, 0);
        boolean actual = OverlappingDetector.isVerticalOverlapping(spyView1, spyView1);

        assertTrue(actual);

    }

    @Test
    public void shouldDetectOverlapWhenView1CompletelyWithinView2() throws Exception {
        setTop(spyView1, 30, 0);
        setBottom(spyView1, 200, 0);

        setTop(spyView2, 0, 0);
        setBottom(spyView2, 100, 0);

        boolean actual = OverlappingDetector.isVerticalOverlapping(spyView1, spyView2);

        assertTrue(actual);
    }

    @Test
    public void shouldNotDetectOverlapWhenView1NotOverlapsView2AsAbove() throws Exception {
        setTop(spyView1, 30, 10);
        setBottom(spyView1, 40, 0);

        setTop(spyView2, 60, 0);
        setBottom(spyView2, 70, 0);

        boolean actual = OverlappingDetector.isVerticalOverlapping(spyView1, spyView2);

        assertFalse(actual);

    }

    @Test
    public void shouldNotDetectOverlapWhenView1NotOverlapsView2AsBelow() throws Exception {
        setTop(spyView1, 100, 10);
        setBottom(spyView1, 140, 0);

        setTop(spyView2, 60, 0);
        setBottom(spyView2, 70, 0);

        boolean actual = OverlappingDetector.isVerticalOverlapping(spyView1, spyView2);

        assertFalse(actual);
    }


    public void setTop(View spyView, int top, int marginTop) throws Exception {
        when(spyView.getTop()).thenReturn(top);

        when(spyView.getLayoutParams())
                .thenReturn(aFakeViewGroupMarginLayoutParams().withMarginTop(marginTop));
    }

    public void setBottom(View spyView, int bottom, int marginBottom) throws Exception {
        when(spyView.getBottom()).thenReturn(bottom);

        when(spyView.getLayoutParams())
                .thenReturn(aFakeViewGroupMarginLayoutParams().withMarginBottom(marginBottom));
    }

}