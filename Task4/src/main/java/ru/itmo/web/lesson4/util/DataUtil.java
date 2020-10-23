package ru.itmo.web.lesson4.util;

import ru.itmo.web.lesson4.model.Post;
import ru.itmo.web.lesson4.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static ru.itmo.web.lesson4.model.Color.*;


public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov", RED),
            new User(6, "pashka", "Pavel Mavrin", RED),
            new User(9, "geranazarov555", "Georgiy Nazarov", GREEN),
            new User(11, "tourist", "Gennady Korotkevich", BLUE)
    );

    private static final List<Post> POSTS = Arrays.asList(
            new Post(1, "Codeforces Global Round 11", "Hi!\n" +
                    "\n" +
                    "On суббота, 10 октября 2020 г. в 17:35 we will host Codeforces Global Round 11.\n" +
                    "\n" +
                    "This is the fifth round of the 2020 series of Codeforces Global Rounds. The rounds are open and rated for everybody.\n" +
                    "\n" +
                    "The prizes for this round are as follows:\n" +
                    "\n" +
                    "    30 best participants get a t-shirt.\n" +
                    "    20 t-shirts are randomly distributed among those with ranks between 31 and 500, inclusive.\n" +
                    "\n" +
                    "The prizes for the 6-round series in 2020:\n" +
                    "\n" +
                    "    In each round top-100 participants get points according to the table.\n" +
                    "    The final result for each participant is equal to the sum of points he gets in the four rounds he placed the highest.\n" +
                    "    The best 20 participants over all series get sweatshirts and place certificates.\n" +
                    "\n" +
                    "Thanks to XTX, which in 2020 supported the global rounds initiative!\n" +
                    "\n" +
                    "Problems for this round are set by me. Thanks a lot to the coordinator antontrygubO_o, to the testers dacin21, Giada, H4CKOM, DimmyT, postscript, oolimry, RedDreamer, PM11, Tlatoani, growup974, nvmdava, bhaskarjoshi2001, dorijanlendvaj, and to MikeMirzayanov for the Codeforces and Polygon platforms.\n" +
                    "\n" +
                    "The round will have 8 problems and will last 180 minutes.\n" +
                    "\n" +
                    "Why such a scoring distribution?\n" +
                    "\n" +
                    "I hope you will have fun solving the problems!", 1, "MikeMirzayanov", new Date().toString()),
            new Post(2, "Mirror of Bubble Cup 13 Finals on Codeforces", "Contest will take place on Sunday, 4th of October at 11AM CEST, virtually. Live results will be available on the official Bubble Cup website (results will be frozen during the last 45 minutes of the competition). Winners will be announced at the closing ceremony. You can find more info on the BubbleCup website.\n" +
                    "\n" +
                    "Just like the previous editions, this final will be followed by an online mirror competition on Codeforces. Mirror will take place on Monday, 5th of October at 15:05 CEST. Contest will last for 3 hours and ACM ICPC rules will be applied. It will be a competition for teams of 1-3 members. There will be at least eight problems.\n" +
                    "\n" +
                    "Just like last year, the finals are divided in two \"divisions\", called Premier League and Rising Stars. The two contests will have most of their problems in common, but the Rising Stars competition will feature some easier tasks targeted at high school contestants.\n" +
                    "\n" +
                    "Both of the contests will be mirrored here on Codeforces, with Premier League mapping to the Div1 contest and Rising Stars mapping to the Div2 contest. The mirror will use native Codeforces ACM-ICPC team contest rules.\n" +
                    "\n" +
                    "We kindly ask participants of the virtual finals to hold off discussing problems publicly until the mirror is over.\n" +
                    "\n" +
                    "Contest was mainly prepared by employees of MDCS with help from our alumni member Lazar Milenković (milenkoviclazar). We give our thanks to Nikolay Kalinin (KAN) for the round coordination, Mike Mirzayanov (MikeMirzayanov) and the team behind Codeforces and Polygon platforms. Special thanks goes to Alexandr Lyashko (knightL) for helping out with problem testing.\n" +
                    "\n" +
                    "The contest will be unrated. The reason for this is because rules of this contest are not common for Codeforces.\n" +
                    "\n" +
                    "Editorial will be available in the booklets section on the Bubble Cup website sometime after the online mirror ends.\n" +
                    "\n" +
                    "You can find problems from previous finals on our Codeforces online mirror competitions:\n" +
                    "\n" +
                    "Bubble Cup 8 — Finals [Online Mirror]\n" +
                    "\n" +
                    "Bubble Cup 9 — Finals [Online Mirror]\n" +
                    "\n" +
                    "Bubble Cup X — Finals [Online Mirror]\n" +
                    "\n" +
                    "Bubble Cup 11 — Finals [Online Mirror, Div. 1]\n" +
                    "\n" +
                    "Bubble Cup 11 — Finals [Online Mirror, Div. 2]\n" +
                    "\n" +
                    "Bubble Cup 12 — Finals [Online Mirror, Div. 1]\n" +
                    "\n" +
                    "Bubble Cup 12 — Finals [Online Mirror, Div. 2]\n" +
                    "\n" +
                    "We wish you best of luck in competition!\n" +
                    "\n" +
                    "Update #1: Given the current situation we want everyone to be safe and enjoy the Bubble Cup finals from their home and that's why team members will be allowed to work on different machines.", 9, "geranazarov555", new Date().toString())

    );

    public static void addData(HttpServletRequest request, Map<String, Object> data) {
        data.put("users", USERS);
        data.put("posts", POSTS);

        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(request.getParameter("logged_user_id"))) {
                data.put("user", user);
            }
        }
    }

    public static Long findUserById(long id) {
        for (User user : USERS) {
            if (user.getId() == id) {
                return id;
            }
        }
        return null;
    }
}
