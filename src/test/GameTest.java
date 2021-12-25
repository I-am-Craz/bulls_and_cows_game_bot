import org.telegram.Game;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;

public class GameTest{

    @Test
    public void getCowsTest(){
        assertEquals(2, Game.getCows(2345, 4567));
        assertEquals(0, Game.getCows(3427, 6518));
        assertEquals(3, Game.getCows(1693, 9146));
    }

    @Test
    public void getBullsTest(){
        assertEquals(2, Game.getBulls(2345, 4365));
        assertEquals(0, Game.getBulls(3427, 6518));
        assertEquals(1, Game.getBulls(1693, 9614));
    }

}