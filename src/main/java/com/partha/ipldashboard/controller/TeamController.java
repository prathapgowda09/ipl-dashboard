package com.partha.ipldashboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.partha.ipldashboard.model.Team;
import com.partha.ipldashboard.repository.MatchRepository;
import com.partha.ipldashboard.repository.TeamRepository;

@RestController
public class TeamController {

	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private MatchRepository matchRepository;

	public TeamController(TeamRepository teamRepository, MatchRepository matchRepository) {
		this.teamRepository = teamRepository;
		this.matchRepository = matchRepository;
	}

	@GetMapping("/team/{teamName}")
	public Team getTeam(@PathVariable String teamName) {
		Team team = teamRepository.findByTeamName(teamName);
		team.setMatches(matchRepository.findLatestMatchesByTeam(teamName, 4));
		return team;
	}

}
