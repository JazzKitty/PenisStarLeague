package com.psl.PenisStarLeague.service;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.psl.PenisStarLeague.dto.CreateLeagueDTO;
import com.psl.PenisStarLeague.dto.LeagueCardDTO;
import com.psl.PenisStarLeague.dto.LeagueDTO;
import com.psl.PenisStarLeague.dto.LeagueDictDTO;
import com.psl.PenisStarLeague.dto.UserLeagueDTO;
import com.psl.PenisStarLeague.model.Game;
import com.psl.PenisStarLeague.model.GameLeague;
import com.psl.PenisStarLeague.model.League;
import com.psl.PenisStarLeague.model.UserLeague;
import com.psl.PenisStarLeague.model.dictionary.LeaguePosition;
import com.psl.PenisStarLeague.model.dictionary.LeagueType;
import com.psl.PenisStarLeague.repo.GameLeagueRepository;
import com.psl.PenisStarLeague.repo.GameRepository;
import com.psl.PenisStarLeague.repo.LeaguePositionRepository;
import com.psl.PenisStarLeague.repo.LeagueRepository;
import com.psl.PenisStarLeague.repo.LeagueTypeRepository;
import com.psl.PenisStarLeague.repo.UserLeagueRepository;
import com.psl.PenisStarLeague.repo.UserRepository;

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
    private final GameLeagueRepository gameLeagueRepository;

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
            userLeague.setJoinDate(new Date());

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
        return getLeague(idLeague, -1);
    }

    /**
     * get league for logged in user
     * 
     * @param idLeague
     * @param idUser
     * @return
     */
    public LeagueDTO getLeague(int idLeague, int idUser) {
        League league = leagueRepository.findByIdLeague(idLeague).orElse(null);
        if (league == null) {
            return null;
        } else {
            String owner = "";
            String isOwner = "N";
            String isMember = "N";
            String userPending = "N";
            int memberCount = 0;
            Set<UserLeagueDTO> users = new HashSet<>();

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");

            for (UserLeague userLeague : league.getUserLeagues()) {
                String userPosition = userLeague.getLeaguePosition().getCode();
                String joinDate = "";
                String isPending = "N";

                if (userPosition.equals("OWN")) {
                    // Grab owner name
                    owner = userLeague.getUser().getUserName();
                    if (userLeague.getUser().getIdUser() == idUser) {
                        isOwner = "Y"; // the person that made this request is the owner
                    }
                }

                if (!userPosition.equals("PND")) {
                    memberCount++; // all none pending users are members
                    if (userLeague.getUser().getIdUser() == idUser) {
                        // user that requested the league is a member...
                        isMember = "Y";
                    }
                } else { // User is pending
                    isPending = "Y";
                    if (userLeague.getUser().getIdUser() == idUser) {
                        // user that requested the league is a pending...
                        userPending = "Y";
                    }
                }

                if (userLeague.getJoinDate() != null) { // format join date
                    joinDate = sdf.format(userLeague.getJoinDate());
                }

                UserLeagueDTO userLeagueDTO = new UserLeagueDTO(userLeague.getUser().getIdUser(),
                        userLeague.getUser().getUserName(), userLeague.getUser().getGamerTag(), joinDate, isPending);
                users.add(userLeagueDTO);
            }

            //Grab games
            Set<Game> games = new HashSet<>();
            for(GameLeague gameLeague: league.getGameLeagues()){
                games.add(gameLeague.getGame());
            }

            LeagueDTO leagueDTO;

            if (league.getLeagueType().getCode().equals("PRIV")) { // League is private
                if (isMember.equals("N")) { // League is private and user is not a member do not show the list of user
                                            // or event
                    leagueDTO = new LeagueDTO(idLeague, league.getLeague(), owner, isOwner, isMember, "Y", userPending,
                            memberCount,
                            league.getDescription(), null, games);
                } else { // League is private but user is a member so show them everything
                    leagueDTO = new LeagueDTO(idLeague, league.getLeague(), owner, isOwner, isMember, "Y", userPending,
                            memberCount,
                            league.getDescription(), users, games);
                }
            } else { // Public league so show the user everything no matter what
                leagueDTO = new LeagueDTO(idLeague, league.getLeague(), owner, isOwner, isMember, "N", "N", memberCount,
                        league.getDescription(), users, games);
            }

            return leagueDTO;
        }
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
                userLeague.setJoinDate(new Date());

            }
            userLeagueRepository.save(userLeague);
            return true;
        }

    }

}