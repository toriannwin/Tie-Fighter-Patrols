/**
 * Tori Windrich
 * 3/18/2018
 * Project 3: Tie Fighter Patrols
 */
package TieFighter;

import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class Main 
{
    public static void main(String[] args) 
    {
        //create a LinkedList, read the file into the LinkedList
        //call the searchAndSort method, create the pilot_areas file
        //and print out the list to it (using toString) and close the file
        try {
            LinkedList list = new LinkedList();
            int numPilots = readFile(list);
            if(list.getHead() != null)
            {
                searchAndSort(list, numPilots);
                File finalFile = new File("pilot_areas.txt");
                PrintWriter finalOutput = new PrintWriter(finalFile);
                finalOutput.print(list);
                finalOutput.close();
            }
            else
                System.out.println("List is empty.");
        }
        //catching any of the possible file not found exceptions
        catch(FileNotFoundException e) {
            System.out.println("A file could not be found. See error message:\n" + e);
        }
    }
    
    //fills the LinkedList with data from the file pilot_routes.txt
    //goes through the file while there is still data in it, validates the line
    //then parses through it to assign the name of the pilot to a Payload,
    //find the area with the rest of the string, add the node to the end 
    public static int readFile(LinkedList list) throws FileNotFoundException //in case pilot_routes.txt is not found
    {
        int numPilots = 0;
        
        //opens pilot_routes.txt and creates a Scanner to access the input
        File file = new File("pilot_routes.txt");
        Scanner input = new Scanner(file);
        String nextLine, name;
        double area;
        Payload newPayload;
        Node newNode;
        
        //while there is still data in the file
        while(input.hasNext())
        {
            //get next line, if it's not empty and if it's valid...
            nextLine = input.nextLine();
            if(nextLine.compareTo("") != 0 && validRoute(nextLine))
            {
                //increment the number of pilots
                numPilots++;
                
                //isolate the name by cutting off at the first comma, and then
                //cutting off at the last space (this will help take care of any
                //names that are multiple words)
                name = nextLine.substring(0,nextLine.indexOf(","));
                name = name.substring(0,name.lastIndexOf(" "));
                
                //create a new Payload with the name
                newPayload = new Payload(name);
                
                //calculate the area for the current pilot route
                area = calcArea(nextLine.substring(name.length()+1));
                
                //set the area into the payload
                newPayload.setArea(area);
                
                //create the new node with the payload
                newNode = new Node(newPayload);
                
                //add node to the list
                list.append(newNode);
            }
        }
        return numPilots; 
    }
    
    //this method goes through the commands.txt file and sorts/searches through
    //the passed in linked list. It writes out all command results in the results.txt
    //file.
    public static void searchAndSort(LinkedList list, int numPilots) throws FileNotFoundException //in case commands.txt doesn't exist
    {
        //input and output file for commands
        File file = new File("commands.txt");
        File file2 = new File("results.txt");
        
        //connecting the Scanner and PrintWriter to the appropriate files
        PrintWriter output = new PrintWriter(file2);
        Scanner input = new Scanner(file);
        String command;
        
        //this array will hold indicators passed by the validCommand method in order to indicate
        //which commands should be performed with the current line from the file
        char [] indicators = new char[3];
        int index;
        //while the input commands file still has data
        while(input.hasNext())
        {
            //get the next line, check that it's valid (and fill indicators array)
            command = input.nextLine();
            if(validCommand(command,indicators))
            {
                //print the 
                output.printf("%-25s",command);
                //switch the first position of indicators (s = sort, a = search for area, n = search for name)
                switch (indicators[0]) {
                    //sort
                    case 's':
                        //determine if we are sorting by area or name
                        if(indicators[1] == 'a')
                        {
                            //determine if we are sorting in ascending or descending
                            if(indicators[2] == 'a')
                            {
                                list.MergeSort("asc", "area");
                            }//ascending order
                            else
                            {
                                list.MergeSort("dec", "area");
                            }//descending order
                            //prints out the head and tail's area values into the results.txt file
                            output.printf("Head: %.2f\t", ((Payload)list.getHead().getVar()).getArea());
                            output.printf("Tail: %.2f\t\r\n", ((Payload)list.getTail().getVar()).getArea());
                        }//sort by area
                        else
                        {
                            //determine if we are sorting in ascending or descending
                            if(indicators[2] == 'a')
                            {
                                list.MergeSort("asc", "name");
                            }//ascending order
                            else
                            {
                                list.MergeSort("dec", "name");
                            }//descending order
                            //prints out the head and tail's name values into the results.txt file
                            output.printf("Head: %s\t", ((Payload)list.getHead().getVar()).getPilot());
                            output.printf("Tail: %s\t\r\n", ((Payload)list.getTail().getVar()).getPilot());
                        }//sort by name
                        break;
                    //search for name
                    case 'n':
                        //search for the name using the search method
                        index = list.searchName(command);
                        //print out either found or not found
                        if(index > -1)
                            output.println(command + " found.");
                        else
                            output.println(command + " not found.");
                        break;
                    //search for area
                    default:
                        //search for the area using the search method
                        index = list.searchArea(Double.parseDouble(command));
                        //print out either found or not found
                        if(index > -1)
                            output.println(command + " found.");
                        else
                            output.println(command + " not found.");
                        break;
                }
            }
        }
        //close the input and output files
        input.close();
        output.close();
    }
    
    //this method calculates the area enclosed by the coordinates by parsing through the
    //string of coords, parsing them to doubles and putting them in a 2D array, then going through
    //the array and performing the following math: (ABS(SIGMA([(currentX + nextX)*(nextY - currentY)])))/2
    public static double calcArea(String list)
    {
        double area = 0;
        String [] coordSets = list.split(" ");
        double [][] coords = new double[coordSets.length][2];
        double sum = 0;
        //going through the list of coordinates and casting them correctly into a 2D array
        //in order to perform the calculations
        for(int i = 0; i < coords.length; i++)
        {
            coords[i][0] = Double.parseDouble(coordSets[i].substring(0,coordSets[i].indexOf(",")));
            coords[i][1] = Double.parseDouble(coordSets[i].substring(coordSets[i].indexOf(",")+1));
        }
        //going through the columns in coords up to the counter, summing up [(currentX + nextX)*(nextY - currentY)]
        for(int row = 0; row <= coords.length - 2; row++)
        {
            sum += (coords[row][0]+coords[row+1][0])*(coords[row+1][1]-coords[row][1]);
        }
        //take the absolute value of sum, half it, and assign it to the proper position in the areas array
        sum = Math.abs(sum);
        return sum/2;
    }
    
    //this method verifies that the input from pilot_routes is valid
    public static boolean validRoute(String info)
    {
        //if there isn't any commas, invalid
        if(info.indexOf(",") <= 0)
            return false;
        
        //if there aren't any spaces, or if the first space is after the first comma, invalid
        if(info.indexOf(" ") == -1 || info.indexOf(" ") > info.indexOf(","))
            return false;
        
        //isolate the name
        String name = info.substring(0,info.indexOf(","));
        name = name.substring(0,name.lastIndexOf(" "));
        
        //if the name contains any invalid characters(if it's not ' or - or alphanumeric), invalid
        for(int i = 0; i < name.length(); i++)
        {
            if(name.charAt(i) != '\'' && name.charAt(i) != '-' && !(name.charAt(i) >= '0' && name.charAt(i) <= '9')
                            && !(name.charAt(i) >= 'A' && name.charAt(i) <= 'Z') && !(name.charAt(i) >= 'a' && name.charAt(i) <= 'z') && name.charAt(i) != ' ')
                return false;
        }
        
        //isolate the rest of the string (after the name)
        String theRest = info.substring(name.length()+1);
        
        //split into an array of coordinates as strings
        String [] coords = theRest.split(" ");
        int numCommas, numDec, numNeg;
        
        //if the first and last coordinate aren't the same, invalid
        if(coords[0].compareTo(coords[coords.length-1]) != 0)
            return false;
        
        //goes through each string in the coords list
        for (String coord : coords) 
        {
            numCommas = 0;
            numDec = 0;
            numNeg = 0;
            
            //if the coordinate doesn't contain a comma, invalid
            if(!coord.contains(","))
                return false;
            
            //if the coordinate's first char is a comma, invalid
            if(coord.charAt(0) == ',')
                return false;
            
            //if rhw coordinate's last index is a comma or a negative, invalid
            if(coord.charAt(coord.length()-1) == ',' || coord.charAt(coord.length()-1) == '-')
                return false;
            
            //if the coord's first number ends with a decimal, invalid
            if(coord.charAt(coord.indexOf(",")-1) == '-')
                return false;
            
            //going through the characters of the current coordinate
            for(int i = 0; i < coord.length(); i++)
            {
                //if it's an invalid character, return false
                if((coord.charAt(i) < '0' || coord.charAt(i) > '9') && coord.charAt(i) != ',' && coord.charAt(i) != '.' && coord.charAt(i) != '-')
                    return false;
                
                //increment commas/decimals/negatives as they're found
                if(coord.charAt(i) == ',')
                    numCommas++;
                if(coord.charAt(i) == '.')
                    numDec++;
                if(coord.charAt(i) == '-')
                    numNeg++;
                
                //if too many commas, decimals, or negatives, return false
                if(numCommas > 1)
                    return false;
                if(numDec > 2)
                    return false;
                if(numNeg > 2)
                    return false;
            }
            
            //if not enough commas, invalid
            if(numCommas != 1)
                return false;
            
            //checking the position of any negatives found
            if(numNeg == 1)
            {
                if (coord.indexOf("-") != 0 && coord.indexOf("-") != coord.indexOf(",")+1)
                    return false;
            }
            else if(numNeg == 2)
            {
                if(coord.indexOf("-") != 0 && coord.lastIndexOf("-") != coord.indexOf(",")+1)
                    return false;
            }
            
            //if there are two decimals, check where they are
            if(numDec == 2)
            {
                if(!(coord.indexOf(".") < coord.indexOf(",") && coord.lastIndexOf(".") > coord.indexOf(",")))
                    return false;
            }     
        }
        return true;
    }
    
    //this method validates the input from commands.txt
    public static boolean validCommand(String command, char[] indicator)
    {
        //if the command stards with sort
        if(command.indexOf("sort") == 0)
        {
            //split the command by spaces
            String [] parts = command.split(" ");
            
            //if there are more than three parts, return false
            if(parts.length != 3)
                return false;
            
            //if the second part isn't area or pilot, return false
            if(parts[1].compareTo("area") != 0 && parts[1].compareTo("pilot") != 0)
                return false;
            
            //if the third part isn't asc or dec, return false
            if(parts[2].compareTo("asc") != 0 && parts[2].compareTo("dec") != 0)
                return false;
            
            //set the first indicator to s
            indicator[0] = 's';
            
            //if it's area, 2nd indicator is a, otherwise n for name
            if (parts[1].compareTo("area") == 0)
                indicator[1] = 'a';
            else
                indicator[1] = 'n';
            
            //if it's asc, 3rd indicator is a, otherwise d for dec
            if (parts[2].compareTo("asc") == 0)
                indicator[2] = 'a';
            else
                indicator[2] = 'd';
            
            return true;
        }
        //if it's not a sort command
        else
        {
            double area;
            boolean foundNum = false;
            boolean foundAlpha = false;
            //figure out if there are alpha and number characters
            for(int i = 0; i < command.length(); i++)
            {
                if(Character.isAlphabetic(command.charAt(i)))
                    foundAlpha = true;
                if(Character.isDigit(command.charAt(i)))
                    foundNum = true;
            }
            //if number is found and no letters found, attempt to parse the command to a double, if it's less than 0, return false
            if (foundNum && !foundAlpha) {
                try
                {
                    area = Double.parseDouble(command);
                }
                catch (NumberFormatException e)
                {
                    return false;
                }
                if (area < 0)
                    return false;
                //set the first indicator to a for area
                indicator[0] = 'a';
                return true;
            }
            //if an exception occurs, check that the command is a valid name using the same checks as in
            //the validRoutes method
            else {
                for(int i = 0; i < command.length(); i++)
                {
                    if(command.charAt(i) != '\'' && command.charAt(i) != '-' && !(command.charAt(i) >= '0' && command.charAt(i) <= '9')
                            && !(command.charAt(i) >= 'A' && command.charAt(i) <= 'Z') && !(command.charAt(i) >= 'a' && command.charAt(i) <= 'z') && command.charAt(i) != ' ')
                    {
                        return false;
                    } 
                }
                //set the first indicator to n for name
                indicator[0] = 'n';
                return true;
            }
        }
    }
}
