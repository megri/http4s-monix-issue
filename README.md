# Reproduction


* Start server with `sbt run`
* Visit http://localhost:8080 to see the live stream update its value between requests;
* Connect to the /ws route to see events tick by asynchronously; this can be done using something like `https://chrome.google.com/webstore/detail/simple-websocket-client/pfdhoblngboilpfeibdedpjgfnlcodoo?hl=en`;
* Hammer the /ws route with connects/disconnects, for instance by spamming the open/close button in the previously used extension;
* Result: both / and /ws stops accepting new events, but /test should work.
