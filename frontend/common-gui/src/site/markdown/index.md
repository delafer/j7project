### General 
The use of this basic module in the GUI simplifies the bootstrap, the 
is required to resolve the dependencies that we need. 

These include:

- AngularJS
- Bootstrap
- jQuery
- styles
- and more...

With the help of requireJS all dependencies are resolved. In the actual project 
then you just have to add

    <script src="webjars/requirejs/2.1.8/require.min.js" data-main="scripts/main.js"></script>

Enter in the index jsp and java script gets all required files. 

Furthermore, the favicon will, images, fonts, etc. posted here, so they do not in any GUI 
Project must be stored. 

The gui-angular Project is incorporated project as maven dependency in the actual (technical) GUI:

    <dependency>
        <groupId>net.j7.infrastructure.gui</groupId>
        <artifactId>gui-angular</artifactId>
    </dependency>

Since the Servlet Spec 2.3, it is possible to load resouces from the ClassPath, the 
are as dependency jar available. In this way, the scripts come in and so the 
Application.

---

### Direktives

---


### Partials (jsp includes)

---

#### Styles

Thus, one must look not all styles together, there are the styles include

    <jsp:include page="partials/styles.partial"/>

The favicon is there already included.

#### Spinner

For a centered loading bar there is also a include. 
The following at the appropriate place insert (under the ng-app):

    <jsp:include page="partials/spinner.partial"/>

Through a http Interceptor, which is "installed" in this project, 
you will getat the appropriate place an animated progress bar always 
when an http request is called.

---

# Infrastructure

---
    
# Config

---

#### Basis Config

