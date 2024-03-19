package com.app.manage_ressource.services;

import com.app.manage_ressource.dto.request.UserDtoRequest;
import com.app.manage_ressource.dto.response.UserDtoResponse;
import com.app.manage_ressource.entities.*;
import com.app.manage_ressource.repositories.IDaoUser;
import com.app.manage_ressource.security.JwtTokenUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
@Service
public class UserService implements  CrudService<UserDtoRequest>
{
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private IDaoUser userRepository;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired(required=true)
    private ModelMapper modelMapper;
    @Override
    public Reponse create(UserDtoRequest user) {
        Reponse reponse = new Reponse();

        try
        {
            if(user.getEmail() != null && user.getEmail().length() > 0 && user.getPhone() != null && user.getPhone().length() > 0)
            {
                userRepository.findByEmailOrPhone(user.getEmail(),user.getPhone()).stream().findFirst().ifPresentOrElse(
                        utilisateur ->
                        {
                            reponse.setMessage( utilisateur.isStatus() ?  "Cet email ou le téléphone  est déjà utilisé svp !" :
                                    "Cet email ou le téléphone est déjà utilisé par compte bloqué svp !");
                            logger.info( utilisateur.isStatus() ?  "Cet email  est déjà utilisé svp !" :
                                    "Cet email ou le téléphone est déjà utilisé par compte bloqué svp !");
                            reponse.setCode(202);
                        },
                        () ->
                        {
                            Utilisateur userConverted =modelMapper.map(user, Utilisateur.class);
                            userConverted.setStatus(true);
                            userConverted.setPassword(bCryptPasswordEncoder.encode(user.getEmail()));
                            Utilisateur userSave = userRepository.save(userConverted);
                            reponse.setData(modelMapper.map(userSave, UserDtoResponse.class));
                            reponse.setMessage("Ce compte a été enregistré avec succès");
                            logger.info("Ce compte a été enregistré avec succès ");
                            reponse.setCode(200);
                        }
                );

            }
            else
            {
                reponse.setMessage("Veuillez renseigner l'email et le téléphone svp !");
                logger.info("Veuillez renseigner l'email et le téléphone  svp ");
                reponse.setCode(201);
            }

        }
        catch (Exception e)
        {
            logger.error(" une exception est survenue "+e.getMessage());
            reponse.setCode(500);
            reponse.setMessage("Un problème de serveur  !");
        }

        return reponse ;
    }

    @Override
    public Reponse update(UserDtoRequest user) {
        Reponse reponse = new Reponse();
        try
        {

            if(user.getID() != null)
            {
                Optional<Utilisateur>  userById = userRepository.findById(user.getID());
                userRepository.findByEmail(user.getEmail()).ifPresentOrElse(
                        utilisateur ->
                        {
                            if(userById.get().getId().compareTo(utilisateur.getId()) != 0)
                            {
                                logger.info(" cet email est déjà utilisé   svp ");
                                reponse.setMessage("cet email est déjà utilisé   svp !");
                                reponse.setCode(201);
                            }
                            else
                            {
                                if(user.getLastname() != null && !user.getLastname().equals(userById.get().getLastname()))
                                {
                                    userById.get().setLastname(user.getLastname());
                                }
                                if(user.getFirstname() != null && !user.getFirstname().equals(userById.get().getFirstname()))
                                {
                                    userById.get().setFirstname(user.getFirstname());
                                }
                                if(user.getEmail() != null && !user.getEmail().equals(userById.get().getEmail()))
                                {
                                    userById.get().setEmail(user.getEmail());
                                }
                                if(user.getPhone() != null && !user.getPhone().equals(userById.get().getPhone()))
                                {
                                    userById.get().setPhone(user.getPhone());
                                }

                                if(user.getAdress() != null && !user.getAdress().equals(userById.get().getAdress()))
                                {
                                    userById.get().setAdress(user.getAdress());
                                }


                                if(user.getRole() != null && !user.getRole().equals(userById.get().getRole()))
                                {
                                    userById.get().setRole(user.getRole());
                                }

                                userById.get().setStatus(true);
                                Utilisateur userSave = userRepository.save(userById.get());
                                UserDtoResponse userDtoResponse=modelMapper.map(userSave, UserDtoResponse.class);
                                reponse.setData(userDtoResponse);
                                reponse.setMessage("Ce compte a été modifié avec succès");
                                reponse.setCode(200);
                                logger.info(" Ce compte a été modifié avec succès  " +userById.get().getEmail());


                            }
                        },
                        () ->
                        {

                            if(user.getLastname() != null && !user.getLastname().equals(userById.get().getLastname()))
                            {
                                userById.get().setLastname(user.getLastname());
                            }
                            if(user.getFirstname() != null && !user.getFirstname().equals(userById.get().getFirstname()))
                            {
                                userById.get().setFirstname(user.getFirstname());
                            }
                            if(user.getEmail() != null && !user.getEmail().equals(userById.get().getEmail()))
                            {
                                userById.get().setEmail(user.getEmail());
                            }
                            if(user.getPhone() != null && !user.getPhone().equals(userById.get().getPhone()))
                            {
                                userById.get().setPhone(user.getPhone());
                            }

                            if(user.getAdress() != null && !user.getAdress().equals(userById.get().getAdress()))
                            {
                                userById.get().setAdress(user.getAdress());
                            }


                            if(user.getRole() != null && !user.getRole().equals(userById.get().getRole()))
                            {
                                userById.get().setRole(user.getRole());
                            }

                            userById.get().setStatus(true);
                            Utilisateur userSave = userRepository.save(userById.get());
                            UserDtoResponse userDtoResponse=modelMapper.map(userSave, UserDtoResponse.class);
                            reponse.setData(userDtoResponse);
                            reponse.setMessage("Ce compte a été modifié avec succès");
                            reponse.setCode(200);
                            logger.info(" Ce compte a été modifié avec succès  " +userById.get().getEmail());



                        }
                );

            }
            else {
                logger.info(" Ce  personnels n'existe pas  ");
                reponse.setMessage(" Ce  personnels n'existe pas");
                reponse.setCode(201);
            }


        }
        catch (Exception e)
        {
            logger.error(" une exception est survenue "+e.getMessage());
            reponse.setCode(500);
            reponse.setMessage("Une erreur interne est survenue coté serveur  !");
        }

        return reponse ;
    }
    @Override
    public Reponse getById(UUID ID)
    {
        Reponse reponse = new Reponse();
        try
        {
            Optional<Utilisateur> user = userRepository.findById(ID);


            if(user.isPresent())
            {
                UserDtoResponse userConverted =modelMapper.map(user.get(), UserDtoResponse.class);
                reponse.setData(userConverted);
                reponse.setMessage("Ce compte a été retrouvé avec succès");
                logger.info("Ce compte a été retrouvé avec succès ");
                reponse.setCode(200);

            }
            else
            {
                reponse.setMessage("Ce compte n'existe pas");
                reponse.setCode(201);

            }


        }
        catch (Exception e) {
            logger.error(" une exception est survenue "+e.getMessage());
            reponse.setMessage("Une erreur interne est survenue");
            reponse.setCode(500);

        }

        return reponse;
    }

    @Override
    public Reponse getAll() {
        Reponse reponse = new Reponse();
        try
        {
            List<UserDtoResponse> users = userRepository.findAll()
                    .stream()
                    .map( v->modelMapper.map(v, UserDtoResponse.class))
                    .collect(Collectors.toList());
            reponse.setData(users);
            reponse.setMessage("Listes des personnels");
            reponse.setCode(200);


        }
        catch (Exception e) {
            logger.error(" une exception est survenue "+e.getMessage());
            reponse.setMessage("Une erreur interne est survenue");
            reponse.setCode(500);

        }

        return reponse;
    }


    public Reponse login_in(Login login)
    {

        Reponse response = new Reponse();
        try
        {
          userRepository.findByEmail(login.getEmail()).ifPresentOrElse(
                  userC ->
                  {
                      if(userC.isStatus() &&(bCryptPasswordEncoder.matches(login.getPassword(), userC.getPassword())))
                      {
                          HashMap<String, String> credentials = new HashMap<String, String>();
                          String token=this.getToken(login.getEmail(), login.getPassword());
                          credentials.put("id", userC.getId().toString());
                          credentials.put("token", token);
                          credentials.put("role", userC.getRole().name());
                          credentials.put("email", userC.getEmail());
                          credentials.put("lastname", userC.getLastname());
                          credentials.put("firstname", userC.getFirstname());
                          credentials.put("phone", userC.getPhone());
                          response.setCode(200);
                          response.setMessage("La connexion a reussi !");
                          logger.info("USER WITH EMAIL :"+login.getEmail() + " connected : "+new Date());
                          response.setData(credentials);

                      }
                      else
                      {
                          response.setCode(201);
                          logger.info("Mot de passe incorrect/ ce compte est verrouillé");
                          response.setMessage("Mot de passe incorrect/ ce compte est verrouillé");
                      }

                  },
                  () -> {
                      response.setCode(201);
                      logger.info(" Cet compte n'existe pas  EMAIL :  "+login.getEmail() + "PASSWORD :"+login.getPassword());
                      response.setMessage("Cet compte n'existe pas !");
                  });


        }
        catch (Exception e)
        {
            logger.error(" une exception est survenue "+e.getMessage());
            response.setCode(500);
            response.setMessage("Un problème de serveur !");
        }


        return response ;
    }
    public Reponse lockUser(UUID id)
    {
        Reponse reponse = new Reponse();
        try
        {
            Optional<Utilisateur> user = userRepository.findById(id);

            if(user.isPresent())
            {
                user.get().setStatus(!user.get().isStatus());
                Utilisateur userSave=userRepository.save(user.get());
                reponse.setData(modelMapper.map(userSave, UserDtoResponse.class));
                reponse.setMessage("Ce compte a été bloqué avec succès");
                logger.info("Ce compte a été bloqué avec succès EMAIL :  "+user.get().getEmail());

                reponse.setCode(200);

            }
            else
            {
                reponse.setMessage("Ce compte n'existe pas");
                logger.info("Ce compte n'existe pas ");

                reponse.setCode(201);

            }


        }
        catch (Exception e) {
            logger.error(" une exception est survenue "+e.getMessage());
            reponse.setMessage("Une erreur interne est survenue");
            reponse.setCode(500);

        }

        return reponse;
    }

    public Reponse changePassword(ChangePasswordDtoRequest changePasswordDtoRequest)
    {
        Reponse response = new Reponse();
        try
        {
            Optional<Utilisateur>  user = userRepository.findById(changePasswordDtoRequest.getId());

            if(user.isPresent())
            {
                    String pwdCryp = bCryptPasswordEncoder.encode(changePasswordDtoRequest.getNewPassword());
                    user.get().setPassword(pwdCryp);
                    userRepository.save(user.get());
                    response.setCode(200);
                    response.setMessage("Le mot de passe a été modifié avec succès : !");
                    logger.info("Le mot de passe a été modifié avec succès ");

                    response.setData(changePasswordDtoRequest.getNewPassword());


            }
            else
            {
                response.setCode(201);
                response.setMessage("Ce compte n'existe pas  !");
                logger.error("Ce compte n'existe pas ");


            }


        }
        catch (Exception e)
        {
            logger.error(" une exception est survenue ");
            response.setCode(500);
            response.setMessage("Une erreur serveur est survenue !");
        }


        return response ;

    }
    public String getToken(String phone , String password)
    {
        try {
            authenticate(phone,  password);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(phone);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return token;

    }
    public  void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }


    public void initAccount() {

        UserDtoRequest userDtoRequest = new UserDtoRequest();
        userDtoRequest.setRole(UtilisateurEnum.ADMINISTRATEUR);
        userDtoRequest.setEmail("arouna.sanou@nest.sn");
        userDtoRequest.setPhone("775073511");
        userDtoRequest.setFirstname("Admin");
        userDtoRequest.setLastname("Nest");
        Reponse reponse=this.create(userDtoRequest);
        logger.info(" Le compte par defaut a été crée avec un code : "+reponse.getCode() + " :"+reponse.getMessage());
    }

    public Reponse getAll(int page, int size, boolean active, UtilisateurEnum role)
    {
        long start = System.currentTimeMillis();
        logger.debug("Users multiple retrieve");
        Reponse reponse = new Reponse();
        HashMap<String, Object> datas = new HashMap<String, Object>();
        logger.debug("Retrieving Users in the DB");
        int skipCount = (page - 1) * size;

        try {
            List<Utilisateur> usergots =userRepository.findByStatusAndRole(active, role);
            List<Utilisateur> users =usergots.stream().skip(skipCount).limit(size).collect(Collectors.toList());
            logger.debug("{} Many Users retrieved successfully in {} ms", users.size(),
                    (System.currentTimeMillis() - start));
            datas.put("data", users);
            datas.put("totals", usergots.size());


            logger.debug("{} Users retrieved successfully in {} ms", users.size(),
                    (System.currentTimeMillis() - start));
            reponse.setData(datas);
            reponse.setMessage("Listes des personnels");
            reponse.setCode(200);
            return reponse;


        }

        catch (Exception e) {
            logger.error("list of users Error: {}, {}", e.getMessage(), e);
            reponse.setMessage("list of users  error");
            reponse.setCode(500);
            return reponse;
        }


    }
}
