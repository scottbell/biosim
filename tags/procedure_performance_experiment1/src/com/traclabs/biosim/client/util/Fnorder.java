package com.traclabs.biosim.client.util;

import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;

import com.traclabs.biosim.util.MersenneTwister;

/**
 * Title: Fnorder
 */

public class Fnorder {
    String currentString;

    Random myRandom;

    String[] adjectives = { "23rd ", "acceptable ", "acrobatic ", "alien ",
            "amiable ", "amoeboid ", "ancient ", "arbitrary ", "atomic ",
            "avenging ", "awesome ", "balanced ", "besotted ", "best ",
            "black ", "blue ", "calculating ", "cast iron ", "cat-like ",
            "cautious ", "Chinese ", "cold ", "communist ", "corrupt ",
            "dead ", "deadly ", "dehydrated ", "disguised ", "dizzy ",
            "drug-crazed ", "drunken ", "easy ", "electric ", "embossed ",
            "enormous ", "expensive ", "explosive ", "extraterrestrial ",
            "ferocious ", "frozen ", "furry ", "gelatinous ", "glowing ",
            "gnarly ", "gold ", "granular ", "greedy ", "green ", "high ",
            "highest ", "hot ", "humming ", "illuminated ", "imitation ",
            "impotent ", "impudent ", "impulsive ", "indictable ", "innocent ",
            "insane ", "Japanese ", "lecherous ", "lizard-like ", "lovely ",
            "maniacal ", "mauve ", "medium-sized ", "morbid ",
            "most influential ", "mutant ", "naughty ", "nuclear ", "oily ",
            "oozing ", "opaque ", "opulent ", "orbital ", "persuasive ",
            "pickled ", "poor ", "pregnant ", "protozoan ", "puce ",
            "pulsating ", "purple ", "putrid ", "radical ", "radioactive ",
            "red ", "reformed ", "reincarnated ", "rubber ", "Russian ",
            "screaming ", "sexy ", "shiftless ", "shifty ", "Siamese ",
            "silver ", "sin-ridden ", "sinister ", "sizzling ", "skeptical ",
            "slack-producing ", "sleeping ", "slick ", "slime-dripping ",
            "slimy ", "slippery ", "sluggish ", "smoking ", "solid gold ",
            "splendid ", "squamous ", "stoned ", "sweet ", "temporary ",
            "throbbing ", "tin-plated ", "tiny ", "transient ", "treacherous ",
            "tubular ", "ugly ", "untouchable ", "user-friendly ",
            "user-serviceable ", "vacant ", "vacillating ", "vampiric ",
            "vibrant ", "virginal ", "vivid ", "wealthy ", "well-dressed ",
            "white ", "wimpy ", "worthless ", "young ", "illegal ",
            "tax-free ", "drugged ", "polluted ", "flaming ", "diseased ",
            "agnostic ", "anorexic ", "conquering ", "cosmic ", "dancing ",
            "dyslexic ", "frenzied ", "lumpy ", "musical ", "plump ",
            "perfectly ordinary ", "French ", "Martian " };

    String[] names = { "007 ", "a dead relative ", "a long-lost uncle ",
            "a dead rock star ", "The Illigitimus ", "Abraham Lincoln ",
            "Agent Orange ", "Bill Gates ", "Albert Einstein ",
            "Alfred E. Neumann ", "Ali Baba ", "Aladdin ", "William Kida ",
            "a shadowy figure ", "Batman ", "Buck Rogers ", "Calvin ",
            "Chow Yun Fat ", "Captain Ahab ", "Captain America ",
            "Captain Kirk ", "Ratbert ", "Dogbert ", "Cthulhu ", "Dan Quayle ",
            "Dilbert ", "Darth Vader ", "Dave Letterman ", "Uncle Bob ",
            "Dr. Destructo ", "Dracula ", "Eddie Murphy ",
            "Edgar Rice Burroughs ", "Elvis ", "Michael Jackson ",
            "Evil Stevie ", "Fearless Leader ", "Ferdinand Marcos ",
            "Flamin' Jane ", "Flaming Carrot ", "Gary Shandling ", "George ",
            "George Lucas ", "Gerald Ford ", "Gandhi ", "Gorbachev ",
            "Grandmother ", "Gumby ", "Hamlet ", "Hobbes ", "Han Solo ",
            "Hitler ", "Ho Chi Minh ", "Hulk Hogan ", "Hillary ",
            "Hunter S. Thompson ", "Idi Amin ", "Internal Security ",
            "Isaac Asimov ", "Big Dick ", "Mayor Dinkens ", "Jesus ",
            "Jimmy Carter ", "Mayor McCheese ", "Madonna ", "Joseph Stalin ",
            "George Bush, Jr. ", "King Arthur ", "King Tut ", "Lex Luthor ",
            "Luke Skywalker ", "Manuel Noriega ", "Margaret Thatcher ",
            "Michael Jordan ", "Mick Jagger ", "Mohammed ", "Moses ",
            "Butt-head ", "Beevis ", "Mr. Science ", "Mr. Spock ", "Biff ",
            "Nancy Reagan ", "Nelson Mandela ", "Nixon ", "Norman Bates ",
            "Obi-Wan Kenobi ", "Oliver North ", "Paul ", "Zorro ",
            "Perry Mason ", "President Clinton ", "Princess Leia ", "Rambo ",
            "John Gray, Ph.D ", "Ringo ", "Robby the Robot ",
            "Robert Heinlein ", "Rocky ", "Roger Rabbit ", "Ronald Reagan ",
            "Scrooge ", "Sir Lancelot ", "Snoopy ", "Spaceman Spiff ",
            "Spiderman ", "Squad 23 ", "Tom Petty ", "Steven Spielberg ",
            "Superman ", "the A.C.L.U. ", "The Computer ", "the Discordian ",
            "the network guy ", "the Hand ", "The Illuminati ", "the Joker ",
            "the Legion of Doom ", "the ninja ", "the Pentagon ",
            "the Secret Master ", "the Secret Service ", "the vice squad ",
            "Thor ", "Timothy Leary ", "Tiny Tim ", "Tom Clancy ",
            "Tweety-Bird ", "Uncle Duke ", "Weird Al ", "Winston Churchill ",
            "yo' mama ", "your brother ", "your evil twin ", "your father ",
            "your mother ", "your sister ", "Zaphod Beeblebrox ", "Zeus ",
            "Zonker ", "Donovan's Brain ", "Tuos Mater ", "Tristero ",
            "the Archdean ", "Asmodeus ", "Archangel Gabriel ",
            "O.J. Simpson ", "Karie Norman ", "Scott Bell ",
            "David Kortenkamp ", "Bryn Wolfe ", "Tod Milam " };

    String[] places = { "(not available at your clearance) ",
            "Alpha Centauri ", "Alpha Complex ", "Atlantis ", "Austin ",
            "Berkeley ", "Berlin ", "Buckingham Palace ", "Callahan's Place ",
            "Cheyenne Mountain ", "Chicago ", "Cyberworld ", "Dallas ",
            "Death Valley ", "Dime Box ", "Endsville ", "Gasoline Alley ",
            "headquarters ", "Hell ", "Hollywood ", "Hong Kong ", "Iran ",
            "Iraq ", "Israel ", "Joe's Bar and Grill ", "Katmandu ",
            "Lake Geneva ", "Las Vegas ", "left field ", "Lithuania ",
            "London ", "Los Angeles ", "Main Street ", "Mars ",
            "Middle Earth ", "Mission Control ", "Mordor ", "Moscow ",
            "Munich ", "my back yard ", "New York ", "Peking ", "Poland ",
            "San Francisco ", "Siberia ", "Sixth Street ", "LIPS ",
            "Switzerland ", "Tel Aviv ", "the back forty ", "the Bastille ",
            "the bathroom ", "the best place possible ", "the brewery ",
            "the Bat Cave ", "the corner bar ", "the dentists' convention ",
            "the doghouse ", "the dumpster ", "the editorial department ",
            "the Empire State Building ", "the hackers' convention ",
            "the home of a trusted friend ", "the Hotel California ",
            "the Last National Bank ", "the North Pole ", "the ocean ",
            "the outback ", "the Pentagon ", "the Phoenix Project ",
            "the river ", "the same place as before ", "the service station ",
            "the South Pole ", "the Super Bowl ", "the tavern ",
            "the toxic waste dump ", "the U.S. Attorney's Office ",
            "the Vatican ", "the Watergate Hotel ", "the White House ",
            "the World Trade Center ", "Toledo ", "Topeka ", "Uranus ",
            "Wall Street ", "Washington, D.C. ", "you-know-where ",
            "your place ", "Yrth ", "the Death Star ",
            "beautiful downtown Burbank " };

    String[] prepositions = { "assumes responsibility for ",
            "avoids servants of ", "deals with ", "elopes with ",
            "evades agents of ", "flees from ", "flees to ", "flies to ",
            "flies toward ", "goes for ", "goes to ", "has finished in ",
            "has left with ", "hides in ", "is attacked by ",
            "is commanded by ", "is concerned about ", "is contaminated by ",
            "is destroyed by ", "is distressed by ", "is financed by ",
            "is fondled by ", "is found by ", "is imitated by ",
            "is infiltrated by ", "is joined by ", "is like a god to ",
            "is removed by ", "is the patron of ", "is threatened by ",
            "listens to ", "makes fun of ", "may not visit ", "moves to ",
            "originates from ", "reports to ", "retreats from ", "returns to ",
            "shoots henchmen of ", "should watch for ", "steals from ",
            "takes blame for ", "takes control of ", "takes no notice of ",
            "takes refuge in ", "travels to ", "walks to ",
            "was eliminated by ", "was seen in ", "will go to ",
            "withdraws from ", "assumed responsibility for ",
            "avoided servants of ", "has dealt with ", "eloped with ",
            "evaded agents of ", "fled from ", "fled to ", "flew to ",
            "flew toward ", "has gone for ", "went to ", "hides in ",
            "was attacked by ", "was commanded by ", "was concerned about ",
            "was contaminated by ", "was destroyed by ", "was distressed by ",
            "was financed by ", "was fondled by ", "was found by ",
            "was imitated by ", "was infiltrated by ", "was joined by ",
            "was removed by ", "was the patron of ", "was threatened by ",
            "listened to ", "made fun of ", "moved to ", "originated from ",
            "reported to ", "retreated from ", "returned to ",
            "shot henchmen of ", "watched for ", "stole from ",
            "took blame for ", "took control of ", "took no notice of ",
            "took refuge in ", "traveled to ", "walked to ", "withdrew from ",
            "plays with ", "played with ", "is assassinated by ",
            "was assassinated by ", "is boggled by ", "was boggled by ",
            "performs surgical alternations on " };

    String[] actions = { "amuses ", "avoids ", "berates ", "boggles ",
            "bothers ", "buries ", "catches ", "commands ", "contaminates ",
            "controls ", "converts ", "delivers ", "destroys ", "disfigures ",
            "eats ", "enters ", "fondles ", "handles ", "harasses ",
            "hassles ", "helps ", "imitates ", "infiltrates ", "inherits ",
            "joins ", "kills ", "leaves ", "massages ", "molests ",
            "persuades ", "perverts ", "pitches ", "rebuilds ", "reinforces ",
            "removes ", "replaces ", "resurrects ", "saves ", "serves ",
            "spanks ", "squeezes ", "strokes ", "subverts ", "swallows ",
            "swats ", "torments ", "tortures ", "transforms ", "whips ",
            "teases ", "stomps ", "mates with ", "tickles ", "audits ",
            "beats ", "defeats ", "outwits ", "manipulates ", "defects to ",
            "titillates ", "perverts ", "defenestrates ", "discards ",
            "abandons ", "talks to ", "talks back to ", "allies with ",
            "discovers ", "betrays ", "assassinates ", "promotes ",
            "pretends to be ", "disguises ", "disobeys ", "alters ",
            "intimidates " };

    String[] pronouns = { "his ", "my ", "our ", "the ", "your " };

    String[] intros = { "4 out of 5 dentists recommend that ",
            "Abort immediately unless ", "Abort previous sequence. ",
            "Advance code sequence. ", "Alert all stations! ",
            "Confirmed report: ", "Contrary to popular belief, ",
            "Delete all evidence that ", "Determine whether ",
            "E.F. Hutton says ", "Effective immediately, ",
            "Enemy agents now know that ", "Fnord! ", "Follow plan x if ",
            "Ignore previous message. ", "Ignore this message. ",
            "Imperative that ", "It appears that ", "It is not true that ",
            "Most people surveyed believe that ", "Observe and report if ",
            "Oral Roberts dreamed that ", "Our foes believe that ",
            "Our reporters claim that ", "Pentagon officials deny that ",
            "Please investigate report that ", "Step up operation. ",
            "Terminate operation if ", "The surgeon general warns that ",
            "Unsubstantiated rumor: ", "Urgent! ",
            "Usual sources confirm that ", "Warning! ", "We suspect that " };

    String[] nouns = { "911 file ", "(censored) ", "amethyst ", "amulet ",
            "ash tray ", "baby ", "BBS ", "beer bottle ", "blueprint ",
            "boat ", "book ", "bowling ball ", "business card ", "button ",
            "cable ", "cactus ", "cannibal ", "capsule ", "carnation ",
            "cash ", "cat ", "cauliflower ", "chainsaw ", "chair ", "chicken ",
            "club ", "cockroach ", "code wheel ", "coke can ", "compact disc ",
            "computer ", "cork ", "couch ", "cow ", "crystal ", "cummerbund ",
            "cyberdeck ", "demon ", "devil ", "diamond ", "dictaphone ",
            "dictator ", "dinosaur ", "disk drive ", "document ", "dragon ",
            "drug ", "duck ", "elephant ", "engine ", "eye ", "file ", "flag ",
            "floppy disk ", "fly ", "football ", "frame ", "frog ",
            "geographer ", "goldfish ", "grasshopper ", "grimoire ",
            "gyroslugger ", "hammer ", "hat ", "hat-rack ", "helmet ",
            "hemisphere ", "hot tub ", "hypodermic ", "ice cream ", "ID card ",
            "iguana ", "implement ", "infant ", "insect ", "jellybean ",
            "jet ", "jet ski ", "jukebox ", "kitten ", "Klingon ",
            "krugerrand ", "kumquat ", "lamp ", "light bulb ", "machine gun ",
            "mallet ", "manuscript ", "mason jar ", "message ", "mosquito ",
            "motorcycle ", "mouse ", "oar ", "octopus ", "olive ", "ostrich ",
            "paddle ", "paintbrush ", "paper clip ", "passport ",
            "password file ", "password ", "pendant ", "penguin ", "petunia ",
            "phased plasma rifle ", "phone ", "photocopy ", "piranha ",
            "pistol ", "pit viper ", "plant ", "playtester ", "pop tart ",
            "power drill ", "propeller ", "ptarmigan ", "pterodactyl ",
            "puppy ", "pyramid ", "racquetball ", "radio ", "railroad ",
            "razor ", "rescuer ", "ring ", "rom chip ", "saber ", "saxophone ",
            "scenario ", "scraper ", "screwdriver ", "scuba mask ",
            "sculpture ", "sex toy ", "shark ", "shoggoth ", "skateboard ",
            "ski lift ", "skillet ", "spark plug ", "spider ", "submarine ",
            "surfboard ", "sword ", "teddy bear ", "telegram ", "television ",
            "tennis ball ", "termite ", "textbook ", "toast ", "tornado ",
            "traitor ", "transmitter ", "treasure chest ", "tree ", "trolley ",
            "trumpet ", "typewriter ", "user's manual ", "Uzi ", "van ",
            "virus ", "volleyball ", "wand ", "wheel ", "whip ", "yak ",
            "Zulu ", "INWO deck ", "ukelele ", "icon ", "amphibian ", "toad ",
            "terminal ", "additive ", "piccolo ", "tuba ", "angel ", "demon " };

    String[] modifiers = { "apparently ", "barely ", "definitely ",
            "disgustingly ", "far too ", "much too ", "not ", "probably ",
            "secretly ", "slightly ", "too " };

    String[] infinitives = { "amuse ", "avoid ", "berate ", "boggle ",
            "bother ", "bury ", "catch ", "command ", "contaminate ",
            "control ", "convert ", "deliver ", "destroy ", "disfigure ",
            "dissolve ", "eat ", "enter ", "fondle ", "handle ", "harass ",
            "hassle ", "help ", "imitate ", "infiltrate ", "inherits ",
            "join ", "kill ", "kiss ", "leave ", "love ", "massage ",
            "molest ", "persuade ", "pervert ", "pitche ", "rebuild ",
            "reinforce ", "remove ", "replace ", "resurrect ", "save ",
            "serve ", "spank ", "squeezes ", "stroke ", "subvert ", "swallow ",
            "swat ", "swim ", "torment ", "torture ", "transform ", "whip " };

    String[] abilities = { "can ", "cannot ", "will ", "will not ", "should ",
            "should not ", "must ", "must not ", "will probably ",
            "will probably not ", "doesn't ", "wants to ", "is afraid to ",
            "is ordered to " };

    public Fnorder() {
        Date currentDate = new Date(); //used for seed in generating random
        // numbers
        myRandom = new MersenneTwister(currentDate.getTime());
    }

    public static void main(String[] args) {
        Fnorder myFnord = new Fnorder();
        Logger.getLogger(Fnorder.class).info(myFnord.getFnord());
    }

    private int random(int MAX) {
        return java.lang.Math.abs(myRandom.nextInt() % (MAX));
    }

    private String chop(String s) {
        StringBuffer sb = new StringBuffer(s);
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    private String p_noun() {
        return nouns[random(nouns.length - 1)];
    }

    private String p_name() {
        return names[random(names.length - 1)];
    }

    private String p_place() {
        return places[random(places.length - 1)];
    }

    private String p_prs() {
        return prepositions[random(prepositions.length - 1)];
    }

    private String p_act() {
        return actions[random(actions.length - 1)];
    }

    private String p_pronoun() {
        return pronouns[random(pronouns.length - 1)];
    }

    private String p_adj() {
        return adjectives[random(adjectives.length - 1)];
    }

    public String getFnord() {
        StringBuffer sb;
        int syntax = random(13);
        String msg = new String();
        // GRAMMAR RULES:
        // -----------------------------------
        if (syntax == 0) {
            msg = msg + ("The "); // The
            if (random(2) > 0) { // <adjective> (50% chance)
                msg = msg + (p_adj());
            }
            msg = msg + (p_noun()); // <noun>
            if (random(5) == 0) { // in <someplace> (20% chance)
                msg = msg + ("in ");
                msg = msg + (p_place());
            }
            msg = msg + ("is "); // is
            msg = msg + (p_adj()); // <adjective>
        } else if (syntax == 1) {
            msg = msg + (p_name()); // <name>
            msg = msg + (p_act()); // <action>
            msg = msg + ("the "); // the
            msg = msg + (p_adj()); // <adjective>
            msg = msg + (p_noun()); // <noun>
            msg = msg + ("and the "); // and the
            msg = msg + (p_adj()); // <adjective>
            msg = msg + (p_noun()); // <noun>
        } else if (syntax == 2) {
            msg = msg + ("The "); // The
            msg = msg + (p_noun()); // <noun>
            msg = msg + ("from "); // from
            msg = msg + (p_place()); // <place>
            msg = msg + ("will go to "); // will go to
            msg = msg + (p_place()); // <place>
        } else if (syntax == 3) {
            msg = msg + (p_name()); // <name>
            msg = msg + ("must take the "); // must take the
            msg = msg + (p_adj()); // <adjective>
            msg = msg + (p_noun()); // <noun>
            msg = msg + ("from "); // from
            msg = msg + (p_place()); // <place>
        } else if (syntax == 4) {
            msg = msg + (p_place()); // <place>
            msg = msg + ("is "); // is
            msg = msg + (p_adj()); // <adjective>
            msg = msg + ("and the "); // and the
            msg = msg + (p_noun()); // <noun>
            msg = msg + ("is "); // is
            msg = msg + (p_adj()); // <adjective>
        } else if (syntax == 5) {
            msg = msg + (p_name()); // <name>
            msg = msg + (p_prs()); // <preposition>
            msg = msg + (p_place()); // <place>
            msg = msg + ("for the "); // for the
            msg = msg + (p_adj()); // <adjective>
            msg = msg + (p_noun()); // <noun>
        } else if (syntax == 6) {
            msg = msg + ("The ");
            if (random(2) > 0) {
                msg = msg + (p_adj()); // <adjective> (50% chance)
            }
            msg = msg + (p_noun()); // <noun>
            msg = msg + (p_act()); // <action>
            msg = msg + ("the "); // the
            msg = msg + (p_adj()); // <adjective>
            msg = msg + (p_noun()); // <noun>
            if (random(5) == 0) { // in <place> (20% chance)
                msg = msg + ("in ");
                msg = msg + (p_place());
            }
        } else if (syntax == 7) {
            msg = msg + (p_name()); // <name>
            msg = msg + (p_prs()); // <preposition>
            msg = msg + (p_place()); // <place>
            msg = msg + ("and "); // and
            msg = msg + (p_act()); // <action>
            msg = msg + ("the "); // the
            msg = msg + (p_noun()); // <noun>
        } else if (syntax == 8) {
            msg = msg + (p_name()); // <name>
            msg = msg + ("takes "); // takes
            msg = msg + (p_pronoun()); // <pronoun>
            if (random(2) > 0) {
                msg = msg + (p_adj()); // <adjective> (50% chance)
            }
            msg = msg + (p_noun()); // <noun>
            msg = msg + ("and "); // and
            msg = msg + (p_prs()); // <preposition>
            msg = msg + (p_place()); // <place>
        } else if (syntax == 9) {
            msg = msg + (p_name()); // <name>
            msg = msg + (p_act()); // <action>
            msg = msg + ("the "); // the
            if (random(2) > 0) {
                msg = msg + (p_adj()); // <adjective> (50% chance)
            }
            msg = msg + (p_noun()); // <noun>
        } else if (syntax == 10) {
            msg = msg + (p_name()); // <name>
            msg = msg + (p_act()); // <action>
            msg = msg + (p_name()); // <name>
            msg = msg + ("and "); // and
            msg = msg + (p_pronoun()); // <pronoun>
            if (random(2) > 0) {
                msg = msg + (p_adj()); // <adjective> (50% chance)
            }
            msg = msg + (p_noun()); // <noun>
        } else if (syntax == 11) {
            msg = msg + (p_name()); // <name>
            msg = msg + ("is the "); // is the
            if (random(2) > 0) {
                msg = msg + (p_adj()); // <adjective) (50% chance)
            }
            msg = msg + (p_noun()); // <noun>
            msg = chop(msg);
            msg = msg + ("; "); // ;
            msg = msg + (p_name()); // <name>
            msg = msg + (p_prs()); // <preposition>
            msg = msg + (p_place()); // <place>
        } else if (syntax == 12) {
            msg = msg + ("You must meet "); // You must meet
            msg = msg + (p_name()); // <name>
            msg = msg + ("at "); // at
            msg = msg + (p_place()); // <place>
            msg = msg + ("and get the "); // and get the
            if (random(2) > 0) {
                msg = msg + (p_adj()); // <adjective> (50% chance)
            }
            msg = msg + (p_noun()); // <noun>
        } else {
            msg = msg + ("A "); // A
            msg = msg + (p_noun()); // <noun>
            msg = msg + ("from "); // from
            msg = msg + (p_place()); // <place>
            msg = msg + (p_act()); // <action>
            msg = msg + ("the "); // the
            if (random(2) > 0) {
                msg = msg + (p_adj()); // <adjective> (50% chance)
            }
            if (random(5) == 0) {
                msg = msg + (p_adj()); // <adjective> (20% chance)
            }
            msg = msg + (p_noun()); // <noun>
        }
        msg = chop(msg); // delete the blank at the end
        msg = msg + ("."); // add a period
        sb = new StringBuffer(msg);
        sb.setCharAt(0, Character.toUpperCase(msg.charAt(0)));
        // the end.
        return sb.toString();
    }
}