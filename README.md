"# BusStop" 

Different ways to start the applications:

/!\ Note: the three .json files in the top directory (stopdata-init.json, allthelines.json and stopdata.json) are there only to be used  
to fetch the data locally instead of using a request (avoids wasting keys and convenient for developement)

__Opt1-On IDE:__

    run the com.app.Application class
    
    Open browser to https://localhost:8080


__Opt2-Docker container__
    
        On BusStop directory:
            docker build --tag java-docker .
            docker run -d -p 8080:8080 java-docker
        
        Open in browser (from Docker Desktop), it will load immediatly on port 8080
        Check the docker console for some output

__Opt3- From console (might encounter character encoding issues with Swedish äöå)__
    
    From BusStop directory:
        mvnw spring-boot:run



__Comments__
The output shows some bus stops that are named "n/a-CODE": this occurs when the stop name is unavailable.
It was chosen to let the user be aware of that rather than hiding it.

All the lines have two directions. In most of the case it's as simple as a back and forth trip but for some lines
one direction has more stop that the other. For this reason it was chose to evaluate the total number of stop including
duplicates and simply remove them on the client output for better readability.
