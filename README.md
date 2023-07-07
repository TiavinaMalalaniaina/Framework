# auteur Tiavina Malalaniaina
# matricule ETU002025
# numero_classe 106

# DOCS

## Format du web.xml par défaut
    <?xml version="1.0" encoding="UTF-8"?>
    <web-app version="3.1" xmlns="http://xmlns.jcp.org/xml/ns/javaee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd">
        <servlet>
            <servlet-name>FrontServlet</servlet-name>
            <servlet-class>etu2025.framework.servlet.FrontServlet</servlet-class>
            <init-param>
                <param-name>model-package</param-name>
                <param-value>etu2025.model</param-value> // Le package où se trouve tous les models
            </init-param>
        </servlet>
        <servlet-mapping>
            <servlet-name>FrontServlet</servlet-name>
            <url-pattern>*.action</url-pattern> // Modifiable (ex: *.action, *.do, *.url)
        </servlet-mapping>
        <session-config>
            <session-timeout>
                30
            </session-timeout>
        </session-config>
    </web-app>

## Forme du modele:
    public class Model {
    
        // url est une annotation qui décrit quelle Url mènent vers la fonction
        // la fonction doit impérativement retourner un ModelView
        @url("/url.action")
        public ModelView nameFunction() {
            ModelView mv = new ModelView("view_name.jsp");
            return mv;
        }
    }

## Ajout d'attribut dans le modele: (Le nom dans les formulaires doivent correspondre au nom d' attribut)
    // Les types d'attributs ne doivent surtout pas être des types primitives
    public class Model {
        String nom;
        Integer age;
        Date dateNaissance;
        String[] hobbies;

        @url("/url.action")
        public ModelView nameFunction() {
            ModelView mv = new ModelView("view_name.jsp");
            mv.addItem("item1", getNom());
            mv.addItem("item2", getAge());
            mv.addItem("item3", getDateNaissance());
            mv.addItem("item4", getHobbies());
            return mv;
        }
        // getter et setter requis
    }

## Ajout de paramètre dans la fonction
    // Les types d'attributs ne doivent surtout pas être des types primitives
    @url("/url.action")
    public ModelView nameFunction(String param1, String param2) {
        ModelView mv = new ModelView("view_name.jsp");
        mv.addItem("item1", param1);
        mv.addItem("item2", param2);
        return mv;
    }
    
## Envoie de valeur vers la vue
    public class Model {
        String nom;
        Integer age;
        Date dateNaissance;
        String[] hobbies;

        @url("/url.action")
        public ModelView nameFunction(Integer nombre1, Integer nombre2) {
            ModelView mv = new ModelView("view_name.jsp");
            mv.addItem("resultat", nombre1 + nombre2);
            mv.addItem("nom", getNom());
            mv.addItem("age", getAge());
            mv.addItem("dateNaissance", getDateNaissance());
            mv.addItem("hobbies", getHobbies());
            return mv;
        }
        // getter et setter requis
    }

## Ajout de session
    @url("/url.action")
    public ModelView nameFunction() {
        ModelView mv = new ModelView("view_name.jsp");
        Object object = new Object();
        mv.addSession("session_name", object);
        return mv;
    }

## Recuperer les session
    public class Model {
        HashMap<String, Object> session = new HashMap<>(); // doit inclure attributs et ses getters et setters

        // Les fonctions voulant utilisés les sessions doit inclure cette annotation
        @session
        @url("/url.action")
        public ModelView nameFunction() {
            ModelView mv = new ModelView("view_name.jsp");
            Object object = getSession().get("session_name");
            return mv;
        }
    }

## Remove session
    public class Model {
        @url("/url.action")
        public ModelView nameFunction() {
            ModelView mv = new ModelView("view_name.jsp");
            mv.addRemovingSession("session_name");
            return mv;
        }
    }

## Remove All session
    @url("/url.action")
    public ModelView nameFunction() {
        ModelView mv = new ModelView("view_name.jsp");
        mv.invalidateSession(true);
        return mv;
    }

## Authentification

    //  la fonction est toujours accessible
    @url("/login.action")
    public ModelView login() {
        ModelView mv = new ModelView("loged.jsp");
        mv.addSession("profile", "admin");
        mv.addSession("isconnecter", true);
        return mv;
    }

    //  la fonction est toujours accessible
    @auth
    @url("/login.action")
    public ModelView login() {
        ModelView mv = new ModelView("logout.jsp");
        mv.invalidateSession(true);
        return mv;
    }

    //  le profile de l'utilisateur connecté doit être admin
    @auth("admin")
    @url("/url.action")
    public ModelView nameFunction() {
        ModelView mv = new ModelView("view_name.jsp");
        return mv;
    }

    //  l'utilisateur doit être connecté
    @auth
    @url("/url.action")
    public ModelView nameFunction() {
        ModelView mv = new ModelView("view_name.jsp");
        return mv;
    }

    // changement dans web.xml
    <?xml version="1.0" encoding="UTF-8"?>
    <web-app version="3.1" xmlns="http://xmlns.jcp.org/xml/ns/javaee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd">

        <servlet>
            <servlet-name>FrontServlet</servlet-name>
            <servlet-class>etu2025.framework.servlet.FrontServlet</servlet-class>
            <init-param>
                <param-name>model-package</param-name>
                <param-value>etu2025.model</param-value>
            </init-param>
            <init-param>
                <param-name>session-name-isconnected</param-name> 
                <param-value>isconnected</param-value> // modifiable, le nom de la session pour dire qu'un utilisateur est connecté
            </init-param>
            <init-param>
                <param-name>session-name-profile</param-name>
                <param-value>profile</param-value> // modifiable, le nom de la session pour dire le profile de l'utilisateur connecté
            </init-param>
        </servlet>
        <servlet-mapping>
            <servlet-name>FrontServlet</servlet-name>
            <url-pattern>*.action</url-pattern>
        </servlet-mapping>
        <session-config>
            <session-timeout>
                30
            </session-timeout>
        </session-config>
    </web-app>

## JSON avec ModelView
    @url("/json.action")
    public ModelView nameFunction() {
        ModelView mv = new ModelView();
        mv.activeJSON(); // Cette fonction permet de dire au framework de renvoyer au format JSON les Items envoyés
        mv.addItem("name", "Tiavina");
        mv.addItem("last_name", "Malalaniaina");
        return mv;
    }

## JSON sans ModelView
    @restAPI //Cette annotation renvoie le JSON de la valeur de retour de la fonction
    @url("/apirest.action")
    public Object apiRest() {
        return this;
    }

## FileUpload
    public class Model {
        FileUpload myFiles; // plus getter et setter

        // Les fonctions voulant utilisés les sessions doit inclure cette annotation
        @session
        @url("/url.action")
        public ModelView nameFunction() {
            ModelView mv = new ModelView("view_name.jsp");
            Object object = getSession().get("session_name");
            return mv;
        }
    }
### Web.xml
    <?xml version="1.0" encoding="UTF-8"?>
    <web-app version="3.1" xmlns="http://xmlns.jcp.org/xml/ns/javaee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd">
        <servlet>
            <servlet-name>FrontServlet</servlet-name>
            <servlet-class>etu2025.framework.servlet.FrontServlet</servlet-class>
            <init-param>
                <param-name>model-package</param-name>
                <param-value>etu2025.model</param-value>
            </init-param>
            <multipart-config>
                <max-file-size>5242880</max-file-size>
                <max-request-size>10485760</max-request-size>
                <file-size-threshold>0</file-size-threshold>
            </multipart-config>
        </servlet>
        <servlet-mapping>
            <servlet-name>FrontServlet</servlet-name>
            <url-pattern>*.action</url-pattern>
        </servlet-mapping>
        <session-config>
            <session-timeout>
                30
            </session-timeout>
        </session-config>
    </web-app>

## Singleton
    // Une classe singleton est une classe qui n'est instancier qu'une fois
    @scope("singleton")
    public class Model {
        FileUpload myFiles; // plus getter et setter

        // Les fonctions voulant utilisés les sessions doit inclure cette annotation
        @session
        @url("/url.action")
        public ModelView nameFunction() {
            ModelView mv = new ModelView("view_name.jsp");
            Object object = getSession().get("session_name");
            return mv;
        }
    }

## Package 
import etu2025.framework.ModelView;
import etu2025.framework.annotation.url;
import etu2025.framework.FileUpload;
import etu2025.framework.annotation.auth;
import etu2025.framework.annotation.restAPI;
import etu2025.framework.annotation.session;
import java.util.HashMap;