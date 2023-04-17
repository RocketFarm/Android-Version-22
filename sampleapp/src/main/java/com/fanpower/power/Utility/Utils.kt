package com.fanpower.power.Utility

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.inputmethod.InputMethodManager
import android.webkit.ValueCallback
import android.webkit.WebView
import android.widget.EditText
import com.google.gson.Gson
import com.google.gson.GsonBuilder


class Utils {


    companion object{

        public var testHTML: String = "<html><body><![CDATA[<p>This weekend, NASCAR&#8217;s premier series returns to Nashville Superspeedway for just the second time.</p><p>The Cup Series heads back to the 1.33-mile concrete oval in Lebanon, Tennessee, for the Ally 400 on Sunday (5 p.m. ET, NBC/NBC Sports App, MRN, SiriusXM NASCAR Radio) after the series&#8217; off weekend.</p><p>Just 10 races remain in the regular season. The final stretch before the playoffs begins now.</p><p><b>GETTING REPS</b></p><p>Drivers will get a full 50-minute practice session to open their weekend on Friday (6:30 p.m. ET, USA Network, MRN).</p><p>Nashville marks the fifth of six race tracks to host an extended practice session this year and the last until Championship Weekend kicks off at Phoenix Raceway in November.</p><p>Friday&#8217;s racing rehearsal will be followed up by single-lap, single-car qualifying on Saturday afternoon (1 p.m. ET, USA, MRN). The fastest five drivers from Group A and the fastest five from Group B will advance to the second round of qualifying, where those 10 drivers will post one more timed lap. The fastest of those 10 will start Sunday&#8217;s race from the pole position.</p><p><b>RELATED: <a href=\"https://www.nascar.com/wp-content/uploads/sites/7/2022/06/22/Cup-Qualifying-Order-Nashville.pdf\" target=\"\" rel=\"noopener\">See this week&#8217;s qualifying order</a> | <a href=\"https://www.nascar.com/weekend-schedule\">Weekend schedule</a> | </b><a href=\"https://www.nascar.com/standings/nascar-cup-series/\"><b>Cup Series standings</b></a><b> </b></p><p><b>NASCAR IN NASHVILLE</b></p><p><span style=\"font-weight: 400;\">&#8212; Nashville Superspeedway&#8217;s 1.33-mile concrete oval layout sits 30 miles southeast of Nashville, Tennessee. </span></p><p><span style=\"font-weight: 400;\">&#8212; The track opened in 2001 and was owned by Dover Motorsports, Inc. until Speedway Motorsports purchased Dover and its properties in December 2021.</span></p><p><span style=\"font-weight: 400;\">&#8212; Myriad series races at Nashville from its debut year forward, including the Xfinity and Camping World Truck Series, ARCA, ARCA East and IndyCar. Xfinity races twice annually at the track from 2002-11. Trucks, meanwhile, competed once a year from 2001-09 and added a second date for the 2010-11 seasons after the closure of Memphis Motorsports Park.</span></p><p><span style=\"font-weight: 400;\">&#8212; The track was put up for sale in 2012 after Dover Motorsports decided it would no longer sanction NASCAR races at the facility. </span></p><p><span style=\"font-weight: 400;\">&#8212; Nashville remained available for private use and became a popular NASCAR testing facility and venue for commercial and film opportunities.</span></p><p><span style=\"font-weight: 400;\">&#8212; NASCAR held its season-ending banquet in Nashville in 2019, bolstering the sport&#8217;s return to the area.</span></p><p><span style=\"font-weight: 400;\">&#8212; The Cup Series made its debut at the track in June 2021, the track&#8217;s first NASCAR race weekend since 2011.</span></p><p><i><span style=\"font-weight: 400;\">Source: Racing Insights</span></i></p><p><b>GOODYEAR TIRES</b></p><p><span lang=\"EN\">While there is plenty of &#8220;new&#8221; in 2022, NASCAR Cup Series teams enter this weekend with a good notebook on these tires, according to Goodyear. </span></p><p>The tire combination used this weekend is the same combination teams competed with at both Kansas Speedway and Texas Motor Speedway. Additionally, the left-side tire compound was utilized at Dover Motor Speedway while the right-side compound was used at Charlotte Motor Speedway.</p><p><span lang=\"EN\">In a press release, Goodyear noted its minimum recommended air pressure for left-rear tires was &#8220;of particular importance&#8221; this week, re-emphasizing the higher loads impacting that corner of the car compared to the previous generation car. Setup components will also affect how the tires wear throughout the weekend.</span></p><p><u></u><span lang=\"EN\">“Every time we race on a concrete track, like the one we have at Nashville this week, we design our tires to specifically lay rubber on the surface,” said Greg Stucker, Goodyear&#8216;s director of racing. “Rubbering in the primary groove helps the racing by causing drivers to move around to find the grip provided by fresher concrete. As we come back from an off weekend for the Cup cars, having a full practice, it will be important for teams to find the right balance in their car set-ups on the left rear of the car. Having an established tire set-up they have run before should help them with that.”</span><u></u><u></u></p><p><b>NASHVILLE STORY LINES </b></p><p><span style=\"font-weight: 400;\">&#8212; </span>Kyle Larson is the defending race winner, leading 264 of 300 laps in last year&#8217;s inaugural Nashville race.</p><p><span style=\"font-weight: 400;\">&#8212; Aric Almirola won the pole for last year&#8217;s race, which featured 11 cautions and an average green-flag stretch of 20 laps.</span></p><p><span style=\"font-weight: 400;\">&#8212; Twelve different drivers have gone to Victory Lane in 2022, leaving just four playoff positions available with 10 races to go in the regular season.</span></p><p><span style=\"font-weight: 400;\">&#8212; Four drivers (Austin Cindric, Chase Briscoe, Ross Chastain and Daniel Suárez) have collected their first career wins this year, the most in 16 races since 1950.</span></p><p><span style=\"font-weight: 400;\">&#8212; </span>Of the eight active Cup champions, only Martin Truex Jr, Kevin Harvick and Brad Keselowski have yet to win in 2022. They have combined for just six top fives.</p><p><span style=\"font-weight: 400;\">&#8212; Harvick is in the midst of a 59-race winless streak, the second longest of his career.</span></p><p><span style=\"font-weight: 400;\">&#8212; The longest top-10 streak this season is five (Christopher Bell, Kyle Busch and Chase Elliott).</span></p><p><span style=\"font-weight: 400;\">&#8212; Michael McDowell&#8217;s six top 10s and 38 laps led this year are career-high marks.</span></p><p><i><span style=\"font-weight: 400;\">Source: Racing Insights</span></i></p><p><b>BEST OF THE BUNCH</b></p><p>With just one Cup race in the books at Nashville, there&#8217;s not a lot of data to rely on for this week&#8217;s favorites.</p><p>But it should come as no surprise Kyle Larson is BetMGM&#8217;s opening favorite at 5-1 odds after his dominant 2021 performance. Anytime one driver manages to lead 88% of the race and finish the job with a win, leaving them off your bet slip or fantasy team the next time around is a bad move.</p><p>Kyle Busch (7-1) has two Xfinity wins and a Truck Series triumph on his Nashville resume, but the two-time Cup champ finished 11th at Nashville last season. Chase Elliott (9-1) earned a Dover win in May, conquering the concrete in Delaware for his first win of the year. Will that translate to success in Nashville on Sunday?</p><p>Ross Chastain (8-1) earned a season-best runner-up finish at Nashville a season ago. Now a two-time Cup race winner, Chastain enters as a known threat for this week&#8217;s checkered flag.</p><p><b data-stringify-type=\"bold\">RELATED: <a href=\"https://www.nascar.com/gallery/nascar-betting-2022-nashville-superspeedway-odds/#photo-1\">Betting odds for Nashville</a></p><p></b><div class=\\\"pu-prop-embed\\\" data-pickup-prop-id=\\\"25452\\\"><section><a href=\\\"https://playpickup.com/news/Array / surez-vs-chastain-who-wins-in-nashville - 25452\\\" rel=\\\"follow\\\" title=\\\"Suárez vs. Chastain: Who wins in Nashville? - Powered By PickUp\\\">Suárez vs. Chastain: Who wins in Nashville? - Powered By PickUp</a></section></div><b data-stringify-type=\"bold\"><br /></b></p><p><strong>FANTASY LIVE</strong></p><p><span style=\"font-weight: 400;\">Want to manage a team and race your way to the top of the leaderboards? Check out NASCAR Fantasy Live, which is open now. The free-to-play game lets you choose your drivers each week and show off your crew-chief instincts by garaging a driver by the end of Stage 3, and there is a $25,000 prize for the winner.</span></p><p><span style=\"font-weight: 400;\">The 2022 Fantasy Live points leaders are </span><span style=\"font-weight: 400;\">Chase Elliott (530), Ross Chastain (509) and Kyle Busch (506).</span></p><p><a href=\"https://www.nascar.com/news-media/2022/02/15/nascar-fantasy-game-rules/\"><b>How to play: Fantasy Live</b></a><b> | </b><a href=\"https://fantasygames.nascar.com/live/picks\"><b>Set up a team today!</b></a></p><p><b>ALSO ON NASCAR.COM</b></p><p><span style=\"font-weight: 400;\">Get additional camera views by logging on to NASCAR Drive, where each week a select number of in-car cameras will be available — as well as a battle cam and an overhead look.</span></p><p><span style=\"font-weight: 400;\">NASCAR has partnered with LiveLike to add fan engagement in the NASCAR Mobile App. Log in to the mobile app during the race for polls, quizzes, the cheer meter and more — and see instant results from NASCAR fans like you.</span></p>]]></body></html>"
        public var testHTMLWithId: String = "<html><body><![CDATA[<p>This weekend, NASCAR&#8217;s premier series returns to Nashville Superspeedway for just the second time.</p><p>The Cup Series heads back to the 1.33-mile concrete oval in Lebanon, Tennessee, for the Ally 400 on Sunday (5 p.m. ET, NBC/NBC Sports App, MRN, SiriusXM NASCAR Radio) after the series&#8217; off weekend.</p><p>Just 10 races remain in the regular season. The final stretch before the playoffs begins now.</p><p><b>GETTING REPS</b></p><p>Drivers will get a full 50-minute practice session to open their weekend on Friday (6:30 p.m. ET, USA Network, MRN).</p><p>Nashville marks the fifth of six race tracks to host an extended practice session this year and the last until Championship Weekend kicks off at Phoenix Raceway in November.</p><p>Friday&#8217;s racing rehearsal will be followed up by single-lap, single-car qualifying on Saturday afternoon (1 p.m. ET, USA, MRN). The fastest five drivers from Group A and the fastest five from Group B will advance to the second round of qualifying, where those 10 drivers will post one more timed lap. The fastest of those 10 will start Sunday&#8217;s race from the pole position.</p><p><b>RELATED: <a href=\"https://www.nascar.com/wp-content/uploads/sites/7/2022/06/22/Cup-Qualifying-Order-Nashville.pdf\" target=\"\" rel=\"noopener\">See this week&#8217;s qualifying order</a> | <a href=\"https://www.nascar.com/weekend-schedule\">Weekend schedule</a> | </b><a href=\"https://www.nascar.com/standings/nascar-cup-series/\"><b>Cup Series standings</b></a><b> </b></p><p><b>NASCAR IN NASHVILLE</b></p><p><span style=\"font-weight: 400;\">&#8212; Nashville Superspeedway&#8217;s 1.33-mile concrete oval layout sits 30 miles southeast of Nashville, Tennessee. </span></p><p><span style=\"font-weight: 400;\">&#8212; The track opened in 2001 and was owned by Dover Motorsports, Inc. until Speedway Motorsports purchased Dover and its properties in December 2021.</span></p><p><span style=\"font-weight: 400;\">&#8212; Myriad series races at Nashville from its debut year forward, including the Xfinity and Camping World Truck Series, ARCA, ARCA East and IndyCar. Xfinity races twice annually at the track from 2002-11. Trucks, meanwhile, competed once a year from 2001-09 and added a second date for the 2010-11 seasons after the closure of Memphis Motorsports Park.</span></p><p><span style=\"font-weight: 400;\">&#8212; The track was put up for sale in 2012 after Dover Motorsports decided it would no longer sanction NASCAR races at the facility. </span></p><p><span style=\"font-weight: 400;\">&#8212; Nashville remained available for private use and became a popular NASCAR testing facility and venue for commercial and film opportunities.</span></p><p><span style=\"font-weight: 400;\">&#8212; NASCAR held its season-ending banquet in Nashville in 2019, bolstering the sport&#8217;s return to the area.</span></p><p><span style=\"font-weight: 400;\">&#8212; The Cup Series made its debut at the track in June 2021, the track&#8217;s first NASCAR race weekend since 2011.</span></p><p><i><span style=\"font-weight: 400;\">Source: Racing Insights</span></i></p><p><b>GOODYEAR TIRES</b></p><p><span lang=\"EN\">While there is plenty of &#8220;new&#8221; in 2022, NASCAR Cup Series teams enter this weekend with a good notebook on these tires, according to Goodyear. </span></p><p>The tire combination used this weekend is the same combination teams competed with at both Kansas Speedway and Texas Motor Speedway. Additionally, the left-side tire compound was utilized at Dover Motor Speedway while the right-side compound was used at Charlotte Motor Speedway.</p><p><span lang=\"EN\">In a press release, Goodyear noted its minimum recommended air pressure for left-rear tires was &#8220;of particular importance&#8221; this week, re-emphasizing the higher loads impacting that corner of the car compared to the previous generation car. Setup components will also affect how the tires wear throughout the weekend.</span></p><p><u></u><span lang=\"EN\">“Every time we race on a concrete track, like the one we have at Nashville this week, we design our tires to specifically lay rubber on the surface,” said Greg Stucker, Goodyear&#8216;s director of racing. “Rubbering in the primary groove helps the racing by causing drivers to move around to find the grip provided by fresher concrete. As we come back from an off weekend for the Cup cars, having a full practice, it will be important for teams to find the right balance in their car set-ups on the left rear of the car. Having an established tire set-up they have run before should help them with that.”</span><u></u><u></u></p><p><b>NASHVILLE STORY LINES </b></p><p><span style=\"font-weight: 400;\">&#8212; </span>Kyle Larson is the defending race winner, leading 264 of 300 laps in last year&#8217;s inaugural Nashville race.</p><p><span style=\"font-weight: 400;\">&#8212; Aric Almirola won the pole for last year&#8217;s race, which featured 11 cautions and an average green-flag stretch of 20 laps.</span></p><p><span style=\"font-weight: 400;\">&#8212; Twelve different drivers have gone to Victory Lane in 2022, leaving just four playoff positions available with 10 races to go in the regular season.</span></p><p><span style=\"font-weight: 400;\">&#8212; Four drivers (Austin Cindric, Chase Briscoe, Ross Chastain and Daniel Suárez) have collected their first career wins this year, the most in 16 races since 1950.</span></p><p><span style=\"font-weight: 400;\">&#8212; </span>Of the eight active Cup champions, only Martin Truex Jr, Kevin Harvick and Brad Keselowski have yet to win in 2022. They have combined for just six top fives.</p><p><span style=\"font-weight: 400;\">&#8212; Harvick is in the midst of a 59-race winless streak, the second longest of his career.</span></p><p><span style=\"font-weight: 400;\">&#8212; The longest top-10 streak this season is five (Christopher Bell, Kyle Busch and Chase Elliott).</span></p><p><span style=\"font-weight: 400;\">&#8212; Michael McDowell&#8217;s six top 10s and 38 laps led this year are career-high marks.</span></p><p><i><span style=\"font-weight: 400;\">Source: Racing Insights</span></i></p><p><b>BEST OF THE BUNCH</b></p><p>With just one Cup race in the books at Nashville, there&#8217;s not a lot of data to rely on for this week&#8217;s favorites.</p><p>But it should come as no surprise Kyle Larson is BetMGM&#8217;s opening favorite at 5-1 odds after his dominant 2021 performance. Anytime one driver manages to lead 88% of the race and finish the job with a win, leaving them off your bet slip or fantasy team the next time around is a bad move.</p><p>Kyle Busch (7-1) has two Xfinity wins and a Truck Series triumph on his Nashville resume, but the two-time Cup champ finished 11th at Nashville last season. Chase Elliott (9-1) earned a Dover win in May, conquering the concrete in Delaware for his first win of the year. Will that translate to success in Nashville on Sunday?</p><p>Ross Chastain (8-1) earned a season-best runner-up finish at Nashville a season ago. Now a two-time Cup race winner, Chastain enters as a known threat for this week&#8217;s checkered flag.</p><p><b data-stringify-type=\"bold\">RELATED: <a href=\"https://www.nascar.com/gallery/nascar-betting-2022-nashville-superspeedway-odds/#photo-1\">Betting odds for Nashville</a></p><p></b><div class=\"pu-prop-embed\" id=\"fanpower\"  data-pickup-prop-id=\\\"25452\\\"><section><a href=\\\"https://playpickup.com/news/Array / surez-vs-chastain-who-wins-in-nashville - 25452\\\" rel=\\\"follow\\\" title=\\\"Suárez vs. Chastain: Who wins in Nashville? - Powered By PickUp\\\">Suárez vs. Chastain: Who wins in Nashville? - Powered By PickUp</a></section></div><b data-stringify-type=\"bold\"><br /></b></p><p><strong>FANTASY LIVE</strong></p><p><span style=\"font-weight: 400;\">Want to manage a team and race your way to the top of the leaderboards? Check out NASCAR Fantasy Live, which is open now. The free-to-play game lets you choose your drivers each week and show off your crew-chief instincts by garaging a driver by the end of Stage 3, and there is a $25,000 prize for the winner.</span></p><p><span style=\"font-weight: 400;\">The 2022 Fantasy Live points leaders are </span><span style=\"font-weight: 400;\">Chase Elliott (530), Ross Chastain (509) and Kyle Busch (506).</span></p><p><a href=\"https://www.nascar.com/news-media/2022/02/15/nascar-fantasy-game-rules/\"><b>How to play: Fantasy Live</b></a><b> | </b><a href=\"https://fantasygames.nascar.com/live/picks\"><b>Set up a team today!</b></a></p><p><b>ALSO ON NASCAR.COM</b></p><p><span style=\"font-weight: 400;\">Get additional camera views by logging on to NASCAR Drive, where each week a select number of in-car cameras will be available — as well as a battle cam and an overhead look.</span></p><p><span style=\"font-weight: 400;\">NASCAR has partnered with LiveLike to add fan engagement in the NASCAR Mobile App. Log in to the mobile app during the race for polls, quizzes, the cheer meter and more — and see instant results from NASCAR fans like you.</span></p>]]><br /> <br /> <br /> <br /> <br /> <br /> </body></html>"

        public var testHtmlWithAId = "<html>\n" +
                "<body>\n" +
                "\n" +
                "<h2><a id=\"top\">Some heading</a></h2>\n" +
                "\n" +
                "<p>Lots of text....</p>\n" +
                "<p>Lots of text....</p>\n" +
                "<p>Lots of text....</p>\n" +
                "<p>Lots of text....</p>\n" +
                "<p>Lots of text....</p>\n" +
                "<p>Lots of text....</p>\n" +
                "<p>Lots of text....</p>\n" +
                "<p>Lots of text....</p>\n" +
                "<p>Lots of text....</p>\n" +
                "<p>Lots of text....</p>\n" +
                "<p>Lots of text....</p>\n" +
                "<p>Lots of text....</p>\n" +
                "<p>Lots of text....</p>\n" +
                "<p>Lots of text....</p>\n" +
                "<p>Lots of text....</p>\n" +
                "<p>Lots of text....</p>\n" +
                "<p>Lots of text....</p>\n" +
                "<p>Lots of text....</p>\n" +
                "<div class =\"test\" id=\"fanpower\">Some heading</div>\n" +
                "<p>Lots of text....</p>\n" +
                "<p>Lots of text....</p>\n" +
                "<p>Lots of text....</p>\n" +
                "<p>Lots of text....</p>\n" +
                "<p>Lots of text....</p>\n" +
                "<p>Lots of text....</p>\n" +
                "<p>Lots of text....</p>\n" +
                "<p>Lots of text....</p>\n" +
                "<p>Lots of text....</p>\n" +
                "<p>Lots of text....</p>\n" +
                "<p>Lots of text....</p>\n" +
                "<p>Lots of text....</p>\n" +
                "\n" +
                "<a href=\"#top\">Go to top</a>\n" +
                "\n" +
                "</body>\n" +
                "</html>"

        public var testHTMLUpper: String = "<![CDATA[This weekend, NASCAR&#8217;s premier series returns to Nashville Superspeedway for just the second time. The Cup Series heads back to the 1.33-mile concrete oval in Lebanon, Tennessee, for the Ally 400 on Sunday (5 p.m. ET, NBC/NBC Sports App, MRN, SiriusXM NASCAR Radio) after the series&#8217; off weekend. Just 10 races remain in the regular [&hellip;]]]>\",\n" +
                "\t\t\t\t\"Primary_Category\": \"Series\",\n" +
                "\t\t\t\t\"Body Top\": \"<![CDATA[<p>This weekend, NASCAR&#8217;s premier series returns to Nashville Superspeedway for just the second time.</p><p>The Cup Series heads back to the 1.33-mile concrete oval in Lebanon, Tennessee, for the Ally 400 on Sunday (5 p.m. ET, NBC/NBC Sports App, MRN, SiriusXM NASCAR Radio) after the series&#8217; off weekend.</p><p>Just 10 races remain in the regular season. The final stretch before the playoffs begins now.</p><p><b>GETTING REPS</b></p><p>Drivers will get a full 50-minute practice session to open their weekend on Friday (6:30 p.m. ET, USA Network, MRN).</p><p>Nashville marks the fifth of six race tracks to host an extended practice session this year and the last until Championship Weekend kicks off at Phoenix Raceway in November.</p><p>Friday&#8217;s racing rehearsal will be followed up by single-lap, single-car qualifying on Saturday afternoon (1 p.m. ET, USA, MRN). The fastest five drivers from Group A and the fastest five from Group B will advance to the second round of qualifying, where those 10 drivers will post one more timed lap. The fastest of those 10 will start Sunday&#8217;s race from the pole position.</p><p><b>RELATED: <a href=\\\"https://www.nascar.com/wp-content/uploads/sites/7/2022/06/22/Cup-Qualifying-Order-Nashville.pdf\\\" target=\\\"\\\" rel=\\\"noopener\\\">See this week&#8217;s qualifying order</a> | <a href=\\\"https://www.nascar.com/weekend-schedule\\\">Weekend schedule</a> | </b><a href=\\\"https://www.nascar.com/standings/nascar-cup-series/\\\"><b>Cup Series standings</b></a><b> </b></p><p><b>NASCAR IN NASHVILLE</b></p><p><span style=\\\"font-weight: 400;\\\">&#8212; Nashville Superspeedway&#8217;s 1.33-mile concrete oval layout sits 30 miles southeast of Nashville, Tennessee. </span></p><p><span style=\\\"font-weight: 400;\\\">&#8212; The track opened in 2001 and was owned by Dover Motorsports, Inc. until Speedway Motorsports purchased Dover and its properties in December 2021.</span></p><p><span style=\\\"font-weight: 400;\\\">&#8212; Myriad series races at Nashville from its debut year forward, including the Xfinity and Camping World Truck Series, ARCA, ARCA East and IndyCar. Xfinity races twice annually at the track from 2002-11. Trucks, meanwhile, competed once a year from 2001-09 and added a second date for the 2010-11 seasons after the closure of Memphis Motorsports Park.</span></p><p><span style=\\\"font-weight: 400;\\\">&#8212; The track was put up for sale in 2012 after Dover Motorsports decided it would no longer sanction NASCAR races at the facility. </span></p><p><span style=\\\"font-weight: 400;\\\">&#8212; Nashville remained available for private use and became a popular NASCAR testing facility and venue for commercial and film opportunities.</span></p><p><span style=\\\"font-weight: 400;\\\">&#8212; NASCAR held its season-ending banquet in Nashville in 2019, bolstering the sport&#8217;s return to the area.</span></p><p><span style=\\\"font-weight: 400;\\\">&#8212; The Cup Series made its debut at the track in June 2021, the track&#8217;s first NASCAR race weekend since 2011.</span></p><p><i><span style=\\\"font-weight: 400;\\\">Source: Racing Insights</span></i></p><p><b>GOODYEAR TIRES</b></p>\n" +
                "<div class=\\\\\\\"pu-prop-embed\\\\\\\" data-pickup-prop-id=\\\\\\\"25452\\\\\\\"><section><a href=\\\\\\\"https://playpickup.com/news/Array / surez-vs-chastain-who-wins-in-nashville - 25452\\\\\\\" rel=\\\\\\\"follow\\\\\\\" title=\\\\\\\"Suárez vs. Chastain: Who wins in Nashville? - Powered By PickUp\\\\\\\">Suárez vs. Chastain: Who wins in Nashville? - Powered By PickUp</a></section></div><p><span lang=\\\"EN\\\">While there is plenty of &#8220;new&#8221; in 2022, NASCAR Cup Series teams enter this weekend with a good notebook on these tires, according to Goodyear. </span></p><p>The tire combination used this weekend is the same combination teams competed with at both Kansas Speedway and Texas Motor Speedway. Additionally, the left-side tire compound was utilized at Dover Motor Speedway while the right-side compound was used at Charlotte Motor Speedway.</p><p><span lang=\\\"EN\\\">In a press release, Goodyear noted its minimum recommended air pressure for left-rear tires was &#8220;of particular importance&#8221; this week, re-emphasizing the higher loads impacting that corner of the car compared to the previous generation car. Setup components will also affect how the tires wear throughout the weekend.</span></p><p><u></u><span lang=\\\"EN\\\">“Every time we race on a concrete track, like the one we have at Nashville this week, we design our tires to specifically lay rubber on the surface,” said Greg Stucker, Goodyear&#8216;s director of racing. “Rubbering in the primary groove helps the racing by causing drivers to move around to find the grip provided by fresher concrete. As we come back from an off weekend for the Cup cars, having a full practice, it will be important for teams to find the right balance in their car set-ups on the left rear of the car. Having an established tire set-up they have run before should help them with that.”</span><u></u><u></u></p><p><b>NASHVILLE STORY LINES </b></p><p><span style=\\\"font-weight: 400;\\\">&#8212; </span>Kyle Larson is the defending race winner, leading 264 of 300 laps in last year&#8217;s inaugural Nashville race.</p><p><span style=\\\"font-weight: 400;\\\">&#8212; Aric Almirola won the pole for last year&#8217;s race, which featured 11 cautions and an average green-flag stretch of 20 laps.</span></p><p><span style=\\\"font-weight: 400;\\\">&#8212; Twelve different drivers have gone to Victory Lane in 2022, leaving just four playoff positions available with 10 races to go in the regular season.</span></p><p><span style=\\\"font-weight: 400;\\\">&#8212; Four drivers (Austin Cindric, Chase Briscoe, Ross Chastain and Daniel Suárez) have collected their first career wins this year, the most in 16 races since 1950.</span></p><p><span style=\\\"font-weight: 400;\\\">&#8212; </span>Of the eight active Cup champions, only Martin Truex Jr, Kevin Harvick and Brad Keselowski have yet to win in 2022. They have combined for just six top fives.</p><p><span style=\\\"font-weight: 400;\\\">&#8212; Harvick is in the midst of a 59-race winless streak, the second longest of his career.</span></p><p><span style=\\\"font-weight: 400;\\\">&#8212; The longest top-10 streak this season is five (Christopher Bell, Kyle Busch and Chase Elliott).</span></p><p><span style=\\\"font-weight: 400;\\\">&#8212; Michael McDowell&#8217;s six top 10s and 38 laps led this year are career-high marks.</span></p><p><i><span style=\\\"font-weight: 400;\\\">Source: Racing Insights</span></i></p><p><b>BEST OF THE BUNCH</b></p><p>With just one Cup race in the books at Nashville, there&#8217;s not a lot of data to rely on for this week&#8217;s favorites.</p><p>But it should come as no surprise Kyle Larson is BetMGM&#8217;s opening favorite at 5-1 odds after his dominant 2021 performance. Anytime one driver manages to lead 88% of the race and finish the job with a win, leaving them off your bet slip or fantasy team the next time around is a bad move.</p><p>Kyle Busch (7-1) has two Xfinity wins and a Truck Series triumph on his Nashville resume, but the two-time Cup champ finished 11th at Nashville last season. Chase Elliott (9-1) earned a Dover win in May, conquering the concrete in Delaware for his first win of the year. Will that translate to success in Nashville on Sunday?</p><p>Ross Chastain (8-1) earned a season-best runner-up finish at Nashville a season ago. Now a two-time Cup race winner, Chastain enters as a known threat for this week&#8217;s checkered flag.</p><p><b data-stringify-type=\\\"bold\\\">RELATED: <a href=\\\"https://www.nascar.com/gallery/nascar-betting-2022-nashville-superspeedway-odds/#photo-1\\\">Betting odds for Nashville</a></p><p></b><b data-stringify-type=\\\"bold\\\"><br /></b></p><p><strong>FANTASY LIVE</strong></p><p><span style=\\\"font-weight: 400;\\\">Want to manage a team and race your way to the top of the leaderboards? Check out NASCAR Fantasy Live, which is open now. The free-to-play game lets you choose your drivers each week and show off your crew-chief instincts by garaging a driver by the end of Stage 3, and there is a \$25,000 prize for the winner.</span></p><p><span style=\\\"font-weight: 400;\\\">The 2022 Fantasy Live points leaders are </span><span style=\\\"font-weight: 400;\\\">Chase Elliott (530), Ross Chastain (509) and Kyle Busch (506).</span></p><p><a href=\\\"https://www.nascar.com/news-media/2022/02/15/nascar-fantasy-game-rules/\\\"><b>How to play: Fantasy Live</b></a><b> | </b><a href=\\\"https://fantasygames.nascar.com/live/picks\\\"><b>Set up a team today!</b></a></p><p><b>ALSO ON NASCAR.COM</b></p><p><span style=\\\"font-weight: 400;\\\">Get additional camera views by logging on to NASCAR Drive, where each week a select number of in-car cameras will be available — as well as a battle cam and an overhead look.</span></p><p><span style=\\\"font-weight: 400;\\\">NASCAR has partnered with LiveLike to add fan engagement in the NASCAR Mobile App. Log in to the mobile app during the race for polls, quizzes, the cheer meter and more — and see instant results from NASCAR fans like you.</span></p>]]>"

        @JvmStatic
        fun getWebJQueryRects(webView: WebView, queries: List<String>, callback: (rects: List<Rect>) -> Unit) {
            var serializedQueries = Gson().toJson(queries);
            var js: String = "(function(){" +
                    "var results = [];" +
                    "for (var query of $serializedQueries) {" +
                    "console.log(\$(query));" +
                    "results.push(\$(query)[0].getBoundingClientRect());" +
                    "}" +
                    "console.log(JSON.stringify({'rects':results}));" +
                    "return JSON.stringify({'rects':results});" +
                    "})()";
            getWebRects(webView,js,callback)
        }


        @JvmStatic
        fun getWebClassRects(webView: WebView, queries: List<String>, callback: (rects: List<Rect>) -> Unit) {
            var serializedQueries = Gson().toJson(queries);
            var js: String = "(function(){\n" +
                    "var results = [];\n" +
                    "for (var query of $serializedQueries) {\n" +
                    "console.log(query);\n" +
                    "var divs = document.getElementsByClassName(query);\n" +
                    "for (var div of divs)\n" +
                    "results.push(div.getBoundingClientRect());\n" +
                    "}\n" +
                    "console.log(JSON.stringify({'rects':results}));\n" +
                    "return JSON.stringify({'rects':results});\n" +
                    "})()\n";
            getWebRects(webView,js,callback)
        }


        @JvmStatic
        fun getWebIDRects(webView: WebView, queries: List<String>, callback: (rects: List<Rect>) -> Unit) {
            var serializedQueries = Gson().toJson(queries);
            var js: String = "(function(){" +
                    "var results = [];" +
                    "for (var query of $serializedQueries) {" +
                    "console.log(query);" +
                    "results.push(document.getElementById(query).getBoundingClientRect());" +
                    "}" +
                    "console.log(JSON.stringify({'rects':results}));" +
                    "return JSON.stringify({'rects':results});" +
                    "})()";
            getWebRects(webView,js,callback)
        }

        @JvmStatic
        fun getWebTagRects(webView: WebView, queries: List<String>, callback: (rects: List<Rect>) -> Unit) {
            var serializedQueries = Gson().toJson(queries);
            var js: String = "(function(){\n" +
                    "var results = [];\n" +
                    "for (var query of $serializedQueries) {\n" +
                    "console.log(query);\n" +
                    "var divs = document.getElementsByTagName(query);\n" +
                    "console.log(divs);\n" +
                    "for (var div of divs)\n" +
                    "results.push(div.getBoundingClientRect());\n" +
                    "}\n" +
                    "console.log(JSON.stringify({'rects':results}));\n" +
                    "return JSON.stringify({'rects':results});\n" +
                    "})()\n";
            getWebRects(webView,js,callback)
        }

        @JvmStatic
        private fun getWebRects(webView: WebView, js: String, callback: (rects: List<Rect>) -> Unit) {
            webView.evaluateJavascript(js,
                object : ValueCallback<String> {
                    override fun onReceiveValue (value: String) {
                        val rects: ArrayList<Rect> = ArrayList();
                        val webViewRect = Rect();
                        webView.getGlobalVisibleRect(webViewRect)
                        try {
                            val jsRects = GsonBuilder().setLenient().create().fromJson<JSRectList>(
                                value.substring(1, value.length - 1).replace("\\", ""),
                                JSRectList::class.java
                            )
                            for (jsRect in jsRects.rects) {
                                val rect = Rect(
                                    jsRect.left.toInt(),
                                    jsRect.top.toInt(),
                                    jsRect.right.toInt(),
                                    jsRect.bottom.toInt()
                                )
                                rect.offset(webViewRect.left, webViewRect.top)
                                rects.add(rect)
                            }
                        }catch (e: Exception)
                        {
                            e.printStackTrace()
                        }
                        callback(rects)
                    }
                });
        }

        data class JSRectList(
            var rects: List<JSRect> ) {
        }

        data class JSRect(
            var x: Float = 0.0f,
            var y: Float = 0.0f,
            var width: Float = 0.0f,
            var height: Float = 0.0f,
            var top: Float = 0.0f,
            var bottom: Float = 0.0f,
            var left: Float = 0.0f,
            var right: Float = 0.0f) {
        }

        fun convertPixelsToDp(px: Float, context: Context): Float {
            return px / (context.getResources().getDisplayMetrics().densityDpi as Float / DisplayMetrics.DENSITY_DEFAULT)
        }

        fun dpFromPx(context: Context, px: Float): Float {
            return px / context.resources.displayMetrics.density
        }

        fun pxFromDp(context: Context, dp: Float): Float {
            return dp * context.resources.displayMetrics.density
        }



    }
}