// Eli Rosenfeld
// 4/25/2026
// The purpose of this class is to utilize a CharacterSheet object
// to streamline the process of character creation in D&D

import java.util.*;
import java.io.*;

public class CharacterCreator
{
    // I want to be able to use this Scanner wherever
    private static Scanner stdin = new Scanner(System.in);

    // I want to make this variable to make it clear (and more easily editable)
    // how many stats there are
    private static final int NUMBER_OF_STATS = 6;

    public static void main(String[] args) throws NegativeItemNumberException, InvalidAbilityScoreException, NegativeLimiterException, InvalidWeaponException, ItemChildException, ItemDoesNotExistException, InvalidHitPointsException, UnusualNumberOfStatsException, FileNotFoundException
    {
        // I need to open the right files to read in the classes (Jobs)
        // and Backgrounds
        Scanner classIn = new Scanner(new File("classList.txt"));
        Scanner backgroundIn = new Scanner(new File("backgroundList.txt"));

        // I will use a method to create the HashMap of classes (Jobs)
        // The String will be the name of the class
        // I know this is redundant, but it will be useful later on
        HashMap<String, Job> allClasses = readClassFile(classIn);

        // I will use a method to create the HashMap of Background
        // The String will be the name of the Background
        // I know this is redundant, but it will be useful later on
        HashMap<String, Background> allBackgrounds = readBackgroundFile(backgroundIn);

        // Closing the files
        classIn.close();
        backgroundIn.close();

        // Now I can have the user create their character
        CharacterSheet playerCharacter = buildSheet(allClasses, allBackgrounds);


        // After this, I want to give the user the opportunity to view their sheet
        // and equip Items
        while(true)
        {
            int choice = menu();

            // If the user wants to print their sheet
            if (choice == 1)
                System.out.println(playerCharacter);

            // If the user wants to equip a Weapon
            if (choice == 2)
            {
                // Prompting user for their weapon choice
                System.out.println("What weapon do you want to equip? (Type its name)");
                String name = stdin.nextLine();

                // Trying to equip that weapon
                try
                {
                    playerCharacter.equipWeapon(name);
                    System.out.println("You have equipped the following: " + name);

                }

                // Catching the exception
                catch(ItemDoesNotExistException e)
                {
                    System.out.println("That item does not exist in your inventory, sorry!");

                }

            }

            // If the user wants to equip Armor
            if (choice == 3)
            {
                // Prompting user for their Armor choice
                System.out.println("What armor do you want to equip? (Type its name)");
                String name = stdin.nextLine();

                // Trying to equip that Armor
                try
                {
                    playerCharacter.equipArmor(name);
                    System.out.println("You have equipped the following: " + name);

                }

                // Catching the exception
                catch(ItemDoesNotExistException e)
                {
                    System.out.println("That item does not exist in your inventory, sorry!");

                }

            }

            // If the user wants to equip a Shield
            if (choice == 4)
            {
                // Prompting user for their Shield choice
                System.out.println("What shield do you want to equip? (Type its name)");
                String name = stdin.nextLine();

                // Trying to equip that shield
                try
                {
                    playerCharacter.equipShield(name);
                    System.out.println("You have equipped the following: " + name);

                }

                // Catching the exception
                catch(ItemDoesNotExistException e)
                {
                    System.out.println("That item does not exist in your inventory, sorry!");

                }

            }

            // If the user wants to quit
            if (choice == 5)
                break;
        }

    }

    // This method prints and gets your choice from a menu
    private static int menu()
    {
        // The user's choice
        int choice = 0;

        // We don't know if the choice is valid yet
        boolean valid = false;

        do
        {
            System.out.println("What would you like to do?");
            System.out.println("1 = Print your character sheet");
            System.out.println("2 = Equip a Weapon");
            System.out.println("3 = Equip Armor");
            System.out.println("4 = Equip a Shield");
            System.out.println("5 = Quit");

            try
            {
                choice = Integer.parseInt(stdin.nextLine());

                // If the user types a valid choice
                if (choice > 0 && choice < 6)
                    valid = true;

                else
                    System.out.println("That number is not an option, sorry!");

            }

            // If the user does not type an integer
            catch(NumberFormatException e)
            {
                System.out.println("Please type integers only");

            }

        } while(!valid);

        // Returning the choice
        return choice;
        
    }

    // This method is used to prompt the user to build a character sheet
    private static CharacterSheet buildSheet(HashMap<String, Job> allClasses, HashMap<String, Background> allBackgrounds) throws InvalidAbilityScoreException, UnusualNumberOfStatsException, ItemChildException
    {
        // Name
        System.out.println("What is your character's full name?");
        String name = stdin.nextLine();

        // Species
        System.out.println("What is your character's species? (Make one up if you want)");
        String species = stdin.nextLine();

        // Background
        String backgroundName;

        // I gotta make sure the background actually exists
        boolean exists = false;
        do
        {
            System.out.println("What is your character's background? (Make sure it is capitalized and spelled properly)");
            backgroundName = stdin.nextLine();

            // If the background exists
            if (allBackgrounds.containsKey(backgroundName))
                exists = true;

            else
                System.out.println("Sorry, I couldn't find that background. Try again!");


        } while(!exists);

        // Grabbing the background
        Background bg = allBackgrounds.get(backgroundName);

        // Class
        String className;

        // Making exists false again
        exists = false;

        do
        {
            System.out.println("What class is your character? (Make sure it is capitalized and spelled properly)");
            className = stdin.nextLine();

            // If the class exists
            if (allClasses.containsKey(className))
                exists = true;

            else
                System.out.println("Sorry, I couldn't find that class. Try again!");

        } while(!exists);

        // Grabbing the class
        Job cl = allClasses.get(className);

        // Stats
        // 0 = Strength, 1 = Dexterity, 2 = Constitution, 3 = Intelligence, 4 = Wisdom, 5 = Charisma
        int[] stats = new int[NUMBER_OF_STATS];

        // I am creating a PriorityQueue of the stat values to be able to easily poll from them
        PriorityQueue<Integer> statVals = new PriorityQueue<Integer>();

        System.out.println("Rolling Stats...");
        for (int i = 0; i < NUMBER_OF_STATS; i++)
            statVals.add(rollStat());

        // Asking the user where they want to place their stats,
        // from lowest to highest
        for (int i = 0; i < NUMBER_OF_STATS; i++)
        {
            // I need to check if the player entered a viable answer
            boolean viable = false;

            do
            {
                System.out.println("What stat do you want to place the " + statVals.peek() + " in?");
                System.out.println("1 = Strength; 2 = Dexterity; 3 = Constitution; 4 = Intelligence; 5 = Wisdom; 6 = Charisma");

                try
                {
                    int ans = Integer.parseInt(stdin.nextLine());

                    // Check if the user entered a viable number
                    if (ans > 0 && ans < 7)
                    {
                        // If the user has already chosen that stat
                        if (stats[ans - 1] != 0)
                            System.out.println("You already chose that stat! Try again");

                        else
                        {
                            // Setting viable to true
                            viable = true;

                            // Placing the number in the right spot
                            stats[ans - 1] = statVals.poll();

                        }

                    }

                    // Otherwise, they entered something incorrectly
                    else
                        System.out.println("That number does not map to one of the stats; try again!");

                }

                // Catch if the user messed up the input
                catch (NumberFormatException e)
                {
                    System.out.println("Sorry, that is not a valid answer; try typing an integer");

                }          

            } while(!viable);

        }

        // By this point, the CharacterSheet is complete
        return new CharacterSheet(name, species, stats, bg, cl);

    }

    private static int rollStat()
    {
        Random rnd = new Random();

        // The way the 6 stats are calculated is by rolling 4d6,
        // removing the smallest number,
        // and adding the rest together
        // Thus, I have to keep track of the lowest roll
        int[] rolls = new int[4];
        int lowIndex = 0;

        // Printing to the user
        System.out.print("Rolls: ");

        for (int i = 0; i < rolls.length; i++)
        {
            rolls[i] = rnd.nextInt(6) + 1;

            // For the user's sake, I will print each roll
            System.out.print(rolls[i] + ", ");

            // If the current roll is lower than the lowest one,
            // set the lowest index to that one
            if (rolls[i] < rolls[lowIndex])
                lowIndex = i;

        }

        // Now I can change the value at lowIndex to 0 and add all the indexes together
        System.out.println("\nRemoving the " + rolls[lowIndex] + "...");
        rolls[lowIndex] = 0;
        int sum = 0;
        for (int i = 0; i < rolls.length; i++)
            sum += rolls[i];

        // This sum is the answer we are looking for
        System.out.println("This gives us " + sum);
        return sum;

    }

    // This method takes in a Scanner reading a file and fills up a HashMap of classes and their names
    private static HashMap<String, Job> readClassFile(Scanner classIn) throws NegativeItemNumberException, InvalidWeaponException, NegativeLimiterException, InvalidHitPointsException
    {
        // I need to put all the Backgrounds into a HashMap to later return
        HashMap<String, Job> allClasses = new HashMap<String, Job>();

        // The first line is the number of Backgrounds in the file
        int numClasses = Integer.parseInt(classIn.nextLine());

        // I need an array of Strings to keep track of the Classes' names
        String[] classNames = new String[numClasses];

        // Gathering the data from each Class
        for (int i = 0; i < numClasses; i++)
        {
            // Getting the next line and tokenizing it
            StringTokenizer curLine = new StringTokenizer(classIn.nextLine(), "\t");

            // Grabbing the class's name
            String curName = curLine.nextToken();

            // Adding the class name to the array of names
            classNames[i] = curName;

            // Grabbing the hp maximum
            int curHp = Integer.parseInt(curLine.nextToken());

            // The line after this is the number of starter Items in the class
            int numItems = Integer.parseInt(classIn.nextLine());

            // Looping through all those Items and adding them to the ArrayList of Items
            ArrayList<Item> curItems = new ArrayList<Item>();
            for (int j = 0; j < numItems; j++)
            {
                // Getting each line
                // It is important to tokenize using tab as a delimiter
                // Because some items have multiple words in them,
                // and the spaces would mess that up
                curLine = new StringTokenizer(classIn.nextLine(), "\t");

                // Figuring out the Item type
                String itemType = curLine.nextToken();

                // Using the method I made to figure out what to do next with the Item type given
                curItems.add(evaluateItemType(itemType, curLine));

            }

            // Each iteration, I need to add a Class to the HashMap
            // and the name that acts as its key
            allClasses.put(classNames[i], new Job(curName, curHp, curItems));

        }

        // Returning the HashSet of classes
        return allClasses;

    }

    // This method takes in a Scanner reading a file and fills up a HashMap of Backgrounds and their names
    private static HashMap<String, Background> readBackgroundFile(Scanner backgroundIn) throws NegativeItemNumberException, InvalidWeaponException, NegativeLimiterException
    {
        // I need to put all the Backgrounds into a HashMap to later return
        HashMap<String, Background> allBackgrounds = new HashMap<String, Background>();

        // The first line is the number of Backgrounds in the file
        int numBackgrounds = Integer.parseInt(backgroundIn.nextLine());

        // I need an array of Strings to keep track of the Backgrounds' names
        String[] backgroundNames = new String[numBackgrounds];

        // Gathering the data from each Background
        for (int i = 0; i < numBackgrounds; i++)
        {
            // Getting the next line and tokenizing it
            StringTokenizer curLine = new StringTokenizer(backgroundIn.nextLine(), "\t");

            // Grabbing the class's name
            String curName = curLine.nextToken();

            // Adding this name to the array of names
            backgroundNames[i] = curName;

            // Grabbing the array of stat names
            // This array will always be the number of stat names left in the line long
            // In D&D, this number is always 3, but I will make it customizable
            // because there is no reason not to
            String[] curStatNames = new String[curLine.countTokens()];
            for (int j = 0; j < curStatNames.length; j++)
                curStatNames[j] = curLine.nextToken();

            // The line after this is the number of starter Items in the Background
            int numItems = Integer.parseInt(backgroundIn.nextLine());

            // Looping through all those Items and adding them to the ArrayList of Items
            ArrayList<Item> curItems = new ArrayList<Item>();
            for (int j = 0; j < numItems; j++)
            {
                // Getting each line
                // It is important to tokenize using tab as a delimiter
                // Because some items have multiple words in them,
                // and the spaces would mess that up
                curLine = new StringTokenizer(backgroundIn.nextLine(), "\t");

                // Figuring out the Item type
                String itemType = curLine.nextToken();

                // Using the method I made to figure out what to do next
                curItems.add(evaluateItemType(itemType, curLine));

            }

            // Each iteration, I need to add a Background to the HashMap
            // and the name that acts as its key
            allBackgrounds.put(backgroundNames[i], new Background(curName, curStatNames, curItems));

        }

        // Returning the HashSet of Backgrounds
        return allBackgrounds;

    }

    private static Item evaluateItemType(String itemType, StringTokenizer curLine) throws NegativeItemNumberException, InvalidWeaponException, NegativeLimiterException
    {
        // Creating the resulting Item
        // I have to initialize it because of java syntax
        // So I will initialize it as null
        // If it ever stays as null, the file format being used is incorrect
        Item res = null;

        // No matter the case, the Item name goes first
        String itemName = curLine.nextToken();

        // If the Item type is a generic Item
        if (itemType.equals("I"))
        {
            // Getting the number of that Item you get and initializing res
            int count = Integer.parseInt(curLine.nextToken());
            res = new Item(itemName, count);

        }

        // If the Item type is a Weapon
        if (itemType.equals("W"))
        {
            // Getting the damage dice
            int damage = Integer.parseInt(curLine.nextToken());

            // Getting the range
            int range = Integer.parseInt(curLine.nextToken());

            // Getting the number of that Item you get
            int count = Integer.parseInt(curLine.nextToken());

            // Initializing res
            res = new Weapon(itemName, damage, range, count);

        }

        // If the Item type is Armor
        if (itemType.equals("A"))
        {
            // Getting the base AC
            int ac = Integer.parseInt(curLine.nextToken());

            // The AC limiter
            Integer limit;

            // Checking if the AC limiter is null or not
            String check = curLine.nextToken();
            if (check.equals("null"))
                limit = null;

            else
                limit = Integer.parseInt(check);

            // The status of disadvantage
            boolean disadvantage = false;

            // If the next token is "T",
            // disadvantage becomes true
            if (curLine.nextToken().equals("T"))
                disadvantage = true;

            // Getting the number of that Item you get
            int count = Integer.parseInt(curLine.nextToken());

            // Initializing res
            res = new Armor(itemName, ac, limit, disadvantage, count);

        }

        // If the type is Shield
        if (itemType.equals("S"))
        {
            // Getting the AC bonus
            int bonus = Integer.parseInt(curLine.nextToken());

            // Getting the number of that Item you get
            int count = Integer.parseInt(curLine.nextToken());

            // Initializing res
            res = new Shield(itemName, bonus, count);

        }

        // Returning the Item
        return res;

    }
    
}