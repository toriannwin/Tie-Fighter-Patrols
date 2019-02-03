# Tie-Fighter-Patrols
Program uses a files of pilot routes to determine the areas covered by the routes, and then can search and sort through the list of pilots and areas via a commands file.

Requires file: "pilot_routes.txt" to be in the same directory. Will create "pilot_areas.txt" with the areas from that route. Sample pilot_routes.txt is given in the repository.

Requires file: "commands.txt" to be in the same directory. Will create "results.txt" with the results of the searches that are called from the commands file. Sample commands.txt file is given in the repository.

This project performs input validation, creates a LinkedList using a Generic Node which holds the Payload, aka the pilot.
