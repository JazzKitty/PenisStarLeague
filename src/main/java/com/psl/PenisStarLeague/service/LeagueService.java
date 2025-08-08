package com.psl.PenisStarLeague.service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.psl.PenisStarLeague.dto.CreateLeagueDTO;
import com.psl.PenisStarLeague.dto.LeagueCardDTO;
import com.psl.PenisStarLeague.dto.LeagueDTO;
import com.psl.PenisStarLeague.dto.LeagueDictDTO;
import com.psl.PenisStarLeague.dto.LeagueEventDTO;
import com.psl.PenisStarLeague.dto.PendingUserLeagueDTO;
import com.psl.PenisStarLeague.dto.UserLeagueDTO;
import com.psl.PenisStarLeague.model.Event;
import com.psl.PenisStarLeague.model.Game;
import com.psl.PenisStarLeague.model.GameLeague;
import com.psl.PenisStarLeague.model.League;
import com.psl.PenisStarLeague.model.PSLUser;
import com.psl.PenisStarLeague.model.UserLeague;
import com.psl.PenisStarLeague.model.dictionary.LeaguePosition;
import com.psl.PenisStarLeague.model.dictionary.LeagueType;
import com.psl.PenisStarLeague.repo.GameRepository;
import com.psl.PenisStarLeague.repo.LeaguePositionRepository;
import com.psl.PenisStarLeague.repo.LeagueRepository;
import com.psl.PenisStarLeague.repo.LeagueTypeRepository;
import com.psl.PenisStarLeague.repo.UserLeagueRepository;
import com.psl.PenisStarLeague.repo.UserRepository;
import com.psl.PenisStarLeague.util.PSLUtil;

@Service
@Slf4j
@RequiredArgsConstructor
public class LeagueService {
    private final LeagueTypeRepository leagueTypeRepository;
    private final LeagueRepository leagueRepository;
    private final LeaguePositionRepository leaguePositionRepository;
    private final UserRepository userRepository;
    private final UserLeagueRepository userLeagueRepository;
    private final GameRepository gameRepository;

    public List<LeagueType> getLeagueTypes() {
        return leagueTypeRepository.findAll();
    }

    public boolean createLeague(int idUser, CreateLeagueDTO createLeagueDTO) {
        Integer leagueCount = leagueRepository.findByIdUserAndLeague(createLeagueDTO.league()).orElse(null);
        LeaguePosition leaguePosition = leaguePositionRepository.findByCode("OWN").orElse(null);

        // No leagues for this user under that name

        if (leagueCount == null || leagueCount == 0) {
            League league = new League();
            league.setLeague(createLeagueDTO.league());
            league.setDescription(createLeagueDTO.description());
            league.setLeagueType(leagueTypeRepository.getReferenceById(createLeagueDTO.idLeagueType()));

            UserLeague userLeague = new UserLeague();
            userLeague.setUser(userRepository.getReferenceById(idUser));
            userLeague.setLeaguePosition(leaguePosition);
            userLeague.setJoinDate(Instant.now());

            for (int idGame : createLeagueDTO.idGames()) {
                GameLeague gameLeague = new GameLeague();
                gameLeague.setGame(gameRepository.getReferenceById(idGame));
                league.addNewGameLeague(gameLeague);
            }

            league.addNewUserLeague(userLeague);

            leagueRepository.save(league);
            return true;
        }

        return false;
    }

    public List<LeagueCardDTO> getLeagueCards() {
        return leagueRepository.getLeagueCards().orElse(new ArrayList<>());
    }

    /**
     * get league for non-logged in user
     * 
     * @param idLeague
     * @return
     */
    public LeagueDTO getLeague(int idLeague) {
        return getLeague(idLeague, -1, null);
    }

    /**
     * get league for logged in user
     * 
     * @param idLeague
     * @param idUser
     * @return
     */
    public LeagueDTO getLeague(int idLeague, int idUser, TimeZone timeZone) {
        League league = leagueRepository.findByIdLeague(idLeague).orElse(null);

        if (league == null) {
            return null;
        } else {
            String userPositionCode = leaguePositionRepository.findByIdLeagueAndIdUser(idLeague, idUser).orElse(null);
            String leagueTypeCode = league.getLeagueType().getCode();
            if(userPositionCode == null || userPositionCode.isEmpty()){ // User is not part of the league and not 
                if(leagueTypeCode.equals("PRIV")){
                    return populateNonUserPrivateLeagueDTO(league, "");
                }else{
                    return populateUserOrPublicLeagueDTO(league, "", timeZone);
                }
            }else{
                switch (userPositionCode) {
                    case "USR":
                        return populateUserOrPublicLeagueDTO(league, "USR", timeZone);
                    case "OWN":
                        return populateOwnerLeagueDTO(league, timeZone);
                    case "PND":                
                        return populateNonUserPrivateLeagueDTO(league, "PND");
                }
            }

            return null;
        }
    }

    private static LeagueDTO populateNonUserPrivateLeagueDTO(League league, String leaguePosCode){
        int memberCount = 0; 
        String ownerName = "";
        for(UserLeague userLeague: league.getUserLeagues()){
            String userPosCode = userLeague.getLeaguePosition().getCode();
            if(userPosCode.equals("OWN")){
                ownerName = userLeague.getUser().getUserName();
            }
            
            if(userPosCode.equals("USR")){
                memberCount ++; 
            }
        } 

        Set<Game> games = new HashSet<>();
        for(GameLeague gameLeague: league.getGameLeagues()){
            games.add(gameLeague.getGame());
        }

        return new LeagueDTO(league.getIdLeague(), league.getLeague(), ownerName, "Y", memberCount, league.getLeague(), leaguePosCode, null, null, games, null); 

    }

    private static LeagueDTO populateUserOrPublicLeagueDTO(League league, String leaguePosCode, TimeZone timeZone){
        int memberCount = 0; 
        String ownerName = "";
        Set<UserLeagueDTO> userLeagueDTOs = new HashSet<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");

        for(UserLeague userLeague: league.getUserLeagues()){
            String userPosCode = userLeague.getLeaguePosition().getCode();
            if(userPosCode.equals("OWN")){
                ownerName = userLeague.getUser().getUserName();
            }
            
            if(userPosCode.equals("USR")){
                memberCount ++; 
                PSLUser user = userLeague.getUser();
                String joinDate = "";
                if (userLeague.getJoinDate() != null) { // format join date
                    joinDate = sdf.format(Date.from(userLeague.getJoinDate()));
                }

                userLeagueDTOs.add(new UserLeagueDTO(user.getIdUser(), user.getUserName(), user.getGamerTag(), user.getBio(), joinDate));
            }
        } 

        Set<Game> games = new HashSet<>();
        for(GameLeague gameLeague: league.getGameLeagues()){
            games.add(gameLeague.getGame());
        }

        Set<LeagueEventDTO> events = new HashSet<>();
        for(Event event: league.getEvents()){
            events.add(new LeagueEventDTO(event.getIdEvent(), event.getEvent(), getLeagueEventStr(event, timeZone)));
        }

        return new LeagueDTO(league.getIdLeague(), league.getLeague(), ownerName, "N", memberCount, league.getLeague(), leaguePosCode, userLeagueDTOs, null, games, events); 
    }

    private static LeagueDTO populateOwnerLeagueDTO(League league, TimeZone timeZone){
        int memberCount = 0; 
        String ownerName = "";
        Set<UserLeagueDTO> userLeagueDTOs = new HashSet<>();
        Set<PendingUserLeagueDTO> pendingUserLeagueDTOs = new HashSet<>();

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");

        for(UserLeague userLeague: league.getUserLeagues()){
            String userPosCode = userLeague.getLeaguePosition().getCode();

            PSLUser user = userLeague.getUser();

            if(userPosCode.equals("OWN")){
                ownerName = userLeague.getUser().getUserName();

            } else if(userPosCode.equals("USR")){
                memberCount ++; 
                String joinDate = "";

                if (userLeague.getJoinDate() != null) { // format join date
                    joinDate = sdf.format(Date.from(userLeague.getJoinDate()));
                }

                userLeagueDTOs.add(new UserLeagueDTO(user.getIdUser(), user.getUserName(), user.getGamerTag(), user.getBio(), joinDate));

            } else if(userPosCode.equals("PND")){
                pendingUserLeagueDTOs.add(new PendingUserLeagueDTO(user.getIdUser(), user.getUserName(), user.getGamerTag(), user.getBio()));
            }
        } 

        Set<Game> games = new HashSet<>();
        for(GameLeague gameLeague: league.getGameLeagues()){
            games.add(gameLeague.getGame());
        }

        Set<LeagueEventDTO> events = new HashSet<>();
        for(Event event: league.getEvents()){
            events.add(new LeagueEventDTO(event.getIdEvent(), event.getEvent(), getLeagueEventStr(event, timeZone)));
        }

        return new LeagueDTO(league.getIdLeague(), league.getLeague(), ownerName, "N", memberCount, league.getLeague(), "OWN", userLeagueDTOs, pendingUserLeagueDTOs, games, events); 

    }

    public boolean deleteLeague(int idLeague, int idUser) {
        League league = leagueRepository.findByIdUserAndIdLeague(idLeague, idUser).orElse(null);
        if (league == null) {
            return false;
        } else {
            leagueRepository.delete(league);
            return true;
        }
    }

    public boolean editTitle(int idLeague, int idUser, String title) {
        League league = leagueRepository.findByIdUserAndIdLeague(idLeague, idUser).orElse(null);
        if (league == null) {
            return false;
        } else {
            league.setLeague(title);
            leagueRepository.save(league);
            return true;
        }
    }

    public boolean editDescription(int idLeague, int idUser, String description) {
        League league = leagueRepository.findByIdUserAndIdLeague(idLeague, idUser).orElse(null);
        if (league == null) {
            return false;
        } else {
            league.setDescription(description);
            leagueRepository.save(league);
            return true;
        }
    }

    public boolean editPrimaryGames(int idLeague, int idUser, Set<Integer> idGames){
        League league = leagueRepository.findByIdUserAndIdLeague(idLeague, idUser).orElse(null);
        if (league == null) {
            return false;
        } else {
            Set<GameLeague> gameLeagues = league.getGameLeagues();
            Set<GameLeague> toRemove = new HashSet<>();
            for(GameLeague gameLeague: gameLeagues){
                int idGame = gameLeague.getGame().getIdGame();
                if(idGames.contains(idGame)){
                    idGames.remove(idGame);
                }else{
                    toRemove.add(gameLeague);
                }
            }
            league.getGameLeagues().removeAll(toRemove);
            
            for(int idGame: idGames){
                GameLeague newGameLeague = new GameLeague(); 
                newGameLeague.setGame(gameRepository.getReferenceById(idGame));
                league.addNewGameLeague(newGameLeague);
            }

            leagueRepository.save(league);
            return true;
        }
    }

    public List<LeagueDictDTO> getOwnedLeagues(int idUser) {
        List<LeagueDictDTO> l = leagueRepository.findOwnedLeagues(idUser);
        return l;
    }

    public boolean requestToJoin(int idUser, int idLeague) {
        int i = userLeagueRepository.findByIdLeagueIdUser(idLeague, idUser).orElse(null);
        if (i != 0) {// already a member or already requested to be a member
            return false;
        } else {
            String s = leagueTypeRepository.findLeagueTypeCodeByIdLeague(idLeague).orElse(null);
            if (s == null) {
                return false; // should not have a league without a type
            }
            UserLeague userLeague = new UserLeague();
            if (s.equals("PRIV")) {
                userLeague.setUser(userRepository.getReferenceById(idUser));
                userLeague.setLeague(leagueRepository.getReferenceById(idLeague));
                LeaguePosition leaguePosition = leaguePositionRepository.findByCode("PND").orElse(null);
                if (leaguePosition == null) {
                    return false; // bad but this is my fault
                }
                userLeague.setLeaguePosition(leaguePosition);
            } else {
                userLeague.setUser(userRepository.getReferenceById(idUser));
                userLeague.setLeague(leagueRepository.getReferenceById(idLeague));
                LeaguePosition leaguePosition = leaguePositionRepository.findByCode("USR").orElse(null);
                if (leaguePosition == null) {
                    return false; // bad but this is my fault
                }
                userLeague.setLeaguePosition(leaguePosition);
                userLeague.setJoinDate(Instant.now());

            }
            userLeagueRepository.save(userLeague);
            return true;
        }

    }

    /**
     * remove member from league 
     * @param idOwner idOwner (the person who should of made this request)
     * @param idLeague id of the league to remove the user from 
     * @param idMember id of the meember to remove
     * @return
     */
    public boolean removeMember(int idOwner, int idLeague, Set<Integer> idMembers){
        int status = userLeagueRepository.removeMember(idLeague, idMembers, idOwner);
        return status == 1; 
    }

    /**
     * remove member from league 
     * @param idOwner idOwner (the person who should of made this request)
     * @param idLeague id of the league to remove the user from 
     * @param idMember id of the meember to remove
     * @return
     */
    public boolean addMember(int idOwner, int idLeague, Set<Integer> idMembers){
        int status = userLeagueRepository.addMember(idLeague, idMembers, idOwner);
        return status == 1; 
    }

    /**
     * 
     * @param event
     * @param timeZone
     * @return
     */
    private static String getLeagueEventStr(Event event, TimeZone timeZone){
        ZonedDateTime zonedDateTime = event.getDate().atZone(PSLUtil.getIdZone(timeZone));
        String occursStr = "";

        if (event.getEventIntervalType() == null) {
            occursStr = "Occurs on " + PSLUtil.getDateString(zonedDateTime);
        } else {
            switch (event.getEventIntervalType().getIntervalType()) {
                case "Yearly":
                    occursStr = "Yearly on " + PSLUtil.getYearlyString(zonedDateTime);
                    break;
                case "Monthly":
                    occursStr = "Monthly on the " + PSLUtil.getMonthlyString(zonedDateTime);
                    break;
                case "Weekly":
                    occursStr = "Weekly every " + PSLUtil.getWeeklyString(zonedDateTime);
                    break;
                case "Daily":
                    occursStr = "Daily at " + PSLUtil.getTimeString(zonedDateTime.getHour(), zonedDateTime.getHour());
                    break;
            }
        }
        return occursStr; 
    }
}