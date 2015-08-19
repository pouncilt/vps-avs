LLVA.Session -- AJAX-ified KaaJee logins

Kaajee-based logins can be enabled in any app with these simple steps:

1. Include this script, as in:
    <script type="text/javascript" src="/jsonutils/js/session.js"></script>

2. Create a callback function to be used upon successful login.
    myNiftyLoginCallback = function() { ... };
    
3. Statically call the session init function and give it your callback
    function:
    
    LLVA.Session.init(myNiftyLoginCallback);

The callback function can also be set statically instead of being passed at
runtime:

    LLVA.Session.loginCallback = function() { ... };

LLVA.Session uses the following default URLs for communicating with the 
server:

    LLVA.Session.urls.userInfo   = '../w/s/user/userInfo.action';
    LLVA.Session.urls.facilities = '../w/login/stations.action';
    LLVA.Session.urls.login      = '../w/login/LoginController';
    LLVA.Session.urls.logout     = '../w/login/logout.action';


Note that these are relative URL's.  It is assumed that the HTML file
serving as the base of the application is located two subfolders down
from the Apache document root, as in:

    /myproject/blahblah/index.html

The Session init function places the logged-in user's parameters such 
as name and roles into a Model object located at:

    LLVA.Session.user

So, for example, you can grab the user's name from anywhere in your app
with the following line of code:

    var name = LLVA.Session.user.get('userNameDisplay');

To log out, call LLVA.Session.logout().

That's it!  Enjoy.

-Curtis Farnham, c. Spring 2011
 