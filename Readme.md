# Projet EBancking : Spring_Angular_Oauth2_OIDC_Keycloak

![img.png](img.png)

## Partie 1 : Mise en place de Keycloak et gestion de l'authentification avec OAuth 2.0 / OpenID Connect

### 1. Télécharger Keycloak 19

![img_1.png](img_1.png)

2. **Démarrer Keycloak**

![img_2.png](img_2.png)

3. **Créer un compte Admin**

![img_3.png](img_3.png)

4. **Créer une Realm**

![img_4.png](img_4.png)

![img_13.png](img_13.png)

5. **Créer un client à sécuriser**

![img_5.png](img_5.png)

6. **Créer des utilisateurs**

- USER1

![img_8.png](img_8.png)

![img_9.png](img_9.png)

- USER2

![img_10.png](img_10.png)

7. **Créer des rôles**

![img_7.png](img_7.png)

![img_6.png](img_6.png)

8. **Affecter les rôles aux utilisateurs**

- USER1

 ![img_11.png](img_11.png)

- USER2

![img_12.png](img_12.png)

9. **Avec PostMan** :

    - Tester l'authentification avec le mot de passe

   ![img_14.png](img_14.png)

   ![img_15.png](img_15.png)

   ![img_16.png](img_16.png)

    - Analyser les contenus des deux JWT Access Token et Refresh Token
   
      - JWT Access Token
   
      ![img_17.png](img_17.png)
   
      - Refresh Token
   
    - Tester l'authentification avec le Refresh Token
   
      ![img_18.png](img_18.png)

    - Tester l'authentification avec Client ID et Client Secret
   
      ![img_19.png](img_19.png)
      
      ![img_20.png](img_20.png)
      
      ![img_21.png](img_21.png)

   Remarque : même si on veut maintenant avec password on doit assigner client-secret
   
   ![img_22.png](img_22.png)
   
   - Changer les paramètres des Tokens Access Token et Refresh Token

## Part 2 : Sécuriser une architecture  micro-service Spring Angular avec Keycloak :  
### microservice inventory-service
créer le microservice

![img_23.png](img_23.png)
tester 
![img_24.png](img_24.png)
![img_25.png](img_25.png)
