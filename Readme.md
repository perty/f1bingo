# F1 Bingo
![Build Status](https://github.com/perty/f1bingo/actions/workflows/maven.yml/badge.svg)

A bingo game for Formula 1 fans. In Swedish.

## Can I play?

If you run it on a server, and you can read Swedish, yes. If you can't read Swedish, you could replace the statements in the database with your own. Then you would need to translate GUI as well. There's not only the buttons but also info pages.

The database is set up with the 2024 calendar.


You also need to set up user (fans) accounts in the database.

## Game play

Each fan get their own bingo cards which are generated when they pick the GP and their name in the drop-downs. 

During the session (qualification or race) the fans can click on the statements that come true. The cell gets a green border. If they click another time, the border turns red. The latter is for user convenience only, to mark what is no longer possible.

After the session, the admin can go through statements that have been claimed true and accept or reject them. Admin then closes the session and points are calculated. For each bingo, one point is given. 

![screenshot.jpg](src%2Fmain%2Fresources%2Fstatic%2Fpublic%2Fimages%2Fscreenshot.jpg)
![screenshot2.jpg](src%2Fmain%2Fresources%2Fstatic%2Fpublic%2Fimages%2Fscreenshot2.jpg)

## Tech

The game is built with Spring Boot and vanilla JavaScript. The database is PostgreSQL. Build tool is Maven.

This is a [PWA (Progressive Web App)](https://developer.mozilla.org/en-US/docs/Web/Progressive_web_apps) and so Android users will get a prompt to install it. Apple is behind here, but users can still add it to their home screen. See `manifest.json` and `service-worker.js` for more info.

## How to run

Obviously, you need to have a database so just see that you have Docker installed and then run the script `start_db.sh`.

Then you can run the Spring Boot application. It will set up the schema and populate the database with the 2024 calendar, statements and some users.

## The team

The design was made by Ewa, and the software was built by Perty.
