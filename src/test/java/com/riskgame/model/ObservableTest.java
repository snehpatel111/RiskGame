package com.riskgame.model;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Observer;

public class ObservableTest {

    private Observable observable;
    private TestObserver observer1;
    private TestObserver observer2;

    @Before
    public void setUp() {
        observable = new Observable();
        observer1 = new TestObserver();
        observer2 = new TestObserver();
    }

    @Test
    public void testAttach() {
        observable.attach(observer1);
        observable.attach(observer2);

        Assert.assertEquals(2, observable.getAttachedObserverCount());
    }

    @Test
    public void testDetach() {
        observable.attach(observer1);
        observable.attach(observer2);

        observable.detach(observer1);

        Assert.assertEquals(1, observable.getAttachedObserverCount());
    }

    @Test
    public void testNotifyObservers() {
        observable.attach(observer1);
        observable.attach(observer2);

        observable.notifyObservers(observable);

        Assert.assertTrue(observer1.isUpdated());
        Assert.assertTrue(observer2.isUpdated());
    }

    // Custom Observer for testing
    private static class TestObserver implements Observer {
        private boolean updated = false;

        @Override
        public void update(java.util.Observable o, Object arg) {
            updated = true;
        }

        public boolean isUpdated() {
            return updated;
        }
    }
}
