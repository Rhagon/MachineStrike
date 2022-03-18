package machinestrike.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static machinestrike.util.Orientation.*;

public class OrientationTest {

    @Test
    public void testAdd() {
        for(Orientation orientation : Orientation.values()) {
            Assertions.assertEquals(orientation, orientation.add(NORTH));
        }
        Assertions.assertEquals(SOUTH, WEST.add(WEST));
        Assertions.assertEquals(SOUTH, EAST.add(EAST));
        Assertions.assertEquals(EAST, SOUTH.add(WEST));
    }

    @Test
    public void testSubtract() {
        for(Orientation orientation : Orientation.values()) {
            Assertions.assertEquals(orientation.add(SOUTH), orientation.subtract(SOUTH));
        }
        Assertions.assertEquals(NORTH, EAST.subtract(EAST));
        Assertions.assertEquals(SOUTH, EAST.subtract(WEST));
    }

    @Test
    public void testOrientationFromIllegalValue() throws NoSuchMethodException, IllegalAccessException {
        Method m = Orientation.class.getDeclaredMethod("fromValue", int.class);
        m.setAccessible(true);
        try {
            m.invoke(null, 5);
            Assertions.fail("Expected a throw");
        } catch(InvocationTargetException e) {
            Assertions.assertSame(IllegalArgumentException.class, e.getTargetException().getClass());
        }
    }


}
