/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SocialNetworkTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment
     * looks like.
     * Make sure you have partitions.
     */

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());

        assertTrue("expected empty graph", followsGraph.isEmpty());
    }

    @Test
    public void testGuessFollowsGraphSingleTweetNoMentions() {
        Tweet t1 = new Tweet(1, "user1", "is it reasonable to talk about rivest so much?",
                Instant.parse("2016-02-17T10:00:00Z"));
        List<Tweet> tweets = List.of(t1);
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);

        assertTrue("expected user1 to be in the follows graph", followsGraph.containsKey("user1"));
        assertTrue("expected user1 to follow no one", followsGraph.get("user1").isEmpty());
    }

    @Test
    public void testGuessFollowsGraphSingleTweetWithMention() {
        Tweet t1 = new Tweet(1, "user1", "Hello @user2!",
                Instant.parse("2016-02-17T10:00:00Z"));
        List<Tweet> tweets = List.of(t1);
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);

        assertTrue("expected user1 to be in the follows graph", followsGraph.containsKey("user1"));
        assertTrue("expected user1 to follow user2", followsGraph.get("user1").contains("user2"));
    }

    @Test
    public void testGuessFollowsGraphMultipleTweetsWithMentions() {
        var d = Instant.parse("2016-02-17T10:00:00Z");
        List<Tweet> tweets = List.of(
                new Tweet(1, "user1", "Hello @user2!", d),
                new Tweet(2, "user2", "Hi @user3!", d),
                new Tweet(3, "user3", "Hey @user1 and @user2!", d));
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);

        assertTrue("expected user1 to be in the follows graph", followsGraph.containsKey("user1"));
        assertTrue("expected user2 to be in the follows graph", followsGraph.containsKey("user2"));
        assertTrue("expected user3 to be in the follows graph", followsGraph.containsKey("user3"));

        assertTrue("expected user1 to follow user2", followsGraph.get("user1").contains("user2"));
        assertTrue("expected user2 to follow user3", followsGraph.get("user2").contains("user3"));
        assertTrue("expected user3 to follow user1", followsGraph.get("user3").contains("user1"));
        assertTrue("expected user3 to follow user2", followsGraph.get("user3").contains("user2"));
    }

    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);

        assertTrue("expected empty list", influencers.isEmpty());
    }

    @Test
    public void testInfluencers() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        followsGraph.put("a", new HashSet<>(Set.of("b", "c")));
        followsGraph.put("b", new HashSet<>(Set.of("c")));
        followsGraph.put("c", new HashSet<>());
        List<String> influencers = SocialNetwork.influencers(followsGraph);

        assertEquals("c", influencers.get(0));
        assertEquals("b", influencers.get(1));
        // assertEquals("a", influencers.get(2));
    }
    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
