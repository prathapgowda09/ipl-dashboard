
package com.partha.ipldashboard.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.partha.ipldashboard.model.Match;

public class MatchDataProcessor implements ItemProcessor<MatchInput, Match> {

	private static final Logger log = LoggerFactory.getLogger(MatchDataProcessor.class);

	@Override
	public Match process(final MatchInput matchInput) throws Exception {

		Match match = new Match();
		match.setId(Long.parseLong(matchInput.getId()));
		match.setCity(matchInput.getCity());
		//System.out.println("-----------------------"+matchInput.getDate());
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate date = LocalDate.parse(matchInput.getDate(), formatter);
		//System.out.println("-----------------------"+date);
		match.setDate(date);
		//match.setDate(LocalDate.parse(matchInput.getDate()));
		match.setPlayeOfMatch(matchInput.getPlayer_of_match());
		match.setVenue(matchInput.getVenue());

		// set Team 1 and Team 2 depending on the innings order
		String firstInningsTeam, secondinningsTeam;

		if ("bat".equals(matchInput.getToss_decision())) {
			firstInningsTeam = matchInput.getToss_winner();
			secondinningsTeam = matchInput.getToss_winner().equals(matchInput.getTeam1()) ? matchInput.getTeam2()
					: matchInput.getTeam1();
		} else {
			secondinningsTeam = matchInput.getToss_winner();
			firstInningsTeam = matchInput.getToss_winner().equals(matchInput.getTeam1()) ? matchInput.getTeam2()
					: matchInput.getTeam1();
		}

		match.setTeam1(firstInningsTeam);
		match.setTeam2(secondinningsTeam);
		match.setTossWinner(matchInput.getToss_winner());
		match.setMatchWinner(matchInput.getWinner());
		match.setTossDecision(matchInput.getToss_decision());
		match.setResult(matchInput.getResult());
		match.setResultMargin(matchInput.getResult_margin());
		match.setUmpire1(matchInput.getUmpire1());
		match.setUmpire2(matchInput.getUmpire2());

		return match;
	}

}
