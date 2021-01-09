# About

This is a front-end UI for the incrementing-integer-core backend service.

This project is built using Node.js and React.

_This is a Work In Progress and is not complete!_

The current status of the project is:
* I have the rough layout for the single-page app done. Some of the UI elements do not scale perfectly with different dimension browser windows.
* I just got to programming in the functionality using `fetch` to register the user, but then hit a CORS issue, since our backend is hosted on a different service. This can be solved relatively easily with a proxy to our backend hosted on our NPM webservice. A library like `node-http-proxy` would get the job done. 

# How to run the webapp

### (prerequisite) Install Node.js
Install the latest version of Node.js on your system. Make sure you can run the following two commands to see that you have suitable versions:
```
$ node -v
v14.15.4

$ npm -v
6.14.10
```

### Run the web app
Run the web app by simple running the following command in your shell
```
$ npm start
```