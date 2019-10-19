it's a simple app that uses lastfm api to access their database, and manage personal music.
there is just one admin access

In order to use it create a new properties file (application.properties) in the 
appropriate location(spring boot wink wink) and add your api key like so

lastfm.api_key = YOUR_API_KEY
lastfm.shared_secret = YOUR_SHARED_SECRET

and then enjoy the app.
Beware, a music app with no music is not easy to handle.