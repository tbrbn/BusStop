"# BusStop_SBAB" 

Different ways to start the application


__Opt1-On IDE:__

    run the Application.main()
    
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