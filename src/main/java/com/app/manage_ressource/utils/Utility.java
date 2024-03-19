package com.app.manage_ressource.utils;


import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public final class Utility
{


	public static final String ATTACHED_PIECE_FOLDER = "attached_piece_folder/";

	// CONSTANT POUR LA SECURITE
//	public static final long EXPIRATION_TIME = 1*24* 60 * 60 ; // expire dans un 1 jour

	public static final long EXPIRATION_TIME = 1*2*60*60 ; // expire dans un 2h

	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SECRET = "javainuse";




	public static final String TYPERESSOURCES = "/typeressources";
	public static final String TYPERESSOURCE_ALL = "/typeressources/all";

	public static final String GET_TYPERESSOURCE_BY_ID = "/typeressources/{id}";
	public static final String UPDATE_TYPERESSOURCE = "/typeressources/update";

	public static final String RESSOURCES = "/ressources";
	public static final String RESSOURCES_SAMPLE = "/ressources/sample";
	public static final String RESSOURCES_DELETE = "/ressources/delete/{id}";

	public static final String RESSOURCES_WITH_RESERVATION = "/ressources/reservations";
	public static final String UPDATE_DEFINITELY = "/definitely/update";
	public static final String GET_DEFINITELY_BY_ID = "/definitely/{id}";
	public static final String GET_DEFINITELY_BY_RESSOURCE = "/definitely/ressource/{id}";

	public static final String DEFINITELY = "/definitely";

	public static final String UPDATE_RESSOURCE = "/ressources/update";
	public static final String GET_RESSOURCE_BY_ID = "/ressources/{id}";

	public static final String GET_RESSOURCE_BY_USER_ID = "/ressources/user";


	public static final String UNAVAILABILITIES = "/unavailabilities";
	public static final String UPDATE_UNAVAILABILITIE = "/unavailabilities/update";
	public static final String GET_UNAVAILABILITIE_RESTITUTION = "/unavailabilities/restitution";
	public static final String GET_UNAVAILABILITIE_ID = "/unavailabilities/{id}";

	public static final String GET_UNAVAILABILITIE_BY_RESSOURCE_ID = "/unavailabilities/ressource/{id}";


	public static final String PLANNINGS = "/plannings";
	public static final String UPDATE_PLANNING = "/plannings/update";
	public static final String DELETE_PLANNING_ID = "/plannings/delete/{id}";

	public static final String GET_PLANNING_ID = "/plannings/{id}";
	public static final String GET_PLANNING_BY_RESSOURCE_ID = "/plannings/ressource/{id}";
	public static final String GET_PLANNING_CHECK = "/plannings/checkdate";


	public static final String REGISTERFOLDERS_WITH_ATTACHED_PIECE = "/registerfolders/withattachedpieces";
	public static final String REGISTERFOLDERS = "/registerfolders";

	public static final String UPDATE_REGISTERFOLDER = "/registerfolders/update";
	public static final String GET_REGISTERFOLDER_BY_ID = "/registerfolders/{id}";
	public static final String ADD_ATTACHED_PIECE_TO_REGISTERFOLDERS = "/registerfolders/add/attachedpiece";


	public static final String ATTACHED_PIECES = "/attachedpieces";
	public static final String UPDATE_ATTACHED_PIECE = "/attachedpieces/update";
	public static final String GET_ATTACHED_PIECE_BY_ID = "/attachedpieces/{id}";
	public static final String GET_ATTACHED_PIECE_BY_FILENAME = "/attachedpieces/file/{filename}";


	public static final String USERS = "/users";
	public static final String USER_ALLS = "/users/all";
	public static final String UPDATE_USER = "/users/update";

	public static final String GET_USER_BY_ID = "/users/{id}";
	public static final String DELETE_USER_BY_ID = "/users/{id}";
	public static final String DO_LOGIN = "/login";
	public static final String UPDATE_PASSWORD = "/users/modifierpwd/{id}";


	// GENERER TOKEN

	public static boolean isCompareField(String fieldOfDb,String fieldOfEntity )
	{

		if(fieldOfEntity != null && fieldOfEntity.length() != 0
		   && fieldOfDb != null && fieldOfDb.length() != 0
		   && (fieldOfDb.toLowerCase().trim().contains(fieldOfEntity.toLowerCase().trim()) || fieldOfDb.toUpperCase().trim().contains(fieldOfEntity.toUpperCase().trim()) )

		)
		{
         return  true;
		}
		else if(fieldOfEntity == null)
		{
			return  true;
		}
		else {
			return  false;
		}
	}

	public static long currentSlot(long queueHourStart,long slot,int index)
	{

		    Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date(queueHourStart + (slot*60000*index)));
			calendar.getTime().getTime();
			return calendar.getTime().getTime();

	}

    public static File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;}

}
