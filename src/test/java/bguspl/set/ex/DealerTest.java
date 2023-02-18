package bguspl.set.ex;


import bguspl.set.Config;
import bguspl.set.Env;
import bguspl.set.UserInterface;
import bguspl.set.Util;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DealerTest {

    Player player;
    @Mock
    Util util;
    @Mock
    private UserInterface ui;
    @Mock
    private Table table;
    @Mock
    private Dealer dealer;
    @Mock
    private Logger logger;

    private Player[] players;





    void assertInvariants() {
        assertTrue(player.id >= 0);
        assertTrue(player.getScore() >= 0);
    }

    @BeforeEach
    void setUp() {
        Env env = new Env(logger, new Config(logger, ""), ui, util);
        table = new Table(env, new Integer[env.config.tableSize], new Integer[env.config.deckSize]);
        player = new Player(env, dealer, table, 0, false);
        players = new Player[1];
        players[0] = player;
        dealer = new Dealer(env, table, players);
        assertInvariants();
    }

    @AfterEach
    void tearDown() {
        assertInvariants();
    }

    void putCardsOnTableAndKey(){
        dealer.placeCardsOnTable();
        for (Player player : players){
            player.keyPressed(1);
        }
    }
// pre-condition : there is a card in the table and the player has token in slot 1
    @Test
    void RemovedCard(){
        putCardsOnTableAndKey();
        int card = table.slotToCard[1];
        assertTrue(player.tokens[1]);
        dealer.removeCard(card);
        assertNull(table.slotToCard[1]);
        assertFalse(player.tokens[1]);

    }
    // post-condition : the player dont have token in slot 1 and the card is null after removed



    //pre-condition : the time is <= 60 second
    @Test
    void checkTimerOfThread(){
        dealer.updateTimerDisplay(true);
        long time = dealer.getTime() - System.currentTimeMillis();
        assertTrue(time > 59900);
    }

    //post-condition : the timer is

}