package com.psl.PenisStarLeague.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.psl.PenisStarLeague.dto.EventLeagueDTO;
import com.psl.PenisStarLeague.dto.LeagueCardDTO;
import com.psl.PenisStarLeague.dto.LeagueDTO;
import com.psl.PenisStarLeague.dto.LeagueDictDTO;
import com.psl.PenisStarLeague.dto.UserLeagueDTO;
import com.psl.PenisStarLeague.model.League;
import com.psl.PenisStarLeague.model.UserLeague;
import com.psl.PenisStarLeague.model.dictionary.LeaguePosition;
import com.psl.PenisStarLeague.model.dictionary.LeagueType;
import com.psl.PenisStarLeague.repo.LeaguePositionRepository;
import com.psl.PenisStarLeague.repo.LeagueRepository;
import com.psl.PenisStarLeague.repo.LeagueTypeRepository;
import com.psl.PenisStarLeague.repo.UserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class LeagueService {
    private final LeagueTypeRepository leagueTypeRepository;
    private final LeagueRepository leagueRepository; 
    private final LeaguePositionRepository leaguePositionRepository; 
    private final UserRepository userRepository; 

    public List<LeagueType> getLeagueTypes(){
        return leagueTypeRepository.findAll();
    }

    public boolean createLeague(int idUser, String leagueName, int idLeagueType, String description){
        Integer leagueCount = leagueRepository.findByIdUserAndLeague( leagueName).orElse(null);
        LeaguePosition leaguePosition = leaguePositionRepository.findByCode("OWN").orElse(null);  
        
        /** 
         * No leagues for this user under that name
         */
        if(leagueCount == null || leagueCount == 0){
            League league = new League();
            league.setLeague(leagueName);
            league.setDescription(description);
            league.setIdLeagueType(idLeagueType);

            UserLeague userLeague = new UserLeague();
            userLeague.setUser(userRepository.getReferenceById(idUser));
            userLeague.setLeaguePosition(leaguePosition);

            league.addNewUserLeague(userLeague);
            
            leagueRepository.save(league);
            return true;
        }
    
        return false; 
    }

    public List<LeagueCardDTO> getLeagueCards(){ 
        return leagueRepository.getLeagueCards().orElse(new ArrayList<>()); 
    }

    /**
     * get league for non-logged in user
     * @param idLeague
     * @return
     */
    public LeagueDTO getLeague(int idLeague){
        return getLeague(idLeague, -1);
    }

    /**
     * get league for logged in user
     * @param idLeague
     * @param idUser
     * @return
     */
    public LeagueDTO getLeague(int idLeague, int idUser) {
        League league = leagueRepository.findLeagueById(idLeague, idUser).orElse(null); 
        if(league == null){
            return null; 
        }else{
            String owner = "";
            String isOwner = "N";
            Set<UserLeagueDTO> users = new HashSet<>(); 
            Set<EventLeagueDTO> eventLeagueDTOs = new HashSet<>();

            for(UserLeague userLeague: league.getUserLeagues()){
                //Grab owner name
                if(userLeague.getLeaguePosition().getCode().equals("OWN")){
                    owner = userLeague.getUser().getUserName();
                    if(userLeague.getUser().getIdUser() == idUser){
                        isOwner = "Y"; // the person that made this request is the owner
                    }
                }

                //TODO: Change join date to something real
                UserLeagueDTO userLeagueDTO = new UserLeagueDTO(userLeague.getUser().getIdUser(), userLeague.getUser().getUserName(), "");
                users.add(userLeagueDTO);
            }

            LeagueDTO leagueDTO = new LeagueDTO(league.getIdLeague(),
            league.getLeague(),
            owner, 
            isOwner, league.getUserLeagues().size(), league.getDescription(), users, eventLeagueDTOs);
            return leagueDTO;
        }
    }

    public boolean deleteLeague(int idLeague, int idUser){
        League league = leagueRepository.findByIdUserAndIdLeague(idLeague, idUser).orElse(null); 
        if(league == null){
            return false;
        }else{
            leagueRepository.delete(league);
            return true;
        }
    }

    
    public boolean editTitle(int idLeague, int idUser, String title){
        League league = leagueRepository.findByIdUserAndIdLeague(idLeague, idUser).orElse(null); 
        if(league == null){
            return false;
        }else{
            league.setLeague(title);
            leagueRepository.save(league);
            return true;
        }
    }

    public boolean editDescription(int idLeague, int idUser, String description){
        League league = leagueRepository.findByIdUserAndIdLeague(idLeague, idUser).orElse(null); 
        if(league == null){
            return false;
        }else{
            league.setDescription(description);
            leagueRepository.save(league);
            return true;
        }
    }

    public List<LeagueDictDTO> getOwnedLeagues(int idUser){
        List<LeagueDictDTO> l = leagueRepository.findOwnedLeagues(idUser);
        return l;
    }

}